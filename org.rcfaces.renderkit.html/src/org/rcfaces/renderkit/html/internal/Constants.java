/*
 * $Id$
 * 
 * $Log$
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

    private static final String version;

    static {
        version = org.rcfaces.core.internal.Constants.searchVersion(
                Constants.class, RCFACES_HTML_VERSION_SYSTEM_PARAMETER,
                "RCFaces HTML");

        LOG.info("INTERACTIVE_RENDER_DEFAULT_VALUE="
                + INTERACTIVE_RENDER_DEFAULT_VALUE);

        LOG.info("INTERACTIVE_RENDER_GZIP_DEFAULT_VALUE="
                + INTERACTIVE_RENDER_GZIP_DEFAULT_VALUE);

        LOG.info("FRAMEWORK_VERSIONED_URL_SUPPORT="
                + FRAMEWORK_VERSIONED_URL_SUPPORT);

        LOG.info("CLIENT_BUNDLE_VERSIONED_SUPPORT="
                + CLIENT_BUNDLE_VERSIONED_SUPPORT);
    }

    public static final String getVersion() {
        return version;
    }

}
