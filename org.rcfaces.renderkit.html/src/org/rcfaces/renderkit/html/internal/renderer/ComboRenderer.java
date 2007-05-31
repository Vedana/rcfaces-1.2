/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.capability.IDisabledCapability;
import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.component.capability.ITextDirectionCapability;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.core.model.IFiltredCollection;
import org.rcfaces.renderkit.html.internal.AbstractSelectItemsRenderer;
import org.rcfaces.renderkit.html.internal.IFilteredItemsRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.decorator.ComboDecorator;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.util.ListenerTools.INameSpace;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ComboRenderer extends AbstractSelectItemsRenderer implements
        IFilteredItemsRenderer {
    private static final String REVISION = "$Revision$";

    private static final String FILTRED_COLLECTION_PROPERTY = "camelia.combo.filtredCollection";

    protected String getActionEventName(INameSpace nameSpace) {
        return nameSpace.getSelectionEventName();
    }

    protected void encodeBeforeDecorator(IHtmlWriter htmlWriter,
            IComponentDecorator componentDecorator) throws WriterException {

        UIComponent combo = htmlWriter.getComponentRenderContext()
                .getComponent();

        htmlWriter.startElement(IHtmlWriter.SELECT);
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        if (combo instanceof ITextDirectionCapability) {
            writeTextDirection(htmlWriter, (ITextDirectionCapability) combo);
        }

        htmlWriter.writeName(htmlWriter.getComponentRenderContext()
                .getComponentClientId());

        if (isMultipleSelect(combo)) {
            htmlWriter.writeMultiple();
        }

        if (combo instanceof IDisabledCapability) {
            IDisabledCapability enabledCapability = (IDisabledCapability) combo;

            if (enabledCapability.isDisabled()) {
                htmlWriter.writeDisabled();
            }
        }

        int size = getRowNumber(combo);
        if (size > 0) {
            htmlWriter.writeSize(size);
        }

        if (combo instanceof IFilterCapability) {
            if (hasFilteredCollections(combo)) {
                htmlWriter.getComponentRenderContext().setAttribute(
                        FILTRED_COLLECTION_PROPERTY, Boolean.TRUE);

                htmlWriter.writeAttribute("v:filtred", true);
            }
        }
    }

    protected void encodeAfterDecorator(IHtmlWriter htmlWriter,
            IComponentDecorator componentDecorator) throws WriterException {

        htmlWriter.endElement(IHtmlWriter.SELECT);

        super.encodeAfterDecorator(htmlWriter, componentDecorator);
    }

    private boolean hasFilteredCollections(UIComponent combo) {
        List children = combo.getChildren();
        for (Iterator it = children.iterator(); it.hasNext();) {
            UIComponent child = (UIComponent) it.next();

            if ((child instanceof UISelectItems) == false) {
                continue;
            }

            UISelectItems selectItems = (UISelectItems) child;

            Object value = selectItems.getValue();
            if ((value instanceof IFiltredCollection) == false) {
                continue;
            }

            return true;
        }

        return false;
    }

    protected boolean isMultipleSelect(UIComponent component) {
        return false;
    }

    protected int getRowNumber(UIComponent component) {
        return 1;
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.COMBO;
    }

    protected void addRequiredJavaScriptClassNames(IHtmlWriter htmlWriter,
            Set classes) {
        super.addRequiredJavaScriptClassNames(htmlWriter, classes);

        if (htmlWriter.getComponentRenderContext().containsAttribute(
                FILTRED_COLLECTION_PROPERTY)) {

            IJavaScriptRenderContext javaScriptRenderContext = htmlWriter
                    .getHtmlComponentRenderContext().getHtmlRenderContext()
                    .getJavaScriptRenderContext();

            // On prend .COMBO en dure, car le filter n'est pas defini pour les
            // classes qui en héritent !
            javaScriptRenderContext.appendRequiredClasses(classes,
                    JavaScriptClasses.COMBO, "filter");
        }
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {

        IFilterProperties filterProperties = null;

        if (component instanceof IFilterCapability) {
            filterProperties = ((IFilterCapability) component)
                    .getFilterProperties();
        }

        return createComboDecorator(facesContext, component, filterProperties,
                false);
    }

    protected IComponentDecorator createComboDecorator(
            FacesContext facesContext, UIComponent component,
            IFilterProperties filterProperties, boolean jsVersion) {

        return new ComboDecorator(component, filterProperties, jsVersion);
    }

    public void encodeFilteredItems(IJavaScriptWriter jsWriter,
            IFilterCapability comboComponent,
            IFilterProperties filterProperties, int maxResultNumber)
            throws WriterException {

        IComponentDecorator componentDecorator = createComboDecorator(jsWriter
                .getFacesContext(), (UIComponent) comboComponent,
                filterProperties, true);
        if (componentDecorator == null) {
            return;
        }

        componentDecorator.encodeJavaScript(jsWriter);
    }
}