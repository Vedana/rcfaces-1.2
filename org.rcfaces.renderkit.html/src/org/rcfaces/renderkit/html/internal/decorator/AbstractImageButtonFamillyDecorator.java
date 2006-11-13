/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.decorator;

import java.io.ObjectInputStream.GetField;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;

import org.rcfaces.core.component.capability.IAccessKeyCapability;
import org.rcfaces.core.component.capability.IFontCapability;
import org.rcfaces.core.component.capability.IForegroundBackgroundColorCapability;
import org.rcfaces.core.component.capability.IHorizontalTextPositionCapability;
import org.rcfaces.core.component.capability.ISelectedCapability;
import org.rcfaces.core.component.capability.ISizeCapability;
import org.rcfaces.core.component.capability.ITextPositionCapability;
import org.rcfaces.core.component.familly.IImageButtonFamilly;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.component.IImageAccessors;
import org.rcfaces.core.internal.component.IStatesImageAccessors;
import org.rcfaces.core.internal.contentAccessor.ContentAccessorFactory;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.images.ImageContentAccessorHandler;
import org.rcfaces.core.internal.images.operation.DisableOperation;
import org.rcfaces.core.internal.images.operation.HoverOperation;
import org.rcfaces.core.internal.images.operation.SelectedOperation;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.renderkit.border.IBorderRenderersRegistry;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.ICssRenderer;
import org.rcfaces.renderkit.html.internal.ICssWriter;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.border.AbstractHtmlBorderRenderer;
import org.rcfaces.renderkit.html.internal.border.IHtmlBorderRenderer;
import org.rcfaces.renderkit.html.internal.border.NoneBorderRenderer;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractImageButtonFamillyDecorator extends
        AbstractComponentDecorator {
    private static final String REVISION = "$Revision$";

    // private static final String SELECTED_IMAGE_RENDERED =
    // "imageButton.selected.rendered";

    // private static final String DISABLED_IMAGE_RENDERED =
    // "imageButton.disabled.rendered";

    private static final String IMAGE_CLASSNAME_SUFFIX = "_image";

    private static final String TEXT_CLASSNAME_SUFFIX = "_text";

    private static final String NONE_BORDER_ID = "none";

    private static final String EMPTY_IMAGE_URL = "imageButton/blank.gif";

    protected final IImageButtonFamilly imageButtonFamilly;

    private IStatesImageAccessors imageAccessors;

    protected IHtmlWriter writer;

    protected Renderer renderer;

    private String className;

    private String mainClassName;

    protected IJavaScriptWriter javaScriptWriter;

    protected String imageSrc;

    protected String text;

    protected IHtmlBorderRenderer htmlBorderWriter;

    private boolean alignHorizontal;

    protected int textPosition;

    protected int imageWidth;

    protected int imageHeight;

    public AbstractImageButtonFamillyDecorator(
            IImageButtonFamilly imageButtonFamilly) {
        this.imageButtonFamilly = imageButtonFamilly;
    }

    public final void encodeContainerEnd(IHtmlWriter writer, Renderer renderer)
            throws WriterException {
        this.writer = writer;
        this.renderer = renderer;
        try {
            IComponentRenderContext componentRenderContext = writer
                    .getComponentRenderContext();

            FacesContext facesContext = componentRenderContext
                    .getFacesContext();

            String borderType = null;
            if (imageButtonFamilly.isBorder(facesContext)) {
                borderType = imageButtonFamilly.getBorderType(facesContext);

                IBorderRenderersRegistry borderRendererRegistry = RcfacesContext
                        .getInstance(facesContext).getBorderRenderersRegistry();

                UIComponent cmp = (UIComponent) imageButtonFamilly;

                htmlBorderWriter = (IHtmlBorderRenderer) borderRendererRegistry
                        .getBorderRenderer(facesContext,
                                RenderKitFactory.HTML_BASIC_RENDER_KIT, cmp
                                        .getFamily(), cmp.getRendererType(),
                                borderType);

            }

            text = imageButtonFamilly.getText(facesContext);
            textPosition = imageButtonFamilly.getTextPosition(facesContext);

            if (htmlBorderWriter == null && (text != null)) {
                IBorderRenderersRegistry borderRendererRegistry = RcfacesContext
                        .getInstance(facesContext).getBorderRenderersRegistry();

                htmlBorderWriter = (IHtmlBorderRenderer) borderRendererRegistry
                        .getBorderRenderer(facesContext,
                                RenderKitFactory.HTML_BASIC_RENDER_KIT, null,
                                null, NONE_BORDER_ID);
            }

            boolean disabled = imageButtonFamilly.isDisabled(facesContext);

            boolean selected = false;
            if (imageButtonFamilly instanceof ISelectedCapability) {
                selected = isSelected((ISelectedCapability) imageButtonFamilly);
            }

            imageWidth = imageButtonFamilly.getImageWidth(facesContext);
            imageHeight = imageButtonFamilly.getImageHeight(facesContext);

            String width = null;
            String height = null;
            if (imageButtonFamilly instanceof ISizeCapability) {
                ISizeCapability sizeCapability = (ISizeCapability) imageButtonFamilly;

                width = sizeCapability.getWidth();
                if (width != null && imageWidth < 1) {
                    alignHorizontal = true;
                }

                height = sizeCapability.getHeight();
            }

            int tableHorizontalSpan = computeHorizontalSpan();
            int tableVerticalSpan = computeVerticalSpan();

            String mainComponent = null;
            if (htmlBorderWriter == null) {
                if (isCompositeComponent()) {
                    if (width == null && height == null) {
                        mainComponent = "SPAN";

                    } else {
                        mainComponent = "DIV";
                    }

                } else {
                    mainComponent = "INPUT";
                }

                writer.startElement(mainComponent);

            } else {
                mainComponent = "DIV";

                writer.startElement(mainComponent);

                htmlBorderWriter.initialize(writer, width, height,
                        tableHorizontalSpan, tableVerticalSpan, disabled,
                        selected);
            }

            if (borderType != null) {
                writer.writeAttribute("v:borderType", borderType);
            }

            String classSuffix = null;
            if (disabled) {
                classSuffix = "_disabled";

            } else if (selected) {
                classSuffix = "_selected";
            }

            writeAttributes(classSuffix);

            if (classSuffix != null) {
                writer.writeAttribute("v:className", getComponentClassName());
            }

            boolean initJavascript = initializeJavaScript();

            IContentAccessor imageAccessor = getImageAccessor(writer);
            IContentAccessor disabledImageAccessor = getDisabledImageAccessor(writer);
            IContentAccessor selectedImageAccessor = getSelectedImageAccessor(writer);
            IContentAccessor hoverImageAccessor = getHoverImageAccessor(writer);

            imageSrc = imageAccessor.resolveURL(facesContext, null, null);

            if (disabled) {
                if (disabledImageAccessor != null) {
                    initJavascript = true;

                    if (imageSrc != null) {
                        writer.writeAttribute("v:imageURL", imageSrc);
                    }

                    imageSrc = disabledImageAccessor.resolveURL(facesContext,
                            null, null);
                }

                if (selectedImageAccessor != null) {
                    String selectedSrc = selectedImageAccessor.resolveURL(
                            facesContext, null, null);

                    if (selectedSrc != null) {
                        initJavascript = true;
                        writer
                                .writeAttribute("v:selectedImageURL",
                                        selectedSrc);
                    }
                }

            } else {
                if (selectedImageAccessor != null) {
                    String selectedImageURL = selectedImageAccessor.resolveURL(
                            facesContext, null, null);

                    if (selectedImageURL != null) {
                        initJavascript = true;

                        if (selected) {
                            if (imageSrc != null) {
                                writer.writeAttribute("v:imageURL", imageSrc);
                            }
                            imageSrc = selectedImageURL;

                        } else {
                            writer.writeAttribute("v:selectedImageURL",
                                    selectedImageURL);
                        }
                    }
                }

                if (disabledImageAccessor != null) {
                    initJavascript = true;

                    String disabledImageURL = disabledImageAccessor.resolveURL(
                            facesContext, null, null);

                    if (disabledImageURL != null) {
                        writer.writeAttribute("v:disabledImageURL",
                                disabledImageURL);
                    }
                }
            }

            if (htmlBorderWriter == null && mainComponent.equals("INPUT")) {
                writer.writeType("image");

                if (imageSrc != null) {
                    writer.writeSrc(imageSrc);
                }
                writer.writeAlign("baseline");

                writeImageAttributes(writer, imageButtonFamilly);
            }

            if (hoverImageAccessor != null) {
                String hoverImageURL = hoverImageAccessor.resolveURL(
                        facesContext, null, null);

                if (hoverImageURL != null) {
                    initJavascript = true;
                    writer.writeAttribute("v:hoverImageURL", hoverImageURL);
                }
            }

            /*
             * Le javascript s'occupe de ca ! if (button == false &&
             * imageJavascript == false) { writer.writeAttribute("href",
             * "javascript:void(0)"); }
             */

            if (initJavascript) {
                writer.enableJavaScript();
            }

            if (htmlBorderWriter != null) {
                htmlBorderWriter.startComposite(writer);

                if (textPosition == ITextPositionCapability.BOTTOM_POSITION) {
                    writeBottomPosition();

                } else if (textPosition == ITextPositionCapability.TOP_POSITION) {
                    writeTopPosition();

                } else if (textPosition == IHorizontalTextPositionCapability.RIGHT_POSITION) {
                    writeRightPosition();

                } else {
                    writeLeftPosition();
                }

                htmlBorderWriter.endComposite(writer);

            } else if ("INPUT".equals(mainComponent) == false) {
                // htmlBorderWriter=null;

                // Nous sommes dans DIV ou SPAN
                writeImage();
            }

            if (isCompositeComponent()) {
                writeEndCompositeComponent();
            }

            if (mainComponent != null) {
                writer.endElement(mainComponent);
            }

        } finally {
            this.writer = null;
            this.renderer = null;
        }

        super.encodeContainerEnd(writer, renderer);
    }

    protected boolean isSelected(ISelectedCapability imageButtonFamilly) {
        return imageButtonFamilly.isSelected();
    }

    protected void writeEndCompositeComponent() throws WriterException {
    }

    protected void writeImageAttributes(IHtmlWriter writer,
            IImageButtonFamilly imageButtonFamilly) throws WriterException {

        if (imageButtonFamilly instanceof IAccessKeyCapability) {
            IAccessKeyCapability accessKeyCapability = (IAccessKeyCapability) imageButtonFamilly;

            String accessKey = accessKeyCapability.getAccessKey();
            if (accessKey != null) {
                writer.writeAccessKey(accessKey);
            }
        }

        if (imageWidth > 0 && imageHeight > 0) {
            writer.writeWidth(imageWidth);
            writer.writeHeight(imageHeight);
        }
    }

    protected boolean isCompositeComponent() {
        return false;
    }

    protected IStatesImageAccessors getImageButtonAccessors(
            IHtmlWriter htmlWriter) {
        if (imageAccessors != null) {
            return imageAccessors;
        }

        imageAccessors = (IStatesImageAccessors) imageButtonFamilly
                .getImageAccessors(htmlWriter.getComponentRenderContext()
                        .getFacesContext());

        return imageAccessors;
    }

    protected boolean useImageFilterIfNecessery() {
        return false;
    }

    protected IContentAccessor getHoverImageAccessor(IHtmlWriter htmlWriter) {
        IContentAccessor contentAccessor = getImageButtonAccessors(htmlWriter)
                .getHoverImageAccessor();
        if (contentAccessor != null || useImageFilterIfNecessery() == false) {
            return contentAccessor;
        }

        return ContentAccessorFactory.createFromWebResource(null,
                HoverOperation.ID + IContentAccessor.FILTER_SEPARATOR,
                getImageAccessor(htmlWriter));

    }

    protected IContentAccessor getSelectedImageAccessor(IHtmlWriter htmlWriter) {
        IContentAccessor contentAccessor = getImageButtonAccessors(htmlWriter)
                .getSelectedImageAccessor();
        if (contentAccessor != null || useImageFilterIfNecessery() == false) {
            return contentAccessor;
        }

        return ContentAccessorFactory.createFromWebResource(null,
                SelectedOperation.ID + IContentAccessor.FILTER_SEPARATOR,
                getImageAccessor(htmlWriter));

    }

    protected IContentAccessor getDisabledImageAccessor(IHtmlWriter htmlWriter) {
        IContentAccessor contentAccessor = getImageButtonAccessors(htmlWriter)
                .getDisabledImageAccessor();
        if (contentAccessor != null || useImageFilterIfNecessery() == false) {
            return contentAccessor;
        }

        return ContentAccessorFactory.createFromWebResource(null,
                DisableOperation.ID + IContentAccessor.FILTER_SEPARATOR,
                getImageAccessor(htmlWriter));

    }

    protected IContentAccessor getImageAccessor(IHtmlWriter htmlWriter) {
        return getImageButtonAccessors(htmlWriter).getImageAccessor();
    }

    protected boolean initializeJavaScript() {
        return false;
    }

    protected final String getComponentClassName() {
        if ((renderer instanceof ICssRenderer) == false) {
            throw new FacesException("Can not compute className !");
        }

        if (className != null) {
            return className;
        }

        className = ((ICssRenderer) renderer).getStyleClassName(writer
                .getComponentRenderContext().getComponent());

        if (className == null) {
            throw new NullPointerException("Component className is null !");
        }

        return className;
    }

    protected final String getMainClassName() {
        if ((renderer instanceof ICssRenderer) == false) {
            throw new FacesException("Can not compute className !");
        }

        if (mainClassName != null) {
            return mainClassName;
        }

        mainClassName = ((ICssRenderer) renderer).getMainStyleClassName();

        if (mainClassName == null) {
            throw new NullPointerException("Main className is null !");
        }

        return mainClassName;
    }

    protected int computeVerticalSpan() {
        if (text == null) {
            return 1;
        }

        if (textPosition == ITextPositionCapability.BOTTOM_POSITION
                || textPosition == ITextPositionCapability.TOP_POSITION) {
            return 2;
        }

        return 1;
    }

    protected int computeHorizontalSpan() {
        if (text == null) {
            return 1;
        }

        if (textPosition == IHorizontalTextPositionCapability.RIGHT_POSITION
                || textPosition == IHorizontalTextPositionCapability.LEFT_POSITION) {
            return 2;
        }
        return 1;
    }

    protected abstract void writeAttributes(String classSuffix)
            throws WriterException;

    protected void writeImage() throws WriterException {
        String url = imageSrc;
        if (url == null) {
            url = computeBlankImageURL();
        }

        writer.startElement("INPUT");
        writer.writeType("image");

        writer.writeClass(getImageClassName(htmlBorderWriter));

        writer.writeSrc(url);
        // writer.writeAttribute("align", "baseline");

        if (textPosition == IHorizontalTextPositionCapability.LEFT_POSITION
                || textPosition == IHorizontalTextPositionCapability.RIGHT_POSITION) {
            writer.writeStyle().writeVerticalAlign("middle");
        }

        writeImageAttributes(writer, imageButtonFamilly);
    }

    protected String getImageClassName(IHtmlBorderRenderer htmlBorderWriter) {
        return getMainClassName() + IMAGE_CLASSNAME_SUFFIX;
    }

    protected void writeText() throws WriterException {
        if (text == null) {
            return;
        }

        UIComponent component = writer.getComponentRenderContext()
                .getComponent();
        if (component instanceof IAccessKeyCapability) {
            HtmlTools.writeSpanAccessKey(writer,
                    (IAccessKeyCapability) component, text, false);
            return;
        }

        writer.writeText(text);
    }

    protected void writeBottomPosition() throws WriterException {

        writeStartRow();

        String valign = VALIGN_BOTTOM;
        if (isValidText(text) == false) {
            valign = VALIGN_CENTER;
        }

        writeImage(HALIGN_CENTER, valign, null, null);

        if (text != null) {
            writeEndRow(1);

            writeStartRow();

            writeText(HALIGN_CENTER, VALIGN_TOP, null, null);
        }

        writeEndRow(0);
    }

    protected void writeTopPosition() throws WriterException {

        writeStartRow();

        String valign = VALIGN_CENTER;
        if (isValidText(text)) {
            writeText(HALIGN_CENTER, VALIGN_BOTTOM, null, null);

            writeEndRow(1);

            writeStartRow();

            valign = VALIGN_TOP;
        }

        writeImage(HALIGN_CENTER, valign, null, null);

        writeEndRow(0);
    }

    protected void writeEndRow(int nextRowCount) throws WriterException {
        htmlBorderWriter.endRow(writer);
    }

    protected void writeStartRow() throws WriterException {
        htmlBorderWriter.startRow(writer);
    }

    protected void writeLeftPosition() throws WriterException {

        writeStartRow();

        String width = null;
        String halign = HALIGN_CENTER;
        if (isValidText(text)) {
            if (alignHorizontal) {
                width = "50%";
            }
            writeText(HALIGN_RIGHT, VALIGN_CENTER, width, null);
            halign = HALIGN_LEFT;
        }

        if (width == null && imageWidth >= 0) {
            width = String.valueOf(imageWidth);
        }

        writeImage(halign, VALIGN_CENTER, width, null);

        writeEndRow(0);
    }

    protected void writeRightPosition() throws WriterException {
        writeStartRow();

        String width = null;
        String halign = HALIGN_RIGHT;
        if (isValidText(text) == false) {
            halign = HALIGN_CENTER;

        } else if (alignHorizontal) {
            width = "50%";
        }

        String imgWidth = width;
        if (imgWidth == null && imageWidth >= 0) {
            imgWidth = String.valueOf(imageWidth);
            width = null;
        }

        writeImage(halign, VALIGN_CENTER, imgWidth, null);

        if (text != null) {
            writeText(HALIGN_LEFT, VALIGN_CENTER, width, null);
        }

        writeEndRow(0);
    }

    protected boolean isValidText(String text) {
        return (text != null);
    }

    protected void writeText(String halign, String valign, String width,
            String height) throws WriterException {

        htmlBorderWriter.startChild(writer, AbstractHtmlBorderRenderer.TD_TEXT,
                halign, valign, width, height, 1, 1);

        writer.startElement("SPAN");
        writer.writeClass(getTextClassName(htmlBorderWriter));

        ICssWriter cssWriter = writer.writeStyle(128);

        UIComponent mainComponent = writer.getComponentRenderContext()
                .getComponent();
        if (mainComponent instanceof IFontCapability) {
            cssWriter.writeFont((IFontCapability) mainComponent);
        }

        if (mainComponent instanceof IForegroundBackgroundColorCapability) {
            cssWriter
                    .writeForeground((IForegroundBackgroundColorCapability) mainComponent);
        }

        writeText();

        writer.endElement("SPAN");

        htmlBorderWriter.endChild(writer);
    }

    protected String getTextClassName(IHtmlBorderRenderer htmlBorderWriter) {
        return getMainClassName() + TEXT_CLASSNAME_SUFFIX;
    }

    protected void writeImage(String halign, String valign, String width,
            String height) throws WriterException {

        htmlBorderWriter.startChild(writer,
                AbstractHtmlBorderRenderer.TD_IMAGE, halign, valign, width,
                height, 1, 1);

        // En effet notre image est déjà affichée dans ce cas !
        writeImage();

        htmlBorderWriter.endChild(writer);
    }

    protected String computeBlankImageURL() {
        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        // FacesContext facesContext = componentRenderContext.getFacesContext();

        IHtmlRenderContext htmlRenderContext = (IHtmlRenderContext) componentRenderContext
                .getRenderContext();

        return htmlRenderContext.getHtmlProcessContext().getStyleSheetURI(
                EMPTY_IMAGE_URL, true);
    }

    protected void writeComboImage(int nextRowCount) throws WriterException {
        htmlBorderWriter.startChild(writer, "_cmarker",
                getComboImageHorizontalAlignment(),
                getComboImageVerticalAlignment(), getComboImageWidth(),
                getComboImageHeight(), 1, 1 + nextRowCount);

        writeComboImage();

        htmlBorderWriter.endChild(writer);
    }

    protected String getComboImageWidth() {
        return "7";
    }

    protected String getComboImageHeight() {
        return null;
    }

    protected String getComboImageHorizontalAlignment() {
        return HALIGN_CENTER;
    }

    protected String getComboImageVerticalAlignment() {
        return VALIGN_CENTER;
    }

    protected void writeComboImage() throws WriterException {
        if (htmlBorderWriter == null) {
            NoneBorderRenderer.SINGLETON.writeComboImage(writer,
                    getMainClassName());
            return;
        }
        htmlBorderWriter.writeComboImage(writer, getMainClassName());
    }
}
