package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.AbstractMenuComponent;
import java.util.Collection;

/**
 * Describes a top-level menu bar.
 */
public class MenuBarComponent extends AbstractMenuComponent {

	private static final Log LOG = LogFactory.getLog(MenuBarComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.menuBar";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractMenuComponent.BEHAVIOR_EVENT_NAMES;

	public MenuBarComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public MenuBarComponent(String componentId) {
		this();
		setId(componentId);
	}

}
