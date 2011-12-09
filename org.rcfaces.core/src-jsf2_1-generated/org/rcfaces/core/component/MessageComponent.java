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
import java.util.Collection;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.component.capability.ISeverityStyleClassCapability;

/**
 * <p>The message Component is a placeholder for error messages (only one is shown).</p>
 * <p>The message Component has the following capabilities :
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
public class MessageComponent extends AbstractMessageComponent implements 
	IImageSizeCapability,
	ITextCapability,
	ISeverityStyleClassCapability,
	ISeverityImagesCapability,
	IImageAccessorsCapability {

	private static final Log LOG = LogFactory.getLog(MessageComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.message";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractMessageComponent.BEHAVIOR_EVENT_NAMES;

	public MessageComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public MessageComponent(String componentId) {
		this();
		setId(componentId);
	}

	public IContentAccessors getImageAccessors(FacesContext facesContext) {


			return ImageAccessorTools.createImageAccessors(facesContext, this, getComponentEngine());
		
	}

	public int getImageHeight() {
		return getImageHeight(null);
	}

	/**
	 * See {@link #getImageHeight() getImageHeight()} for more details
	 */
	public int getImageHeight(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.IMAGE_HEIGHT, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "imageHeight" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImageHeightSetted() {
		return getStateHelper().get(Properties.IMAGE_HEIGHT)!=null;
	}

	public void setImageHeight(int imageHeight) {
		getStateHelper().put(Properties.IMAGE_HEIGHT, imageHeight);
	}

	public int getImageWidth() {
		return getImageWidth(null);
	}

	/**
	 * See {@link #getImageWidth() getImageWidth()} for more details
	 */
	public int getImageWidth(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.IMAGE_WIDTH, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "imageWidth" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImageWidthSetted() {
		return getStateHelper().get(Properties.IMAGE_WIDTH)!=null;
	}

	public void setImageWidth(int imageWidth) {
		getStateHelper().put(Properties.IMAGE_WIDTH, imageWidth);
	}

	public java.lang.String getText() {
		return getText(null);
	}

	/**
	 * See {@link #getText() getText()} for more details
	 */
	public java.lang.String getText(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.TEXT);
	}

	/**
	 * Returns <code>true</code> if the attribute "text" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextSetted() {
		return getStateHelper().get(Properties.TEXT)!=null;
	}

	public void setText(java.lang.String text) {
		getStateHelper().put(Properties.TEXT, text);
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

	public java.lang.String getImageURL() {
		return getImageURL(null);
	}

	/**
	 * See {@link #getImageURL() getImageURL()} for more details
	 */
	public java.lang.String getImageURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.IMAGE_URL);
	}

	/**
	 * Returns <code>true</code> if the attribute "imageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImageURLSetted() {
		return getStateHelper().get(Properties.IMAGE_URL)!=null;
	}

	public void setImageURL(java.lang.String imageURL) {
		getStateHelper().put(Properties.IMAGE_URL, imageURL);
	}

	public IContentAccessors getImageAccessors() {


			return getImageAccessors(null);
		
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

}
