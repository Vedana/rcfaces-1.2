package org.rcfaces.core.component;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.ICheckEventCapability;
import org.rcfaces.core.component.capability.IMenuEventCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.iterator.IMenuItemIterator;
import org.rcfaces.core.internal.component.CameliaSelectManyComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.MenuTools;

public class MenuComponent extends CameliaSelectManyComponent implements 
	IMenuEventCapability,
	ISelectionEventCapability,
	ICheckEventCapability,
	IMenuComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.menu";


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

	public final Object[] getSelectionValues() {
		return getSelectionValues(null);
	}

	public final Object[] getSelectionValues(javax.faces.context.FacesContext facesContext) {
		return (Object[])engine.getValue(Properties.SELECTION_VALUES, facesContext);
	}

	public final void setSelectionValues(Object[] selectionValues) {
		engine.setProperty(Properties.SELECTION_VALUES, selectionValues);
	}

	public final void setSelectionValues(ValueBinding selectionValues) {
		engine.setValueBinding(Properties.SELECTION_VALUES, selectionValues);
	}

	public final boolean isSelectionValuesSetted() {
		return engine.isPropertySetted(Properties.SELECTION_VALUES);
	}

	public final Object[] getCheckValues() {
		return getCheckValues(null);
	}

	public final Object[] getCheckValues(javax.faces.context.FacesContext facesContext) {
		return (Object[])engine.getValue(Properties.CHECK_VALUES, facesContext);
	}

	public final void setCheckValues(Object[] checkValues) {
		engine.setProperty(Properties.CHECK_VALUES, checkValues);
	}

	public final void setCheckValues(ValueBinding checkValues) {
		engine.setValueBinding(Properties.CHECK_VALUES, checkValues);
	}

	public final boolean isCheckValuesSetted() {
		return engine.isPropertySetted(Properties.CHECK_VALUES);
	}

	public final String getMenuId() {
		return getMenuId(null);
	}

	public final String getMenuId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MENU_ID, facesContext);
	}

	public final void setMenuId(String menuId) {
		engine.setProperty(Properties.MENU_ID, menuId);
	}

	public final void setMenuId(ValueBinding menuId) {
		engine.setProperty(Properties.MENU_ID, menuId);
	}

	public final boolean isMenuIdSetted() {
		return engine.isPropertySetted(Properties.MENU_ID);
	}

	public final boolean isRemoveAllWhenShown() {
		return isRemoveAllWhenShown(null);
	}

	public final boolean isRemoveAllWhenShown(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.REMOVE_ALL_WHEN_SHOWN, false, facesContext);
	}

	public final void setRemoveAllWhenShown(boolean removeAllWhenShown) {
		engine.setProperty(Properties.REMOVE_ALL_WHEN_SHOWN, removeAllWhenShown);
	}

	public final void setRemoveAllWhenShown(ValueBinding removeAllWhenShown) {
		engine.setProperty(Properties.REMOVE_ALL_WHEN_SHOWN, removeAllWhenShown);
	}

	public final boolean isRemoveAllWhenShownSetted() {
		return engine.isPropertySetted(Properties.REMOVE_ALL_WHEN_SHOWN);
	}

	public void release() {
		super.release();
	}
}
