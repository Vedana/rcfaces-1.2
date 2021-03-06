/*
 * $Id$
 */
package org.rcfaces.core.internal.capability;

import javax.faces.context.FacesContext;
import javax.faces.event.FacesListener;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IComponentLifeCycle {
    void constructPhase(FacesContext facesContext);

    void initializePhase(FacesContext facesContext, boolean reused);

    void settedPhase(FacesContext facesContext);

    void decodePhase(FacesContext facesContext);

    void validationPhase(FacesContext facesContext);

    void updatePhase(FacesContext facesContext);

    void renderPhase(FacesContext facesContext);

    boolean confirmListenerAppend(FacesContext facesContext,
            Class< ? extends FacesListener> facesListenerClass);

}
