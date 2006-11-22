/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IResetListener;


/**
 * 
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IResetEventCapability {

	void addResetListener(IResetListener facesListener);

	void removeResetListener(IResetListener facesListener);

	FacesListener[] listResetListeners();
}
