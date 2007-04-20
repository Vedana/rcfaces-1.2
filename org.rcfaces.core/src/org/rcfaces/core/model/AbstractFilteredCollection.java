/*
 * $Id$
 */
package org.rcfaces.core.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.model.SelectItem;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractFilteredCollection implements Collection,
        IFiltredCollection, IFiltredCollection2 {
    private static final String REVISION = "$Revision$";

    public static final Collection EMPTY_COLLECTION = new AbstractFilteredCollection() {
        private static final String REVISION = "$Revision$";

        protected boolean accept(IFilterProperties filter, SelectItem selectItem) {
            return false;
        }
    };

    public static final IFiltredCollection EMPTY_FILTERED_COLLECTION = (IFiltredCollection) EMPTY_COLLECTION;

    protected final Collection collection;

    public AbstractFilteredCollection() {
        this(Collections.EMPTY_LIST);
    }

    public AbstractFilteredCollection(SelectItem selecItems[]) {
        this(Arrays.asList(selecItems));
    }

    public AbstractFilteredCollection(Collection collection) {
        this.collection = collection;
    }

    /**
     * 
     * @param filter
     * @param selectItem
     * @return <code>true</code> if the selectItem is sent to the client.
     */
    protected abstract boolean accept(IFilterProperties filter,
            SelectItem selectItem);

    public int size() {
        return collection.size();
    }

    public boolean add(Object o) {
        return collection.add(o);
    }

    public boolean addAll(Collection c) {
        return collection.addAll(c);
    }

    public void clear() {
        collection.clear();
    }

    public boolean contains(Object o) {
        return collection.contains(o);
    }

    public boolean containsAll(Collection c) {
        return collection.containsAll(c);
    }

    public boolean isEmpty() {
        return collection.isEmpty();
    }

    public Iterator iterator() {
        return collection.iterator();
    }

    public boolean remove(Object o) {
        return collection.remove(o);
    }

    public boolean removeAll(Collection c) {
        return collection.removeAll(c);
    }

    public boolean retainAll(Collection c) {
        return collection.retainAll(c);
    }

    public Object[] toArray() {
        return collection.toArray();
    }

    public Object[] toArray(Object[] a) {
        return collection.toArray(a);
    }

    public Iterator iterator(IFilterProperties filterProperties,
            int maxNumberResult) {
        return new FilteredIterator(filterProperties, maxNumberResult);
    }

    public Iterator iterator(UIComponent component,
            IFilterProperties filterProperties, int maxNumberResult) {
        return new FilteredIterator(component, filterProperties,
                maxNumberResult);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected class FilteredIterator implements IFiltredIterator {
        private static final String REVISION = "$Revision$";

        private final IFilterProperties filterProperties;

        private final UIComponent component;

        private int maxResultNumber;

        private boolean limitTested = false;

        private int currentIndex = 0;

        private int size;

        private Iterator iterator;

        private SelectItem currentSelectItem;

        public FilteredIterator(IFilterProperties filterProperties) {
            this(null, filterProperties, NO_MAXIMUM_RESULT_NUMBER);
        }

        public FilteredIterator(IFilterProperties filterProperties,
                int maxResultNumber) {
            this(null, filterProperties, maxResultNumber);
        }

        public FilteredIterator(UIComponent component,
                IFilterProperties filterProperties, int maxResultNumber) {

            this.component = component;
            this.filterProperties = filterProperties;
            this.maxResultNumber = maxResultNumber;
            this.size = 0;

            iterator = collection.iterator();
        }

        public void remove() {
            iterator.remove();
        }

        public boolean hasNext() {
            if (iterator == null) {
                return false;
            }
            if (currentSelectItem != null) {
                return true;
            }

            if (maxResultNumber > 0 && currentIndex >= maxResultNumber) {
                if (limitTested) {
                    return false;
                }

                limitTested = true;

                // La limite est atteinte, mais il en reste peut-etre
                // d'autres ...

                for (; iterator.hasNext();) {
                    SelectItem selectItem = (SelectItem) iterator.next();
                    if (accept(filterProperties, selectItem) == false) {
                        continue;
                    }

                    size++;
                    break;
                }

                return false;
            }

            for (;;) {
                if (iterator.hasNext() == false) {
                    iterator = null;
                    return false;
                }

                SelectItem selectItem = (SelectItem) iterator.next();
                if (accept(filterProperties, selectItem) == false) {
                    continue;
                }

                currentSelectItem = selectItem;
                size++;
                return true;
            }
        }

        public Object next() {
            if (currentSelectItem == null) {
                throw new IllegalStateException("No more selectItems ...");
            }

            Object old = currentSelectItem;
            currentSelectItem = null;
            currentIndex++;

            return old;
        }

        public int getSize() {
            return size;
        }

        public void release() {
        }

        protected UIComponent getComponent() {
            if (component == null) {
                throw new NullPointerException("Component is not known !");
            }

            return component;
        }
    }

}
