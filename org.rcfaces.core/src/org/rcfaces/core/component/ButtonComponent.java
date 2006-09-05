package org.rcfaces.core.component;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.internal.component.AbstractCommandComponent;
import org.rcfaces.core.internal.component.Properties;

public class ButtonComponent extends AbstractCommandComponent implements
        ITextCapability, ISelectionEventCapability, IReadOnlyCapability {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.button";

    public ButtonComponent() {
        setRendererType(COMPONENT_TYPE);
    }

    public ButtonComponent(String componentId) {
        this();
        setId(componentId);
    }

    public final void setValue(Object value) {

        if (value instanceof javax.faces.el.ValueBinding) {
            setValue((javax.faces.el.ValueBinding) value);
            return;
        }
        engine.setValue(Properties.VALUE, value);

    }

    public final void setValue(ValueBinding value) {

        engine.setValueBinding(Properties.VALUE, value);

    }

    public final Object getValue() {

        return getValue(null);

    }

    public final Object getValue(FacesContext context) {

        return engine.getValue(Properties.VALUE, context);

    }

    public final java.lang.String getText() {
        return getText(null);
    }

    public final java.lang.String getText(
            javax.faces.context.FacesContext facesContext) {
        return org.rcfaces.core.internal.tools.ValuesTools.valueToString(this,
                facesContext);
    }

    public final void setText(java.lang.String text) {
        setValue(text);
    }

    public final void setText(ValueBinding text) {
        setValue(text);
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

    public final boolean isReadOnly() {
        return isReadOnly(null);
    }

    public final boolean isReadOnly(
            javax.faces.context.FacesContext facesContext) {
        return engine
                .getBoolProperty(Properties.READ_ONLY, false, facesContext);
    }

    public final void setReadOnly(boolean readOnly) {
        engine.setProperty(Properties.READ_ONLY, readOnly);
    }

    public final void setReadOnly(ValueBinding readOnly) {
        engine.setProperty(Properties.READ_ONLY, readOnly);
    }

    public void release() {
        super.release();
    }
}
