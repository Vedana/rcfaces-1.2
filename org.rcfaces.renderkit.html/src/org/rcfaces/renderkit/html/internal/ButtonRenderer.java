/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.26  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.25  2006/04/27 13:49:47  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.24  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.23  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.22  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.21  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.20  2005/10/05 14:34:19  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.19  2005/09/16 09:54:41  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.18  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.17  2005/02/18 14:46:06  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.16  2004/12/24 15:10:04  oeuillot
 * Refonte des tabbedPanes
 * Correction de problemes de value sur FieldSet nottament
 *
 * Revision 1.15  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 * Revision 1.14  2004/09/24 14:01:35  oeuillot
 * *** empty log message ***
 *
 * Revision 1.13  2004/09/02 17:44:30  oeuillot
 * *** empty log message ***
 *
 * Revision 1.12  2004/08/30 08:28:47  oeuillot
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;

import org.rcfaces.core.component.ButtonComponent;
import org.rcfaces.core.event.SelectionEvent;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.util.ListenerTools.INameSpace;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ButtonRenderer extends AbstractInputRenderer {
    private static final String REVISION = "$Revision$";

    // On le met sur le end, car des clientsDatas ... et autres peuvent survenir
    // ...
    public void encodeEnd(IComponentWriter writer) throws WriterException {
        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        encodeComponent(htmlWriter);

        super.encodeEnd(htmlWriter);
    }

    protected void encodeComponent(IHtmlWriter htmlWriter)
            throws WriterException {
        ButtonComponent button = (ButtonComponent) htmlWriter
                .getComponentRenderContext().getComponent();

        htmlWriter.startElement("INPUT");

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);
        writeInputAttributes(htmlWriter);

        String txt = button.getText();
        if (txt != null) {
            htmlWriter.writeAttribute("value", txt);
        }

        htmlWriter.endElement("INPUT");
    }

    protected boolean isNameEqualsId() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractInputRenderer#getInputType()
     */
    protected String getInputType(UIComponent component) {
        return BUTTON_TYPE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer#decode(javax.faces.component.UIComponent,
     *      org.rcfaces.core.internal.renderkit.IComponentData)
     */
    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        if (componentData.isEventComponent()) {
            return;
        }

        // Si il n'y a pas d'evenement Camelia, on regarde les evenements HTML !
        String value = componentData.getComponentParameter();
        if (value != null) {
            ActionEvent actionEvent = new SelectionEvent(component, null, null,
                    0);
            actionEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            component.queueEvent(actionEvent);

            return;
        }

        // Position X Y ?
        String id = component.getId();

        String x = componentData.getParameter(id + ".x");
        if (x == null) {
            return;
        }

        String y = componentData.getParameter(id + ".y");
        if (y == null) {
            return;
        }

        int buttons = SelectionEvent.UNKNOWN_BUTTONS;
        int modifiers = SelectionEvent.UNKNOWN_MODIFIERS;

        int px = SelectionEvent.UNKNOWN_POSITION;
        int py = SelectionEvent.UNKNOWN_POSITION;

        if (x.length() > 0) {
            try {
                px = Integer.parseInt(x);
            } catch (NumberFormatException ex) {
                FacesContext.getCurrentInstance().getExternalContext().log(
                        "Can not parse X position '" + x + "'.", ex);
            }
        }

        if (y.length() > 0) {
            try {
                py = Integer.parseInt(y);

            } catch (NumberFormatException ex) {
                FacesContext.getCurrentInstance().getExternalContext().log(
                        "Can not parse Y position '" + y + "'.", ex);
            }
        }

        ActionEvent actionEvent = new SelectionEvent(component, 0, px, py,
                buttons, modifiers);
        actionEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
        component.queueEvent(actionEvent);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#getJavaScriptClassName()
     */
    protected String getJavaScriptClassName() {
        return JavaScriptClasses.BUTTON;
    }

    protected String getActionEventName(INameSpace nameSpace) {
        return nameSpace.getSelectionEventName();
    }
}