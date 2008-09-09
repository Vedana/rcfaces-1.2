package org.rcfaces.renderkit.html.component;

import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.FileItemComponent;
import org.rcfaces.renderkit.html.component.Properties;

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
