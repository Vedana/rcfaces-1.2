/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * L'element peut etre activ� par un raccourci clavier.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IAccessKeyCapability {
	/**
	 * Retourne la combinaison de touches pour activer cet element.
	 */
	String getAccessKey();

	/**
	 * Specifie la combinaison de touches pour activer cet element.
	 */
	void setAccessKey(String key);
}