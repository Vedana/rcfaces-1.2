/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ComboGridComponent;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ComboGridRenderer extends AbstractGridRenderer {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(ComboGridRenderer.class);

    private static final int ARROW_IMAGE_WIDTH = 20;

    private static final int ARROW_IMAGE_HEIGHT = 20;

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

        String classSuffix = null;
        if (disabled) {
            classSuffix = getMainStyleClassName() + "_disabled";
        }

        String width = comboGridComponent.getWidth(facesContext);

        int colWidth = -1;
        if (width != null) {
            int totalWidth = computeSize(width, 0, 2);

            colWidth = totalWidth - ARROW_IMAGE_WIDTH - 2;
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
        htmlWriter.writeClass(computeComponentStyleClass(comboGridComponent,
                "_inputCell"));

        htmlWriter.startElement(IHtmlWriter.INPUT);
        if (colWidth > 0) {
            htmlWriter.writeStyle().writeWidth(colWidth + "px");
        }

        htmlWriter.writeType(IHtmlWriter.TEXT_INPUT_TYPE);

        htmlWriter.writeClass(getMainStyleClassName() + "_input");

        htmlWriter.endElement(IHtmlWriter.INPUT);

        htmlWriter.endElement(IHtmlWriter.TD);

        htmlWriter.startElement(IHtmlWriter.TD);
        htmlWriter.writeWidth(ARROW_IMAGE_WIDTH);
        htmlWriter.writeClass(getMainStyleClassName() + "_buttonCell");

        htmlWriter.startElement(IHtmlWriter.IMG);
        htmlWriter.writeWidth(ARROW_IMAGE_WIDTH);
        htmlWriter.writeHeight(ARROW_IMAGE_HEIGHT);

        htmlWriter.writeClass(getMainStyleClassName() + "_button");

        htmlWriter.endElement(IHtmlWriter.IMG);

        htmlWriter.endElement(IHtmlWriter.TD);

        htmlWriter.endElement(IHtmlWriter.TR);
        htmlWriter.endElement(IHtmlWriter.TBODY);
        htmlWriter.endElement(IHtmlWriter.TABLE);
    }

    public AbstractGridRenderContext createTableContext(
            IHtmlComponentRenderContext componentRenderContext) {
        // TODO Auto-generated method stub
        return null;
    }

    protected void encodeJsHeader(IJavaScriptWriter htmlWriter,
            AbstractGridRenderContext gridRenderContext) {
    }

    protected void encodeJsBody(IJavaScriptWriter htmlWriter,
            AbstractGridRenderContext gridRenderContext) throws WriterException {
    }

    protected void encodeJsFooter(IJavaScriptWriter htmlWriter,
            AbstractGridRenderContext data) {
    }

    protected void encodeJsColumns(IJavaScriptWriter htmlWriter,
            AbstractGridRenderContext gridRenderContext) throws WriterException {
        // TODO Auto-generated method stub

    }

}
