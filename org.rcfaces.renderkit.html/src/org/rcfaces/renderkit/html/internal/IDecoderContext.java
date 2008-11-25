/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.component.UIComponent;
import javax.faces.render.Renderer;

import org.rcfaces.core.internal.renderkit.IProcessContext;

public interface IDecoderContext {
    Renderer getRenderer();

    UIComponent getComponent();

    IProcessContext getProcessContext();
}
