package org.rcfaces.core.component;

import org.rcfaces.core.component.ImageButtonComponent;
import org.rcfaces.core.internal.component.Properties;

public class ImageSubmitButtonComponent extends ImageButtonComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.imageSubmitButton";


	public ImageSubmitButtonComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ImageSubmitButtonComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void release() {
		super.release();
	}
}
