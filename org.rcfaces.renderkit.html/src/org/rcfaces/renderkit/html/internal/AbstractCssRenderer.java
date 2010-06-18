/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IBackgroundImageCapability;
import org.rcfaces.core.component.capability.IFontCapability;
import org.rcfaces.core.component.capability.IForegroundBackgroundColorCapability;
import org.rcfaces.core.component.capability.IHiddenModeCapability;
import org.rcfaces.core.component.capability.IMarginCapability;
import org.rcfaces.core.component.capability.IPositionCapability;
import org.rcfaces.core.component.capability.ISeverityStyleClassCapability;
import org.rcfaces.core.component.capability.ISizeCapability;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import org.rcfaces.core.component.capability.ITextAlignmentCapability;
import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.renderer.ICssStyleClasses;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractCssRenderer extends AbstractJavaScriptRenderer
        implements ICssRenderer {

    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(AbstractCssRenderer.class);

    private static final int DEFAULT_RENDERED_HIDDEN_MODE = IHiddenModeCapability.IGNORE_HIDDEN_MODE;

    private static final String DEFAULT_MARGIN_UNIT = "px";

    private static final String CSS_STYLE_CLASSES_PROPERTY_NAME = "org.rcfaces.html.CSS_STYLE_CLASSES";

    public static final String BLANK_IMAGE_URL = "blank.gif";

    protected static final int CSS_ALL_MASK = 0xffff;

    protected static final int CSS_FONT_MASK = 1;

    protected static final int CSS_SIZE_MASK = 2;

    protected static final int SEVERITY_CLASSES_MASK = 4;

    /**
     * @param htmlWriter
     */
    public String getComponentStyleClassName(IHtmlWriter htmlWriter) {
        return getMainStyleClassName();
    }

    public String getMainStyleClassName() {
        return getJavaScriptClassName();
    }

    public final ICssStyleClasses getCssStyleClasses(IHtmlWriter htmlWriter) {

        ICssStyleClasses cssStyleClasses = (ICssStyleClasses) htmlWriter
                .getComponentRenderContext().getAttribute(
                        CSS_STYLE_CLASSES_PROPERTY_NAME);
        if (cssStyleClasses != null) {
            return cssStyleClasses;
        }

        cssStyleClasses = createStyleClasses(htmlWriter);

        htmlWriter.getComponentRenderContext().setAttribute(
                CSS_STYLE_CLASSES_PROPERTY_NAME, cssStyleClasses);

        return cssStyleClasses;
    }

    protected ICssStyleClasses createStyleClasses(IHtmlWriter htmlWriter) {
        return new CssStyleClasses(getMainStyleClassName(),
                getComponentStyleClassName(htmlWriter));
    }

    protected IHtmlWriter writeStyleClass(IHtmlWriter writer,
            ICssStyleClasses cssStyleClasses) throws WriterException {
        UIComponent component = writer.getComponentRenderContext()
                .getComponent();

        if (component instanceof IStyleClassCapability) {
            IStyleClassCapability styleClassCapability = (IStyleClassCapability) component;

            String styleClass = styleClassCapability.getStyleClass();
            if (styleClass != null) {
                cssStyleClasses.addStyleClass(styleClass);
            }

            String sc = cssStyleClasses.constructUserStyleClasses();
            if (sc != null && sc.length() > 0) {
                writer.writeAttribute("v:styleClass", sc);
            }
        }

        String cssClass = cssStyleClasses.constructClassName();
        if (cssClass.length() > 0) {
            writer.writeClass(cssClass);
        }

        return writer;
    }

    /*
     * public final String getStyleClassName(UIComponent component) { return
     * getComponentStyleClassName(); }
     */

    /*
     * protected String computeComponentStyleClass(UIComponent component, String
     * classSuffix) { if (component instanceof IDisabledCapability) { if
     * (((IDisabledCapability) component).isDisabled()) { classSuffix =
     * "_disabled"; } }
     * 
     * if (classSuffix == null) { if (component instanceof IReadOnlyCapability)
     * { if (((IReadOnlyCapability) component).isReadOnly()) { classSuffix =
     * "_readOnly"; } } }
     * 
     * if (component instanceof IRequiredCapability) { if
     * (((IRequiredCapability) component).isRequired()) { classSuffix +=
     * "_required"; } }
     * 
     * return classSuffix; }
     */

    protected final IHtmlWriter writeCssAttributes(IHtmlWriter htmlWriter)
            throws WriterException {
        return writeCssAttributes(htmlWriter, getCssStyleClasses(htmlWriter),
                CSS_ALL_MASK);
    }

    protected final IHtmlWriter writeCssAttributes(IHtmlWriter writer,
            ICssStyleClasses cssStyleClasses, int attributesMask)
            throws WriterException {
        UIComponent component = writer.getComponentRenderContext()
                .getComponent();

        writeStyleClass(writer, cssStyleClasses);

        if ((attributesMask & SEVERITY_CLASSES_MASK) != 0) {
            if (component instanceof ISeverityStyleClassCapability) {
                writeSeverityStyleClasses(writer,
                        (ISeverityStyleClassCapability) component);
            }
        }

        ICssWriter cssWriter = writer.writeStyle();

        int hiddenMode = DEFAULT_RENDERED_HIDDEN_MODE;

        if (component instanceof IVisibilityCapability) {
            IVisibilityCapability visibilityCapability = (IVisibilityCapability) component;

            cssWriter.writeVisibility(visibilityCapability);

            if (visibilityCapability instanceof IHiddenModeCapability) {
                IHiddenModeCapability hiddenModeCapability = (IHiddenModeCapability) visibilityCapability;

                hiddenMode = hiddenModeCapability.getHiddenMode();
                if (hiddenMode == 0) {
                    hiddenMode = DEFAULT_RENDERED_HIDDEN_MODE;
                }
            }
        }

        if ((attributesMask & CSS_FONT_MASK) != 0) {
            if (component instanceof IFontCapability) {
                cssWriter.writeFont((IFontCapability) component);
            }
        }

        if (component instanceof ITextAlignmentCapability) {
            cssWriter.writeTextAlignment((ITextAlignmentCapability) component);
        }

        if (component instanceof IPositionCapability) {
            cssWriter.writePosition((IPositionCapability) component);
        }

        if ((attributesMask & CSS_SIZE_MASK) != 0) {
            if (component instanceof ISizeCapability) {
                cssWriter.writeSize((ISizeCapability) component);
            }
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

        if (hiddenMode != DEFAULT_RENDERED_HIDDEN_MODE) {
            writer.writeAttribute("v:hiddenMode", hiddenMode);
        }

        return writer;
    }

    protected void writeCustomCss(IHtmlWriter writer, ICssWriter cssWriter) {
    }

    public static final String getSize(String size) {
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

    public static final String computeSizeInPixel(String size, int parentWidth,
            int delta) {
        int ssize = computeSize(size, parentWidth, delta);
        if (ssize < 0) {
            return null;
        }

        return ssize + "px";
    }

    public static final int computeSize(String size, int parentWidth, int delta) {
        int v = getPixelSize(size, parentWidth);
        if (v < 0) {
            return v;
        }

        return v + delta;
    }

    public static final int getPixelSize(String size, int parentWidth) {
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
            return Double.valueOf(size).intValue();
        } catch (NumberFormatException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Can not parse int '" + size + "'.", ex);
            }
            return -1;
        }
    }

    protected static String normalizeMarginValue(String value) {
        value = value.trim();

        if (Constants.NORMALIZE_STYLE_MARGINS == false) {
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

            String margin = "0";
            if (LOG.isDebugEnabled()) {
                LOG.debug("Normalize margins original='" + value
                        + "' normalized='" + margin + "'.");
            }

            return margin;
        }

        if (negative) {
            v = -v;
        }

        // La fin !
        String unit;
        if (i == chs.length) {
            // Pas d'unité, on en ajoute une !
            unit = DEFAULT_MARGIN_UNIT;

        } else {
            unit = value.substring(i).trim();
            if (unit.length() < 1) {
                unit = DEFAULT_MARGIN_UNIT;
            }
        }

        String margin;
        if (decimal < 1) {
            margin = Math.floor(v) + DEFAULT_MARGIN_UNIT;
        } else {
            margin = v + DEFAULT_MARGIN_UNIT;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Normalize margins original='" + value + "' normalized='"
                    + margin + "'.");
        }

        return margin;
    }

    protected boolean sendCompleteComponent(
            IHtmlComponentRenderContext htmlComponentContext) {
        return true;
    }
    
    protected final String computeBlankImageURL(IHtmlWriter writer) {

        IHtmlRenderContext htmlRenderContext = writer
                .getHtmlComponentRenderContext().getHtmlRenderContext();

        return htmlRenderContext.getHtmlProcessContext().getStyleSheetURI(
                AbstractCssRenderer.BLANK_IMAGE_URL, true);
    }
}