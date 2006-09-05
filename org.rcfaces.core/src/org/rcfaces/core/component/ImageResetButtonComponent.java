package org.rcfaces.core.component;


public class ImageResetButtonComponent extends ImageButtonComponent {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.imageResetButton";

    public ImageResetButtonComponent() {
        setRendererType(COMPONENT_TYPE);
    }

    public ImageResetButtonComponent(String componentId) {
        this();
        setId(componentId);
    }

    public void release() {
        super.release();
    }
}
