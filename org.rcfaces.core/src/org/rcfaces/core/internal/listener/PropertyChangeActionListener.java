/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.AbortProcessingException;

import org.rcfaces.core.event.IPropertyChangeListener;
import org.rcfaces.core.event.PropertyChangeEvent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class PropertyChangeActionListener extends AbstractActionListener
        implements IPropertyChangeListener {
    private static final String REVISION = "$Revision$";

    private static final Class actionParameters[] = { PropertyChangeEvent.class };

    public PropertyChangeActionListener() {
    }

    public PropertyChangeActionListener(String expression) {
        super(expression);
    }

    public PropertyChangeActionListener(String expression,
            boolean partialRendering) {
        super(expression, partialRendering);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.rcfaces.core.component.listener.IChangeListener#processChange(org
     * .rcfaces.core.component.listener.ChangeEvent)
     */
    public void processPropertyChange(PropertyChangeEvent event)
            throws AbortProcessingException {
        process(event);
    }

    protected Class[] listParameterClasses() {
        return actionParameters;
    }
}
