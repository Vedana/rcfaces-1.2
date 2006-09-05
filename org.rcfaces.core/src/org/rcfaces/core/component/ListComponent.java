package org.rcfaces.core.component;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IDoubleClickEventCapability;
import org.rcfaces.core.component.capability.IMultipleSelectCapability;
import org.rcfaces.core.internal.component.Properties;

public class ListComponent extends ComboComponent implements
        IMultipleSelectCapability, IDoubleClickEventCapability {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.list";

    public ListComponent() {
        setRendererType(COMPONENT_TYPE);
    }

    public ListComponent(String componentId) {
        this();
        setId(componentId);
    }

    public final boolean isMultipleSelect() {
        return isMultipleSelect(null);
    }

    public final boolean isMultipleSelect(
            javax.faces.context.FacesContext facesContext) {
        return engine.getBoolProperty(Properties.MULTIPLE_SELECT, false,
                facesContext);
    }

    public final void setMultipleSelect(boolean multipleSelect) {
        engine.setProperty(Properties.MULTIPLE_SELECT, multipleSelect);
    }

    public final void setMultipleSelect(ValueBinding multipleSelect) {
        engine.setProperty(Properties.MULTIPLE_SELECT, multipleSelect);
    }

    public final void addDoubleClickListener(
            org.rcfaces.core.event.IDoubleClickListener listener) {
        addFacesListener(listener);
    }

    public final void removeDoubleClickListener(
            org.rcfaces.core.event.IDoubleClickListener listener) {
        removeFacesListener(listener);
    }

    public final javax.faces.event.FacesListener[] listDoubleClickListeners() {
        return getFacesListeners(org.rcfaces.core.event.IDoubleClickListener.class);
    }

    public final int getRowNumber() {
        return getRowNumber(null);
    }

    public final int getRowNumber(javax.faces.context.FacesContext facesContext) {
        return engine.getIntProperty(Properties.ROW_NUMBER, 0, facesContext);
    }

    public final void setRowNumber(int rowNumber) {
        engine.setProperty(Properties.ROW_NUMBER, rowNumber);
    }

    public final void setRowNumber(ValueBinding rowNumber) {
        engine.setProperty(Properties.ROW_NUMBER, rowNumber);
    }

    public final boolean isRowNumberSetted() {
        return engine.isPropertySetted(Properties.ROW_NUMBER);
    }

    public void release() {
        super.release();
    }
}
