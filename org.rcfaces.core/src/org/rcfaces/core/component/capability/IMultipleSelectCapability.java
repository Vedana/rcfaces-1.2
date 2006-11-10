/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * Un �l�ment qui impl�mente cette interface poss�de deux modes de selection :
 * une seule entr�e s�lectionn�e, ou plusieurs ...
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
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
