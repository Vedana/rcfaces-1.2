/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.AbortProcessingException;

import org.rcfaces.core.event.DropEvent;
import org.rcfaces.core.event.IDropListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DropScriptListener extends AbstractScriptListener implements
        IDropListener {
    private static final String REVISION = "$Revision$";

    public DropScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public DropScriptListener() {
    }

    public void componentDropped(DropEvent event)
            throws AbortProcessingException {
    }
}
