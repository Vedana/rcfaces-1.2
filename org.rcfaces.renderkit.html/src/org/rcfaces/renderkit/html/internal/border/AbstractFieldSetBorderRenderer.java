/*
 * $Id$
 * 
 * $Log$
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
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.ICssWriter;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public abstract class AbstractFieldSetBorderRenderer extends
        AbstractHtmlBorderRenderer implements ITitledBorderRenderer {
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

        String className = getClassName();

        writer.startElement("TD");
        writer.writeAttribute("class", getClassName() + CELL_HEAD);
        if (horizontalSpan > 1) {
            writer.writeAttribute("colspan", horizontalSpan);
        }

        UIComponent component = writer.getComponentRenderContext()
                .getComponent();

        if (((ISizeCapability) component).getWidth() != null) {
            writer.writeAttribute("width", "100%");
        }

        writer.startElement("TABLE").writeAttribute("class",
                className + TABLE_HEAD);
        writer.writeAttribute("cellspacing", "0");
        writer.writeAttribute("cellpadding", "0");

        writer.startElement("TR").writeAttribute("class", className + ROW_HEAD);

        writer.startElement("TD");

        writer.startElement("LABEL").writeAttribute("class",
                getClassName() + TEXT);
        if (title == null) {
            writer.writeAttribute("style", "display: none");
        }

        writer.flush();

        ICssWriter cssWriter = AbstractCssRenderer.createCssWriter();

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

        cssWriter.close(writer);

        if (title != null) {
            writer.writeText(title);
        }

        writer.endElement("LABEL");

        writer.endElement("TD");

        writer.startElement("TD").writeAttribute("class", className + BORDER_N);

        writer.endElement("TD");

        writer.endElement("TR");

        writer.endElement("TABLE");

        writer.endElement("TD");

        return writer;
    }

    public void setText(IComponentWriter writer, String text) {
        if (text != null && text.length() < 1) {
            text = null;
        }

        this.title = text;
    }

}
