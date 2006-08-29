/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.32  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.31  2006/05/04 13:40:13  oeuillot
 * Ajout de f_findComponent cot� client
 *
 * Revision 1.30  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.29  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.28  2006/03/23 19:12:39  oeuillot
 * Ajout des marges
 * Ajout des processors
 * Amelioration des menus
 *
 * Revision 1.27  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.26  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.25  2005/12/27 16:08:16  oeuillot
 * Gestion imageButtonWriter
 * Ajout de fa_images
 * Preparation de f_imageCombo
 *
 * Revision 1.24  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.23  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.22  2005/10/05 14:34:19  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.21  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.20  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.19  2004/12/30 17:24:20  oeuillot
 * Gestion des validateurs
 * Debuggage des composants
 *
 * Revision 1.18  2004/12/24 15:10:04  oeuillot
 * Refonte des tabbedPanes
 * Correction de problemes de value sur FieldSet nottament
 *
 * Revision 1.17  2004/12/22 12:16:15  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import org.rcfaces.core.component.capability.IBackgroundImageCapability;
import org.rcfaces.core.component.capability.IFontCapability;
import org.rcfaces.core.component.capability.IForegroundBackgroundColorCapability;
import org.rcfaces.core.component.capability.IMarginCapability;
import org.rcfaces.core.component.capability.IPositionCapability;
import org.rcfaces.core.component.capability.ISizeCapability;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import org.rcfaces.core.component.capability.ITextAlignmentCapability;
import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.codec.StringAppender;
import org.rcfaces.core.internal.converter.HiddenModeConverter;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.util.FastWriter;


