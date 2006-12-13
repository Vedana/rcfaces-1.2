/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * A boolean value indicating wether the component is collapsed.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICollapsableCapability {
    
	/**
	 * Returns a boolean value indicating wether the component is collapsed.
	 * 
	 * @return boolean
	 */
	boolean isCollapsed();

	/**
	 * Sets a boolean value indicating wether the component should be collapsed.
	 * 
	 * @param collapsed boolean
	 */
    void setCollapsed(boolean collapsed);
}
