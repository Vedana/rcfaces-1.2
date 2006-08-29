package org.rcfaces.core.component;

import org.rcfaces.core.component.TextEntryComponent;
import org.rcfaces.core.internal.component.Properties;

public class PasswordEntryComponent extends TextEntryComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.passwordEntry";


	public PasswordEntryComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public PasswordEntryComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void release() {
		super.release();
	}
}
