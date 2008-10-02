package org.rcfaces.renderkit.svg.component;

import org.rcfaces.renderkit.svg.component.NodeComponent;
import java.util.Arrays;
import org.rcfaces.renderkit.svg.component.Properties;
import java.util.Set;
import java.util.HashSet;

public class GroupComponent extends NodeComponent {

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
