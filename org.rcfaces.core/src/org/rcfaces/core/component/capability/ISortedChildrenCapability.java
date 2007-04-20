/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

import javax.faces.component.UIComponent;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISortedChildrenCapability {

    /**
     * Set the ordered list of the sorted components
     */
    void setSortedChildren(UIComponent components[]);

    /**
     * Returns a string value giving the ordered list of the sorted components.
     * 
     * @return ordered list of the sorted components
     */
    UIComponent[] getSortedChildren();

}
