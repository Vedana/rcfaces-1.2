package org.rcfaces.renderkit.svg.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import org.rcfaces.renderkit.svg.component.PathComponent;
import org.apache.commons.logging.LogFactory;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;

public class PathTag extends NodeTag implements Tag {


	private static final Log LOG=LogFactory.getLog(PathTag.class);

	private ValueExpression clip;
	private ValueExpression clipPath;
	private ValueExpression clipRule;
	private ValueExpression color;
	private ValueExpression display;
	private ValueExpression fill;
	private ValueExpression fillRule;
	private ValueExpression fillOpacity;
	private ValueExpression fontFamily;
	private ValueExpression fontSize;
	private ValueExpression fontSizeAdjust;
	private ValueExpression fontStretch;
	private ValueExpression fontStyle;
	private ValueExpression fontVariant;
	private ValueExpression fontWeight;
	private ValueExpression opacity;
	private ValueExpression overflow;
	private ValueExpression stopColor;
	private ValueExpression stopOpacity;
	private ValueExpression stroke;
	private ValueExpression strokeDashArray;
	private ValueExpression strokeDashOffset;
	private ValueExpression strokeLineCap;
	private ValueExpression strokeLineJoin;
	private ValueExpression strokeMiterLimit;
	private ValueExpression strokeOpacity;
	private ValueExpression strokeWidth;
	private ValueExpression textAlign;
	private ValueExpression textAnchor;
	private ValueExpression textDecoration;
	private ValueExpression textRendering;
	private ValueExpression visibility;
	private ValueExpression wordSpacing;
	private ValueExpression writingMode;
	public String getComponentType() {
		return PathComponent.COMPONENT_TYPE;
	}

	public final void setClip(ValueExpression clip) {
		this.clip = clip;
	}

	public final void setClipPath(ValueExpression clipPath) {
		this.clipPath = clipPath;
	}

	public final void setClipRule(ValueExpression clipRule) {
		this.clipRule = clipRule;
	}

	public final void setColor(ValueExpression color) {
		this.color = color;
	}

	public final void setDisplay(ValueExpression display) {
		this.display = display;
	}

	public final void setFill(ValueExpression fill) {
		this.fill = fill;
	}

	public final void setFillRule(ValueExpression fillRule) {
		this.fillRule = fillRule;
	}

	public final void setFillOpacity(ValueExpression fillOpacity) {
		this.fillOpacity = fillOpacity;
	}

	public final void setFontFamily(ValueExpression fontFamily) {
		this.fontFamily = fontFamily;
	}

	public final void setFontSize(ValueExpression fontSize) {
		this.fontSize = fontSize;
	}

	public final void setFontSizeAdjust(ValueExpression fontSizeAdjust) {
		this.fontSizeAdjust = fontSizeAdjust;
	}

	public final void setFontStretch(ValueExpression fontStretch) {
		this.fontStretch = fontStretch;
	}

	public final void setFontStyle(ValueExpression fontStyle) {
		this.fontStyle = fontStyle;
	}

	public final void setFontVariant(ValueExpression fontVariant) {
		this.fontVariant = fontVariant;
	}

	public final void setFontWeight(ValueExpression fontWeight) {
		this.fontWeight = fontWeight;
	}

	public final void setOpacity(ValueExpression opacity) {
		this.opacity = opacity;
	}

	public final void setOverflow(ValueExpression overflow) {
		this.overflow = overflow;
	}

	public final void setStopColor(ValueExpression stopColor) {
		this.stopColor = stopColor;
	}

	public final void setStopOpacity(ValueExpression stopOpacity) {
		this.stopOpacity = stopOpacity;
	}

	public final void setStroke(ValueExpression stroke) {
		this.stroke = stroke;
	}

	public final void setStrokeDashArray(ValueExpression strokeDashArray) {
		this.strokeDashArray = strokeDashArray;
	}

	public final void setStrokeDashOffset(ValueExpression strokeDashOffset) {
		this.strokeDashOffset = strokeDashOffset;
	}

	public final void setStrokeLineCap(ValueExpression strokeLineCap) {
		this.strokeLineCap = strokeLineCap;
	}

