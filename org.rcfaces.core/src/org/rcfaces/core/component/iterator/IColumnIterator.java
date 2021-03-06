/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.iterator;

import java.util.NoSuchElementException;

import javax.faces.component.UIColumn;

/**
 * An iterator over a collection of UIColumns.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IColumnIterator extends IComponentIterator<UIColumn> {

    /**
     * Returns the next UIColumn in the iteration. Calling this method
     * repeatedly until the {@link #hasNext()} method returns false will return
     * each UIColumn in the underlying collection exactly once.
     * 
     * @return the next UIColumn in the iteration.
     * @exception NoSuchElementException
     *                iteration has no more DataColumnComponents.
     */
    UIColumn next();

    /**
     * Returns an array containing all of the UIColumns in this list in proper
     * sequence.
     * 
     * @return an array containing all of the UIColumns in this list in proper
     *         sequence.
     */
    UIColumn[] toArray();
}
