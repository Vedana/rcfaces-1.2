package org.rcfaces.core.component;


public class SubmitButtonComponent extends ButtonComponent {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.submitButton";

    public SubmitButtonComponent() {
        setRendererType(COMPONENT_TYPE);
    }

    public SubmitButtonComponent(String componentId) {
        this();
        setId(componentId);
    }

    public void release() {
        super.release();
    }
}
