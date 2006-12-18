package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.AbstractMenuComponent;

public class MenuBarComponent extends AbstractMenuComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.menuBar";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractMenuComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {}));
	}

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
