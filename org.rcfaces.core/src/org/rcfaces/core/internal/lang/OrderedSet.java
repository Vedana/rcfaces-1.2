/*
 * $Id$
 */
package org.rcfaces.core.internal.lang;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class OrderedSet extends AbstractSet implements Serializable {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -8481215239333898818L;

    private final List order;

    public OrderedSet() {
        order = new ArrayList();
    }

    public OrderedSet(int size) {
        order = new ArrayList(size);
    }

    public OrderedSet(Collection collection) {
        this(collection.size());

        addAll(collection);
    }

    public Iterator iterator() {
        return order.iterator();
    }

    public boolean contains(Object o) {
        return order.contains(o);
    }

    public boolean containsAll(Collection c) {
        return order.containsAll(c);
    }

    public boolean add(Object o) {
        if (order.contains(o)) {
            return false;
        }

        return order.add(o);
    }

    public boolean remove(Object o) {
        return order.remove(o);
    }

    public boolean removeAll(Collection c) {
        return order.removeAll(c);
    }

    public int size() {
        return order.size();
    }

    public void clear() {
        order.clear();
    }

    public Object[] toArray() {
        return order.toArray();
    }

    public Object[] toArray(Object[] a) {
        return order.toArray(a);
    }

    public int hashCode() {
        return order.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final OrderedSet other = (OrderedSet) obj;
        if (order == null) {
            if (other.order != null) {
                return false;
            }

        } else if (!order.equals(other.order)) {
            return false;
        }

        return true;
    }

    public boolean retainAll(Collection c) {
        return order.retainAll(c);
    }

    public String toString() {
        return order.toString();
    }

}
