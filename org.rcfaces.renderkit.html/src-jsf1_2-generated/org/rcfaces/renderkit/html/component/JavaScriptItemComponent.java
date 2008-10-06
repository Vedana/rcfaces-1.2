package org.rcfaces.renderkit.html.component;

import org.rcfaces.renderkit.html.component.Properties;
import org.rcfaces.core.component.FileItemComponent;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class JavaScriptItemComponent extends FileItemComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.html.javaScriptItem";


	public JavaScriptItemComponent() {
		setRendererType(null);
	}

	public JavaScriptItemComponent(String componentId) {
		this();
		setId(componentId);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
