/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.service.AbstractService;
import org.rcfaces.core.internal.webapp.ConfiguredHttpServlet;
import org.rcfaces.core.lang.ApplicationException;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.util.JavaScriptResponseWriter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractHtmlService extends AbstractService {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(AbstractHtmlService.class);

    public static final int SERVICE_ERROR = 0x2000000;

    public static final int INVALID_PARAMETER_SERVICE_ERROR = 0x2000001;

    public static final int SESSION_EXPIRED_SERVICE_ERROR = 0x2000002;

    public static final int INTERNAL_SERVICE_RESPONSE_ERROR = 0x2000007;

    protected static final String RESPONSE_CHARSET = "UTF-8";

    private static final String CAMELIA_RESPONSE_HEADER = "X-Camelia-Service";

    private transient boolean useGzip;

    static void sendJsError(FacesContext facesContext, ApplicationException ex,
            String componentId) {

        sendJsError(facesContext, componentId, ex.getErrorCode(), ex
                .getMessage(), ex.getErrorMessage());
    }

    static void sendJsError(FacesContext facesContext,
            String componentClientId, int messageCode, String message,
            String messageDetail) {

        if (LOG.isErrorEnabled()) {
            LOG.error("Send javascript error : code=" + messageCode
                    + " message='" + message + "' messageDetail='"
                    + messageDetail + "'.");
        }

        ServletResponse response = (ServletResponse) facesContext
                .getExternalContext().getResponse();

        setNoCache(response);
        response.setContentType(IHtmlRenderContext.JAVASCRIPT_TYPE
                + "; charset=" + RESPONSE_CHARSET);

        try {
            PrintWriter printWriter = response.getWriter();

            IJavaScriptWriter jsWriter = new JavaScriptResponseWriter(
                    facesContext, printWriter, RESPONSE_CHARSET, null, null);

            jsWriter.writeCall("f_core", "PerformErrorEvent").writeString(
                    componentClientId);

            int pred = 0;

            if (componentClientId != null) {
                for (; pred > 0; pred--) {
                    jsWriter.write(',').writeNull();
                }

                jsWriter.write(',').writeString(componentClientId);

            } else {
                pred++;
            }

            if (messageCode != 0) {
                for (; pred > 0; pred--) {
                    jsWriter.write(',').writeNull();
                }

                jsWriter.write(',').writeInt(messageCode);

            } else {
                pred++;
            }

            if (message != null) {
                for (; pred > 0; pred--) {
                    jsWriter.write(',').writeNull();
                }
                jsWriter.write(',').writeString(message);

            } else {
                pred++;
            }

            if (messageDetail != null) {
                for (; pred > 0; pred--) {
                    jsWriter.write(',').writeNull();
                }

                jsWriter.write(',').writeString(messageDetail);

            } else {
                pred++;
            }

            jsWriter.write(");");

        } catch (IOException ex) {
            throw new FacesException("Can not write cancel response.", ex);
        }

        facesContext.responseComplete();
    }

    public void initialize(FacesContext facesContext) {
        useGzip = "true".equalsIgnoreCase(facesContext.getExternalContext()
                .getInitParameter(ConfiguredHttpServlet.USE_GZIP_PARAMETER));
    }

    protected final boolean canUseGzip(FacesContext facesContext) {
        if (useGzip == false) {
            return false;
        }

        if (facesContext == null) {
            return true;
        }

        // On verifie que le browser le supporte
        return ConfiguredHttpServlet.hasGzipSupport(facesContext);
    }

    static final void setNoCache(ServletResponse response) {
        if ((response instanceof HttpServletResponse) == false) {
            LOG.error("Can not set 'noCache' for this response type ! ("
                    + response.getClass() + ")");
            return;
        }

        ConfiguredHttpServlet.setNoCache((HttpServletResponse) response);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Set no cache for response.");
        }
    }

    static final void setCameliaResponse(ServletResponse response,
            String version) {
        if ((response instanceof HttpServletResponse) == false) {
            LOG.error("Can not set 'noCache' for this response type ! ("
                    + response.getClass() + ")");
            return;
        }

        ((HttpServletResponse) response).setHeader(CAMELIA_RESPONSE_HEADER,
                version);
    }

}
