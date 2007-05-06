package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.AbstractInputComponent;
import org.rcfaces.core.component.capability.IImageCapability;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ImageAccessorTools;

public class MessageDialogComponent extends AbstractInputComponent implements IImageCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.messageDialog";

	public static final String PROP_DEFAULT_VALUE="defaultValue";
	public static final String PROP_JS_CALLBACK="jsCallback";
	public static final String PROP_PRIORITY="priority";
	
	public static final int MAX_PRIORITY = 1000;
	
	public static final String JS_SUBMITVALUE_CALLBACK = "f_messageDialog.SubmitValue";
	
	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractInputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"title", "text", "defaultValue", "jsCallsback", "priority"}));
	}
	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}

	public MessageDialogComponent() {
		super();
		setRendererType(COMPONENT_TYPE);
	}
	
	public void setTitle(String title) {
		engine.setProperty(Properties.TITLE, title);
	}
	public void setTitle(ValueBinding title) {
		engine.setProperty(Properties.TITLE, title);
	}
	public String getTitle() {
		return getTitle(null);
	}
	public String getTitle(FacesContext facesContext) {
		return engine.getStringProperty(Properties.TITLE, facesContext);
	}

	public void setText(String text) {
		engine.setProperty(Properties.TEXT, text);
	}
	public void setText(ValueBinding text) {
		engine.setProperty(Properties.TEXT, text);
	}
	public String getText() {
		return getText(null);
	}
	public String getText(FacesContext facesContext) {
		return engine.getStringProperty(Properties.TEXT, facesContext);
	}

	public void setDefaultValue(String defaultValue) {
		engine.setProperty(PROP_DEFAULT_VALUE, defaultValue);
	}
	public void setDefaultValue(ValueBinding defaultValue) {
		engine.setProperty(PROP_DEFAULT_VALUE, defaultValue);
	}
	public String getDefaultValue() {
		return getDefaultValue(null);
	}
	public String getDefaultValue(FacesContext facesContext) {
		return engine.getStringProperty(PROP_DEFAULT_VALUE, facesContext);
	}

	public void setJsCallback(String jsCallback) {
		engine.setProperty(PROP_JS_CALLBACK, jsCallback);
	}
	public void setJsCallback(ValueBinding jsCallback) {
		engine.setProperty(PROP_JS_CALLBACK, jsCallback);
	}
	public String getJsCallback() {
		return getJsCallback(null);
	}
	public String getJsCallback(FacesContext facesContext) {
		return engine.getStringProperty(PROP_JS_CALLBACK, facesContext);
	}
	
	public void setStyleClass(String styleClass) {
		engine.setProperty(Properties.STYLE_CLASS, styleClass);
	}
	public void setStyleClass(ValueBinding styleClass) {
		engine.setProperty(Properties.STYLE_CLASS, styleClass);
	}
	public String getStyleClass() {
		return getStyleClass(null);
	}
	public String getStyleClass(FacesContext facesContext) {
		return engine.getStringProperty(Properties.STYLE_CLASS, facesContext);
	}

	/**
	 * 
	 * @param facesContext
	 * @return a css Base name that will be used by invoking in javascript f_setCssClassBase
	 */
	public String getCssClassBase(FacesContext facesContext) {
		return null;
	}

	public IContentAccessors getImageAccessors() {
		return getImageAccessors(null);
	}

	public IContentAccessors getImageAccessors(FacesContext facesContext) {
		return ImageAccessorTools.createImageAccessors(facesContext, this, engine);
	}

	public String getImageURL() {
		return getImageURL(null);
	}

	public String getImageURL(FacesContext facesContext) {
		return engine.getStringProperty(Properties.IMAGE_URL, facesContext);
	}

	public void setImageURL(String url) {
		engine.setProperty(Properties.IMAGE_URL, url);
	}
	public void setImageURL(ValueBinding url) {
		engine.setProperty(Properties.IMAGE_URL, url);
	}

	public int getPriority() {
		return getPriority(null);
	}
	public int getPriority(FacesContext facesContext) {
		return engine.getIntProperty(PROP_PRIORITY, 0, facesContext);
	}
	public void setPriority(int priority) {
		engine.setProperty(PROP_PRIORITY, priority);
	}
	public void setPriority(ValueBinding priority) {
		engine.setProperty(PROP_PRIORITY, priority);
	}
}
