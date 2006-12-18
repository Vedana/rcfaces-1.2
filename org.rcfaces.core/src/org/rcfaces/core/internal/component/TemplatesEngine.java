/*
 * $Id$
 */
package org.rcfaces.core.internal.component;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.rcfaces.core.internal.Constants;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TemplatesEngine implements PhaseListener {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -6370096499604012889L;

    private static final ThreadLocal templateConstructPhase;
    static {
        if (Constants.TEMPLATE_ENGINE_SUPPORT) {
            templateConstructPhase = new ThreadLocal();
        }
    }

    public void afterPhase(PhaseEvent event) {
        if (Constants.TEMPLATE_ENGINE_SUPPORT == false) {
            return;
        }

        templateConstructPhase.set(null);
    }

    public void beforePhase(PhaseEvent event) {
        if (Constants.TEMPLATE_ENGINE_SUPPORT == false) {
            return;
        }

        templateConstructPhase.set(Boolean.TRUE);
    }

    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    public static boolean isConstructPhase() {
        if (Constants.TEMPLATE_ENGINE_SUPPORT == false) {
            return false;
        }

        return templateConstructPhase.get() == null;
    }
}
