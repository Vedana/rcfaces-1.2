package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.faces.el.ValueBinding;
import java.util.Arrays;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import java.util.Set;
import java.util.HashSet;

public class FocusManagerComponent extends CameliaBaseComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.focusManager";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"focusId"}));
	}

	public FocusManagerComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public FocusManagerComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final String getFocusId() {
		return getFocusId(null);
	}

	public final String getFocusId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOCUS_ID, facesContext);
	}

	public final void setFocusId(String focusId) {
		engine.setProperty(Properties.FOCUS_ID, focusId);
	}

	public final void setFocusId(ValueBinding focusId) {
		engine.setProperty(Properties.FOCUS_ID, focusId);
	}

	public final boolean isFocusIdSetted() {
		return engine.isPropertySetted(Properties.FOCUS_ID);
	}

	public void release() {
		super.release();
	}
	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
