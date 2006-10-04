/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/10/04 12:31:58  oeuillot
 * Stabilisation
 *
 * Revision 1.2  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:12  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/01/31 16:04:23  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.1  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 */
package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.ICheckEventCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.iterator.IMenuItemIterator;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IMenuComponent extends ICheckEventCapability,
        ISelectionEventCapability {

    /*
    int getItemImageWidth();

    void setItemImageWidth(int width);

    int getItemImageHeight();

    void setItemImageHeight(int height);
*/
    IMenuItemIterator listMenuItems();

    boolean isRemoveAllWhenShown();

    void setRemoveAllWhenShown(boolean removeAllWhenShown);
}
