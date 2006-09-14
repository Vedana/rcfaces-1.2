/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.4  2006/05/04 13:40:13  oeuillot
 * Ajout de f_findComponent cot� client
 *
 * Revision 1.3  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.2  2006/02/06 16:47:05  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.1  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 */
package org.rcfaces.core.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import javax.faces.model.SelectItem;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractFilteredCollection implements Collection,
        IFilteredCollection {
    private static final String REVISION = "$Revision$";

    public static final Collection EMPTY_COLLECTION = new AbstractFilteredCollection() {
        private static final String REVISION = "$Revision$";

        protected boolean accept(IFilterProperties filter, SelectItem selectItem) {
            return false;
        }
    };

    public static final IFilteredCollection EMPTY_FILTERED_COLLECTION = (IFilteredCollection) EMPTY_COLLECTION;

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

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected class FilteredIterator implements IFilteredIterator {
        private static final String REVISION = "$Revision$";

        private final IFilterProperties filterProperties;

        private int maxResultNumber;

        private boolean limitTested = false;

        private int currentIndex = 0;

        private int size;

        private Iterator iterator;

        private SelectItem currentSelectItem;

        public FilteredIterator(IFilterProperties filterProperties) {
            this(filterProperties, NO_MAXIMUM_RESULT_NUMBER);

            iterator = collection.iterator();
        }

        public FilteredIterator(IFilterProperties filterProperties,
                int maxResultNumber) {
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

            if (maxResultNumber >= 0 && currentIndex >= maxResultNumber) {
                if (limitTested) {
                    return false;
                }

                limitTested = true;
                if (iterator.hasNext()) {
                    // La limite est atteinte, mais il reste peut-etre d'autres
                    // ...
                    size++;
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
    }

}
