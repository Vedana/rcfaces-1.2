/*
 * $Id$
 *
 */
package org.rcfaces.core.component.capability;

/**
 * Sp�cifie l'orientation du composant.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IOrientationCapability {

	/**
	 * Retourne l'orientation du composant.
	 */
	String getOrientation();

	/**
	 * Sp�cifie l'orientation du composant.
	 */
	void setOrientation(String orientation);

}
