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
     * 
     */
    String getHorizontalScrollPosition();

    /**
     * 
     */
    void setHorizontalScrollPosition(String position);

    /**
     * 
     */
    String getVerticalScrollPosition();

    /**
     * 
     */
    void setVerticalScrollPosition(String position);
}
