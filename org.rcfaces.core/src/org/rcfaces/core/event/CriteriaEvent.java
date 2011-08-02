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
public class CriteriaEvent extends ActionEvent {

	private static final long serialVersionUID = -8778872157368114361L;

	public CriteriaEvent(UIComponent component) {
		super(component);
	}

	public boolean isAppropriateListener(FacesListener listener) {

		if (listener instanceof ICriteriaListener) {
			return true;
		}

		return super.isAppropriateListener(listener);
	}

	public void processListener(FacesListener listener) {
		if (listener instanceof ICriteriaListener) {
			((ICriteriaListener) listener).processCriteriaChanged(this);
			return;
		}

		super.processListener(listener);
	}

}
