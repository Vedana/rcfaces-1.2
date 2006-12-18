/*
 * $Id$
 */
package org.rcfaces.core.internal;

import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IAsyncRenderModeCapability;
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

    private static final String CONSTANT_PREFIX;
    static {
        Package _package = Constants.class.getPackage();

        String name = _package.getName();
        if (name.endsWith(".internal")) {
            name = name.substring(0, name.lastIndexOf('.'));
        }

        CONSTANT_PREFIX = name;
    }

    public static final boolean READ_ONLY_COLLECTION_LOCK_ENABLED = true;

    public static final boolean TEMPLATE_ENGINE_SUPPORT = true;

    public static final boolean RESOURCE_CONTENT_VERSION_SUPPORT = true;

    public static final boolean CACHED_COMPONENT_ITERATOR = true;

    public static final boolean STATED_COMPONENT_CHILDREN_LIST = true;

    public static final boolean ENCODE_URI = false;

    public static final boolean HASH_SUPPORT = true;

    public static final boolean ETAG_SUPPORT = true;

    public static final boolean GZIP_SUPPORT_DEFAULT_VALUE = true;

    /**
     * Collection can be converted to DataModel (JSF spec extension)
     */
    public static final boolean COLLECTION_DATAMODEL_SUPPORT = true;

    public static final long DEFAULT_EXPIRATION_DELAY = Delay.WEEK;

    public static final long DEFAULT_VERSIONED_EXPIRATION_DELAY = Delay.YEAR;

    public static final int VERSIONED_URI_HASHCODE_MAX_SIZE = 16;

    private static final String RCFACES_VERSION_SYSTEM_PARAMETER = "rcfaces.core.version";

    public static final String SAVE_STATE_FIELD_MARKER_SYSTEM_PARAMETER = "rcfaces.core.stateFieldMarker";

    public static final String SAVE_STATE_FIELD_MARKER_PARAMETER = getPackagePrefix()
            + ".SAVE_STATE_FIELD_MARKER";

    public static final boolean CLIENT_LOCALE_SUPPORT_DEFAULT_VALUE = true;

    public static final String RESOURCE_VERSION_DIGEST_ALGORITHM = "SHA-1";

    public static final String ETAG_DIGEST_ALGORITHM = "SHA-1";

    public static final String HASH_DIGEST_ALGORITHM = "MD5";

    public static final boolean BASIC_CONTENT_WEAK_CACHE_ENABLED = true;

    public static final int BASIC_CONTENT_CACHE_SIZE = 256;

    private static final String version;
    static {
        version = searchVersion(Constants.class,
                RCFACES_VERSION_SYSTEM_PARAMETER, "RCFaces Core");
    }

    private static final IFactory defaultFactory;

    public static final IImageLoaderFactory IMAGE_LOADER_FACTORY = null;

    public static final boolean CACHED_LOCALE_FORMATS = true;

    public static final boolean COMPACTED_PROPERTY_NAME = true;

    public static final boolean LOCKED_CLIENT_ATTRIBUTES_DEFAULT_VALUE = false;

    private static final String FACELET_CLASSNAME = "com.sun.facelets.Facelet";

    public static final int DEFAULT_ASYNC_MODE = IAsyncRenderModeCapability.NONE_ASYNC_RENDER_MODE;

    public static final int ENABLE_ASYNC_MODE_VALUE = IAsyncRenderModeCapability.BUFFER_ASYNC_RENDER_MODE;

    public static final boolean FACELETS_SUPPORT = true;

    public static final boolean FLUSH_AFTER_ENCODE_CHILDREN = false;

    public static final boolean FLUSH_AFTER_ENCODE_END = false;

    public static final boolean ADAPT_SELECT_ITEMS = true;

    static {
        LOG.info("READ_ONLY_COLLECTION_LOCK_ENABLED="
                + READ_ONLY_COLLECTION_LOCK_ENABLED);

        LOG.info("LOCKED_CLIENT_ATTRIBUTES_DEFAULT_VALUE="
                + LOCKED_CLIENT_ATTRIBUTES_DEFAULT_VALUE);

        LOG.info("TEMPLATE_ENGINE_SUPPORT=" + TEMPLATE_ENGINE_SUPPORT);

        LOG.info("RESOURCE_CONTENT_VERSION_SUPPORT="
                + RESOURCE_CONTENT_VERSION_SUPPORT);

        LOG.info("STATED_COMPONENT_CHILDREN_LIST="
                + STATED_COMPONENT_CHILDREN_LIST);

        LOG.info("CACHED_COMPONENT_ITERATOR=" + CACHED_COMPONENT_ITERATOR);

        LOG.info("CACHED_LOCALE_FORMATS=" + CACHED_LOCALE_FORMATS);

        LOG.info("COMPACTED_PROPERTY_NAME=" + COMPACTED_PROPERTY_NAME);

        LOG.info("ETAG_SUPPORT=" + ETAG_SUPPORT + " (algorithm: "
                + ETAG_DIGEST_ALGORITHM + ")");

        LOG.info("HASH_SUPPORT=" + HASH_SUPPORT + " (algorithm: "
                + HASH_DIGEST_ALGORITHM + ")");

        LOG.info("GZIP_SUPPORT_DEFAULT_VALUE=" + GZIP_SUPPORT_DEFAULT_VALUE);

        LOG.info("DEFAULT_EXPIRATION_DELAY=" + DEFAULT_EXPIRATION_DELAY + " ("
                + Delay.format(DEFAULT_EXPIRATION_DELAY) + ")");

        LOG.info("DEFAULT_VERSIONED_EXPIRATION_DELAY="
                + DEFAULT_VERSIONED_EXPIRATION_DELAY + " ("
                + Delay.format(DEFAULT_VERSIONED_EXPIRATION_DELAY) + ")");

        LOG.info("CLIENT_LOCALE_SUPPORT_DEFAULT_VALUE="
                + CLIENT_LOCALE_SUPPORT_DEFAULT_VALUE);

        LOG.info("VERSIONED_URI_HASHCODE_MAX_SIZE="
                + VERSIONED_URI_HASHCODE_MAX_SIZE);

        LOG.info("FACELETS_SUPPORT=" + FACELETS_SUPPORT);

        switch (DEFAULT_ASYNC_MODE) {
        case IAsyncRenderModeCapability.TREE_ASYNC_RENDER_MODE:
            LOG.info("DEFAULT_ASYNC_MODE=tree");
            break;

        case IAsyncRenderModeCapability.BUFFER_ASYNC_RENDER_MODE:
            LOG.info("DEFAULT_ASYNC_MODE=buffer");
            break;

        default:
            LOG.info("DEFAULT_ASYNC_MODE=none");
            break;
        }

        switch (ENABLE_ASYNC_MODE_VALUE) {
        case IAsyncRenderModeCapability.TREE_ASYNC_RENDER_MODE:
            LOG.info("ENABLE_ASYNC_MODE_VALUE=tree");
            break;

        case IAsyncRenderModeCapability.BUFFER_ASYNC_RENDER_MODE:
            LOG.info("ENABLE_ASYNC_MODE_VALUE=buffer");
            break;

        default:
            LOG.info("ENABLE_ASYNC_MODE_VALUE=unknown");
            break;
        }

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
