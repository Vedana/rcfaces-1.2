/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.IMouseOverListener;
import org.rcfaces.core.event.MouseOverEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class MouseOverScriptListener extends AbstractScriptListener implements
        IMouseOverListener {
    private static final String REVISION = "$Revision$";

    public MouseOverScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public MouseOverScriptListener() {
    }

    public void processMouseOver(MouseOverEvent event) {
    }
}
