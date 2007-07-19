/*
 * $Id$
 */
package org.rcfaces.core.internal.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISelectionComponent {

    /**
     * 
     */
    void select(Object rowValue);

    /**
     * Selects all of the items in the receiver. If the receiver is
     * single-select, do nothing.
     */
    void selectAll();

    void deselect(Object rowValue);

    /**
     * Deselects all selected items in the receiver.
     */
    void deselectAll();
}
