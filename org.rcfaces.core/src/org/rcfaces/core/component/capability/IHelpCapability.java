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
 * Permet d'associer plusieurs formes d'aide � un �l�ment.
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IHelpCapability extends IToolTipCapability {

	/**
	 * Retourne le message associ� � l'aide.
	 */
	String getHelpMessage();

	/**
	 * Sp�cifie le message associ� � l'aide.
	 */
	void setHelpMessage(String message);

	/**
	 * Retourne une URL associ�e � l'aide.
	 */
	String getHelpURL();

	/**
	 * Sp�cifie l'URL associ�e � l'aide.
	 */
	void setHelpURL(String url);
}