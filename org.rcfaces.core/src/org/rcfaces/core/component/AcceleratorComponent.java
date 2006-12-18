package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.CameliaCommandComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.capability.IKeyPressEventCapability;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.capability.IImmediateCapability;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IForCapability;

public class AcceleratorComponent extends CameliaCommandComponent implements 
	IKeyPressEventCapability,
	IImmediateCapability,
	IForCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.accelerator";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaCommandComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"forItemValue","keyBinding","immediate","keyPressListener","for"}));
	}

	public AcceleratorComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public AcceleratorComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final void addKeyPressListener(org.rcfaces.core.event.IKeyPressListener listener) {
		addFacesListener(listener);
	}

	public final void removeKeyPressListener(org.rcfaces.core.event.IKeyPressListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listKeyPressListeners() {
		return getFacesListeners(org.rcfaces.core.event.IKeyPressListener.class);
	}

	public final java.lang.String getFor() {
		return getFor(null);
	}

	public final java.lang.String getFor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOR, facesContext);
	}

	public final void setFor(java.lang.String forValue) {
		engine.setProperty(Properties.FOR, forValue);
	}

	public final void setFor(ValueBinding forValue) {
		engine.setProperty(Properties.FOR, forValue);
	}

	/**
	 * Returns a string specifying a value to select in the component identified in the <b>for</b> property.
	 * @return value to selected
	 */
	public final String getForItemValue() {
		return getForItemValue(null);
	}

	/**
	 * Returns a string specifying a value to select in the component identified in the <b>for</b> property.
	 * @return value to selected
	 */
	public final String getForItemValue(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOR_ITEM_VALUE, facesContext);
	}

	/**
	 * Sets a string specifying a value to select in the component identified in the <b>for</b> property.
	 * @param forItemValue value to select
	 */
	public final void setForItemValue(String forItemValue) {
		engine.setProperty(Properties.FOR_ITEM_VALUE, forItemValue);
	}

	/**
	 * Sets a string specifying a value to select in the component identified in the <b>for</b> property.
	 * @param forItemValue value to select
	 */
	public final void setForItemValue(ValueBinding forItemValue) {
		engine.setProperty(Properties.FOR_ITEM_VALUE, forItemValue);
	}

	/**
	 * Returns <code>true</code> if the attribute "forItemValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isForItemValueSetted() {
		return engine.isPropertySetted(Properties.FOR_ITEM_VALUE);
	}

	/**
	 * Returns a string value specifying the key associated to the component. for example "Alt+F1"
	 * @return key
	 */
	public final String getKeyBinding() {
		return getKeyBinding(null);
	}

	/**
	 * Returns a string value specifying the key associated to the component. for example "Alt+F1"
	 * @return key
	 */
	public final String getKeyBinding(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.KEY_BINDING, facesContext);
	}

	/**
	 * Sets a string value specifying the key associated to the component. for example "Alt+F1"
	 * @param keyBinding key
	 */
	public final void setKeyBinding(String keyBinding) {
		engine.setProperty(Properties.KEY_BINDING, keyBinding);
	}

	/**
	 * Sets a string value specifying the key associated to the component. for example "Alt+F1"
	 * @param keyBinding key
	 */
	public final void setKeyBinding(ValueBinding keyBinding) {
		engine.setProperty(Properties.KEY_BINDING, keyBinding);
	}

	/**
	 * Returns <code>true</code> if the attribute "keyBinding" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isKeyBindingSetted() {
		return engine.isPropertySetted(Properties.KEY_BINDING);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
