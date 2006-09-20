package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.ImageButtonComponent;

public class HelpButtonComponent extends ImageButtonComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.helpButton";


	public HelpButtonComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public HelpButtonComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void release() {
		super.release();
	}
}
