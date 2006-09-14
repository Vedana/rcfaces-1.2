/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
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
 * Un �l�ment qui impl�mente cette interface permet de specifier l'alignement du
 * texte dans celui-ci.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ITextAlignmentCapability {

	/**
	 * Retourne le type d'alignement du texte dans l'�l�ment.
	 */
	String getTextAlignment();

	/**
	 * Sp�cifie le type d'alignement du texte dans l'�lement.
	 */
	void setTextAlignment(String textAlignment);
}