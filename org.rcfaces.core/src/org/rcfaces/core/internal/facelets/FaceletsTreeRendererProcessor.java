/*
 * $Id$
 */
package org.rcfaces.core.internal.facelets;

import java.io.IOException;
import java.io.Writer;

import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.renderkit.tools.AbstractTreeRendererProcessor;
import org.rcfaces.core.internal.tools.ComponentTools;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class FaceletsTreeRendererProcessor extends
        AbstractTreeRendererProcessor {

    public FaceletsTreeRendererProcessor(FacesContext facesContext) {
        super(facesContext);
    }

    public void encodeChildrenRecursive(UIComponent component,
            String componentId) throws WriterException {

        ViewHandler viewHandler = facesContext.getApplication()
                .getViewHandler();
        if ((viewHandler instanceof FaceletViewHandler) == false) {
            throw new FacesException(
                    "To use AJAX feature with Facelets, you must declare  org.rcfaces.core.internal.facelets.FaceletViewHandler as view handler !");
        }

        try {
            ((FaceletViewHandler) viewHandler).buildFaceletViewRoot(
                    facesContext, facesContext.getViewRoot());

        } catch (IOException e) {
            throw new WriterException(null, e, component);
        }

        component = ComponentTools.getForComponent(facesContext, componentId,
                facesContext.getViewRoot());

        ComponentTools.encodeChildrenRecursive(facesContext, component);

    }

    public boolean hasSaveStateFieldMarker(String content) {
        return false;
    }

    public void writeFilteredContent(Writer writer, String content) throws IOException {

        writer.write(content);
    }

}
