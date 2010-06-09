/*
 * $Id$
 */

package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IDragListener;

/**
 * 
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IDragEventCapability {

    /**
     * Adds a listener to the component for the drag event
     * 
     * @param facesListener
     *            the drag listener to add
     */
    void addDragListener(IDragListener facesListener);

    /**
     * Removes a listener from the component for the drag event
     * 
     * @param facesListener
     *            the drag listener to remove
     */
    void removeDragListener(IDragListener facesListener);

    /**
     * Returns a list of drag listener for the component
     * 
     * @return drag listeners' list
     */
    FacesListener[] listDragListeners();
}