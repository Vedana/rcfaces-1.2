package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.AbstractSeparatorComponent;
import org.rcfaces.core.internal.component.Properties;

public class ToolItemSeparatorComponent extends AbstractSeparatorComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.toolItemSeparator";


	public ToolItemSeparatorComponent() {
		setRendererType(null);
	}

	public ToolItemSeparatorComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void release() {
		super.release();
	}
}
