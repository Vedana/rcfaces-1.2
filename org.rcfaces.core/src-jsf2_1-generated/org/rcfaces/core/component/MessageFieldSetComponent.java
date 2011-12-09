package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.component.FieldSetComponent;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IForCapability;
import org.rcfaces.core.component.capability.ISeverityImagesCapability;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import java.util.Set;
import java.util.Arrays;
import java.util.Collection;
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

	private static final Log LOG = LogFactory.getLog(MessageFieldSetComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.messageFieldSet";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=FieldSetComponent.BEHAVIOR_EVENT_NAMES;

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
		return (String)getStateHelper().eval(Properties.FOR);
	}

	/**
	 * Returns <code>true</code> if the attribute "for" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isForSetted() {
		return getStateHelper().get(Properties.FOR)!=null;
	}

	public void setFor(java.lang.String forValue) {
		getStateHelper().put(Properties.FOR, forValue);
	}

	public java.lang.String getErrorStyleClass() {
		return getErrorStyleClass(null);
	}

	/**
	 * See {@link #getErrorStyleClass() getErrorStyleClass()} for more details
	 */
	public java.lang.String getErrorStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ERROR_STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "errorStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isErrorStyleClassSetted() {
		return getStateHelper().get(Properties.ERROR_STYLE_CLASS)!=null;
	}

	public void setErrorStyleClass(java.lang.String errorStyleClass) {
		getStateHelper().put(Properties.ERROR_STYLE_CLASS, errorStyleClass);
	}

	public java.lang.String getFatalStyleClass() {
		return getFatalStyleClass(null);
	}

	/**
	 * See {@link #getFatalStyleClass() getFatalStyleClass()} for more details
	 */
	public java.lang.String getFatalStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.FATAL_STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "fatalStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFatalStyleClassSetted() {
		return getStateHelper().get(Properties.FATAL_STYLE_CLASS)!=null;
	}

	public void setFatalStyleClass(java.lang.String fatalStyleClass) {
		getStateHelper().put(Properties.FATAL_STYLE_CLASS, fatalStyleClass);
	}

	public java.lang.String getInfoStyleClass() {
		return getInfoStyleClass(null);
	}

	/**
	 * See {@link #getInfoStyleClass() getInfoStyleClass()} for more details
	 */
	public java.lang.String getInfoStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.INFO_STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "infoStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isInfoStyleClassSetted() {
		return getStateHelper().get(Properties.INFO_STYLE_CLASS)!=null;
	}

	public void setInfoStyleClass(java.lang.String infoStyleClass) {
		getStateHelper().put(Properties.INFO_STYLE_CLASS, infoStyleClass);
	}

	public java.lang.String getWarnStyleClass() {
		return getWarnStyleClass(null);
	}

	/**
	 * See {@link #getWarnStyleClass() getWarnStyleClass()} for more details
	 */
	public java.lang.String getWarnStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.WARN_STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "warnStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isWarnStyleClassSetted() {
		return getStateHelper().get(Properties.WARN_STYLE_CLASS)!=null;
	}

	public void setWarnStyleClass(java.lang.String warnStyleClass) {
		getStateHelper().put(Properties.WARN_STYLE_CLASS, warnStyleClass);
	}

	public java.lang.String getErrorImageURL() {
		return getErrorImageURL(null);
	}

	/**
	 * See {@link #getErrorImageURL() getErrorImageURL()} for more details
	 */
	public java.lang.String getErrorImageURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ERROR_IMAGE_URL);
	}

	/**
	 * Returns <code>true</code> if the attribute "errorImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isErrorImageURLSetted() {
		return getStateHelper().get(Properties.ERROR_IMAGE_URL)!=null;
	}

	public void setErrorImageURL(java.lang.String errorImageURL) {
		getStateHelper().put(Properties.ERROR_IMAGE_URL, errorImageURL);
	}

	public java.lang.String getFatalImageURL() {
		return getFatalImageURL(null);
	}

	/**
	 * See {@link #getFatalImageURL() getFatalImageURL()} for more details
	 */
	public java.lang.String getFatalImageURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.FATAL_IMAGE_URL);
	}

	/**
	 * Returns <code>true</code> if the attribute "fatalImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFatalImageURLSetted() {
		return getStateHelper().get(Properties.FATAL_IMAGE_URL)!=null;
	}

	public void setFatalImageURL(java.lang.String fatalImageURL) {
		getStateHelper().put(Properties.FATAL_IMAGE_URL, fatalImageURL);
	}

	public java.lang.String getInfoImageURL() {
		return getInfoImageURL(null);
	}

	/**
	 * See {@link #getInfoImageURL() getInfoImageURL()} for more details
	 */
	public java.lang.String getInfoImageURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.INFO_IMAGE_URL);
	}

	/**
	 * Returns <code>true</code> if the attribute "infoImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isInfoImageURLSetted() {
		return getStateHelper().get(Properties.INFO_IMAGE_URL)!=null;
	}

	public void setInfoImageURL(java.lang.String infoImageURL) {
		getStateHelper().put(Properties.INFO_IMAGE_URL, infoImageURL);
	}

	public java.lang.String getWarnImageURL() {
		return getWarnImageURL(null);
	}

	/**
	 * See {@link #getWarnImageURL() getWarnImageURL()} for more details
	 */
	public java.lang.String getWarnImageURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.WARN_IMAGE_URL);
	}

	/**
	 * Returns <code>true</code> if the attribute "warnImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isWarnImageURLSetted() {
		return getStateHelper().get(Properties.WARN_IMAGE_URL)!=null;
	}

	public void setWarnImageURL(java.lang.String warnImageURL) {
		getStateHelper().put(Properties.WARN_IMAGE_URL, warnImageURL);
	}

	public boolean isSetFocusIfMessage() {
		return isSetFocusIfMessage(null);
	}

	public boolean isSetFocusIfMessage(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.SET_FOCUS_IF_MESSAGE, false);
	}

	public void setSetFocusIfMessage(boolean setFocusIfMessage) {
		 getStateHelper().put(Properties.SET_FOCUS_IF_MESSAGE, setFocusIfMessage);
	}

	/**
	 * Returns <code>true</code> if the attribute "setFocusIfMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSetFocusIfMessageSetted() {
		return getStateHelper().get(Properties.SET_FOCUS_IF_MESSAGE)!=null;
	}

	public boolean isShowActiveComponentMessage() {
		return isShowActiveComponentMessage(null);
	}

	public boolean isShowActiveComponentMessage(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.SHOW_ACTIVE_COMPONENT_MESSAGE, false);
	}

	public void setShowActiveComponentMessage(boolean showActiveComponentMessage) {
		 getStateHelper().put(Properties.SHOW_ACTIVE_COMPONENT_MESSAGE, showActiveComponentMessage);
	}

	/**
	 * Returns <code>true</code> if the attribute "showActiveComponentMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isShowActiveComponentMessageSetted() {
		return getStateHelper().get(Properties.SHOW_ACTIVE_COMPONENT_MESSAGE)!=null;
	}

}
