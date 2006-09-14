/*
 * $Id$
 * 
 * $Log$
 * Revision 1.4  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.3  2006/09/05 08:57:21  oeuillot
 * Derni�res corrections pour la migration Rcfaces
 *
 * Revision 1.2  2006/09/01 15:24:29  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/08/28 16:03:55  oeuillot
 * Version avant migation en org.rcfaces
 *
 */
package org.rcfaces.core.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.component.ComponentsFactory;
import org.rcfaces.core.internal.component.IFactory;
import org.rcfaces.core.internal.images.IImageLoaderFactory;
import org.rcfaces.core.internal.util.Delay;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class Constants {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(Constants.class);

    public static final boolean READ_ONLY_COLLECTION_LOCK_ENABLED = true;

    public static final boolean TEMPLATE_SUPPORT = true;

    public static final boolean URL_REWRITING_SUPPORT = true;

    public static final boolean CACHED_ITERATOR_ENABLED = true;

    public static final boolean STATE_CHILDREN_LIST_ENABLED = true;

    public static final boolean ENCODE_URI = false;

    public static final boolean DEFAULT_HASH_SUPPORT = true;

    public static final boolean DEFAULT_ETAG_SUPPORT = true;

    public static final boolean DEFAULT_GZIP_SUPPORT = true;

    public static final long DEFAULT_EXPIRATION_DELAY = Delay.WEEK;

    public static final int VERSIONED_URI_HASHCODE_MAX_SIZE = 16;

    private static final String RCFACES_VERSION_SYSTEM_PARAMETER = "rcfaces.core.version";

    public static final String SAVE_STATE_FIELD_MARKER_SYSTEM_PARAMETER = "rcfaces.core.stateFieldMarker";

    public static final String SAVE_STATE_FIELD_MARKER_PARAMETER = getPackagePrefix()
            + ".SAVE_STATE_FIELD_MARKER";

    public static final boolean DEFAULT_CLIENT_LOCALE_SUPPORT = true;

    public static final String RESOURCE_VERSION_DIGEST_ALGORITHM = "SHA-1";

    private static final String version;
    static {
        version = searchVersion(Constants.class,
                RCFACES_VERSION_SYSTEM_PARAMETER, "RCFaces Core");

    }

    private static final String CONSTANT_PREFIX;
    static {
        String name = Constants.class.getPackage().getName();
        if (name.endsWith(".internal")) {
            name = name.substring(0, name.lastIndexOf('.'));
        }

        CONSTANT_PREFIX = name;
    }

    private static final IFactory defaultFactory;

    public static final IImageLoaderFactory IMAGE_LOADER_FACTORY = null;

    static {
        LOG.info("READ_ONLY_COLLECTION_LOCK_ENABLED="
                + READ_ONLY_COLLECTION_LOCK_ENABLED);

        LOG.info("TEMPLATE_SUPPORT=" + TEMPLATE_SUPPORT);

        LOG.info("URL_REWRITING_SUPPORT=" + URL_REWRITING_SUPPORT);

        LOG.info("STATE_CHILDREN_LIST_ENABLED=" + STATE_CHILDREN_LIST_ENABLED);

        LOG.info("CACHED_ITERATOR_ENABLED=" + CACHED_ITERATOR_ENABLED);

        LOG.info("DEFAULT_ETAG_SUPPORT=" + DEFAULT_ETAG_SUPPORT);

        LOG.info("DEFAULT_GZIP_SUPPORT=" + DEFAULT_GZIP_SUPPORT);

        LOG.info("DEFAULT_HASH_SUPPORT=" + DEFAULT_HASH_SUPPORT);

        LOG.info("DEFAULT_EXPIRATION_DELAY=" + DEFAULT_EXPIRATION_DELAY + " ("
                + Delay.format(DEFAULT_EXPIRATION_DELAY) + ")");

        LOG.info("DEFAULT_CLIENT_LOCALE_SUPPORT="
                + DEFAULT_CLIENT_LOCALE_SUPPORT);

        LOG.info("VERSIONED_URI_HASHCODE_MAX_SIZE="
                + VERSIONED_URI_HASHCODE_MAX_SIZE);

        defaultFactory = ComponentsFactory.getCameliaFactory(null);
        LOG.info("Default components factory: " + defaultFactory.getName());
    }

    public static final String getPackagePrefix() {
        return CONSTANT_PREFIX;
    }

    public static IFactory getCameliaFactory() {
        return defaultFactory;
    }

    public static String getVersion() {
        return version;
    }

    public static final String searchVersion(Class clazz,
            String systemParameter, String versionName) {
        try {
            String version = System.getProperty(systemParameter);
            if (version != null) {
                if ("now".equalsIgnoreCase(version)) {
                    version = "0." + String.valueOf(System.currentTimeMillis());

                    try {
                        System.setProperty(systemParameter, version);

                    } catch (Throwable th2) {
                        LOG.debug(th2);
                    }

                    LOG.info(versionName
                            + " version: DEVELOPMENT MODE (Use fake version: "
                            + version + ")");

                } else {
                    LOG.info(versionName + " version setted by property: "
                            + version);
                }

                return version;
            }

        } catch (Throwable th) {
            // Un probleme de sécurité peut-etre !
            LOG.debug(th);
        }

        try {
            String version = clazz.getPackage().getImplementationVersion();
            if (version != null) {
                LOG.info(versionName + " version: " + version);

                return version;
            }

        } catch (Throwable th) {
            LOG.error("Can not get " + versionName
                    + " version by package API ! ("
                    + clazz.getPackage().getName() + ")", th);
        }

        String version = "0." + String.valueOf(System.currentTimeMillis());
        LOG.error("Version of " + versionName
                + " can not be determined ! (Use the fake version: " + version
                + ")");

        return version;
    }
}
