package org.rcfaces.core.internal.facelets;

import org.rcfaces.core.internal.taglib.Listeners;
import org.rcfaces.core.internal.taglib.Listeners.IListenerType;

import com.sun.facelets.tag.jsf.ComponentConfig;

public class AcceleratorHandler extends CameliaComponentHandler {

	public AcceleratorHandler(ComponentConfig config) {
		super(config);
	}

	protected IListenerType getDefaultListenerType() {
		return Listeners.KEY_PRESS_LISTENER_TYPE;
	}
}
