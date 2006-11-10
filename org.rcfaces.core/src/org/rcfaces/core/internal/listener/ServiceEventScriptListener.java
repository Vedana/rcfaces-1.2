/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import org.rcfaces.core.event.IServiceEventListener;
import org.rcfaces.core.event.ServiceEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ServiceEventScriptListener extends AbstractScriptListener
        implements IServiceEventListener {
    private static final String REVISION = "$Revision$";

    public ServiceEventScriptListener(String scriptType, String command) {
        super(scriptType, command);
    }

    public ServiceEventScriptListener() {
    }

    public void processServiceEvent(ServiceEvent event) {
    }
}
