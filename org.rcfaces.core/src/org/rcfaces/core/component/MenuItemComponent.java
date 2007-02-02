package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IAccessKeyCapability;
import org.rcfaces.core.component.ExpandableItemComponent;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import javax.faces.el.ValueBinding;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IAcceleratorKeyCapability;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.internal.tools.MenuTools;
import org.rcfaces.core.component.iterator.IMenuItemIterator;
import org.rcfaces.core.component.capability.IMenuEventCapability;

/**
 * A menu item
 */
public class MenuItemComponent extends ExpandableItemComponent implements 
	IAccessKeyCapability,
	IAcceleratorKeyCapability,
	IStyleClassCapability,
	IMenuEventCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.menuItem";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(ExpandableItemComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"styleClass","acceleratorKey","removeAllWhenShown","accessKey","menuListener"}));
	}

	public MenuItemComponent() {
		setRendererType(null);
	}

	public MenuItemComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final IMenuComponent getMenu() {


		return MenuTools.getMenu(this);
		
	}

	public final IMenuItemIterator listMenuItems() {


		return MenuTools.listMenuItems(this);
		
	}

	public final java.lang.String getAccessKey() {
		return getAccessKey(null);
	}

	/**
	 * See {@link #getAccessKey() getAccessKey()} for more details
	 */
	public final java.lang.String getAccessKey(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ACCESS_KEY, facesContext);
	}

	public final void setAccessKey(java.lang.String accessKey) {
		engine.setProperty(Properties.ACCESS_KEY, accessKey);
	}

	/**
	 * See {@link #setAccessKey(String) setAccessKey(String)} for more details
	 */
	public final void setAccessKey(ValueBinding accessKey) {
		engine.setProperty(Properties.ACCESS_KEY, accessKey);
	}

	public final java.lang.String getAcceleratorKey() {
		return getAcceleratorKey(null);
	}

	/**
	 * See {@link #getAcceleratorKey() getAcceleratorKey()} for more details
	 */
	public final java.lang.String getAcceleratorKey(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ACCELERATOR_KEY, facesContext);
	}

	public final void setAcceleratorKey(java.lang.String acceleratorKey) {
		engine.setProperty(Properties.ACCELERATOR_KEY, acceleratorKey);
	}

	/**
	 * See {@link #setAcceleratorKey(String) setAcceleratorKey(String)} for more details
	 */
	public final void setAcceleratorKey(ValueBinding acceleratorKey) {
		engine.setProperty(Properties.ACCELERATOR_KEY, acceleratorKey);
	}

	public final java.lang.String getStyleClass() {
		return getStyleClass(null);
	}

	/**
	 * See {@link #getStyleClass() getStyleClass()} for more details
	 */
	public final java.lang.String getStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.STYLE_CLASS, facesContext);
	}

	public final void setStyleClass(java.lang.String styleClass) {
		engine.setProperty(Properties.STYLE_CLASS, styleClass);
	}

	/**
	 * See {@link #setStyleClass(String) setStyleClass(String)} for more details
	 */
	public final void setStyleClass(ValueBinding styleClass) {
		engine.setProperty(Properties.STYLE_CLASS, styleClass);
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
	public final boolean isRemoveAllWhenShown() {
		return isRemoveAllWhenShown(null);
	}

	/**
	 * Returns a boolean value specifying wether the content of the component must be remove before the listener is called and the component displayed.
	 * @return true if content is removed when shown
	 */
	public final boolean isRemoveAllWhenShown(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.REMOVE_ALL_WHEN_SHOWN, false, facesContext);
	}

	/**
	 * Sets a boolean value specifying wether the content of the component must be remove before the listener is called and the component displayed.
	 * @param removeAllWhenShown true if content is to be removed when shown
	 */
	public final void setRemoveAllWhenShown(boolean removeAllWhenShown) {
		engine.setProperty(Properties.REMOVE_ALL_WHEN_SHOWN, removeAllWhenShown);
	}

	/**
	 * Sets a boolean value specifying wether the content of the component must be remove before the listener is called and the component displayed.
	 * @param removeAllWhenShown true if content is to be removed when shown
	 */
	public final void setRemoveAllWhenShown(ValueBinding removeAllWhenShown) {
		engine.setProperty(Properties.REMOVE_ALL_WHEN_SHOWN, removeAllWhenShown);
	}

	/**
	 * Returns <code>true</code> if the attribute "removeAllWhenShown" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRemoveAllWhenShownSetted() {
		return engine.isPropertySetted(Properties.REMOVE_ALL_WHEN_SHOWN);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
