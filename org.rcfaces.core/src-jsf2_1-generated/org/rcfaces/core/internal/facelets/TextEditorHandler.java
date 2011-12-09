package org.rcfaces.core.internal.facelets;

import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.internal.tools.ListenersTools.IListenerType;

import javax.faces.view.facelets.ComponentConfig;

public class TextEditorHandler extends CameliaComponentHandler {

	public TextEditorHandler(ComponentConfig config) {
		super(config);
	}

	protected IListenerType getDefaultListenerType() {
		return ListenersTools.SELECTION_LISTENER_TYPE;
	}

}
