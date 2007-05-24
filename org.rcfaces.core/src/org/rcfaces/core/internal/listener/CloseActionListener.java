/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.AbortProcessingException;

import org.rcfaces.core.event.CheckEvent;
import org.rcfaces.core.event.CloseEvent;
import org.rcfaces.core.event.ICloseListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CloseActionListener extends AbstractActionListener implements
        ICloseListener {
    private static final String REVISION = "$Revision$";

    private static final Class actionParameters[] = { CheckEvent.class };

    public CloseActionListener(String expression) {
        super(expression);
    }

    public CloseActionListener() {
    }

    public void processClose(CloseEvent event) throws AbortProcessingException {
        process(event);
    }

    protected Class[] listParameterClasses() {
        return actionParameters;
    }
}
