/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.io.CharArrayWriter;

import javax.faces.FacesException;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIColumn;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ComboGridComponent;
import org.rcfaces.core.component.capability.ISizeCapability;
import org.rcfaces.core.component.iterator.IColumnIterator;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.renderkit.IScriptRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.model.ISortedComponent;
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

        AbstractGridRenderContext gridRenderContext = getGridRenderContext(componentRenderContext);

        int rows = gridRenderContext.getRows();
        if (rows > 0) {
            htmlWriter.writeAttribute("v:rows", rows);
        }
        if (gridRenderContext.isPaged() == false) {
            htmlWriter.writeAttribute("v:paged", "false");
        }

        String rowStyleClasses[] = gridRenderContext.getRowStyleClasses();

        if (rowStyleClasses != null) {
            StringAppender sa = new StringAppender(rowStyleClasses.length * 32);

            for (int i = 0; i < rowStyleClasses.length; i++) {
                String token = rowStyleClasses[i];

                if (sa.length() > 0) {
                    sa.append(',');
                }
                sa.append(token);
            }

            htmlWriter.writeAttribute("v:rowStyleClass", sa.toString());
        }

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
        
        System.out.println("Buffer="+buffer);

        htmlWriter.getComponentRenderContext().setAttribute(GRID_HTML_CONTENT, buffer.toString());
                
    }

    protected IHtmlWriter writeIdAttribute(IHtmlWriter htmlWriter)
            throws WriterException {
        IHtmlComponentRenderContext componentRenderContext = htmlWriter
                .getHtmlComponentRenderContext();

        if (isDataGridRenderer(htmlWriter) == false) {
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
                    htmlContent).writeln(");");
        }
    }

    public String getComponentStyleClassName(IHtmlWriter htmlWriter) {

        if (isDataGridRenderer(htmlWriter)) {
            return super.getComponentStyleClassName(htmlWriter);
        }

        return getJavaScriptClassName();
    }

    protected boolean isDataGridRenderer(IHtmlWriter htmlWriter) {
        return Boolean.TRUE.equals(htmlWriter.getComponentRenderContext()
                .getAttribute(GRID_HTML_CONTENT));
    }

    public AbstractGridRenderContext createTableContext(
            IHtmlComponentRenderContext componentRenderContext) {
        AbstractGridRenderContext tableContext = new ComboGridRenderContext(
                componentRenderContext);

        return tableContext;
    }

    public DataGridRenderContext createTableContext(
            IProcessContext processContext,
            IScriptRenderContext scriptRenderContext, IGridComponent dg,
            int rowIndex, int forcedRows, ISortedComponent sortedComponents[],
            String filterExpression) {
        DataGridRenderContext tableContext = new ComboGridRenderContext(
                processContext, scriptRenderContext, dg, rowIndex, forcedRows,
                sortedComponents, filterExpression);

        return tableContext;
    }

    protected UIColumn getRowValueColumn(IGridComponent dg) {
        ComboGridComponent dataGridComponent = (ComboGridComponent) dg;

        String valueColumnId = dataGridComponent.getValueColumnId();
        if (valueColumnId != null) {
            for (IColumnIterator it = dg.listColumns(); it.hasNext();) {
                UIColumn column = it.next();
                if (valueColumnId.equals(column.getId()) == false) {
                    continue;
                }

                return column;
            }

            throw new FacesException("Can not find column '" + valueColumnId
                    + "'.");
        }

        return null;
    }

    protected void encodeJsColumns(IJavaScriptWriter htmlWriter,
            AbstractGridRenderContext gridRenderContext) throws WriterException {
        encodeJsColumns(htmlWriter, gridRenderContext, GENERATE_CELL_IMAGES
                | GENERATE_CELL_TEXT | GENERATE_CELL_WIDTH);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public class ComboGridRenderContext extends DataGridRenderContext {

        private static final String REVISION = "$Revision$";

        public ComboGridRenderContext(IProcessContext processContext,
                IScriptRenderContext scriptRenderContext, IGridComponent dg,
                int rowIndex, int forcedRows,
                ISortedComponent[] sortedComponents, String filterExpression) {
            super(processContext, scriptRenderContext, dg, rowIndex,
                    forcedRows, sortedComponents, filterExpression);
        }

        public ComboGridRenderContext(
                IHtmlComponentRenderContext componentRenderContext) {
            super(componentRenderContext);
        }

        protected void computeGridSize(ISizeCapability sizeCapability) {
            this.gridWidth = ((ComboGridComponent) gridComponent)
                    .getPopupWidth();
            this.gridHeight = ((ComboGridComponent) gridComponent)
                    .getPopupHeight();
        }

    }
}
