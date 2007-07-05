package org.rcfaces.core.internal.facelets;

import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.internal.tools.ListenersTools.IListenerType;

import com.sun.facelets.tag.jsf.ComponentConfig;

public class TextAreaHandler extends CameliaComponentHandler {

	public TextAreaHandler(ComponentConfig config) {
		super(config);
	}

	protected IListenerType getDefaultListenerType() {
		return ListenersTools.SELECTION_LISTENER_TYPE;
	}

}
