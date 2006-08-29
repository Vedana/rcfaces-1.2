/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:28  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.1  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
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
 * @author Olivier Oeuillot
 * @version $Revision$
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
