/*
 * $Id$
 * 
 * $Log$
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
import org.rcfaces.core.internal.util.CameliaVersion;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class Constants {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(Constants.class);

    public static final boolean READ_ONLY_COLLECTION_LOCK_ENABLED = true;

    public static final boolean TEMPLATE_SUPPORT = true;

    public static final boolean URL_REWRITING_SUPPORT = true;

    public static final boolean CACHED_ITERATOR_ENABLED = true;

    public static final boolean STATE_CHILDREN_LIST_ENABLED = true;

    public static final boolean BUILD_ID_URL_SUPPORT = true;

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
        CameliaVersion.getVersion();

        LOG.info("READ_ONLY_COLLECTION_LOCK_ENABLED="
                + READ_ONLY_COLLECTION_LOCK_ENABLED);

        LOG.info("TEMPLATE_SUPPORT=" + TEMPLATE_SUPPORT);

        LOG.info("URL_REWRITING_SUPPORT=" + URL_REWRITING_SUPPORT);

        LOG.info("BUILD_ID_URL_SUPPORT=" + BUILD_ID_URL_SUPPORT);

        LOG.info("STATE_CHILDREN_LIST_ENABLED=" + STATE_CHILDREN_LIST_ENABLED);

        LOG.info("CACHED_ITERATOR_ENABLED=" + CACHED_ITERATOR_ENABLED);

        defaultFactory = ComponentsFactory.getCameliaFactory(null);
        LOG.info("Default components factory: " + defaultFactory.getName());
    }

    public static final String getPackagePrefix() {
        return CONSTANT_PREFIX;
    }

    public static IFactory getCameliaFactory() {
        return defaultFactory;
    }
}
