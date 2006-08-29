package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.AbstractMenuComponent;
import org.rcfaces.core.internal.component.Properties;

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
