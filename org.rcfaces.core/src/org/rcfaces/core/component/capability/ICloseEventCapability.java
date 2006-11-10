/*
 * $Id$
 * 
 */

package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.ICloseListener;


/**
 * 
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICloseEventCapability {

	void addCloseListener(ICloseListener facesListener);

	void removeCloseListener(ICloseListener facesListener);

	FacesListener [] listCloseListeners();
}
