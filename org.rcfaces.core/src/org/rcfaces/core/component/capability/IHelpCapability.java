/*
 * $Id$
 * 
 */

package org.rcfaces.core.component.capability;

/**
 * Permet d'associer plusieurs formes d'aide � un �l�ment.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
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
