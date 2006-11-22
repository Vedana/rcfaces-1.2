/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IDoubleClickListener;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IDoubleClickEventCapability {

	void addDoubleClickListener(IDoubleClickListener facesListener);

	void removeDoubleClickListener(IDoubleClickListener facesListener);

	FacesListener [] listDoubleClickListeners();
}
