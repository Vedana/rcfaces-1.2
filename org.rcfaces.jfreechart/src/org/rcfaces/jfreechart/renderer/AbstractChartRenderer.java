/*
 * $Id$
 */
package org.rcfaces.jfreechart.renderer;

import java.awt.Color;
import java.awt.Point;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.ui.Drawable;
import org.rcfaces.core.component.capability.IImageFormatCapability;
import org.rcfaces.core.component.capability.ISizeCapability;
import org.rcfaces.core.image.IImageContentModel;
import org.rcfaces.core.internal.contentAccessor.ContentAccessorFactory;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.lang.IContentFamily;
import org.rcfaces.core.model.IContentModel;
import org.rcfaces.jfreechart.util.ColorNameConverter;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlElements;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractChartRenderer extends AbstractCssRenderer {

    private static final Log LOG = LogFactory
            .getLog(AbstractChartRenderer.class);

    private static final String DEFAULT_IMAGE_MIME_TYPE = "image/jpeg";

    protected void encodeBegin(IComponentWriter writer) throws WriterException {
        super.encodeBegin(writer);

        IComponentRenderContext componentContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentContext.getFacesContext();

        UIComponent component = componentContext.getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement(IHtmlElements.IMG);

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        ISizeCapability sizeCapability = (ISizeCapability) component;

        // get pie values
        int chartWidth = computeSize(sizeCapability.getWidth(), -1, 0);
        int chartHeight = computeSize(sizeCapability.getHeight(), -1, 0);

        if (chartWidth < 0 || chartHeight < 0) {
            Point point = getDefaultChartSize(componentContext);
            if (point == null) {
                throw new FacesException("Invalid chart size !");
            }

            chartWidth = point.x;
            chartHeight = point.y;
        }

        Drawable drawable = createChart(componentContext, chartWidth,
                chartHeight);

        IContentModel contentModel = new ChartContentModel();

        GenerationChartInformation generationInformation = new GenerationChartInformation();

        generationInformation.setDrawable(drawable);
        generationInformation.setComponent(componentContext);
        generationInformation.setImageWidth(chartWidth);
        generationInformation.setImageHeight(chartHeight);

        String mimeType = null;
        if (component instanceof IImageFormatCapability) {
            mimeType = ((IImageFormatCapability) component).getImageFormat();
        }

        if (mimeType == null) {
            mimeType = getDefaultImageFormat();
        }

        if (mimeType == null) {
            mimeType = DEFAULT_IMAGE_MIME_TYPE;
        }

        generationInformation.setAttribute(
                IImageContentModel.ENCODER_MIME_TYPE_PROPERTY, mimeType);

        generationInformation
                .setAttribute(
                        IContentModel.AUTO_GENERATE_RESOURCE_KEY_PROPERTY,
                        Boolean.TRUE);

        GeneratedChartInformation generatedImageInformations = new GeneratedChartInformation();

        IContentAccessor contentAccessor = ContentAccessorFactory
                .createFromWebResource(facesContext, contentModel,
                        IContentFamily.IMAGE);

        String url = contentAccessor.resolveURL(facesContext,
                generatedImageInformations, generationInformation);

        if (url == null) {
            url = computeBlankImageURL(htmlWriter);
        }

        htmlWriter.writeSrc(url);

        int imageWidth = chartWidth;
        int imageHeight = chartHeight;

        if (generatedImageInformations != null) {
            int w = generatedImageInformations.getImageWidth();
            if (w > 0) {
                imageWidth = w;
            }
            int h = generatedImageInformations.getImageHeight();
            if (h > 0) {
                imageHeight = h;
            }
        }

        if (imageWidth > 0) {
            htmlWriter.writeWidth(imageWidth);
        }
        if (imageHeight > 0) {
            htmlWriter.writeHeight(imageHeight);
        }

        htmlWriter.endElement(IHtmlElements.IMG);
    }

    protected Point getDefaultChartSize(IComponentRenderContext componentContext) {
        return null;
    }

    protected abstract Drawable createChart(
            IComponentRenderContext componentContext, int chartWidth,
            int chartHeight) throws WriterException;

    protected String getDefaultImageFormat() {
        return DEFAULT_IMAGE_MIME_TYPE;
    }

    protected static Color convertColorName(String colorName) {
        return (Color) ColorNameConverter.SINGLETON.getAsObject(null, null,
                colorName);
    }

}
