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
 * Revision 1.5  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.4  2005/01/04 13:02:51  oeuillot
 * Amelioration des tables. (Ajout des tris, scrollbars ...)
 *
 * Revision 1.3  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 * Revision 1.2  2004/09/02 17:44:30  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.component.capability;

/**
 * Sp�cifie le type d'ordre g�r� par l'�l�ment.
 * 
 * @author Joel Merlin, Olivier Oeuillot
 * @version $Revision$ $Date$
 */
public interface IOrderCapability {

	/**
	 * Retourne l'ordre de l'�lement
	 */
	boolean isAscending();

	/**
	 * Sp�cifie l'ordre de l'�lement
	 */
	void setAscending(boolean ascending);

}