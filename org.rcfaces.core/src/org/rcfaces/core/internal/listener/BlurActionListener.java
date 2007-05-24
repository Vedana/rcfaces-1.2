/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.AbortProcessingException;

import org.rcfaces.core.event.BlurEvent;
import org.rcfaces.core.event.IBlurListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class BlurActionListener extends AbstractActionListener implements
        IBlurListener {
    private static final String REVISION = "$Revision$";

    private static final Class actionParameters[] = { BlurEvent.class };

    public BlurActionListener() {
        // Pour la déserialisation ...
    }

    public BlurActionListener(String expression) {
        super(expression);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.component.listener.IChangeListener#processChange(org.rcfaces.core.component.listener.ChangeEvent)
     */
    public void processBlur(BlurEvent event) throws AbortProcessingException {
        process(event);
    }

    protected Class[] listParameterClasses() {
        return actionParameters;
    }
}
