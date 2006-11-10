/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.border;

import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IHtmlBorderRenderer {

    void initialize(IHtmlWriter writer, String width, String height,
            int horizontalSpan, int verticalSpan, boolean disabled,
            boolean selected) throws WriterException;

    IHtmlWriter startComposite(IHtmlWriter writer) throws WriterException;

    IHtmlWriter startRow(IHtmlWriter writer) throws WriterException;

    IHtmlWriter endRow(IHtmlWriter writer) throws WriterException;

    IHtmlWriter endComposite(IHtmlWriter writer) throws WriterException;

    IHtmlWriter startChild(IHtmlWriter writer, String classSuffix)
            throws WriterException;

    IHtmlWriter startChild(IHtmlWriter writer, String classSuffix,
            String halign, String valign, String width, String height,
            int colspan, int rowspan) throws WriterException;

    IHtmlWriter endChild(IHtmlWriter writer) throws WriterException;

    void writeComboImage(IHtmlWriter writer, String componentClassName) throws WriterException;

    int getWestBorderWidth();

    int getEastBorderWidth();

    int getNorthBorderHeight();

    int getSouthBorderHeight();
}
