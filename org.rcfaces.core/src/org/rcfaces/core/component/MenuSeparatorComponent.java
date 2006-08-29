package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.AbstractSeparatorComponent;
import org.rcfaces.core.internal.component.Properties;

public class MenuSeparatorComponent extends AbstractSeparatorComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.menuSeparator";


	public MenuSeparatorComponent() {
		setRendererType(null);
	}

	public MenuSeparatorComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void release() {
		super.release();
	}
}
