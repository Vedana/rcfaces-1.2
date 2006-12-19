package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IBackgroundImageCapability;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.capability.IForCapability;
import org.rcfaces.core.component.capability.IInitEventCapability;
import org.rcfaces.core.component.capability.IMouseEventCapability;
import org.rcfaces.core.component.capability.ISeverityStyleClassCapability;
import org.rcfaces.core.internal.component.Properties;

/**
 * An englobing tag that changes the style class surronuding a component when a particular message is emitted for a component.
 * ex :
 * &lt;v:styledMessage errorStyleClass="formErreur" for="ef1"&gt;
 * &lt;v:textEntry
 * id="ef1"
 * ... /&gt;
 * &lt;/v:styledMessage&gt;
 */
public class StyledMessageComponent extends AbstractBasicComponent implements 
	IBackgroundImageCapability,
	IBorderCapability,
	IMouseEventCapability,
	IInitEventCapability,
	IForCapability,
	ISeverityStyleClassCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.styledMessage";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractBasicComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"errorStyleClass","fatalStyleClass","mouseOverListener","backgroundImageVerticalPosition","warnStyleClass","showIfMessage","styleClass","backgroundImageHorizontalPosition","backgroundImageVerticalRepeat","infoStyleClass","backgroundImageHorizontalRepeat","initListener","backgroundImageURL","border","mouseOutListener","setFocusIfMessage","for"}));
	}

	public StyledMessageComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public StyledMessageComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final java.lang.String getBackgroundImageHorizontalPosition() {
		return getBackgroundImageHorizontalPosition(null);
	}

	/**
	 * See {@link #getBackgroundImageHorizontalPosition() getBackgroundImageHorizontalPosition()} for more details
	 */
	public final java.lang.String getBackgroundImageHorizontalPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BACKGROUND_IMAGE_HORIZONTAL_POSITION, facesContext);
	}

	public final void setBackgroundImageHorizontalPosition(java.lang.String backgroundImageHorizontalPosition) {
		engine.setProperty(Properties.BACKGROUND_IMAGE_HORIZONTAL_POSITION, backgroundImageHorizontalPosition);
	}

	/**
	 * See {@link #setBackgroundImageHorizontalPosition(String) setBackgroundImageHorizontalPosition(String)} for more details
	 */
	public final void setBackgroundImageHorizontalPosition(ValueBinding backgroundImageHorizontalPosition) {
		engine.setProperty(Properties.BACKGROUND_IMAGE_HORIZONTAL_POSITION, backgroundImageHorizontalPosition);
	}

	public final boolean isBackgroundImageHorizontalRepeat() {
		return isBackgroundImageHorizontalRepeat(null);
	}

	/**
	 * See {@link #isBackgroundImageHorizontalRepeat() isBackgroundImageHorizontalRepeat()} for more details
	 */
	public final boolean isBackgroundImageHorizontalRepeat(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.BACKGROUND_IMAGE_HORIZONTAL_REPEAT, false, facesContext);
	}

	public final void setBackgroundImageHorizontalRepeat(boolean backgroundImageHorizontalRepeat) {
		engine.setProperty(Properties.BACKGROUND_IMAGE_HORIZONTAL_REPEAT, backgroundImageHorizontalRepeat);
	}

	/**
	 * See {@link #setBackgroundImageHorizontalRepeat(boolean) setBackgroundImageHorizontalRepeat(boolean)} for more details
	 */
	public final void setBackgroundImageHorizontalRepeat(ValueBinding backgroundImageHorizontalRepeat) {
		engine.setProperty(Properties.BACKGROUND_IMAGE_HORIZONTAL_REPEAT, backgroundImageHorizontalRepeat);
	}

	public final java.lang.String getBackgroundImageURL() {
		return getBackgroundImageURL(null);
	}

	/**
	 * See {@link #getBackgroundImageURL() getBackgroundImageURL()} for more details
	 */
	public final java.lang.String getBackgroundImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BACKGROUND_IMAGE_URL, facesContext);
	}

	public final void setBackgroundImageURL(java.lang.String backgroundImageURL) {
		engine.setProperty(Properties.BACKGROUND_IMAGE_URL, backgroundImageURL);
	}

	/**
	 * See {@link #setBackgroundImageURL(String) setBackgroundImageURL(String)} for more details
	 */
	public final void setBackgroundImageURL(ValueBinding backgroundImageURL) {
		engine.setProperty(Properties.BACKGROUND_IMAGE_URL, backgroundImageURL);
	}

	public final java.lang.String getBackgroundImageVerticalPosition() {
		return getBackgroundImageVerticalPosition(null);
	}

	/**
	 * See {@link #getBackgroundImageVerticalPosition() getBackgroundImageVerticalPosition()} for more details
	 */
	public final java.lang.String getBackgroundImageVerticalPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BACKGROUND_IMAGE_VERTICAL_POSITION, facesContext);
	}

	public final void setBackgroundImageVerticalPosition(java.lang.String backgroundImageVerticalPosition) {
		engine.setProperty(Properties.BACKGROUND_IMAGE_VERTICAL_POSITION, backgroundImageVerticalPosition);
	}

	/**
	 * See {@link #setBackgroundImageVerticalPosition(String) setBackgroundImageVerticalPosition(String)} for more details
	 */
	public final void setBackgroundImageVerticalPosition(ValueBinding backgroundImageVerticalPosition) {
		engine.setProperty(Properties.BACKGROUND_IMAGE_VERTICAL_POSITION, backgroundImageVerticalPosition);
	}

	public final boolean isBackgroundImageVerticalRepeat() {
		return isBackgroundImageVerticalRepeat(null);
	}

	/**
	 * See {@link #isBackgroundImageVerticalRepeat() isBackgroundImageVerticalRepeat()} for more details
	 */
	public final boolean isBackgroundImageVerticalRepeat(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.BACKGROUND_IMAGE_VERTICAL_REPEAT, false, facesContext);
	}

	public final void setBackgroundImageVerticalRepeat(boolean backgroundImageVerticalRepeat) {
		engine.setProperty(Properties.BACKGROUND_IMAGE_VERTICAL_REPEAT, backgroundImageVerticalRepeat);
	}

	/**
	 * See {@link #setBackgroundImageVerticalRepeat(boolean) setBackgroundImageVerticalRepeat(boolean)} for more details
	 */
	public final void setBackgroundImageVerticalRepeat(ValueBinding backgroundImageVerticalRepeat) {
		engine.setProperty(Properties.BACKGROUND_IMAGE_VERTICAL_REPEAT, backgroundImageVerticalRepeat);
	}

	public final boolean isBorder() {
		return isBorder(null);
	}

	/**
	 * See {@link #isBorder() isBorder()} for more details
	 */
	public final boolean isBorder(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.BORDER, true, facesContext);
	}

	public final void setBorder(boolean border) {
		engine.setProperty(Properties.BORDER, border);
	}

	/**
	 * See {@link #setBorder(boolean) setBorder(boolean)} for more details
	 */
	public final void setBorder(ValueBinding border) {
		engine.setProperty(Properties.BORDER, border);
	}

	public final void addMouseOutListener(org.rcfaces.core.event.IMouseOutListener listener) {
		addFacesListener(listener);
	}

	public final void removeMouseOutListener(org.rcfaces.core.event.IMouseOutListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listMouseOutListeners() {
		return getFacesListeners(org.rcfaces.core.event.IMouseOutListener.class);
	}

	public final void addMouseOverListener(org.rcfaces.core.event.IMouseOverListener listener) {
		addFacesListener(listener);
	}

	public final void removeMouseOverListener(org.rcfaces.core.event.IMouseOverListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listMouseOverListeners() {
		return getFacesListeners(org.rcfaces.core.event.IMouseOverListener.class);
	}

	public final void addInitListener(org.rcfaces.core.event.IInitListener listener) {
		addFacesListener(listener);
	}

	public final void removeInitListener(org.rcfaces.core.event.IInitListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listInitListeners() {
		return getFacesListeners(org.rcfaces.core.event.IInitListener.class);
	}

	public final java.lang.String getFor() {
		return getFor(null);
	}

	/**
	 * See {@link #getFor() getFor()} for more details
	 */
	public final java.lang.String getFor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOR, facesContext);
	}

	public final void setFor(java.lang.String forValue) {
		engine.setProperty(Properties.FOR, forValue);
	}

	/**
	 * See {@link #setFor(String) setFor(String)} for more details
	 */
	public final void setFor(ValueBinding forValue) {
		engine.setProperty(Properties.FOR, forValue);
	}

	public final java.lang.String getErrorStyleClass() {
		return getErrorStyleClass(null);
	}

	/**
	 * See {@link #getErrorStyleClass() getErrorStyleClass()} for more details
	 */
	public final java.lang.String getErrorStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ERROR_STYLE_CLASS, facesContext);
	}

	public final void setErrorStyleClass(java.lang.String errorStyleClass) {
		engine.setProperty(Properties.ERROR_STYLE_CLASS, errorStyleClass);
	}

	/**
	 * See {@link #setErrorStyleClass(String) setErrorStyleClass(String)} for more details
	 */
	public final void setErrorStyleClass(ValueBinding errorStyleClass) {
		engine.setProperty(Properties.ERROR_STYLE_CLASS, errorStyleClass);
	}

	public final java.lang.String getFatalStyleClass() {
		return getFatalStyleClass(null);
	}

	/**
	 * See {@link #getFatalStyleClass() getFatalStyleClass()} for more details
	 */
	public final java.lang.String getFatalStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FATAL_STYLE_CLASS, facesContext);
	}

	public final void setFatalStyleClass(java.lang.String fatalStyleClass) {
		engine.setProperty(Properties.FATAL_STYLE_CLASS, fatalStyleClass);
	}

	/**
	 * See {@link #setFatalStyleClass(String) setFatalStyleClass(String)} for more details
	 */
	public final void setFatalStyleClass(ValueBinding fatalStyleClass) {
		engine.setProperty(Properties.FATAL_STYLE_CLASS, fatalStyleClass);
	}

	public final java.lang.String getInfoStyleClass() {
		return getInfoStyleClass(null);
	}

	/**
	 * See {@link #getInfoStyleClass() getInfoStyleClass()} for more details
	 */
	public final java.lang.String getInfoStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.INFO_STYLE_CLASS, facesContext);
	}

	public final void setInfoStyleClass(java.lang.String infoStyleClass) {
		engine.setProperty(Properties.INFO_STYLE_CLASS, infoStyleClass);
	}

	/**
	 * See {@link #setInfoStyleClass(String) setInfoStyleClass(String)} for more details
	 */
	public final void setInfoStyleClass(ValueBinding infoStyleClass) {
		engine.setProperty(Properties.INFO_STYLE_CLASS, infoStyleClass);
	}

	public final java.lang.String getWarnStyleClass() {
		return getWarnStyleClass(null);
	}

	/**
	 * See {@link #getWarnStyleClass() getWarnStyleClass()} for more details
	 */
	public final java.lang.String getWarnStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.WARN_STYLE_CLASS, facesContext);
	}

	public final void setWarnStyleClass(java.lang.String warnStyleClass) {
		engine.setProperty(Properties.WARN_STYLE_CLASS, warnStyleClass);
	}

	/**
	 * See {@link #setWarnStyleClass(String) setWarnStyleClass(String)} for more details
	 */
	public final void setWarnStyleClass(ValueBinding warnStyleClass) {
		engine.setProperty(Properties.WARN_STYLE_CLASS, warnStyleClass);
	}

	public final boolean isShowIfMessage() {
		return isShowIfMessage(null);
	}

	public final boolean isShowIfMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.SHOW_IF_MESSAGE, false, facesContext);
	}

	public final void setShowIfMessage(boolean showIfMessage) {
		engine.setProperty(Properties.SHOW_IF_MESSAGE, showIfMessage);
	}

	public final void setShowIfMessage(ValueBinding showIfMessage) {
		engine.setProperty(Properties.SHOW_IF_MESSAGE, showIfMessage);
	}

	/**
	 * Returns <code>true</code> if the attribute "showIfMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isShowIfMessageSetted() {
		return engine.isPropertySetted(Properties.SHOW_IF_MESSAGE);
	}

	public final boolean isSetFocusIfMessage() {
		return isSetFocusIfMessage(null);
	}

	public final boolean isSetFocusIfMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.SET_FOCUS_IF_MESSAGE, false, facesContext);
	}

	public final void setSetFocusIfMessage(boolean setFocusIfMessage) {
		engine.setProperty(Properties.SET_FOCUS_IF_MESSAGE, setFocusIfMessage);
	}

	public final void setSetFocusIfMessage(ValueBinding setFocusIfMessage) {
		engine.setProperty(Properties.SET_FOCUS_IF_MESSAGE, setFocusIfMessage);
	}

	/**
	 * Returns <code>true</code> if the attribute "setFocusIfMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSetFocusIfMessageSetted() {
		return engine.isPropertySetted(Properties.SET_FOCUS_IF_MESSAGE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
