package org.rcfaces.core.internal.facelets;

import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.internal.tools.ListenersTools.IListenerType;

import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.component.UIComponent;

public class TextHandler extends CameliaComponentHandler {

	public TextHandler(ComponentConfig config) {
		super(config);
	}

	@Override
	public void onComponentCreated(FaceletContext ctx, UIComponent c, UIComponent parent) {
		setTextBody(ctx, c);
	}

	@Override
	public void applyNextHandler(FaceletContext ctx, UIComponent c) {
	}

}
