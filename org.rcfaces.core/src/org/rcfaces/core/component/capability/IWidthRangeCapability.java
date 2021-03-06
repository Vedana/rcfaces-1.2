/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IWidthRangeCapability extends IWidthCapability {

    /**
     * Returns an int value specifying the maximum width in pixels (if
     * resizeable).
     * 
     * @return max width
     */
    int getMaxWidth();

    /**
     * Sets an int value specifying the maximum width in pixels (if resizeable).
     * 
     * @param maxWidth
     *            max width
     */
    void setMaxWidth(int maxWidth);

    /**
     * Returns an int value specifying the minimum width in pixels (if
     * resizeable).
     * 
     * @return min width
     */
    int getMinWidth();

    /**
     * Sets an int value specifying the minimum width in pixels (if resizeable).
     * 
     * @param minWidth
     *            min width
     */
    void setMinWidth(int minWidth);
}
