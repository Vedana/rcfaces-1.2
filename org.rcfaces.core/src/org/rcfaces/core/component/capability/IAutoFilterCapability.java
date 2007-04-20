/*
 * $Id$
 */
package org.rcfaces.core.component.capability;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IAutoFilterCapability {

    /**
     * Returns a boolean value indicating if the component should apply filter
     * automatically.
     * 
     * @return true if the component should apply filter
     */
    boolean isAutoFilter();

    /**
     * Sets a boolean value indicating if the component should apply filter
     * automatically.
     * 
     * @param autoFilter
     *            true if the component should apply filter
     */
    void setAutoFilter(boolean autoFilter);
}
