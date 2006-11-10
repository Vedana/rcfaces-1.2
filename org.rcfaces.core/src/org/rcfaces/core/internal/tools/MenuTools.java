/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;

import org.rcfaces.core.component.AbstractMenuComponent;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.component.MenuComponent;
import org.rcfaces.core.component.MenuItemComponent;
import org.rcfaces.core.component.iterator.IMenuItemIterator;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.internal.manager.IContainerManager;
import org.rcfaces.core.internal.util.ComponentIterators;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class MenuTools {
    private static final String REVISION = "$Revision$";

    private static final IMenuItemIterator EMPTY_MENU_ITEM_ITERATOR = new MenuItemListIterator(
            Collections.EMPTY_LIST);

    private static final IMenuIterator EMPTY_MENU_ITERATOR = new MenuListIterator(
            Collections.EMPTY_LIST);

    private static final String MENU_FACET = "popup";

    public static final IMenuItemIterator listMenuItems(
            AbstractMenuComponent menu) {
        return listMenuItems((IContainerManager) menu);
    }

    public static final IMenuItemIterator listMenuItems(
            MenuItemComponent menuItem) {
        return listMenuItems((IContainerManager) menuItem);
    }

    public static final IMenuItemIterator listMenuItems(MenuComponent menu) {
        return listMenuItems((IContainerManager) menu);
    }

    private static IMenuItemIterator listMenuItems(IContainerManager manager) {

        List list = ComponentIterators.list(manager, UISelectItem.class);
        if (list.isEmpty()) {
            return EMPTY_MENU_ITEM_ITERATOR;
        }

        return new MenuItemListIterator(list);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static final class MenuItemListIterator extends
            ComponentIterators.ComponentListIterator implements
            IMenuItemIterator {
        private static final String REVISION = "$Revision$";

        public MenuItemListIterator(List list) {
            super(list);
        }

        public final MenuItemComponent next() {
            return (MenuItemComponent) nextComponent();
        }

        public MenuItemComponent[] toArray() {
            return (MenuItemComponent[]) toArray(new MenuItemComponent[count()]);
        }
    }

    public static IMenuComponent getMenu(MenuItemComponent item) {
        for (;;) {
            UIComponent component = item.getParent();
            if (component == null) {
                return null;
            }

            if (component instanceof MenuComponent) {
                return (MenuComponent) component;
            }

            if ((component instanceof MenuItemComponent) == false) {
                return null;
            }

            item = (MenuItemComponent) component;
        }
    }

    public static IMenuComponent getMenu(UIComponent component) {
        return searchMenu(component, null);
    }

    public static IMenuComponent getMenu(UIComponent component, String menuId) {
        return searchMenu(component, menuId);
    }

    private static IMenuComponent searchMenu(UIComponent container,
            String menuId) {
        List children = container.getChildren();
        if (children.isEmpty()) {
            return null;
        }

        for (Iterator it = children.iterator(); it.hasNext();) {
            UIComponent child = (UIComponent) it.next();

            if ((child instanceof IMenuComponent) == false) {
                continue;
            }
            if (menuId != null && (child instanceof MenuComponent)) {
                MenuComponent menuComponent = (MenuComponent) child;

                if (menuId.equals(menuComponent.getMenuId()) == false) {
                    continue;
                }
            }

            return (IMenuComponent) child;
        }

        return null;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static final class MenuListIterator extends
            ComponentIterators.ComponentListIterator implements IMenuIterator {
        private static final String REVISION = "$Revision$";

        public MenuListIterator(List list) {
            super(list);
        }

        public final MenuComponent next() {
            return (MenuComponent) nextComponent();
        }

        public MenuComponent[] toArray() {
            return (MenuComponent[]) toArray(new MenuComponent[count()]);
        }
    }

    public static IMenuIterator listMenus(IContainerManager component) {
        Map facets = ((UIComponent) component).getFacets();
        if (facets != null && facets.isEmpty() == false) {
            List popups = null;
            int i = 0;
            for (Iterator it = facets.values().iterator(); it.hasNext();) {
                UIComponent child = (UIComponent) it.next();

                if ((child instanceof MenuComponent) == false) {
                    continue;
                }

                if (popups == null) {
                    popups = new ArrayList(facets.size() - i);
                }
                popups.add(child);
            }

            if (popups == null) {
                return EMPTY_MENU_ITERATOR;
            }

            return new MenuListIterator(popups);
        }

        List list = ComponentIterators.list(component, MenuComponent.class);
        if (list.isEmpty()) {
            return EMPTY_MENU_ITERATOR;
        }

        return new MenuListIterator(list);
    }
}
