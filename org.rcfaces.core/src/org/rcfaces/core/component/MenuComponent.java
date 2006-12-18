package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.component.CameliaSelectManyComponent;
import org.rcfaces.core.component.capability.ICheckEventCapability;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.component.iterator.IMenuItemIterator;
import org.rcfaces.core.internal.tools.MenuTools;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.capability.IMenuEventCapability;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class MenuComponent extends CameliaSelectManyComponent implements 
	IMenuEventCapability,
	ISelectionEventCapability,
	ICheckEventCapability,
	IMenuComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.menu";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaSelectManyComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"selectionListener","menuId","converter","checkedValues","checkListener","removeAllWhenShown","menuListener","selectedValues"}));
	}

	public MenuComponent() {
		setRendererType(null);
	}

	public MenuComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final IMenuItemIterator listMenuItems() {


		return MenuTools.listMenuItems(this);
		
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

	public final void addSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		addFacesListener(listener);
	}

	public final void removeSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listSelectionListeners() {
		return getFacesListeners(org.rcfaces.core.event.ISelectionListener.class);
	}

	public final void addCheckListener(org.rcfaces.core.event.ICheckListener listener) {
		addFacesListener(listener);
	}

	public final void removeCheckListener(org.rcfaces.core.event.ICheckListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listCheckListeners() {
		return getFacesListeners(org.rcfaces.core.event.ICheckListener.class);
	}

	/**
	 * Returns a table of the values associated with selected nodes for the component. (Binding only)
	 * @return table of values
	 */
	public final Object[] getSelectedValues() {
		return getSelectedValues(null);
	}

	/**
	 * Returns a table of the values associated with selected nodes for the component. (Binding only)
	 * @return table of values
	 */
	public final Object[] getSelectedValues(javax.faces.context.FacesContext facesContext) {
		return (Object[])engine.getValue(Properties.SELECTED_VALUES, facesContext);
	}

	/**
	 * Sets a table of the values associated with selected nodes for the component. (Binding only)
	 * @param selectedValues table of values
	 */
	public final void setSelectedValues(Object[] selectedValues) {
		engine.setProperty(Properties.SELECTED_VALUES, selectedValues);
	}

	/**
	 * Sets a table of the values associated with selected nodes for the component. (Binding only)
	 * @param selectedValues table of values
	 */
	public final void setSelectedValues(ValueBinding selectedValues) {
		engine.setValueBinding(Properties.SELECTED_VALUES, selectedValues);
	}

	/**
	 * Returns <code>true</code> if the attribute "selectedValues" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSelectedValuesSetted() {
		return engine.isPropertySetted(Properties.SELECTED_VALUES);
	}

	public final Object[] getCheckedValues() {
		return getCheckedValues(null);
	}

	public final Object[] getCheckedValues(javax.faces.context.FacesContext facesContext) {
		return (Object[])engine.getValue(Properties.CHECKED_VALUES, facesContext);
	}

	public final void setCheckedValues(Object[] checkedValues) {
		engine.setProperty(Properties.CHECKED_VALUES, checkedValues);
	}

	public final void setCheckedValues(ValueBinding checkedValues) {
		engine.setValueBinding(Properties.CHECKED_VALUES, checkedValues);
	}

	/**
	 * Returns <code>true</code> if the attribute "checkedValues" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCheckedValuesSetted() {
		return engine.isPropertySetted(Properties.CHECKED_VALUES);
	}

	/**
	 * Returns an id used to retreive a specific menu. For example, "#node" is used to call a menu on a tree node while "#body" is used for the menu boody.
	 * @return menu Id
	 */
	public final String getMenuId() {
		return getMenuId(null);
	}

	/**
	 * Returns an id used to retreive a specific menu. For example, "#node" is used to call a menu on a tree node while "#body" is used for the menu boody.
	 * @return menu Id
	 */
	public final String getMenuId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MENU_ID, facesContext);
	}

	/**
	 * Sets an id used to retreive a specific menu. For example, "#node" is used to call a menu on a tree node while "#body" is used for the menu boody.
	 * @param menuId menu Id
	 */
	public final void setMenuId(String menuId) {
		engine.setProperty(Properties.MENU_ID, menuId);
	}

	/**
	 * Sets an id used to retreive a specific menu. For example, "#node" is used to call a menu on a tree node while "#body" is used for the menu boody.
	 * @param menuId menu Id
	 */
	public final void setMenuId(ValueBinding menuId) {
		engine.setProperty(Properties.MENU_ID, menuId);
	}

	/**
	 * Returns <code>true</code> if the attribute "menuId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMenuIdSetted() {
		return engine.isPropertySetted(Properties.MENU_ID);
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
