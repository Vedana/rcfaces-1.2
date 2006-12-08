package org.rcfaces.core.internal.facelets;

import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.internal.tools.ListenersTools.IListenerType;

import com.sun.facelets.tag.jsf.ComponentConfig;

public class AcceleratorHandler extends CameliaComponentHandler {

	public AcceleratorHandler(ComponentConfig config) {
		super(config);
	}

	protected IListenerType getDefaultListenerType() {
		return ListenersTools.KEY_PRESS_LISTENER_TYPE;
	}

}
