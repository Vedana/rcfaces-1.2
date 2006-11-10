/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.IKeyPressListener;
import org.rcfaces.core.event.KeyPressEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class KeyPressScriptListener extends AbstractScriptListener implements
        IKeyPressListener {
    private static final String REVISION = "$Revision$";

    public KeyPressScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public KeyPressScriptListener() {
    }

    public void processKeyPress(KeyPressEvent event) {
    }
}
