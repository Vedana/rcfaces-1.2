/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IResetListener;


/**
 * L'�lement qui impl�mente cette interface g�n�re un �v�nement d�s que
 * l'utilisateur r�alise un "RESET" des composants de la vue.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IResetEventCapability {

	void addResetListener(IResetListener facesListener);

	void removeResetListener(IResetListener facesListener);

	FacesListener[] listResetListeners();
}
