/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IDroppableCapability {
    String[] getDropTypes();

    void setDropTypes(String[] types);

    int getDropEffects();

    void setDropEffects(int effects);

    boolean isDroppable();

    void setDroppable(boolean droppable);
}
