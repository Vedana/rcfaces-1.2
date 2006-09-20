/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/09/20 17:55:19  oeuillot
 * Tri multiple des tables
 * Dialogue modale en JS
 *
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/09/05 08:57:21  oeuillot
 * Derni�res corrections pour la migration Rcfaces
 *
 */
package org.rcfaces.core.internal.rewriting;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.util.ServletTools;
import org.rcfaces.core.internal.webapp.ConfiguredHttpServlet;
import org.rcfaces.core.internal.webapp.ExpirationDate;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ApplicationVersionServlet extends ConfiguredHttpServlet {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -4209462021160100620L;

    private static final Log LOG = LogFactory
            .getLog(ApplicationVersionServlet.class);

    private static final String DEFAULT_APPLICATION_VERSION_URL = "/ap-v";

    private static final String APPLICATION_VERSION_URL_PROPERTY = "org.rcfaces.core.internal.rewriting.APPLICATION_VERSION_URL_PROPERTY";

    public void init(ServletConfig config) throws ServletException {

        super.init(config);

        String applicationVersionURL = ServletTools.computeResourceURI(
                getServletContext(), DEFAULT_APPLICATION_VERSION_URL,
                getClass());
        if (applicationVersionURL == null) {
            return;
        }
        LOG.info("Base of application version url is '" + applicationVersionURL
                + "'.");

        getServletContext().setAttribute(APPLICATION_VERSION_URL_PROPERTY,
                applicationVersionURL);
    }

    static String getApplicationVersionURI(Map applicationMap) {
        return (String) applicationMap.get(APPLICATION_VERSION_URL_PROPERTY);
    }

    protected void service(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String url = request.getRequestURI();

        String contextPath = request.getContextPath();
        if (contextPath != null) {
            url = url.substring(contextPath.length());
        }

        String servletPath = request.getServletPath();
        if (servletPath != null) {
            url = url.substring(servletPath.length());
        }

        // Retire le nom de notre servlet
        int idx = url.indexOf('/');
        if (idx < 0) {
            throw new ServletException("Can not understand URI '"
                    + request.getRequestURI() + "'.");
        }

        url = url.substring(idx + 1);

        idx = url.indexOf('/');
        if (idx < 0) {
            throw new ServletException("Can not understand URI '"
                    + request.getRequestURI() + "'.");
        }
        String version = url.substring(0, idx);
        url = url.substring(idx);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Forward url='" + url + "' version='" + version
                    + "'  requested url='" + request.getRequestURI() + "'.");
        }

        RequestDispatcher requestDispatcher = getServletContext()
                .getRequestDispatcher(url);
        if (requestDispatcher == null) {
            LOG.error("Can not get request dispatcher for url '" + url + "'.");

            throw new ServletException(
                    "Can not get request dispatcher for url '" + url + "'.");
        }

        ExpirationDate expirationDate = getDefaultExpirationDate(true);
        if (expirationDate != null) {
            expirationDate.sendExpires(response);
        }

        requestDispatcher.forward(request, response);
    }

}
