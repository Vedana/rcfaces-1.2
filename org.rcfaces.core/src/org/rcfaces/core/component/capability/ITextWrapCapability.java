/*
 * $Id$
 * 
 */

package org.rcfaces.core.component.capability;

/**
 * Un �l�ment qui impl�mente cette interface permet de specifier le mode de
 * retour � la ligne du texte dans celui-ci.
 * 
 * @author Jo�l Merlin
 * @version $Revision$ $Date$
 */
public interface ITextWrapCapability {

	/**
	 * Retourne le mode de retour � la ligne du texte dans l'�l�ment.
	 */
	String getTextWrapMode();

	/**
	 * Sp�cifie le mode de retour � la ligne du texte dans l'�l�ment.
	 */
	void setTextWrapMode(String textWrapMode);
}
