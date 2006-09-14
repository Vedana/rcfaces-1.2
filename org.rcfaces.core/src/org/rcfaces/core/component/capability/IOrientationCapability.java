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
 * Revision 1.2  2004/09/02 17:44:30  oeuillot
 * *** empty log message ***
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