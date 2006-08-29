/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.renderkit.IComponentRenderContext;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface ICssRenderer {
    String getStyleClassName(IComponentRenderContext componentRenderContext);

    String getStyleClassName(IComponentRenderContext componentRenderContext,
            UIComponent component);

    String getStyleClassName(IComponentRenderContext componentRenderContext,
            UIComponent component, String suffix);

}
