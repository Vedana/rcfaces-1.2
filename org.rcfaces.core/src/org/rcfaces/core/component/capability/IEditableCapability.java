/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * Un �l�ment qui impl�mente cette interface autorise l'�lement � pouvoir etre
 * �dit�.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IEditableCapability {

	/**
	 * Retourne si l'�l�ment est �ditable ou pas.
	 */
	boolean isEditable();

	/**
	 * Sp�cifie si l'�l�ment est �ditable.
	 */
	void setEditable(boolean editable);
}
