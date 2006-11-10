/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.CheckEvent;
import org.rcfaces.core.event.ICheckListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CheckScriptListener extends AbstractScriptListener implements
        ICheckListener {
    private static final String REVISION = "$Revision$";

    public CheckScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public CheckScriptListener() {
    }

    public void processCheck(CheckEvent event) {
    }
}
