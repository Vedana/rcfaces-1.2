/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * Un �l�ment qui implemente ExpandCapability permet d'afficher ou non ses
 * enfants.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IExpandCapability {

	/**
	 * Retourne si l'�l�ment affiche ses enfants ou pas.
	 */
	boolean isExpanded();

	/**
	 * Sp�cifie si l'�l�ment affiche ses enfants ou pas.
	 */
	void setExpanded(boolean expand);
}
