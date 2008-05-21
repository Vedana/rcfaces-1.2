package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.TextComponent;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;

/**
 * <p>The styledText Component is a placeholder for displaying "enhanced" text. It accepts any HTML tags. it's a <A href="/comps/textComponent.html">text Component</A></p>
 * <p>The styledText Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text, font &amp; justification</li>
 * <li>Margin</li>
 * <li>Help</li>
 * <li>Visibility</li>
 * <li>Events Handling</li>
 * <li>Association with another component</li>
 * </ul>
 * </p>
 */
public class StyledTextComponent extends TextComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.styledText";


	public StyledTextComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public StyledTextComponent(String componentId) {
		this();
		setId(componentId);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
