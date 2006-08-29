/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/06/19 17:22:17  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 */
package org.rcfaces.core.internal.component;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class BasicPropertiesManager extends BasicPropertiesAccessor implements
        IPropertiesManager {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(BasicPropertiesManager.class);

    protected IFactory factory;

    private IPropertiesAccessor propertiesAccessor;

    private IDeltaPropertiesAccessor deltaPropertiesAccessor;

    public void setCameliaFactory(IFactory factory) {
        this.factory = factory;
    }

    public void releaseManager() {
        if (deltaPropertiesAccessor != null) {
            deltaPropertiesAccessor.release();
            deltaPropertiesAccessor = null;
        }

        if (propertiesAccessor != null) {
            propertiesAccessor.release();
            propertiesAccessor = null;
        }
    }

    public void commitManager(FacesContext context) {
        if (deltaPropertiesAccessor == null) {
            return;
        }

        deltaPropertiesAccessor.commitProperties(context);
        deltaPropertiesAccessor.release();
        deltaPropertiesAccessor = null;
    }

    public IPropertiesAccessor getPropertiesAccessor(boolean enableDelta,
            boolean forceDelta) {

        if (enableDelta) {
            if (forceDelta) {
                if (deltaPropertiesAccessor == null) {
                    if (propertiesAccessor == null) {
                        propertiesAccessor = createPropertiesAccessor();
                    }

                    deltaPropertiesAccessor = propertiesAccessor
                            .createDeltaPropertiesAccessor();
                }

                return deltaPropertiesAccessor;
            }

            if (deltaPropertiesAccessor != null) {
                return deltaPropertiesAccessor;
            }
        }

        if (propertiesAccessor != null || forceDelta == false) {
            return propertiesAccessor;
        }

        propertiesAccessor = createPropertiesAccessor();

        return propertiesAccessor;

    }

    public void restoreManagerState(FacesContext context, Object state) {
        if (state == null) {
            return;
        }

        propertiesAccessor = createPropertiesAccessor();

        deltaPropertiesAccessor = propertiesAccessor.restoreState(context,
                state);
    }

    public Object saveManagerState(FacesContext context) {
        if (propertiesAccessor == null) {
            return null;
        }

        if (deltaPropertiesAccessor != null) {
            return deltaPropertiesAccessor.saveState(context);
        }

        return propertiesAccessor.saveState(context);
    }

    protected IPropertiesAccessor createPropertiesAccessor() {
        return this;
    }

}
