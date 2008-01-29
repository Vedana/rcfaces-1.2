/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.renderkit.IRenderContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISubInputClientIdRenderer {
    String computeSubInputClientId(IRenderContext renderContext,
            UIComponent component, String clientId);
}
