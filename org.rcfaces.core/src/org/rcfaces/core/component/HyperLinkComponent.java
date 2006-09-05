package org.rcfaces.core.component;


public class HyperLinkComponent extends ButtonComponent {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.hyperLink";

    public HyperLinkComponent() {
        setRendererType(COMPONENT_TYPE);
    }

    public HyperLinkComponent(String componentId) {
        this();
        setId(componentId);
    }

    public void release() {
        super.release();
    }
}
