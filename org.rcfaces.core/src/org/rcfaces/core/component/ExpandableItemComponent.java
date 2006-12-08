package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IExpandImageCapability;
import javax.faces.el.ValueBinding;
import java.util.Arrays;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.component.capability.IForegroundBackgroundColorCapability;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.UIImageItemComponent;

public abstract class ExpandableItemComponent extends UIImageItemComponent implements 
	IForegroundBackgroundColorCapability,
	ITextCapability,
	IExpandImageCapability {

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(UIImageItemComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"hoverImageURL","imageURL","text","expandedImageURL","disabledImageURL","selectedImageURL","foregroundColor","backgroundColor"}));
	}


	public final void setText(String text) {


			setItemLabel(text);
			
	}

	public final void setText(ValueBinding text) {


			setValueBinding("itemLabel", text);
			
	}

	public final String getText() {


			return getItemLabel();
			
	}

	public final java.lang.String getBackgroundColor() {
		return getBackgroundColor(null);
	}

	public final java.lang.String getBackgroundColor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BACKGROUND_COLOR, facesContext);
	}

	public final void setBackgroundColor(java.lang.String backgroundColor) {
		engine.setProperty(Properties.BACKGROUND_COLOR, backgroundColor);
	}

	public final void setBackgroundColor(ValueBinding backgroundColor) {
		engine.setProperty(Properties.BACKGROUND_COLOR, backgroundColor);
	}

	public final java.lang.String getForegroundColor() {
		return getForegroundColor(null);
	}

	public final java.lang.String getForegroundColor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOREGROUND_COLOR, facesContext);
	}

	public final void setForegroundColor(java.lang.String foregroundColor) {
		engine.setProperty(Properties.FOREGROUND_COLOR, foregroundColor);
	}

	public final void setForegroundColor(ValueBinding foregroundColor) {
		engine.setProperty(Properties.FOREGROUND_COLOR, foregroundColor);
	}

	public final java.lang.String getExpandedImageURL() {
		return getExpandedImageURL(null);
	}

	public final java.lang.String getExpandedImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.EXPANDED_IMAGE_URL, facesContext);
	}

	public final void setExpandedImageURL(java.lang.String expandedImageURL) {
		engine.setProperty(Properties.EXPANDED_IMAGE_URL, expandedImageURL);
	}

	public final void setExpandedImageURL(ValueBinding expandedImageURL) {
		engine.setProperty(Properties.EXPANDED_IMAGE_URL, expandedImageURL);
	}

	public void release() {
		super.release();
	}
	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
