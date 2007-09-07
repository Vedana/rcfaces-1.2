package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.ButtonComponent;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;

/**
 * <p>The hyperLink Component translates into a classic hyperlink and is a <A href="/comps/buttonComponent.html">button</A>.</p>
 * <p>The hyperLink Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text, font &amp; image</li>
 * <li>Help</li>
 * <li>Visibility, Read-Only, Disabled</li>
 * <li>Events Handling</li>
 * </ul>
 * </p>
 */
public class HyperLinkComponent extends ButtonComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.hyperLink";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(ButtonComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {}));
	}

	public HyperLinkComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public HyperLinkComponent(String componentId) {
		this();
		setId(componentId);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
