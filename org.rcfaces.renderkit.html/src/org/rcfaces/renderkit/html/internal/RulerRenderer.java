/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class RulerRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    public void encodeEnd(IComponentWriter writer) throws WriterException {

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement("HR");

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        htmlWriter.endElement("HR");

        super.encodeEnd(htmlWriter);
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.RULER;
    }
}