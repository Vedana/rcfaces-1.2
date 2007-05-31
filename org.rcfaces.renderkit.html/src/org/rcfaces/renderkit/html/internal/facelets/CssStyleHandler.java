package org.rcfaces.renderkit.html.internal.facelets;

import javax.faces.component.UIComponent;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.jsf.ComponentConfig;

public class CssStyleHandler extends
        org.rcfaces.core.internal.facelets.CameliaComponentHandler {

    public CssStyleHandler(ComponentConfig config) {
        super(config);
    }

    protected void onComponentCreated(FaceletContext ctx, UIComponent c,
            UIComponent parent) {
        setTextBody(ctx, c);
    }

    protected void applyNextHandler(FaceletContext ctx, UIComponent c) {
    }

}
