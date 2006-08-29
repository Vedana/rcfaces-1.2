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
 * Un �l�ment qui impl�mente cette interface permet son positionnement en X,Y et
 * la sp�cification de sa largeur et hauteur.
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IPositionCapability {

	/**
	 * Retourne la position X si elle est connue.
	 */
	String getX();

	/**
	 * Sp�cifie la position X de l'�l�ment.
	 */
	void setX(String x);

	/**
	 * Retourne la position Y si elle est connue.
	 */
	String getY();

	/**
	 * Sp�cifie la position Y de l'�l�ment.
	 */
	void setY(String y);
}