package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IImageSizeCapability;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.AbstractOutputComponent;
import org.rcfaces.core.component.capability.IImageCapability;

public class ImageComponent extends AbstractOutputComponent implements 
	IImageCapability,
	IImageSizeCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.image";


	public ImageComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ImageComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final java.lang.String getImageURL() {
		return getImageURL(null);
	}

	public final java.lang.String getImageURL(javax.faces.context.FacesContext facesContext) {
		return org.rcfaces.core.internal.tools.ValuesTools.valueToString(this, facesContext);
	}

	public final void setImageURL(java.lang.String imageURL) {
		setValue(imageURL);
	}

	public final void setImageURL(ValueBinding imageURL) {
		setValue(imageURL);
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

	public void release() {
		super.release();
	}
}
