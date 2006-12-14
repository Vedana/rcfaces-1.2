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
public interface ISelectedCapability {

    /**
     * Returns a boolean value indicating wether the component is selected.
     * @return true if selected, false otherwise
     */
    boolean isSelected();

    /**
     * Sets a boolean value indicating wether the component is selected.
     * @param selected true to select, false to unselect
     */
    void setSelected(boolean selected);
}