/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public abstract class AbstractCssRenderer extends AbstractJavaScriptRenderer
        implements ICssRenderer {

    private static final String REVISION = "$Revision$";

    private static final int DEFAULT_RENDERED_HIDDEN_MODE = IVisibilityCapability.IGNORE_HIDDEN_MODE;

    private static final String DEFAULT_MARGIN_UNIT = "px";

    public static final String BLANK_IMAGE_URL = "blank.gif";

    private static final boolean NORMALIZE_MARGIN = true;

    protected String getDefaultCssClassName() {
        return getJavaScriptClassName();
    }

    protected IHtmlWriter writeStyleClass(IHtmlWriter writer, String classSuffix)
            throws WriterException {

        String cssClass = getStyleClassName(writer.getComponentRenderContext(),
                writer.getComponentRenderContext().getComponent());
        if (cssClass != null) {
            if (classSuffix != null && classSuffix.length() > 0) {
                cssClass += classSuffix;
            }
            writer.writeAttribute("class", cssClass);
        }

        return writer;
    }

    public final String getStyleClassName(
            IComponentRenderContext componentRenderContext) {
        return getStyleClassName(componentRenderContext, componentRenderContext
                .getComponent(), null);
    }

    public final String getStyleClassName(
            IComponentRenderContext componentRenderContext,
            UIComponent component) {
        return getStyleClassName(componentRenderContext, component, null);
    }

    public String getStyleClassName(
            IComponentRenderContext componentRenderContext,
            UIComponent component, String suffix) {
        if (component instanceof IStyleClassCapability) {
            IStyleClassCapability styleClassCapability = (IStyleClassCapability) component;

            String cssClass = styleClassCapability.getStyleClass();
            if (cssClass != null) {
                if (suffix == null) {
                    return cssClass;
                }
                return cssClass + suffix;
            }
        }

        String cssClass = getDefaultCssClassName();
        if (suffix == null) {
            return cssClass;
        }
        return cssClass + suffix;
    }

    protected final IHtmlWriter writeCssAttributes(IHtmlWriter writer)
            throws WriterException {
        return writeCssAttributes(writer, null);
    }

    protected final IHtmlWriter writeCssAttributes(IHtmlWriter writer,
            String classSuffix) throws WriterException {
        UIComponent component = writer.getComponentRenderContext()
                .getComponent();

        writeStyleClass(writer, classSuffix);

        CssWriter cssWriter = new CssWriter();

        String hiddenMode = null;

        if (component instanceof IVisibilityCapability) {
            IVisibilityCapability visibilityCapability = (IVisibilityCapability) component;

            cssWriter.writeVisibility(visibilityCapability);

            int hm = visibilityCapability.getHiddenMode();
            if (hm != DEFAULT_RENDERED_HIDDEN_MODE
                    && hm != IVisibilityCapability.SERVER_HIDDEN_MODE) {
                hiddenMode = HiddenModeConverter.getName(hm);
            }
        }

        if (component instanceof IFontCapability) {
            cssWriter.writeFont((IFontCapability) component);
        }

        if (component instanceof ITextAlignmentCapability) {
            cssWriter.writeTextAlignment((ITextAlignmentCapability) component);
        }

        if (component instanceof IPositionCapability) {
            cssWriter.writePosition((IPositionCapability) component);
        }

        if (component instanceof ISizeCapability) {
            cssWriter.writeSize((ISizeCapability) component);
        }

        if (component instanceof IMarginCapability) {
            cssWriter.writeMargin((IMarginCapability) component);
        }

        IForegroundBackgroundColorCapability foregroundBackgroundColorCapability = null;
        if (component instanceof IForegroundBackgroundColorCapability) {
            foregroundBackgroundColorCapability = (IForegroundBackgroundColorCapability) component;

            cssWriter.writeForeground(foregroundBackgroundColorCapability);
        }

        IBackgroundImageCapability backgroundImageCapability = null;
        if (component instanceof IBackgroundImageCapability) {
            backgroundImageCapability = (IBackgroundImageCapability) component;
        }

        if (backgroundImageCapability != null
                || foregroundBackgroundColorCapability != null) {
            cssWriter.writeBackground(foregroundBackgroundColorCapability,
                    backgroundImageCapability);
        }

        writeCustomCss(writer, cssWriter);

        cssWriter.close(writer);

        if (hiddenMode != null) {
            writer.writeAttribute("v:hiddenMode", hiddenMode);
        }

        return writer;
    }

    protected void writeCustomCss(IHtmlWriter writer, ICssWriter cssWriter) {
    }

    protected static final String getSize(String size) {
        if (size == null) {
            return size;
        }

        int len = size.length();
        if (len < 1) {
            return size;
        }

        if (Character.isDigit(size.charAt(len - 1)) == false) {
            return size;
        }

        return size + "px";
    }

    protected static final String computeSize(String size, int parentWidth,
            int delta) {
        int v = getPixelSize(size, parentWidth);
        if (v < 0) {
            return null;
        }

        return (v + delta) + "px";
    }

    protected static final int getPixelSize(String size, int parentWidth) {
        if (size == null) {
            return -1;
        }

        size = size.trim();

        int len = size.length();
        if (len < 1) {
            return -1;
        }

        boolean unitPX = false;
        for (; Character.isDigit(size.charAt(len - 1)) == false;) {
            if (size.toLowerCase().endsWith("px") == false) {
                return -1;
            }

            if (unitPX) {
                throw new FacesException("More one unit for size '" + size
                        + "' ?");
            }

            unitPX = true;

            size = size.substring(0, size.length() - 2).trim();

            len = size.length();
            if (len < 1) {
                throw new FacesException("Bad format of size '" + size
                        + "' ? (only unit)");
            }
        }

        try {
            return Integer.parseInt(size);

        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    public static ICssWriter createCssWriter() {
        return new CssWriter();
    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    private static class CssWriter extends FastWriter implements ICssWriter {
        private static final String REVISION = "$Revision$";

        private CssWriter() {
        }

        public void close(IHtmlWriter writer) throws WriterException {
            if (this.getSize() < 1) {
                return;
            }

            writer.writeAttribute("style", getBuffer());
        }

        public ICssWriter writePropertyName(String name) {
            ensure(name.length() + 2 + 2);

            if (this.getSize() > 0) {
                write("; ");
            }

            write(name).write(':');

            return this;

        }

        public ICssWriter writeProperty(String name, String value) {

            ensure(name.length() + 2 + value.length() + 2);

            if (this.getSize() > 0) {
                write("; ");
            }

            write(name).write(":").write(value);

            return this;
        }

        public ICssWriter writeValue(char character) {
            write(character);

            return this;
        }

        public ICssWriter writeValue(int value) {
            write(value);

            return this;
        }

        public ICssWriter writeValue(String string) {
            write(string);

            return this;
        }

        public final ICssWriter writeForeground(
                IForegroundBackgroundColorCapability elementUI) {
            String fg = elementUI.getForegroundColor();
            if (fg == null) {
                return this;
            }

            return writeProperty("color", fg);
        }

        public final ICssWriter writeTextAlignment(
                ITextAlignmentCapability element) {
            String align = element.getTextAlignment();
            if (align == null) {
                return this;
            }

            return writeProperty("text-align", align);
        }

        public final ICssWriter writeFont(IFontCapability element) {

            Boolean funderline = element.getFontUnderline();
            if (funderline != null) {
                if (funderline.booleanValue()) {
                    writeProperty("text-decoration", "underline");
                } else {
                    writeProperty("text-decoration", "none");
                }
            }

            String fontName = element.getFontName();
            String fontSize = element.getFontSize();
            Boolean fbold = element.getFontBold();
            Boolean fitalic = element.getFontItalic();

            if (fontSize != null) {
                StringAppender sb = new StringAppender(4 * 16);

                if (fitalic != null) {
                    if (fitalic.booleanValue()) {
                        sb.append("italic");
                    } else {
                        sb.append("normal");
                    }
                }
                if (fbold != null) {
                    if (sb.length() > 0) {
                        sb.append(' ');
                    }

                    if (fbold.booleanValue()) {
                        sb.append("bold");
                    } else {
                        sb.append("normal");
                    }
                }

                if (sb.length() > 0) {
                    sb.append(' ');
                }

                sb.append(fontSize);

                if (fontName != null) {
                    sb.append(' ');

                    sb.append(fontName);
                }

                writeProperty("font", sb.toString());
                return this;
            }

            if (fontName != null) {
                writeProperty("font-family", fontName);
            }

            if (fontSize != null) {
                writeProperty("font-size", fontSize);
            }

            if (fbold != null) {
                if (fbold.booleanValue()) {
                    writeProperty("font-weight", "bold");
                } else {
                    writeProperty("font-weight", "normal");
                }
            }

            if (fitalic != null) {
                if (fitalic.booleanValue()) {
                    writeProperty("font-style", "italic");
                } else {
                    writeProperty("font-style", "normal");
                }
            }

            return this;
        }

        public final ICssWriter writePosition(IPositionCapability element) {
            String x = element.getX();
            if (x != null && x.length() == 0) {
                x = null;
            }
            String y = element.getY();
            if (y != null && y.length() == 0) {
                y = null;
            }

            if (x == null && y == null) {
                return this;
            }

            writeProperty("position", "absolute");

            if (x != null) {
                writeProperty("left", AbstractCssRenderer.getSize(x));
            }

            if (y != null) {
                writeProperty("top", AbstractCssRenderer.getSize(y));
            }

            return this;
        }

        public final ICssWriter writeSize(ISizeCapability element) {
            String width = element.getWidth();
            if ((width != null) && width.length() > 0) {
                writeProperty("width", AbstractCssRenderer.getSize(width));
            }

            String height = element.getHeight();
            if ((height != null) && height.length() > 0) {
                writeProperty("height", AbstractCssRenderer.getSize(height));
            }

            return this;
        }

        public final ICssWriter writeMargin(IMarginCapability element) {
            int cnt = 0;

            String top = element.getMarginTop();
            if (top != null) {
                top = normalizeMarginValue(top);
                if (top != null) {
                    cnt++;
                }
            }

            String left = element.getMarginLeft();
            if (left != null) {
                left = normalizeMarginValue(left);
                if (left != null) {
                    cnt++;
                }
            }

            String right = element.getMarginRight();
            if (right != null) {
                right = normalizeMarginValue(right);
                if (right != null) {
                    cnt++;
                }
            }

            String bottom = element.getMarginBottom();
            if (bottom != null) {
                bottom = normalizeMarginValue(bottom);
                if (bottom != null) {
                    cnt++;
                }
            }

            if (cnt < 1) {
                return this;
            }

            if (cnt > 3) {
                if (right.equals(left)) {
                    if (top.equals(bottom)) {
                        if (top.equals(right)) {
                            writeProperty("margin", top);
                            return this;
                        }

                        writeProperty("margin", top + " " + left);
                        return this;
                    }

                    writeProperty("margin", top + " " + left + " " + bottom);
                    return this;
                }

                writeProperty("margin", top + " " + left + " " + bottom + " "
                        + right);

                return this;
            }

            if (top != null) {
                writeProperty("margin-top", top);
            }
            if (left != null) {
                writeProperty("margin-left", left);
            }
            if (bottom != null) {
                writeProperty("margin-bottom", bottom);
            }
            if (right != null) {
                writeProperty("margin-right", right);
            }

            return this;
        }

        public final ICssWriter writeBackground(
                IForegroundBackgroundColorCapability foregroundBackgroundColorCapability,
                IBackgroundImageCapability backgroundImageCapability) {
            String backgroundImageURL = null;
            boolean repeatX = true;
            boolean repeatY = true;
            int positionX = 0;
            int positionY = 0;
            String backgroundColor = null;

            if (foregroundBackgroundColorCapability != null) {
                backgroundColor = foregroundBackgroundColorCapability
                        .getBackgroundColor();
            }
            if (backgroundImageCapability != null) {
                backgroundImageURL = backgroundImageCapability
                        .getBackgroundImageURL();
                if (backgroundImageURL != null) {
                    repeatX = backgroundImageCapability
                            .isBackgroundImageHorizontalRepeat();
                    repeatY = backgroundImageCapability
                            .isBackgroundImageVerticalRepeat();
                    positionX = backgroundImageCapability
                            .getBackgroundImageHorizontalPosition();
                    positionY = backgroundImageCapability
                            .getBackgroundImageVerticalPosition();
                }
            }

            if (backgroundImageURL == null && repeatX == true
                    && repeatY == true && positionX == 0 && positionY == 0
                    && backgroundColor == null) {
                // Repeat=TRUE est la valeur par d�faut !
                return this;
            }

            writePropertyName("background");

            if (backgroundColor != null) {
                writeValue(' ').writeValue(backgroundColor);
            }

            if (backgroundImageURL != null) {
                writeValue(" url('").writeValue(backgroundImageURL).writeValue(
                        "')");
            }

            if (repeatX == false) {
                if (repeatY == false) {
                    writeValue(" no-repeat");

                } else {
                    writeValue(" repeat-y");
                }

            } else if (repeatY == false) {
                writeValue(" repeat-x");
            }

            if (positionX > 0 || positionY > 0) {
                writeValue(' ').writeValue(positionX).writeValue("% ")
                        .writeValue(positionY).writeValue('%');
            }

            return this;
        }

        public final ICssWriter writeVisibility(IVisibilityCapability visibility) {
            Boolean visible = visibility.getVisible();
            if (visible == null) {
                return this;
            }

            if (visible == Boolean.TRUE || visible.booleanValue()) {
                writeProperty("visibility", "inherit");
                return this;
            }

            int hiddenMode = visibility.getHiddenMode();
            if (hiddenMode == 0) {
                hiddenMode = IVisibilityCapability.DEFAULT_HIDDEN_MODE;
            }

            if (IVisibilityCapability.IGNORE_HIDDEN_MODE == hiddenMode) {
                writeProperty("display", "none");
                return this;
            }

            writeProperty("visibility", "hidden");

            return this;
        }
    }

    protected static String normalizeMarginValue(String value) {
        if (NORMALIZE_MARGIN == false) {
            value = value.trim();
            if (value.length() < 1) {
                return null;
            }

            return value;
        }

        char chs[] = value.toCharArray();

        int i = 0;
        // Recherche le debut !
        for (; i < chs.length; i++) {
            char c = chs[i];
            if (Character.isDigit(c) || c == '-' || c == '.') {
                break;
            }
        }
        if (i == chs.length) {
            return null;
        }

        boolean negative = false;
        char c = chs[i];
        if (c == '-') {
            i++;
            negative = true;
        }

        double v = 0;
        int decimal = -1;
        // Recherche la fin
        for (; i < chs.length; i++) {
            c = chs[i];

            if (c == '.') {
                if (decimal >= 0) {
                    throw new FacesException("Bad margin unit '" + value + "'.");
                }
                decimal = 0;
                continue;
            }

            if (Character.isDigit(c) == false) {
                break;
            }

            if (decimal >= 0) {
                decimal++;
            }

            v = v * 10 + (c - '0');
        }

        if (decimal > 0) {
            v /= Math.pow(10, decimal);
        }

        // Passe des espaces ....
        for (; i < chs.length; i++) {
            c = chs[i];
            if (Character.isWhitespace(c) == false) {
                break;
            }
        }

        if (v == 0.0) {
            if (i < chs.length) {
                c = chs[i];

                if (Character.isLetter(c) == false && c != '%') {
                    throw new FacesException("Bad unit for margin '" + value
                            + "'.");
                }
            }
            return "0";
        }

        if (negative) {
            v = -v;
        }

        // La fin !
        if (i == chs.length) {
            // Pas d'unité, on en ajoute une !
            if (decimal < 1) {
                return Math.floor(v) + DEFAULT_MARGIN_UNIT;
            }
            return v + DEFAULT_MARGIN_UNIT;
        }

        return null;
    }

    protected boolean sendCompleteComponent() {
        return true;
    }
}