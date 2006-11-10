/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IMenuListener;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IMenuEventCapability {
	void addMenuListener(IMenuListener facesListener);

	void removeMenuListener(IMenuListener facesListener);

	FacesListener [] listMenuListeners();
}
