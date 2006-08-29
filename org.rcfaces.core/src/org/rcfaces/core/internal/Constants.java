/*
 * $Id$
 * 
 * $Log$
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
import org.rcfaces.core.internal.util.CameliaVersion;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class Constants {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(Constants.class);

    public static final boolean LOCK_READ_ONLY_COLLECTIONS = true;

    public static final boolean TEMPLATE_SUPPORT = true;

    private static final String CONSTANT_PREFIX;
    static {
        String name = Constants.class.getPackage().getName();
        if (name.endsWith(".internal")) {
            name = name.substring(0, name.lastIndexOf('.'));
        }

        CONSTANT_PREFIX = name;
    }

    private static final IFactory defaultFactory;

    static {
        CameliaVersion.getVersion();

        LOG.info("LOCK_READ_ONLY_COLLECTIONS=" + LOCK_READ_ONLY_COLLECTIONS);

        LOG.info("TEMPLATE_SUPPORT=" + TEMPLATE_SUPPORT);

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
