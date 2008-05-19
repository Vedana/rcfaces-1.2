package org.rcfaces.renderkit.html.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.FileItemComponent;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class CssStyleItemComponent extends FileItemComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.html.cssStyleItem";


	public CssStyleItemComponent() {
		setRendererType(null);
	}

	public CssStyleItemComponent(String componentId) {
		this();
		setId(componentId);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
