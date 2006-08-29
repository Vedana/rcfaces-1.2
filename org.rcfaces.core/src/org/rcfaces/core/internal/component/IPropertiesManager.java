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

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IPropertiesManager {

    void releaseManager();

    void commitManager(FacesContext context);

    IPropertiesAccessor getPropertiesAccessor(boolean enableDelta,
            boolean forceDelta);

    void restoreManagerState(FacesContext context, Object props);

    Object saveManagerState(FacesContext context);

}
