/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.IInitListener;
import org.rcfaces.core.event.InitEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class InitScriptListener extends AbstractScriptListener implements
        IInitListener {
    private static final String REVISION = "$Revision$";

    public InitScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public InitScriptListener() {
    }

    public void processInit(InitEvent event) {
    }
}
