/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/09/01 15:24:28  oeuillot
 * Gestion des ICOs
 *
 */
package org.rcfaces.core.internal.util;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.util.WebXmlParser.ServletBean;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class ServletTools {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(ServletTools.class);

    public static String computeResourceURI(ServletContext servletContext,
            String defaultURI, Class servletClass) {

        WebXmlParser parser = new WebXmlParser(servletContext);

        ServletBean servlets[] = parser.getServletsByClassName(servletClass
                .getName());

        if (LOG.isDebugEnabled()) {
            LOG
                    .debug("Search pattern associated to servlet class '"
                            + servletClass + "' => " + servlets.length
                            + " response(s)");
        }

        if (servlets.length < 1) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Return default uri ('" + defaultURI
                        + "') for pattern of servlet '" + servletClass + "'.");
            }

            return defaultURI;
        }

        for (int i = 0; i < servlets.length; i++) {
            ServletBean servlet = servlets[i];

            String patterns[] = servlet.listUrlPatterns();
            if (patterns.length < 1) {
                continue;
            }

            for (int j = 0; j < patterns.length; j++) {
                String pattern = patterns[j];

                if (pattern.startsWith("/") == false
                        || pattern.endsWith("/*") == false) {

                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Refuse pattern '" + pattern + "'");
                    }
                    continue;
                }

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Accept pattern '" + pattern + "'");
                }

                return pattern.substring(0, pattern.length() - 2);
            }
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Return default uri ('" + defaultURI
                    + "') for pattern of servlet '" + servletClass + "'.");
        }

        return defaultURI;
    }

}
