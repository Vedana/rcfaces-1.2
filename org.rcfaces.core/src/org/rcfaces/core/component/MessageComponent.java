package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.AbstractMessageComponent;

public class MessageComponent extends AbstractMessageComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.message";


	public MessageComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public MessageComponent(String componentId) {
		this();
		setId(componentId);
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

	public void release() {
		super.release();
	}
}
