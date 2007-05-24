/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;

import org.rcfaces.core.event.IServiceEventListener;
import org.rcfaces.core.event.ServiceEvent;
import org.rcfaces.core.internal.service.IEventReturnValue;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ServiceEventActionListener extends AbstractActionListener
        implements IServiceEventListener {
    private static final String REVISION = "$Revision$";

    private static final Class actionParameters[] = { ServiceEvent.class };

    public ServiceEventActionListener() {
    }

    public ServiceEventActionListener(String expression) {
        super(expression);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.component.listener.IChangeListener#processChange(org.rcfaces.core.component.listener.ChangeEvent)
     */
    public void processServiceEvent(ServiceEvent event)
            throws AbortProcessingException {
        process(event);
    }

    protected void processReturn(FacesContext facesContext,
            MethodBinding binding, FacesEvent event, Object ret) {
        // Pas de traitement de retour !

        if (ret == null) {
            return;
        }

        if (event instanceof IEventReturnValue) {
            ((IEventReturnValue) event).setReturnValue(ret);
        }
    }

    protected Class[] listParameterClasses() {
        return actionParameters;
    }
}