	public final void setStrokeLineJoin(ValueExpression strokeLineJoin) {
		this.strokeLineJoin = strokeLineJoin;
	}

	public final void setStrokeMiterLimit(ValueExpression strokeMiterLimit) {
		this.strokeMiterLimit = strokeMiterLimit;
	}

	public final void setStrokeOpacity(ValueExpression strokeOpacity) {
		this.strokeOpacity = strokeOpacity;
	}

	public final void setStrokeWidth(ValueExpression strokeWidth) {
		this.strokeWidth = strokeWidth;
	}

	public final void setTextAlign(ValueExpression textAlign) {
		this.textAlign = textAlign;
	}

	public final void setTextAnchor(ValueExpression textAnchor) {
		this.textAnchor = textAnchor;
	}

	public final void setTextDecoration(ValueExpression textDecoration) {
		this.textDecoration = textDecoration;
	}

	public final void setTextRendering(ValueExpression textRendering) {
		this.textRendering = textRendering;
	}

	public final void setVisibility(ValueExpression visibility) {
		this.visibility = visibility;
	}

	public final void setWordSpacing(ValueExpression wordSpacing) {
		this.wordSpacing = wordSpacing;
	}

	public final void setWritingMode(ValueExpression writingMode) {
		this.writingMode = writingMode;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (PathComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  clip='"+clip+"'");
			LOG.debug("  clipPath='"+clipPath+"'");
			LOG.debug("  clipRule='"+clipRule+"'");
			LOG.debug("  color='"+color+"'");
			LOG.debug("  display='"+display+"'");
			LOG.debug("  fill='"+fill+"'");
			LOG.debug("  fillRule='"+fillRule+"'");
			LOG.debug("  fillOpacity='"+fillOpacity+"'");
			LOG.debug("  fontFamily='"+fontFamily+"'");
			LOG.debug("  fontSize='"+fontSize+"'");
			LOG.debug("  fontSizeAdjust='"+fontSizeAdjust+"'");
			LOG.debug("  fontStretch='"+fontStretch+"'");
			LOG.debug("  fontStyle='"+fontStyle+"'");
			LOG.debug("  fontVariant='"+fontVariant+"'");
			LOG.debug("  fontWeight='"+fontWeight+"'");
			LOG.debug("  opacity='"+opacity+"'");
			LOG.debug("  overflow='"+overflow+"'");
			LOG.debug("  stopColor='"+stopColor+"'");
			LOG.debug("  stopOpacity='"+stopOpacity+"'");
			LOG.debug("  stroke='"+stroke+"'");
			LOG.debug("  strokeDashArray='"+strokeDashArray+"'");
			LOG.debug("  strokeDashOffset='"+strokeDashOffset+"'");
			LOG.debug("  strokeLineCap='"+strokeLineCap+"'");
			LOG.debug("  strokeLineJoin='"+strokeLineJoin+"'");
			LOG.debug("  strokeMiterLimit='"+strokeMiterLimit+"'");
			LOG.debug("  strokeOpacity='"+strokeOpacity+"'");
			LOG.debug("  strokeWidth='"+strokeWidth+"'");
			LOG.debug("  textAlign='"+textAlign+"'");
			LOG.debug("  textAnchor='"+textAnchor+"'");
			LOG.debug("  textDecoration='"+textDecoration+"'");
			LOG.debug("  textRendering='"+textRendering+"'");
			LOG.debug("  visibility='"+visibility+"'");
			LOG.debug("  wordSpacing='"+wordSpacing+"'");
			LOG.debug("  writingMode='"+writingMode+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof PathComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'PathComponent'.");
		}

		PathComponent component = (PathComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (clip != null) {
			if (clip.isLiteralText()==false) {
				component.setValueExpression(Properties.CLIP, clip);

			} else {
				component.setClip(clip.getExpressionString());
			}
		}

		if (clipPath != null) {
			if (clipPath.isLiteralText()==false) {
				component.setValueExpression(Properties.CLIP_PATH, clipPath);

			} else {
				component.setClipPath(clipPath.getExpressionString());
			}
		}

		if (clipRule != null) {
			if (clipRule.isLiteralText()==false) {
				component.setValueExpression(Properties.CLIP_RULE, clipRule);

			} else {
				component.setClipRule(clipRule.getExpressionString());
			}
		}

		if (color != null) {
			if (color.isLiteralText()==false) {
				component.setValueExpression(Properties.COLOR, color);

			} else {
				component.setColor(color.getExpressionString());
			}
		}

		if (display != null) {
			if (display.isLiteralText()==false) {
				component.setValueExpression(Properties.DISPLAY, display);

			} else {
				component.setDisplay(display.getExpressionString());
			}
		}

		if (fill != null) {
			if (fill.isLiteralText()==false) {
				component.setValueExpression(Properties.FILL, fill);

			} else {
				component.setFill(fill.getExpressionString());
			}
		}

		if (fillRule != null) {
			if (fillRule.isLiteralText()==false) {
				component.setValueExpression(Properties.FILL_RULE, fillRule);

			} else {
				component.setFillRule(fillRule.getExpressionString());
			}
		}

		if (fillOpacity != null) {
			if (fillOpacity.isLiteralText()==false) {
				component.setValueExpression(Properties.FILL_OPACITY, fillOpacity);

			} else {
				component.setFillOpacity(fillOpacity.getExpressionString());
			}
		}

		if (fontFamily != null) {
			if (fontFamily.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_FAMILY, fontFamily);

			} else {
				component.setFontFamily(fontFamily.getExpressionString());
			}
		}

		if (fontSize != null) {
			if (fontSize.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_SIZE, fontSize);

			} else {
				component.setFontSize(fontSize.getExpressionString());
			}
		}

		if (fontSizeAdjust != null) {
			if (fontSizeAdjust.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_SIZE_ADJUST, fontSizeAdjust);

			} else {
				component.setFontSizeAdjust(fontSizeAdjust.getExpressionString());
			}
		}

