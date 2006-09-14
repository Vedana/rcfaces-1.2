/*
 * $Id$
 * 
 * $Log$
 * Revision 1.4  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.3  2006/09/05 08:57:13  oeuillot
 * Derni�res corrections pour la migration Rcfaces
 *
 * Revision 1.2  2006/09/01 15:24:34  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.4  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.3  2006/01/31 16:04:24  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.2  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.1  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
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
