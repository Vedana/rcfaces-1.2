/*
 * $Id$
 * 
 */

package org.rcfaces.core.component.capability;

/**
 * Un �l�ment qui impl�mente cette interface autorise un rendu diff�rent.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ILookAndFeelCapability {

	/**
	 * Sp�cifie le LookID de l'�l�ment.
	 */
	void setLookId(String ID);

	/**
	 * Retourne le LookID de l'�l�ment.
	 */
	String getLookId();
}
