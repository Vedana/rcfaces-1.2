/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.renderkit.border;

import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IBorderRenderersRegistry {
    IBorderRenderer getBorderRenderer(FacesContext facesContext,
            String renderKitId, String componentFamily,
            String componentRenderType, String borderType);
}
