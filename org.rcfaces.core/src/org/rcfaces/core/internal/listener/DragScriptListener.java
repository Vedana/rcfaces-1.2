/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.AbortProcessingException;

import org.rcfaces.core.event.DragEvent;
import org.rcfaces.core.event.IDragListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DragScriptListener extends AbstractScriptListener implements
        IDragListener {
    private static final String REVISION = "$Revision$";

    public DragScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public DragScriptListener() {
    }

    public void componentDragged(DragEvent event)
            throws AbortProcessingException {
    }
}
