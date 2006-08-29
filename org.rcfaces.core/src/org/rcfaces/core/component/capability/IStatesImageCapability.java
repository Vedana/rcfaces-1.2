/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2004/09/24 14:01:36  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.component.capability;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IStatesImageCapability {
	/**
	 * Retourne l'URL de l'image qui doit etre affich�e quand la souris passe
	 * sur l'�lement.
	 */
	String getHoverImageURL();

	/**
	 * Sp�cifie l'URL de l'image qui doit etre affich�e quand la souris passe
	 * sur l'�lement.
	 */
	void setHoverImageURL(String url);

	/**
	 * Retourne l'URL de l'image qui doit etre affich�e quand l'�lement est
	 * s�lectionn�.
	 */
	String getSelectedImageURL();

	/**
	 * Sp�cifie l'URL de l'image qui doit etre affich�e quand l'�lement est
	 * s�lectionn�.
	 */
	void setSelectedImageURL(String url);

	/**
	 * Retourne l'URL de l'image qui doit etre affich�e quand l'�lement est
	 * d�sactiv�.
	 */
	String getDisabledImageURL();

	/**
	 * Sp�cifie l'URL de l'image qui doit etre affich�e quand l'�lement est
	 * d�sactiv�.
	 */
	void setDisabledImageURL(String url);

}