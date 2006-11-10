/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.AbortProcessingException;

import org.rcfaces.core.event.FocusEvent;
import org.rcfaces.core.event.IFocusListener;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class FocusScriptListener extends AbstractScriptListener implements
        IFocusListener {
    private static final String REVISION = "$Revision$";

    public FocusScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public FocusScriptListener() {
    }

    public void processFocus(FocusEvent event) throws AbortProcessingException {
    }
}
