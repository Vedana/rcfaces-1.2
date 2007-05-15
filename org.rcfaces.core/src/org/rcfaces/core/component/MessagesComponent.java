package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.ISeverityStyleClassCapability;
import org.rcfaces.core.internal.component.Properties;

/**
 * <p>The messages Component is a placeholder for error messages (several messages can be shown simultaneously).</p>
 * <p>The messages Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text, font &amp; image</li>
 * <li>Margin</li>
 * <li>Help</li>
 * <li>Visibility</li>
 * <li>Events Handling</li>
 * </ul>
 * </p>
 */
public class MessagesComponent extends AbstractMessagesComponent implements 
	ISeverityStyleClassCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.messages";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractMessagesComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"showActiveComponentMessage","errorStyleClass","showIfMessage","styleClass","fatalStyleClass","infoStyleClass","warnStyleClass"}));
	}

	public MessagesComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public MessagesComponent(String componentId) {
		this();
		setId(componentId);
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

	/**
	 * See {@link #setErrorStyleClass(String) setErrorStyleClass(String)} for more details
	 */
	public void setErrorStyleClass(ValueBinding errorStyleClass) {
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

	/**
	 * See {@link #setFatalStyleClass(String) setFatalStyleClass(String)} for more details
	 */
	public void setFatalStyleClass(ValueBinding fatalStyleClass) {
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

	/**
	 * See {@link #setInfoStyleClass(String) setInfoStyleClass(String)} for more details
	 */
	public void setInfoStyleClass(ValueBinding infoStyleClass) {
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

	/**
	 * See {@link #setWarnStyleClass(String) setWarnStyleClass(String)} for more details
	 */
	public void setWarnStyleClass(ValueBinding warnStyleClass) {
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

	public final boolean isShowActiveComponentMessage() {
		return isShowActiveComponentMessage(null);
	}

	public final boolean isShowActiveComponentMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.SHOW_ACTIVE_COMPONENT_MESSAGE, false, facesContext);
	}

	public final void setShowActiveComponentMessage(boolean showActiveComponentMessage) {
		engine.setProperty(Properties.SHOW_ACTIVE_COMPONENT_MESSAGE, showActiveComponentMessage);
	}

	public final void setShowActiveComponentMessage(ValueBinding showActiveComponentMessage) {
		engine.setProperty(Properties.SHOW_ACTIVE_COMPONENT_MESSAGE, showActiveComponentMessage);
	}

	/**
	 * Returns <code>true</code> if the attribute "showActiveComponentMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isShowActiveComponentMessageSetted() {
		return engine.isPropertySetted(Properties.SHOW_ACTIVE_COMPONENT_MESSAGE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
