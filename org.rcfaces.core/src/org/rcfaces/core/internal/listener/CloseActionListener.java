/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.AbortProcessingException;

import org.rcfaces.core.event.CloseEvent;
import org.rcfaces.core.event.ICloseListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CloseActionListener extends AbstractActionListener implements
        ICloseListener {

    private static final Class< ? >[] actionParameters = { CloseEvent.class };

    public CloseActionListener() {
    }

    public CloseActionListener(String expression) {
        super(expression);
    }

    public CloseActionListener(String expression, boolean partialRendering) {
        super(expression, partialRendering);
    }

    public void processClose(CloseEvent event) throws AbortProcessingException {
        process(event);
    }

    @Override
    protected Class< ? >[] listParameterClasses() {
        return actionParameters;
    }
}
