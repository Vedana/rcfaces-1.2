package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.ButtonComponent;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import java.util.Collection;

/**
 * <p>The resetButton Component is based on the standard HTML tag &lt;INPUT TYPE="reset"&gt;. It is a <a href="/comps/buttonComponent.html">button Component</a>.</p>
 * <p>The resetButton Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text &amp; font</li>
 * <li>Help</li>
 * <li>Visibility, Read-Only, Disabled</li>
 * <li>Events Handling</li>
 * </ul>
 * </p>
 */
public class ResetButtonComponent extends ButtonComponent {

	private static final Log LOG = LogFactory.getLog(ResetButtonComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.resetButton";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=ButtonComponent.BEHAVIOR_EVENT_NAMES;

	public ResetButtonComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ResetButtonComponent(String componentId) {
		this();
		setId(componentId);
	}

}
