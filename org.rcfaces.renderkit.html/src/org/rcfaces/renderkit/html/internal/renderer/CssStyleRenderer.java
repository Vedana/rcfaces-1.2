/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.contentAccessor.ContentAccessorFactory;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.lang.IContentFamily;
import org.rcfaces.renderkit.html.component.CssStyleComponent;
import org.rcfaces.renderkit.html.internal.AbstractHtmlRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
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

        IHtmlComponentRenderContext componentRenderContext = htmlWriter
                .getHtmlComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        IHtmlProcessContext htmlProcessContext = componentRenderContext
                .getHtmlRenderContext().getHtmlProcessContext();

        CssStyleComponent cssStyleComponent = (CssStyleComponent) componentRenderContext
                .getComponent();

        boolean useMetaContentStyleType = htmlProcessContext
                .useMetaContentStyleType();

        String src = cssStyleComponent.getSrc(facesContext);
        if (src != null) {
            IContentAccessor contentAccessor = ContentAccessorFactory
                    .createFromWebResource(facesContext, src,
                            IContentFamily.STYLE);

            src = contentAccessor.resolveURL(facesContext, null, null);
            if (src != null) {
                htmlWriter.startElement(IHtmlWriter.LINK);
                htmlWriter.writeRel("stylesheet");
                if (useMetaContentStyleType == false) {
                    htmlWriter.writeType(IHtmlRenderContext.CSS_TYPE);
                }

                htmlWriter.writeHRef(src);

                htmlWriter.endElement(IHtmlWriter.LINK);
            }
        }

        String text = cssStyleComponent.getText(facesContext);
        if (text != null && text.trim().length() > 0) {
            htmlWriter.startElement(IHtmlWriter.STYLE);
            if (useMetaContentStyleType == false) {
                htmlWriter.writeType(IHtmlRenderContext.CSS_TYPE);
            }

            htmlWriter.write(text);

            htmlWriter.endElement(IHtmlWriter.STYLE);

        }
    }

    public boolean getRendersChildren() {
        return true;
    }

    public void encodeChildren(FacesContext facesContext, UIComponent component) {
    }
}
