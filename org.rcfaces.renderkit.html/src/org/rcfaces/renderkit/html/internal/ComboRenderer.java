/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:38  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.18  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.17  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.16  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.15  2006/02/06 16:47:04  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.14  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.13  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.12  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.11  2005/12/28 11:12:48  oeuillot
 * Ajout des writer de Menu et ImageButton
 * Split de l'aspect fa_menu
 * Gestion de l'heritage d'aspect !
 *
 * Revision 1.10  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.9  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.8  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.7  2005/02/21 17:33:06  oeuillot
 * Reorganisation du JAVASCRIPT
 * Reorganisation des ImageXxxxButton
 * Reorganise le ComponentTools => Converters
 *
 * Revision 1.6  2005/02/18 14:46:07  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.5  2004/12/30 17:24:20  oeuillot
 * Gestion des validateurs
 * Debuggage des composants
 *
 * Revision 1.4  2004/12/24 15:10:04  oeuillot
 * Refonte des tabbedPanes
 * Correction de problemes de value sur FieldSet nottament
 *
 * Revision 1.3  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 * Revision 1.2  2004/09/24 14:01:35  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/09/13 08:34:26  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.capability.IDisabledCapability;
import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.core.model.IFilteredCollection;
import org.rcfaces.renderkit.html.internal.decorator.ComboDecorator;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ComboRenderer extends AbstractSelectItemsRenderer implements
        IFilteredItemsRenderer {
    private static final String REVISION = "$Revision$";

    private static final String FILTERED_COLLECTION_PROPERTY = "camelia.combo.filteredCollection";

    public void encodeBegin(IComponentWriter writer) throws WriterException {
        UIComponent combo = writer.getComponentRenderContext().getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement("SELECT");
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        htmlWriter.writeAttribute("name", htmlWriter
                .getComponentRenderContext().getComponentId());

        if (isMultipleSelect(combo)) {
            htmlWriter.writeAttribute("multiple");
        }

        if (combo instanceof IDisabledCapability) {
            IDisabledCapability enabledCapability = (IDisabledCapability) combo;

            if (enabledCapability.isDisabled()) {
                htmlWriter.writeAttribute("disabled");
            }
        }

        int size = getRowNumber(combo);
        if (size > 0) {
            htmlWriter.writeAttribute("size", size);
        }

        if (combo instanceof IFilterCapability) {
            if (hasFilteredCollections(combo)) {
                htmlWriter.getComponentRenderContext().setAttribute(
                        FILTERED_COLLECTION_PROPERTY, Boolean.TRUE);

                htmlWriter.writeAttribute("v:filtered", "true");
            }
        }

        super.encodeBegin(htmlWriter);
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
            if ((value instanceof IFilteredCollection) == false) {
                continue;
            }

            return true;
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer#encodeEnd(org.rcfaces.core.internal.renderkit.IWriter)
     */

    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.endElement("SELECT");

        super.encodeEnd(htmlWriter);
    }

    protected boolean isMultipleSelect(UIComponent component) {
        return false;
    }

    protected int getRowNumber(UIComponent component) {
        return 1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#getJavaScriptClassName()
     */
    protected String getJavaScriptClassName() {
        return JavaScriptClasses.COMBO;
    }

    protected void addRequiredJavaScriptClassNames(IHtmlWriter htmlWriter,
            Set classes) {
        super.addRequiredJavaScriptClassNames(htmlWriter, classes);

        if (htmlWriter.getComponentRenderContext().containsAttribute(
                FILTERED_COLLECTION_PROPERTY)) {

            IJavaScriptRenderContext javaScriptRenderContext = getHtmlRenderContext(
                    htmlWriter).getJavaScriptRenderContext();

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