package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.ISeverityStyleClassCapability;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.tools.ImageAccessorTools;
import org.rcfaces.core.component.AbstractMessageComponent;
import java.util.Arrays;
import org.rcfaces.core.component.capability.ISeverityImagesCapability;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IImageSizeCapability;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.component.familly.IContentAccessors;

public class MessageComponent extends AbstractMessageComponent implements 
	IImageSizeCapability,
	ITextCapability,
	ISeverityStyleClassCapability,
	ISeverityImagesCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.message";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractMessageComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"errorStyleClass","imageHeight","fatalStyleClass","imageURL","errorImageURL","warnStyleClass","showIfMessage","warnImageURL","styleClass","text","imageWidth","infoStyleClass","infoImageURL","setFocusIfMessage","fatalImageURL"}));
	}

	public MessageComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public MessageComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final IContentAccessors getImageAccessors(FacesContext facesContext) {


			return ImageAccessorTools.createImageAccessors(facesContext, this, engine);
		
	}

	public final int getImageHeight() {
		return getImageHeight(null);
	}

	public final int getImageHeight(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.IMAGE_HEIGHT,0, facesContext);
	}

	public final void setImageHeight(int imageHeight) {
		engine.setProperty(Properties.IMAGE_HEIGHT, imageHeight);
	}

	public final void setImageHeight(ValueBinding imageHeight) {
		engine.setProperty(Properties.IMAGE_HEIGHT, imageHeight);
	}

	public final int getImageWidth() {
		return getImageWidth(null);
	}

	public final int getImageWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.IMAGE_WIDTH,0, facesContext);
	}

	public final void setImageWidth(int imageWidth) {
		engine.setProperty(Properties.IMAGE_WIDTH, imageWidth);
	}

	public final void setImageWidth(ValueBinding imageWidth) {
		engine.setProperty(Properties.IMAGE_WIDTH, imageWidth);
	}

	public final java.lang.String getText() {
		return getText(null);
	}

	public final java.lang.String getText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TEXT, facesContext);
	}

	public final void setText(java.lang.String text) {
		engine.setProperty(Properties.TEXT, text);
	}

	public final void setText(ValueBinding text) {
		engine.setProperty(Properties.TEXT, text);
	}

	public final java.lang.String getErrorStyleClass() {
		return getErrorStyleClass(null);
	}

	public final java.lang.String getErrorStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ERROR_STYLE_CLASS, facesContext);
	}

	public final void setErrorStyleClass(java.lang.String errorStyleClass) {
		engine.setProperty(Properties.ERROR_STYLE_CLASS, errorStyleClass);
	}

	public final void setErrorStyleClass(ValueBinding errorStyleClass) {
		engine.setProperty(Properties.ERROR_STYLE_CLASS, errorStyleClass);
	}

	public final java.lang.String getFatalStyleClass() {
		return getFatalStyleClass(null);
	}

	public final java.lang.String getFatalStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FATAL_STYLE_CLASS, facesContext);
	}

	public final void setFatalStyleClass(java.lang.String fatalStyleClass) {
		engine.setProperty(Properties.FATAL_STYLE_CLASS, fatalStyleClass);
	}

	public final void setFatalStyleClass(ValueBinding fatalStyleClass) {
		engine.setProperty(Properties.FATAL_STYLE_CLASS, fatalStyleClass);
	}

	public final java.lang.String getInfoStyleClass() {
		return getInfoStyleClass(null);
	}

	public final java.lang.String getInfoStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.INFO_STYLE_CLASS, facesContext);
	}

	public final void setInfoStyleClass(java.lang.String infoStyleClass) {
		engine.setProperty(Properties.INFO_STYLE_CLASS, infoStyleClass);
	}

	public final void setInfoStyleClass(ValueBinding infoStyleClass) {
		engine.setProperty(Properties.INFO_STYLE_CLASS, infoStyleClass);
	}

	public final java.lang.String getWarnStyleClass() {
		return getWarnStyleClass(null);
	}

	public final java.lang.String getWarnStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.WARN_STYLE_CLASS, facesContext);
	}

	public final void setWarnStyleClass(java.lang.String warnStyleClass) {
		engine.setProperty(Properties.WARN_STYLE_CLASS, warnStyleClass);
	}

	public final void setWarnStyleClass(ValueBinding warnStyleClass) {
		engine.setProperty(Properties.WARN_STYLE_CLASS, warnStyleClass);
	}

	public final java.lang.String getErrorImageURL() {
		return getErrorImageURL(null);
	}

	public final java.lang.String getErrorImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ERROR_IMAGE_URL, facesContext);
	}

	public final void setErrorImageURL(java.lang.String errorImageURL) {
		engine.setProperty(Properties.ERROR_IMAGE_URL, errorImageURL);
	}

	public final void setErrorImageURL(ValueBinding errorImageURL) {
		engine.setProperty(Properties.ERROR_IMAGE_URL, errorImageURL);
	}

	public final java.lang.String getFatalImageURL() {
		return getFatalImageURL(null);
	}

	public final java.lang.String getFatalImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FATAL_IMAGE_URL, facesContext);
	}

	public final void setFatalImageURL(java.lang.String fatalImageURL) {
		engine.setProperty(Properties.FATAL_IMAGE_URL, fatalImageURL);
	}

	public final void setFatalImageURL(ValueBinding fatalImageURL) {
		engine.setProperty(Properties.FATAL_IMAGE_URL, fatalImageURL);
	}

	public final java.lang.String getInfoImageURL() {
		return getInfoImageURL(null);
	}

	public final java.lang.String getInfoImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.INFO_IMAGE_URL, facesContext);
	}

	public final void setInfoImageURL(java.lang.String infoImageURL) {
		engine.setProperty(Properties.INFO_IMAGE_URL, infoImageURL);
	}

	public final void setInfoImageURL(ValueBinding infoImageURL) {
		engine.setProperty(Properties.INFO_IMAGE_URL, infoImageURL);
	}

	public final java.lang.String getWarnImageURL() {
		return getWarnImageURL(null);
	}

	public final java.lang.String getWarnImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.WARN_IMAGE_URL, facesContext);
	}

	public final void setWarnImageURL(java.lang.String warnImageURL) {
		engine.setProperty(Properties.WARN_IMAGE_URL, warnImageURL);
	}

	public final void setWarnImageURL(ValueBinding warnImageURL) {
		engine.setProperty(Properties.WARN_IMAGE_URL, warnImageURL);
	}

	public final java.lang.String getImageURL() {
		return getImageURL(null);
	}

	public final java.lang.String getImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.IMAGE_URL, facesContext);
	}

	public final void setImageURL(java.lang.String imageURL) {
		engine.setProperty(Properties.IMAGE_URL, imageURL);
	}

	public final void setImageURL(ValueBinding imageURL) {
		engine.setProperty(Properties.IMAGE_URL, imageURL);
	}

	public final IContentAccessors getImageAccessors() {


			return getImageAccessors(null);
		
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

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
