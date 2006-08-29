package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.component.AbstractBasicComponent;

public class DataPagerComponent extends AbstractBasicComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.dataPager";


	public DataPagerComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public DataPagerComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final String getFor() {
		return getFor(null);
	}

	public final String getFor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOR, facesContext);
	}

	public final void setFor(String forVal) {
		engine.setProperty(Properties.FOR, forVal);
	}

	public final void setFor(ValueBinding forVal) {
		engine.setProperty(Properties.FOR, forVal);
	}

	public final boolean isForSetted() {
		return engine.isPropertySetted(Properties.FOR);
	}

	public void release() {
		super.release();
	}
}
