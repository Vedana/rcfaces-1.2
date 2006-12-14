/*
 * $Id$
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
    
	/**
	 * Returns the top-most menu associated to the component.
	 * @return top-most menu component
	 */
	IMenuComponent getMenu();

	/**
	 * Returns the menu identified by its id from the menus associated to the component.
	 * @param menuId Id for a particular menu
	 * @return menu component
	 */
    IMenuComponent getMenu(String menuId);
    
    /**
     * Returns a list of all the menus associated to the component.
     * @return Menu iterator
     */
    IMenuIterator listMenus();
}
