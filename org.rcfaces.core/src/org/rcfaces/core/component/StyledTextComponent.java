package org.rcfaces.core.component;

import org.rcfaces.core.component.TextComponent;
import org.rcfaces.core.internal.component.Properties;

public class StyledTextComponent extends TextComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.styledText";


	public StyledTextComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public StyledTextComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void release() {
		super.release();
	}
}
