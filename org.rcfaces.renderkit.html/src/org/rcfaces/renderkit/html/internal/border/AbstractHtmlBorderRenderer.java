/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.border;

import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.renderkit.border.AbstractBorderRenderer;
import org.rcfaces.renderkit.html.internal.ICssWriter;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
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

    protected static final String MARKER_IMAGEURL = "blank.gif";

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

        writer.startElement(IHtmlWriter.TR);
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
        writer.startElement(IHtmlWriter.TR);
        writer.writeHeight(getNorthBorderHeight());

        writeCellBorderNorthWest(writer);

        writeCellBorderNorth(writer);

        writeCellBorderNorthEast(writer);

        writer.endElement(IHtmlWriter.TR);

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

        writer.endElement(IHtmlWriter.TR);

        return writer;
    }

    public IHtmlWriter startComposite(IHtmlWriter writer)
            throws WriterException {
        if (noTable) {
            return writer;
        }

        writer.startElement(IHtmlWriter.TABLE);

        String className = getClassName();
        if (className != null) {
            String tableClassName = getTableClassName(writer, disabled,
                    selected);

            writer.writeClass(tableClassName);

            if (tableClassName != className) {
                writer.writeAttribute("v:className", className);
            }
        }
        writer.writeCellPadding(0);
        writer.writeCellSpacing(0);

        if (width != null || height != null) {
            ICssWriter cssWriter = writer.writeStyle(64);

            if (width != null) {
                if (onlyDigit(width)) {
                    width += "px";
                }
                cssWriter.writeWidth(width);
            }
            if (height != null) {
                if (onlyDigit(height)) {
                    height += "px";
                }
                cssWriter.writeHeight(height);
            }
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

        return htmlRenderContext.getHtmlProcessContext().getStyleSheetURI(
                BORDER_BLANK_IMAGEURL, true);
    }

    protected IHtmlWriter writeCellBorderNorthWest(IHtmlWriter writer)
            throws WriterException {
        writer.startElement(IHtmlWriter.TD);
        writer.writeWidth(getWestBorderWidth());
        writer.writeClass(getWestBorderClassName(writer));

        writer.startElement(IHtmlWriter.IMG);
        writer.writeWidth(getWestBorderWidth());
        writer.writeHeight(getNorthBorderHeight());
        writer.writeSrc(getBlankImageURL(writer));
        writer.endElement(IHtmlWriter.IMG);

        writer.endElement(IHtmlWriter.TD);

        return writer;
    }

    protected String getWestBorderClassName(IHtmlWriter writer) {
        return getClassName() + BORDER_NW;
    }

    public int getWestBorderWidth() {
        return 4;
    }

    public int getEastBorderWidth() {
        return 4;
    }

    public int getNorthBorderHeight() {
        return 4;
    }

    public int getSouthBorderHeight() {
        return 4;
    }

    protected IHtmlWriter writeCellBorderNorthEast(IHtmlWriter writer)
            throws WriterException {
        writer.startElement(IHtmlWriter.TD);
        writer.writeWidth(getEastBorderWidth());
        writer.writeClass(getEastBorderClassName(writer));

        writer.startElement(IHtmlWriter.IMG);
        writer.writeWidth(getEastBorderWidth());
        writer.writeHeight(getNorthBorderHeight());
        writer.writeSrc(getBlankImageURL(writer));
        writer.endElement(IHtmlWriter.IMG);

        writer.endElement(IHtmlWriter.TD);

        return writer;
    }

    protected String getEastBorderClassName(IHtmlWriter writer) {
        return getClassName() + BORDER_NE;
    }

    protected IHtmlWriter writeCellBorderSouthWest(IHtmlWriter writer)
            throws WriterException {
        writer.startElement(IHtmlWriter.TD);
        writer.writeClass(getSouthBorderClassName(writer));
        writer.endElement(IHtmlWriter.TD);

        return writer;
    }

    protected String getSouthBorderClassName(IHtmlWriter writer) {
        return getClassName() + BORDER_SW;
    }

    protected IHtmlWriter writeCellBorderSouthEast(IHtmlWriter writer)
            throws WriterException {
        writer.startElement(IHtmlWriter.TD);
        writer.writeClass(getSouthEastBorderClassName(writer));
        writer.endElement(IHtmlWriter.TD);

        return writer;
    }

    protected String getSouthEastBorderClassName(IHtmlWriter writer) {
        return getClassName() + BORDER_SE;
    }

    protected IHtmlWriter writeCellBorderWest(IHtmlWriter writer)
            throws WriterException {
        writer.startElement(IHtmlWriter.TD);
        writer.writeClass(getBorderWestClassName(writer));

        if (verticalSpan > 1) {
            writer.writeRowSpan(verticalSpan);
        }
        writer.endElement(IHtmlWriter.TD);

        return writer;
    }

    protected String getBorderWestClassName(IHtmlWriter writer) {
        return getClassName() + BORDER_W;
    }

    protected IHtmlWriter writeCellBorderEast(IHtmlWriter writer)
            throws WriterException {
        writer.startElement(IHtmlWriter.TD);
        writer.writeClass(getBorderEastClassName(writer));

        if (verticalSpan > 1) {
            writer.writeRowSpan(verticalSpan);
            skipVerticalRow = verticalSpan - 1;
        }

        writer.endElement(IHtmlWriter.TD);

        return writer;
    }

    protected String getBorderEastClassName(IHtmlWriter writer) {
        return getClassName() + BORDER_E;
    }

    protected IHtmlWriter writeCellBorderSouth(IHtmlWriter writer)
            throws WriterException {
        writer.startElement(IHtmlWriter.TD);
        writer.writeClass(getBorderSouthClassName(writer));

        if (horizontalSpan > 1) {
            writer.writeColSpan(horizontalSpan);
        }
        writer.endElement(IHtmlWriter.TD);

        return writer;
    }

    protected String getBorderSouthClassName(IHtmlWriter writer) {
        return getClassName() + BORDER_S;
    }

    protected IHtmlWriter writeCellBorderNorth(IHtmlWriter writer)
            throws WriterException {
        writer.startElement(IHtmlWriter.TD);
        writer.writeClass(getBorderNorthClassName(writer));
        if (horizontalSpan > 1) {
            writer.writeColSpan(horizontalSpan);
        }
        writer.endElement(IHtmlWriter.TD);

        return writer;
    }

    protected String getBorderNorthClassName(IHtmlWriter writer) {
        return getClassName() + BORDER_N;
    }

    public IHtmlWriter endComposite(IHtmlWriter writer) throws WriterException {
        if (noTable) {
            return writer;
        }

        if (hasBorder()) {
            verifyTopBorder(writer);

            writer.startElement(IHtmlWriter.TR);
            writer.writeHeight(getSouthBorderHeight());

            writeCellBorderSouthWest(writer);

            writeCellBorderSouth(writer);

            writeCellBorderSouthEast(writer);

            writer.endElement(IHtmlWriter.TR);
        }

        writer.endElement(IHtmlWriter.TABLE);

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

        writer.startElement(IHtmlWriter.TD);

        String className = getClassName();
        if (className != null) {
            writer.writeClass(className + classSuffix);
        }
        if (halign != null) {
            writer.writeAlign(halign);
        }
        if (valign != null) {
            writer.writeVAlign(valign);
        }
        if (width != null) {
            writer.writeWidth(width);
        }
        if (height != null) {
            writer.writeHeight(height);
        }
        if (colspan > 1) {
            writer.writeColSpan(colspan);
        }
        if (rowspan > 1) {
            writer.writeRowSpan(rowspan);
        }

        return writer;
    }

    public IHtmlWriter endChild(IHtmlWriter writer) throws WriterException {
        if (noTable) {
            return writer;
        }

        writer.endElement(IHtmlWriter.TD);

        return writer;
    }

    protected abstract boolean hasBorder();

    protected abstract String getClassName();

    protected String getTableClassName(IHtmlWriter htmlWriter,
            boolean disabled, boolean selected) {
        String tableClassName = getClassName();

        if (disabled) {
            tableClassName += DISABLED_SUFFIX;

        } else if (selected) {
            tableClassName += SELECTED_SUFFIX;
        }

        return tableClassName;
    }

    public void writeComboImage(IHtmlWriter writer, String componentClassName)
            throws WriterException {

        writer.startElement(IHtmlWriter.IMG);

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();
        IHtmlRenderContext htmlRenderContext = (IHtmlRenderContext) componentRenderContext
                .getRenderContext();

        String imageURL = htmlRenderContext.getHtmlProcessContext()
                .getStyleSheetURI(MARKER_IMAGEURL, true);
        writer.writeClass(getMarkerClassName(writer, componentClassName));
        writer.writeSrc(imageURL);
        writer.writeWidth(5);
        writer.writeHeight(3);
        writer.writeVAlign(getComboImageVerticalAlign(writer));

        writer.endElement(IHtmlWriter.IMG);
    }

    private String getMarkerClassName(IHtmlWriter writer,
            String componentClassName) {
        return componentClassName + "_marker";
    }

    protected String getComboImageVerticalAlign(IHtmlWriter writer) {
        return "center";
    }

}