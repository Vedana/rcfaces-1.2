package org.rcfaces.core.component;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.TextEntryComponent;
import org.rcfaces.core.internal.component.Properties;

public class SpinnerComponent extends TextEntryComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.spinner";


	public SpinnerComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public SpinnerComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final int getMinimum() {
		return getMinimum(null);
	}

	public final int getMinimum(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.MINIMUM, 0, facesContext);
	}

	public final void setMinimum(int minimum) {
		engine.setProperty(Properties.MINIMUM, minimum);
	}

	public final void setMinimum(ValueBinding minimum) {
		engine.setProperty(Properties.MINIMUM, minimum);
	}

	public final boolean isMinimumSetted() {
		return engine.isPropertySetted(Properties.MINIMUM);
	}

	public final int getMaximum() {
		return getMaximum(null);
	}

	public final int getMaximum(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.MAXIMUM, 0, facesContext);
	}

	public final void setMaximum(int maximum) {
		engine.setProperty(Properties.MAXIMUM, maximum);
	}

	public final void setMaximum(ValueBinding maximum) {
		engine.setProperty(Properties.MAXIMUM, maximum);
	}

	public final boolean isMaximumSetted() {
		return engine.isPropertySetted(Properties.MAXIMUM);
	}

	public void release() {
		super.release();
	}
}
