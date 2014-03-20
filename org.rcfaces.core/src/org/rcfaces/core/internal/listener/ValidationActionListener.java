/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.AbortProcessingException;

import org.rcfaces.core.event.IValidationListener;
import org.rcfaces.core.event.ValidationEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ValidationActionListener extends AbstractActionListener implements
        IValidationListener {
    

    private static final Class actionParameters[] = { ValidationEvent.class };

    public ValidationActionListener() {
        // Pour la déserialisation ...
    }

    public ValidationActionListener(String expression) {
        super(expression);
    }

    public ValidationActionListener(String expression, boolean partialRendering) {
        super(expression, partialRendering);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.rcfaces.core.component.listener.IChangeListener#processChange(org
     * .rcfaces.core.component.listener.ChangeEvent)
     */
    public void processValidationEvent(ValidationEvent event)
            throws AbortProcessingException {
        process(event);
    }

    protected Class[] listParameterClasses() {
        return actionParameters;
    }
}
