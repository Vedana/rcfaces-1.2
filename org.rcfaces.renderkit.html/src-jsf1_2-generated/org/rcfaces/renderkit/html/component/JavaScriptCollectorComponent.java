package org.rcfaces.renderkit.html.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;

public class JavaScriptCollectorComponent extends CameliaBaseComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.html.javaScriptCollector";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {}));
	}

	public JavaScriptCollectorComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public JavaScriptCollectorComponent(String componentId) {
		this();
		setId(componentId);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
