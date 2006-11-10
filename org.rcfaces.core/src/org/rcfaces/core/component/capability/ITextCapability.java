/*
 * $Id$
 * 
 */

package org.rcfaces.core.component.capability;

/**
 * Un �l�ment qui implemente cet interface permet � l'utilisateur de saisir un
 * texte.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ITextCapability {
	/**
	 * Sp�cifie le texte affich� par l'�l�ment.
	 */
	void setText(String text);

	/**
	 * Retourne le texte affich� par l'�l�ment.
	 */
	String getText();
}
