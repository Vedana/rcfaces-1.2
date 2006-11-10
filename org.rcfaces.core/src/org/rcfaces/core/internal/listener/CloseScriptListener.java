/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.CloseEvent;
import org.rcfaces.core.event.ICloseListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CloseScriptListener extends AbstractScriptListener implements
        ICloseListener {
    private static final String REVISION = "$Revision$";

    public CloseScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public CloseScriptListener() {
    }

    public void processClose(CloseEvent event) {
    }
}
