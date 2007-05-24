/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.renderkit;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IRendererExtension {
    boolean getDecodesChildren();

    void decodeEnd(FacesContext context, UIComponent component);

    void decodeChildren(FacesContext context, UIComponent component);
}
