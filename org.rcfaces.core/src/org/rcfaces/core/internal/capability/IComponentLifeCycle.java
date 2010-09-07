/*
 * $Id$
 */
package org.rcfaces.core.internal.capability;

import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IComponentLifeCycle {
    void constructPhase(FacesContext facesContext);
    
    void initializePhase(FacesContext facesContext, boolean reused);

    void decodePhase(FacesContext facesContext);

    void validationPhase(FacesContext facesContext);

    void updatePhase(FacesContext facesContext);

    void renderPhase(FacesContext facesContext);
}
