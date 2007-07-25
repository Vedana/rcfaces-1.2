/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public class AdditionalInformationRenderer extends AbstractCssRenderer {

    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AdditionalInformationRenderer.class);

    protected void encodeBegin(IComponentWriter writer) throws WriterException {
        super.encodeBegin(writer);

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement(IHtmlWriter.DIV);

        writeComponentAttributes(htmlWriter);

        htmlWriter.getHtmlComponentRenderContext().getHtmlRenderContext()
                .pushInteractiveRenderComponent(htmlWriter);
    }

    protected void writeComponentAttributes(IHtmlWriter htmlWriter)
            throws WriterException {
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.endElement(IHtmlWriter.DIV);

        super.encodeEnd(writer);
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.ADDITIONAL_INFORMATION;
    }

}
