/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.5  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.4  2004/09/13 08:34:27  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.internal.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.component.UISelectItem;

import org.rcfaces.core.component.ComboComponent;
import org.rcfaces.core.component.capability.IMultipleSelectCapability;
import org.rcfaces.core.component.iterator.ISelectItemIterator;
import org.rcfaces.core.internal.util.ComponentIterators;
import org.rcfaces.core.internal.util.IncompatibleValueException;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ListTools {
    private static final String REVISION = "$Revision$";

    private static final boolean USE_ARRAY_VALUE_FOR_Combo = false;

    private static final ISelectItemIterator EMPTY_COMPONENT_ITERATOR = new SelectItemIteratorWrapper(
            Collections.EMPTY_LIST);

    public static ISelectItemIterator listItems(ComboComponent component) {

        List list = ComponentIterators.list(component, UISelectItem.class);
        if (list.isEmpty()) {
            return EMPTY_COMPONENT_ITERATOR;
        }

        return new SelectItemIteratorWrapper(list);
    }

    public static ISelectItemIterator listAllSelectedItems(
            ComboComponent component) {

        Object value = component.getValue();
        if (value == null) {
            return EMPTY_COMPONENT_ITERATOR;
        }

        if (value instanceof Object[]) {
            // C'est peut être le cas de la listBox

            List l = listAllSelectedItems(component, (Object[]) value);
            if (l == null || l.isEmpty()) {
                return EMPTY_COMPONENT_ITERATOR;
            }

            return new SelectItemListIterator(l);
        }

        // C'est peut être le cas de la Combo

        UISelectItem si = listAllSelectedItems(component, value);
        if (si == null) {
            return EMPTY_COMPONENT_ITERATOR;
        }

        return new SelectItemArrayIterator(si);
    }

    private static UISelectItem listAllSelectedItems(ComboComponent component,
            Object value) {

        return getFirstSelectedItem(component, value);
    }

    private static List listAllSelectedItems(ComboComponent component,
            Object values[]) {
        if (values == null || values.length < 1) {
            // Aucun ID sélectionné !
            return null;
        }

        ISelectItemIterator si = listItems(component);
        if (si.hasNext() == false) {
            // Aucun SelectItem !
            return null;
        }

        Set valuesSet = new HashSet(Arrays.asList(values));

        List l = null;
        for (; si.hasNext();) {
            UISelectItem selectItem = si.next();

            Object value = selectItem.getItemValue();
            if (value == null) {
                // On ignore les selectItems qui n'ont pas d'ID : mais est-ce
                // possible ?
                continue;
            }

            if (valuesSet.contains(value) == false) {
                // Pas dans la liste des Ids sélectionnés ...
                continue;
            }

            if (l == null) {
                int n = Math.min(values.length, si.count());

                l = new ArrayList(n);
            }

            l.add(selectItem);
        }

        if (l == null) {
            return null;
        }

        return l;

    }

    public static UISelectItem getFirstSelectedItem(ComboComponent component) {
        Object value = component.getValue();

        if (value == null) {
            return null;
        }

        if (value instanceof Object[]) {
            return getFirstSelectedItem(component, (Object[]) value);
        }

        if (value instanceof String) {
            return getFirstSelectedItem(component, value);
        }

        throw new IncompatibleValueException(value, "String, String[]");
    }

    private static UISelectItem getFirstSelectedItem(ComboComponent component,
            Object value) {
        ISelectItemIterator si = listItems(component);
        for (; si.hasNext();) {
            UISelectItem item = si.next();

            if (value.equals(item.getItemValue())) {
                return item;
            }
        }

        return null;
    }

    private static UISelectItem getFirstSelectedItem(ComboComponent component,
            Object values[]) {
        if (values == null || values.length == 0) {
            return null;
        }

        ISelectItemIterator si = listItems(component);
        if (si.hasNext() == false) {
            return null;
        }

        Set valuesSet = new HashSet(Arrays.asList(values));

        for (; si.hasNext();) {
            UISelectItem selectItem = si.next();

            Object value = selectItem.getItemValue();
            if (value == null) {
                continue;
            }

            if (valuesSet.contains(value) == false) {
                continue;
            }

            return selectItem;
        }

        return null;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static final class SelectItemIteratorWrapper extends
            ComponentIterators.ComponentListIterator implements
            ISelectItemIterator {
        private static final String REVISION = "$Revision$";

        public SelectItemIteratorWrapper(List list) {
            super(list);
        }

        public final UISelectItem next() {
            return (UISelectItem) nextComponent();
        }

        public UISelectItem[] toArray() {
            return (UISelectItem[]) toArray(new UISelectItem[count()]);
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static final class SelectItemListIterator extends
            ComponentIterators.ComponentListIterator implements
            ISelectItemIterator {
        private static final String REVISION = "$Revision$";

        public SelectItemListIterator(List list) {
            super(list);
        }

        public final UISelectItem next() {
            return (UISelectItem) nextComponent();
        }

        public UISelectItem[] toArray() {
            return (UISelectItem[]) toArray(new UISelectItem[count()]);
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static final class SelectItemArrayIterator extends
            ComponentIterators.ComponentArrayIterator implements
            ISelectItemIterator {
        private static final String REVISION = "$Revision$";

        public SelectItemArrayIterator(UISelectItem selectItem) {
            super(selectItem);
        }

        public final UISelectItem next() {
            return (UISelectItem) nextComponent();
        }

        public UISelectItem[] toArray() {
            return (UISelectItem[]) toArray(new UISelectItem[count()]);
        }
    }

    public static int findIndexOfFirstListItemSelected(ComboComponent component) {

        Object value = component.getValue();

        if (value == null) {
            return -1;
        }

        if (value instanceof Object[]) {
            return findIndexOfFirstListItemSelected(component, (Object[]) value);
        }

        return findIndexOfFirstListItemSelected(component, value);
    }

    private static int findIndexOfFirstListItemSelected(
            ComboComponent component, Object value) {

        ISelectItemIterator si = listItems(component);
        if (si.hasNext() == false) {
            return -1;
        }

        int index = 0;
        for (; si.hasNext(); index++) {
            UISelectItem selectItem = si.next();

            Object cid = selectItem.getValue();
            if (cid == null) {
                continue;
            }

            if (value.equals(cid) == false) {
                continue;
            }

            return index;
        }

        return -1;

    }

    private static int findIndexOfFirstListItemSelected(
            ComboComponent component, Object values[]) {

        if (values.length == 0) {
            return -1;
        }

        ISelectItemIterator si = listItems(component);
        if (si.hasNext() == false) {
            return -1;
        }

        Set valuesSet = new HashSet(Arrays.asList(values));

        int index = 0;
        for (; si.hasNext(); index++) {
            UISelectItem selectItem = si.next();

            Object value = selectItem.getItemValue();
            if (value == null) {
                continue;
            }

            if (valuesSet.contains(value) == false) {
                continue;
            }

            return index;
        }

        return -1;
    }

    public static void deselect(ComboComponent component, UISelectItem item) {
        Object value = component.getValue();

        if (value == null) {
            return;
        }

        if (value instanceof String) {
            deselect(component, item, (String) value);
            return;
        }

        if (value instanceof String[]) {
            deselect(component, item, (String[]) value);
            return;
        }

        throw new IncompatibleValueException(value, "String, String[]");
    }

    private static void deselect(ComboComponent component, UISelectItem item,
            String id) {
        if (id.equals(item.getId()) == false) {
            return;
        }

        component.setValue(null);
    }

    private static void deselect(ComboComponent component, UISelectItem item,
            String ids[]) {

        if (ids.length == 1) {
            deselect(component, item, ids[0]);
            return;
        }

        Set idsSet = new HashSet(Arrays.asList(ids));
        if (idsSet.remove(item.getId()) == false) {
            // pas de remove ? donc pas de changement !
            return;
        }

        component.setValue(idsSet.toArray(new String[idsSet.size()]));
    }

    public static void selectAll(ComboComponent component) {
        ISelectItemIterator si = listItems(component);
        if (si.hasNext() == false) {
            component.setValue(null);
            return;
        }

        if ((component instanceof IMultipleSelectCapability) == false) {
            // Une selection simple , on considere la value comme une simple
            // String (pas un tableau)

            for (; si.hasNext();) {
                // On recherche le premier selectItem valide !
                UISelectItem selectItem = si.next();

                String id = selectItem.getId();
                if (id == null) {
                    continue;
                }

                component.setValue(id);
                return;
            }

            // On a rien trouv� !
            component.setValue(null);
            return;
        }

        List l = new ArrayList(si.count());
        for (; si.hasNext();) {
            UISelectItem selectItem = si.next();

            String id = selectItem.getId();
            if (id == null) {
                continue;
            }

            l.add(id);
        }

        component.setValue(l.toArray(new String[l.size()]));
    }
/*
    public static int[] listIndexOfAllSelectedItems(ListComponent component) {
        return null;
    }
*/
    /**
     * @param item
     */
    public static void select(ComboComponent ComboComponent, UISelectItem item) {
        if ((ComboComponent instanceof IMultipleSelectCapability) == false) {
            // Selection simple, c'ets le cas d'une Combo

            if (USE_ARRAY_VALUE_FOR_Combo) {
                ComboComponent.setValue(new Object[] { item.getItemValue() });
                return;
            }

            ComboComponent.setValue(item.getItemValue());
            return;
        }

        Object value = ComboComponent.getValue();
        if (value == null) {
            ComboComponent.setValue(new Object[] { item.getItemValue() });
            return;
        }

        Set ids;
        if (value instanceof Object[]) {
            Object s[] = (Object[]) value;

            ids = new HashSet(Arrays.asList(s));
        } else {
            ids = new HashSet(2);
            ids.add(value);
        }

        ids.add(item.getItemValue());

        ComboComponent.setValue(ids.toArray());
    }

    public static Set listAllSelectedItemsSet(ComboComponent component) {

        Object value = component.getValue();
        if (value == null) {
            return Collections.EMPTY_SET;
        }

        if (value instanceof Object[]) {
            // C'est peut �tre le cas de la listBox

            List l = listAllSelectedItems(component, (Object[]) value);
            if (l == null) {
                return Collections.EMPTY_SET;
            }
            return new HashSet(l);
        }

        UISelectItem us = listAllSelectedItems(component, value);
        if (us == null) {
            return Collections.EMPTY_SET;
        }
        Set set = new HashSet(1);
        set.add(us);

        return set;
    }
}