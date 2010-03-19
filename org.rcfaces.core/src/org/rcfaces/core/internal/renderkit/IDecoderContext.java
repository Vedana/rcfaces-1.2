/*
 * $Id$
 */
package org.rcfaces.core.internal.renderkit;

import javax.faces.component.UIComponent;
import javax.faces.render.Renderer;

public interface IDecoderContext {
    Renderer getRenderer();

    UIComponent getComponent();

    IProcessContext getProcessContext();
}
