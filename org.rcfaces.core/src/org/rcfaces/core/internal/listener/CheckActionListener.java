/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.AbortProcessingException;

import org.rcfaces.core.event.CheckEvent;
import org.rcfaces.core.event.ICheckListener;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CheckActionListener extends AbstractActionListener implements
        ICheckListener {
    private static final String REVISION = "$Revision$";

    private static final Class actionParameters[] = { CheckEvent.class };

    public CheckActionListener(String expression) {
        super(expression);
    }

    public CheckActionListener() {
    }

    public void processCheck(CheckEvent event) throws AbortProcessingException {
        process(event);
    }

    protected Class[] listParameterClasses() {
        return actionParameters;
    }
}
