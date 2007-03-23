/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.iterator;

import java.util.NoSuchElementException;

import org.rcfaces.core.component.ImageRadioButtonComponent;

/**
 * An iterator over a collection of ImageRadioButtonComponents.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IImageRadioButtonIterator extends IComponentIterator {

    /**
     * Returns the next ImageRadioButtonComponent in the iteration. Calling this
     * method repeatedly until the {@link #hasNext()} method returns false will
     * return each element in the underlying collection exactly once.
     * 
     * @return the next ImageRadioButtonComponent in the iteration.
     * @exception NoSuchElementException
     *                iteration has no more ImageRadioButtonComponents.
     */
    ImageRadioButtonComponent next();

    /**
     * Returns an array containing all of the ImageRadioButtonComponents in this
     * list in proper sequence.
     * 
     * @return an array containing all of the ImageRadioButtonComponents in this
     *         list in proper sequence.
     */
    ImageRadioButtonComponent[] toArray();
}
