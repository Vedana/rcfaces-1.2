package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.AbstractSeparatorComponent;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;

/**
 * A separator Menu item
 */
public class MenuSeparatorComponent extends AbstractSeparatorComponent {

	private static final Log LOG = LogFactory.getLog(MenuSeparatorComponent.class);

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
