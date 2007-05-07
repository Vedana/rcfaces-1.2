/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.border;

import javax.faces.component.UIComponent;

import org.rcfaces.core.component.capability.IFontCapability;
import org.rcfaces.core.component.capability.IForegroundBackgroundColorCapability;
import org.rcfaces.core.component.capability.ISizeCapability;
import org.rcfaces.core.component.capability.ITextAlignmentCapability;
import org.rcfaces.core.component.capability.ITextDirectionCapability;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.renderkit.border.ITitledBorderRenderer;
import org.rcfaces.renderkit.html.internal.AbstractHtmlRenderer;
import org.rcfaces.renderkit.html.internal.ICssWriter;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractFieldSetBorderRenderer extends
        AbstractHtmlBorderRenderer implements IFieldSetBorderRenderer,
        ITitledBorderRenderer {
    private static final String REVISION = "$Revision$";

    private static final String TEXT = "_text";

    private static final String TABLE_HEAD = "_table_head";

    private static final String ROW_HEAD = "_row_head";

    private static final String CELL_HEAD = "_cell_head";

    protected String title;

    protected boolean hasBorder() {
        return true;
    }

    protected IHtmlWriter writeCellBorderNorth(IHtmlWriter writer)
            throws WriterException {

        if (title == null) {
            return super.writeCellBorderNorth(writer);
        }

        writer.startElement(IHtmlWriter.TD);
        writer.writeClass(getCellHeadClassName(writer));
        if (horizontalSpan > 1) {
            writer.writeColSpan(horizontalSpan);
        }

        UIComponent component = writer.getComponentRenderContext()
                .getComponent();

        if (((ISizeCapability) component).getWidth() != null) {
            writer.writeWidth("100%");
        }

        writer.startElement(IHtmlWriter.TABLE);
        writer.writeClass(getTableHeadClassName(writer));
        writer.writeCellSpacing(0);
        writer.writeCellPadding(0);

        writer.startElement(IHtmlWriter.TR);
        writer.writeClass(getRowHeadClassName(writer));

        writer.startElement(IHtmlWriter.TD);

        writer.startElement(IHtmlWriter.LABEL);
        writer.writeClass(getLabelClassName(writer));

        if (component instanceof ITextDirectionCapability) {
            AbstractHtmlRenderer.writeTextDirection(writer,
                    (ITextDirectionCapability) component);
        }

        ICssWriter cssWriter = writer.writeStyle(64);

        if (title == null) {
            cssWriter.writeDisplay(ICssWriter.NONE);
        }

        if (component instanceof IFontCapability) {
            cssWriter.writeFont((IFontCapability) component);
        }

        if (component instanceof ITextAlignmentCapability) {
            cssWriter.writeTextAlignment((ITextAlignmentCapability) component);
        }

        if (component instanceof IForegroundBackgroundColorCapability) {
            cssWriter
                    .writeForeground((IForegroundBackgroundColorCapability) component);
        }

        if (title != null) {
            writer.writeText(title);
        }

        writer.endElement(IHtmlWriter.LABEL);

        writer.endElement(IHtmlWriter.TD);

        writer.startElement(IHtmlWriter.TD);
        writer.writeClass(getBorderNorthClassName(writer));

        writer.endElement(IHtmlWriter.TD);

        writer.endElement(IHtmlWriter.TR);

        writer.endElement(IHtmlWriter.TABLE);

        writer.endElement(IHtmlWriter.TD);

        return writer;
    }

    protected String getTableHeadClassName(IHtmlWriter writer) {
        return getClassName() + TABLE_HEAD;
    }

    protected String getRowHeadClassName(IHtmlWriter writer) {
        return getClassName() + ROW_HEAD;
    }

    protected String getBorderNorthClassName(IHtmlWriter writer) {
        return getClassName() + BORDER_N;
    }

    protected String getLabelClassName(IHtmlWriter writer) {
        return getClassName() + TEXT;
    }

    protected String getCellHeadClassName(IHtmlWriter writer) {
        return getClassName() + CELL_HEAD;
    }

    public void setText(IComponentWriter writer, String text) {
        if (text != null && text.length() < 1) {
            text = null;
        }

        this.title = text;
    }

}
