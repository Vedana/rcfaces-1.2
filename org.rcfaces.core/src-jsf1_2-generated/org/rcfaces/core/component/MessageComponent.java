package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.component.AbstractMessageComponent;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IImageSizeCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.capability.ISeverityImagesCapability;
import org.rcfaces.core.internal.tools.ImageAccessorTools;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.internal.capability.IImageAccessorsCapability;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.component.capability.ISeverityStyleClassCapability;

/**
 * <p>The message Component is a placeholder for error messages (only one is shown).</p>
 * <p>The message Component has the following capabilities :
 * <ul>
 * <li>IImageSizeCapability</li>
 * <li>ITextCapability</li>
 * <li>ISeverityStyleClassCapability</li>
 * <li>ISeverityImagesCapability</li>
 * <li>IImageAccessorsCapability</li>
 * </ul>
 * </p>
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/MessageComponent.html">message</a> renderer is linked to the <a href="/jsdoc/index.html?f_message.html" target="_blank">f_message</a> javascript class. f_message extends f_component, fa_message1</p>
 * 
 * 
 * <p> Table of component style classes: </p>
 * <table border="1" cellpadding="3" cellspacing="0" width="100%">
 * <tbody>
 * 
 * <tr style="text-align:left">
 * <td bgcolor="#eeeeee"  width="33%">Style Name</td>
 * <td bgcolor="#eeeeee"  width="50%">Description</td>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td bgcolor="#ffffff" width="33%">f_message</td>
 * <td width="50%">Defines styles for the wrapper DIV element</td>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td bgcolor="#ffffff" width="33%">f_message_summary</td>
 * <td width="50%">Defines styles for the summary error message</td>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td bgcolor="#ffffff" width="33%">f_message_detail</td>
 * <td width="50%">Defines styles for the detail error message</td>
 * </tr>
 * 
 * 
 * </tbody>
 * </table>
 */
public class MessageComponent extends AbstractMessageComponent implements 
	IImageSizeCapability,
	ITextCapability,
	ISeverityStyleClassCapability,
	ISeverityImagesCapability,
	IImageAccessorsCapability {

	private static final Log LOG = LogFactory.getLog(MessageComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.message";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractMessageComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"imageHeight","text","fatalStyleClass","showIfMessage","styleClass","fatalImageURL","showActiveComponentMessage","errorStyleClass","warnStyleClass","infoStyleClass","infoImageURL","warnImageURL","errorImageURL","imageWidth","imageURL"}));
	}

	public MessageComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public MessageComponent(String componentId) {
		this();
		setId(componentId);
	}

	public IContentAccessors getImageAccessors(FacesContext facesContext) {


			return ImageAccessorTools.createImageAccessors(facesContext, this, engine);
		
	}

	public int getImageHeight() {
		return getImageHeight(null);
	}

	/**
	 * See {@link #getImageHeight() getImageHeight()} for more details
	 */
	public int getImageHeight(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.IMAGE_HEIGHT,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "imageHeight" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImageHeightSetted() {
		return engine.isPropertySetted(Properties.IMAGE_HEIGHT);
	}

	public void setImageHeight(int imageHeight) {
		engine.setProperty(Properties.IMAGE_HEIGHT, imageHeight);
	}

	public int getImageWidth() {
		return getImageWidth(null);
	}

	/**
	 * See {@link #getImageWidth() getImageWidth()} for more details
	 */
	public int getImageWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.IMAGE_WIDTH,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "imageWidth" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImageWidthSetted() {
		return engine.isPropertySetted(Properties.IMAGE_WIDTH);
	}

	public void setImageWidth(int imageWidth) {
		engine.setProperty(Properties.IMAGE_WIDTH, imageWidth);
	}

	public java.lang.String getText() {
		return getText(null);
	}

	/**
	 * See {@link #getText() getText()} for more details
	 */
	public java.lang.String getText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TEXT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "text" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextSetted() {
		return engine.isPropertySetted(Properties.TEXT);
	}

	public void setText(java.lang.String text) {
		engine.setProperty(Properties.TEXT, text);
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

	public java.lang.String getImageURL() {
		return getImageURL(null);
	}

	/**
	 * See {@link #getImageURL() getImageURL()} for more details
	 */
	public java.lang.String getImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.IMAGE_URL, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "imageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImageURLSetted() {
		return engine.isPropertySetted(Properties.IMAGE_URL);
	}

	public void setImageURL(java.lang.String imageURL) {
		engine.setProperty(Properties.IMAGE_URL, imageURL);
	}

	public IContentAccessors getImageAccessors() {


			return getImageAccessors(null);
		
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

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
