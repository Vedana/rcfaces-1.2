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

        writer.startElement(IHtmlWriter.TABLE);

        int cellPadding = toolFolderComponent.getToolBar().getItemPadding(
                facesContext);
        if (cellPadding < 0) {
            cellPadding = 0;
        }
        writer.writeCellPadding(cellPadding);

        writer.writeCellSpacing(0);

        writeHtmlAttributes(writer);
        writeJavaScriptAttributes(writer);
        writeCssAttributes(writer);

        String verticalAlignment = toolFolderComponent
                .getVerticalAlignment(facesContext);
        if (verticalAlignment != null) {
            writer.writeVAlign(verticalAlignment);
        }

        writer.startElement(IHtmlWriter.TBODY);

        writer.startElement(IHtmlWriter.TR);
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
        writer.endElement(IHtmlWriter.TR);

        writer.endElement(IHtmlWriter.TBODY);

        writer.endElement(IHtmlWriter.TABLE);
    }

    public void encodeChildren(FacesContext facesContext, UIComponent component)
            throws IOException {

        List children = component.getChildren();

        for (Iterator it = children.iterator(); it.hasNext();) {
            UIComponent child = (UIComponent) it.next();

            encodeToolItem(facesContext, child);
        }
    }

    protected void encodeToolItem(FacesContext facesContext,
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

        htmlWriter.startElement(IHtmlWriter.TD);

        htmlWriter.endComponent();

        ComponentTools.encodeRecursive(facesContext, component);

        htmlWriter.endElement(IHtmlWriter.TD);
    }

}
