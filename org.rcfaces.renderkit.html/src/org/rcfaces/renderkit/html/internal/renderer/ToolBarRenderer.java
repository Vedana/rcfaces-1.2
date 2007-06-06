/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ItemsToolFolderComponent;
import org.rcfaces.core.component.ToolBarComponent;
import org.rcfaces.core.component.ToolFolderComponent;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ToolBarRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(ToolBarRenderer.class);

    public boolean getRendersChildren() {
        return true;
    }

    protected void encodeBegin(IComponentWriter writer) throws WriterException {
        super.encodeBegin(writer);

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        ToolBarComponent toolBarComponent = (ToolBarComponent) componentRenderContext
                .getComponent();

        htmlWriter.startElement(IHtmlWriter.UL);
        // htmlWriter.writeCellPadding(0);
        // htmlWriter.writeCellSpacing(0);

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        String verticalAlignment = toolBarComponent
                .getVerticalAlignment(facesContext);
        if (verticalAlignment != null) {
            htmlWriter.writeVAlign(verticalAlignment);
        }

        if (toolBarComponent.isLocked(facesContext)) {
            htmlWriter.writeAttribute("v:locked", true);
        }
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.endElement(IHtmlWriter.UL);

        super.encodeEnd(writer);
    }

    public void encodeChildren(FacesContext facesContext, UIComponent component)
            throws IOException {

        List children = component.getChildren();

        for (Iterator it = children.iterator(); it.hasNext();) {
            UIComponent child = (UIComponent) it.next();

            if (child instanceof ItemsToolFolderComponent) {
                encodeToolFolder(facesContext, child);
                continue;
            }
            if (child instanceof ToolFolderComponent) {
                encodeToolFolder(facesContext, child);
                continue;
            }

            LOG.error("Invalid child of ToolBar: " + component + " id="
                    + component.getId());
        }
    }

    protected void encodeToolFolder(FacesContext facesContext,
            UIComponent component) throws WriterException {

        Renderer renderer = getRenderer(facesContext, component);

        if (renderer == null) {
            LOG.error("Can not get renderer for component '" + component
                    + "' id=" + component.getId());
            return;
        }

        IRenderContext renderContext = getRenderContext(facesContext);

        IHtmlWriter htmlWriter = (IHtmlWriter) renderContext
                .getComponentWriter();

        htmlWriter.startElement(IHtmlWriter.LI);

        ComponentTools.encodeRecursive(facesContext, component);

        htmlWriter.endElement(IHtmlWriter.LI);
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.TOOL_BAR;
    }

    public boolean getDecodesChildren() {
        return true;
    }
}