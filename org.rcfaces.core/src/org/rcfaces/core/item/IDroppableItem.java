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
public interface IDroppableItem {
    String[] getDropTypes(SelectItem droppedSelectItem);

    int getDropEffects(SelectItem droppedSelectItem);
}
