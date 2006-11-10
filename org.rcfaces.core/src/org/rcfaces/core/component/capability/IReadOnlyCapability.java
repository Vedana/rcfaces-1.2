/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * Un �l�ment qui impl�mente cette interface, peut etre mis dans un �tat ou
 * l'utilisateur ne peut plus agir sur l'�tat de cet �l�ment.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IReadOnlyCapability {

	/**
	 * Retourne si l'�l�ment est dans un �tat verouill� ou pas.
	 */
	boolean isReadOnly();

	/**
	 * Sp�cifie si l'�tat de l'�l�ment est verouill� ou pas.
	 */
	void setReadOnly(boolean readOnly);
}
