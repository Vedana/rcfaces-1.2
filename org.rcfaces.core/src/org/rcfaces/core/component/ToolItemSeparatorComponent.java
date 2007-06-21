package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.rcfaces.core.component.capability.IAlternateTextCapability;
import org.rcfaces.core.internal.component.Properties;

/**
 * <b>EXPERIMENTAL</b>
 */
public class ToolItemSeparatorComponent extends AbstractSeparatorComponent
        implements IAlternateTextCapability {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.toolItemSeparator";

    protected static final Set CAMELIA_ATTRIBUTES = new HashSet(
            AbstractSeparatorComponent.CAMELIA_ATTRIBUTES);
    static {
        CAMELIA_ATTRIBUTES.addAll(Arrays
                .asList(new String[] { "alternateText" }));
    }

    public ToolItemSeparatorComponent() {
        setRendererType(null);
    }

    public ToolItemSeparatorComponent(String componentId) {
        this();
        setId(componentId);
    }

    public ToolFolderComponent getToolFolder() {

        return (ToolFolderComponent) getParent();

    }

    public java.lang.String getAlternateText() {
        return getAlternateText(null);
    }

    /**
     * See {@link #getAlternateText() getAlternateText()} for more details
     */
    public java.lang.String getAlternateText(
            javax.faces.context.FacesContext facesContext) {
        return engine
                .getStringProperty(Properties.ALTERNATE_TEXT, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "alternateText" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isAlternateTextSetted() {
        return engine.isPropertySetted(Properties.ALTERNATE_TEXT);
    }

    public void setAlternateText(java.lang.String alternateText) {
        engine.setProperty(Properties.ALTERNATE_TEXT, alternateText);
    }

    protected Set getCameliaFields() {
        return CAMELIA_ATTRIBUTES;
    }
}
