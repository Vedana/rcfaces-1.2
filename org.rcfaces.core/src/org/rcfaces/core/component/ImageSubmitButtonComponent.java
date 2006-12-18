package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.ImageButtonComponent;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class ImageSubmitButtonComponent extends ImageButtonComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.imageSubmitButton";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(ImageButtonComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {}));
	}

	public ImageSubmitButtonComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ImageSubmitButtonComponent(String componentId) {
		this();
		setId(componentId);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
