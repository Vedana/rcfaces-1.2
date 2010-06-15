/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.AbortProcessingException;

import org.rcfaces.core.event.DropEvent;
import org.rcfaces.core.event.IDropListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DropActionListener extends AbstractActionListener implements
        IDropListener {
    private static final String REVISION = "$Revision$";

    private static final Class actionParameters[] = { DropEvent.class };

    public DropActionListener(String expression, boolean partialRendering) {
        super(expression, partialRendering);
    }

    public DropActionListener(String expression) {
        super(expression);
    }

    public DropActionListener() {
    }

    public void componentDropped(DropEvent event)
            throws AbortProcessingException {
        process(event);
    }

    protected Class[] listParameterClasses() {
        return actionParameters;
    }
}
