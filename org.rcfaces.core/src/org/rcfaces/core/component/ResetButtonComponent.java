package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * The resetButton Component is based on the standard HTML tag &lt;INPUT
 * TYPE="reset"&gt;. It is a <a href="/comps/buttonComponent.html">button
 * Component</a>.
 * </p>
 * <p>
 * The resetButton Component has the following capabilities :
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

    public static final String COMPONENT_TYPE = "org.rcfaces.core.resetButton";

    protected static final Set CAMELIA_ATTRIBUTES = new HashSet(
            ButtonComponent.CAMELIA_ATTRIBUTES);
    static {
        CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {}));
    }

    public ResetButtonComponent() {
        setRendererType(COMPONENT_TYPE);
    }

    public ResetButtonComponent(String componentId) {
        this();
        setId(componentId);
    }

    protected Set getCameliaFields() {
        return CAMELIA_ATTRIBUTES;
    }
}
