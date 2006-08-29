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
 * Revision 1.3  2004/08/06 09:35:14  oeuillot
 * *** empty log message ***
 *
 * Revision 1.2  2004/08/05 09:49:50  oeuillot
 * Retire la gestion des Resources
 *
 */
package org.rcfaces.core.component.capability;

/**
 * Un �lement qui impl�mente cette interface peut posseder un ou plusieurs
 * ascenseurs afin de visualiser des informations cach�es.
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
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