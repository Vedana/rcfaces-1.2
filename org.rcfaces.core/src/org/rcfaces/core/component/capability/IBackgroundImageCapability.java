/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/10/04 12:31:59  oeuillot
 * Stabilisation
 *
 * Revision 1.2  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2004/09/24 14:01:36  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.component.capability;

/**
 * Description de l'image de fond (si elle existe)
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IBackgroundImageCapability {
	String getBackgroundImageURL();

	void setBackgroundImageURL(String backgroundImageURL);

	/**
	 * Retourne la position horizontale en % de l'image de fond.
	 */
	String getBackgroundImageHorizontalPosition();

	/**
	 * Sp�cifie la position horizontale en % de l'image de fond.
	 */
	void setBackgroundImageHorizontalPosition(String position);

	/**
	 * Retourne la position verticale en % de l'image de fond.
	 */
    String getBackgroundImageVerticalPosition();

	/**
	 * Sp�cifie la position verticale en % de l'image de fond.
	 */
	void setBackgroundImageVerticalPosition(String position);

	/**
	 * Retourne <code>true</code> si l'image de fond est r�pet�e sur l'axe
	 * horizontal.
	 * 
	 * @return <code>true</code> par d�faut.
	 */
	boolean isBackgroundImageHorizontalRepeat();

	/**
	 * Sp�cifie si l'image de fond est r�pet�e sur l'axe horizontal.
	 */
	void setBackgroundImageHorizontalRepeat(boolean repeat);

	/**
	 * Retourne <code>true</code> si l'image de fond est r�pet�e sur l'axe
	 * vertical.
	 * 
	 * @return <code>true</code> par d�faut.
	 */
	boolean isBackgroundImageVerticalRepeat();

	/**
	 * Sp�cifie si l'image de fond est r�pet�e sur l'axe vertical.
	 */
	void setBackgroundImageVerticalRepeat(boolean repeat);
}