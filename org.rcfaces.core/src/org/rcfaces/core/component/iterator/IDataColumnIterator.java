/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.iterator;

import java.util.NoSuchElementException;

import org.rcfaces.core.component.DataColumnComponent;

/**
 * An iterator over a collection of DataColumnComponents.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IDataColumnIterator extends
        IComponentIterator<DataColumnComponent> {

    /**
     * Returns the next DataColumnComponent in the iteration. Calling this
     * method repeatedly until the {@link #hasNext()} method returns false will
     * return each DataColumnComponent in the underlying collection exactly
     * once.
     * 
     * @return the next DataColumnComponent in the iteration.
     * @exception NoSuchElementException
     *                iteration has no more DataColumnComponents.
     */
    DataColumnComponent next();

    /**
     * Returns an array containing all of the DataColumnComponents in this list
     * in proper sequence.
     * 
     * @return an array containing all of the DataColumnComponents in this list
     *         in proper sequence.
     */
    DataColumnComponent[] toArray();
}
