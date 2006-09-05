package org.rcfaces.core.component;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.internal.component.CameliaBaseComponent;
import org.rcfaces.core.internal.component.Properties;

public class FocusManagerComponent extends CameliaBaseComponent {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.focusManager";

    public FocusManagerComponent() {
        setRendererType(COMPONENT_TYPE);
    }

    public FocusManagerComponent(String componentId) {
        this();
        setId(componentId);
    }

    public final String getFocusId() {
        return getFocusId(null);
    }

    public final String getFocusId(javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.FOCUS_ID, facesContext);
    }

    public final void setFocusId(String focusId) {
        engine.setProperty(Properties.FOCUS_ID, focusId);
    }

    public final void setFocusId(ValueBinding focusId) {
        engine.setProperty(Properties.FOCUS_ID, focusId);
    }

    public final boolean isFocusIdSetted() {
        return engine.isPropertySetted(Properties.FOCUS_ID);
    }

    public void release() {
        super.release();
    }
}
