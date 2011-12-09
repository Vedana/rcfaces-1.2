package org.rcfaces.core.component;

import javax.faces.component.NamingContainer;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.TabComponent;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import java.util.Collection;

public class TabNamingContainerComponent extends TabComponent implements 
	NamingContainer {

	private static final Log LOG = LogFactory.getLog(TabNamingContainerComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.tabNamingContainer";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=TabComponent.BEHAVIOR_EVENT_NAMES;

	public TabNamingContainerComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public TabNamingContainerComponent(String componentId) {
		this();
		setId(componentId);
	}

}
