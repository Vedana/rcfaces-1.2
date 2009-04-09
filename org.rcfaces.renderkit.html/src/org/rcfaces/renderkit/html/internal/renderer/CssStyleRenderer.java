/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.contentAccessor.ContentAccessorFactory;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IGenerationResourceInformation;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.style.IStyleContentAccessorHandler;
import org.rcfaces.core.lang.IContentFamily;
import org.rcfaces.renderkit.html.component.CssStyleComponent;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlProcessContext;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.decorator.FilesCollectorDecorator;
import org.rcfaces.renderkit.html.internal.util.FileItemSource;
import org.rcfaces.renderkit.html.internal.util.UserAgentTools;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CssStyleRenderer extends AbstractFilesCollectorRenderer {
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

        String userAgent = cssStyleComponent.getUserAgent(facesContext);
        if (userAgent != null && userAgent.length() > 0) {
            if (htmlWriter.getHtmlComponentRenderContext()
                    .getHtmlRenderContext().isUserAgentVary() == false) {
                throw new FacesException(
                        "In order to use userAgentVary property, you must declare <v:init userAgentVary=\"true\" ...>");
            }

            if (UserAgentTools.accept(facesContext, cssStyleComponent) == false) {
                return;
            }
        }

        boolean useMetaContentStyleType = htmlProcessContext
                .useMetaContentStyleType();

        String src = cssStyleComponent.getSrc(facesContext);

        FileItemSource sources[] = null;
        FilesCollectorDecorator filesCollectorDecorator = (FilesCollectorDecorator) getComponentDecorator(componentRenderContext);
        if (filesCollectorDecorator != null) {
            sources = filesCollectorDecorator.listSources();
        }

        if (src != null || (sources != null && sources.length > 0)) {

            IGenerationResourceInformation generationInformation = null;
            if (sources != null && sources.length > 0) {
                generationInformation = new FilesCollectorGenerationInformation(
                        sources);

                if (src == null) {
                    src = IStyleContentAccessorHandler.MERGE_FILTER_NAME
                            + IContentAccessor.FILTER_SEPARATOR;
                }
            }

            IContentAccessor contentAccessor = ContentAccessorFactory
                    .createFromWebResource(facesContext, src,
                            IContentFamily.STYLE);

            src = contentAccessor.resolveURL(facesContext, null,
                    generationInformation);
            if (src != null) {
                htmlWriter.startElement(IHtmlWriter.LINK);
                htmlWriter.writeRel(IHtmlRenderContext.STYLESHEET_REL);
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
