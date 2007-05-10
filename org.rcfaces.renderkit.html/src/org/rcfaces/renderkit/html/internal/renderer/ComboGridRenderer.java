/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.io.CharArrayWriter;

import javax.faces.component.NamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ComboGridComponent;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.HtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ComboGridRenderer extends DataGridRenderer {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(ComboGridRenderer.class);

    private static final String GRID_HTML_CONTENT = "org.rcfaces.renderkit.html.GRID_HTML_CONTENT";

    private static final int ARROW_IMAGE_WIDTH = 16;

    private static final int ARROW_IMAGE_HEIGHT = 16;

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.COMBO_GRID;
    }

    public String getComponentStyleClassName() {
        return getJavaScriptClassName();
    }

    protected void encodeGrid(IHtmlWriter htmlWriter) throws WriterException {

        IHtmlComponentRenderContext componentRenderContext = htmlWriter
                .getHtmlComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        ComboGridComponent comboGridComponent = (ComboGridComponent) componentRenderContext
                .getComponent();

        boolean disabled = comboGridComponent.isDisabled(facesContext);
        boolean readOnly = comboGridComponent.isReadOnly(facesContext);

        String classSuffix = null;
        if (disabled) {
            classSuffix = getMainStyleClassName() + "_disabled";

        } else if (readOnly) {
            classSuffix = getMainStyleClassName() + "_readOnly";
        }

        String width = comboGridComponent.getWidth(facesContext);

        int colWidth = -1;
        if (width != null) {
            int totalWidth = computeSize(width, 0, 2);

            colWidth = totalWidth - ARROW_IMAGE_WIDTH - 4;
        }

        htmlWriter.startElement(IHtmlWriter.TABLE);
        htmlWriter.writeAttribute("cellspacing", "0");
        htmlWriter.writeAttribute("cellpadding", "0");

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter, classSuffix, CSS_ALL_MASK);

        htmlWriter.startElement(IHtmlWriter.COL);
        if (colWidth > 0) {
            htmlWriter.writeWidth(colWidth);
        } else {
            htmlWriter.writeWidth("1*");
        }
        htmlWriter.endElement(IHtmlWriter.COL);

        htmlWriter.startElement(IHtmlWriter.COL);
        htmlWriter.writeWidth(ARROW_IMAGE_WIDTH);
        htmlWriter.endElement(IHtmlWriter.COL);

        htmlWriter.startElement(IHtmlWriter.TBODY);

        htmlWriter.startElement(IHtmlWriter.TR);

        htmlWriter.startElement(IHtmlWriter.TD);
        htmlWriter.writeClass(getMainStyleClassName() + "_inputCell");

        htmlWriter.startElement(IHtmlWriter.INPUT);
        if (colWidth > 0) {
            htmlWriter.writeStyle().writeWidth((colWidth - 4) + "px");
        }

        htmlWriter.writeType(IHtmlWriter.TEXT_INPUT_TYPE);

        htmlWriter.writeClass(getMainStyleClassName() + "_input");

        if (disabled) {
            htmlWriter.writeDisabled();
        }
        if (readOnly) {
            htmlWriter.writeReadOnly();
        }

        htmlWriter.endElement(IHtmlWriter.INPUT);

        htmlWriter.endElement(IHtmlWriter.TD);

        htmlWriter.startElement(IHtmlWriter.TD);
        htmlWriter.writeWidth(ARROW_IMAGE_WIDTH);
        htmlWriter.writeClass(getMainStyleClassName() + "_buttonCell");

        htmlWriter.startElement(IHtmlWriter.IMG);
        htmlWriter.writeClass(getMainStyleClassName() + "_button");
        htmlWriter.writeWidth(ARROW_IMAGE_WIDTH);
        htmlWriter.writeHeight(ARROW_IMAGE_HEIGHT);

        String url = componentRenderContext.getHtmlRenderContext()
                .getHtmlProcessContext()
                .getStyleSheetURI(BLANK_IMAGE_URL, true);

        htmlWriter.writeSrc(url);

        htmlWriter.endElement(IHtmlWriter.IMG);

        htmlWriter.endElement(IHtmlWriter.TD);

        htmlWriter.endElement(IHtmlWriter.TR);
        htmlWriter.endElement(IHtmlWriter.TBODY);
        htmlWriter.endElement(IHtmlWriter.TABLE);

        htmlWriter.enableJavaScript();

        htmlWriter.endComponent();

        CharArrayWriter buffer = new CharArrayWriter(1024);

        htmlWriter.getComponentRenderContext().setAttribute(GRID_HTML_CONTENT,
                Boolean.TRUE);

        ResponseWriter oldResponseWriter = facesContext.getResponseWriter();
        try {
            ResponseWriter newResponseWriter = oldResponseWriter
                    .cloneWithWriter(buffer);

            facesContext.setResponseWriter(newResponseWriter);

            IHtmlWriter newHtmlWriter = (IHtmlWriter) ((HtmlRenderContext) htmlWriter
                    .getHtmlComponentRenderContext().getRenderContext())
                    .createWriter(comboGridComponent, newResponseWriter);

            super.encodeGrid(newHtmlWriter);

            newHtmlWriter.endComponent();

        } finally {
            facesContext.setResponseWriter(oldResponseWriter);
        }

        htmlWriter.getComponentRenderContext().setAttribute(GRID_HTML_CONTENT,
                buffer.toString());
    }

    protected IHtmlWriter writeIdAttribute(IHtmlWriter htmlWriter)
            throws WriterException {
        IHtmlComponentRenderContext componentRenderContext = htmlWriter
                .getHtmlComponentRenderContext();

        if (Boolean.TRUE.equals(componentRenderContext
                .getAttribute(GRID_HTML_CONTENT))) {
            return super.writeIdAttribute(htmlWriter);
        }

        StringAppender id = new StringAppender(componentRenderContext
                .getComponentClientId(), 16);

        String separator = componentRenderContext.getRenderContext()
                .getProcessContext().getNamingSeparator();
        if (separator != null) {
            id.append(separator);
        } else {
            id.append(NamingContainer.SEPARATOR_CHAR);
        }

        id.append("grid");

        htmlWriter.writeId(id.toString());

        return htmlWriter;
    }

    protected String getRowValueColumnId(IGridComponent gridComponent) {
        return ((ComboGridComponent) gridComponent).getValueColumnId();
    }

    protected void encodeJsBodyRows(IJavaScriptWriter jsWriter,
            AbstractGridRenderContext tableContext) {
        // On génère rien
    }

    protected void encodeJsHeader(IJavaScriptWriter jsWriter,
            AbstractGridRenderContext gridRenderContext) throws WriterException {

        String htmlContent = (String) jsWriter.getComponentRenderContext()
                .removeAttribute(GRID_HTML_CONTENT);

        if (htmlContent != null) {
            jsWriter.writeMethodCall("f_setGridInnerHTML").writeString(
                    htmlContent).write(");");
        }
    }
}
