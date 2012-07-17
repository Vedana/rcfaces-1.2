/*
 * $Id$
 * 
 */
package org.rcfaces.core.event;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesListener;

/**
 * @author jbmeslin@vedana.com
 */
public class PreSelectionEvent extends CameliaEvent {

    private static final long serialVersionUID = 6935088762418998766L;

    public PreSelectionEvent(UIComponent component) {
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
        return (listener instanceof IPreSelectionListener);
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
        ((IPreSelectionListener) listener).processPreSelection(this);
    }

}
