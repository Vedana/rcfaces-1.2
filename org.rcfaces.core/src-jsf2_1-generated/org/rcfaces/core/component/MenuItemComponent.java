package org.rcfaces.core.component;

import org.rcfaces.core.component.ExpandableItemComponent;
import org.rcfaces.core.internal.component.Properties;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IAcceleratorKeyCapability;
import org.rcfaces.core.component.capability.IAccessKeyCapability;
import org.rcfaces.core.internal.tools.MenuTools;
import org.rcfaces.core.component.iterator.IMenuItemIterator;
import org.rcfaces.core.component.capability.IImmediateCapability;
import org.rcfaces.core.component.IMenuComponent;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.component.capability.IMenuEventCapability;
import java.util.Collection;

/**
 * A menu item
 */
public class MenuItemComponent extends ExpandableItemComponent implements 
	IAccessKeyCapability,
	IAcceleratorKeyCapability,
	IStyleClassCapability,
	IImmediateCapability,
	IMenuEventCapability {

	private static final Log LOG = LogFactory.getLog(MenuItemComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.menuItem";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=ExpandableItemComponent.BEHAVIOR_EVENT_NAMES;

	public MenuItemComponent() {
		setRendererType(null);
	}

	public MenuItemComponent(String componentId) {
		this();
		setId(componentId);
	}

	public IMenuComponent getMenu() {


		return MenuTools.getMenu(this);
		
	}

	public IMenuItemIterator listMenuItems() {


		return MenuTools.listMenuItems(this);
		
	}

	public java.lang.String getAccessKey() {
		return getAccessKey(null);
	}

	/**
	 * See {@link #getAccessKey() getAccessKey()} for more details
	 */
	public java.lang.String getAccessKey(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ACCESS_KEY);
	}

	/**
	 * Returns <code>true</code> if the attribute "accessKey" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAccessKeySetted() {
		return getStateHelper().get(Properties.ACCESS_KEY)!=null;
	}

	public void setAccessKey(java.lang.String accessKey) {
		getStateHelper().put(Properties.ACCESS_KEY, accessKey);
	}

	public java.lang.String getAcceleratorKey() {
		return getAcceleratorKey(null);
	}

	/**
	 * See {@link #getAcceleratorKey() getAcceleratorKey()} for more details
	 */
	public java.lang.String getAcceleratorKey(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ACCELERATOR_KEY);
	}

	/**
	 * Returns <code>true</code> if the attribute "acceleratorKey" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAcceleratorKeySetted() {
		return getStateHelper().get(Properties.ACCELERATOR_KEY)!=null;
	}

	public void setAcceleratorKey(java.lang.String acceleratorKey) {
		getStateHelper().put(Properties.ACCELERATOR_KEY, acceleratorKey);
	}

	public java.lang.String getStyleClass() {
		return getStyleClass(null);
	}

	/**
	 * See {@link #getStyleClass() getStyleClass()} for more details
	 */
	public java.lang.String getStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "styleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isStyleClassSetted() {
		return getStateHelper().get(Properties.STYLE_CLASS)!=null;
	}

	public void setStyleClass(java.lang.String styleClass) {
		getStateHelper().put(Properties.STYLE_CLASS, styleClass);
	}

	public boolean isImmediate() {
		return isImmediate(null);
	}

	/**
	 * See {@link #isImmediate() isImmediate()} for more details
	 */
	public boolean isImmediate(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.IMMEDIATE, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "immediate" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImmediateSetted() {
		return getStateHelper().get(Properties.IMMEDIATE)!=null;
	}

	public void setImmediate(boolean immediate) {
		getStateHelper().put(Properties.IMMEDIATE, immediate);
	}

	public final void addMenuListener(org.rcfaces.core.event.IMenuListener listener) {
		addFacesListener(listener);
	}

	public final void removeMenuListener(org.rcfaces.core.event.IMenuListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listMenuListeners() {
		return getFacesListeners(org.rcfaces.core.event.IMenuListener.class);
	}

	/**
	 * Returns a boolean value specifying wether the content of the component must be remove before the listener is called and the component displayed.
	 * @return true if content is removed when shown
	 */
	public boolean isRemoveAllWhenShown() {
		return isRemoveAllWhenShown(null);
	}

	/**
	 * Returns a boolean value specifying wether the content of the component must be remove before the listener is called and the component displayed.
	 * @return true if content is removed when shown
	 */
	public boolean isRemoveAllWhenShown(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.REMOVE_ALL_WHEN_SHOWN, false);
	}

	/**
	 * Sets a boolean value specifying wether the content of the component must be remove before the listener is called and the component displayed.
	 * @param removeAllWhenShown true if content is to be removed when shown
	 */
	public void setRemoveAllWhenShown(boolean removeAllWhenShown) {
		 getStateHelper().put(Properties.REMOVE_ALL_WHEN_SHOWN, removeAllWhenShown);
	}

	/**
	 * Sets a boolean value specifying wether the content of the component must be remove before the listener is called and the component displayed.
	 * @param removeAllWhenShown true if content is to be removed when shown
	 */
	/**
	 * Returns <code>true</code> if the attribute "removeAllWhenShown" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isRemoveAllWhenShownSetted() {
		return getStateHelper().get(Properties.REMOVE_ALL_WHEN_SHOWN)!=null;
	}

}
