package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.AbstractMenuComponent;

/**
 * Describes a top-level menu bar.
 */
public class MenuBarComponent extends AbstractMenuComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.menuBar";


	public MenuBarComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public MenuBarComponent(String componentId) {
		this();
		setId(componentId);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
