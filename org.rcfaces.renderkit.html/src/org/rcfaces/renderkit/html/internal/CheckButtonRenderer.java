/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.36  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.35  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.34  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.33  2006/03/15 13:53:04  oeuillot
 * Stabilisation
 * Ajout des bundles pour le javascript
 * R�organisation de l'arborescence de GridData qui n'herite plus de UIData
 *
 * Revision 1.32  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.31  2006/02/06 16:47:04  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.30  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.29  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.28  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.27  2005/12/27 16:08:16  oeuillot
 * Gestion imageButtonWriter
 * Ajout de fa_images
 * Preparation de f_imageCombo
 *
 * Revision 1.26  2005/11/17 10:04:56  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.25  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.24  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.23  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.22  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.21  2005/02/18 14:46:07  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.20  2004/12/30 17:24:20  oeuillot
 * Gestion des validateurs
 * Debuggage des composants
 *
 * Revision 1.19  2004/12/24 15:10:04  oeuillot
 * Refonte des tabbedPanes
 * Correction de problemes de value sur FieldSet nottament
 *
 * Revision 1.18  2004/12/22 12:16:15  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 * Revision 1.17  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 * Revision 1.16  2004/09/24 14:01:35  oeuillot
 * *** empty log message ***
 *
 * Revision 1.15  2004/09/13 08:34:26  oeuillot
 * *** empty log message ***
 *
 * Revision 1.14  2004/09/02 17:44:30  oeuillot
 * *** empty log message ***
 *
 * Revision 1.13  2004/09/01 13:47:36  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.CheckButtonComponent;
import org.rcfaces.core.component.capability.IHorizontalTextPositionCapability;
import org.rcfaces.core.component.capability.IRequiredCapability;
import org.rcfaces.core.event.PropertyChangeEvent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class CheckButtonRenderer extends AbstractInputRenderer {
    private static final String REVISION = "$Revision$";

    protected static final String DEFAULT_VALUE = "CHECKED";

    public static final String INPUT = "_input";

    public static final String TEXT = "_text";

    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        encodeComponent(htmlWriter);

        super.encodeEnd(writer);
    }

    protected void encodeComponent(IHtmlWriter htmlWriter)
            throws WriterException {
        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        CheckButtonComponent button = (CheckButtonComponent) componentRenderContext
                .getComponent();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        htmlWriter.startElement("SPAN");
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);
        if (button.isDisabled(facesContext)) {
            htmlWriter.writeAttribute("DISABLED");
        }

        if (button instanceof IRequiredCapability) {
            IRequiredCapability requiredCapability = (IRequiredCapability) button;

            if (requiredCapability.isRequired()) {
                htmlWriter.writeAttribute("v:required", "true");

                htmlWriter.enableJavaScript();
            }
        }

        String buttonId = componentRenderContext.getComponentId();

        String className = getStyleClassName(componentRenderContext, button);

        int horizontalTextPosition = button.getTextPosition(facesContext);
        if (horizontalTextPosition == IHorizontalTextPositionCapability.LEFT_POSITION) {
            writeLabel(htmlWriter, button, className, buttonId);

            writeInput(htmlWriter, button, className, buttonId);

        } else {
            writeInput(htmlWriter, button, className, buttonId);

            writeLabel(htmlWriter, button, className, buttonId);
        }

        htmlWriter.endElement("SPAN");
    }

    protected void writeInput(IHtmlWriter htmlWriter,
            CheckButtonComponent button, String className, String componentId)
            throws WriterException {

        String inputId = componentId + "_input";

        htmlWriter.startElement("INPUT");
        htmlWriter.writeAttribute("id", inputId);
        writeInputAttributes(htmlWriter, inputId);
        writeChecked(htmlWriter, button);

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();
        String value = getValue(facesContext, button);
        if (value != null) {
            htmlWriter.writeAttribute("value", value);
        }

        htmlWriter.writeAttribute("class", className + INPUT);

        if (htmlWriter.isJavaScriptEnabled() == false) {
            // Pour le FOCUS, pour retrouver le composant parent !
            htmlWriter.writeAttribute("v:container", componentId);
        }

        String accessKey = button.getAccessKey(facesContext);
        if (accessKey != null) {
            htmlWriter.writeAttribute("accessKey", accessKey);
        }

        htmlWriter.endElement("INPUT");
    }

    protected IHtmlWriter writeLabel(IHtmlWriter htmlWriter,
            CheckButtonComponent button, String className, String componentId)
            throws WriterException {
        htmlWriter.startElement("LABEL");
        String claz = className + TEXT;

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();
        if (button.isDisabled(facesContext)) {
            claz += "_disabled";
        }
        htmlWriter.writeAttribute("class", claz);

        String inputId = componentId + "_input";
        htmlWriter.writeAttribute("for", inputId);

        String text = button.getText(facesContext);
        HtmlTools.writeSpanAccessKey(htmlWriter, button, text, true);

        htmlWriter.endElement("LABEL");

        return htmlWriter;
    }

    protected String getValue(FacesContext facesContext,
            CheckButtonComponent component) {
        Object value = component.getValue();

        String svalue = convertValue(facesContext, component, value);

        if (svalue != null) {
            return svalue;
        }

        return DEFAULT_VALUE;
    }

    protected String getInputType(UIComponent component) {
        return CHECKBOX_TYPE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer#decode(javax.faces.component.UIComponent,
     *      org.rcfaces.core.internal.renderkit.IComponentData)
     */
    protected void decode(IRequestContext context, UIComponent element,
            IComponentData componentData) {
        super.decode(context, element, componentData);

        CheckButtonComponent button = (CheckButtonComponent) element;

        parseSelectedProperty(context.getFacesContext(), button, componentData);
    }

    protected void parseSelectedProperty(FacesContext facesContext,
            CheckButtonComponent button, IComponentData clientData) {
        String values[] = clientData.getComponentParameters();

        boolean selected = false;
        if (values != null && values.length > 0) {
            String componentValue = getValue(facesContext, button);
            if (componentValue != null) {
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];

                    if (componentValue.equals(value) == false) {
                        continue;
                    }

                    selected = true;

                    break;
                }
            }
        }

        if (button.isSelected(facesContext) != selected) {
            button.setSelected(selected);

            button.queueEvent(new PropertyChangeEvent(button,
                    Properties.SELECTED, Boolean.valueOf(selected == false),
                    Boolean.valueOf(selected)));
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#getJavaScriptClassName()
     */

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.CHECK_BUTTON;
    }
}