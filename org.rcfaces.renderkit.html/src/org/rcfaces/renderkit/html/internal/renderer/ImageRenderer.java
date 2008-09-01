/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.ImageComponent;
import org.rcfaces.core.image.ImageContentInformation;
import org.rcfaces.core.internal.component.IImageAccessors;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ImageRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    private static final String FILTRED_CONTENT_PROPERTY = "camelia.image.filtredContent";

    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        IHtmlComponentRenderContext componentRenderContext = (IHtmlComponentRenderContext) writer
                .getComponentRenderContext();
        FacesContext facesContext = componentRenderContext.getFacesContext();

        ImageComponent image = (ImageComponent) componentRenderContext
                .getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement(IHtmlWriter.IMG);
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        ImageContentInformation imageContentInformation = null;
        IImageAccessors imageAccessors = (IImageAccessors) image
                .getImageAccessors(facesContext);
        String url = null;
        IContentAccessor contentAccessor = imageAccessors.getImageAccessor();
        if (contentAccessor != null) {
            imageContentInformation = new ImageContentInformation();
            imageContentInformation.setComponent(componentRenderContext);

            url = contentAccessor.resolveURL(facesContext,
                    imageContentInformation, null);

            if (imageContentInformation.isFiltredModel()) {
                componentRenderContext.setAttribute(FILTRED_CONTENT_PROPERTY,
                        Boolean.TRUE);

                htmlWriter.writeAttribute("v:filtred", true);

                IFilterProperties filterProperties = image
                        .getFilterProperties();
                if (filterProperties != null
                        && filterProperties.isEmpty() == false) {
                    String filterExpression = HtmlTools.encodeFilterExpression(
                            filterProperties, componentRenderContext
                                    .getRenderContext().getProcessContext(),
                            componentRenderContext.getComponent());
                    htmlWriter.writeAttribute("v:filterExpression",
                            filterExpression);
                }
            }
        }

        if (url == null) {
            url = componentRenderContext.getHtmlRenderContext()
                    .getHtmlProcessContext().getStyleSheetURI(BLANK_IMAGE_URL,
                            true);

            htmlWriter.writeAttribute("v:blank", true);
        }
        htmlWriter.writeSrc(url);

        int imageWidth = image.getImageWidth(facesContext);
        int imageHeight = image.getImageHeight(facesContext);

        if (imageWidth < 0 && imageHeight < 0) {
            imageWidth = imageContentInformation.getImageWidth();
            imageHeight = imageContentInformation.getImageHeight();
        }

        if (imageWidth > 0) {
            htmlWriter.writeWidth(imageWidth);
        }
        if (imageHeight > 0) {
            htmlWriter.writeHeight(imageHeight);
        }

        htmlWriter.endElement(IHtmlWriter.IMG);

        super.encodeEnd(htmlWriter);
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.IMAGE;
    }

    public void addRequiredJavaScriptClassNames(IHtmlWriter htmlWriter,
            IJavaScriptRenderContext javaScriptRenderContext) {
        super.addRequiredJavaScriptClassNames(htmlWriter,
                javaScriptRenderContext);

        if (htmlWriter.getComponentRenderContext().containsAttribute(
                FILTRED_CONTENT_PROPERTY)) {

            // On prend .COMBO en dure, car le filter n'est pas defini pour les
            // classes qui en héritent !
            javaScriptRenderContext.appendRequiredClass(
                    JavaScriptClasses.IMAGE, "filter");
        }
    }
}