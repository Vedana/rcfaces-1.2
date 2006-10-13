/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import org.rcfaces.core.component.capability.IBackgroundImageCapability;
import org.rcfaces.core.component.capability.IFontCapability;
import org.rcfaces.core.component.capability.IForegroundBackgroundColorCapability;
import org.rcfaces.core.component.capability.IMarginCapability;
import org.rcfaces.core.component.capability.IPositionCapability;
import org.rcfaces.core.component.capability.ISizeCapability;
import org.rcfaces.core.component.capability.ITextAlignmentCapability;
import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.renderkit.WriterException;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICssWriter {

    String ABSOLUTE = "absolute";

    String BOLD = "bold";

    String HIDDEN = "hidden";

    String INHERIT = "inherit";

    String ITALIC = "italic";

    String NO_REPEAT = "no-repeat";

    String NONE = "none";

    String NORMAL = "normal";

    String REPEAT_X = "repeat-x";

    String REPEAT_Y = "repeat-Y";

    String UNDERLINE = "underline";

    void close() throws WriterException;

    ICssWriter writeProperty(String name, String value);

    ICssWriter writeFont(IFontCapability capability);

    ICssWriter writeFont(String font);

    ICssWriter writeFontFamily(String fontFamily);

    ICssWriter writeFontSize(String fontSize);

    ICssWriter writeFontStyle(String fontStyle);

    ICssWriter writeFontWeight(String fontWeight);

    ICssWriter writeTextAlignment(ITextAlignmentCapability capability);

    ICssWriter writeForeground(IForegroundBackgroundColorCapability capability);

    ICssWriter writeColor(String color);

    ICssWriter writeVisibility(String visibility);

    ICssWriter writePosition(IPositionCapability capability);

    ICssWriter writePosition(String position);

    ICssWriter writeTextDecoration(String textDecoration);

    ICssWriter writeTop(String top);

    ICssWriter writeLeft(String left);

    ICssWriter writeSize(ISizeCapability capability);

    ICssWriter writeMargin(IMarginCapability capability);

    ICssWriter writeVisibility(IVisibilityCapability capability);

    ICssWriter writeBackground(
            IForegroundBackgroundColorCapability foregroundBackgroundColorCapability,
            IBackgroundImageCapability backgroundImageCapability);

    ICssWriter writeBackground(String backgroundColor,
            String backgroundImageURL, Boolean repeatX, Boolean repeatY,
            String positionX, String positionY);

    ICssWriter writeOverflow(String overflowValue);

    ICssWriter writeDisplay(String displayValue);

    ICssWriter writeWidth(String widthValue);

    ICssWriter writeHeight(String heightValue);

    ICssWriter writeTextAlign(String textAlignement);

    ICssWriter writeVerticalAlign(String verticalAlignement);

    ICssWriter writeBorderStyle(String borderStyle);

    ICssWriter writeMargin(String margin);
}