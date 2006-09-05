package org.rcfaces.core.component;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.internal.component.Properties;

public class ToolItemComponent extends UIImageItemComponent implements
        ISelectionEventCapability, IReadOnlyCapability {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.toolItem";

    public ToolItemComponent() {
        setRendererType(null);
    }

    public ToolItemComponent(String componentId) {
        this();
        setId(componentId);
    }

    public final ToolFolderComponent getToolFolder() {

        return (ToolFolderComponent) getParent();

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
