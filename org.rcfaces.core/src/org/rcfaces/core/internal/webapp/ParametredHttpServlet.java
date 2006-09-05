/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/09/05 08:57:21  oeuillot
 * Dernières corrections pour la migration Rcfaces
 *
 * Revision 1.1  2006/09/01 15:24:29  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.13  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.12  2006/06/19 17:22:17  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.11  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.10  2006/02/06 16:47:04  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.9  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.8  2005/11/08 12:16:26  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cotï¿½ client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.7  2005/10/28 14:41:49  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.6  2005/10/05 14:34:18  oeuillot
 * Version avec decode/validation/update des propriï¿½tï¿½s des composants
 *
 * Revision 1.5  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalitï¿½s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.4  2005/03/07 10:47:02  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.3  2004/09/29 20:49:38  oeuillot
 * *** empty log message ***
 *
 * Revision 1.2  2004/08/20 16:00:54  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/12 14:21:06  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.internal.webapp;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.util.Delay;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class ParametredHttpServlet extends ExtendedHttpServlet {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(ParametredHttpServlet.class);

    private static final String DEFAULT_CHARSET = "8859_1";

    public static final String USE_GZIP_PARAMETER = Constants
            .getPackagePrefix()
            + ".GZIP_SUPPORT";

    private static final String EXPIRE_PARAMETER = Constants.getPackagePrefix()
            + ".EXPIRES";

    private static final String CHARSET_PARAMETER = Constants
            .getPackagePrefix()
            + ".CHARSET";

    private static final String ETAG_SUPPORT_PARAMETER = Constants
            .getPackagePrefix()
            + ".ETAG_SUPPORT";

    private static final String HASH_SUPPORT_PARAMETER = Constants
            .getPackagePrefix()
            + ".HASH_SUPPORT";

    private static final long DEFAULT_EXPIRATION_DELAY = Delay.WEEK;

    private boolean hasGZipSupport = false;

    private String charset;

    private boolean etagSupport;

    private boolean hashSupport;

    private ExpirationDate expirationDate;

    public void init(ServletConfig config) throws ServletException {

        super.init(config);

        String useGZIP = getParameter(USE_GZIP_PARAMETER);
        if ("true".equalsIgnoreCase(useGZIP)) {
            hasGZipSupport = true;

            LOG
                    .info("Enable zip support for sevlet '" + getServletName()
                            + "'");
        }

        String etagSupport = getParameter(ETAG_SUPPORT_PARAMETER);
        if ("false".equalsIgnoreCase(etagSupport) == false) {
            this.etagSupport = true;

            LOG.info("Enable etag support for sevlet '" + getServletName()
                    + "'");

        }

        String hashSupport = getParameter(HASH_SUPPORT_PARAMETER);
        if ("false".equalsIgnoreCase(hashSupport) == false) {
            this.hashSupport = true;

            LOG.info("Enable hash support for sevlet '" + getServletName()
                    + "'");

        }

        String expires = getParameter(EXPIRE_PARAMETER);
        if (expires != null) {
            expirationDate = ExpirationDate.parse(getServletName(),
                    EXPIRE_PARAMETER, expires);
        }

        if (expirationDate == null) {
            expirationDate = ExpirationDate.fromDelay(DEFAULT_EXPIRATION_DELAY);
        }

        if (LOG.isInfoEnabled() && expirationDate != null) {
            if (expirationDate.getExpiresDate() >= 0) {
                LOG.info("Expire date setted to "
                        + expirationDate.getExpiresDate() + "  for sevlet '"
                        + getServletName() + "'.");
            }

            if (expirationDate.getExpiresDelay() >= 0) {
                LOG.info("Expire delay setted to "
                        + Delay.format(expirationDate.getExpiresDelay())
                        + " for sevlet '" + getServletName() + "'.");

            }
        }

        charset = getParameter(CHARSET_PARAMETER);
        if (charset == null) {
            charset = getDefaultCharset();
            LOG.info("Charset setted to DEFAULT value : \"" + charset
                    + "\"  for sevlet '" + getServletName() + "'");
        } else {
            LOG.info("Charset setted to \"" + charset + "\"  for sevlet '"
                    + getServletName() + "'.");
        }
    }

    protected ExpirationDate getDefaultExpirationDate() {
        return expirationDate;
    }

    protected String getDefaultCharset() {
        return DEFAULT_CHARSET;
    }

    protected boolean hasEtagSupport() {
        return etagSupport;
    }

    protected boolean hasHashSupport() {
        return hashSupport;
    }

    protected final String getCharset() {
        return charset;
    }

    protected final boolean hasGZipSupport() {
        return hasGZipSupport;
    }
}
