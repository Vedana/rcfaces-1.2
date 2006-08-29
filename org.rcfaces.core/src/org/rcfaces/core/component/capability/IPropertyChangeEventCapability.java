/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2004/09/08 09:26:08  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IPropertyChangeListener;


/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IPropertyChangeEventCapability {
	void addPropertyChangeListener(IPropertyChangeListener facesListener);

	void removePropertyChangeListener(IPropertyChangeListener facesListener);

	FacesListener [] listPropertyChangeListeners();

}
