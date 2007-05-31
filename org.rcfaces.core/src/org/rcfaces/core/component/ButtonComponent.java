package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.component.capability.ITextDirectionCapability;
import org.rcfaces.core.internal.component.Properties;

/**
 * <p>
 * The button Component is equivalent to the standard HTML tag &lt;BUTTON&gt;.
 * </p>
 * <p>
 * The button Component has the following capabilities :
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
public class ButtonComponent extends AbstractCommandComponent implements
        ITextCapability, ITextDirectionCapability, ISelectionEventCapability,
        IReadOnlyCapability {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.button";

    protected static final Set CAMELIA_ATTRIBUTES = new HashSet(
            AbstractCommandComponent.CAMELIA_ATTRIBUTES);
    static {
        CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {
                "selectionListener", "text", "readOnly", "textDirection" }));
    }

    public ButtonComponent() {
        setRendererType(COMPONENT_TYPE);
    }

    public ButtonComponent(String componentId) {
        this();
        setId(componentId);
    }

    public final Object getValue() {

        return getValue(null);

    }

    public final Object getValue(FacesContext context) {

        return engine.getValue(Properties.VALUE, context);

    }

    public java.lang.String getText() {
        return getText(null);
    }

    /**
     * See {@link #getText() getText()} for more details
     */
    public java.lang.String getText(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.TEXT, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "text" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isTextSetted() {
        return engine.isPropertySetted(Properties.TEXT);
    }

    public void setText(java.lang.String text) {
        engine.setProperty(Properties.TEXT, text);
    }

    public int getTextDirection() {
        return getTextDirection(null);
    }

    /**
     * See {@link #getTextDirection() getTextDirection()} for more details
     */
    public int getTextDirection(javax.faces.context.FacesContext facesContext) {
        return engine
                .getIntProperty(Properties.TEXT_DIRECTION, 0, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "textDirection" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isTextDirectionSetted() {
        return engine.isPropertySetted(Properties.TEXT_DIRECTION);
    }

    public void setTextDirection(int textDirection) {
        engine.setProperty(Properties.TEXT_DIRECTION, textDirection);
    }

    public final void addSelectionListener(
            org.rcfaces.core.event.ISelectionListener listener) {
        addFacesListener(listener);
    }

    public final void removeSelectionListener(
            org.rcfaces.core.event.ISelectionListener listener) {
        removeFacesListener(listener);
    }

    public final javax.faces.event.FacesListener[] listSelectionListeners() {
        return getFacesListeners(org.rcfaces.core.event.ISelectionListener.class);
    }

    public boolean isReadOnly() {
        return isReadOnly(null);
    }

    /**
     * See {@link #isReadOnly() isReadOnly()} for more details
     */
    public boolean isReadOnly(javax.faces.context.FacesContext facesContext) {
        return engine
                .getBoolProperty(Properties.READ_ONLY, false, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "readOnly" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isReadOnlySetted() {
        return engine.isPropertySetted(Properties.READ_ONLY);
    }

    public void setReadOnly(boolean readOnly) {
        engine.setProperty(Properties.READ_ONLY, readOnly);
    }

    protected Set getCameliaFields() {
        return CAMELIA_ATTRIBUTES;
    }
}
