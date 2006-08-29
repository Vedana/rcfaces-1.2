/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2005/08/24 15:39:33  oeuillot
 * Colonne retaillable + debug de composants
 *
 */
package org.rcfaces.core.component.capability;

/**
 * L'�l�ment qui impl�mente cette interface permet de d�finir s'il est possible � l'utilisateur de modifier
 * la taille du composant.
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
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
