/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.util;

import java.util.Iterator;
import java.util.StringTokenizer;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.capability.IBundleVarCapability;
import org.rcfaces.core.component.capability.IForCapability;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ContextTools;
import org.rcfaces.core.internal.tools.MessageTools;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class JavaScriptTools {
    private static final String REVISION = "$Revision$";

    public static void writeFirstMessage(IJavaScriptWriter js)
            throws WriterException {
        IHtmlRenderContext htmlRenderContext = js.getHtmlComponentRenderContext()
                .getHtmlRenderContext();
        IForCapability messageComponent = (IForCapability) htmlRenderContext
                .getComponent();

        FacesContext facesContext = js.getFacesContext();

        String forValues = messageComponent.getFor();
        if (forValues == null) {
            return;
        }

        FacesMessage facesMessage = null;
        String forValue = null;

        StringTokenizer st = new StringTokenizer(forValues, ", ");
        for (; st.hasMoreTokens();) {
            String forToken = st.nextToken();

            Iterator iterator = MessageTools.listMessages(facesContext,
                    forToken, (UIComponent) messageComponent);
            for (; iterator.hasNext();) {
                FacesMessage message = (FacesMessage) iterator.next();

                if (facesMessage == null
                        || facesMessage.getSeverity().compareTo(
                                message.getSeverity()) < 0) {

                    facesMessage = message;
                    forValue = forToken;

                }
            }
        }

        if (facesMessage == null) {
            return;
        }

        String bundleVar = null;
        if (messageComponent instanceof IBundleVarCapability) {
            bundleVar = ((IBundleVarCapability) messageComponent)
                    .getBundleVar();
        }

        writeMessage(js, facesMessage, forValue, false, bundleVar);
    }

    public static IJavaScriptWriter writeMessage(IJavaScriptWriter js,
            FacesMessage facesMessage, String componentId, boolean isGlobal,
            String bundleVar) throws WriterException {

        IHtmlRenderContext htmlRenderContext = (IHtmlRenderContext) js
                .getHtmlComponentRenderContext().getRenderContext();

        IJavaScriptRenderContext javascriptRenderContext = htmlRenderContext
                .getJavaScriptRenderContext();

        FacesContext facesContext = js.getFacesContext();

        String componentClientId = htmlRenderContext
                .computeBrotherComponentClientId(js.getWriter()
                        .getComponentRenderContext().getComponent(), componentId);

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

            js.write("var ").write(key).write('=').writeConstructor(
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

        js.writeMethodCall("f_registerMessageObject");

        pred = 0;
        if (componentClientId != null) {
            js.writeString(componentClientId);

        } else if (isGlobal) {
            js.write("true");

        } else {
            js.writeNull();
        }

        js.write(',').write(key).writeln(");");

        return js;
    }
}
