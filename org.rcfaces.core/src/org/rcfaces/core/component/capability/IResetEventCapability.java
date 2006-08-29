/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2004/12/22 12:16:15  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 * Revision 1.1  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IResetListener;


/**
 * L'�lement qui impl�mente cette interface g�n�re un �v�nement d�s que
 * l'utilisateur r�alise un "RESET" des composants de la vue.
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IResetEventCapability {

	void addResetListener(IResetListener facesListener);

	void removeResetListener(IResetListener facesListener);

	FacesListener[] listResetListeners();
}