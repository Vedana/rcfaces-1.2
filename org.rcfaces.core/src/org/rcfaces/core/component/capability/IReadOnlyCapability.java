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
 * Revision 1.2  2004/09/24 14:01:36  oeuillot
 * *** empty log message ***
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