/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.17  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.16  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.15  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
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
 * Revision 1.10  2005/12/27 16:08:16  oeuillot
 * Gestion imageButtonWriter
 * Ajout de fa_images
 * Preparation de f_imageCombo
 *
 * Revision 1.9  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.8  2005/10/05 14:34:19  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.7  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.6  2005/03/18 14:42:50  oeuillot
 * Support de la table des symbols pour le javascript compress�
 * Menu du style XP et pas Office !
 *
 * Revision 1.5  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.4  2005/02/21 17:33:06  oeuillot
 * Reorganisation du JAVASCRIPT
 * Reorganisation des ImageXxxxButton
 * Reorganise le ComponentTools => Converters
 *
 * Revision 1.3  2005/02/18 14:46:06  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.2  2004/12/24 15:10:04  oeuillot
 * Refonte des tabbedPanes
 * Correction de problemes de value sur FieldSet nottament
 *
 * Revision 1.1  2004/12/22 12:16:14  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.rcfaces.core.component.ToolBarComponent;
import org.rcfaces.core.event.ItemActionEvent;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IEventData;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.decorator.ToolBarDecorator;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class ToolBarRenderer extends AbstractSelectItemsRenderer {
    private static final String REVISION = "$Revision$";

    protected void encodeBeforeDecorator(IHtmlWriter writer,
            IComponentDecorator componentDecorator) throws WriterException {
        super.encodeBeforeDecorator(writer, componentDecorator);

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        writer.startElement("DIV");
        writeHtmlAttributes(writer);
        writeJavaScriptAttributes(writer);
        writeCssAttributes(writer);

        ToolBarComponent toolBarComponent = (ToolBarComponent) componentRenderContext
                .getComponent();
        if (toolBarComponent.isDisabled(facesContext) == false) {
            writer.writeAttribute("v:disabled", "true");
        }
        if (toolBarComponent.isReadOnly(facesContext)) {
            writer.writeAttribute("v:readOnly", "true");
        }

        String className = getStyleClassName(componentRenderContext);
        // Un dummy pour eviter des sauts de pages
        writer.startElement("A");
        String cls = className + "_itemFolder";
        writer.writeAttribute("class", cls);
        writer.endElement("A");
    }

    protected void encodeAfterDecorator(IHtmlWriter writer,
            IComponentDecorator componentDecorator) throws WriterException {

        writer.endElement("DIV");

        super.encodeAfterDecorator(writer, componentDecorator);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#getJavaScriptClassName()
     */
    protected String getJavaScriptClassName() {
        return JavaScriptClasses.TOOL_BAR;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer#getDecodesChildren()
     */
    public boolean getDecodesChildren() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer#decodeEvent(org.rcfaces.core.internal.renderkit.IRequestContext,
     *      javax.faces.component.UIComponent, java.lang.String,
     *      java.lang.String)
     */
    protected void decodeEvent(IRequestContext context, UIComponent component,
            IEventData eventData) {

        if (eventData != null
                && JavaScriptClasses.EVENT_SELECTION.equals(eventData
                        .getEventName())) {

            Object value = eventData.getEventValue();

            // TODO: Il faut converir la value de l'item ...

            ActionEvent actionEvent = new ItemActionEvent(component, value);
            component.queueEvent(actionEvent);

            return;
        }

        super.decodeEvent(context, component, eventData);
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {
        return new ToolBarDecorator(component);
    }
}