/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.IPreSelectionListener;
import org.rcfaces.core.event.PreSelectionEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class PreSelectionScriptListener extends AbstractScriptListener implements
        IPreSelectionListener {
    

    public PreSelectionScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public PreSelectionScriptListener() {
    }

	public void processPreSelection(PreSelectionEvent event) {
	}
}
