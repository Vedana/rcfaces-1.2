package org.rcfaces.core.event;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesListener;

/**
 * @author meslin.jb@vedana.com
 */
public class ExpandEvent extends ActionEvent {
 
	private static final long serialVersionUID = -8338479464413940009L;
	private static final String REVISION = "$Revision$";
   

    public ExpandEvent(UIComponent component) {
        super(component);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.event.FacesEvent#isAppropriateListener(javax.faces.event.FacesListener)
     */
    public boolean isAppropriateListener(FacesListener listener) {
        return (listener instanceof IExpandListener);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.event.FacesEvent#processListener(javax.faces.event.FacesListener)
     */
    public void processListener(FacesListener listener) {
        ((IExpandListener) listener).processExpand(this);
    }

}
