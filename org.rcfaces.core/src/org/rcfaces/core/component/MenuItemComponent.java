package org.rcfaces.core.component;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IAcceleratorKeyCapability;
import org.rcfaces.core.component.capability.IAccessKeyCapability;
import org.rcfaces.core.component.capability.IMenuEventCapability;
import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.component.iterator.IMenuItemIterator;
import org.rcfaces.core.internal.component.ExpandableItemComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.MenuTools;

public class MenuItemComponent extends ExpandableItemComponent implements
        IAccessKeyCapability, IAcceleratorKeyCapability, IVisibilityCapability,
        IMenuEventCapability {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.menuItem";

    public MenuItemComponent() {
        setRendererType(null);
    }

    public MenuItemComponent(String componentId) {
        this();
        setId(componentId);
    }

    public final IMenuComponent getMenu() {

        return MenuTools.getMenu(this);

    }

    public final IMenuItemIterator listMenuItems() {

        return MenuTools.listMenuItems(this);

    }

    public final java.lang.String getAccessKey() {
        return getAccessKey(null);
    }

    public final java.lang.String getAccessKey(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.ACCESS_KEY, facesContext);
    }

    public final void setAccessKey(java.lang.String accessKey) {
        engine.setProperty(Properties.ACCESS_KEY, accessKey);
    }

    public final void setAccessKey(ValueBinding accessKey) {
        engine.setProperty(Properties.ACCESS_KEY, accessKey);
    }

    public final java.lang.String getAcceleratorKey() {
        return getAcceleratorKey(null);
    }

    public final java.lang.String getAcceleratorKey(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.ACCELERATOR_KEY,
                facesContext);
    }

    public final void setAcceleratorKey(java.lang.String acceleratorKey) {
        engine.setProperty(Properties.ACCELERATOR_KEY, acceleratorKey);
    }

    public final void setAcceleratorKey(ValueBinding acceleratorKey) {
        engine.setProperty(Properties.ACCELERATOR_KEY, acceleratorKey);
    }

    public final void addMenuListener(
            org.rcfaces.core.event.IMenuListener listener) {
        addFacesListener(listener);
    }

    public final void removeMenuListener(
            org.rcfaces.core.event.IMenuListener listener) {
        removeFacesListener(listener);
    }

    public final javax.faces.event.FacesListener[] listMenuListeners() {
        return getFacesListeners(org.rcfaces.core.event.IMenuListener.class);
    }

    public final boolean isRemoveAllWhenShown() {
        return isRemoveAllWhenShown(null);
    }

    public final boolean isRemoveAllWhenShown(
            javax.faces.context.FacesContext facesContext) {
        return engine.getBoolProperty(Properties.REMOVE_ALL_WHEN_SHOWN, false,
                facesContext);
    }

    public final void setRemoveAllWhenShown(boolean removeAllWhenShown) {
        engine
                .setProperty(Properties.REMOVE_ALL_WHEN_SHOWN,
                        removeAllWhenShown);
    }

    public final void setRemoveAllWhenShown(ValueBinding removeAllWhenShown) {
        engine
                .setProperty(Properties.REMOVE_ALL_WHEN_SHOWN,
                        removeAllWhenShown);
    }

    public final boolean isRemoveAllWhenShownSetted() {
        return engine.isPropertySetted(Properties.REMOVE_ALL_WHEN_SHOWN);
    }

    public void release() {
        super.release();
    }
}
