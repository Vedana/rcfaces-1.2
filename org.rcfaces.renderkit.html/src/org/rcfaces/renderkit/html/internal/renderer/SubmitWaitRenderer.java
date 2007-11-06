/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.SubmitWaitComponent;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.internal.component.IImageAccessors;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.AbstractJavaScriptRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * Renderer du composant <u:submitWait>
 * 
 * @author flefevere (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SubmitWaitRenderer extends AbstractJavaScriptRenderer {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(SubmitWaitRenderer.class);

    private static final String DEFAULT_SUBMIT_WAIT_IMAGE_URL = "waiting/submitWait.gif";

    private static final int DEFAULT_SUBMIT_WAIT_IMAGE_WIDTH = 32;

    private static final int DEFAULT_SUBMIT_WAIT_IMAGE_HEIGHT = 32;

    protected void encodeEnd(IComponentWriter p_Writer) throws WriterException {
        IComponentRenderContext componentRenderContext = p_Writer
                .getComponentRenderContext();
        FacesContext facesContext = componentRenderContext.getFacesContext();

        SubmitWaitComponent submitWaitComponent = (SubmitWaitComponent) componentRenderContext
                .getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) p_Writer;

        htmlWriter.enableJavaScript();

        htmlWriter.startElement(AbstractJavaScriptRenderer.LAZY_INIT_TAG);
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);

        /*
         * String backgroundMode = component.getBackgroundMode(facesContext); if
         * (backgroundMode != null) {
         * htmlWriter.writeAttribute("v:backgroundMode", backgroundMode); }
         */

        String width = submitWaitComponent.getWidth(facesContext);
        String height = submitWaitComponent.getHeight(facesContext);

        IContentAccessor imageAccessor = null;

        if (submitWaitComponent.isImageURLSetted()) {

            IContentAccessors contentAccessors = submitWaitComponent
                    .getImageAccessors(facesContext);

            if (contentAccessors instanceof IImageAccessors) {
                IImageAccessors imageAccessors = (IImageAccessors) contentAccessors;
                imageAccessor = imageAccessors.getImageAccessor();
            }
        }

        if (imageAccessor == null) {
            imageAccessor = getDefaultImageAccessor(htmlWriter);
            if (imageAccessor != null) {
                width = String.valueOf(getDefaultImageWidth(htmlWriter));
                height = String.valueOf(getDefaultImageHeight(htmlWriter));
            }
        }

        if (imageAccessor != null) {
            String imageSrc = imageAccessor
                    .resolveURL(facesContext, null, null);
            if (imageSrc != null) {
                htmlWriter.writeAttribute("v:imageURL", imageSrc);
            }
        }

        if (width != null) {
            htmlWriter.writeAttribute("v:width", width);
        }

        if (height != null) {
            htmlWriter.writeAttribute("v:height", height);
        }

        String text = submitWaitComponent.getText(facesContext);
        if (text == null) {
            text = getDefaultText(htmlWriter);
        }
        if (text != null) {
            htmlWriter.writeAttribute("v:text", text);
        }

        htmlWriter.endElement(AbstractJavaScriptRenderer.LAZY_INIT_TAG);

        declareLazyJavaScriptRenderer(htmlWriter);

        super.encodeEnd(htmlWriter);
    }

    protected String getDefaultText(IHtmlWriter htmlWriter) {
        return null;
    }

    protected IContentAccessor getDefaultImageAccessor(IHtmlWriter htmlWriter) {
        return htmlWriter.getHtmlComponentRenderContext()
                .getHtmlRenderContext().getHtmlProcessContext()
                .getStyleSheetContentAccessor(DEFAULT_SUBMIT_WAIT_IMAGE_URL,
                        null);

    }

    protected int getDefaultImageWidth(IHtmlWriter htmlWriter) {
        return DEFAULT_SUBMIT_WAIT_IMAGE_WIDTH;
    }

    protected int getDefaultImageHeight(IHtmlWriter htmlWriter) {
        return DEFAULT_SUBMIT_WAIT_IMAGE_HEIGHT;
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.WAITING_SHELL;
    }

    protected boolean sendCompleteComponent() {
        return false;
    }

}
