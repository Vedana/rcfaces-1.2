/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.IKeyUpListener;
import org.rcfaces.core.event.KeyUpEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class KeyUpScriptListener extends AbstractScriptListener implements
        IKeyUpListener {
    private static final String REVISION = "$Revision$";

    public KeyUpScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public KeyUpScriptListener() {
    }

    public void processKeyUp(KeyUpEvent event) {
    }
}
