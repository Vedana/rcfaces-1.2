/*
 * $Id$
 */
package org.rcfaces.core.event;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesListener;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ValidationEvent extends CameliaEvent {

    private static final long serialVersionUID = -1228480671525989664L;

    public ValidationEvent(UIComponent component) {
        super(component);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.faces.event.FacesEvent#isAppropriateListener(javax.faces.event.
     * FacesListener)
     */
    @Override
    public boolean isAppropriateListener(FacesListener listener) {
        return (listener instanceof IValidationListener);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.faces.event.FacesEvent#processListener(javax.faces.event.FacesListener
     * )
     */
    @Override
    public void processListener(FacesListener listener) {
        ((IValidationListener) listener).processValidationEvent(this);
    }

}