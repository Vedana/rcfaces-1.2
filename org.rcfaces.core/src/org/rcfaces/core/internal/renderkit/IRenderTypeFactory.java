/*
 * $Id$
 */
package org.rcfaces.core.internal.renderkit;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IRenderTypeFactory {

    String computeRendererType(FacesContext facesContext,
            UIComponent component, String componentFamily, String baseRendererType);

}
