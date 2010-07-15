/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.MessagesComponent;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.IAccessibilityRoles;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class MessagesRenderer extends AbstractCssRenderer {
    public static final String REVISION = "$Revision$";

    protected void encodeBegin(IComponentWriter writer) throws WriterException {
        super.encodeBegin(writer);

        IComponentRenderContext componentContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentContext.getFacesContext();

        MessagesComponent messagesComponent = (MessagesComponent) componentContext
                .getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement(IHtmlWriter.TABLE);
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        boolean globalOnly = messagesComponent.isGlobalOnly();
        if (globalOnly) {
            htmlWriter.writeAttribute("v:globalOnly", true);
        }

        if (messagesComponent.isShowSummary()) {
            htmlWriter.writeAttribute("v:showSummary", true);
        }

        if (messagesComponent.isShowDetail()) {
            htmlWriter.writeAttribute("v:showDetail", true);
        }

        if (messagesComponent.isShowActiveComponentMessage(facesContext)) {
            htmlWriter.writeAttribute("v:showActiveComponentMessage", true);
        }

        int maxCount = messagesComponent.getMaxCount(facesContext);
        if (maxCount > 0) {
            htmlWriter.writeAttribute("v:maxCount", maxCount);
        }

        htmlWriter.endElement(IHtmlWriter.TABLE);

        htmlWriter.getJavaScriptEnableMode().enableOnMessage();
    }
    
    protected String getWAIRole() {
    	return IAccessibilityRoles.ALERT;
    }

    /*
     * protected void encodeJavaScript(IJavaScriptWriter js) throws
     * WriterException { super.encodeJavaScript(js);
     * 
     * FacesContext facesContext = js.getFacesContext(); IComponentRenderContext
     * componentRenderContext = js .getHtmlComponentRenderContext();
     * MessagesComponent messagesComponent = (MessagesComponent)
     * componentRenderContext .getComponent();
     * 
     * String bundleVar = messagesComponent.getBundleVar(facesContext);
     * 
     * boolean globalOnly = messagesComponent.isGlobalOnly();
     * 
     * Iterator iterator = facesContext.getMessages(null); Set globals = null;
     * if (globalOnly == false) { globals = new HashSet(); for (;
     * iterator.hasNext();) { globals.add(iterator.next()); }
     * 
     * iterator = facesContext.getMessages(); }
     * 
     * boolean messageGlobal = true;
     * 
     * for (; iterator.hasNext();) { FacesMessage facesMessage = (FacesMessage)
     * iterator.next();
     * 
     * if (globals != null) { messageGlobal = globals.contains(facesMessage); }
     * 
     * JavaScriptTools.writeMessage(js, facesMessage, null, messageGlobal,
     * bundleVar); } }
     */

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.MESSAGES;
    }

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
         * String forValue=componentData.getProperty("for"); if (forValue!=null)
         * { messageComponent.setFor(forValue); }
         */

        super.decode(context, component, componentData);
    }
}
