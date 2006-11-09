/*
 * $Id$
 */
package org.rcfaces.core.internal;

import java.util.StringTokenizer;

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

    public static final boolean RESOURCE_CONTENT_VERSION_SUPPORT = true;

    public static final boolean CACHED_COMPONENT_ITERATOR = true;

    public static final boolean STATED_COMPONENT_CHILDREN_LIST = true;

    public static final boolean ENCODE_URI = false;

    public static final boolean DEFAULT_HASH_SUPPORT = true;

    public static final boolean DEFAULT_ETAG_SUPPORT = true;

    public static final boolean DEFAULT_GZIP_SUPPORT = true;

    public static final long DEFAULT_EXPIRATION_DELAY = Delay.WEEK;

    public static final long DEFAULT_VERSIONED_EXPIRATION_DELAY = Delay.YEAR;

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
        Package _package = Constants.class.getPackage();

        String name = _package.getName();
        if (name.endsWith(".internal")) {
            name = name.substring(0, name.lastIndexOf('.'));
        }

        CONSTANT_PREFIX = name;
    }

    private static final IFactory defaultFactory;

    public static final IImageLoaderFactory IMAGE_LOADER_FACTORY = null;

    public static final boolean CACHED_LOCALE_FORMAT = true;

    public static final boolean COMPACTED_PROPERTY_NAME = true;

    static {
        LOG.info("READ_ONLY_COLLECTION_LOCK_ENABLED="
                + READ_ONLY_COLLECTION_LOCK_ENABLED);

        LOG.info("TEMPLATE_SUPPORT=" + TEMPLATE_SUPPORT);

        LOG.info("RESOURCE_CONTENT_VERSION_SUPPORT="
                + RESOURCE_CONTENT_VERSION_SUPPORT);

        LOG.info("STATED_COMPONENT_CHILDREN_LIST="
                + STATED_COMPONENT_CHILDREN_LIST);

        LOG.info("CACHED_COMPONENT_ITERATOR=" + CACHED_COMPONENT_ITERATOR);

        LOG.info("CACHED_LOCALE_FORMAT=" + CACHED_LOCALE_FORMAT);

        LOG.info("COMPACTED_PROPERTY_NAME=" + COMPACTED_PROPERTY_NAME);

        LOG.info("DEFAULT_ETAG_SUPPORT=" + DEFAULT_ETAG_SUPPORT);

        LOG.info("DEFAULT_GZIP_SUPPORT=" + DEFAULT_GZIP_SUPPORT);

        LOG.info("DEFAULT_HASH_SUPPORT=" + DEFAULT_HASH_SUPPORT);

        LOG.info("DEFAULT_EXPIRATION_DELAY=" + DEFAULT_EXPIRATION_DELAY + " ("
                + Delay.format(DEFAULT_EXPIRATION_DELAY) + ")");

        LOG.info("DEFAULT_VERSIONED_EXPIRATION_DELAY="
                + DEFAULT_VERSIONED_EXPIRATION_DELAY + " ("
                + Delay.format(DEFAULT_VERSIONED_EXPIRATION_DELAY) + ")");

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

        Package clazzPackage = clazz.getPackage();
        if (clazzPackage != null) {
            try {
                String version = clazzPackage.getImplementationVersion();
                if (version != null) {
                    LOG.info(versionName + " version: " + version);

                    return version;
                }

            } catch (Throwable th) {
                LOG.error("Can not get " + versionName
                        + " version by package API ! ("
                        + clazzPackage.getName() + ")", th);
            }
        }

        String version = "0." + String.valueOf(System.currentTimeMillis());
        LOG.error("Version of " + versionName
                + " can not be determined ! (Use the fake version: " + version
                + ")");

        return version;
    }

    public static final String getBuildId(String version) {
        StringTokenizer st = new StringTokenizer(version, ".");
        if (st.countTokens() < 4) {
            return version;
        }

        st.nextToken();
        st.nextToken();
        st.nextToken();

        return st.nextToken();
    }
}
