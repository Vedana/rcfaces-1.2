package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.NodeComponent;

public class GroupComponent extends NodeComponent {

	private static final Log LOG = LogFactory.getLog(GroupComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.group";


	public GroupComponent() {
		setRendererType(null);
	}

	public GroupComponent(String componentId) {
		this();
		setId(componentId);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
