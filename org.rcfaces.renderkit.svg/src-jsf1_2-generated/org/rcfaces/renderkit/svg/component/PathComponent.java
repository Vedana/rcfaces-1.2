package org.rcfaces.renderkit.svg.component;

import javax.el.ValueExpression;
import org.rcfaces.renderkit.svg.component.NodeComponent;
import java.util.Arrays;
import org.rcfaces.renderkit.svg.component.Properties;
import java.util.Set;
import java.util.HashSet;

public class PathComponent extends NodeComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.svg.path";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(NodeComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"display","visibility","fontWeight","stopColor","fontSize","fontVariant","fill","textAlign","strokeLineCap","fontStyle","fillOpacity","clipRule","strokeDashArray","clipPath","textAnchor","textRendering","fillRule","strokeWidth","fontStretch","clip","opacity","writingMode","wordSpacing","fontFamily","strokeLineJoin","strokeMiterLimit","color","stopOpacity","fontSizeAdjust","textDecoration","stroke","strokeDashOffset","strokeOpacity","overflow"}));
	}

	public PathComponent() {
		setRendererType(null);
	}

	public PathComponent(String componentId) {
		this();
		setId(componentId);
	}

	public String getClip() {
		return getClip(null);
	}

	public String getClip(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.CLIP, facesContext);
	}

	public void setClip(String clip) {
		engine.setProperty(Properties.CLIP, clip);
	}

	/**
	 * Returns <code>true</code> if the attribute "clip" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isClipSetted() {
		return engine.isPropertySetted(Properties.CLIP);
	}

	public String getClipPath() {
		return getClipPath(null);
	}

	public String getClipPath(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.CLIP_PATH, facesContext);
	}

	public void setClipPath(String clipPath) {
		engine.setProperty(Properties.CLIP_PATH, clipPath);
	}

	/**
	 * Returns <code>true</code> if the attribute "clipPath" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isClipPathSetted() {
		return engine.isPropertySetted(Properties.CLIP_PATH);
	}

	public String getClipRule() {
		return getClipRule(null);
	}

	public String getClipRule(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.CLIP_RULE, facesContext);
	}

	public void setClipRule(String clipRule) {
		engine.setProperty(Properties.CLIP_RULE, clipRule);
	}

	/**
	 * Returns <code>true</code> if the attribute "clipRule" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isClipRuleSetted() {
		return engine.isPropertySetted(Properties.CLIP_RULE);
	}

	public String getColor() {
		return getColor(null);
	}

	public String getColor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.COLOR, facesContext);
	}

	public void setColor(String color) {
		engine.setProperty(Properties.COLOR, color);
	}

	/**
	 * Returns <code>true</code> if the attribute "color" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isColorSetted() {
		return engine.isPropertySetted(Properties.COLOR);
	}

	public String getDisplay() {
		return getDisplay(null);
	}

	public String getDisplay(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DISPLAY, facesContext);
	}

	public void setDisplay(String display) {
		engine.setProperty(Properties.DISPLAY, display);
	}

	/**
	 * Returns <code>true</code> if the attribute "display" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDisplaySetted() {
		return engine.isPropertySetted(Properties.DISPLAY);
	}

	public String getFill() {
		return getFill(null);
	}

	public String getFill(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FILL, facesContext);
	}

	public void setFill(String fill) {
		engine.setProperty(Properties.FILL, fill);
	}

	/**
	 * Returns <code>true</code> if the attribute "fill" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isFillSetted() {
		return engine.isPropertySetted(Properties.FILL);
	}

	public String getFillRule() {
		return getFillRule(null);
	}

	public String getFillRule(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FILL_RULE, facesContext);
	}

	public void setFillRule(String fillRule) {
		engine.setProperty(Properties.FILL_RULE, fillRule);
	}

	/**
	 * Returns <code>true</code> if the attribute "fillRule" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isFillRuleSetted() {
		return engine.isPropertySetted(Properties.FILL_RULE);
	}

	public String getFillOpacity() {
		return getFillOpacity(null);
	}

	public String getFillOpacity(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FILL_OPACITY, facesContext);
	}

	public void setFillOpacity(String fillOpacity) {
		engine.setProperty(Properties.FILL_OPACITY, fillOpacity);
	}

	/**
	 * Returns <code>true</code> if the attribute "fillOpacity" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isFillOpacitySetted() {
		return engine.isPropertySetted(Properties.FILL_OPACITY);
	}

	public String getFontFamily() {
		return getFontFamily(null);
	}

	public String getFontFamily(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FONT_FAMILY, facesContext);
	}

	public void setFontFamily(String fontFamily) {
		engine.setProperty(Properties.FONT_FAMILY, fontFamily);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontFamily" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isFontFamilySetted() {
		return engine.isPropertySetted(Properties.FONT_FAMILY);
	}

	public String getFontSize() {
		return getFontSize(null);
	}

	public String getFontSize(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FONT_SIZE, facesContext);
	}

	public void setFontSize(String fontSize) {
		engine.setProperty(Properties.FONT_SIZE, fontSize);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontSize" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isFontSizeSetted() {
		return engine.isPropertySetted(Properties.FONT_SIZE);
	}

	public String getFontSizeAdjust() {
		return getFontSizeAdjust(null);
	}

	public String getFontSizeAdjust(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FONT_SIZE_ADJUST, facesContext);
	}

	public void setFontSizeAdjust(String fontSizeAdjust) {
		engine.setProperty(Properties.FONT_SIZE_ADJUST, fontSizeAdjust);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontSizeAdjust" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isFontSizeAdjustSetted() {
		return engine.isPropertySetted(Properties.FONT_SIZE_ADJUST);
	}

	public String getFontStretch() {
		return getFontStretch(null);
	}

	public String getFontStretch(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FONT_STRETCH, facesContext);
	}

	public void setFontStretch(String fontStretch) {
		engine.setProperty(Properties.FONT_STRETCH, fontStretch);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontStretch" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isFontStretchSetted() {
		return engine.isPropertySetted(Properties.FONT_STRETCH);
	}

	public String getFontStyle() {
		return getFontStyle(null);
	}

	public String getFontStyle(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FONT_STYLE, facesContext);
	}

	public void setFontStyle(String fontStyle) {
		engine.setProperty(Properties.FONT_STYLE, fontStyle);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontStyle" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isFontStyleSetted() {
		return engine.isPropertySetted(Properties.FONT_STYLE);
	}

	public String getFontVariant() {
		return getFontVariant(null);
	}

	public String getFontVariant(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FONT_VARIANT, facesContext);
	}

	public void setFontVariant(String fontVariant) {
		engine.setProperty(Properties.FONT_VARIANT, fontVariant);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontVariant" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isFontVariantSetted() {
		return engine.isPropertySetted(Properties.FONT_VARIANT);
	}

	public String getFontWeight() {
		return getFontWeight(null);
	}

	public String getFontWeight(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FONT_WEIGHT, facesContext);
	}

	public void setFontWeight(String fontWeight) {
		engine.setProperty(Properties.FONT_WEIGHT, fontWeight);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontWeight" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isFontWeightSetted() {
		return engine.isPropertySetted(Properties.FONT_WEIGHT);
	}

	public String getOpacity() {
		return getOpacity(null);
	}

	public String getOpacity(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.OPACITY, facesContext);
	}

	public void setOpacity(String opacity) {
		engine.setProperty(Properties.OPACITY, opacity);
	}

	/**
	 * Returns <code>true</code> if the attribute "opacity" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isOpacitySetted() {
		return engine.isPropertySetted(Properties.OPACITY);
	}

	public String getOverflow() {
		return getOverflow(null);
	}

	public String getOverflow(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.OVERFLOW, facesContext);
	}

	public void setOverflow(String overflow) {
		engine.setProperty(Properties.OVERFLOW, overflow);
	}

	/**
	 * Returns <code>true</code> if the attribute "overflow" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isOverflowSetted() {
		return engine.isPropertySetted(Properties.OVERFLOW);
	}

	public String getStopColor() {
		return getStopColor(null);
	}

	public String getStopColor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.STOP_COLOR, facesContext);
	}

	public void setStopColor(String stopColor) {
		engine.setProperty(Properties.STOP_COLOR, stopColor);
	}

	/**
	 * Returns <code>true</code> if the attribute "stopColor" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isStopColorSetted() {
		return engine.isPropertySetted(Properties.STOP_COLOR);
	}

	public String getStopOpacity() {
		return getStopOpacity(null);
	}

	public String getStopOpacity(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.STOP_OPACITY, facesContext);
	}

	public void setStopOpacity(String stopOpacity) {
		engine.setProperty(Properties.STOP_OPACITY, stopOpacity);
	}

	/**
	 * Returns <code>true</code> if the attribute "stopOpacity" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isStopOpacitySetted() {
		return engine.isPropertySetted(Properties.STOP_OPACITY);
	}

	public String getStroke() {
		return getStroke(null);
	}

	public String getStroke(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.STROKE, facesContext);
	}

	public void setStroke(String stroke) {
		engine.setProperty(Properties.STROKE, stroke);
	}

	/**
	 * Returns <code>true</code> if the attribute "stroke" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isStrokeSetted() {
		return engine.isPropertySetted(Properties.STROKE);
	}

	public String getStrokeDashArray() {
		return getStrokeDashArray(null);
	}

	public String getStrokeDashArray(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.STROKE_DASH_ARRAY, facesContext);
	}

	public void setStrokeDashArray(String strokeDashArray) {
		engine.setProperty(Properties.STROKE_DASH_ARRAY, strokeDashArray);
	}

	/**
	 * Returns <code>true</code> if the attribute "strokeDashArray" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isStrokeDashArraySetted() {
		return engine.isPropertySetted(Properties.STROKE_DASH_ARRAY);
	}

	public String getStrokeDashOffset() {
		return getStrokeDashOffset(null);
	}

	public String getStrokeDashOffset(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.STROKE_DASH_OFFSET, facesContext);
	}

	public void setStrokeDashOffset(String strokeDashOffset) {
		engine.setProperty(Properties.STROKE_DASH_OFFSET, strokeDashOffset);
	}

	/**
	 * Returns <code>true</code> if the attribute "strokeDashOffset" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isStrokeDashOffsetSetted() {
		return engine.isPropertySetted(Properties.STROKE_DASH_OFFSET);
	}

	public String getStrokeLineCap() {
		return getStrokeLineCap(null);
	}

	public String getStrokeLineCap(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.STROKE_LINE_CAP, facesContext);
	}

	public void setStrokeLineCap(String strokeLineCap) {
		engine.setProperty(Properties.STROKE_LINE_CAP, strokeLineCap);
	}

	/**
	 * Returns <code>true</code> if the attribute "strokeLineCap" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isStrokeLineCapSetted() {
		return engine.isPropertySetted(Properties.STROKE_LINE_CAP);
	}

	public String getStrokeLineJoin() {
		return getStrokeLineJoin(null);
	}

	public String getStrokeLineJoin(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.STROKE_LINE_JOIN, facesContext);
	}

	public void setStrokeLineJoin(String strokeLineJoin) {
		engine.setProperty(Properties.STROKE_LINE_JOIN, strokeLineJoin);
	}

	/**
	 * Returns <code>true</code> if the attribute "strokeLineJoin" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isStrokeLineJoinSetted() {
		return engine.isPropertySetted(Properties.STROKE_LINE_JOIN);
	}

	public String getStrokeMiterLimit() {
		return getStrokeMiterLimit(null);
	}

	public String getStrokeMiterLimit(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.STROKE_MITER_LIMIT, facesContext);
	}

	public void setStrokeMiterLimit(String strokeMiterLimit) {
		engine.setProperty(Properties.STROKE_MITER_LIMIT, strokeMiterLimit);
	}

	/**
	 * Returns <code>true</code> if the attribute "strokeMiterLimit" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isStrokeMiterLimitSetted() {
		return engine.isPropertySetted(Properties.STROKE_MITER_LIMIT);
	}

	public String getStrokeOpacity() {
		return getStrokeOpacity(null);
	}

	public String getStrokeOpacity(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.STROKE_OPACITY, facesContext);
	}

	public void setStrokeOpacity(String strokeOpacity) {
		engine.setProperty(Properties.STROKE_OPACITY, strokeOpacity);
	}

	/**
	 * Returns <code>true</code> if the attribute "strokeOpacity" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isStrokeOpacitySetted() {
		return engine.isPropertySetted(Properties.STROKE_OPACITY);
	}

	public String getStrokeWidth() {
		return getStrokeWidth(null);
	}

	public String getStrokeWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.STROKE_WIDTH, facesContext);
	}

	public void setStrokeWidth(String strokeWidth) {
		engine.setProperty(Properties.STROKE_WIDTH, strokeWidth);
	}

	/**
	 * Returns <code>true</code> if the attribute "strokeWidth" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isStrokeWidthSetted() {
		return engine.isPropertySetted(Properties.STROKE_WIDTH);
	}

	public String getTextAlign() {
		return getTextAlign(null);
	}

	public String getTextAlign(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TEXT_ALIGN, facesContext);
	}

	public void setTextAlign(String textAlign) {
		engine.setProperty(Properties.TEXT_ALIGN, textAlign);
	}

	/**
	 * Returns <code>true</code> if the attribute "textAlign" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isTextAlignSetted() {
		return engine.isPropertySetted(Properties.TEXT_ALIGN);
	}

	public String getTextAnchor() {
		return getTextAnchor(null);
	}

	public String getTextAnchor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TEXT_ANCHOR, facesContext);
	}

	public void setTextAnchor(String textAnchor) {
		engine.setProperty(Properties.TEXT_ANCHOR, textAnchor);
	}

	/**
	 * Returns <code>true</code> if the attribute "textAnchor" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isTextAnchorSetted() {
		return engine.isPropertySetted(Properties.TEXT_ANCHOR);
	}

	public String getTextDecoration() {
		return getTextDecoration(null);
	}

	public String getTextDecoration(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TEXT_DECORATION, facesContext);
	}

	public void setTextDecoration(String textDecoration) {
		engine.setProperty(Properties.TEXT_DECORATION, textDecoration);
	}

	/**
	 * Returns <code>true</code> if the attribute "textDecoration" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isTextDecorationSetted() {
		return engine.isPropertySetted(Properties.TEXT_DECORATION);
	}

	public String getTextRendering() {
		return getTextRendering(null);
	}

	public String getTextRendering(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TEXT_RENDERING, facesContext);
	}

	public void setTextRendering(String textRendering) {
		engine.setProperty(Properties.TEXT_RENDERING, textRendering);
	}

	/**
	 * Returns <code>true</code> if the attribute "textRendering" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isTextRenderingSetted() {
		return engine.isPropertySetted(Properties.TEXT_RENDERING);
	}

	public String getVisibility() {
		return getVisibility(null);
	}

	public String getVisibility(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.VISIBILITY, facesContext);
	}

	public void setVisibility(String visibility) {
		engine.setProperty(Properties.VISIBILITY, visibility);
	}

	/**
	 * Returns <code>true</code> if the attribute "visibility" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isVisibilitySetted() {
		return engine.isPropertySetted(Properties.VISIBILITY);
	}

	public String getWordSpacing() {
		return getWordSpacing(null);
	}

	public String getWordSpacing(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.WORD_SPACING, facesContext);
	}

	public void setWordSpacing(String wordSpacing) {
		engine.setProperty(Properties.WORD_SPACING, wordSpacing);
	}

	/**
	 * Returns <code>true</code> if the attribute "wordSpacing" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isWordSpacingSetted() {
		return engine.isPropertySetted(Properties.WORD_SPACING);
	}

	public String getWritingMode() {
		return getWritingMode(null);
	}

	public String getWritingMode(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.WRITING_MODE, facesContext);
	}

	public void setWritingMode(String writingMode) {
		engine.setProperty(Properties.WRITING_MODE, writingMode);
	}

	/**
	 * Returns <code>true</code> if the attribute "writingMode" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isWritingModeSetted() {
		return engine.isPropertySetted(Properties.WRITING_MODE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
