package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.ISeverityStyleClassCapability;
import javax.faces.el.ValueBinding;
import java.util.Arrays;
import org.rcfaces.core.component.AbstractMessagesComponent;
import java.util.Set;
import java.util.HashSet;

public class MessagesComponent extends AbstractMessagesComponent implements 
	ISeverityStyleClassCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.messages";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractMessagesComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"errorStyleClass","styleClass","fatalStyleClass","infoStyleClass","warnStyleClass"}));
	}

	public MessagesComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public MessagesComponent(String componentId) {
		this();
		setId(componentId);
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

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
