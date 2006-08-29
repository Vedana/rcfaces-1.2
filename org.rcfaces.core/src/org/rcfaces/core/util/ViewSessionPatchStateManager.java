/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.3  2006/06/19 17:22:19  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.2  2006/02/06 16:47:05  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.1  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 */
package org.rcfaces.core.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.application.StateManager;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.Constants;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class ViewSessionPatchStateManager extends StateManager {
    private static final String REVISION = "$Revision$";

    private static final String FACES_VIEW_LIST = "com.sun.faces.VIEW_LIST";

    private static final String VIEW_PATCH_PARAMETER = Constants
            .getPackagePrefix()
            + ".VIEW_SESSION_PATCH";

    private static final Log LOG = LogFactory
            .getLog(ViewSessionPatchStateManager.class);

    private final StateManager parent;

    private Boolean enabled;

    public ViewSessionPatchStateManager(StateManager stateManager) {
        this.parent = stateManager;
    }

    public SerializedView saveSerializedView(FacesContext context) {
        synchronized (this) {
            if (enabled == null) {
                if ("true".equalsIgnoreCase(context.getExternalContext()
                        .getInitParameter(VIEW_PATCH_PARAMETER))) {
                    enabled = Boolean.TRUE;
                    if (LOG.isInfoEnabled()) {
                        LOG.info("Enable view-session patch.");
                    }
                } else {
                    enabled = Boolean.FALSE;
                }
            }
        }

        if (enabled == Boolean.TRUE) {
            Map sessionMap = context.getExternalContext().getSessionMap();

            synchronized (this) {
                if (sessionMap != null) {
                    List viewList = (List) sessionMap.get(FACES_VIEW_LIST);
                    if (viewList != null) {
                        viewList.remove(context.getViewRoot().getViewId());

                        if (LOG.isDebugEnabled()) {
                            LOG.debug("View list size=" + viewList.size());
                        }
                    }
                }
            }
        }

        return parent.saveSerializedView(context);
    }

    public void writeState(FacesContext context, SerializedView state)
            throws IOException {
        parent.writeState(context, state);
    }

    public UIViewRoot restoreView(FacesContext context, String viewId,
            String renderKitId) {

        return parent.restoreView(context, viewId, renderKitId);
    }

    /* ---- */

    protected Object getTreeStructureToSave(FacesContext context) {
        return null;
    }

    protected Object getComponentStateToSave(FacesContext context) {
        return null;
    }

    protected UIViewRoot restoreTreeStructure(FacesContext context,
            String viewId, String renderKitId) {
        return null;
    }

    protected void restoreComponentState(FacesContext context,
            UIViewRoot viewRoot, String renderKitId) {
    }
}
