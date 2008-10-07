/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.css;

import java.net.URL;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.repository.SourceRepository;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class StyleSheetRepository extends SourceRepository {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(StyleSheetRepository.class);

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
        String all_sources = config.getInitParameter(CSS_REPOSITORY_PARAMETER);

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
            error("Can not get css resource '" + filename
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
