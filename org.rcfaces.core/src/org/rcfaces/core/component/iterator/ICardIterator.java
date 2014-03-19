/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.iterator;

import java.util.NoSuchElementException;

import org.rcfaces.core.component.CardComponent;

/**
 * An iterator over a collection of CardComponents.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICardIterator extends IComponentIterator<CardComponent> {
    /**
     * Returns the next CardComponent in the iteration. Calling this method
     * repeatedly until the {@link #hasNext()} method returns false will return
     * each CardComponent in the underlying collection exactly once.
     * 
     * @return the next CardComponent in the iteration.
     * @exception NoSuchElementException
     *                iteration has no more CardComponents.
     */
    CardComponent next();

    /**
     * Returns an array containing all of the CardComponents in this list in
     * proper sequence.
     * 
     * @return an array containing all of the CardComponents in this list in
     *         proper sequence.
     */
    CardComponent[] toArray();
}
