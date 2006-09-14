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
 * Revision 1.3  2006/03/23 19:12:39  oeuillot
 * Ajout des marges
 * Ajout des processors
 * Amelioration des menus
 *
 * Revision 1.2  2006/01/03 15:21:39  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.1  2005/03/18 14:42:50  oeuillot
 * Support de la table des symbols pour le javascript compress�
 * Menu du style XP et pas Office !
 *
 */
package org.rcfaces.core.component.capability;

import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.component.iterator.IMenuIterator;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IMenuCapability {
    IMenuComponent getMenu();

    IMenuComponent getMenu(String menuId);
    
    IMenuIterator listMenus();
}
