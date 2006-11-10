/*
 * $Id$
 * 
 * $Log$
 * Revision 1.4  2006/11/10 14:00:05  oeuillot
 * *** empty log message ***
 *
 * Revision 1.3  2006/10/04 12:31:59  oeuillot
 * Stabilisation
 *
 * Revision 1.2  2006/09/20 17:55:20  oeuillot
 * Tri multiple des tables
 * Dialogue modale en JS
 *
 * Revision 1.1  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/09/05 08:57:21  oeuillot
 * Derni�res corrections pour la migration Rcfaces
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
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.7  2005/10/28 14:41:49  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.6  2005/10/05 14:34:18  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.5  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.util.Delay;
import org.rcfaces.core.internal.util.ServletTools;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ConfiguredHttpServlet extends ExtendedHttpServlet {

    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 8336388990583712944L;

    private static final Log LOG = LogFactory
            .getLog(ConfiguredHttpServlet.class);

    public static final String USE_GZIP_PARAMETER = Constants
            .getPackagePrefix()
            + ".GZIP_SUPPORT";

    private static final String EXPIRE_PARAMETER = Constants.getPackagePrefix()
            + ".EXPIRES";

    private static final String VERSIONED_EXPIRE_PARAMETER = Constants
            .getPackagePrefix()
            + ".VERSIONED_EXPIRES";

    private static final String ETAG_SUPPORT_PARAMETER = Constants
            .getPackagePrefix()
            + ".ETAG_SUPPORT";

    private static final String HASH_SUPPORT_PARAMETER = Constants
            .getPackagePrefix()
            + ".HASH_SUPPORT";

    private static final String FILTERED_LOCALES_PARAMETER = Constants
            .getPackagePrefix()
            + ".FILTERED_LOCALES";

    private static final String DEFAULT_LOCALE_PARAMETER = Constants
            .getPackagePrefix()
            + ".DEFAULT_LOCALE";

    private static final String LOCALE_SUPPORT_PARAMETER = Constants
            .getPackagePrefix()
            + ".LOCALE_SUPPORT";

    private static final String NONE_EXPIRATION_KEYWORD = "none";

    private boolean gZipSupport;

    private boolean etagSupport;

    private boolean hashSupport;

    private ExpirationDate expirationDate;

    private ExpirationDate versionedExpirationDate;

    private Set filtredLocales;

    protected boolean localeSupport;

    private final Map convertedLocales = new HashMap(32);

    private Locale defaultLocale;

    public void init(ServletConfig config) throws ServletException {

        super.init(config);

        String useGZIP = getParameter(USE_GZIP_PARAMETER);
        if ("true".equalsIgnoreCase(useGZIP)) {
            gZipSupport = true;

            LOG.info("Enable gzip support for sevlet '" + getServletName()
                    + "'");
        } else if ("false".equalsIgnoreCase(useGZIP)) {
            gZipSupport = false;

            LOG.info("Disable gzip support for sevlet '" + getServletName()
                    + "'");
        } else {
            gZipSupport = getDefaultGZipSupport();

            LOG.info("Use default gzip support configuration (value="
                    + this.gZipSupport + ") for sevlet '" + getServletName()
                    + "'");
        }

        String etagSupport = getParameter(ETAG_SUPPORT_PARAMETER);
        if ("true".equalsIgnoreCase(etagSupport)) {
            this.etagSupport = true;

            LOG.info("Enable etag support for sevlet '" + getServletName()
                    + "'");

        } else if ("false".equalsIgnoreCase(etagSupport)) {
            this.etagSupport = false;

            LOG.info("Disable etag support for sevlet '" + getServletName()
                    + "'");

        } else {
            this.etagSupport = getDefaultETagSupport();

            LOG.info("Use default ETag support configuration (value="
                    + this.etagSupport + ") for sevlet '" + getServletName()
                    + "'");
        }

        String hashSupport = getParameter(HASH_SUPPORT_PARAMETER);
        if ("true".equalsIgnoreCase(hashSupport)) {
            this.hashSupport = true;

            LOG.info("Enable hash support for sevlet '" + getServletName()
                    + "'");

        } else if ("false".equalsIgnoreCase(hashSupport)) {
            this.hashSupport = false;

            LOG.info("Disable hash support for sevlet '" + getServletName()
                    + "'");

        } else {
            this.hashSupport = getDefaultHashSupport();

            LOG.info("Use default Hash support configuration (value="
                    + this.hashSupport + ") for sevlet '" + getServletName()
                    + "'");
        }

        String expires = getParameter(EXPIRE_PARAMETER);
        if (expires != null) {
            expirationDate = ExpirationDate.parse(getServletName(),
                    EXPIRE_PARAMETER, expires);
        }

        if (expirationDate == null
                && NONE_EXPIRATION_KEYWORD.equalsIgnoreCase(expires) == false) {
            expirationDate = ExpirationDate
                    .fromDelay(getDefaultExpirationDelay());
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

        String versionedExpires = getParameter(VERSIONED_EXPIRE_PARAMETER);
        if (versionedExpires != null) {
            versionedExpirationDate = ExpirationDate.parse(getServletName(),
                    VERSIONED_EXPIRE_PARAMETER, versionedExpires);
        }

        if (versionedExpirationDate == null
                && NONE_EXPIRATION_KEYWORD.equalsIgnoreCase(versionedExpires) == false) {
            versionedExpirationDate = ExpirationDate
                    .fromDelay(getDefaultVersionedExpirationDelay());
        }

        if (LOG.isInfoEnabled() && versionedExpirationDate != null) {
            if (versionedExpirationDate.getExpiresDate() >= 0) {
                LOG.info("Versioned resources expire date detected: "
                        + versionedExpirationDate.getExpiresDate()
                        + "  for sevlet '" + getServletName() + "'");
            }

            if (versionedExpirationDate.getExpiresDelay() >= 0) {
                LOG.info("Versioned resources expire delay setted to "
                        + Delay.format(versionedExpirationDate
                                .getExpiresDelay()) + " for sevlet '"
                        + getServletName() + "'");
            }
        }

        if (hasLocaleSupport()) {
            String localeSupportProperty = getParameter(LOCALE_SUPPORT_PARAMETER);
            if ("false".equalsIgnoreCase(localeSupportProperty)) {
                localeSupport = false;

                LOG.info("LOCALE_SUPPORT is disabled for servlet '"
                        + getServletName() + "'.");
            } else if ("true".equalsIgnoreCase(localeSupportProperty)) {
                localeSupport = true;

                LOG.info("LOCALE_SUPPORT is enabled for servlet '"
                        + getServletName() + "'.");
            } else {
                localeSupport = getDefaultLocaleSupport();

                LOG.info("Use default value (" + localeSupport
                        + ") for LOCALE_SUPPORT for servlet '"
                        + getServletName() + "'.");
            }

            if (localeSupport) {
                String acceptedLocaleNames = getParameter(FILTERED_LOCALES_PARAMETER);
                if (acceptedLocaleNames != null
                        && acceptedLocaleNames.trim().length() > 0) {

                    StringTokenizer st = new StringTokenizer(
                            acceptedLocaleNames, ", ");
                    filtredLocales = new HashSet(st.countTokens());

                    for (; st.hasMoreTokens();) {
                        String localeName = st.nextToken();

                        Locale locale = convertLocaleName(localeName, false);
                        if (locale == null) {
                            LOG.error("Rejected locale '" + localeName + "'.");
                            continue;
                        }

                        filtredLocales.add(locale);
                    }

                    LOG.info("Accepted locale: " + filtredLocales
                            + " for servlet '" + getServletName() + "'.");
                }

                String localeName = getParameter(DEFAULT_LOCALE_PARAMETER);
                if (localeName != null) {
                    defaultLocale = convertLocaleName(localeName, true);

                    if (defaultLocale != null) {
                        LOG.info("DEFAULT_LOCALE specify default locale to '"
                                + defaultLocale + "'.");
                    } else {
                        LOG.info("DEFAULT_LOCALE value '" + localeName
                                + "' is not valid !");
                    }
                }
            }
        }
    }

    protected boolean hasLocaleSupport() {
        return true;
    }

    private long getDefaultVersionedExpirationDelay() {
        return Constants.DEFAULT_VERSIONED_EXPIRATION_DELAY;
    }

    protected boolean getDefaultLocaleSupport() {
        return Constants.DEFAULT_CLIENT_LOCALE_SUPPORT;
    }

    protected long getDefaultExpirationDelay() {
        return Constants.DEFAULT_EXPIRATION_DELAY;
    }

    protected boolean getDefaultHashSupport() {
        return Constants.HASH_SUPPORT;
    }

    protected boolean getDefaultETagSupport() {
        return Constants.ETAG_SUPPORT;
    }

    protected boolean getDefaultGZipSupport() {
        return Constants.DEFAULT_GZIP_SUPPORT;
    }

    protected ExpirationDate getDefaultExpirationDate(boolean versioned) {
        if (versioned && versionedExpirationDate != null) {
            return versionedExpirationDate;
        }

        return expirationDate;
    }

    protected boolean hasEtagSupport() {
        return etagSupport;
    }

    protected boolean hasHashSupport() {
        return hashSupport;
    }

    protected final boolean hasGZipSupport() {
        return gZipSupport;
    }

    protected final Locale getDefaultLocale(HttpServletRequest request,
            HttpServletResponse response) {
        synchronized (this) {
            if (defaultLocale == null) {
                defaultLocale = ServletTools.getDefaultLocale(
                        getServletContext(), request, response);
            }
        }

        return defaultLocale;
    }

    protected final Locale convertLocaleName(String localeName, boolean accept) {
        localeName = localeName.toLowerCase();

        Locale locale;
        synchronized (convertedLocales) {
            locale = (Locale) convertedLocales.get(localeName);
        }

        if (locale != null) {
            return locale;
        }

        // On synchronise pas le bloc, histore de pas bloquer le reste des
        // Threads ...
        // Et tanpis pour les put multiple de la meme valeur !

        StringTokenizer st = new StringTokenizer(localeName, "_");
        String language = st.nextToken().toLowerCase();
        String country = (st.hasMoreTokens()) ? st.nextToken().toLowerCase()
                : "";
        String variant = (st.hasMoreTokens()) ? st.nextToken().toLowerCase()
                : "";

        Locale bestLocale = null;
        int bestHit = 0;

        Locale locales[] = Locale.getAvailableLocales();
        for (int i = 0; i < locales.length; i++) {
            locale = locales[i];
            if (accept && filtredLocales != null
                    && filtredLocales.contains(locale) == false) {
                continue;
            }

            if (locale.getLanguage().equalsIgnoreCase(language) == false) {
                continue;
            }
            int hit = 1;

            String lcountry = locale.getCountry();
            if (lcountry.equalsIgnoreCase(country)) {
                hit += 2;

                String lvariant = locale.getVariant();
                if (lvariant.equalsIgnoreCase(variant)) {
                    hit += 2;

                } else if (lvariant.length() < 1) {
                    hit++;
                }

            } else if (lcountry.length() < 1) {
                hit++;
            }

            if (hit < bestHit) {
                continue;
            }

            bestLocale = locale;
            bestHit = hit;
        }

        if (bestLocale == null) {
            // On n'enregistre pas la mauvaise reponse !
            return null;
        }

        synchronized (convertedLocales) {
            convertedLocales.put(localeName, bestLocale);

            return bestLocale;
        }
    }
}
