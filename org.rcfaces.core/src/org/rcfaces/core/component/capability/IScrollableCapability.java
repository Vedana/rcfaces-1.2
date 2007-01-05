/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IScrollableCapability {

    /**
     * Returns an integer value specifying the position of the horizontal scroolbar (browser dependant).
     * @return horizontal scroll position 
     */
    int getHorizontalScrollPosition();

    /**
     * Sets an integer value specifying the position of the horizontal scroolbar (browser dependant).
     * @param position horizontal scroll position 
     */
    void setHorizontalScrollPosition(int position);

    /**
     * Returns an integer value specifying the position of the vertical scroolbar (Browser dependant).
     * @return vertical scroll position
     */
    int getVerticalScrollPosition();

    /**
     * Sets an integer value specifying the position of the vertical scroolbar (Browser dependant).
     * @param position vertical scroll position
     */
    void setVerticalScrollPosition(int position);
}
