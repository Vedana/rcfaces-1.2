/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:28  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.12  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.11  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.10  2006/06/28 17:48:28  oeuillot
 * Ajout de dateEntry
 * Ajout D'une constante g�n�rale de sp�cification de l'attributesLocale
 * Ajout d'un attribut <v:init attributesLocale='' />
 *
 * Revision 1.9  2006/06/27 09:23:09  oeuillot
 * Mise � jour du calendrier de dateChooser
 *
 * Revision 1.8  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.7  2006/05/11 16:34:19  oeuillot
 * Correction du calendar
 * Ajout de DateChooser
 * Ajout du moteur de filtre d'images
 * Ajout de l'evt   loadListener pour le AsyncManager
 *
 * Revision 1.6  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.5  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.4  2006/03/15 13:53:04  oeuillot
 * Stabilisation
 * Ajout des bundles pour le javascript
 * R�organisation de l'arborescence de GridData qui n'herite plus de UIData
 *
 * Revision 1.3  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.2  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.1  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.2  2005/12/28 11:12:48  oeuillot
 * Ajout des writer de Menu et ImageButton
 * Split de l'aspect fa_menu
 * Gestion de l'heritage d'aspect !
 *
 * Revision 1.1  2005/12/27 16:08:16  oeuillot
 * Gestion imageButtonWriter
 * Ajout de fa_images
 * Preparation de f_imageCombo
 *
 */
package org.rcfaces.renderkit.html.internal.decorator;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;

