package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.ButtonComponent;

public class ResetButtonComponent extends ButtonComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.resetButton";


	public ResetButtonComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ResetButtonComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void release() {
		super.release();
	}
}
