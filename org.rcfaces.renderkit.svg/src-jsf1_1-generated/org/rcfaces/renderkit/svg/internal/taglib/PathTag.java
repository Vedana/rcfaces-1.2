package org.rcfaces.renderkit.svg.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.context.FacesContext;
import org.rcfaces.renderkit.svg.component.PathComponent;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;

public class PathTag extends NodeTag implements Tag {


	private static final Log LOG=LogFactory.getLog(PathTag.class);

	private String clip;
	private String clipPath;
	private String clipRule;
	private String color;
	private String display;
	private String fill;
	private String fillRule;
	private String fillOpacity;
	private String fontFamily;
	private String fontSize;
	private String fontSizeAdjust;
	private String fontStretch;
	private String fontStyle;
	private String fontVariant;
	private String fontWeight;
	private String opacity;
	private String overflow;
	private String stopColor;
	private String stopOpacity;
	private String stroke;
	private String strokeDashArray;
	private String strokeDashOffset;
	private String strokeLineCap;
	private String strokeLineJoin;
	private String strokeMiterLimit;
	private String strokeOpacity;
	private String strokeWidth;
	private String textAlign;
	private String textAnchor;
	private String textDecoration;
	private String textRendering;
	private String visibility;
	private String wordSpacing;
	private String writingMode;
	public String getComponentType() {
		return PathComponent.COMPONENT_TYPE;
	}

	public final void setClip(String clip) {
		this.clip = clip;
	}

	public final void setClipPath(String clipPath) {
		this.clipPath = clipPath;
	}

	public final void setClipRule(String clipRule) {
		this.clipRule = clipRule;
	}

	public final void setColor(String color) {
		this.color = color;
	}

	public final void setDisplay(String display) {
		this.display = display;
	}

	public final void setFill(String fill) {
		this.fill = fill;
	}

	public final void setFillRule(String fillRule) {
		this.fillRule = fillRule;
	}

	public final void setFillOpacity(String fillOpacity) {
		this.fillOpacity = fillOpacity;
	}