import org.rcfaces.core.component.capability.IAccessKeyCapability;
import org.rcfaces.core.component.capability.IHorizontalTextPositionCapability;
import org.rcfaces.core.component.capability.ISelectedCapability;
import org.rcfaces.core.component.capability.ISizeCapability;
import org.rcfaces.core.component.capability.ITextPositionCapability;
import org.rcfaces.core.component.familly.IImageButtonFamilly;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.renderkit.border.IBorderRenderersRegistry;
import org.rcfaces.core.provider.IURLRewritingProvider;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.ICssRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.border.AbstractHtmlBorderRenderer;
import org.rcfaces.renderkit.html.internal.border.IHtmlBorderRenderer;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractImageButtonFamillyDecorator extends
        AbstractComponentDecorator {
    private static final String REVISION = "$Revision$";

    private static final String SELECTED_IMAGE_RENDERED = "imageButton.selected.rendered";

    private static final String DISABLED_IMAGE_RENDERED = "imageButton.disabled.rendered";

    static final String MARKER_IMAGEURL = "blank.gif";

    private static final String IMAGE = "_image";

    private static final String TEXT = "_text";

    protected static final String HALIGN_LEFT = "left";

    protected static final String HALIGN_RIGHT = "right";

    protected static final String HALIGN_CENTER = "center";

    protected static final String VALIGN_CENTER = "middle";

    protected static final String VALIGN_BOTTOM = "bottom";

    protected static final String VALIGN_TOP = "top";

    private static final String NONE_BORDER_ID = "none";

    private static final String EMPTY_IMAGE_URL = "imageButton/blank.gif";

    protected final IImageButtonFamilly imageButtonFamilly;

    protected IHtmlWriter writer;

    protected Renderer renderer;

    private String className;

    protected IJavaScriptWriter javaScriptWriter;

    protected String imageSrc;

    protected String text;

    protected IHtmlBorderRenderer htmlBorderWriter;

    private boolean alignHorizontal;

    protected int textPosition;

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
                selected = ((ISelectedCapability) imageButtonFamilly)
                        .isSelected();
            }

            String width = null;
            String height = null;
            if (imageButtonFamilly instanceof ISizeCapability) {
                ISizeCapability sizeCapability = (ISizeCapability) imageButtonFamilly;

                width = sizeCapability.getWidth();
                if (width != null) {
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

            } else if ((imageButtonFamilly instanceof ISelectedCapability)
                    && ((ISelectedCapability) imageButtonFamilly).isSelected()) {
                classSuffix = "_selected";
            }

            writeAttributes(classSuffix);

            if (classSuffix != null) {
                writer.writeAttribute("v:className", getClassName());
            }

            boolean initJavascript = initializeJavaScript();

            String originalImageSrc = getImageURL(facesContext);
            if (originalImageSrc != null) {
                imageSrc = AbstractCameliaRenderer.rewriteURL(
                        componentRenderContext,
                        IURLRewritingProvider.IMAGE_URL_TYPE, "imageURL",
                        originalImageSrc, null);
            }

            String disabledSrc = getDisabledImageURL(facesContext);
            if (disabledSrc != null) {
                disabledSrc = AbstractCameliaRenderer.rewriteURL(
                        componentRenderContext,
                        IURLRewritingProvider.IMAGE_URL_TYPE,
                        "disabledImageURL", disabledSrc, originalImageSrc);
            }

            if (disabled) {
                if (disabledSrc != null) {
                    initJavascript = true;

                    writer.writeAttribute("v:imageURL", imageSrc);
                    imageSrc = disabledSrc;

                    componentRenderContext.setAttribute(
                            DISABLED_IMAGE_RENDERED, Boolean.TRUE);
                }
                String selectedSrc = getSelectedImageURL(facesContext);
                if (selectedSrc != null) {
                    selectedSrc = AbstractCameliaRenderer.rewriteURL(
                            componentRenderContext,
                            IURLRewritingProvider.IMAGE_URL_TYPE,
                            "selectedImageURL", selectedSrc, originalImageSrc);

                    if (selectedSrc != null) {
                        initJavascript = true;
                        writer
                                .writeAttribute("v:selectedImageURL",
                                        selectedSrc);
                    }
                }

                componentRenderContext.setAttribute(SELECTED_IMAGE_RENDERED,
                        Boolean.TRUE);

            } else {
                String selectedSrc = getSelectedImageURL(facesContext);
                if (selectedSrc != null) {
                    selectedSrc = AbstractCameliaRenderer.rewriteURL(
                            componentRenderContext,
                            IURLRewritingProvider.IMAGE_URL_TYPE,
                            "selectedImageURL", selectedSrc, originalImageSrc);

                    if (selectedSrc != null) {
                        initJavascript = true;

                        if (selected) {
                            if (imageSrc != null) {
                                writer.writeAttribute("v:imageURL", imageSrc);
                            }
                            imageSrc = selectedSrc;

                        } else {
                            writer.writeAttribute("v:selectedImageURL",
                                    selectedSrc);
                        }

                        componentRenderContext.setAttribute(
                                SELECTED_IMAGE_RENDERED, Boolean.TRUE);
                    }
                }

                if (disabledSrc != null) {
                    initJavascript = true;
                    writer.writeAttribute("v:disabledImageURL", disabledSrc);
                    componentRenderContext.setAttribute(
                            DISABLED_IMAGE_RENDERED, Boolean.TRUE);
                }
            }

            if (htmlBorderWriter == null && mainComponent.equals("INPUT")) {
                writer.writeAttribute("type", "image");

                if (imageSrc != null) {
                    writer.writeAttribute("src", imageSrc);
                }
                writer.writeAttribute("align", "baseline");

                writeImageAttributes(writer, imageButtonFamilly);
            }

            String hoverSrc = getHoverImageURL(facesContext);
            if (hoverSrc != null) {
                hoverSrc = AbstractCameliaRenderer.rewriteURL(
                        componentRenderContext,
                        IURLRewritingProvider.IMAGE_URL_TYPE, "hoverImageURL",
                        hoverSrc, originalImageSrc);

                if (hoverSrc != null) {
                    initJavascript = true;
                    writer.writeAttribute("v:hoverImageURL", hoverSrc);
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

    protected void writeEndCompositeComponent() throws WriterException {
    }

    protected void writeImageAttributes(IHtmlWriter writer,
            IImageButtonFamilly imageButtonFamilly) throws WriterException {
        FacesContext facesContext = writer.getComponentRenderContext()
                .getFacesContext();

        if (imageButtonFamilly instanceof IAccessKeyCapability) {
            IAccessKeyCapability accessKeyCapability = (IAccessKeyCapability) imageButtonFamilly;

            String accessKey = accessKeyCapability.getAccessKey();
            if (accessKey != null) {
                writer.writeAttribute("accessKey", accessKey);
            }
        }

        int imageWidth = imageButtonFamilly.getImageWidth(facesContext);
        if (imageWidth > 0) {
            int imageHeight = imageButtonFamilly.getImageHeight(facesContext);
            if (imageHeight > 0) {
                writer.writeAttribute("width", imageWidth);
                writer.writeAttribute("height", imageHeight);
            }
        }
    }

    protected boolean isCompositeComponent() {
        return false;
    }

    protected String getHoverImageURL(FacesContext facesContext) {
        return imageButtonFamilly.getHoverImageURL(facesContext);
    }

    protected String getSelectedImageURL(FacesContext facesContext) {
        return imageButtonFamilly.getSelectedImageURL(facesContext);
    }

    protected String getDisabledImageURL(FacesContext facesContext) {
        return imageButtonFamilly.getDisabledImageURL(facesContext);
    }

    protected String getImageURL(FacesContext facesContext) {
        return imageButtonFamilly.getImageURL(facesContext);
    }

    protected boolean initializeJavaScript() {
        return false;
    }

    protected final String getClassName() {
        if ((renderer instanceof ICssRenderer) == false) {
            throw new FacesException("Can not compute className !");
        }

        if (className != null) {
            return className;
        }

        className = ((ICssRenderer) renderer).getStyleClassName(writer
                .getComponentRenderContext());

        return className;
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
        writer.writeAttribute("type", "image");

        String className = getClassName();

        writer.writeAttribute("class", className + IMAGE);

        writer.writeAttribute("src", url);
        // writer.writeAttribute("align", "baseline");

        if (textPosition == IHorizontalTextPositionCapability.LEFT_POSITION
                || textPosition == IHorizontalTextPositionCapability.RIGHT_POSITION) {
            writer.writeAttribute("style", "vertical-align: middle");
        }

        writeImageAttributes(writer, imageButtonFamilly);

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

        writeImage(halign, VALIGN_CENTER, width, null);

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
        writer.writeAttribute("class", getClassName() + TEXT);

        writeText();

        writer.endElement("SPAN");

        htmlBorderWriter.endChild(writer);
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

    public final void encodeJavaScript(IJavaScriptWriter writer)
            throws WriterException {

        this.javaScriptWriter = writer;
        try {
            IComponentRenderContext componentContext = writer
                    .getComponentRenderContext();

            FacesContext facesContext = componentContext.getFacesContext();

            IImageButtonFamilly component = (IImageButtonFamilly) componentContext
                    .getComponent();

            String originalImageURL = component.getImageURL(facesContext);

            if (componentContext.containsAttribute(DISABLED_IMAGE_RENDERED) == false) {
                String disabledImageURL = component
                        .getDisabledImageURL(facesContext);
                if (disabledImageURL != null) {
                    disabledImageURL = AbstractCameliaRenderer.rewriteURL(
                            componentContext,
                            IURLRewritingProvider.IMAGE_URL_TYPE,
                            "disabledImageURL", disabledImageURL,
                            originalImageURL);

                    if (disabledImageURL != null) {
                        disabledImageURL = writer
                                .allocateString(disabledImageURL);
                        writer.writeMethodCall("f_setDisabledImageURL").write(
                                disabledImageURL).writeln(");");
                    }
                }
            }

            /*
             * String hoverImageURL = component.getHoverImageURL(); if
             * (hoverImageURL != null) {
             * writer.write("f_setHoverImageURL(\"").write(hoverImageURL).writeln(
             * "\");"); }
             */

            if (componentContext.containsAttribute(SELECTED_IMAGE_RENDERED) == false) {
                String selectedImageURL = component
                        .getSelectedImageURL(facesContext);
                if (selectedImageURL != null) {
                    selectedImageURL = AbstractCameliaRenderer.rewriteURL(
                            componentContext,
                            IURLRewritingProvider.IMAGE_URL_TYPE,
                            "selectedImageURL", selectedImageURL,
                            originalImageURL);

                    if (selectedImageURL != null) {
                        selectedImageURL = writer
                                .allocateString(selectedImageURL);
                        writer.writeMethodCall("f_setSelectedImageURL").write(
                                selectedImageURL).writeln(");");
                    }
                }
            }
        } finally {
            this.javaScriptWriter = null;
        }

        super.encodeJavaScript(writer);
    }

    protected String computeBlankImageURL() {
        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        // FacesContext facesContext = componentRenderContext.getFacesContext();

        IHtmlRenderContext htmlRenderContext = (IHtmlRenderContext) componentRenderContext
                .getRenderContext();

        return htmlRenderContext.getHtmlExternalContext().getStyleSheetURI(
                EMPTY_IMAGE_URL);
    }

    protected void writeComboImage(int nextRowCount) throws WriterException {
        htmlBorderWriter.startChild(writer, "_cmarker", HALIGN_CENTER,
                VALIGN_CENTER, "7", null, 1, 1 + nextRowCount);

        writeComboImage();

        htmlBorderWriter.endChild(writer);
    }

    protected void writeComboImage() throws WriterException {
        writer.startElement("IMG");

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();
        IHtmlRenderContext htmlRenderContext = (IHtmlRenderContext) componentRenderContext
                .getRenderContext();

        String imageURL = htmlRenderContext.getHtmlExternalContext()
                .getStyleSheetURI(MARKER_IMAGEURL);
        writer.writeAttribute("class", getClassName() + "_marker");
        writer.writeAttribute("src", imageURL);
        writer.writeAttribute("width", 5);
        writer.writeAttribute("height", 3);
        writer.writeAttribute("valign", "center");
    }
}
