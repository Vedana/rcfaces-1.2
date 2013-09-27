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

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(ExpandableItemComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"acceleratorKey","immediate","accessKey","removeAllWhenShown","menuListener","styleClass"}));
	}

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
		return engine.getStringProperty(Properties.ACCESS_KEY, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "accessKey" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAccessKeySetted() {
		return engine.isPropertySetted(Properties.ACCESS_KEY);
	}

	public void setAccessKey(java.lang.String accessKey) {
		engine.setProperty(Properties.ACCESS_KEY, accessKey);
	}

	public java.lang.String getAcceleratorKey() {
		return getAcceleratorKey(null);
	}

	/**
	 * See {@link #getAcceleratorKey() getAcceleratorKey()} for more details
	 */
	public java.lang.String getAcceleratorKey(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ACCELERATOR_KEY, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "acceleratorKey" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAcceleratorKeySetted() {
		return engine.isPropertySetted(Properties.ACCELERATOR_KEY);
	}

	public void setAcceleratorKey(java.lang.String acceleratorKey) {
		engine.setProperty(Properties.ACCELERATOR_KEY, acceleratorKey);
	}

	public java.lang.String getStyleClass() {
		return getStyleClass(null);
	}

	/**
	 * See {@link #getStyleClass() getStyleClass()} for more details
	 */
	public java.lang.String getStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.STYLE_CLASS, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "styleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isStyleClassSetted() {
		return engine.isPropertySetted(Properties.STYLE_CLASS);
	}

	public void setStyleClass(java.lang.String styleClass) {
		engine.setProperty(Properties.STYLE_CLASS, styleClass);
	}

	public boolean isImmediate() {
		return isImmediate(null);
	}

	/**
	 * See {@link #isImmediate() isImmediate()} for more details
	 */
	public boolean isImmediate(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.IMMEDIATE, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "immediate" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImmediateSetted() {
		return engine.isPropertySetted(Properties.IMMEDIATE);
	}

	public void setImmediate(boolean immediate) {
		engine.setProperty(Properties.IMMEDIATE, immediate);
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
		return engine.getBoolProperty(Properties.REMOVE_ALL_WHEN_SHOWN, false, facesContext);
	}

	/**
	 * Sets a boolean value specifying wether the content of the component must be remove before the listener is called and the component displayed.
	 * @param removeAllWhenShown true if content is to be removed when shown
	 */
	public void setRemoveAllWhenShown(boolean removeAllWhenShown) {
		engine.setProperty(Properties.REMOVE_ALL_WHEN_SHOWN, removeAllWhenShown);
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
		return engine.isPropertySetted(Properties.REMOVE_ALL_WHEN_SHOWN);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
