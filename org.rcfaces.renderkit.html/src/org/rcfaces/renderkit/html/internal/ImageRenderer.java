/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.ImageComponent;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.provider.IURLRewritingProvider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ImageRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();
        FacesContext facesContext = componentRenderContext.getFacesContext();

        ImageComponent image = (ImageComponent) componentRenderContext
                .getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement("IMG");
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        String src = image
                .getImageURL(componentRenderContext.getFacesContext());
        if (src != null) {
            src = rewriteURL(componentRenderContext,
                    IURLRewritingProvider.IMAGE_URL_TYPE, "imageURL", src,
                    null, null);

            if (src != null) {
                htmlWriter.writeAttribute("src", src);
            }
        }

        int imageWidth = image.getImageWidth(facesContext);
        if (imageWidth > 0) {
            htmlWriter.writeAttribute("width", imageWidth);
        }

        int imageHeight = image.getImageHeight(facesContext);
        if (imageHeight > 0) {
            htmlWriter.writeAttribute("height", imageHeight);
        }

        htmlWriter.endElement("IMG");

        super.encodeEnd(htmlWriter);
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.IMAGE;
    }
}