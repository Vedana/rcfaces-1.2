package org.rcfaces.renderkit.html.internal;

import org.rcfaces.core.component.capability.IBackgroundImageCapability;
import org.rcfaces.core.component.capability.IFontCapability;
import org.rcfaces.core.component.capability.IForegroundBackgroundColorCapability;
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

    public CssWriter() {
    }

    public CssWriter(int initialSize) {
        super(initialSize);
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

        write(name).write(':').write(value);

        return this;
    }

    public ICssWriter writeValue(char character) {
        write(character);

        return this;
    }

    public ICssWriter writeValue(int value) {
        write(String.valueOf(value));

        return this;
    }

    public ICssWriter writeValue(String text) {
        write(text);

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

    public final ICssWriter writeTextAlignment(ITextAlignmentCapability element) {
        String align = element.getTextAlignment();
        if (align == null) {
            return this;
        }

        return writeProperty(TEXT_ALIGN, align);
    }

    public final ICssWriter writeFont(IFontCapability element) {

        Boolean funderline = element.getFontUnderline();
        if (funderline != null) {
            if (funderline.booleanValue()) {
                writeProperty(TEXT_DECORATION, "underline");
            } else {
                writeProperty(TEXT_DECORATION, "none");
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
            writeProperty(LEFT, AbstractCssRenderer.getSize(x));
        }

        if (y != null) {
            writeProperty(TOP, AbstractCssRenderer.getSize(y));
        }

        return this;
    }

    public final ICssWriter writeSize(ISizeCapability element) {
        String width = element.getWidth();
        if ((width != null) && width.length() > 0) {
            writeProperty(WIDTH, AbstractCssRenderer.getSize(width));
        }

        String height = element.getHeight();
        if ((height != null) && height.length() > 0) {
            writeProperty(HEIGHT, AbstractCssRenderer.getSize(height));
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
                        writeProperty(MARGIN, top);
                        return this;
                    }

                    writeProperty(MARGIN, top + " " + left);
                    return this;
                }

                writeProperty(MARGIN, top + " " + left + " " + bottom);
                return this;
            }

            writeProperty(MARGIN, top + " " + left + " " + bottom + " " + right);

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
        boolean repeatX = true;
        boolean repeatY = true;
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

        if (backgroundImageURL == null && repeatX == true && repeatY == true
                && positionX == null && positionY == null
                && backgroundColor == null) {
            // Repeat=TRUE est la valeur par d?faut !
            return this;
        }

        writePropertyName("background");

        if (backgroundColor != null) {
            writeValue(' ').writeValue(backgroundColor);
        }

        if (backgroundImageURL != null) {
            writeValue(" url('").writeValue(backgroundImageURL)
                    .writeValue("')");
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

        if (positionX != null || positionY != null) {
            if (positionX == null || positionX.length() < 1) {
                positionX = "0";

            }
            if (positionY == null || positionY.length() < 1) {
                positionY = "0";
            }
            writeValue(' ').writeValue(positionX).writeValue(' ').writeValue(
                    positionY);
        }

        return this;
    }

    public final ICssWriter writeVisibility(IVisibilityCapability visibility) {
        Boolean visible = visibility.getVisible();
        if (visible == null) {
            return this;
        }

        if (visible == Boolean.TRUE || visible.booleanValue()) {
            writeProperty(VISIBILITY, "inherit");
            return this;
        }

        int hiddenMode = visibility.getHiddenMode();
        if (hiddenMode == 0) {
            hiddenMode = IVisibilityCapability.DEFAULT_HIDDEN_MODE;
        }

        if (IVisibilityCapability.IGNORE_HIDDEN_MODE == hiddenMode) {
            writeProperty(DISPLAY, "none");
            return this;
        }

        writeProperty(VISIBILITY, "hidden");

        return this;
    }
}