/*
 * $Id$
 */
package org.rcfaces.core.internal.renderkit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DefaultDesignerInterface implements IDesignerInterface {

    private static final Log LOG = LogFactory
            .getLog(DefaultDesignerInterface.class);

    public void containerChildren(ISgmlWriter writer, String containerName) {

        try {
            writer.writeAttribute("_rcfaces_container", containerName);

        } catch (WriterException ex) {
            LOG.error("Can not write container chidren marker", ex);
        }
    }

}
