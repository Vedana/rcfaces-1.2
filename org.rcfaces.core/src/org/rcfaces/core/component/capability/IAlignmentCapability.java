/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * Un élément qui impl�mente cette interface permet de specifier l'alignement
 * graphique.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IAlignmentCapability {

	/**
	 * Retourne le type d'alignement dans l'élément.
	 */
	String getAlignment();

	/**
	 * Sp�cifie le type d'alignement dans l'élément.
	 */
	void setAlignment(String textAlignment);
}