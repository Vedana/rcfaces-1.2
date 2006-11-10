/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * Un �l�ment qui implemente cette interface permet d'ordonner la capture du
 * focus par rapport aux autres �lemeent de la view.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
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
