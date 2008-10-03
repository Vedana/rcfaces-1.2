package org.rcfaces.renderkit.svg.component;

import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.component.PropertiesRepository;

public class Properties {
	public static final String ALTERNATE_TEXT = "alternateText";
	public static final String CLIP = "clip";
	public static final String CLIP_PATH = "clipPath";
	public static final String CLIP_RULE = "clipRule";
	public static final String COLOR = "color";
	public static final String CURVE_FLATNESS = "curveFlatness";
	public static final String DISPLAY = "display";
	public static final String FILL = "fill";
	public static final String FILL_OPACITY = "fillOpacity";
	public static final String FILL_RULE = "fillRule";
	public static final String FILTER_PROPERTIES = "filterProperties";
	public static final String FONT_BOLD = "fontBold";
	public static final String FONT_FAMILY = "fontFamily";
	public static final String FONT_ITALIC = "fontItalic";
	public static final String FONT_NAME = "fontName";
	public static final String FONT_SIZE = "fontSize";
	public static final String FONT_SIZE_ADJUST = "fontSizeAdjust";
	public static final String FONT_STRETCH = "fontStretch";
	public static final String FONT_STYLE = "fontStyle";
	public static final String FONT_UNDERLINE = "fontUnderline";
	public static final String FONT_VARIANT = "fontVariant";
	public static final String FONT_WEIGHT = "fontWeight";
	public static final String IMAGE_HEIGHT = "imageHeight";
	public static final String IMAGE_URL = "imageURL";
	public static final String IMAGE_WIDTH = "imageWidth";
	public static final String ITEM_DESCRIPTION = "itemDescription";
	public static final String ITEM_DISABLED = "itemDisabled";
	public static final String ITEM_LABEL = "itemLabel";
	public static final String ITEM_VALUE = "itemValue";
	public static final String OPACITY = "opacity";
	public static final String OVERFLOW = "overflow";
	public static final String PIXEL_UNIT_TO_MILLIMETER = "pixelUnitToMillimeter";
	public static final String RENDERED = "rendered";
	public static final String SELECTABLE = "selectable";
	public static final String STOP_COLOR = "stopColor";
	public static final String STOP_OPACITY = "stopOpacity";
	public static final String STROKE = "stroke";
	public static final String STROKE_DASH_ARRAY = "strokeDashArray";
	public static final String STROKE_DASH_OFFSET = "strokeDashOffset";
	public static final String STROKE_LINE_CAP = "strokeLineCap";
	public static final String STROKE_LINE_JOIN = "strokeLineJoin";
	public static final String STROKE_MITER_LIMIT = "strokeMiterLimit";
	public static final String STROKE_OPACITY = "strokeOpacity";
	public static final String STROKE_WIDTH = "strokeWidth";
	public static final String TARGET_ID = "targetId";
	public static final String TEXT_ALIGN = "textAlign";
	public static final String TEXT_ANCHOR = "textAnchor";
	public static final String TEXT_DECORATION = "textDecoration";
	public static final String TEXT_RENDERING = "textRendering";
	public static final String VALUE = "value";
	public static final String VISIBILITY = "visibility";
	public static final String WORD_SPACING = "wordSpacing";
	public static final String WRITING_MODE = "writingMode";
	static {
		if (Constants.COMPACTED_PROPERTY_NAME) {
			PropertiesRepository.declareProperties(new String[] {ALTERNATE_TEXT,CLIP,CLIP_PATH,CLIP_RULE,COLOR,CURVE_FLATNESS,DISPLAY,FILL,FILL_OPACITY,FILL_RULE,FILTER_PROPERTIES,FONT_BOLD,FONT_FAMILY,FONT_ITALIC,FONT_NAME,FONT_SIZE,FONT_SIZE_ADJUST,FONT_STRETCH,FONT_STYLE,FONT_UNDERLINE,FONT_VARIANT,FONT_WEIGHT,IMAGE_HEIGHT,IMAGE_URL,IMAGE_WIDTH,ITEM_DESCRIPTION,ITEM_DISABLED,ITEM_LABEL,ITEM_VALUE,OPACITY,OVERFLOW,PIXEL_UNIT_TO_MILLIMETER,RENDERED,SELECTABLE,STOP_COLOR,STOP_OPACITY,STROKE,STROKE_DASH_ARRAY,STROKE_DASH_OFFSET,STROKE_LINE_CAP,STROKE_LINE_JOIN,STROKE_MITER_LIMIT,STROKE_OPACITY,STROKE_WIDTH,TARGET_ID,TEXT_ALIGN,TEXT_ANCHOR,TEXT_DECORATION,TEXT_RENDERING,VALUE,VISIBILITY,WORD_SPACING,WRITING_MODE});
		}
	}
}
