/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.IMouseOutListener;
import org.rcfaces.core.event.MouseOutEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class MouseOutScriptListener extends AbstractScriptListener implements
        IMouseOutListener {
    private static final String REVISION = "$Revision$";

    public MouseOutScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public MouseOutScriptListener() {
    }

    public void processMouseOut(MouseOutEvent event) {
    }

}
