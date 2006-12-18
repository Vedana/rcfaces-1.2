package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.ButtonComponent;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class SubmitButtonComponent extends ButtonComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.submitButton";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(ButtonComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {}));
	}

	public SubmitButtonComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public SubmitButtonComponent(String componentId) {
		this();
		setId(componentId);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
