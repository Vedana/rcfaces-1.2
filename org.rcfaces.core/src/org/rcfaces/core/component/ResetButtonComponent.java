package org.rcfaces.core.component;


public class ResetButtonComponent extends ButtonComponent {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.resetButton";

    public ResetButtonComponent() {
        setRendererType(COMPONENT_TYPE);
    }

    public ResetButtonComponent(String componentId) {
        this();
        setId(componentId);
    }

    public void release() {
        super.release();
    }
}
