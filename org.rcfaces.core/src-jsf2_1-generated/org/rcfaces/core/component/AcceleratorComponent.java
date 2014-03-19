package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IKeyPressEventCapability;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IForCapability;
import org.rcfaces.core.component.capability.IValidationEventCapability;
import org.rcfaces.core.component.capability.IImmediateCapability;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import java.util.Set;
import java.util.Arrays;
import java.util.Collection;
import org.rcfaces.core.internal.component.CameliaCommandComponent;

/**
 * <p>The accelerator Component is a non-visual component.</p>
 * <p>It allows to associate an accelerator key to an action or another component.</p>
 * <p>The accelerator Component has the following capabilities :
 * <ul>
 * <li>Key pressed</li>
 * <li>Association to another component</li>
 * </ul>
 * </p>
 */
public class AcceleratorComponent extends CameliaCommandComponent implements 
	IKeyPressEventCapability,
	IImmediateCapability,
	IValidationEventCapability,
	IForCapability {

	private static final Log LOG = LogFactory.getLog(AcceleratorComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.accelerator";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=CameliaCommandComponent.BEHAVIOR_EVENT_NAMES;

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

	public final void addValidationListener(org.rcfaces.core.event.IValidationListener listener) {
		addFacesListener(listener);
	}

	public final void removeValidationListener(org.rcfaces.core.event.IValidationListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listValidationListeners() {
		return getFacesListeners(org.rcfaces.core.event.IValidationListener.class);
	}

	public java.lang.String getFor() {
		return getFor(null);
	}

	/**
	 * See {@link #getFor() getFor()} for more details
	 */
	public java.lang.String getFor(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.FOR);
	}

	/**
	 * Returns <code>true</code> if the attribute "for" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isForSetted() {
		return getStateHelper().get(Properties.FOR)!=null;
	}

	public void setFor(java.lang.String forValue) {
		getStateHelper().put(Properties.FOR, forValue);
	}

	/**
	 * Returns a string specifying a value to select in the component identified in the <b>for</b> property.
	 * @return value to selected
	 */
	public String getForItemValue() {
		return getForItemValue(null);
	}

	/**
	 * Returns a string specifying a value to select in the component identified in the <b>for</b> property.
	 * @return value to selected
	 */
	public String getForItemValue(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.FOR_ITEM_VALUE);
	}

	/**
	 * Sets a string specifying a value to select in the component identified in the <b>for</b> property.
	 * @param forItemValue value to select
	 */
	public void setForItemValue(String forItemValue) {
		 getStateHelper().put(Properties.FOR_ITEM_VALUE, forItemValue);
	}

	/**
	 * Sets a string specifying a value to select in the component identified in the <b>for</b> property.
	 * @param forItemValue value to select
	 */
	/**
	 * Returns <code>true</code> if the attribute "forItemValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isForItemValueSetted() {
		return getStateHelper().get(Properties.FOR_ITEM_VALUE)!=null;
	}

	/**
	 * Returns a string value specifying the key associated to the component. for example "Alt+F1"
	 * @return key
	 */
	public String getKeyBinding() {
		return getKeyBinding(null);
	}

	/**
	 * Returns a string value specifying the key associated to the component. for example "Alt+F1"
	 * @return key
	 */
	public String getKeyBinding(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.KEY_BINDING);
	}

	/**
	 * Sets a string value specifying the key associated to the component. for example "Alt+F1"
	 * @param keyBinding key
	 */
	public void setKeyBinding(String keyBinding) {
		 getStateHelper().put(Properties.KEY_BINDING, keyBinding);
	}

	/**
	 * Sets a string value specifying the key associated to the component. for example "Alt+F1"
	 * @param keyBinding key
	 */
	/**
	 * Returns <code>true</code> if the attribute "keyBinding" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isKeyBindingSetted() {
		return getStateHelper().get(Properties.KEY_BINDING)!=null;
	}

	public boolean isIgnoreEditableComponent() {
		return isIgnoreEditableComponent(null);
	}

	public boolean isIgnoreEditableComponent(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.IGNORE_EDITABLE_COMPONENT, false);
	}

	public void setIgnoreEditableComponent(boolean ignoreEditableComponent) {
		 getStateHelper().put(Properties.IGNORE_EDITABLE_COMPONENT, ignoreEditableComponent);
	}

	/**
	 * Returns <code>true</code> if the attribute "ignoreEditableComponent" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isIgnoreEditableComponentSetted() {
		return getStateHelper().get(Properties.IGNORE_EDITABLE_COMPONENT)!=null;
	}

}