	public final void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	public final void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}

	public final void setFontSizeAdjust(String fontSizeAdjust) {
		this.fontSizeAdjust = fontSizeAdjust;
	}

	public final void setFontStretch(String fontStretch) {
		this.fontStretch = fontStretch;
	}

	public final void setFontStyle(String fontStyle) {
		this.fontStyle = fontStyle;
	}

	public final void setFontVariant(String fontVariant) {
		this.fontVariant = fontVariant;
	}

	public final void setFontWeight(String fontWeight) {
		this.fontWeight = fontWeight;
	}

	public final void setOpacity(String opacity) {
		this.opacity = opacity;
	}

	public final void setOverflow(String overflow) {
		this.overflow = overflow;
	}

	public final void setStopColor(String stopColor) {
		this.stopColor = stopColor;
	}

	public final void setStopOpacity(String stopOpacity) {
		this.stopOpacity = stopOpacity;
	}

	public final void setStroke(String stroke) {
		this.stroke = stroke;
	}

	public final void setStrokeDashArray(String strokeDashArray) {
		this.strokeDashArray = strokeDashArray;
	}

	public final void setStrokeDashOffset(String strokeDashOffset) {
		this.strokeDashOffset = strokeDashOffset;
	}

	public final void setStrokeLineCap(String strokeLineCap) {
		this.strokeLineCap = strokeLineCap;
	}

	public final void setStrokeLineJoin(String strokeLineJoin) {
		this.strokeLineJoin = strokeLineJoin;
	}

	public final void setStrokeMiterLimit(String strokeMiterLimit) {
		this.strokeMiterLimit = strokeMiterLimit;
	}

	public final void setStrokeOpacity(String strokeOpacity) {
		this.strokeOpacity = strokeOpacity;
	}

	public final void setStrokeWidth(String strokeWidth) {
		this.strokeWidth = strokeWidth;
	}

	public final void setTextAlign(String textAlign) {
		this.textAlign = textAlign;
	}

	public final void setTextAnchor(String textAnchor) {
		this.textAnchor = textAnchor;
	}

	public final void setTextDecoration(String textDecoration) {
		this.textDecoration = textDecoration;
	}

	public final void setTextRendering(String textRendering) {
		this.textRendering = textRendering;
	}

	public final void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public final void setWordSpacing(String wordSpacing) {
		this.wordSpacing = wordSpacing;
	}

	public final void setWritingMode(String writingMode) {
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
		if ((uiComponent instanceof PathComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'PathComponent'.");
		}

		super.setProperties(uiComponent);

		PathComponent component = (PathComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (clip != null) {
			if (isValueReference(clip)) {
				ValueBinding vb = application.createValueBinding(clip);
				component.setValueBinding(Properties.CLIP, vb);

			} else {
				component.setClip(clip);
			}
		}

		if (clipPath != null) {
			if (isValueReference(clipPath)) {
				ValueBinding vb = application.createValueBinding(clipPath);
				component.setValueBinding(Properties.CLIP_PATH, vb);

			} else {
				component.setClipPath(clipPath);
			}
		}

		if (clipRule != null) {
			if (isValueReference(clipRule)) {
				ValueBinding vb = application.createValueBinding(clipRule);
				component.setValueBinding(Properties.CLIP_RULE, vb);

			} else {
				component.setClipRule(clipRule);
			}
		}

		if (color != null) {
			if (isValueReference(color)) {
				ValueBinding vb = application.createValueBinding(color);
				component.setValueBinding(Properties.COLOR, vb);

			} else {
				component.setColor(color);
			}
		}

		if (display != null) {
			if (isValueReference(display)) {
				ValueBinding vb = application.createValueBinding(display);
				component.setValueBinding(Properties.DISPLAY, vb);

			} else {
				component.setDisplay(display);
			}
		}

		if (fill != null) {
			if (isValueReference(fill)) {
				ValueBinding vb = application.createValueBinding(fill);
				component.setValueBinding(Properties.FILL, vb);

			} else {
				component.setFill(fill);
			}
		}

		if (fillRule != null) {
			if (isValueReference(fillRule)) {
				ValueBinding vb = application.createValueBinding(fillRule);
				component.setValueBinding(Properties.FILL_RULE, vb);

			} else {
				component.setFillRule(fillRule);
			}
		}

		if (fillOpacity != null) {
			if (isValueReference(fillOpacity)) {
				ValueBinding vb = application.createValueBinding(fillOpacity);
				component.setValueBinding(Properties.FILL_OPACITY, vb);

			} else {
				component.setFillOpacity(fillOpacity);
			}
		}

		if (fontFamily != null) {
			if (isValueReference(fontFamily)) {
				ValueBinding vb = application.createValueBinding(fontFamily);
				component.setValueBinding(Properties.FONT_FAMILY, vb);

			} else {
				component.setFontFamily(fontFamily);
			}
		}

		if (fontSize != null) {
			if (isValueReference(fontSize)) {
				ValueBinding vb = application.createValueBinding(fontSize);
				component.setValueBinding(Properties.FONT_SIZE, vb);

			} else {
				component.setFontSize(fontSize);
			}
		}

		if (fontSizeAdjust != null) {
			if (isValueReference(fontSizeAdjust)) {
				ValueBinding vb = application.createValueBinding(fontSizeAdjust);
				component.setValueBinding(Properties.FONT_SIZE_ADJUST, vb);

			} else {
				component.setFontSizeAdjust(fontSizeAdjust);
			}
		}

		if (fontStretch != null) {
			if (isValueReference(fontStretch)) {
				ValueBinding vb = application.createValueBinding(fontStretch);
				component.setValueBinding(Properties.FONT_STRETCH, vb);

			} else {
				component.setFontStretch(fontStretch);
			}
		}

		if (fontStyle != null) {
			if (isValueReference(fontStyle)) {
				ValueBinding vb = application.createValueBinding(fontStyle);
				component.setValueBinding(Properties.FONT_STYLE, vb);

			} else {
				component.setFontStyle(fontStyle);
			}
		}

		if (fontVariant != null) {
			if (isValueReference(fontVariant)) {
				ValueBinding vb = application.createValueBinding(fontVariant);
				component.setValueBinding(Properties.FONT_VARIANT, vb);

			} else {
				component.setFontVariant(fontVariant);
			}
		}

		if (fontWeight != null) {
			if (isValueReference(fontWeight)) {
				ValueBinding vb = application.createValueBinding(fontWeight);
				component.setValueBinding(Properties.FONT_WEIGHT, vb);

			} else {
				component.setFontWeight(fontWeight);
			}
		}

		if (opacity != null) {
			if (isValueReference(opacity)) {
				ValueBinding vb = application.createValueBinding(opacity);
				component.setValueBinding(Properties.OPACITY, vb);

			} else {
				component.setOpacity(opacity);
			}
		}

		if (overflow != null) {
			if (isValueReference(overflow)) {
				ValueBinding vb = application.createValueBinding(overflow);
				component.setValueBinding(Properties.OVERFLOW, vb);

			} else {
				component.setOverflow(overflow);
			}
		}

		if (stopColor != null) {
			if (isValueReference(stopColor)) {
				ValueBinding vb = application.createValueBinding(stopColor);
				component.setValueBinding(Properties.STOP_COLOR, vb);

			} else {
				component.setStopColor(stopColor);
			}
		}

		if (stopOpacity != null) {
			if (isValueReference(stopOpacity)) {
				ValueBinding vb = application.createValueBinding(stopOpacity);
				component.setValueBinding(Properties.STOP_OPACITY, vb);

			} else {
				component.setStopOpacity(stopOpacity);
			}
		}

		if (stroke != null) {
			if (isValueReference(stroke)) {
				ValueBinding vb = application.createValueBinding(stroke);
				component.setValueBinding(Properties.STROKE, vb);

			} else {
				component.setStroke(stroke);
			}
		}

		if (strokeDashArray != null) {
			if (isValueReference(strokeDashArray)) {
				ValueBinding vb = application.createValueBinding(strokeDashArray);
				component.setValueBinding(Properties.STROKE_DASH_ARRAY, vb);

			} else {
				component.setStrokeDashArray(strokeDashArray);
			}
		}

		if (strokeDashOffset != null) {
			if (isValueReference(strokeDashOffset)) {
				ValueBinding vb = application.createValueBinding(strokeDashOffset);
				component.setValueBinding(Properties.STROKE_DASH_OFFSET, vb);

			} else {
				component.setStrokeDashOffset(strokeDashOffset);
			}
		}

		if (strokeLineCap != null) {
			if (isValueReference(strokeLineCap)) {
				ValueBinding vb = application.createValueBinding(strokeLineCap);
				component.setValueBinding(Properties.STROKE_LINE_CAP, vb);

			} else {
				component.setStrokeLineCap(strokeLineCap);
			}
		}

		if (strokeLineJoin != null) {
			if (isValueReference(strokeLineJoin)) {
				ValueBinding vb = application.createValueBinding(strokeLineJoin);
				component.setValueBinding(Properties.STROKE_LINE_JOIN, vb);

			} else {
				component.setStrokeLineJoin(strokeLineJoin);
			}
		}

		if (strokeMiterLimit != null) {
			if (isValueReference(strokeMiterLimit)) {
				ValueBinding vb = application.createValueBinding(strokeMiterLimit);
				component.setValueBinding(Properties.STROKE_MITER_LIMIT, vb);

			} else {
				component.setStrokeMiterLimit(strokeMiterLimit);
			}
		}

		if (strokeOpacity != null) {
			if (isValueReference(strokeOpacity)) {
				ValueBinding vb = application.createValueBinding(strokeOpacity);
				component.setValueBinding(Properties.STROKE_OPACITY, vb);

			} else {
				component.setStrokeOpacity(strokeOpacity);
			}
		}

		if (strokeWidth != null) {
			if (isValueReference(strokeWidth)) {
				ValueBinding vb = application.createValueBinding(strokeWidth);
				component.setValueBinding(Properties.STROKE_WIDTH, vb);

			} else {
				component.setStrokeWidth(strokeWidth);
			}
		}

		if (textAlign != null) {
			if (isValueReference(textAlign)) {
				ValueBinding vb = application.createValueBinding(textAlign);
				component.setValueBinding(Properties.TEXT_ALIGN, vb);

			} else {
				component.setTextAlign(textAlign);
			}
		}

		if (textAnchor != null) {
			if (isValueReference(textAnchor)) {
				ValueBinding vb = application.createValueBinding(textAnchor);
				component.setValueBinding(Properties.TEXT_ANCHOR, vb);

			} else {
				component.setTextAnchor(textAnchor);
			}
		}

		if (textDecoration != null) {
			if (isValueReference(textDecoration)) {
				ValueBinding vb = application.createValueBinding(textDecoration);
				component.setValueBinding(Properties.TEXT_DECORATION, vb);

			} else {
				component.setTextDecoration(textDecoration);
			}
		}

		if (textRendering != null) {
			if (isValueReference(textRendering)) {
				ValueBinding vb = application.createValueBinding(textRendering);
				component.setValueBinding(Properties.TEXT_RENDERING, vb);

			} else {
				component.setTextRendering(textRendering);
			}
		}

		if (visibility != null) {
			if (isValueReference(visibility)) {
				ValueBinding vb = application.createValueBinding(visibility);
				component.setValueBinding(Properties.VISIBILITY, vb);

			} else {
				component.setVisibility(visibility);
			}
		}

		if (wordSpacing != null) {
			if (isValueReference(wordSpacing)) {
				ValueBinding vb = application.createValueBinding(wordSpacing);
				component.setValueBinding(Properties.WORD_SPACING, vb);

			} else {
				component.setWordSpacing(wordSpacing);
			}
		}

		if (writingMode != null) {
			if (isValueReference(writingMode)) {
				ValueBinding vb = application.createValueBinding(writingMode);
				component.setValueBinding(Properties.WRITING_MODE, vb);

			} else {
				component.setWritingMode(writingMode);
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
