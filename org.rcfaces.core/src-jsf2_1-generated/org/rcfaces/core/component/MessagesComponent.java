package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.rcfaces.core.component.AbstractMessagesComponent;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import java.util.Collection;
import org.rcfaces.core.component.capability.ISeverityStyleClassCapability;

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

	private static final Log LOG = LogFactory.getLog(MessagesComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.messages";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractMessagesComponent.BEHAVIOR_EVENT_NAMES;

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

	public boolean isShowIfMessage() {
		return isShowIfMessage(null);
	}

	public boolean isShowIfMessage(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.SHOW_IF_MESSAGE, false);
	}

	public void setShowIfMessage(boolean showIfMessage) {
		 getStateHelper().put(Properties.SHOW_IF_MESSAGE, showIfMessage);
	}

	/**
	 * Returns <code>true</code> if the attribute "showIfMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isShowIfMessageSetted() {
		return getStateHelper().get(Properties.SHOW_IF_MESSAGE)!=null;
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

	public int getMaxCount() {
		return getMaxCount(null);
	}

	public int getMaxCount(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.MAX_COUNT, 0);
	}

	public void setMaxCount(int maxCount) {
		 getStateHelper().put(Properties.MAX_COUNT, maxCount);
	}

	/**
	 * Returns <code>true</code> if the attribute "maxCount" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isMaxCountSetted() {
		return getStateHelper().get(Properties.MAX_COUNT)!=null;
	}

}
