package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IVisibilityCapability;
import java.lang.String;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.AbstractItemComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.tools.ImageAccessorTools;
import org.rcfaces.core.internal.converter.HiddenModeConverter;
import java.util.Arrays;
import org.rcfaces.core.component.capability.IToolTipCapability;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IStatesImageCapability;
import org.rcfaces.core.component.familly.IContentAccessors;

public class UIImageItemComponent extends AbstractItemComponent implements 
	IVisibilityCapability,
	IToolTipCapability,
	IStatesImageCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.UIImageItem";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractItemComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"hoverImageURL","imageURL","disabledImageURL","selectedImageURL","toolTipText","visible","hiddenMode","rendered"}));
	}

	public UIImageItemComponent() {
		setRendererType(null);
	}

	public UIImageItemComponent(String componentId) {
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

	public final void setToolTip(String text) {


			setItemDescription(text);
			
	}

	public final void setToolTip(ValueBinding text) {


			setValueBinding("itemDescription", text);
			
	}

	public final String getToolTip() {


			return getItemDescription();
			
	}

	public final void setHiddenMode(String hiddenMode) {


			setHiddenMode(((Integer)HiddenModeConverter.SINGLETON.getAsObject(null, null, hiddenMode)).intValue());
		
	}

	public final int getHiddenMode() {
		return getHiddenMode(null);
	}

	public final int getHiddenMode(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.HIDDEN_MODE,0, facesContext);
	}

	public final void setHiddenMode(int hiddenMode) {
		engine.setProperty(Properties.HIDDEN_MODE, hiddenMode);
	}

	public final void setHiddenMode(ValueBinding hiddenMode) {
		engine.setProperty(Properties.HIDDEN_MODE, hiddenMode);
	}

	public final boolean isVisible() {
		return isVisible(null);
	}

	public final boolean isVisible(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.VISIBLE, false, facesContext);
	}

	public final void setVisible(boolean visible) {
		engine.setProperty(Properties.VISIBLE, visible);
	}

	public final void setVisible(ValueBinding visible) {
		engine.setProperty(Properties.VISIBLE, visible);
	}

	public final Boolean getVisibleState() {


				return getVisibleState(null);
			
	}

	public final java.lang.String getToolTipText() {
		return getToolTipText(null);
	}

	public final java.lang.String getToolTipText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TOOL_TIP_TEXT, facesContext);
	}

	public final void setToolTipText(java.lang.String toolTipText) {
		engine.setProperty(Properties.TOOL_TIP_TEXT, toolTipText);
	}

	public final void setToolTipText(ValueBinding toolTipText) {
		engine.setProperty(Properties.TOOL_TIP_TEXT, toolTipText);
	}

	public final java.lang.String getDisabledImageURL() {
		return getDisabledImageURL(null);
	}

	public final java.lang.String getDisabledImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DISABLED_IMAGE_URL, facesContext);
	}

	public final void setDisabledImageURL(java.lang.String disabledImageURL) {
		engine.setProperty(Properties.DISABLED_IMAGE_URL, disabledImageURL);
	}

	public final void setDisabledImageURL(ValueBinding disabledImageURL) {
		engine.setProperty(Properties.DISABLED_IMAGE_URL, disabledImageURL);
	}

	public final java.lang.String getHoverImageURL() {
		return getHoverImageURL(null);
	}

	public final java.lang.String getHoverImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.HOVER_IMAGE_URL, facesContext);
	}

	public final void setHoverImageURL(java.lang.String hoverImageURL) {
		engine.setProperty(Properties.HOVER_IMAGE_URL, hoverImageURL);
	}

	public final void setHoverImageURL(ValueBinding hoverImageURL) {
		engine.setProperty(Properties.HOVER_IMAGE_URL, hoverImageURL);
	}

	public final java.lang.String getSelectedImageURL() {
		return getSelectedImageURL(null);
	}

	public final java.lang.String getSelectedImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SELECTED_IMAGE_URL, facesContext);
	}

	public final void setSelectedImageURL(java.lang.String selectedImageURL) {
		engine.setProperty(Properties.SELECTED_IMAGE_URL, selectedImageURL);
	}

	public final void setSelectedImageURL(ValueBinding selectedImageURL) {
		engine.setProperty(Properties.SELECTED_IMAGE_URL, selectedImageURL);
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

	public void release() {
		super.release();
	}
	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
