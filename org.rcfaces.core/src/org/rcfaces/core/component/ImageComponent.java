package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.component.AbstractOutputComponent;
import org.rcfaces.core.component.capability.IImageCapability;

public class ImageComponent extends AbstractOutputComponent implements 
	IImageCapability {

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

	public void release() {
		super.release();
	}
}
