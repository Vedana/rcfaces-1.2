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
public interface ISelected3StatesCapability {

	String NOT_SELECTED_STATE = "notSelected";

	String SELECTED_STATE = "selected";

	String UNDETERMINATED_STATE = "undeterminated";

	/**
	 * Retourne si l'�l�ment est s�lectionn� ou pas.
	 */
	String getSelectedState();

	/**
	 * Sp�cifie si l'�l�ment est s�lectionn� ou pas.
	 */
	void setSelectedState(String selectedState);

	boolean isSelected();

	boolean isUndeterminated();
}
