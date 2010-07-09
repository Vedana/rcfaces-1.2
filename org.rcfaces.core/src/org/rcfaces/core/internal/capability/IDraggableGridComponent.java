/*
 * $Id$
 */
package org.rcfaces.core.internal.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IDraggableGridComponent {

    String[] getRowDragTypes();

    boolean isRowDragTypesSetted();

    int getRowDragEffects();

    boolean isRowDragEffectsSetted();
}
