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
 * <p>The submitButton Component is based on the standard HTML tag &lt;INPUT TYPE="submit"&gt;. It is a <a href="/comps/buttonComponent.html">button Component</a>.</p>
 * <p>The submitButton Component has the following capabilities :
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
public class SubmitButtonComponent extends ButtonComponent {

	private static final Log LOG = LogFactory.getLog(SubmitButtonComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.submitButton";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=ButtonComponent.BEHAVIOR_EVENT_NAMES;

	public SubmitButtonComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public SubmitButtonComponent(String componentId) {
		this();
		setId(componentId);
	}

}
