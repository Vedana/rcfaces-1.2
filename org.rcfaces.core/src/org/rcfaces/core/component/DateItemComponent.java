package org.rcfaces.core.component;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IStyleClassCapability;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.internal.component.AbstractItemComponent;
import org.rcfaces.core.internal.component.Properties;

public class DateItemComponent extends AbstractItemComponent implements
        ITextCapability, IStyleClassCapability {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.dateItem";

    public DateItemComponent() {
        setRendererType(null);
    }

    public DateItemComponent(String componentId) {
        this();
        setId(componentId);
    }

    public final void setDate(Object date) {

        setItemValue(date);

    }

    public final void setDate(ValueBinding valueBinding) {

        setValueBinding("itemValue", valueBinding);

    }

    public final Object getDate() {

        return getItemValue();

    }

    public final void setText(String text) {

        setItemLabel(text);

    }

    public final void setText(ValueBinding valueBinding) {

        setValueBinding("itemLabel", valueBinding);

    }

    public final String getText() {

        return getItemLabel();

    }

    public final java.lang.String getStyleClass() {
        return getStyleClass(null);
    }

    public final java.lang.String getStyleClass(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.STYLE_CLASS, facesContext);
    }

    public final void setStyleClass(java.lang.String styleClass) {
        engine.setProperty(Properties.STYLE_CLASS, styleClass);
    }

    public final void setStyleClass(ValueBinding styleClass) {
        engine.setProperty(Properties.STYLE_CLASS, styleClass);
    }

    public void release() {
        super.release();
    }
}
