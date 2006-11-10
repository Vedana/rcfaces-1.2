/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * Un �lement qui impl�mente cette interface peut posseder un ou plusieurs
 * ascenseurs afin de visualiser des informations cach�es.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IScrollableCapability {

	/**
	 * Retourne la position horizontale des ascenseurs de l'�l�ment.
	 */
	String getHorizontalScrollPosition();

	/**
	 * Sp�cifie la position horizontale des ascenseurs de l'�l�ment.
	 */
	void setHorizontalScrollPosition(String position);

	/**
	 * Retourne la position verticale des ascenseurs de l'�l�ment.
	 */
	String getVerticalScrollPosition();

	/**
	 * Sp�cifie la position verticale des ascenseurs de l'�l�ment.
	 */
	void setVerticalScrollPosition(String position);
}
