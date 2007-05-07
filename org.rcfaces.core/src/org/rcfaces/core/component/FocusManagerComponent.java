package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.internal.component.CameliaBaseComponent;
import org.rcfaces.core.internal.component.Properties;

/**
 * <p>The focusManager Component is a non-visual component.</p>
 * <p>It allows to deal with the focus on the current page.</p>
 */
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

	/**
	 * Returns a component's id. This component will get the focus.
	 * @return id
	 */
	public final String getFocusId() {
		return getFocusId(null);
	}

	/**
	 * Returns a component's id. This component will get the focus.
	 * @return id
	 */
	public final String getFocusId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOCUS_ID, facesContext);
	}

	/**
	 * Sets a component's id. This component will get the focus.
	 * @param focusId id
	 */
	public final void setFocusId(String focusId) {
		engine.setProperty(Properties.FOCUS_ID, focusId);
	}

	/**
	 * Sets a component's id. This component will get the focus.
	 * @param focusId id
	 */
	public final void setFocusId(ValueBinding focusId) {
		engine.setProperty(Properties.FOCUS_ID, focusId);
	}

	/**
	 * Returns <code>true</code> if the attribute "focusId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFocusIdSetted() {
		return engine.isPropertySetted(Properties.FOCUS_ID);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
