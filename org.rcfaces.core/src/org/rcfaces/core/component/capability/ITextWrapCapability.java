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
 * Revision 1.3  2004/08/06 09:35:14  oeuillot
 * *** empty log message ***
 *
 * Revision 1.2  2004/08/05 09:49:50  oeuillot
 * Retire la gestion des Resources
 *
 */

package org.rcfaces.core.component.capability;

/**
 * Un �l�ment qui impl�mente cette interface permet de specifier le mode de
 * retour � la ligne du texte dans celui-ci.
 * 
 * @author Jo�l Merlin
 * @version $Revision$
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