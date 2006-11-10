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
    IMenuComponent getMenu();

    IMenuComponent getMenu(String menuId);
    
    IMenuIterator listMenus();
}
