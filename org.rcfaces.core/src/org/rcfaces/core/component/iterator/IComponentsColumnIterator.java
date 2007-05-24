/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.iterator;

import java.util.NoSuchElementException;

import org.rcfaces.core.component.ComponentsColumnComponent;

/**
 * An iterator over a collection of ComponentsColumnComponents.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IComponentsColumnIterator extends IComponentIterator {

    /**
     * Returns the next ComponentsColumnComponent in the iteration. Calling this
     * method repeatedly until the {@link #hasNext()} method returns false will
     * return each ComponentsColumnComponent in the underlying collection
     * exactly once.
     * 
     * @return the next ComponentsColumnComponent in the iteration.
     * @exception NoSuchElementException
     *                iteration has no more ComponentsColumnComponents.
     */
    ComponentsColumnComponent next();

    /**
     * Returns an array containing all of the ComponentsColumnComponents in this
     * list in proper sequence.
     * 
     * @return an array containing all of the ComponentsColumnComponents in this
     *         list in proper sequence.
     */
    ComponentsColumnComponent[] toArray();
}
