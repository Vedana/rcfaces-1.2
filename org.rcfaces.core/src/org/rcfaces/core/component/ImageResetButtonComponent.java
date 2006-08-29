package org.rcfaces.core.component;

import org.rcfaces.core.component.ImageButtonComponent;
import org.rcfaces.core.internal.component.Properties;

public class ImageResetButtonComponent extends ImageButtonComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.imageResetButton";


	public ImageResetButtonComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ImageResetButtonComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void release() {
		super.release();
	}
}
