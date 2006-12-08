package org.rcfaces.core.component;

import org.rcfaces.core.component.TextComponent;
import org.rcfaces.core.internal.component.Properties;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class StyledTextComponent extends TextComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.styledText";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(TextComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {}));
	}

	public StyledTextComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public StyledTextComponent(String componentId) {
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
