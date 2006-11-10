/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IPropertyChangeListener;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IPropertyChangeEventCapability {
	void addPropertyChangeListener(IPropertyChangeListener facesListener);

	void removePropertyChangeListener(IPropertyChangeListener facesListener);

	FacesListener [] listPropertyChangeListeners();

}