		if (fontStretch != null) {
			if (fontStretch.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_STRETCH, fontStretch);

			} else {
				component.setFontStretch(fontStretch.getExpressionString());
			}
		}

		if (fontStyle != null) {
			if (fontStyle.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_STYLE, fontStyle);

			} else {
				component.setFontStyle(fontStyle.getExpressionString());
			}
		}

		if (fontVariant != null) {
			if (fontVariant.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_VARIANT, fontVariant);

			} else {
				component.setFontVariant(fontVariant.getExpressionString());
			}
		}

		if (fontWeight != null) {
			if (fontWeight.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_WEIGHT, fontWeight);

			} else {
				component.setFontWeight(fontWeight.getExpressionString());
			}
		}

		if (opacity != null) {
			if (opacity.isLiteralText()==false) {
				component.setValueExpression(Properties.OPACITY, opacity);

			} else {
				component.setOpacity(opacity.getExpressionString());
			}
		}

		if (overflow != null) {
			if (overflow.isLiteralText()==false) {
				component.setValueExpression(Properties.OVERFLOW, overflow);

			} else {
				component.setOverflow(overflow.getExpressionString());
			}
		}

		if (stopColor != null) {
			if (stopColor.isLiteralText()==false) {
				component.setValueExpression(Properties.STOP_COLOR, stopColor);

			} else {
				component.setStopColor(stopColor.getExpressionString());
			}
		}

		if (stopOpacity != null) {
			if (stopOpacity.isLiteralText()==false) {
				component.setValueExpression(Properties.STOP_OPACITY, stopOpacity);

			} else {
				component.setStopOpacity(stopOpacity.getExpressionString());
			}
		}

		if (stroke != null) {
			if (stroke.isLiteralText()==false) {
				component.setValueExpression(Properties.STROKE, stroke);

			} else {
				component.setStroke(stroke.getExpressionString());
			}
		}

		if (strokeDashArray != null) {
			if (strokeDashArray.isLiteralText()==false) {
				component.setValueExpression(Properties.STROKE_DASH_ARRAY, strokeDashArray);

			} else {
				component.setStrokeDashArray(strokeDashArray.getExpressionString());
			}
		}

		if (strokeDashOffset != null) {
			if (strokeDashOffset.isLiteralText()==false) {
				component.setValueExpression(Properties.STROKE_DASH_OFFSET, strokeDashOffset);

			} else {
				component.setStrokeDashOffset(strokeDashOffset.getExpressionString());
			}
		}

		if (strokeLineCap != null) {
			if (strokeLineCap.isLiteralText()==false) {
				component.setValueExpression(Properties.STROKE_LINE_CAP, strokeLineCap);

			} else {
				component.setStrokeLineCap(strokeLineCap.getExpressionString());
			}
		}

		if (strokeLineJoin != null) {
			if (strokeLineJoin.isLiteralText()==false) {
				component.setValueExpression(Properties.STROKE_LINE_JOIN, strokeLineJoin);

			} else {
				component.setStrokeLineJoin(strokeLineJoin.getExpressionString());
			}
		}

		if (strokeMiterLimit != null) {
			if (strokeMiterLimit.isLiteralText()==false) {
				component.setValueExpression(Properties.STROKE_MITER_LIMIT, strokeMiterLimit);

			} else {
				component.setStrokeMiterLimit(strokeMiterLimit.getExpressionString());
			}
		}

		if (strokeOpacity != null) {
			if (strokeOpacity.isLiteralText()==false) {
				component.setValueExpression(Properties.STROKE_OPACITY, strokeOpacity);

			} else {
				component.setStrokeOpacity(strokeOpacity.getExpressionString());
			}
		}

		if (strokeWidth != null) {
			if (strokeWidth.isLiteralText()==false) {
				component.setValueExpression(Properties.STROKE_WIDTH, strokeWidth);

			} else {
				component.setStrokeWidth(strokeWidth.getExpressionString());
			}
		}

		if (textAlign != null) {
			if (textAlign.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT_ALIGN, textAlign);

			} else {
				component.setTextAlign(textAlign.getExpressionString());
			}
		}

		if (textAnchor != null) {
			if (textAnchor.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT_ANCHOR, textAnchor);

			} else {
				component.setTextAnchor(textAnchor.getExpressionString());
			}
		}

		if (textDecoration != null) {
			if (textDecoration.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT_DECORATION, textDecoration);

			} else {
				component.setTextDecoration(textDecoration.getExpressionString());
			}
		}

		if (textRendering != null) {
			if (textRendering.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT_RENDERING, textRendering);

			} else {
				component.setTextRendering(textRendering.getExpressionString());
			}
		}

		if (visibility != null) {
			if (visibility.isLiteralText()==false) {
				component.setValueExpression(Properties.VISIBILITY, visibility);

			} else {
				component.setVisibility(visibility.getExpressionString());
			}
		}

		if (wordSpacing != null) {
			if (wordSpacing.isLiteralText()==false) {
				component.setValueExpression(Properties.WORD_SPACING, wordSpacing);

			} else {
				component.setWordSpacing(wordSpacing.getExpressionString());
			}
		}

		if (writingMode != null) {
			if (writingMode.isLiteralText()==false) {
				component.setValueExpression(Properties.WRITING_MODE, writingMode);

			} else {
				component.setWritingMode(writingMode.getExpressionString());
			}
		}
	}

	public void release() {
		clip = null;
		clipPath = null;
		clipRule = null;
		color = null;
		display = null;
		fill = null;
		fillRule = null;
		fillOpacity = null;
		fontFamily = null;
		fontSize = null;
		fontSizeAdjust = null;
		fontStretch = null;
		fontStyle = null;
		fontVariant = null;
		fontWeight = null;
		opacity = null;
		overflow = null;
		stopColor = null;
		stopOpacity = null;
		stroke = null;
		strokeDashArray = null;
		strokeDashOffset = null;
		strokeLineCap = null;
		strokeLineJoin = null;
		strokeMiterLimit = null;
		strokeOpacity = null;
		strokeWidth = null;
		textAlign = null;
		textAnchor = null;
		textDecoration = null;
		textRendering = null;
		visibility = null;
		wordSpacing = null;
		writingMode = null;

		super.release();
	}

}
