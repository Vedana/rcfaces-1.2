package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.MenuComponent;

public class MenuCriteriaComponent extends MenuComponent {

	private static final Log LOG = LogFactory.getLog(MenuCriteriaComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.menuCriteria";


	public MenuCriteriaComponent() {
		setRendererType(null);
	}

	public MenuCriteriaComponent(String componentId) {
		this();
		setId(componentId);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
