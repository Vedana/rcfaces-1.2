/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2004/08/20 12:08:30  oeuillot
 * *** empty log message ***
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

/**
 * Un �l�ment qui implemente cette interface permet d'ordonner la capture du
 * focus par rapport aux autres �lemeent de la view.
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface ITabIndexCapability {
	/**
	 * Retourne une valeur qui va indiquer l'ordre d'obtention du focus par
	 * rapport aux autres elements de la view.
	 */
	Integer getTabIndex();

	/**
	 * Sp�cifie une valeur qui va indiquer l'ordre d'obtention du focus par
	 * rapport aux autres elements de la view.
	 */
	void setTabIndex(Integer tabIndex);
}