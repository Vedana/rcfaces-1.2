package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.rcfaces.core.component.AbstractMessagesComponent;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.ICaptionCapability;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.capability.ISeverityStyleClassCapability;

/**
 * <p>The messages Component is a placeholder for error messages (several messages can be shown simultaneously).</p>
 * <p>The messages Component has the following capabilities :
 * <ul>
 * <li>ISeverityStyleClassCapability</li>
 * </ul>
 * </p>
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/MessagesComponent.html">messages</a> renderer is linked to the <a href="/jsdocs/index.html?f_messages.html" target="_blank">f_messages</a> javascript class. f_messages extends  f_component, fa_messageText</p>
 * 
 * <p> Table of component style classes: </p>
 * <table border="1" cellpadding="3" cellspacing="0" width="100%">
 * <tbody>
 * 
 * <tr style="text-align:left">
 * <th  width="33%">Style Name</th>
 * <th width="50%">Description</th>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td width="33%">f_messages</td>
 * <td width="50%">Defines styles for the wrapper  element</td>
 * </tr>
 * 
 * </tbody>
 * </table>
 */
public class MessagesComponent extends AbstractMessagesComponent implements 
	ISeverityStyleClassCapability,
	ICaptionCapability {

	private static final Log LOG = LogFactory.getLog(MessagesComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.messages";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractMessagesComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"fatalStyleClass","showIfMessage","warnStyleClass","errorStyleClass","infoStyleClass","styleClass","caption","maxCount","showActiveComponentMessage"}));
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

	public java.lang.String getCaption() {
		return getCaption(null);
	}

	/**
	 * See {@link #getCaption() getCaption()} for more details
	 */
	public java.lang.String getCaption(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.CAPTION, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "caption" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCaptionSetted() {
		return engine.isPropertySetted(Properties.CAPTION);
	}

	public void setCaption(java.lang.String caption) {
		engine.setProperty(Properties.CAPTION, caption);
	}

	public boolean isShowIfMessage() {
		return isShowIfMessage(null);
	}

	public boolean isShowIfMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.SHOW_IF_MESSAGE, false, facesContext);
	}

	public void setShowIfMessage(boolean showIfMessage) {
		engine.setProperty(Properties.SHOW_IF_MESSAGE, showIfMessage);
	}

	/**
	 * Returns <code>true</code> if the attribute "showIfMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isShowIfMessageSetted() {
		return engine.isPropertySetted(Properties.SHOW_IF_MESSAGE);
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

	public int getMaxCount() {
		return getMaxCount(null);
	}

	public int getMaxCount(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.MAX_COUNT, 0, facesContext);
	}

	public void setMaxCount(int maxCount) {
		engine.setProperty(Properties.MAX_COUNT, maxCount);
	}

	/**
	 * Returns <code>true</code> if the attribute "maxCount" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isMaxCountSetted() {
		return engine.isPropertySetted(Properties.MAX_COUNT);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
