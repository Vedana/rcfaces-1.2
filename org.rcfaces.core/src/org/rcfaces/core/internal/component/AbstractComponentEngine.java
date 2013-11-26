/*
 * $Id$
 */
package org.rcfaces.core.internal.component;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.rcfaces.core.internal.capability.IComponentEngine;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractComponentEngine implements IComponentEngine {

    static final Iterator< ? > EMPTY_ITERATOR = Collections.EMPTY_SET
            .iterator();

    protected final IFactory factory;

    AbstractComponentEngine(IFactory factory) {
        if (factory == null) {
            throw new NullPointerException("Factory is NULL !");
        }
        this.factory = factory;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.component.UIComponent#getFamily()
     */

    protected static final boolean isListEmpty(Collection< ? > list) {
        return (list == null) || (list.size() < 1);
    }

    protected static final boolean isMapEmpty(Map< ? , ? > map) {
        return (map == null) || (map.size() < 1);
    }

    @SuppressWarnings("unchecked")
    protected static final <T> Iterator<T> iteratorList(Collection<T> list) {
        if (isListEmpty(list)) {
            return (Iterator<T>) EMPTY_ITERATOR;
        }

        return list.iterator();
    }
}
