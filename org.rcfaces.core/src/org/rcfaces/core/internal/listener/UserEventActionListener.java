/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.AbortProcessingException;

import org.rcfaces.core.event.IUserEventListener;
import org.rcfaces.core.event.UserEvent;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class UserEventActionListener extends AbstractActionListener implements
        IUserEventListener {
    private static final String REVISION = "$Revision$";

    private static final Class actionParameters[] = { UserEvent.class };

    public UserEventActionListener() {
        // Pour la déserialisation ...
    }

    public UserEventActionListener(String expression) {
        super(expression);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.component.listener.IChangeListener#processChange(org.rcfaces.core.component.listener.ChangeEvent)
     */
    public void processUserEvent(UserEvent event)
            throws AbortProcessingException {
        process(event);
    }

    protected Class[] listParameterClasses() {
        return actionParameters;
    }
}
