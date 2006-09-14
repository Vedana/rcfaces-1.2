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
 * Revision 1.2  2004/08/06 09:35:14  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/04 16:28:11  oeuillot
 * Premier jet !
 *
 */
package org.rcfaces.core.component.capability;

/**
 * Un �l�ment qui implemente ExpandCapability permet d'afficher ou non ses
 * enfants.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IExpandCapability {

	/**
	 * Retourne si l'�l�ment affiche ses enfants ou pas.
	 */
	boolean isExpanded();

	/**
	 * Sp�cifie si l'�l�ment affiche ses enfants ou pas.
	 */
	void setExpanded(boolean expand);
}