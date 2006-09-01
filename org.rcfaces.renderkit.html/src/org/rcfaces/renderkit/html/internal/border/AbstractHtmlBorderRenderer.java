package org.rcfaces.renderkit.html.internal.border;

import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.renderkit.border.AbstractBorderRenderer;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.ICssWriter;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public abstract class AbstractHtmlBorderRenderer extends AbstractBorderRenderer
        implements IHtmlBorderRenderer {
    private static final String REVISION = "$Revision$";

    public static final String TD_TEXT = "_ctext";

    public static final String TD_IMAGE = "_cimage";

    protected static final String DISABLED_SUFFIX = "_disabled";

    protected static final String SELECTED_SUFFIX = "_selected";

    protected static final String BORDER_NW = "_1";

    protected static final String BORDER_N = "_2";

    protected static final String BORDER_NE = "_3";

    protected static final String BORDER_E = "_4";

    protected static final String BORDER_SE = "_5";

    protected static final String BORDER_S = "_6";

    protected static final String BORDER_SW = "_7";

    protected static final String BORDER_W = "_8";

    protected static final String BORDER_BLANK_IMAGEURL = "border/blank.gif";

    protected static final String BORDER_BLANK_IMAGEURL_PROPERTY = "camelia.borderWriter.blank.imageURL";

    protected String width;

    protected String height;

    protected int horizontalSpan;

    protected int verticalSpan;

    protected boolean writeTopBorder = false;

    protected int skipVerticalRow = 0;

    protected String blankImageURL;

    private boolean selected;

    private boolean disabled;

    protected boolean noTable = false;

    public void initialize(IHtmlWriter writer, String width, String height,
            int horizontalSpan, int verticalSpan, boolean disabled,
            boolean selected) throws WriterException {
        this.width = width;
        this.height = height;
        this.horizontalSpan = horizontalSpan;
        this.verticalSpan = verticalSpan;
        this.selected = selected;
        this.disabled = disabled;
    }

    public IHtmlWriter startRow(IHtmlWriter writer) throws WriterException {
        if (noTable) {
            return writer;
        }

        verifyTopBorder(writer);

        writer.startElement("TR");
        if (hasBorder() == false) {
            return writer;
        }

        if (skipVerticalRow > 0) {
            return writer;
        }

        return writeCellBorderWest(writer);
    }

    protected final IHtmlWriter verifyTopBorder(IHtmlWriter writer)
            throws WriterException {
        if (this.writeTopBorder == false) {
            return writer;
        }

        this.writeTopBorder = false;

        return writeTopBorder(writer);
    }

    protected IHtmlWriter writeTopBorder(IHtmlWriter writer)
            throws WriterException {
        writer.startElement("TR");
        writer.writeAttribute("height", "4");

        writeCellBorderNorthWest(writer);

        writeCellBorderNorth(writer);

        writeCellBorderNorthEast(writer);

        writer.endElement("TR");

        return writer;
    }

    public IHtmlWriter endRow(IHtmlWriter writer) throws WriterException {
        if (noTable) {
            return writer;
        }

        if (hasBorder()) {
            if (skipVerticalRow < 1) {
                writeCellBorderEast(writer);

            } else {
                skipVerticalRow--;
            }
        }

        writer.endElement("TR");

        return writer;
    }

    public IHtmlWriter startComposite(IHtmlWriter writer)
            throws WriterException {
        if (noTable) {
            return writer;
        }

        writer.startElement("TABLE");

        String className = getClassName();
        if (className != null) {
            String tableClassName = getTableClassName();

            writer.writeAttribute("class", tableClassName);

            if (tableClassName != className) {
                writer.writeAttribute("v:className", className);
            }
        }
        writer.writeAttribute("cellspacing", "0");
        writer.writeAttribute("cellpadding", "0");

        if (width != null || height != null) {
            ICssWriter cssWriter = AbstractCssRenderer.createCssWriter();

            if (width != null) {
                if (onlyDigit(width)) {
                    width += "px";
                }
                cssWriter.writeProperty("width", width);
            }
            if (height != null) {
                if (onlyDigit(height)) {
                    width += "px";
                }
                cssWriter.writeProperty("height", height);
            }

            cssWriter.close(writer);
        }

        this.writeTopBorder = hasBorder();

        return writer;
    }

    private static boolean onlyDigit(String txt) {
        char cs[] = txt.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            char c = cs[i];

            if (Character.isDigit(c) == false) {
                return false;
            }
        }

        return true;
    }

    protected String getBlankImageURL(IHtmlWriter writer) {
        if (blankImageURL != null) {
            return blankImageURL;
        }

        blankImageURL = computeBlankImageURL(writer);

        return blankImageURL;
    }

    protected String computeBlankImageURL(IHtmlWriter writer) {
        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        IHtmlRenderContext htmlRenderContext = (IHtmlRenderContext) componentRenderContext
                .getRenderContext();

        return htmlRenderContext.getHtmlExternalContext().getStyleSheetURI(
                BORDER_BLANK_IMAGEURL);
    }

    protected IHtmlWriter writeCellBorderNorthWest(IHtmlWriter writer)
            throws WriterException {
        writer.startElement("TD");
        writer.writeAttribute("width", getWestBorderWidth());
        writer.writeAttribute("class", getClassName() + BORDER_NW);

        writer.startElement("IMG");
        writer.writeAttribute("width", getWestBorderWidth());
        writer.writeAttribute("height", getNorthBorderHeight());
        writer.writeAttribute("src", getBlankImageURL(writer));

        writer.endElement("TD");

        return writer;
    }

    protected int getWestBorderWidth() {
        return 4;
    }

    protected int getEastBorderWidth() {
        return 4;
    }

    protected int getNorthBorderHeight() {
        return 4;
    }

    protected int getSouthBorderHeight() {
        return 4;
    }

    protected IHtmlWriter writeCellBorderNorthEast(IHtmlWriter writer)
            throws WriterException {
        writer.startElement("TD");
        writer.writeAttribute("width", getEastBorderWidth());
        writer.writeAttribute("class", getClassName() + BORDER_NE);

        writer.startElement("IMG");
        writer.writeAttribute("width", getEastBorderWidth());
        writer.writeAttribute("height", getNorthBorderHeight());
        writer.writeAttribute("src", getBlankImageURL(writer));

        writer.endElement("TD");

        return writer;
    }

    protected IHtmlWriter writeCellBorderSouthWest(IHtmlWriter writer)
            throws WriterException {
        writer.startElement("TD");
        writer.writeAttribute("class", getClassName() + BORDER_SW);
        writer.endElement("TD");

        return writer;
    }

    protected IHtmlWriter writeCellBorderSouthEast(IHtmlWriter writer)
            throws WriterException {
        writer.startElement("TD");
        writer.writeAttribute("class", getClassName() + BORDER_SE);
        writer.endElement("TD");

        return writer;
    }

    protected IHtmlWriter writeCellBorderWest(IHtmlWriter writer)
            throws WriterException {
        writer.startElement("TD");
        writer.writeAttribute("class", getClassName() + BORDER_W);

        if (verticalSpan > 1) {
            writer.writeAttribute("rowspan", verticalSpan);
        }
        writer.endElement("TD");

        return writer;
    }

    protected IHtmlWriter writeCellBorderEast(IHtmlWriter writer)
            throws WriterException {
        writer.startElement("TD");
        writer.writeAttribute("class", getClassName() + BORDER_E);

        if (verticalSpan > 1) {
            writer.writeAttribute("rowspan", verticalSpan);
            skipVerticalRow = verticalSpan - 1;
        }

        writer.endElement("TD");

        return writer;
    }

    protected IHtmlWriter writeCellBorderSouth(IHtmlWriter writer)
            throws WriterException {
        writer.startElement("TD");
        writer.writeAttribute("class", getClassName() + BORDER_S);

        if (horizontalSpan > 1) {
            writer.writeAttribute("colspan", horizontalSpan);
        }
        writer.endElement("TD");

        return writer;
    }

    protected IHtmlWriter writeCellBorderNorth(IHtmlWriter writer)
            throws WriterException {
        writer.startElement("TD");
        writer.writeAttribute("class", getClassName() + BORDER_N);
        if (horizontalSpan > 1) {
            writer.writeAttribute("colspan", horizontalSpan);
        }
        writer.endElement("TD");

        return writer;
    }

    public IHtmlWriter endComposite(IHtmlWriter writer) throws WriterException {
        if (noTable) {
            return writer;
        }

        if (hasBorder()) {
            verifyTopBorder(writer);

            writer.startElement("TR");
            writer.writeAttribute("height", getSouthBorderHeight());

            writeCellBorderSouthWest(writer);

            writeCellBorderSouth(writer);

            writeCellBorderSouthEast(writer);

            writer.endElement("TR");
        }

        writer.endElement("TABLE");

        return writer;
    }

    public IHtmlWriter startChild(IHtmlWriter writer, String classSuffix)
            throws WriterException {
        return startChild(writer, classSuffix, null, null, null, null, 1, 1);
    }

    public IHtmlWriter startChild(IHtmlWriter writer, String classSuffix,
            String halign, String valign, String width, String height,
            int colspan, int rowspan) throws WriterException {
        if (noTable) {
            return writer;
        }

        writer.startElement("TD");

        String className = getClassName();
        if (className != null) {
            writer.writeAttribute("class", className + classSuffix);
        }
        if (halign != null) {
            writer.writeAttribute("align", halign);
        }
        if (valign != null) {
            writer.writeAttribute("valign", valign);
        }
        if (width != null) {
            writer.writeAttribute("width", width);
        }
        if (height != null) {
            writer.writeAttribute("height", height);
        }
        if (colspan > 1) {
            writer.writeAttribute("colspan", colspan);
        }
        if (rowspan > 1) {
            writer.writeAttribute("rowspan", rowspan);
        }

        return writer;
    }

    public IHtmlWriter endChild(IHtmlWriter writer) throws WriterException {
        if (noTable) {
            return writer;
        }

        writer.endElement("TD");

        return writer;
    }

    protected abstract boolean hasBorder();

    protected abstract String getClassName();

    protected String getTableClassName() {
        String tableClassName = getClassName();

        if (disabled) {
            tableClassName += DISABLED_SUFFIX;

        } else if (selected) {
            tableClassName += SELECTED_SUFFIX;
        }

        return tableClassName;
    }
}