package org.rcfaces.renderkit.html.internal.facelets;

import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.internal.tools.ListenersTools.IListenerType;

import com.sun.facelets.tag.jsf.ComponentConfig;
import com.sun.facelets.FaceletContext;
import javax.faces.component.UIComponent;

public class CssStyleHandler extends org.rcfaces.core.internal.facelets.CameliaComponentHandler {

	public CssStyleHandler(ComponentConfig config) {
		super(config);
	}

	protected void onComponentCreated(FaceletContext ctx, UIComponent c, UIComponent parent) {
		setTextBody(ctx, c);
	}

}
