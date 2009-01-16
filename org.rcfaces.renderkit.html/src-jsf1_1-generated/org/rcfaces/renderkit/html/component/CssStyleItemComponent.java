package org.rcfaces.renderkit.html.component;

import org.rcfaces.core.internal.component.Properties;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.FileItemComponent;
import org.apache.commons.logging.Log;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class CssStyleItemComponent extends FileItemComponent {

	private static final Log LOG = LogFactory.getLog(CssStyleItemComponent.class);

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
