/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.5  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.4  2004/08/20 13:32:07  oeuillot
 * *** empty log message ***
 *
 * Revision 1.3  2004/08/13 13:04:58  oeuillot
 * *** empty log message ***
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