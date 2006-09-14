package org.rcfaces.core.component;


public class MenuBarComponent extends AbstractMenuComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.menuBar";


	public MenuBarComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public MenuBarComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void release() {
		super.release();
	}
}
