package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IDoubleClickEventCapability;
import org.rcfaces.core.component.capability.IInitEventCapability;
import org.rcfaces.core.component.capability.IMenuCapability;
import org.rcfaces.core.component.capability.IMouseEventCapability;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.MenuTools;

/**
 * <b>EXPERIMENTAL</b>
 */
public class ToolFolderComponent extends AbstractBasicComponent implements 
	IInitEventCapability,
	IMenuCapability,
	IMouseEventCapability,
	IDoubleClickEventCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.toolFolder";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractBasicComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"itemPadding","initListener","doubleClickListener","mouseOverListener","mouseOutListener"}));
	}

	public ToolFolderComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ToolFolderComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final ToolBarComponent getToolBar() {


		return (ToolBarComponent)getParent();
		
	}

	public final void addInitListener(org.rcfaces.core.event.IInitListener listener) {
		addFacesListener(listener);
	}

	public final void removeInitListener(org.rcfaces.core.event.IInitListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listInitListeners() {
		return getFacesListeners(org.rcfaces.core.event.IInitListener.class);
	}

	public final IMenuComponent getMenu(String menuId) {


		return MenuTools.getMenu(this, menuId);
		
	}

	public final IMenuComponent getMenu() {


		return MenuTools.getMenu(this);
		
	}

	public final IMenuIterator listMenus() {


		return MenuTools.listMenus(this);
		
	}

	public final void addMouseOutListener(org.rcfaces.core.event.IMouseOutListener listener) {
		addFacesListener(listener);
	}

	public final void removeMouseOutListener(org.rcfaces.core.event.IMouseOutListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listMouseOutListeners() {
		return getFacesListeners(org.rcfaces.core.event.IMouseOutListener.class);
	}

	public final void addMouseOverListener(org.rcfaces.core.event.IMouseOverListener listener) {
		addFacesListener(listener);
	}

	public final void removeMouseOverListener(org.rcfaces.core.event.IMouseOverListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listMouseOverListeners() {
		return getFacesListeners(org.rcfaces.core.event.IMouseOverListener.class);
	}

	public final void addDoubleClickListener(org.rcfaces.core.event.IDoubleClickListener listener) {
		addFacesListener(listener);
	}

	public final void removeDoubleClickListener(org.rcfaces.core.event.IDoubleClickListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listDoubleClickListeners() {
		return getFacesListeners(org.rcfaces.core.event.IDoubleClickListener.class);
	}

	public final int getItemPadding() {
		return getItemPadding(null);
	}

	public final int getItemPadding(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.ITEM_PADDING, 0, facesContext);
	}

	public final void setItemPadding(int itemPadding) {
		engine.setProperty(Properties.ITEM_PADDING, itemPadding);
	}

	public final void setItemPadding(ValueBinding itemPadding) {
		engine.setProperty(Properties.ITEM_PADDING, itemPadding);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemPadding" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isItemPaddingSetted() {
		return engine.isPropertySetted(Properties.ITEM_PADDING);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
