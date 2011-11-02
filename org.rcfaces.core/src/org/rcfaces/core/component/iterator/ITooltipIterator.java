/*
 * $Id$
 */
package org.rcfaces.core.component.iterator;

import java.util.NoSuchElementException;

import org.rcfaces.core.component.TooltipComponent;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ITooltipIterator extends IComponentIterator {

    /**
     * Returns the next AdditionalInformationComponent in the iteration. Calling
     * this method repeatedly until the {@link #hasNext()} method returns false
     * will return each AdditionalInformationComponent in the underlying
     * collection exactly once.
     * 
     * @return the next AdditionalInformationComponent in the iteration.
     * @exception NoSuchElementException
     *                iteration has no more AdditionalInformationComponent.
     */
    TooltipComponent next();

    /**
     * Returns an array containing all of the AdditionalInformationComponent in
     * this list in proper sequence.
     * 
     * @return an array containing all of the AdditionalInformationComponent in
     *         this list in proper sequence.
     */
    TooltipComponent[] toArray();

}
