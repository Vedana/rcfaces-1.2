package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.AbstractSeparatorComponent;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class MenuSeparatorComponent extends AbstractSeparatorComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.menuSeparator";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractSeparatorComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {}));
	}

	public MenuSeparatorComponent() {
		setRendererType(null);
	}

	public MenuSeparatorComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void release() {
		super.release();
	}
	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
