/*
 * $Id$
 */
package org.rcfaces.core.internal.component;

import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IInitializationState {
    /**
     * Returns <code>true</code> if the component is just created.
     * 
     * @return <code>true</code> if this component is just created !
     */
    boolean isConstructionState();

    /**
     * Returns the faces context.
     */
    FacesContext getFacesContext();
}
