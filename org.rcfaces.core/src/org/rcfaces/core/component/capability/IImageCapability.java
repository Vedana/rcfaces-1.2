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
public interface IImageCapability {

	/**
	 * Retourne l'URL de l'image affich�e.
	 */
	String getImageURL();

	/**
	 * Sp�cifie l'URL de l'image qui doit etre affich�e.
	 */
	void setImageURL(String url);
}