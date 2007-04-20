/*
 * $Id$
 */
package org.rcfaces.core.internal.component;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class RestoreViewPhaseListener implements PhaseListener {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -6370096499604012889L;

    private static final String RESTORE_VIEW_PHASE_PROPERTY_NAME = "org.rcfaces.core.RESTORE_VIEW";

    public void afterPhase(PhaseEvent event) {
        event.getFacesContext().getExternalContext().getRequestMap().remove(
                RESTORE_VIEW_PHASE_PROPERTY_NAME);
    }

    public void beforePhase(PhaseEvent event) {
        event.getFacesContext().getExternalContext().getRequestMap().put(
                RESTORE_VIEW_PHASE_PROPERTY_NAME, Boolean.TRUE);
    }

    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    public static boolean isRestoreViewPhase() {
        return isRestoreViewPhase(null);
    }

    public static boolean isRestoreViewPhase(FacesContext facesContext) {
        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();

            // Le facesContext peut être null !
            // Principalement dans la phase d'init de Faces lors des tests des composants.
            if (facesContext == null) {
                return false;
            }
        }

        return facesContext.getExternalContext().getRequestMap().containsKey(
                RESTORE_VIEW_PHASE_PROPERTY_NAME);
    }
}
