package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IBundleVarCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.ISeverityStyleClassCapability;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.FieldSetComponent;
import org.rcfaces.core.component.capability.ISeverityImagesCapability;
import java.util.Arrays;
import org.rcfaces.core.component.familly.IContentAccessors;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IForCapability;

public class MessageFieldSetComponent extends FieldSetComponent implements 
	IForCapability,
	ISeverityStyleClassCapability,
	ISeverityImagesCapability,
	IBundleVarCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.messageFieldSet";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(FieldSetComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"errorStyleClass","fatalStyleClass","imageURL","errorImageURL","warnStyleClass","warnImageURL","styleClass","infoStyleClass","infoImageURL","bundleVar","fatalImageURL","setFocusIfMessage","for"}));
	}

	public MessageFieldSetComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public MessageFieldSetComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final java.lang.String getFor() {
		return getFor(null);
	}

	public final java.lang.String getFor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOR, facesContext);
	}

	public final void setFor(java.lang.String forValue) {
		engine.setProperty(Properties.FOR, forValue);
	}

	public final void setFor(ValueBinding forValue) {
		engine.setProperty(Properties.FOR, forValue);
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

	public final java.lang.String getBundleVar() {
		return getBundleVar(null);
	}

	public final java.lang.String getBundleVar(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BUNDLE_VAR, facesContext);
	}

	public final void setBundleVar(java.lang.String bundleVar) {
		engine.setProperty(Properties.BUNDLE_VAR, bundleVar);
	}

	public final void setBundleVar(ValueBinding bundleVar) {
		engine.setProperty(Properties.BUNDLE_VAR, bundleVar);
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

	public final boolean isSetFocusIfMessageSetted() {
		return engine.isPropertySetted(Properties.SET_FOCUS_IF_MESSAGE);
	}

	public void release() {
		super.release();
	}
	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
