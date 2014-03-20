/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.AbortProcessingException;

import org.rcfaces.core.event.DropCompleteEvent;
import org.rcfaces.core.event.IDropCompleteListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DropCompleteScriptListener extends AbstractScriptListener
        implements IDropCompleteListener {
    

    public DropCompleteScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public DropCompleteScriptListener() {
    }

    public void componentCompleteDropped(DropCompleteEvent event)
            throws AbortProcessingException {
    }
}
