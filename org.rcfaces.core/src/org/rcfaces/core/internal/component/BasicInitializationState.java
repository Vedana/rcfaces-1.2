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
public class BasicInitializationState implements IInitializationState {

    private FacesContext facesContext;

    private Boolean isConstructionState;

    public boolean isConstructionState() {
        if (isConstructionState != null) {
            return isConstructionState.booleanValue();
        }

        FacesContext facesContext = getFacesContext();

        boolean ret = (RestoreViewPhaseListener
                .isRestoreViewPhase(facesContext) == false);

        isConstructionState = Boolean.valueOf(ret);

        return ret;
    }

    public FacesContext getFacesContext() {
        if (facesContext != null) {
            return facesContext;
        }

        facesContext = FacesContext.getCurrentInstance();
        return facesContext;
    }

}
