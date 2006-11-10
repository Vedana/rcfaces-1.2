/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.decorator;

import javax.faces.component.UIComponent;
import javax.faces.render.Renderer;

import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class AbstractComponentDecorator implements IComponentDecorator {
    private static final String REVISION = "$Revision$";

    private IComponentDecorator subWriter;

    public void encodeContainer(IHtmlWriter writer, Renderer renderer)
            throws WriterException {
        if (subWriter != null) {
            subWriter.encodeContainer(writer, renderer);
        }
    }

    public void encodeContainerEnd(IHtmlWriter writer, Renderer renderer)
            throws WriterException {
        if (subWriter != null) {
            subWriter.encodeContainerEnd(writer, renderer);
        }
    }

    public void encodeJavaScript(IJavaScriptWriter jsWriter)
            throws WriterException {

        if (subWriter != null) {
            subWriter.encodeJavaScript(jsWriter);
        }
    }

    public void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {

        if (subWriter != null) {
            subWriter.decode(context, component, componentData);
        }
    }

    public void addChildDecorator(IComponentDecorator writer) {
        if (subWriter != null) {
            subWriter.addChildDecorator(writer);
            return;
        }

        this.subWriter = writer;
    }

}
