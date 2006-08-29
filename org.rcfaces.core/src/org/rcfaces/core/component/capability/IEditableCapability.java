/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2004/09/13 08:34:26  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.component.capability;

/**
 * Un �l�ment qui impl�mente cette interface autorise l'�lement � pouvoir etre
 * �dit�.
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
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