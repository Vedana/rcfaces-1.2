/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.2  2006/09/01 15:24:34  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.6  2006/02/06 16:47:05  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.5  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.4  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.3  2004/12/30 17:24:20  oeuillot
 * Gestion des validateurs
 * Debuggage des composants
 *
 * Revision 1.2  2004/09/24 14:01:36  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/30 12:52:48  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/20 16:00:54  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.renderkit.html.internal.css;

import java.net.URL;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.rcfaces.core.internal.webapp.SourceRepository;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class StyleSheetRepository extends SourceRepository {
    private static final String REVISION = "$Revision$";

    private static final String CSS_PACKAGE = StyleSheetRepository.class
            .getPackage().getName().replace('.', '/') + '/';

    private static final String CSS_REPOSITORY_DEFAULT_URI = CSS_PACKAGE
            + StylesheetsServlet.CAMELIA_CSS_URL;

    private static final String CSS_REPOSITORY_PARAMETER = "repository.uri";

    public StyleSheetRepository(ServletConfig config, Set modules,
            String charSet, boolean canUseGzip, boolean canUseETag,
            boolean canUseHash, String repositoryVersion)
            throws ServletException {
        super(config, getAllResourceURI(config), modules, charSet, canUseGzip,
                canUseETag, canUseHash, EXTERNAL_REPOSITORIES_CONFIG_NAME,
                repositoryVersion);
    }

    static String getAllResourceURI(ServletConfig config)
            throws ServletException {
        String all_sources = config
                .getInitParameter(CSS_REPOSITORY_PARAMETER);

        if (all_sources == null) {
            all_sources = CSS_REPOSITORY_DEFAULT_URI;
        }

        if (all_sources == null) {
            throw new ServletException("You must define '"
                    + CSS_REPOSITORY_PARAMETER
                    + "' to define location of camelia css !");
        }

        return all_sources;
    }

    protected URL[] getSourceURI(String line, Set modules) {
        if (line.startsWith("@import") == false) {
            return null;
        }

        int p1 = line.indexOf('(');
        int p2 = line.indexOf(')', p1 + 1);

        if (p1 < 0 || p2 < 0) {
            return null;
        }

        String filename = line.substring(p1 + 1, p2).trim();
        String moduleName = null;

        int p3 = line.indexOf("//module:", p2 + 1);
        int p4 = line.length();

        if (p3 > p2 && p4 > p3) {
            moduleName = line.substring(p3 + 9, p4).trim();

            if (modules != null) {
                if (modules.contains(moduleName) == false) {
                    return null;
                }
            }
        }

        if (filename.startsWith("css/")) {
            filename = filename.substring(4);
        }
        filename = CSS_PACKAGE + filename;

        URL url = getURL(filename);

        if (url == null) {
            error(
                    "Can not get css resource '" + filename
                            + "'. Skip this file !", null);
            return null;
        }

        return new URL[] { url };
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.webapp.SourceRepository#canSkipSpace()
     */
    protected boolean canSkipSpace() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.webapp.SourceRepository#getSourceType()
     */
    protected String getSourceType() {
        return "StyleSheets";
    }
}