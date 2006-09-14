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
 * Revision 1.6  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.5  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.4  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.3  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.2  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.1  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 */
package org.rcfaces.renderkit.html.internal;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.MessagesComponent;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class MessagesRenderer extends AbstractCssRenderer {
    public static final String REVISION = "$Revision$";

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer#encodeBegin(org.rcfaces.core.internal.renderkit.IWriter)
     */
    protected void encodeBegin(IComponentWriter writer) throws WriterException {
        IComponentRenderContext componentContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentContext.getFacesContext();

        MessagesComponent messagesComponent = (MessagesComponent) componentContext
                .getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement("TABLE");
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        boolean globalOnly = messagesComponent.isGlobalOnly();
        if (globalOnly) {
            htmlWriter.writeAttribute("v:globalOnly", "true");
        }

        if (messagesComponent.isShowSummary()) {
            htmlWriter.writeAttribute("v:showSummary", "true");
        }

        if (messagesComponent.isShowDetail()) {
            htmlWriter.writeAttribute("v:showDetail", "true");
        }

        String infoStyleClass = messagesComponent
                .getInfoStyleClass(facesContext);
        if (infoStyleClass != null) {
            htmlWriter.writeAttribute("v:infoStyleClass", infoStyleClass);
        }

        String warnStyleClass = messagesComponent
                .getWarnStyleClass(facesContext);
        if (warnStyleClass != null) {
            htmlWriter.writeAttribute("v:warnStyleClass", warnStyleClass);
        }

        String errorStyleClass = messagesComponent
                .getErrorStyleClass(facesContext);
        if (errorStyleClass != null) {
            htmlWriter.writeAttribute("v:errorStyleClass", errorStyleClass);
        }

        String fatalStyleClass = messagesComponent
                .getFatalStyleClass(facesContext);
        if (fatalStyleClass != null) {
            htmlWriter.writeAttribute("v:fatalStyleClass", fatalStyleClass);
        }

        htmlWriter.endElement("TABLE");

        htmlWriter.enableJavaScript();
    }

    protected void encodeJavaScript(IJavaScriptWriter js)
            throws WriterException {
        super.encodeJavaScript(js);

        FacesContext facesContext = js.getFacesContext();
        IComponentRenderContext componentRenderContext = js
                .getComponentRenderContext();
        MessagesComponent messagesComponent = (MessagesComponent) componentRenderContext
                .getComponent();

        String bundleVar = messagesComponent.getBundleVar(facesContext);

        boolean globalOnly = messagesComponent.isGlobalOnly();

        Iterator iterator = facesContext.getMessages(null);
        Set globals = null;
        if (globalOnly == false) {
            globals = new HashSet();
            for (; iterator.hasNext();) {
                globals.add(iterator.next());
            }

            iterator = facesContext.getMessages();
        }

        boolean messageGlobal = true;

        for (; iterator.hasNext();) {
            FacesMessage facesMessage = (FacesMessage) iterator.next();

            if (globals != null) {
                messageGlobal = globals.contains(facesMessage);
            }

            MessageRenderer.writeMessage(js, facesMessage, null, messageGlobal,
                    bundleVar);
        }
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.MESSAGES;
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

        MessagesComponent messagesComponent = (MessagesComponent) component;

        Boolean showDetail = componentData.getBooleanProperty("showDetail");
        if (showDetail != null) {
            messagesComponent.setShowDetail(showDetail.booleanValue());
        }

        Boolean showSummary = componentData.getBooleanProperty("showSummary");
        if (showSummary != null) {
            messagesComponent.setShowDetail(showSummary.booleanValue());
        }
        /*
         * String forValue=componentData.getProperty("for"); if (forValue!=null) {
         * messageComponent.setFor(forValue); }
         */

        super.decode(context, component, componentData);
    }
}
