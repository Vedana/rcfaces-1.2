package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.AbstractSeparatorComponent;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

/**
 * A separator Menu item
 */
public class MenuSeparatorComponent extends AbstractSeparatorComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.menuSeparator";


	public MenuSeparatorComponent() {
		setRendererType(null);
	}

	public MenuSeparatorComponent(String componentId) {
		this();
		setId(componentId);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
