/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.AbortProcessingException;

import org.rcfaces.core.event.ILoadListener;
import org.rcfaces.core.event.LoadEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class LoadActionListener extends AbstractActionListener implements
        ILoadListener {
    

    private static final Class actionParameters[] = { LoadEvent.class };

    public LoadActionListener() {
        // Pour la déserialisation ...
    }

    public LoadActionListener(String expression) {
        super(expression);
    }

    public LoadActionListener(String expression, boolean partialRendering) {
        super(expression, partialRendering);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.rcfaces.core.component.listener.IChangeListener#processChange(org
     * .rcfaces.core.component.listener.ChangeEvent)
     */
    public void processLoad(LoadEvent event) throws AbortProcessingException {
        process(event);
    }

    protected Class[] listParameterClasses() {
        return actionParameters;
    }
}
