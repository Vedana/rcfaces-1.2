/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.IResetListener;
import org.rcfaces.core.event.ResetEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ResetScriptListener extends AbstractScriptListener implements
        IResetListener {
    private static final String REVISION = "$Revision$";

    public ResetScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public ResetScriptListener() {
    }

    public void processReset(ResetEvent event) {
    }

}
