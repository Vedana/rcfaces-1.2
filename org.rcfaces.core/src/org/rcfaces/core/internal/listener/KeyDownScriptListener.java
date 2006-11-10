/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.IKeyDownListener;
import org.rcfaces.core.event.KeyDownEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class KeyDownScriptListener extends AbstractScriptListener implements
        IKeyDownListener {
    private static final String REVISION = "$Revision$";

    public KeyDownScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public KeyDownScriptListener() {
    }

    public void processKeyDown(KeyDownEvent event) {
    }
}
