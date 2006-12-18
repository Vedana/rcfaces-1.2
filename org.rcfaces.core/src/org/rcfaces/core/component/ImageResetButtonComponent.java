package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.ImageButtonComponent;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class ImageResetButtonComponent extends ImageButtonComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.imageResetButton";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(ImageButtonComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {}));
	}

	public ImageResetButtonComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ImageResetButtonComponent(String componentId) {
		this();
		setId(componentId);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
