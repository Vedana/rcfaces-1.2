/*
 * $Id$
 *
 */
package org.rcfaces.core.component.capability;

/**
 * Sp�cifie le type d'ordre g�r� par l'�l�ment.
 * 
 * @author Joel Merlin, Olivier Oeuillot
 * @version $Revision$ $Date$
 */
public interface IOrderCapability {

	/**
	 * Retourne l'ordre de l'�lement
	 */
	boolean isAscending();

	/**
	 * Sp�cifie l'ordre de l'�lement
	 */
	void setAscending(boolean ascending);

}
