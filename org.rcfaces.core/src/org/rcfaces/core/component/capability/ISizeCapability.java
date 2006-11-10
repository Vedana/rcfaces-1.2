/*
 * $Id$
 *
 */
package org.rcfaces.core.component.capability;

/**
 * Un �l�ment qui impl�mente cette interface permet la sp�cification de sa
 * largeur et hauteur.
 * 
 * @author Joel Merlin
 * @version $Revision$ $Date$
 */
public interface ISizeCapability {

	/**
	 * Retourne la largeur si elle est connue.
	 */
	String getWidth();

	/**
	 * Sp�cifie la largeur.
	 */
	void setWidth(String width);

	/**
	 * Retourne la hauteur si elle est connue.
	 */
	String getHeight();

	/**
	 * Sp�cifie la hauteur.
	 */
	void setHeight(String height);
}
