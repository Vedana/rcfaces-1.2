/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.22  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.21  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.20  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.19  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.18  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.17  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.16  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.15  2005/12/27 16:08:16  oeuillot
 * Gestion imageButtonWriter
 * Ajout de fa_images
 * Preparation de f_imageCombo
 *
 * Revision 1.14  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.13  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.12  2005/10/28 14:41:50  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.11  2005/10/05 14:34:19  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.10  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.9  2005/03/18 14:42:50  oeuillot
 * Support de la table des symbols pour le javascript compress�
 * Menu du style XP et pas Office !
 *
 * Revision 1.8  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.7  2004/12/24 15:10:04  oeuillot
 * Refonte des tabbedPanes
 * Correction de problemes de value sur FieldSet nottament
 *
 * Revision 1.6  2004/12/22 12:16:15  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 * Revision 1.5  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 * Revision 1.4  2004/09/24 14:01:35  oeuillot
 * *** empty log message ***
 *
 * Revision 1.3  2004/09/02 17:44:30  oeuillot
 * *** empty log message ***
 *
 * Revision 1.2  2004/09/01 13:47:36  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/31 08:29:46  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.MessageComponent;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ContextTools;
import org.rcfaces.core.internal.tools.MessageTools;


/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class MessageRenderer extends AbstractCssRenderer {
    public static final String REVISION = "$Revision$";

    private static final String SUMMARY = "_summary";

    private static final String DETAIL = "_detail";

    private static final String STYLE_CLASS_PROPERTY = "message.style.class";

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer#encodeBegin(org.rcfaces.core.internal.renderkit.IWriter)
     */
    protected void encodeBegin(IComponentWriter writer) throws WriterException {
        IComponentRenderContext componentContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentContext.getFacesContext();

        MessageComponent messageComponent = (MessageComponent) componentContext
                .getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement("DIV");
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        String forValue = messageComponent.getFor();
        if (forValue != null) {
            htmlWriter.writeAttribute("v:for", forValue);
        }

        if (messageComponent.isShowSummary()) {
            htmlWriter.writeAttribute("v:showSummary", "true");
        }

        if (messageComponent.isShowDetail()) {
            htmlWriter.writeAttribute("v:showDetail", "true");
        }

        String infoStyleClass = messageComponent
                .getInfoStyleClass(facesContext);
        if (infoStyleClass != null) {
            htmlWriter.writeAttribute("v:infoStyleClass", infoStyleClass);
        }

        String warnStyleClass = messageComponent
                .getWarnStyleClass(facesContext);
        if (warnStyleClass != null) {
            htmlWriter.writeAttribute("v:warnStyleClass", warnStyleClass);
        }

        String errorStyleClass = messageComponent
                .getErrorStyleClass(facesContext);
        if (errorStyleClass != null) {
            htmlWriter.writeAttribute("v:errorStyleClass", errorStyleClass);
        }

        String fatalStyleClass = messageComponent
                .getFatalStyleClass(facesContext);
        if (fatalStyleClass != null) {
            htmlWriter.writeAttribute("v:fatalStyleClass", fatalStyleClass);
        }

        htmlWriter.endElement("DIV");

        htmlWriter.enableJavaScript();
    }

    protected void writeText(IHtmlWriter htmlWriter,
            MessageComponent messageComponent, String bundleVar, String text)
            throws WriterException {
        if (bundleVar != null) {
            FacesContext facesContext = htmlWriter.getComponentRenderContext()
                    .getFacesContext();

            text = ContextTools.resolveText(facesContext, bundleVar, text);
        }

        if (text == null || text.trim().length() < 1) {
            return;
        }

        htmlWriter.writeText(text);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#encodeJavaScript(org.rcfaces.core.internal.renderkit.html.IJavaScriptWriter)
     */
    protected void encodeJavaScript(IJavaScriptWriter js)
            throws WriterException {
        super.encodeJavaScript(js);

        IHtmlRenderContext htmlRenderContext = getHtmlRenderContext(js
                .getWriter());
        MessageComponent messageComponent = (MessageComponent) htmlRenderContext
                .getComponent();

        FacesContext facesContext = js.getFacesContext();

        String forValue = messageComponent.getFor();
        if (forValue == null) {
            return;
        }

        Iterator iterator = MessageTools.listMessages(facesContext, forValue,
                messageComponent);
        if (iterator.hasNext() == false) {
            return;
        }

        FacesMessage facesMessage = (FacesMessage) iterator.next();

        String bundleVar = messageComponent.getBundleVar(facesContext);

        writeMessage(js, facesMessage, forValue, false, bundleVar);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#getJavaScriptClassName()
     */
    protected String getJavaScriptClassName() {
        return JavaScriptClasses.MESSAGE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer#decode(org.rcfaces.core.internal.renderkit.IRequestContext,
     *      javax.faces.component.UIComponent,
     *      org.rcfaces.core.internal.renderkit.IComponentData)
     */
    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {

        MessageComponent messageComponent = (MessageComponent) component;

        Boolean showDetail = componentData.getBooleanProperty("showDetail");
        if (showDetail != null) {
            messageComponent.setShowDetail(showDetail.booleanValue());
        }

        Boolean showSummary = componentData.getBooleanProperty("showSummary");
        if (showSummary != null) {
            messageComponent.setShowDetail(showSummary.booleanValue());
        }
        /*
         * String forValue=componentData.getProperty("for"); if (forValue!=null) {
         * messageComponent.setFor(forValue); }
         */

        super.decode(context, component, componentData);
    }

    public String getStyleClassName(
            IComponentRenderContext componentRenderContext,
            UIComponent component, String suffix) {
        String styleClass = (String) componentRenderContext
                .getAttribute(STYLE_CLASS_PROPERTY);
        if (styleClass != null) {
            return styleClass;
        }

        return super.getStyleClassName(componentRenderContext, component,
                suffix);
    }

    static IJavaScriptWriter writeMessage(IJavaScriptWriter js,
            FacesMessage facesMessage, String componentId, boolean isGlobal,
            String bundleVar) throws WriterException {

        IHtmlRenderContext htmlRenderContext = (IHtmlRenderContext) js
                .getComponentRenderContext().getRenderContext();

        IJavaScriptRenderContext javascriptRenderContext = htmlRenderContext
                .getJavaScriptRenderContext();

        FacesContext facesContext = js.getFacesContext();

        boolean declare[] = new boolean[1];
        String key = javascriptRenderContext.allocateFacesMessage(facesMessage,
                declare);

        int pred;
        if (declare[0]) {
            String summary = facesMessage.getSummary();
            if (summary != null) {
                if (bundleVar != null) {
                    summary = ContextTools.resolveText(facesContext, bundleVar,
                            summary);
                }

                summary = js.allocateString(summary);
            }

            String detail = facesMessage.getDetail();
            if (detail != null) {
                if (bundleVar != null) {
                    detail = ContextTools.resolveText(facesContext, bundleVar,
                            detail);
                }
                detail = js.allocateString(detail);
            }

            js.write("var ").write(key).write("=new ").writeCall(null,
                    "f_messageObject");

            pred = 0;

            Severity severity = facesMessage.getSeverity();
            // La severity ne peut etre null !
            js.writeInt(severity.getOrdinal());

            if (summary != null) {
                for (; pred > 0; pred--) {
                    js.write(',').writeNull();
                }

                js.write(',').write(summary);
            } else {
                pred++;
            }

            if (detail != null && detail.equals(summary) == false) {
                for (; pred > 0; pred--) {
                    js.write(',').writeNull();
                }
                js.write(',').write(detail);

            } else {
                pred++;
            }

            js.writeln(");");
        }

        js.writeCall(null, "_addMessageObject");

        pred = 0;
        if (componentId != null) {
            js.writeString(componentId);

        } else if (isGlobal) {
            js.write("true");

        } else {
            js.writeNull();
        }

        js.write(',').write(key).writeln(");");

        return js;
    }
}
