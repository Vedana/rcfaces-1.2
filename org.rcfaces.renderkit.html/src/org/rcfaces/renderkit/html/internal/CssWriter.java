package org.rcfaces.renderkit.html.internal;

import org.rcfaces.core.component.capability.IBackgroundImageCapability;
import org.rcfaces.core.component.capability.IFontCapability;
import org.rcfaces.core.component.capability.IForegroundBackgroundColorCapability;
import org.rcfaces.core.component.capability.IHiddenModeCapability;
import org.rcfaces.core.component.capability.IMarginCapability;
import org.rcfaces.core.component.capability.IPositionCapability;
import org.rcfaces.core.component.capability.ISizeCapability;
import org.rcfaces.core.component.capability.ITextAlignmentCapability;
import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.util.FastWriter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CssWriter extends FastWriter implements ICssWriter {
    private static final String REVISION = "$Revision$";

    protected static final String BACKGROUND = "background";

    protected static final String BACKGROUND_COLOR = "background-color";

    protected static final String BORDER_STYLE = "border-style";

    protected static final String COLOR = "color";

    protected static final String DISPLAY = "display";

    protected static final String FONT = "font";

    protected static final String FONT_FAMILY = "font-family";

    protected static final String FONT_SIZE = "font-size";

    protected static final String FONT_STYLE = "font-style";

    protected static final String FONT_WEIGHT = "font-weight";

    protected static final String HEIGHT = "height";

    protected static final String LEFT = "left";

    protected static final String MARGIN = "margin";

    protected static final String MARGIN_BOTTOM = "margin-bottom";

    protected static final String MARGIN_LEFT = "margin-left";

    protected static final String MARGIN_RIGHT = "margin-right";

    protected static final String MARGIN_TOP = "margin-top";

    protected static final String OVERFLOW = "overflow";

    protected static final String PADDING = "padding";

    protected static final String POSITION = "position";

    protected static final String TEXT_ALIGN = "text-align";

    protected static final String TEXT_DECORATION = "text-decoration";

    protected static final String TOP = "top";

    protected static final String VERTICAL_ALIGN = "vertical-align";

    protected static final String VISIBILITY = "visibility";

    protected static final String WIDTH = "width";

    private final IHtmlWriter htmlWriter;

    private boolean needSpace = false;

    private boolean done = false;

    public CssWriter(IHtmlWriter htmlWriter) {
        this.htmlWriter = htmlWriter;
    }

    public CssWriter(IHtmlWriter htmlWriter, int initialSize) {
        super(initialSize);

        this.htmlWriter = htmlWriter;
    }

    public void done() throws WriterException {
        if (done) {
            return;
        }
        done = true;
        close();

        if (this.getSize() < 1) {
            return;
        }

        htmlWriter.writeAttribute("style", getBuffer());
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

        write(name).write(':').write(value);

        needSpace = true;

        return this;
    }

    public ICssWriter writeValue(int value) {
        write(String.valueOf(value));

        return this;
    }

    public ICssWriter writeValue(String text) {
        if (needSpace) {
            write(' ');
        }

        write(text);

        needSpace = true;

        return this;
    }

    public final ICssWriter writeForeground(
            IForegroundBackgroundColorCapability elementUI) {
        String fg = elementUI.getForegroundColor();
        if (fg == null) {
            return this;
        }

        return writeColor(fg);
    }

    public final ICssWriter writeTextAlignment(ITextAlignmentCapability element) {
        String align = element.getTextAlignment();
        if (align == null) {
            return this;
        }

        return writeTextAlign(align);
    }

    public final ICssWriter writeFont(IFontCapability element) {

        Boolean funderline = element.getFontUnderline();
        if (funderline != null) {
            if (funderline.booleanValue()) {
                writeTextDecoration(UNDERLINE);
            } else {
                writeTextDecoration(NONE);
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
                    sb.append(ITALIC);
                } else {
                    sb.append(NORMAL);
                }
            }
            if (fbold != null) {
                if (sb.length() > 0) {
                    sb.append(' ');
                }

                if (fbold.booleanValue()) {
                    sb.append(BOLD);
                } else {
                    sb.append(NORMAL);
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

            writeFont(sb.toString());
            return this;
        }

        if (fontName != null) {
            writeFontFamily(fontName);
        }

        if (fontSize != null) {
            writeFontSize(fontSize);
        }

        if (fbold != null) {
            if (fbold.booleanValue()) {
                writeFontWeight(BOLD);
            } else {
                writeFontWeight(NORMAL);
            }
        }

        if (fitalic != null) {
            if (fitalic.booleanValue()) {
                writeFontStyle(ITALIC);
            } else {
                writeFontStyle(NORMAL);
            }
        }

        return this;
    }

    public ICssWriter writeFontFamily(String fontFamily) {
        return writeProperty(FONT_FAMILY, fontFamily);
    }

    public ICssWriter writeFontSize(String fontSize) {
        return writeProperty(FONT_SIZE, fontSize);
    }

    public ICssWriter writeFontStyle(String fontStyle) {
        return writeProperty(FONT_STYLE, fontStyle);
    }

    public ICssWriter writeFontWeight(String fontWeight) {
        return writeProperty(FONT_WEIGHT, fontWeight);
    }

    public ICssWriter writeFont(String font) {
        return writeProperty(FONT, font);
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

        writePosition(ABSOLUTE);

        if (x != null) {
            writeLeft(AbstractCssRenderer.getSize(x));
        }

        if (y != null) {
            writeTop(AbstractCssRenderer.getSize(y));
        }

        return this;
    }

    public ICssWriter writeTop(String top) {
        return writeProperty(TOP, top);
    }

    public ICssWriter writeLeft(String left) {
        return writeProperty(LEFT, left);
    }

    public ICssWriter writePosition(String position) {
        return writeProperty(POSITION, position);
    }

    public ICssWriter writeTextDecoration(String textDecoration) {
        return writeProperty(TEXT_DECORATION, textDecoration);
    }

    public ICssWriter writeColor(String color) {
        return writeProperty(COLOR, color);
    }

    public ICssWriter writeVisibility(String visibility) {
        return writeProperty(VISIBILITY, visibility);
    }

    public final ICssWriter writeSize(ISizeCapability element) {
        String width = element.getWidth();
        if ((width != null) && width.length() > 0) {
            writeWidth(AbstractCssRenderer.getSize(width));
        }

        String height = element.getHeight();
        if ((height != null) && height.length() > 0) {
            writeHeight(AbstractCssRenderer.getSize(height));
        }

        return this;
    }

    public final ICssWriter writeMargin(IMarginCapability element) {
        int cnt = 0;

        String top = element.getMarginTop();
        if (top != null) {
            top = AbstractCssRenderer.normalizeMarginValue(top);
            if (top != null) {
                cnt++;
            }
        }

        String left = element.getMarginLeft();
        if (left != null) {
            left = AbstractCssRenderer.normalizeMarginValue(left);
            if (left != null) {
                cnt++;
            }
        }

        String right = element.getMarginRight();
        if (right != null) {
            right = AbstractCssRenderer.normalizeMarginValue(right);
            if (right != null) {
                cnt++;
            }
        }

        String bottom = element.getMarginBottom();
        if (bottom != null) {
            bottom = AbstractCssRenderer.normalizeMarginValue(bottom);
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
                        writeMargin(top);
                        return this;
                    }

                    writeMargin(top + " " + left);
                    return this;
                }

                writeMargin(top + " " + left + " " + bottom);
                return this;
            }

            writeMargin(top + " " + left + " " + bottom + " " + right);

            return this;
        }

        if (top != null) {
            writeProperty(MARGIN_TOP, top);
        }
        if (left != null) {
            writeProperty(MARGIN_LEFT, left);
        }
        if (bottom != null) {
            writeProperty(MARGIN_BOTTOM, bottom);
        }
        if (right != null) {
            writeProperty(MARGIN_RIGHT, right);
        }

        return this;
    }

    public final ICssWriter writeBackground(
            IForegroundBackgroundColorCapability foregroundBackgroundColorCapability,
            IBackgroundImageCapability backgroundImageCapability) {
        String backgroundImageURL = null;
        Boolean repeatX = null;
        Boolean repeatY = null;
        String positionX = null;
        String positionY = null;
        String backgroundColor = null;

        if (foregroundBackgroundColorCapability != null) {
            backgroundColor = foregroundBackgroundColorCapability
                    .getBackgroundColor();
        }
        if (backgroundImageCapability != null) {
            backgroundImageURL = backgroundImageCapability
                    .getBackgroundImageURL();
            if (backgroundImageURL != null) {
                repeatX = Boolean.valueOf(backgroundImageCapability
                        .isBackgroundImageHorizontalRepeat());
                repeatY = Boolean.valueOf(backgroundImageCapability
                        .isBackgroundImageVerticalRepeat());
                positionX = backgroundImageCapability
                        .getBackgroundImageHorizontalPosition();
                positionY = backgroundImageCapability
                        .getBackgroundImageVerticalPosition();
            }
        }

        if (backgroundImageURL == null && repeatX == null && repeatY == null
                && positionX == null && positionY == null
                && backgroundColor == null) {
            // Repeat=TRUE est la valeur par d?faut !
            return this;
        }

        return writeBackground(backgroundColor, backgroundImageURL, repeatX,
                repeatY, positionX, positionY);
    }

    public ICssWriter writeBackground(String backgroundColor,
            String backgroundImageURL, Boolean repeatX, Boolean repeatY,
            String positionX, String positionY) {

        writePropertyName(BACKGROUND);

        if (backgroundColor != null) {
            writeValue(backgroundColor);
        }

        if (backgroundImageURL != null) {
            writeURL(backgroundImageURL);
        }

        if (repeatX != null || repeatY != null) {
            if (Boolean.TRUE.equals(repeatX)) {
                writeValue(REPEAT_X);

            } else if (Boolean.TRUE.equals(repeatY)) {
                writeValue(REPEAT_Y);

            } else {
                writeValue(NO_REPEAT);
            }
        }

        if (positionX != null || positionY != null) {
            if (positionX == null || positionX.length() < 1) {
                positionX = "0";

            }
            if (positionY == null || positionY.length() < 1) {
                positionY = "0";
            }
            writeValue(positionX);
            writeValue(positionY);
        }

        return this;
    }

    public final ICssWriter writeVisibility(IVisibilityCapability visibility) {
        Boolean visible = visibility.getVisibleState();
        if (visible == null) {
            return this;
        }

        if (visible.booleanValue()) {
            writeVisibility(INHERIT);
            return this;
        }

        if (visibility instanceof IHiddenModeCapability) {
            IHiddenModeCapability hiddenModeCapability = (IHiddenModeCapability) visibility;

            int hiddenMode = hiddenModeCapability.getHiddenMode();
            if (hiddenMode == 0) {
                hiddenMode = IHiddenModeCapability.DEFAULT_HIDDEN_MODE;
            }

            if (IHiddenModeCapability.IGNORE_HIDDEN_MODE == hiddenMode) {
                writeDisplay(NONE);
                return this;
            }

            writeVisibility(HIDDEN);

            return this;
        }

        writeVisibility(NONE);

        return this;
    }

    public ICssWriter writeOverflow(String overflowValue) {
        return writeProperty(OVERFLOW, overflowValue);
    }

    public ICssWriter writeDisplay(String displayValue) {
        return writeProperty(DISPLAY, displayValue);
    }

    public ICssWriter writeHeight(String heightValue) {
        return writeProperty(HEIGHT, heightValue);
    }

    public ICssWriter writeTextAlign(String textAlignement) {
        return writeProperty(TEXT_ALIGN, textAlignement);
    }

    public ICssWriter writeVerticalAlign(String verticalAlignement) {
        return writeProperty(VERTICAL_ALIGN, verticalAlignement);
    }

    public ICssWriter writeWidth(String widthValue) {
        return writeProperty(WIDTH, widthValue);
    }

    public ICssWriter writeBorderStyle(String borderStyle) {
        return writeProperty(BORDER_STYLE, borderStyle);
    }

    public ICssWriter writeMargin(String margin) {
        return writeProperty(MARGIN, margin);
    }

    public ICssWriter writePadding(String padding) {
        return writeProperty(PADDING, padding);
    }

    public ICssWriter writeURL(String url) {
        writeValue("url('");
        write(url).write("')");

        return this;
    }

    public ICssWriter writeBackgroundColor(String backgroundColor) {
        return writeProperty(BACKGROUND_COLOR, backgroundColor);
    }

}