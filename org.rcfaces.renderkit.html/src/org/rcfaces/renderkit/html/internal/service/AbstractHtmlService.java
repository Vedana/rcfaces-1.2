/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.faces.context.FacesContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.service.AbstractService;
import org.rcfaces.core.internal.webapp.ConfiguredHttpServlet;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.codec.JavascriptCodec;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractHtmlService extends AbstractService {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(AbstractHtmlService.class);

    protected static final String RESPONSE_CHARSET = "UTF-8";

    private transient boolean useGzip;

    static void sendJsError(FacesContext facesContext, String message) {

        if (LOG.isErrorEnabled()) {
            LOG.error("Send javascript error : " + message);
        }

        ServletResponse response = (ServletResponse) facesContext
                .getExternalContext().getResponse();

        setNoCache(response);
        response.setContentType(IHtmlRenderContext.JAVASCRIPT_TYPE
                + "; charset=" + RESPONSE_CHARSET);

        String jm = JavascriptCodec.encodeJavaScript(message, '\"');
        try {
            PrintWriter pw = response.getWriter();

            pw.write("alert(\"" + jm + "\");");

        } catch (IOException ex) {
            LOG.error("Can not write error message '" + message + "'.", ex);
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

}
