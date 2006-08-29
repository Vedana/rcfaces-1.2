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