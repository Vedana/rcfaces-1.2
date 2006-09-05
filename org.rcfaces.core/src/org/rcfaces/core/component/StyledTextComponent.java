package org.rcfaces.core.component;


public class StyledTextComponent extends TextComponent {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.styledText";

    public StyledTextComponent() {
        setRendererType(COMPONENT_TYPE);
    }

    public StyledTextComponent(String componentId) {
        this();
        setId(componentId);
    }

    public void release() {
        super.release();
    }
}
