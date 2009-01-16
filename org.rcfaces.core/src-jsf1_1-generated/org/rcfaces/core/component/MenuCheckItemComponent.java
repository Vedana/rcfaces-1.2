package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.MenuItemComponent;

/**
 * A menu item with a check.
 */
public class MenuCheckItemComponent extends MenuItemComponent {

	private static final Log LOG = LogFactory.getLog(MenuCheckItemComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.menuCheckItem";


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
