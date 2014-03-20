/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.ErrorEvent;
import org.rcfaces.core.event.IErrorListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ErrorScriptListener extends AbstractScriptListener implements
        IErrorListener {
    

    public ErrorScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public ErrorScriptListener() {
    }

    public void processError(ErrorEvent event) {
    }
}
