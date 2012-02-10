/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.DoubleClickEvent;
import org.rcfaces.core.event.IDoubleClickListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DoubleClickScriptListener extends AbstractScriptListener implements
        IDoubleClickListener {

    public DoubleClickScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public DoubleClickScriptListener() {
    }

    public void processDoubleClick(DoubleClickEvent event) {
    }

}
