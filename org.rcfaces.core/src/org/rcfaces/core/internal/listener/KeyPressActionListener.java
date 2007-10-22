/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.AbortProcessingException;

import org.rcfaces.core.event.IKeyPressListener;
import org.rcfaces.core.event.KeyPressEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class KeyPressActionListener extends AbstractActionListener implements
        IKeyPressListener {
    private static final String REVISION = "$Revision$";

    private static final Class actionParameters[] = { KeyPressEvent.class };

    public KeyPressActionListener(String expression) {
        super(expression);
    }

    public KeyPressActionListener() {
    }

    public void processKeyPress(KeyPressEvent event)
            throws AbortProcessingException {
        process(event);
    }

    protected Class[] listParameterClasses() {
        return actionParameters;
    }
}
