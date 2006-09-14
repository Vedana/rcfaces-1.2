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
 * Revision 1.3  2004/08/06 09:35:14  oeuillot
 * *** empty log message ***
 *
 * Revision 1.2  2004/08/05 09:49:50  oeuillot
 * Retire la gestion des Resources
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