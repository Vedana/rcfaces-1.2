/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

public interface IGridCaptionCapability {
	/**
     * 
     * @return the grid caption
     */
    String getCaption();

    /**
     * 
     * @param caption Grid caption
     */
    void setCaption(String caption);
	
	/**
     * 
     * @return the grid summary
     */
    String getSummary();

    /**
     * 
     * @param caption Grid summary
     */
    void setSummary(String summary);
}
