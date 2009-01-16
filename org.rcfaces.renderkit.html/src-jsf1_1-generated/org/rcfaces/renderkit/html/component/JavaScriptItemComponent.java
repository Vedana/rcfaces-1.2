package org.rcfaces.renderkit.html.component;

import org.rcfaces.core.internal.component.Properties;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.FileItemComponent;
import org.apache.commons.logging.Log;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class JavaScriptItemComponent extends FileItemComponent {

	private static final Log LOG = LogFactory.getLog(JavaScriptItemComponent.class);

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
