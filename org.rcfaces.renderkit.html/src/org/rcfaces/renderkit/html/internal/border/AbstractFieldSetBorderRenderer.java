/*
 * $Id$
 * 
 * $Log$
 * Revision 1.4  2006/11/09 19:08:57  oeuillot
 * *** empty log message ***
 *
 * Revision 1.3  2006/10/13 18:04:38  oeuillot
 * Ajout de:
 * DateEntry
 * StyledMessage
 * MessageFieldSet
 * xxxxConverter
 * Adapter
 *
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:28  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.3  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.2  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.1  2005/11/17 10:04:56  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 */
package org.rcfaces.renderkit.html.internal.border;

import javax.faces.component.UIComponent;

import org.rcfaces.core.component.capability.IFontCapability;
import org.rcfaces.core.component.capability.IForegroundBackgroundColorCapability;
import org.rcfaces.core.component.capability.ISizeCapability;
import org.rcfaces.core.component.capability.ITextAlignmentCapability;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.renderkit.border.ITitledBorderRenderer;
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

        writer.startElement("TD");
        writer.writeClass(getCellHeadClassName(writer));
        if (horizontalSpan > 1) {
            writer.writeColSpan(horizontalSpan);
        }

        UIComponent component = writer.getComponentRenderContext()
                .getComponent();

        if (((ISizeCapability) component).getWidth() != null) {
            writer.writeWidth("100%");
        }

        writer.startElement("TABLE");
        writer.writeClass(getTableHeadClassName(writer));
        writer.writeCellSpacing(0);
        writer.writeCellPadding(0);

        writer.startElement("TR");
        writer.writeClass(getRowHeadClassName(writer));

        writer.startElement("TD");

        writer.startElement("LABEL");
        writer.writeClass(getLabelClassName(writer));


        ICssWriter cssWriter = writer.writeStyle(64);

        if (title == null) {
            cssWriter.writeDisplay("none");
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

        writer.endElement("LABEL");

        writer.endElement("TD");

        writer.startElement("TD");
        writer.writeClass(getBorderNorthClassName(writer));

        writer.endElement("TD");

        writer.endElement("TR");

        writer.endElement("TABLE");

        writer.endElement("TD");

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
