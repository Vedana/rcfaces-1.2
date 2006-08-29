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
 * Revision 1.1  2004/08/04 16:28:11  oeuillot
 * Premier jet !
 *
 */
package org.rcfaces.core.component.capability;

/**
 * Un �l�ment qui impl�mente cette interface poss�de deux modes de selection :
 * une seule entr�e s�lectionn�e, ou plusieurs ...
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IMultipleSelectCapability {

	/**
	 * Retourne si l'�l�ment autorise la s�lection de plusieurs entr�e ou pas.
	 */
	boolean isMultipleSelect();

	/**
	 * Sp�cifie le mode de s�lection de l'�lement. (
	 * 
	 * <pre>
	 * false
	 * </pre>
	 * 
	 * pour une seule entr�e � la fois,
	 * 
	 * <pre>
	 * true
	 * </pre>
	 * 
	 * pour plusieurs entr�es � la fois)
	 */
	void setMultipleSelect(boolean multipleSelect);
}