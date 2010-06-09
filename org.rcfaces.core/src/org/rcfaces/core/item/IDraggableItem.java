/*
 * $Id$
 */
package org.rcfaces.core.item;

import javax.faces.model.SelectItem;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IDraggableItem {
    String[] getDragTypes(SelectItem draggedSelectItem);

    int getDragEffects(SelectItem draggedSelectItem);
}
