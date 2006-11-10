/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.IMenuListener;
import org.rcfaces.core.event.MenuEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class MenuScriptListener extends AbstractScriptListener implements
        IMenuListener {
    private static final String REVISION = "$Revision$";

    public MenuScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public MenuScriptListener() {
    }

    public void menuShown(MenuEvent event) {
    }

}
