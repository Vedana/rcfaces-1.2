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
import org.rcfaces.core.component.ToolFolderComponent;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ToolFolderRenderer extends AbstractCssRenderer {

    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(ToolFolderRenderer.class);

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.TOOL_FOLDER;
    }

    protected void encodeBegin(IComponentWriter writer) throws WriterException {
        super.encodeBegin(writer);

        encodeBeginToolFolder((IHtmlWriter) writer,
                (ToolFolderComponent) writer.getComponentRenderContext()
                        .getComponent());
    }

    protected void encodeBeginToolFolder(IHtmlWriter writer,
            ToolFolderComponent toolFolderComponent) throws WriterException {

        FacesContext facesContext = writer.getComponentRenderContext()
                .getFacesContext();

        writer.startElement(IHtmlWriter.UL);

        // writer.writeCellSpacing(0);

        writeHtmlAttributes(writer);
        writeJavaScriptAttributes(writer);
        writeCssAttributes(writer);

        String verticalAlignment = toolFolderComponent
                .getVerticalAlignment(facesContext);
        if (verticalAlignment != null) {
            writer.writeVAlign(verticalAlignment);
        }
    }

    public boolean getRendersChildren() {
        return true;
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        encodeEndToolFolder((IHtmlWriter) writer, (ToolFolderComponent) writer
                .getComponentRenderContext().getComponent());

        super.encodeEnd(writer);
    }

    protected void encodeEndToolFolder(IHtmlWriter writer,
            ToolFolderComponent component) throws WriterException {
        writer.endElement(IHtmlWriter.UL);
    }

    public void encodeChildren(FacesContext facesContext, UIComponent component)
            throws IOException {
        IHtmlRenderContext renderContext = getHtmlRenderContext(facesContext);

        IHtmlWriter htmlWriter = (IHtmlWriter) renderContext
                .getComponentWriter();

        List children = getChildren(htmlWriter);

        for (Iterator it = children.iterator(); it.hasNext();) {
            UIComponent child = (UIComponent) it.next();

            encodeToolItem(htmlWriter, child);
        }

        htmlWriter.endComponent();
    }

    protected List getChildren(IHtmlWriter htmlWriter) {
        return htmlWriter.getComponentRenderContext().getComponent()
                .getChildren();
    }

    protected void encodeToolItem(IHtmlWriter htmlWriter, UIComponent child)
            throws WriterException {

        FacesContext facesContext = htmlWriter.getHtmlComponentRenderContext()
                .getFacesContext();

        Renderer renderer = getRenderer(facesContext, child);

        if (renderer == null) {
            LOG.error("Can not get renderer for component '" + child + "' id="
                    + child.getId());
            return;
        }

        htmlWriter.startElement(IHtmlWriter.LI);
        htmlWriter.writeClass("f_toolFolder_item");

        ToolFolderComponent toolFolderComponent = (ToolFolderComponent) htmlWriter
                .getHtmlComponentRenderContext().getComponent();

        if (toolFolderComponent.getToolBar().isItemPaddingSetted()) {
            int cellPadding = toolFolderComponent.getToolBar().getItemPadding(
                    facesContext);
            if (cellPadding >= 0) {
                htmlWriter.writeStyle().writePadding(cellPadding + "px");
            }
        }

        htmlWriter.endComponent();

        ComponentTools.encodeRecursive(facesContext, child);

        htmlWriter.endElement(IHtmlWriter.LI);
    }

}
