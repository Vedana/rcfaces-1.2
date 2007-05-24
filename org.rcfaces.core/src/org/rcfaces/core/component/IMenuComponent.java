/*
 * $Id$
 * 
 */
package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.ICheckEventCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.iterator.IMenuItemIterator;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IMenuComponent extends ICheckEventCapability,
        ISelectionEventCapability {

    /*
     * int getItemImageWidth();
     * 
     * void setItemImageWidth(int width);
     * 
     * int getItemImageHeight();
     * 
     * void setItemImageHeight(int height);
     */
    IMenuItemIterator listMenuItems();

    boolean isRemoveAllWhenShown();

    void setRemoveAllWhenShown(boolean removeAllWhenShown);
}
