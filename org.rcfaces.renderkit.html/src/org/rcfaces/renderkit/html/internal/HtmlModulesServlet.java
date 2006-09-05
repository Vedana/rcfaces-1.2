/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/09/05 08:57:14  oeuillot
 * Dernières corrections pour la migration Rcfaces
 *
 * Revision 1.2  2006/09/01 15:24:34  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2004/09/24 14:01:35  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.renderkit.html.internal;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.rcfaces.core.internal.webapp.ParametredHttpServlet;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class HtmlModulesServlet extends ParametredHttpServlet {
    private static final String REVISION = "$Revision$";

    private static final String MODULES_PARAMETER = Constants
            .getPackagePrefix()
            + ".MODULES";

    private Set modules;

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        modules = parseModules(config.getServletContext());
    }

    protected Set getModules() {
        return modules;
    }

    private static Set parseModules(ServletContext context) {
        Set modulesNames = null;

        String modulesNamesList = context.getInitParameter(MODULES_PARAMETER);
        if (modulesNamesList != null && modulesNamesList.trim().length() > 0) {
            StringTokenizer st = new StringTokenizer(modulesNamesList, ",;");

            modulesNames = new HashSet(st.countTokens());
            for (; st.hasMoreTokens();) {
                String moduleName = st.nextToken();

                if (moduleName.equals("*")) {
                    modulesNames = null;
                    break;
                }

                modulesNames.add(moduleName);
            }
        }

        return modulesNames;
    }
}
