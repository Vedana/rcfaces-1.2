package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A separator Menu item
 */
public class MenuSeparatorComponent extends AbstractSeparatorComponent {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.menuSeparator";

    protected static final Set CAMELIA_ATTRIBUTES = new HashSet(
            AbstractSeparatorComponent.CAMELIA_ATTRIBUTES);
    static {
        CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {}));
    }

    public MenuSeparatorComponent() {
        setRendererType(null);
    }

    public MenuSeparatorComponent(String componentId) {
        this();
        setId(componentId);
    }

    protected Set getCameliaFields() {
        return CAMELIA_ATTRIBUTES;
    }
}
