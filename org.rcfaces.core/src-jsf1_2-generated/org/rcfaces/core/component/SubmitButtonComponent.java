package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.ButtonComponent;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;

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

	public static final String COMPONENT_TYPE="org.rcfaces.core.submitButton";


	public SubmitButtonComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public SubmitButtonComponent(String componentId) {
		this();
		setId(componentId);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
