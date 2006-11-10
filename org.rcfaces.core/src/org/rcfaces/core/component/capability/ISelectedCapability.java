/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * Cette interface sp�cifie le status de s�lection de l'�l�ment.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISelectedCapability {

	/**
	 * Retourne si l'�l�ment est s�lectionn� ou pas.
	 */
	boolean isSelected();

	/**
	 * Sp�cifie si l'�l�ment est s�lectionn� ou pas.
	 */
	void setSelected(boolean selected);
}
