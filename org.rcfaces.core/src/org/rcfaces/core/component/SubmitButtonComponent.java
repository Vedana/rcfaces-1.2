package org.rcfaces.core.component;

import org.rcfaces.core.component.ButtonComponent;
import org.rcfaces.core.internal.component.Properties;

public class SubmitButtonComponent extends ButtonComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.submitButton";


	public SubmitButtonComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public SubmitButtonComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void release() {
		super.release();
	}
}
