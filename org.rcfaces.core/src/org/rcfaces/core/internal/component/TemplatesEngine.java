/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/08/28 16:03:53  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.4  2005/11/17 10:04:56  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.3  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.2  2004/12/22 12:16:15  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 * Revision 1.1  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
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

    private static final ThreadLocal templateConstructPhase = new ThreadLocal();

    public void afterPhase(PhaseEvent event) {
        if (Constants.TEMPLATE_SUPPORT == false) {
            return;
        }

        templateConstructPhase.set(null);
    }

    public void beforePhase(PhaseEvent event) {
        if (Constants.TEMPLATE_SUPPORT == false) {
            return;
        }

        templateConstructPhase.set(Boolean.TRUE);
    }

    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    public static boolean isConstructPhase() {
        if (Constants.TEMPLATE_SUPPORT == false) {
            return false;
        }

        return templateConstructPhase.get() == null;
    }
}