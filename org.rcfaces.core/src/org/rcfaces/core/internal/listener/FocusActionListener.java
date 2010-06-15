/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.AbortProcessingException;

import org.rcfaces.core.event.FocusEvent;
import org.rcfaces.core.event.IFocusListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class FocusActionListener extends AbstractActionListener implements
        IFocusListener {
    private static final String REVISION = "$Revision$";

    private static final Class actionParameters[] = { FocusEvent.class };

    public FocusActionListener() {
        // Pour la déserialisation ...
    }

    public FocusActionListener(String expression) {
        super(expression);
    }

    public FocusActionListener(String expression, boolean partialRendering) {
        super(expression, partialRendering);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.rcfaces.core.component.listener.IChangeListener#processChange(org
     * .rcfaces.core.component.listener.ChangeEvent)
     */
    public void processFocus(FocusEvent event) throws AbortProcessingException {
        process(event);
    }

    protected Class[] listParameterClasses() {
        return actionParameters;
    }
}
