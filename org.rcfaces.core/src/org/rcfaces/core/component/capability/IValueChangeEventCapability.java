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
 * Revision 1.2  2006/06/27 09:23:09  oeuillot
 * Mise � jour du calendrier de dateChooser
 *
 * Revision 1.1  2004/08/20 13:32:07  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/13 13:04:58  oeuillot
 * *** empty log message ***
 *
 * Revision 1.2  2004/08/13 10:13:47  oeuillot
 * Ajout des evenements
 *
 * Revision 1.1  2004/08/06 14:03:56  jmerlin
 * Vedana Faces
 *
 * Revision 1.3  2004/08/06 09:35:14  oeuillot
 * *** empty log message ***
 *
 * Revision 1.2  2004/08/05 09:49:50  oeuillot
 * Retire la gestion des Resources
 *
 */

package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;
import javax.faces.event.ValueChangeListener;

/**
 * L'élement qui implémente cette interface génère un événement dés que
 * l'utilisateur a modifié le texte ou une valeur.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IValueChangeEventCapability {

	void addValueChangeListener(ValueChangeListener facesListener);

	void removeValueChangeListener(ValueChangeListener facesListener);

	FacesListener [] listValueChangeListeners();
}