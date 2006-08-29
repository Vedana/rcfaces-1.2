/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2005/02/18 14:46:08  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 */
package org.rcfaces.core.component.capability;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IValueLockedCapability {
	
	/**
	 * @TODO
	 */
	boolean isValueLocked();

	/**
	 * @TODO
	 */
	void setValueLocked(boolean lock);

}
