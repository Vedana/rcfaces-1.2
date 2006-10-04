package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IImageSizeCapability;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.AbstractMessageComponent;
import org.rcfaces.core.component.capability.IImageCapability;

public class MessageComponent extends AbstractMessageComponent implements 
	IImageCapability,
	IImageSizeCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.message";


	public MessageComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public MessageComponent(String componentId) {
		this();
		setId(componentId);
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

	public final String getInfoStyleClass() {
		return getInfoStyleClass(null);
	}

	public final String getInfoStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.INFO_STYLE_CLASS, facesContext);
	}

	public final void setInfoStyleClass(String infoStyleClass) {
		engine.setProperty(Properties.INFO_STYLE_CLASS, infoStyleClass);
	}

	public final void setInfoStyleClass(ValueBinding infoStyleClass) {
		engine.setProperty(Properties.INFO_STYLE_CLASS, infoStyleClass);
	}

	public final boolean isInfoStyleClassSetted() {
		return engine.isPropertySetted(Properties.INFO_STYLE_CLASS);
	}

	public final String getInfoImageURL() {
		return getInfoImageURL(null);
	}

	public final String getInfoImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.INFO_IMAGE_URL, facesContext);
	}

	public final void setInfoImageURL(String infoImageURL) {
		engine.setProperty(Properties.INFO_IMAGE_URL, infoImageURL);
	}

	public final void setInfoImageURL(ValueBinding infoImageURL) {
		engine.setProperty(Properties.INFO_IMAGE_URL, infoImageURL);
	}

	public final boolean isInfoImageURLSetted() {
		return engine.isPropertySetted(Properties.INFO_IMAGE_URL);
	}

	public final String getErrorStyleClass() {
		return getErrorStyleClass(null);
	}

	public final String getErrorStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ERROR_STYLE_CLASS, facesContext);
	}

	public final void setErrorStyleClass(String errorStyleClass) {
		engine.setProperty(Properties.ERROR_STYLE_CLASS, errorStyleClass);
	}

	public final void setErrorStyleClass(ValueBinding errorStyleClass) {
		engine.setProperty(Properties.ERROR_STYLE_CLASS, errorStyleClass);
	}

	public final boolean isErrorStyleClassSetted() {
		return engine.isPropertySetted(Properties.ERROR_STYLE_CLASS);
	}

	public final String getErrorImageURL() {
		return getErrorImageURL(null);
	}

	public final String getErrorImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ERROR_IMAGE_URL, facesContext);
	}

	public final void setErrorImageURL(String errorImageURL) {
		engine.setProperty(Properties.ERROR_IMAGE_URL, errorImageURL);
	}

	public final void setErrorImageURL(ValueBinding errorImageURL) {
		engine.setProperty(Properties.ERROR_IMAGE_URL, errorImageURL);
	}

	public final boolean isErrorImageURLSetted() {
		return engine.isPropertySetted(Properties.ERROR_IMAGE_URL);
	}

	public final String getWarnStyleClass() {
		return getWarnStyleClass(null);
	}

	public final String getWarnStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.WARN_STYLE_CLASS, facesContext);
	}

	public final void setWarnStyleClass(String warnStyleClass) {
		engine.setProperty(Properties.WARN_STYLE_CLASS, warnStyleClass);
	}

	public final void setWarnStyleClass(ValueBinding warnStyleClass) {
		engine.setProperty(Properties.WARN_STYLE_CLASS, warnStyleClass);
	}

	public final boolean isWarnStyleClassSetted() {
		return engine.isPropertySetted(Properties.WARN_STYLE_CLASS);
	}

	public final String getWarnImageURL() {
		return getWarnImageURL(null);
	}

	public final String getWarnImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.WARN_IMAGE_URL, facesContext);
	}

	public final void setWarnImageURL(String warnImageURL) {
		engine.setProperty(Properties.WARN_IMAGE_URL, warnImageURL);
	}

	public final void setWarnImageURL(ValueBinding warnImageURL) {
		engine.setProperty(Properties.WARN_IMAGE_URL, warnImageURL);
	}

	public final boolean isWarnImageURLSetted() {
		return engine.isPropertySetted(Properties.WARN_IMAGE_URL);
	}

	public final String getFatalStyleClass() {
		return getFatalStyleClass(null);
	}

	public final String getFatalStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FATAL_STYLE_CLASS, facesContext);
	}

	public final void setFatalStyleClass(String fatalStyleClass) {
		engine.setProperty(Properties.FATAL_STYLE_CLASS, fatalStyleClass);
	}

	public final void setFatalStyleClass(ValueBinding fatalStyleClass) {
		engine.setProperty(Properties.FATAL_STYLE_CLASS, fatalStyleClass);
	}

	public final boolean isFatalStyleClassSetted() {
		return engine.isPropertySetted(Properties.FATAL_STYLE_CLASS);
	}

	public final String getFatalImageURL() {
		return getFatalImageURL(null);
	}

	public final String getFatalImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FATAL_IMAGE_URL, facesContext);
	}

	public final void setFatalImageURL(String fatalImageURL) {
		engine.setProperty(Properties.FATAL_IMAGE_URL, fatalImageURL);
	}

	public final void setFatalImageURL(ValueBinding fatalImageURL) {
		engine.setProperty(Properties.FATAL_IMAGE_URL, fatalImageURL);
	}

	public final boolean isFatalImageURLSetted() {
		return engine.isPropertySetted(Properties.FATAL_IMAGE_URL);
	}

	public final String getBundleVar() {
		return getBundleVar(null);
	}

	public final String getBundleVar(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BUNDLE_VAR, facesContext);
	}

	public final void setBundleVar(String bundleVar) {
		engine.setProperty(Properties.BUNDLE_VAR, bundleVar);
	}

	public final void setBundleVar(ValueBinding bundleVar) {
		engine.setProperty(Properties.BUNDLE_VAR, bundleVar);
	}

	public final boolean isBundleVarSetted() {
		return engine.isPropertySetted(Properties.BUNDLE_VAR);
	}

	public final String getNoMessageText() {
		return getNoMessageText(null);
	}

	public final String getNoMessageText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.NO_MESSAGE_TEXT, facesContext);
	}

	public final void setNoMessageText(String noMessageText) {
		engine.setProperty(Properties.NO_MESSAGE_TEXT, noMessageText);
	}

	public final void setNoMessageText(ValueBinding noMessageText) {
		engine.setProperty(Properties.NO_MESSAGE_TEXT, noMessageText);
	}

	public final boolean isNoMessageTextSetted() {
		return engine.isPropertySetted(Properties.NO_MESSAGE_TEXT);
	}

	public void release() {
		super.release();
	}
}
