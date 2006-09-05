package org.rcfaces.core.component;


public class ImageSubmitButtonComponent extends ImageButtonComponent {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.imageSubmitButton";

    public ImageSubmitButtonComponent() {
        setRendererType(COMPONENT_TYPE);
    }

    public ImageSubmitButtonComponent(String componentId) {
        this();
        setId(componentId);
    }

    public void release() {
        super.release();
    }
}
