/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.iterator;

import org.rcfaces.core.component.MenuItemComponent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IMenuItemIterator extends IComponentIterator {

    MenuItemComponent next();

    MenuItemComponent[] toArray();
}
