package org.rcfaces.core.component;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IRadioGroupCapability;
import org.rcfaces.core.internal.component.Properties;

public class MenuRadioItemComponent extends MenuCheckItemComponent implements
        IRadioGroupCapability {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.menuRadioItem";

    public MenuRadioItemComponent() {
        setRendererType(null);
    }

    public MenuRadioItemComponent(String componentId) {
        this();
        setId(componentId);
    }

    public final java.lang.String getGroupName() {
        return getGroupName(null);
    }

    public final java.lang.String getGroupName(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.GROUP_NAME, facesContext);
    }

    public final void setGroupName(java.lang.String groupName) {
        engine.setProperty(Properties.GROUP_NAME, groupName);
    }

    public final void setGroupName(ValueBinding groupName) {
        engine.setProperty(Properties.GROUP_NAME, groupName);
    }

    public void release() {
        super.release();
    }
}
