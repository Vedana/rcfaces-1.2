package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import java.util.Arrays;
import javax.faces.component.NamingContainer;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.TabComponent;

public class TabNamingContainerComponent extends TabComponent implements 
	NamingContainer {

	private static final Log LOG = LogFactory.getLog(TabNamingContainerComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.tabNamingContainer";


	public TabNamingContainerComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public TabNamingContainerComponent(String componentId) {
		this();
		setId(componentId);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
