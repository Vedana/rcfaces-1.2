/*
 * $Id$
 */
package org.rcfaces.renderkit.svg.internal.renderer;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlElements;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptRenderContext;
import org.rcfaces.renderkit.svg.component.SVGComponent;
import org.rcfaces.renderkit.svg.internal.component.ISVGAccessors;
import org.rcfaces.renderkit.svg.internal.image.SVGGeneratedInformation;
import org.rcfaces.renderkit.svg.internal.image.SVGGenerationInformation;
import org.rcfaces.renderkit.svg.internal.util.ISVGContentFamily;
import org.rcfaces.renderkit.svg.internal.util.SVGContentAccessorFactory;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SVGRenderer extends AbstractCssRenderer {
    private static final String FILTRED_CONTENT_PROPERTY = "org.rcfaces.svg.FILTRED";

    @Override
    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        IHtmlWriter htmlWriter = (IHtmlWriter) writer;
        IHtmlComponentRenderContext componentRenderContext = htmlWriter
                .getHtmlComponentRenderContext();
        FacesContext facesContext = componentRenderContext.getFacesContext();

        SVGComponent svgComponent = (SVGComponent) componentRenderContext
                .getComponent();

        htmlWriter.startElement(IHtmlElements.OBJECT);
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        String width = svgComponent.getWidth(facesContext);
        if (width == null || width.length() < 1) {
            throw new FacesException("Width must be setted for SVG component");
        }

        int svgWidth = computeSize(width, -1, 0);

        String height = svgComponent.getHeight(facesContext);
        if (height == null || height.length() < 1) {
            throw new FacesException("Height must be setted for SVG component");
        }

        int svgHeight = computeSize(height, -1, 0);

        String svgURL = svgComponent.getSvgURL(facesContext);
        if (svgURL == null || svgURL.length() < 1) {
            throw new FacesException("SVG URL must be setted for SVG component");
        }

        ISVGAccessors contentAccessors = SVGContentAccessorFactory
                .createSingleSVGWebResource(facesContext, svgURL,
                        ISVGContentFamily.SVG);

        SVGGenerationInformation generationInformation = null;
        SVGGeneratedInformation generatedInformation = null;

        IContentAccessor contentAccessor = contentAccessors.getSVGAccessor();
        if (contentAccessor != null) {
            generationInformation = new SVGGenerationInformation();

            generationInformation.setComponent(componentRenderContext);

            IFilterProperties filterProperties = svgComponent
                    .getFilterProperties();
            generationInformation.setFilterProperties(filterProperties);

            if (svgWidth > 0) {
                generationInformation.setWidth(svgWidth);
            }

            if (svgHeight > 0) {
                generationInformation.setHeight(svgHeight);
            }

            generatedInformation = new SVGGeneratedInformation();

            svgURL = contentAccessor.resolveURL(facesContext,
                    generatedInformation, generationInformation);

            if (generatedInformation.isFiltredModel()) {
                componentRenderContext.setAttribute(FILTRED_CONTENT_PROPERTY,
                        Boolean.TRUE);

                htmlWriter.writeAttribute("v:filtred", true);

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

        if (generatedInformation != null) {
            int w = generatedInformation.getWidth();
            if (w > 0) {
                svgWidth = w;
            }
            int h = generatedInformation.getHeight();
            if (h > 0) {
                svgHeight = h;
            }
        }

        htmlWriter.writeAttribute("type", "image/svg+xml");

        if (svgURL != null) {
            htmlWriter.writeAttribute("data", svgURL);
        }

        if (svgWidth > 0) {
            htmlWriter.writeWidth(svgWidth);
        }
        if (svgHeight > 0) {
            htmlWriter.writeHeight(svgHeight);
        }

        htmlWriter.endElement(IHtmlElements.OBJECT);

        super.encodeEnd(htmlWriter);
    }

    @Override
    protected String getJavaScriptClassName() {
        return JavaScriptClasses.SVG;
    }

    @Override
    public void addRequiredJavaScriptClassNames(IHtmlWriter htmlWriter,
            IJavaScriptRenderContext javaScriptRenderContext) {
        super.addRequiredJavaScriptClassNames(htmlWriter,
                javaScriptRenderContext);

        if (htmlWriter.getComponentRenderContext().containsAttribute(
                FILTRED_CONTENT_PROPERTY)) {

            // On prend .COMBO en dure, car le filter n'est pas defini pour les
            // classes qui en héritent !
            javaScriptRenderContext.appendRequiredClass(JavaScriptClasses.SVG,
                    "filter");
        }
    }

}
