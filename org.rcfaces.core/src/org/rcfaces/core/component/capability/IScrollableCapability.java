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
     * Returns a string value specifying the position of the horizontal scroolbar (browser dependant).
     * @return horizontal scroll position 
     */
    String getHorizontalScrollPosition();

    /**
     * Sets a string value specifying the position of the horizontal scroolbar (browser dependant).
     * @param position horizontal scroll position 
     */
    void setHorizontalScrollPosition(String position);

    /**
     * Returns a string value specifying the position of the vertical scroolbar (Browser dependant).
     * @return vertical scroll position
     */
    String getVerticalScrollPosition();

    /**
     * Sets a string value specifying the position of the vertical scroolbar (Browser dependant).
     * @param position vertical scroll position
     */
    void setVerticalScrollPosition(String position);
}
