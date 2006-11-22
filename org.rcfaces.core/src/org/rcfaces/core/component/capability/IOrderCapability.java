/*
 * $Id$
 *
 */
package org.rcfaces.core.component.capability;

/**
 * 
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
