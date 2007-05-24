/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IMaxResultNumberCapability {

    /**
     * Sets an int value specifying the maximum number of rows for the result.
     * 
     * @param maximum
     *            max number of rows
     */
    void setMaxResultNumber(int maximum);

    /**
     * Returns an int value specifying the maximum number of rows for the
     * result.
     * 
     * @return max number of rows
     */
    int getMaxResultNumber();
}
