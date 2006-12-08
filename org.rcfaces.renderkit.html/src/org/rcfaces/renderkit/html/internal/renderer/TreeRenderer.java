/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.lang.reflect.Array;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.component.MenuComponent;
import org.rcfaces.core.component.TreeComponent;
import org.rcfaces.core.component.capability.ICardinality;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.AbstractSelectItemsRenderer;
import org.rcfaces.renderkit.html.internal.ICssWriter;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.decorator.ISelectItemNodeWriter;
import org.rcfaces.renderkit.html.internal.decorator.SubMenuDecorator;
import org.rcfaces.renderkit.html.internal.decorator.TreeDecorator;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TreeRenderer extends AbstractSelectItemsRenderer {
    private static final String REVISION = "$Revision$";

    private static final String NODE_ROW_ID = "#node";

    protected void encodeBeforeDecorator(IHtmlWriter htmlWriter,
            IComponentDecorator componentDecorator) throws WriterException {
        super.encodeBeforeDecorator(htmlWriter, componentDecorator);

        IComponentRenderContext componentContext = htmlWriter
                .getComponentRenderContext();

        TreeComponent treeComponent = (TreeComponent) componentContext
                .getComponent();
        FacesContext facesContext = componentContext.getFacesContext();

        htmlWriter.startElement("UL");

        htmlWriter.writeRole("tree");

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        if (treeComponent.isCheckable(facesContext)) {
            int cardinality = treeComponent.getCheckCardinality(facesContext);
            if (cardinality == 0) {
                cardinality = ICardinality.DEFAULT_CARDINALITY;
            }

            htmlWriter.writeAttribute("v:checkCardinality", cardinality);

            if (treeComponent.isClientCheckFullState(facesContext)) {
                htmlWriter.writeAttribute("v:clientCheckFullState", "true");
            }
        }

        if (treeComponent.isSelectable(facesContext)) {
            int cardinality = treeComponent
                    .getSelectionCardinality(facesContext);
            if (cardinality == 0) {
                cardinality = ICardinality.DEFAULT_CARDINALITY;
            }

            htmlWriter.writeAttribute("v:selectionCardinality", cardinality);

            if (treeComponent.isClientSelectionFullState(facesContext)) {
                htmlWriter.writeAttribute("v:clientSelectionFullState", "true");
            }
        }

        if (treeComponent.isUserExpandable(facesContext) == false) {
            htmlWriter.writeAttribute("v:userExpandable", "false");
        }

        if (treeComponent.isHideRootExpandSign(facesContext)) {
            htmlWriter.writeAttribute("v:hideRootExpandSign", "true");
        }

        int depthLevel = treeComponent.getPreloadedLevelDepth(facesContext);
        if (depthLevel > 0) {
            htmlWriter.writeAttribute("v:preloadedLevelDepth", depthLevel);
        }
    }

    protected void encodeAfterDecorator(IHtmlWriter htmlWriter,
            IComponentDecorator componentDecorator) throws WriterException {
        super.encodeAfterDecorator(htmlWriter, componentDecorator);

        htmlWriter.endElement("UL");
    }

    protected void addRequiredJavaScriptClassNames(IHtmlWriter htmlWriter,
            Set classes) {
        super.addRequiredJavaScriptClassNames(htmlWriter, classes);

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        IJavaScriptRenderContext javaScriptRenderContext = htmlWriter
                .getHtmlComponentRenderContext().getHtmlRenderContext()
                .getJavaScriptRenderContext();

        TreeComponent treeComponent = (TreeComponent) htmlWriter
                .getComponentRenderContext().getComponent();
        IMenuIterator menuIterator = treeComponent.listMenus();
        if (menuIterator.hasNext()) {

            javaScriptRenderContext.appendRequiredClasses(classes,
                    JavaScriptClasses.TREE, "menu");
        }

        if (treeComponent.getPreloadedLevelDepth(facesContext) > 0) {
            javaScriptRenderContext.appendRequiredClasses(classes,
                    JavaScriptClasses.TREE, "ajax");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#getJavaScriptClassName()
     */
    protected String getJavaScriptClassName() {
        return JavaScriptClasses.TREE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractCssRenderer#writeCustomCss(org.rcfaces.core.internal.renderkit.IWriter,
     *      org.rcfaces.core.internal.renderkit.html.AbstractCssRenderer.CssWriter)
     */
    protected void writeCustomCss(IHtmlWriter htmlWriter, ICssWriter cssWriter) {
        super.writeCustomCss(htmlWriter, cssWriter);

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        TreeComponent treeComponent = (TreeComponent) componentRenderContext
                .getComponent();
        FacesContext facesContext = componentRenderContext.getFacesContext();

        if (treeComponent.getWidth(facesContext) != null
                || treeComponent.getHeight(facesContext) != null) {
            cssWriter.writeOverflow("auto");
        }

        if (treeComponent.isBorder(facesContext) == false) {
            cssWriter.writeBorderStyle("none");
        }
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {

        IComponentDecorator decorator = new TreeDecorator(
                (TreeComponent) component);

        TreeComponent treeComponent = (TreeComponent) component;

        IComponentDecorator menuDecorators = null;
        IMenuIterator menuIterator = treeComponent.listMenus();
        for (; menuIterator.hasNext();) {
            MenuComponent menuComponent = menuIterator.next();

            IComponentDecorator menuDecorator = new SubMenuDecorator(
                    menuComponent, menuComponent.getMenuId(), null,
                    menuComponent.isRemoveAllWhenShown(facesContext),
                    getItemImageWidth(menuComponent),
                    getItemImageHeight(menuComponent));

            if (menuDecorators == null) {
                menuDecorators = menuDecorator;
                continue;
            }

            menuDecorator.addChildDecorator(menuDecorators);
            menuDecorators = menuDecorator;
        }

        if (menuDecorators != null) {
            decorator.addChildDecorator(menuDecorators);
        }

        return decorator;
    }

    protected int getItemImageHeight(IMenuComponent menuComponent) {
        return -1;
    }

    protected int getItemImageWidth(IMenuComponent menuComponent) {
        return -1;
    }

    public void encodeNodes(IJavaScriptWriter jsWriter,
            TreeComponent treeComponent, ISelectItemNodeWriter nodeRenderer,
            int depth, String containerVarId) throws WriterException {

        TreeDecorator selectItemNodeWriter = (TreeDecorator) getComponentDecorator(jsWriter
                .getHtmlComponentRenderContext());

        selectItemNodeWriter.encodeNodes(jsWriter, treeComponent, nodeRenderer,
                depth, containerVarId);
    }

    public ISelectItemNodeWriter getSelectItemNodeWriter(
            IComponentRenderContext componentRenderContext) {
        return (ISelectItemNodeWriter) getComponentDecorator(componentRenderContext);
    }

    public Object getConvertedValue(FacesContext context,
            UIComponent component, Object submittedValue)
            throws ConverterException {

        if (submittedValue == null
                || submittedValue.getClass().isArray() == false
                || Array.getLength(submittedValue) < 1) {
            return super.getConvertedValue(context, component, submittedValue);
        }

        Object array[] = (Object[]) submittedValue;
        Object ret[] = new Object[array.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = super.getConvertedValue(context, component, array[i]);
        }

        return ret;
    }
}