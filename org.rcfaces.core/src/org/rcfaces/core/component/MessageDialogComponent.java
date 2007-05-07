package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ImageAccessorTools;
import javax.faces.el.ValueBinding;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.internal.component.CameliaInputComponent;
import org.rcfaces.core.internal.capability.IImageAccessorsCapability;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.component.capability.IImageCapability;

public class MessageDialogComponent extends CameliaInputComponent implements 
	IImageCapability,
	IStyleClassCapability,
	ITextCapability,
	IVisibilityCapability,
	IImageAccessorsCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.messageDialog";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaInputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"callback","styleClass","text","imageURL","defaultValue","title","visible","priority"}));
	}

	public MessageDialogComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public MessageDialogComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final IContentAccessors getImageAccessors(FacesContext facesContext) {


			return ImageAccessorTools.createImageAccessors(facesContext, this, engine);
		
	}

	public final Boolean getVisibleState(FacesContext facesContext) {


			if (engine.isPropertySetted(Properties.VISIBLE)==false) {
				return null;
			}
			
			return Boolean.valueOf(isVisible(facesContext));
		
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

	/**
	 * See {@link #setImageURL(String) setImageURL(String)} for more details
	 */
	public void setImageURL(ValueBinding imageURL) {
		engine.setProperty(Properties.IMAGE_URL, imageURL);
	}

	public final IContentAccessors getImageAccessors() {


			return getImageAccessors(null);
		
	}

	public java.lang.String getStyleClass() {
		return getStyleClass(null);
	}

	/**
	 * See {@link #getStyleClass() getStyleClass()} for more details
	 */
	public java.lang.String getStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.STYLE_CLASS, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "styleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isStyleClassSetted() {
		return engine.isPropertySetted(Properties.STYLE_CLASS);
	}

	public void setStyleClass(java.lang.String styleClass) {
		engine.setProperty(Properties.STYLE_CLASS, styleClass);
	}

	/**
	 * See {@link #setStyleClass(String) setStyleClass(String)} for more details
	 */
	public void setStyleClass(ValueBinding styleClass) {
		engine.setProperty(Properties.STYLE_CLASS, styleClass);
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

	/**
	 * See {@link #setText(String) setText(String)} for more details
	 */
	public void setText(ValueBinding text) {
		engine.setProperty(Properties.TEXT, text);
	}

	public boolean isVisible() {
		return isVisible(null);
	}

	/**
	 * See {@link #isVisible() isVisible()} for more details
	 */
	public boolean isVisible(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.VISIBLE, true, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "visible" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isVisibleSetted() {
		return engine.isPropertySetted(Properties.VISIBLE);
	}

	public void setVisible(boolean visible) {
		engine.setProperty(Properties.VISIBLE, visible);
	}

	/**
	 * See {@link #setVisible(boolean) setVisible(boolean)} for more details
	 */
	public void setVisible(ValueBinding visible) {
		engine.setProperty(Properties.VISIBLE, visible);
	}

	public final Boolean getVisibleState() {


			return getVisibleState(null);
		
	}

	/**
	 * Returns a string value specifying the title for the document.
	 * @return title
	 */
	public final String getTitle() {
		return getTitle(null);
	}

	/**
	 * Returns a string value specifying the title for the document.
	 * @return title
	 */
	public final String getTitle(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TITLE, facesContext);
	}

	/**
	 * Sets a string value specifying the title for the document.
	 * @param title title
	 */
	public final void setTitle(String title) {
		engine.setProperty(Properties.TITLE, title);
	}

	/**
	 * Sets a string value specifying the title for the document.
	 * @param title title
	 */
	public final void setTitle(ValueBinding title) {
		engine.setProperty(Properties.TITLE, title);
	}

	/**
	 * Returns <code>true</code> if the attribute "title" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTitleSetted() {
		return engine.isPropertySetted(Properties.TITLE);
	}

	public final String getDefaultValue() {
		return getDefaultValue(null);
	}

	public final String getDefaultValue(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_VALUE, facesContext);
	}

	public final void setDefaultValue(String defaultValue) {
		engine.setProperty(Properties.DEFAULT_VALUE, defaultValue);
	}

	public final void setDefaultValue(ValueBinding defaultValue) {
		engine.setProperty(Properties.DEFAULT_VALUE, defaultValue);
	}

	/**
	 * Returns <code>true</code> if the attribute "defaultValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDefaultValueSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_VALUE);
	}

	public final String getCallback() {
		return getCallback(null);
	}

	public final String getCallback(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.CALLBACK, facesContext);
	}

	public final void setCallback(String callback) {
		engine.setProperty(Properties.CALLBACK, callback);
	}

	public final void setCallback(ValueBinding callback) {
		engine.setProperty(Properties.CALLBACK, callback);
	}

	/**
	 * Returns <code>true</code> if the attribute "callback" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCallbackSetted() {
		return engine.isPropertySetted(Properties.CALLBACK);
	}

	public final int getPriority() {
		return getPriority(null);
	}

	public final int getPriority(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.PRIORITY, 0, facesContext);
	}

	public final void setPriority(int priority) {
		engine.setProperty(Properties.PRIORITY, priority);
	}

	public final void setPriority(ValueBinding priority) {
		engine.setProperty(Properties.PRIORITY, priority);
	}

	/**
	 * Returns <code>true</code> if the attribute "priority" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isPrioritySetted() {
		return engine.isPropertySetted(Properties.PRIORITY);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
