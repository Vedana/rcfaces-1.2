/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

public interface IGridCaptionCapability extends ICaptionCapability {

    /**
     * 
     * @return the grid summary
     */
    String getSummary();

    /**
     * 
     * @param caption
     *            Grid summary
     */
    void setSummary(String summary);
}
