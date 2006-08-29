package org.rcfaces.core.component;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IImageCapability;
import org.rcfaces.core.internal.component.CameliaItemComponent;
import org.rcfaces.core.internal.component.Properties;

public class SelectImageItemComponent extends CameliaItemComponent implements 
	IImageCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.selectImageItem";


	public SelectImageItemComponent() {
		setRendererType(null);
	}

	public SelectImageItemComponent(String componentId) {
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

	public void release() {
		super.release();
	}
}
