package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.rcfaces.core.component.familly.IContentAccessors;
import java.util.HashSet;
import org.rcfaces.core.component.FieldSetComponent;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.capability.IForCapability;
import org.rcfaces.core.component.capability.ISeverityImagesCapability;
import org.rcfaces.core.component.capability.ISeverityStyleClassCapability;

/**
 * <p>The messageFieldSet Component is a <a href="/comps/fieldSetComponent.html">fieldSet Component</a> combined with a <a href="/comps/messageComponent.html">message COmponent</a> in the title part.</p>
 * <p>The messageFieldSet Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text, font &amp; image</li>
 * <li>Margin &amp; border</li>
 * <li>Help</li>
 * <li>Visibility</li>
 * <li>Background Image</li>
 * <li>Events Handling</li>
 * <li>Contextual Menu</li>
 * <li>Async Render (AJAX)</li>
 * </ul>
 * </p>
 */
public class MessageFieldSetComponent extends FieldSetComponent implements 
	IForCapability,
	ISeverityStyleClassCapability,
	ISeverityImagesCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.messageFieldSet";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(FieldSetComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"fatalStyleClass","for","styleClass","fatalImageURL","setFocusIfMessage","showActiveComponentMessage","errorStyleClass","warnStyleClass","infoStyleClass","infoImageURL","warnImageURL","errorImageURL","imageURL"}));
	}

	public MessageFieldSetComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public MessageFieldSetComponent(String componentId) {
		this();
		setId(componentId);
	}

	public java.lang.String getFor() {
		return getFor(null);
	}

	/**
	 * See {@link #getFor() getFor()} for more details
	 */
	public java.lang.String getFor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOR, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "for" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isForSetted() {
		return engine.isPropertySetted(Properties.FOR);
	}

	public void setFor(java.lang.String forValue) {
		engine.setProperty(Properties.FOR, forValue);
	}

	public java.lang.String getErrorStyleClass() {
		return getErrorStyleClass(null);
	}

	/**
	 * See {@link #getErrorStyleClass() getErrorStyleClass()} for more details
	 */
	public java.lang.String getErrorStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ERROR_STYLE_CLASS, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "errorStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isErrorStyleClassSetted() {
		return engine.isPropertySetted(Properties.ERROR_STYLE_CLASS);
	}

	public void setErrorStyleClass(java.lang.String errorStyleClass) {
		engine.setProperty(Properties.ERROR_STYLE_CLASS, errorStyleClass);
	}

	public java.lang.String getFatalStyleClass() {
		return getFatalStyleClass(null);
	}

	/**
	 * See {@link #getFatalStyleClass() getFatalStyleClass()} for more details
	 */
	public java.lang.String getFatalStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FATAL_STYLE_CLASS, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "fatalStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFatalStyleClassSetted() {
		return engine.isPropertySetted(Properties.FATAL_STYLE_CLASS);
	}

	public void setFatalStyleClass(java.lang.String fatalStyleClass) {
		engine.setProperty(Properties.FATAL_STYLE_CLASS, fatalStyleClass);
	}

	public java.lang.String getInfoStyleClass() {
		return getInfoStyleClass(null);
	}

	/**
	 * See {@link #getInfoStyleClass() getInfoStyleClass()} for more details
	 */
	public java.lang.String getInfoStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.INFO_STYLE_CLASS, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "infoStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isInfoStyleClassSetted() {
		return engine.isPropertySetted(Properties.INFO_STYLE_CLASS);
	}

	public void setInfoStyleClass(java.lang.String infoStyleClass) {
		engine.setProperty(Properties.INFO_STYLE_CLASS, infoStyleClass);
	}

	public java.lang.String getWarnStyleClass() {
		return getWarnStyleClass(null);
	}

	/**
	 * See {@link #getWarnStyleClass() getWarnStyleClass()} for more details
	 */
	public java.lang.String getWarnStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.WARN_STYLE_CLASS, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "warnStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isWarnStyleClassSetted() {
		return engine.isPropertySetted(Properties.WARN_STYLE_CLASS);
	}

	public void setWarnStyleClass(java.lang.String warnStyleClass) {
		engine.setProperty(Properties.WARN_STYLE_CLASS, warnStyleClass);
	}

	public java.lang.String getErrorImageURL() {
		return getErrorImageURL(null);
	}

	/**
	 * See {@link #getErrorImageURL() getErrorImageURL()} for more details
	 */
	public java.lang.String getErrorImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ERROR_IMAGE_URL, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "errorImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isErrorImageURLSetted() {
		return engine.isPropertySetted(Properties.ERROR_IMAGE_URL);
	}

	public void setErrorImageURL(java.lang.String errorImageURL) {
		engine.setProperty(Properties.ERROR_IMAGE_URL, errorImageURL);
	}

	public java.lang.String getFatalImageURL() {
		return getFatalImageURL(null);
	}

	/**
	 * See {@link #getFatalImageURL() getFatalImageURL()} for more details
	 */
	public java.lang.String getFatalImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FATAL_IMAGE_URL, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "fatalImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFatalImageURLSetted() {
		return engine.isPropertySetted(Properties.FATAL_IMAGE_URL);
	}

	public void setFatalImageURL(java.lang.String fatalImageURL) {
		engine.setProperty(Properties.FATAL_IMAGE_URL, fatalImageURL);
	}

	public java.lang.String getInfoImageURL() {
		return getInfoImageURL(null);
	}

	/**
	 * See {@link #getInfoImageURL() getInfoImageURL()} for more details
	 */
	public java.lang.String getInfoImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.INFO_IMAGE_URL, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "infoImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isInfoImageURLSetted() {
		return engine.isPropertySetted(Properties.INFO_IMAGE_URL);
	}

	public void setInfoImageURL(java.lang.String infoImageURL) {
		engine.setProperty(Properties.INFO_IMAGE_URL, infoImageURL);
	}

	public java.lang.String getWarnImageURL() {
		return getWarnImageURL(null);
	}

	/**
	 * See {@link #getWarnImageURL() getWarnImageURL()} for more details
	 */
	public java.lang.String getWarnImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.WARN_IMAGE_URL, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "warnImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isWarnImageURLSetted() {
		return engine.isPropertySetted(Properties.WARN_IMAGE_URL);
	}

	public void setWarnImageURL(java.lang.String warnImageURL) {
		engine.setProperty(Properties.WARN_IMAGE_URL, warnImageURL);
	}

	public boolean isSetFocusIfMessage() {
		return isSetFocusIfMessage(null);
	}

	public boolean isSetFocusIfMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.SET_FOCUS_IF_MESSAGE, false, facesContext);
	}

	public void setSetFocusIfMessage(boolean setFocusIfMessage) {
		engine.setProperty(Properties.SET_FOCUS_IF_MESSAGE, setFocusIfMessage);
	}

	/**
	 * Returns <code>true</code> if the attribute "setFocusIfMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSetFocusIfMessageSetted() {
		return engine.isPropertySetted(Properties.SET_FOCUS_IF_MESSAGE);
	}

	public boolean isShowActiveComponentMessage() {
		return isShowActiveComponentMessage(null);
	}

	public boolean isShowActiveComponentMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.SHOW_ACTIVE_COMPONENT_MESSAGE, false, facesContext);
	}

	public void setShowActiveComponentMessage(boolean showActiveComponentMessage) {
		engine.setProperty(Properties.SHOW_ACTIVE_COMPONENT_MESSAGE, showActiveComponentMessage);
	}

	/**
	 * Returns <code>true</code> if the attribute "showActiveComponentMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isShowActiveComponentMessageSetted() {
		return engine.isPropertySetted(Properties.SHOW_ACTIVE_COMPONENT_MESSAGE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
