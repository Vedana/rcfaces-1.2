/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.AbortProcessingException;

import org.rcfaces.core.event.DropCompleteEvent;
import org.rcfaces.core.event.IDropCompleteListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DropCompleteActionListener extends AbstractActionListener
        implements IDropCompleteListener {
    private static final String REVISION = "$Revision$";

    private static final Class actionParameters[] = { DropCompleteEvent.class };

    public DropCompleteActionListener(String expression,
            boolean partialRendering) {
        super(expression, partialRendering);
    }

    public DropCompleteActionListener(String expression) {
        super(expression);
    }

    public DropCompleteActionListener() {
    }

    public void componentCompleteDropped(DropCompleteEvent event)
            throws AbortProcessingException {
        process(event);
    }

    protected Class[] listParameterClasses() {
        return actionParameters;
    }
}
