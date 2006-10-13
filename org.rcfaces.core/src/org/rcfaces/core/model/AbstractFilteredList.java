/*
 * $Id$
 */
package org.rcfaces.core.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import javax.faces.model.SelectItem;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractFilteredList extends AbstractFilteredCollection
        implements List {
    private static final String REVISION = "$Revision$";

    public static final List EMPTY_LIST;
    static {
        EMPTY_LIST = new AbstractFilteredList(Collections.EMPTY_LIST) {
            private static final String REVISION = "$Revision$";

            protected boolean accept(IFilterProperties filter,
                    SelectItem selectItem) {
                return false;
            }
        };
    }

    public AbstractFilteredList() {
        super(new ArrayList());
    }

    public AbstractFilteredList(List list) {
        super(list);
    }

    public AbstractFilteredList(SelectItem selectItem[]) {
        super(selectItem);
    }

    public Object get(int index) {
        return ((List) collection).get(index);
    }

    public Object remove(int index) {
        return ((List) collection).remove(index);
    }

    public void add(int index, Object element) {
        ((List) collection).add(index, element);
    }

    public int indexOf(Object o) {
        return ((List) collection).indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return ((List) collection).lastIndexOf(o);
    }

    public boolean addAll(int index, Collection c) {
        return ((List) collection).addAll(index, c);
    }

    public List subList(int fromIndex, int toIndex) {
        return ((List) collection).subList(fromIndex, toIndex);
    }

    public ListIterator listIterator() {
        return ((List) collection).listIterator();
    }

    public ListIterator listIterator(int index) {
        return ((List) collection).listIterator(index);
    }

    public Object set(int index, Object element) {
        return ((List) collection).set(index, element);
    }

}
