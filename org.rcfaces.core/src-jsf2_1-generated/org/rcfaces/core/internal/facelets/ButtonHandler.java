package org.rcfaces.core.internal.facelets;

import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.internal.tools.ListenersTools.IListenerType;

import javax.faces.view.facelets.ComponentConfig;

public class ButtonHandler extends CameliaComponentHandler {

	public ButtonHandler(ComponentConfig config) {
		super(config);
	}

	protected IListenerType getDefaultListenerType() {
		return ListenersTools.SELECTION_LISTENER_TYPE;
	}

}
