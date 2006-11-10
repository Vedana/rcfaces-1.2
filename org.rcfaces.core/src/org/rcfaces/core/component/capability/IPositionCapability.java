/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * Un �l�ment qui impl�mente cette interface permet son positionnement en X,Y et
 * la sp�cification de sa largeur et hauteur.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
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
