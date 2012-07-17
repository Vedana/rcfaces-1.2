/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.AbortProcessingException;

import org.rcfaces.core.event.DoubleClickEvent;
import org.rcfaces.core.event.IDoubleClickListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DoubleClickActionListener extends AbstractActionListener implements
        IDoubleClickListener {

    private static final Class< ? >[] actionParameters = { DoubleClickEvent.class };

    public DoubleClickActionListener() {
    }

    public DoubleClickActionListener(String expression) {
        super(expression);
    }

    public DoubleClickActionListener(String expression, boolean partialRendering) {
        super(expression, partialRendering);
    }

    public void processDoubleClick(DoubleClickEvent event)
            throws AbortProcessingException {
        process(event);
    }

    @Override
    protected Class< ? >[] listParameterClasses() {
        return actionParameters;
    }
}
