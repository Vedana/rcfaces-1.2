/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.AbortProcessingException;

import org.rcfaces.core.event.IServiceEventListener;
import org.rcfaces.core.event.ServiceEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ServiceEventActionListener extends
        AbstractReturnValueActionListener implements IServiceEventListener {

    private static final Class actionParameters[] = { ServiceEvent.class };

    public ServiceEventActionListener() {
    }

    public ServiceEventActionListener(String expression) {
        super(expression);
    }

    public ServiceEventActionListener(String expression,
            boolean partialRendering) {
        super(expression, partialRendering);
    }

    public void processServiceEvent(ServiceEvent event)
            throws AbortProcessingException {
        process(event);
    }

    protected Class[] listParameterClasses() {
        return actionParameters;
    }
}
