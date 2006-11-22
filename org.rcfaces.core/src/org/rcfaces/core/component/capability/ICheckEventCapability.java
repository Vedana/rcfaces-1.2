/*
 * $Id$
 * 
 */

package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.ICheckListener;


/**
 * 
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICheckEventCapability {

	void addCheckListener(ICheckListener checkListener);

	void removeCheckListener(ICheckListener checkListener);

	FacesListener[] listCheckListeners();
}
