/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class RulerRenderer extends AbstractCssRenderer {
    

    public void encodeEnd(IComponentWriter writer) throws WriterException {

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement(IHtmlWriter.HR);

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        htmlWriter.endElement(IHtmlWriter.HR);

        super.encodeEnd(htmlWriter);
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.RULER;
    }
}