/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IDraggableCapability {
    String[] getDragTypes();

    void setDragTypes(String[] types);

    int getDragEffects();

    void setDragEffects(int effects);

    boolean isDraggable();

    void setDraggable(boolean draggable);
}
