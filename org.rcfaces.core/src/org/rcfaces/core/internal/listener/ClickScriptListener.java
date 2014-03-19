/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.ClickEvent;
import org.rcfaces.core.event.IClickListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ClickScriptListener extends AbstractScriptListener implements
        IClickListener {

    public ClickScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public ClickScriptListener() {
    }

    public void processClick(ClickEvent event) {
    }

}
