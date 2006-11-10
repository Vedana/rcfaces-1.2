/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ChangeActionListener extends AbstractActionListener implements
        ValueChangeListener {
    private static final String REVISION = "$Revision$";

    private final static Class actionParameters[] = { ValueChangeEvent.class };

    public ChangeActionListener(String expression) {
        super(expression);
    }

    public ChangeActionListener() {
    }

    public void processValueChange(ValueChangeEvent event)
            throws AbortProcessingException {
        process(event);
    }

    protected Class[] listParameterClasses() {
        return actionParameters;
    }
}
