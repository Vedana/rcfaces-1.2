/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.AbortProcessingException;

import org.rcfaces.core.event.IResetListener;
import org.rcfaces.core.event.ResetEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ResetActionListener extends AbstractActionListener implements
        IResetListener {
    private static final String REVISION = "$Revision$";

    private static final Class actionParameters[] = { ResetEvent.class };

    public ResetActionListener() {
    }

    public ResetActionListener(String expression) {
        super(expression);
    }

    public ResetActionListener(String expression, boolean partialRendering) {
        super(expression, partialRendering);
    }

    public void processReset(ResetEvent event) throws AbortProcessingException {
        process(event);
    }

    protected Class[] listParameterClasses() {
        return actionParameters;
    }

}
