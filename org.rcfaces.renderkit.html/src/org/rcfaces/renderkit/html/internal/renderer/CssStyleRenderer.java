/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.contentAccessor.ContentAccessorFactory;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IContentType;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.component.CssStyleComponent;
import org.rcfaces.renderkit.html.internal.AbstractHtmlRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlProcessContext;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CssStyleRenderer extends AbstractHtmlRenderer {
    private static final String REVISION = "$Revision$";

    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        CssStyleComponent cssStyleComponent = (CssStyleComponent) htmlWriter
                .getComponentRenderContext().getComponent();

        FacesContext facesContext = FacesContext.getCurrentInstance();

        IHtmlProcessContext htmlProcessContext = htmlWriter
                .getHtmlComponentRenderContext().getHtmlRenderContext()
                .getHtmlProcessContext();

        boolean useMetaContentStyleType = htmlProcessContext
                .useMetaContentStyleType();

        String src = cssStyleComponent.getSrc(facesContext);
        if (src != null) {
            IContentAccessor contentAccessor = ContentAccessorFactory
                    .createFromWebResource(facesContext, src,
                            IContentType.STYLE);

            src = contentAccessor.resolveURL(facesContext, null, null);
            if (src != null) {
                htmlWriter.startElement("LINK");
                htmlWriter.writeRel("stylesheet");
                if (useMetaContentStyleType == false) {
                    htmlWriter.writeType(IHtmlRenderContext.CSS_TYPE);
                }

                htmlWriter.writeHRef(src);

                htmlWriter.endElement("LINK");
            }
        }

        String text = cssStyleComponent.getText(facesContext);
        if (text != null && text.trim().length() > 0) {
            htmlWriter.startElement("STYLE");
            if (useMetaContentStyleType == false) {
                htmlWriter.writeType(IHtmlRenderContext.CSS_TYPE);
            }

            htmlWriter.write(text);

            htmlWriter.endElement("STYLE");

        }
    }
}
