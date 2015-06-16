package org.rcfaces.renderkit.svg.component;

import org.rcfaces.renderkit.svg.component.Properties;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.rcfaces.renderkit.svg.component.NodeComponent;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;

public class GroupComponent extends NodeComponent {

	private static final Log LOG = LogFactory.getLog(GroupComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.svg.group";


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
