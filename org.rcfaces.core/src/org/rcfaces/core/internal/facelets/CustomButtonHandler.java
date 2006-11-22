package org.rcfaces.core.internal.facelets;

import org.rcfaces.core.internal.taglib.Listeners;
import org.rcfaces.core.internal.taglib.Listeners.IListenerType;

import com.sun.facelets.tag.jsf.ComponentConfig;

public class CustomButtonHandler extends CameliaComponentHandler {

	public CustomButtonHandler(ComponentConfig config) {
		super(config);
	}

	protected IListenerType getDefaultListenerType() {
		return Listeners.SELECTION_LISTENER_TYPE;
	}
}
