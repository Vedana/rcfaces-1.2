package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A menu item with a check.
 */
public class MenuCheckItemComponent extends MenuItemComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.menuCheckItem";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(MenuItemComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {}));
	}

	public MenuCheckItemComponent() {
		setRendererType(null);
	}

	public MenuCheckItemComponent(String componentId) {
		this();
		setId(componentId);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
