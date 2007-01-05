package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IAlignmentCapability;
import org.rcfaces.core.component.capability.IForegroundBackgroundColorCapability;
import org.rcfaces.core.component.capability.ILookAndFeelCapability;
import org.rcfaces.core.component.capability.IMarginCapability;
import org.rcfaces.core.component.capability.IOrientationCapability;
import org.rcfaces.core.component.capability.IPositionCapability;
import org.rcfaces.core.component.capability.ISizeCapability;
import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.HiddenModeConverter;
import org.rcfaces.core.internal.tools.MarginTools;

/**
 * <p>The ruler Component is the &lt;HR&gt; HTML equivalent.</p>
 * <p>It is used often when simple HTML is not desirable : for example if a part of a page is loaded via AJAX it might be easier to have only a jsf tree memory represantation.</p>
 * <p>The ruler Component has the following capability :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Visibility</li>
 * <li>Margin</li>
 * </ul>
 * </p>
 */
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


			setHiddenMode(((Integer)HiddenModeConverter.SINGLETON.getAsObject(null, this, hiddenMode)).intValue());
		
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

	/**
	 * See {@link #getX() getX()} for more details
	 */
	public final java.lang.String getX(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.X, facesContext);
	}

	public final void setX(java.lang.String x) {
		engine.setProperty(Properties.X, x);
	}

	/**
	 * See {@link #setX(String) setX(String)} for more details
	 */
	public final void setX(ValueBinding x) {
		engine.setProperty(Properties.X, x);
	}

	public final java.lang.String getY() {
		return getY(null);
	}

	/**
	 * See {@link #getY() getY()} for more details
	 */
	public final java.lang.String getY(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.Y, facesContext);
	}

	public final void setY(java.lang.String y) {
		engine.setProperty(Properties.Y, y);
	}

	/**
	 * See {@link #setY(String) setY(String)} for more details
	 */
	public final void setY(ValueBinding y) {
		engine.setProperty(Properties.Y, y);
	}

	public final java.lang.String getMarginBottom() {
		return getMarginBottom(null);
	}

	/**
	 * See {@link #getMarginBottom() getMarginBottom()} for more details
	 */
	public final java.lang.String getMarginBottom(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MARGIN_BOTTOM, facesContext);
	}

	public final void setMarginBottom(java.lang.String marginBottom) {
		engine.setProperty(Properties.MARGIN_BOTTOM, marginBottom);
	}

	/**
	 * See {@link #setMarginBottom(String) setMarginBottom(String)} for more details
	 */
	public final void setMarginBottom(ValueBinding marginBottom) {
		engine.setProperty(Properties.MARGIN_BOTTOM, marginBottom);
	}

	public final java.lang.String getMarginLeft() {
		return getMarginLeft(null);
	}

	/**
	 * See {@link #getMarginLeft() getMarginLeft()} for more details
	 */
	public final java.lang.String getMarginLeft(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MARGIN_LEFT, facesContext);
	}

	public final void setMarginLeft(java.lang.String marginLeft) {
		engine.setProperty(Properties.MARGIN_LEFT, marginLeft);
	}

	/**
	 * See {@link #setMarginLeft(String) setMarginLeft(String)} for more details
	 */
	public final void setMarginLeft(ValueBinding marginLeft) {
		engine.setProperty(Properties.MARGIN_LEFT, marginLeft);
	}

	public final java.lang.String getMarginRight() {
		return getMarginRight(null);
	}

	/**
	 * See {@link #getMarginRight() getMarginRight()} for more details
	 */
	public final java.lang.String getMarginRight(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MARGIN_RIGHT, facesContext);
	}

	public final void setMarginRight(java.lang.String marginRight) {
		engine.setProperty(Properties.MARGIN_RIGHT, marginRight);
	}

	/**
	 * See {@link #setMarginRight(String) setMarginRight(String)} for more details
	 */
	public final void setMarginRight(ValueBinding marginRight) {
		engine.setProperty(Properties.MARGIN_RIGHT, marginRight);
	}

	public final java.lang.String getMarginTop() {
		return getMarginTop(null);
	}

	/**
	 * See {@link #getMarginTop() getMarginTop()} for more details
	 */
	public final java.lang.String getMarginTop(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MARGIN_TOP, facesContext);
	}

	public final void setMarginTop(java.lang.String marginTop) {
		engine.setProperty(Properties.MARGIN_TOP, marginTop);
	}

	/**
	 * See {@link #setMarginTop(String) setMarginTop(String)} for more details
	 */
	public final void setMarginTop(ValueBinding marginTop) {
		engine.setProperty(Properties.MARGIN_TOP, marginTop);
	}

	public final java.lang.String getHeight() {
		return getHeight(null);
	}

	/**
	 * See {@link #getHeight() getHeight()} for more details
	 */
	public final java.lang.String getHeight(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.HEIGHT, facesContext);
	}

	public final void setHeight(java.lang.String height) {
		engine.setProperty(Properties.HEIGHT, height);
	}

	/**
	 * See {@link #setHeight(String) setHeight(String)} for more details
	 */
	public final void setHeight(ValueBinding height) {
		engine.setProperty(Properties.HEIGHT, height);
	}

	public final java.lang.String getWidth() {
		return getWidth(null);
	}

	/**
	 * See {@link #getWidth() getWidth()} for more details
	 */
	public final java.lang.String getWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.WIDTH, facesContext);
	}

	public final void setWidth(java.lang.String width) {
		engine.setProperty(Properties.WIDTH, width);
	}

	/**
	 * See {@link #setWidth(String) setWidth(String)} for more details
	 */
	public final void setWidth(ValueBinding width) {
		engine.setProperty(Properties.WIDTH, width);
	}

	public final int getHiddenMode() {
		return getHiddenMode(null);
	}

	/**
	 * See {@link #getHiddenMode() getHiddenMode()} for more details
	 */
	public final int getHiddenMode(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.HIDDEN_MODE,0, facesContext);
	}

	public final void setHiddenMode(int hiddenMode) {
		engine.setProperty(Properties.HIDDEN_MODE, hiddenMode);
	}

	/**
	 * See {@link #setHiddenMode(int) setHiddenMode(int)} for more details
	 */
	public final void setHiddenMode(ValueBinding hiddenMode) {
		engine.setProperty(Properties.HIDDEN_MODE, hiddenMode);
	}

	public final boolean isVisible() {
		return isVisible(null);
	}

	/**
	 * See {@link #isVisible() isVisible()} for more details
	 */
	public final boolean isVisible(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.VISIBLE, false, facesContext);
	}

	public final void setVisible(boolean visible) {
		engine.setProperty(Properties.VISIBLE, visible);
	}

	/**
	 * See {@link #setVisible(boolean) setVisible(boolean)} for more details
	 */
	public final void setVisible(ValueBinding visible) {
		engine.setProperty(Properties.VISIBLE, visible);
	}

	public final Boolean getVisibleState() {


			return getVisibleState(null);
		
	}

	public final java.lang.String getLookId() {
		return getLookId(null);
	}

	/**
	 * See {@link #getLookId() getLookId()} for more details
	 */
	public final java.lang.String getLookId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.LOOK_ID, facesContext);
	}

	public final void setLookId(java.lang.String lookId) {
		engine.setProperty(Properties.LOOK_ID, lookId);
	}

	/**
	 * See {@link #setLookId(String) setLookId(String)} for more details
	 */
	public final void setLookId(ValueBinding lookId) {
		engine.setProperty(Properties.LOOK_ID, lookId);
	}

	public final java.lang.String getOrientation() {
		return getOrientation(null);
	}

	/**
	 * See {@link #getOrientation() getOrientation()} for more details
	 */
	public final java.lang.String getOrientation(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ORIENTATION, facesContext);
	}

	public final void setOrientation(java.lang.String orientation) {
		engine.setProperty(Properties.ORIENTATION, orientation);
	}

	/**
	 * See {@link #setOrientation(String) setOrientation(String)} for more details
	 */
	public final void setOrientation(ValueBinding orientation) {
		engine.setProperty(Properties.ORIENTATION, orientation);
	}

	public final java.lang.String getBackgroundColor() {
		return getBackgroundColor(null);
	}

	/**
	 * See {@link #getBackgroundColor() getBackgroundColor()} for more details
	 */
	public final java.lang.String getBackgroundColor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BACKGROUND_COLOR, facesContext);
	}

	public final void setBackgroundColor(java.lang.String backgroundColor) {
		engine.setProperty(Properties.BACKGROUND_COLOR, backgroundColor);
	}

	/**
	 * See {@link #setBackgroundColor(String) setBackgroundColor(String)} for more details
	 */
	public final void setBackgroundColor(ValueBinding backgroundColor) {
		engine.setProperty(Properties.BACKGROUND_COLOR, backgroundColor);
	}

	public final java.lang.String getForegroundColor() {
		return getForegroundColor(null);
	}

	/**
	 * See {@link #getForegroundColor() getForegroundColor()} for more details
	 */
	public final java.lang.String getForegroundColor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOREGROUND_COLOR, facesContext);
	}

	public final void setForegroundColor(java.lang.String foregroundColor) {
		engine.setProperty(Properties.FOREGROUND_COLOR, foregroundColor);
	}

	/**
	 * See {@link #setForegroundColor(String) setForegroundColor(String)} for more details
	 */
	public final void setForegroundColor(ValueBinding foregroundColor) {
		engine.setProperty(Properties.FOREGROUND_COLOR, foregroundColor);
	}

	public final java.lang.String getAlignment() {
		return getAlignment(null);
	}

	/**
	 * See {@link #getAlignment() getAlignment()} for more details
	 */
	public final java.lang.String getAlignment(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ALIGNMENT, facesContext);
	}

	public final void setAlignment(java.lang.String alignment) {
		engine.setProperty(Properties.ALIGNMENT, alignment);
	}

	/**
	 * See {@link #setAlignment(String) setAlignment(String)} for more details
	 */
	public final void setAlignment(ValueBinding alignment) {
		engine.setProperty(Properties.ALIGNMENT, alignment);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
