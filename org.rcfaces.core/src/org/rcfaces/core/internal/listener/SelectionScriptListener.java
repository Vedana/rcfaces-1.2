/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.ISelectionListener;
import org.rcfaces.core.event.SelectionEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SelectionScriptListener extends AbstractScriptListener implements
        ISelectionListener {
    private static final String REVISION = "$Revision$";

    public SelectionScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public SelectionScriptListener() {
    }

    public void componentSelected(SelectionEvent event) {
    }

}
