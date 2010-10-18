package org.rcfaces.core.component;

import org.rcfaces.core.component.TextEntryComponent;
import org.rcfaces.core.internal.component.Properties;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;

/**
 * <p>The passwordEntry Component is based on the standard HTML tag &lt;INPUT TYPE="password"&gt; and is a <a href="/comps/textEntryComponent.html">textEntry Component</a>.</p>
 * <p>The passwordEntry Component has the following capabilities :
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
public class PasswordEntryComponent extends TextEntryComponent {

	private static final Log LOG = LogFactory.getLog(PasswordEntryComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.passwordEntry";


	public PasswordEntryComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public PasswordEntryComponent(String componentId) {
		this();
		setId(componentId);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
