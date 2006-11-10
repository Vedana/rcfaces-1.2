/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.IUserEventListener;
import org.rcfaces.core.event.UserEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class UserEventScriptListener extends AbstractScriptListener implements
        IUserEventListener {
    private static final String REVISION = "$Revision$";

    public UserEventScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public UserEventScriptListener() {
    }

    public void processUserEvent(UserEvent event) {
    }
}
