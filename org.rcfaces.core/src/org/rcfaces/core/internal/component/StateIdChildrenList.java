/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.4  2006/03/15 13:53:04  oeuillot
 * Stabilisation
 * Ajout des bundles pour le javascript
 * R�organisation de l'arborescence de GridData qui n'herite plus de UIData
 *
 * Revision 1.3  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.2  2004/08/06 14:18:12  oeuillot
 * *** empty log message ***
 * Revision 1.1 2004/08/06 14:03:57 jmerlin
 * Vedana Faces
 * 
 * Revision 1.2 2004/08/06 09:35:14 oeuillot *** empty log message *** Revision
 * 1.1 2004/08/04 16:28:11 oeuillot Premier jet !
 *  
 */
package org.rcfaces.core.internal.component;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class StateIdChildrenList implements List {
    private static final String REVISION = "$Revision$";

    private List list;

    private int stateId = 0;

    public final void setChildren(List list) {
        if (list == this.list) {
            return;
        }

        updateStateId();
        this.list = list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#size()
     */
    public final int size() {
        return list.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#isEmpty()
     */
    public final boolean isEmpty() {
        return list.isEmpty();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#contains(java.lang.Object)
     */
    public final boolean contains(Object o) {
        return list.contains(o);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#iterator()
     */
    public final Iterator iterator() {
        return new StateIdIterator(list.iterator());
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#toArray()
     */
    public final Object[] toArray() {
        return list.toArray();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#toArray(java.lang.Object[])
     */
    public final Object[] toArray(Object[] a) {
        return list.toArray(a);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#add(java.lang.Object)
     */
    public final boolean add(Object o) {
        if (list.add(o) == false) {
            return false;
        }

        updateStateId();
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#remove(java.lang.Object)
     */
    public final boolean remove(Object o) {
        if (list.remove(o) == false) {
            return false;
        }

        updateStateId();
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#containsAll(java.util.Collection)
     */
    public final boolean containsAll(Collection c) {
        return list.containsAll(c);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#addAll(java.util.Collection)
     */
    public final boolean addAll(Collection c) {
        if (list.addAll(c) == false) {
            return false;
        }

        updateStateId();
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#addAll(int, java.util.Collection)
     */
    public final boolean addAll(int index, Collection c) {
        if (list.addAll(index, c) == false) {
            return false;
        }

        updateStateId();
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#removeAll(java.util.Collection)
     */
    public final boolean removeAll(Collection c) {
        if (list.removeAll(c) == false) {
            return false;
        }

        updateStateId();
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#retainAll(java.util.Collection)
     */
    public final boolean retainAll(Collection c) {
        if (list.retainAll(c) == false) {
            return false;
        }

        updateStateId();
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#clear()
     */
    public final void clear() {
        list.clear();

        updateStateId();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#get(int)
     */
    public final Object get(int index) {
        return list.get(index);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#set(int, java.lang.Object)
     */
    public final Object set(int index, Object element) {
        Object object = list.set(index, element);

        updateStateId();

        return object;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#add(int, java.lang.Object)
     */
    public final void add(int index, Object element) {
        list.add(index, element);

        updateStateId();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#remove(int)
     */
    public final Object remove(int index) {
        Object object = list.remove(index);

        updateStateId();

        return object;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#indexOf(java.lang.Object)
     */
    public final int indexOf(Object o) {
        return list.indexOf(o);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#lastIndexOf(java.lang.Object)
     */
    public final int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#listIterator()
     */
    public final ListIterator listIterator() {
        return new StateIdListIterator(list.listIterator());
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#listIterator(int)
     */
    public final ListIterator listIterator(int index) {
        return new StateIdListIterator(list.listIterator(index));
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#subList(int, int)
     */
    public final List subList(int fromIndex, int toIndex) {
        return new StateIdSubList(list.subList(fromIndex, toIndex));
    }

    public final int getStateId() {
        return stateId;
    }

    protected void updateStateId() {
        this.stateId++;
    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    private class StateIdIterator implements Iterator {
        private static final String REVISION = "$Revision$";

        private final Iterator iterator;

        public StateIdIterator(Iterator iterator) {
            this.iterator = iterator;
        }

        public final boolean hasNext() {
            return iterator.hasNext();
        }

        public final Object next() {
            return iterator.next();
        }

        public final void remove() {
            iterator.remove();

            updateStateId();
        }
    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    private final class StateIdListIterator extends StateIdIterator implements
            ListIterator {
        private static final String REVISION = "$Revision$";

        private final ListIterator listIterator;

        public StateIdListIterator(ListIterator listIterator) {
            super(listIterator);

            this.listIterator = listIterator;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.util.ListIterator#hasPrevious()
         */
        public final boolean hasPrevious() {
            return listIterator.hasPrevious();
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.util.ListIterator#previous()
         */
        public final Object previous() {
            return listIterator.previous();
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.util.ListIterator#nextIndex()
         */
        public final int nextIndex() {
            return listIterator.nextIndex();
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.util.ListIterator#previousIndex()
         */
        public final int previousIndex() {
            return listIterator.previousIndex();
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.util.ListIterator#set(java.lang.Object)
         */
        public final void set(Object o) {
            listIterator.set(o);

            updateStateId();
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.util.ListIterator#add(java.lang.Object)
         */
        public final void add(Object o) {
            listIterator.add(o);

            updateStateId();
        }
    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    private final class StateIdSubList extends StateIdChildrenList {
        private static final String REVISION = "$Revision$";

        public StateIdSubList(List list) {
            setChildren(list);
        }

        protected void updateStateId() {
            StateIdChildrenList.this.updateStateId();
        }
    }
}