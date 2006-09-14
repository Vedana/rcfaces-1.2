package org.rcfaces.core.component;


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
