/*
 * $Id$
 * 
 */
package org.rcfaces.core.event;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CheckEvent extends ActionEvent {
	private static final long serialVersionUID = -8297800672322392936L;

	public CheckEvent(UIComponent component) {
		super(component);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.faces.event.FacesEvent#isAppropriateListener(javax.faces.event.
	 * FacesListener)
	 */
	public boolean isAppropriateListener(FacesListener listener) {
		return (listener instanceof ICloseListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.faces.event.FacesEvent#processListener(javax.faces.event.FacesListener
	 * )
	 */
	public void processListener(FacesListener listener) {
		((ICheckListener) listener).processCheck(this);
	}

}
