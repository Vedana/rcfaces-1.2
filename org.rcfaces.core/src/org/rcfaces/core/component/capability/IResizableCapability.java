/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * L'�l�ment qui impl�mente cette interface permet de d�finir s'il est possible � l'utilisateur de modifier
 * la taille du composant.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IResizableCapability {
	/**
	 * Retourne <code>true</code> si la taille du composant peut �tre modifi�e. 
	 */
	boolean isResizable();
	
	/**
	 * Sp�cifie si la taille du composant peut �tre modifi�e. 
	 */
	void setResizable(boolean resizable);
}
