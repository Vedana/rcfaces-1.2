/*
 * $Id$
 * 
 */

package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.ICheckListener;


/**
 * L'élement qui impl�mente cette interface g�n�re un événement dés que
 * l'utilisateur interagit avec lui.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICheckEventCapability {

	void addCheckListener(ICheckListener checkListener);

	void removeCheckListener(ICheckListener checkListener);

	FacesListener[] listCheckListeners();
}
