/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2004/08/06 14:03:56  jmerlin
 * Vedana Faces
 *
 * Revision 1.2  2004/08/06 09:35:14  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/05 16:53:26  oeuillot
 * Integration de l'INPUT
 *
 * Revision 1.2  2004/08/05 09:49:50  oeuillot
 * Retire la gestion des Resources
 *
 */
package org.rcfaces.core.component.capability;

/**
 * Cette interface sp�cifie le status de s�lection de l'�l�ment.
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
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