/*
 * $Id$
 * 
 * $Log$
 * Revision 1.4  2006/10/13 18:04:38  oeuillot
 * Ajout de:
 * DateEntry
 * StyledMessage
 * MessageFieldSet
 * xxxxConverter
 * Adapter
 *
 * Revision 1.3  2006/09/20 17:55:24  oeuillot
 * Tri multiple des tables
 * Dialogue modale en JS
 *
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 */
package org.rcfaces.renderkit.html.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
        String name = Constants.class.getPackage().getName();
        if (name.endsWith(".internal")) {
            name = name.substring(0, name.lastIndexOf('.'));
        }

        CONSTANT_PREFIX = name;
    }

    public static final String getPackagePrefix() {
        return CONSTANT_PREFIX;
    }

    private static final String RCFACES_HTML_VERSION_SYSTEM_PARAMETER = "rcfaces.html.version";

    public static final boolean INTERACTIVE_RENDER_DEFAULT_VALUE = true;

    public static final boolean INTERACTIVE_RENDER_GZIP_DEFAULT_VALUE = true;

    public static final int INTERACTIVE_RENDER_MINIMUM_GZIP_BUFFER_SIZE = 64;

    public static final boolean JAVASCRIPPT_APPEND_RCFACES_HEADER = true;

    public static final String JAVASCRIPT_GROUP_ALL_DEFAULT_VALUE = "all";

    public static final String JAVASCRIPT_BOOT_SET_DEFAULT_VALUE = "CORE";

    public static final String JAVASCRIPT_DEFAULT_CHARSET = "UTF-8";

    public static final String CSS_DEFAULT_CHARSET = "UTF-8";

    public static final boolean FRAMEWORK_VERSIONED_URL_SUPPORT = true;

    public static final boolean CLIENT_BUNDLE_VERSIONED_SUPPORT = true;

    public static final boolean PARAMETERIZED_SEPARATOR_SUPPORT = true;

    public static final boolean CLIENT_GROUP_NAME_SUPPORT = true;

    public static final boolean NORMALIZE_MARGINS = true;

    public static final boolean DISABLE_IE_IMAGEBAR_DEFAULT_VALUE = true;

    public static final boolean STAT_RESOURCES_HTTP_RESPONSE = false;

    private static final String version;

    private static final String buildId;

    static {
        version = org.rcfaces.core.internal.Constants.searchVersion(
                Constants.class, RCFACES_HTML_VERSION_SYSTEM_PARAMETER,
                "RCFaces HTML");

        buildId = org.rcfaces.core.internal.Constants.getBuildId(version);

        LOG.info("INTERACTIVE_RENDER_DEFAULT_VALUE="
                + INTERACTIVE_RENDER_DEFAULT_VALUE);

        LOG.info("INTERACTIVE_RENDER_GZIP_DEFAULT_VALUE="
                + INTERACTIVE_RENDER_GZIP_DEFAULT_VALUE);

        LOG.info("FRAMEWORK_VERSIONED_URL_SUPPORT="
                + FRAMEWORK_VERSIONED_URL_SUPPORT);

        LOG.info("CLIENT_BUNDLE_VERSIONED_SUPPORT="
                + CLIENT_BUNDLE_VERSIONED_SUPPORT);

        LOG.info("PARAMETERIZED_SEPARATOR_SUPPORT="
                + PARAMETERIZED_SEPARATOR_SUPPORT);

        LOG.info("CLIENT_GROUP_NAME_SUPPORT=" + CLIENT_GROUP_NAME_SUPPORT);

        LOG.info("NORMALIZE_MARGINS=" + NORMALIZE_MARGINS);

        LOG.debug("DISABLE_IE_IMAGEBAR_DEFAULT_VALUE="
                + DISABLE_IE_IMAGEBAR_DEFAULT_VALUE);

        LOG.debug("STAT_RESOURCES_HTTP_RESPONSE="
                + STAT_RESOURCES_HTTP_RESPONSE);
    }

    public static final String getVersion() {
        return version;
    }

    public static final String getBuildId() {
        return buildId;
    }

}
