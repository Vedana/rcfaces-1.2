/*
 * $Id$
 */
package org.rcfaces.core.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.component.ComponentsFactory;
import org.rcfaces.core.internal.component.IFactory;

public class Constants1 extends Constants {

    private static final Log LOG = LogFactory.getLog(Constants1.class);

    protected static final IFactory defaultFactory;

    static {
        defaultFactory = ComponentsFactory.getCameliaFactory(null);
        LOG.info("Default components factory: "
                + Constants1.defaultFactory.getName());

        FACELETS_SUPPORT = true;
    }

    public static IFactory getCameliaFactory() {
        return defaultFactory;
    }
}
