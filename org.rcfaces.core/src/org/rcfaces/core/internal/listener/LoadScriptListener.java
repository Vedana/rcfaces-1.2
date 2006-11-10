/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.ILoadListener;
import org.rcfaces.core.event.LoadEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class LoadScriptListener extends AbstractScriptListener implements
        ILoadListener {
    private static final String REVISION = "$Revision$";

    public LoadScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public LoadScriptListener() {
    }

    public void processLoad(LoadEvent event) {
    }
}
