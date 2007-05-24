/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * A string that indicates the type of border the component should show.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IBorderTypeCapability {
    String NONE_BORDER_TYPE_NAME = "none";

    /**
     * Returns a string that indicates the type of border the component should
     * show.
     * 
     * @return none|solid|relief|flat|rounded
     */
    String getBorderType();

    /**
     * Sets a string that indicates the type of border the component should
     * show.
     * 
     * @param borderType
     *            none|solid|relief|flat|rounded
     */
    void setBorderType(String borderType);
}
