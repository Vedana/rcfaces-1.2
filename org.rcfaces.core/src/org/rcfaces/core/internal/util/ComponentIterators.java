/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2005/10/05 14:34:19  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.1  2004/08/06 14:03:57  jmerlin
 * Vedana Faces
 *
 * Revision 1.2  2004/08/06 09:35:14  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/04 16:28:11  oeuillot
 * Premier jet !
 *
 */
package org.rcfaces.core.internal.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.faces.component.UIComponent;

import org.rcfaces.core.component.iterator.IComponentIterator;
import org.rcfaces.core.internal.manager.IContainerManager;


/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class ComponentIterators {
    private static final String REVISION = "$Revision$";

    private static final boolean USE_CACHED_LIST = true;

    public static final IComponentIterator EMPTY_COMPONENT_ITERATOR = new EmptyComponentIterator();

    public static final UIComponent[] EMPTY_COMPONENT_ARRAY = new UIComponent[0];

    public static int indexOf(IContainerManager parent, UIComponent child,
            Class childClass) {

        if (USE_CACHED_LIST) {
            UIComponent aos[] = CachedChildrenList.getArray(parent, childClass);
            for (int i = 0; i < aos.length; i++) {
                if (child == aos[i] || child.equals(aos[i])) {
                    return i;
                }
            }
            return -1;
        }

        int idx = 0;
        for (Iterator it = parent.getChildren().iterator(); it.hasNext();) {
            UIComponent obj = (UIComponent) it.next();
            if (obj == null) {
                continue;
            }

            if (childClass.isInstance(obj) == false) {
                continue;
            }

            if (child == obj || obj.equals(child)) {
                return idx;
            }

            idx++;
        }

        return -1;
    }

    public static UIComponent componentAt(IContainerManager parent,
            Class childClass, int position) {
        if (USE_CACHED_LIST) {
            UIComponent elements[] = CachedChildrenList.getArray(parent,
                    childClass);
            if (position < 0 || position >= elements.length) {
                throw new IndexOutOfBoundsException("Out of range (0 <= "
                        + position + " <= " + (elements.length - 1) + ")");
            }

            return elements[position];
        }

        int idx = 0;
        for (Iterator it = parent.getChildren().iterator(); it.hasNext();) {
            UIComponent obj = (UIComponent) it.next();
            if (obj == null) {
                continue;
            }

            if (childClass.isInstance(obj) == false) {
                continue;
            }

            if (idx == position) {
                return obj;
            }

            idx++;
        }

        throw new IndexOutOfBoundsException("Out of range (0 <= " + position
                + " <= " + (idx - 1) + ")");
    }

    public static int count(IContainerManager parent, Class childClass) {
        if (USE_CACHED_LIST) {
            return CachedChildrenList.getCount(parent, childClass);
        }

        int cnt = 0;
        for (Iterator it = parent.getChildren().iterator(); it.hasNext();) {
            UIComponent obj = (UIComponent) it.next();
            if (obj == null) {
                continue;
            }

            if (childClass.isInstance(obj) == false) {
                continue;
            }

            cnt++;
        }

        return cnt;
    }

    public static boolean removeAll(IContainerManager parent, Class childClass) {
        int count = parent.getChildCount();
        if (count < 1) {
            return false;
        }

        List rev = list(parent, childClass);

        if (rev == null || rev.isEmpty()) {
            return false;
        }

        return parent.getChildren().removeAll(rev);
    }

    public static List list(IContainerManager parent, Class childClass) {

        if (USE_CACHED_LIST) {
            return CachedChildrenList.getList(parent, childClass);
        }

        List components = parent.getChildren();
        int total = components.size();
        if (total == 0) {
            return Collections.EMPTY_LIST;
        }

        List rev = null;
        for (Iterator it = components.iterator(); it.hasNext(); total--) {
            UIComponent component = (UIComponent) it.next();

            if (childClass.isInstance(component) == false) {
                continue;
            }

            if (rev == null) {
                rev = new ArrayList(total);
            }

            rev.add(component);
        }

        if (rev == null) {
            return Collections.EMPTY_LIST;
        }

        return rev;
    }

    public static class EmptyComponentIterator implements IComponentIterator {
        private static final String REVISION = "$Revision$";

        /*
         * (non-Javadoc)
         * 
         * @see org.rcfaces.core.iterators.IComponentIterator#count()
         */
        public final int count() {
            return 0;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.rcfaces.core.iterators.IComponentIterator#hasNext()
         */
        public final boolean hasNext() {
            return false;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.rcfaces.core.iterators.IComponentIterator#nextComponent()
         */
        public final UIComponent nextComponent() {
            throw new NoSuchElementException("Empty iterator");
        }

        public UIComponent[] toArray(UIComponent[] array) {
            return EMPTY_COMPONENT_ARRAY;
        }

    }

    public static class ComponentListIterator implements IComponentIterator {
        private static final String REVISION = "$Revision$";

        private final Iterator iterator;

        private int count;

        protected ComponentListIterator(List list) {
            this.iterator = list.iterator();
            this.count = list.size();
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.rcfaces.core.iterators.IComponentIterator#count()
         */
        public final int count() {
            return count;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.rcfaces.core.iterators.IComponentIterator#hasNext()
         */
        public final boolean hasNext() {
            return count > 0;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.rcfaces.core.iterators.IComponentIterator#nextComponent()
         */
        public final UIComponent nextComponent() {
            Object object = iterator.next();

            count--;

            return (UIComponent) object;
        }

        public UIComponent[] toArray(UIComponent[] array) {
            if (count < 1) {
                // Ca doit peter ici !
                iterator.next();
                return null;
            }

            if (array == null) {
                array = new UIComponent[count];

            } else if (array.length < count) {
                array = (UIComponent[]) Array.newInstance(array.getClass()
                        .getComponentType(), count);
            }

            for (int i = 0; count > 0; i++) {
                array[i] = (UIComponent) iterator.next();

                count--;
            }

            return array;
        }

    }

    public static class ComponentArrayIterator implements IComponentIterator {
        private static final String REVISION = "$Revision$";

        private final Object array[];

        private int count;

        protected ComponentArrayIterator(UIComponent object) {
            this.array = new Object[] { object };
            this.count = 1;
        }

        protected ComponentArrayIterator(Object array[]) {
            this.array = array;
            this.count = array.length;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.rcfaces.core.iterators.IComponentIterator#count()
         */
        public final int count() {
            return count;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.rcfaces.core.iterators.IComponentIterator#hasNext()
         */
        public final boolean hasNext() {
            return count > 0;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.rcfaces.core.iterators.IComponentIterator#nextComponent()
         */
        public final UIComponent nextComponent() {
            if (count < 1) {
                throw new NoSuchElementException(
                        "No more components ! (position="
                                + (array.length - count) + ", arraySize="
                                + array.length + ")");
            }

            UIComponent component = (UIComponent) array[array.length - count];
            count--;

            return component;
        }

        public UIComponent[] toArray(UIComponent[] array) {
            if (count < 1) {
                throw new NoSuchElementException(
                        "No more components ! (position="
                                + (array.length - count) + ", arraySize="
                                + array.length + ")");
            }

            if (array == null) {
                array = new UIComponent[count];

            } else if (array.length < count) {
                array = (UIComponent[]) Array.newInstance(array.getClass()
                        .getComponentType(), count);
            }

            System.arraycopy(this.array, this.array.length - count, array, 0,
                    count);

            count = 0;

            return array;
        }

    }
}