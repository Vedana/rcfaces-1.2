/*
 * $Id$
 * 
 */
package org.rcfaces.core.event;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class InitEvent extends CameliaEvent {

    private static final long serialVersionUID = -1290317375001347364L;

    public InitEvent(UIComponent component) {
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
        return (listener instanceof IInitListener);
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
        ((IInitListener) listener).processInit(this);
    }

}
