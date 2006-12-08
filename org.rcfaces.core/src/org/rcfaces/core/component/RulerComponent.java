package org.rcfaces.core.component;

import java.lang.String;
import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.component.capability.IAlignmentCapability;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.capability.ILookAndFeelCapability;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.converter.HiddenModeConverter;
import java.util.Arrays;
import org.rcfaces.core.component.capability.IOrientationCapability;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IPositionCapability;
import org.rcfaces.core.component.capability.IMarginCapability;
import org.rcfaces.core.internal.tools.MarginTools;
import org.rcfaces.core.component.capability.ISizeCapability;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import org.rcfaces.core.component.capability.IForegroundBackgroundColorCapability;

public class RulerComponent extends CameliaBaseComponent implements 
	IPositionCapability,
	IMarginCapability,
	ISizeCapability,
	IVisibilityCapability,
	ILookAndFeelCapability,
	IOrientationCapability,
	IForegroundBackgroundColorCapability,
	IAlignmentCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.ruler";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"width","marginRight","alignment","orientation","hiddenMode","rendered","foregroundColor","marginBottom","height","margins","y","visible","marginLeft","lookId","marginTop","backgroundColor","x"}));
	}

	public RulerComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public RulerComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final void setMargins(String margins) {


				MarginTools.setMargins(this, margins);
			
	}

	public final void setHiddenMode(String hiddenMode) {


			setHiddenMode(((Integer)HiddenModeConverter.SINGLETON.getAsObject(null, null, hiddenMode)).intValue());
		
	}

	public final Boolean getVisibleState(FacesContext facesContext) {


				if (engine.isPropertySetted(Properties.VISIBLE)==false) {
					return null;
				}
				
				return Boolean.valueOf(isVisible(facesContext));
			
	}

	public final java.lang.String getX() {
		return getX(null);
	}

	public final java.lang.String getX(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.X, facesContext);
	}

	public final void setX(java.lang.String x) {
		engine.setProperty(Properties.X, x);
	}

	public final void setX(ValueBinding x) {
		engine.setProperty(Properties.X, x);
	}

	public final java.lang.String getY() {
		return getY(null);
	}

	public final java.lang.String getY(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.Y, facesContext);
	}

	public final void setY(java.lang.String y) {
		engine.setProperty(Properties.Y, y);
	}

	public final void setY(ValueBinding y) {
		engine.setProperty(Properties.Y, y);
	}

	public final java.lang.String getMarginBottom() {
		return getMarginBottom(null);
	}

	public final java.lang.String getMarginBottom(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MARGIN_BOTTOM, facesContext);
	}

	public final void setMarginBottom(java.lang.String marginBottom) {
		engine.setProperty(Properties.MARGIN_BOTTOM, marginBottom);
	}

	public final void setMarginBottom(ValueBinding marginBottom) {
		engine.setProperty(Properties.MARGIN_BOTTOM, marginBottom);
	}

	public final java.lang.String getMarginLeft() {
		return getMarginLeft(null);
	}

	public final java.lang.String getMarginLeft(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MARGIN_LEFT, facesContext);
	}

	public final void setMarginLeft(java.lang.String marginLeft) {
		engine.setProperty(Properties.MARGIN_LEFT, marginLeft);
	}

	public final void setMarginLeft(ValueBinding marginLeft) {
		engine.setProperty(Properties.MARGIN_LEFT, marginLeft);
	}

	public final java.lang.String getMarginRight() {
		return getMarginRight(null);
	}

	public final java.lang.String getMarginRight(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MARGIN_RIGHT, facesContext);
	}

	public final void setMarginRight(java.lang.String marginRight) {
		engine.setProperty(Properties.MARGIN_RIGHT, marginRight);
	}

	public final void setMarginRight(ValueBinding marginRight) {
		engine.setProperty(Properties.MARGIN_RIGHT, marginRight);
	}

	public final java.lang.String getMarginTop() {
		return getMarginTop(null);
	}

	public final java.lang.String getMarginTop(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MARGIN_TOP, facesContext);
	}

	public final void setMarginTop(java.lang.String marginTop) {
		engine.setProperty(Properties.MARGIN_TOP, marginTop);
	}

	public final void setMarginTop(ValueBinding marginTop) {
		engine.setProperty(Properties.MARGIN_TOP, marginTop);
	}

	public final java.lang.String getHeight() {
		return getHeight(null);
	}

	public final java.lang.String getHeight(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.HEIGHT, facesContext);
	}

	public final void setHeight(java.lang.String height) {
		engine.setProperty(Properties.HEIGHT, height);
	}

	public final void setHeight(ValueBinding height) {
		engine.setProperty(Properties.HEIGHT, height);
	}

	public final java.lang.String getWidth() {
		return getWidth(null);
	}

	public final java.lang.String getWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.WIDTH, facesContext);
	}

	public final void setWidth(java.lang.String width) {
		engine.setProperty(Properties.WIDTH, width);
	}

	public final void setWidth(ValueBinding width) {
		engine.setProperty(Properties.WIDTH, width);
	}

	public final int getHiddenMode() {
		return getHiddenMode(null);
	}

	public final int getHiddenMode(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.HIDDEN_MODE,0, facesContext);
	}

	public final void setHiddenMode(int hiddenMode) {
		engine.setProperty(Properties.HIDDEN_MODE, hiddenMode);
	}

	public final void setHiddenMode(ValueBinding hiddenMode) {
		engine.setProperty(Properties.HIDDEN_MODE, hiddenMode);
	}

	public final boolean isVisible() {
		return isVisible(null);
	}

	public final boolean isVisible(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.VISIBLE, false, facesContext);
	}

	public final void setVisible(boolean visible) {
		engine.setProperty(Properties.VISIBLE, visible);
	}

	public final void setVisible(ValueBinding visible) {
		engine.setProperty(Properties.VISIBLE, visible);
	}

	public final Boolean getVisibleState() {


				return getVisibleState(null);
			
	}

	public final java.lang.String getLookId() {
		return getLookId(null);
	}

	public final java.lang.String getLookId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.LOOK_ID, facesContext);
	}

	public final void setLookId(java.lang.String lookId) {
		engine.setProperty(Properties.LOOK_ID, lookId);
	}

	public final void setLookId(ValueBinding lookId) {
		engine.setProperty(Properties.LOOK_ID, lookId);
	}

	public final java.lang.String getOrientation() {
		return getOrientation(null);
	}

	public final java.lang.String getOrientation(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ORIENTATION, facesContext);
	}

	public final void setOrientation(java.lang.String orientation) {
		engine.setProperty(Properties.ORIENTATION, orientation);
	}

	public final void setOrientation(ValueBinding orientation) {
		engine.setProperty(Properties.ORIENTATION, orientation);
	}

	public final java.lang.String getBackgroundColor() {
		return getBackgroundColor(null);
	}

	public final java.lang.String getBackgroundColor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BACKGROUND_COLOR, facesContext);
	}

	public final void setBackgroundColor(java.lang.String backgroundColor) {
		engine.setProperty(Properties.BACKGROUND_COLOR, backgroundColor);
	}

	public final void setBackgroundColor(ValueBinding backgroundColor) {
		engine.setProperty(Properties.BACKGROUND_COLOR, backgroundColor);
	}

	public final java.lang.String getForegroundColor() {
		return getForegroundColor(null);
	}

	public final java.lang.String getForegroundColor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOREGROUND_COLOR, facesContext);
	}

	public final void setForegroundColor(java.lang.String foregroundColor) {
		engine.setProperty(Properties.FOREGROUND_COLOR, foregroundColor);
	}

	public final void setForegroundColor(ValueBinding foregroundColor) {
		engine.setProperty(Properties.FOREGROUND_COLOR, foregroundColor);
	}

	public final java.lang.String getAlignment() {
		return getAlignment(null);
	}

	public final java.lang.String getAlignment(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ALIGNMENT, facesContext);
	}

	public final void setAlignment(java.lang.String alignment) {
		engine.setProperty(Properties.ALIGNMENT, alignment);
	}

	public final void setAlignment(ValueBinding alignment) {
		engine.setProperty(Properties.ALIGNMENT, alignment);
	}

	public void release() {
		super.release();
	}
	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
