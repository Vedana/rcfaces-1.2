package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.TextEntryComponent;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class PasswordEntryComponent extends TextEntryComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.passwordEntry";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(TextEntryComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {}));
	}

	public PasswordEntryComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public PasswordEntryComponent(String componentId) {
		this();
		setId(componentId);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
