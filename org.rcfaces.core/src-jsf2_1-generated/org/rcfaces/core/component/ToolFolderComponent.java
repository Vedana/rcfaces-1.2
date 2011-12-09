package org.rcfaces.core.component;

import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.internal.component.Properties;
import org.apache.commons.logging.LogFactory;
import java.lang.String;
import org.rcfaces.core.component.ToolBarComponent;
import org.rcfaces.core.internal.tools.MenuTools;
import org.rcfaces.core.component.AbstractBasicComponent;
import org.rcfaces.core.component.capability.IBorderTypeCapability;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.component.capability.IMouseEventCapability;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import org.rcfaces.core.internal.tools.ToolBarTools;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.component.capability.IInitEventCapability;
import org.rcfaces.core.component.capability.IDoubleClickEventCapability;
import org.rcfaces.core.component.capability.IMenuCapability;
import java.util.Collection;
import org.rcfaces.core.component.capability.IVerticalAlignmentCapability;

/**
 * <b>EXPERIMENTAL</b>
 */
public class ToolFolderComponent extends AbstractBasicComponent implements 
	IInitEventCapability,
	IMenuCapability,
	IMouseEventCapability,
	IDoubleClickEventCapability,
	IVerticalAlignmentCapability,
	IBorderTypeCapability {

	private static final Log LOG = LogFactory.getLog(ToolFolderComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.toolFolder";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractBasicComponent.BEHAVIOR_EVENT_NAMES;

	public ToolFolderComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ToolFolderComponent(String componentId) {
		this();
		setId(componentId);
	}

	public ToolBarComponent getToolBar() {


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

	public IMenuComponent getMenu() {


		return MenuTools.getMenu(this);
		
	}

	public IMenuComponent getMenu(String menuId) {


		return MenuTools.getMenu(this, menuId);
		
	}

	public IMenuIterator listMenus() {


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

	public java.lang.String getVerticalAlignment() {
		return getVerticalAlignment(null);
	}

	/**
	 * See {@link #getVerticalAlignment() getVerticalAlignment()} for more details
	 */
	public java.lang.String getVerticalAlignment(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.VERTICAL_ALIGNMENT);
	}

	/**
	 * Returns <code>true</code> if the attribute "verticalAlignment" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isVerticalAlignmentSetted() {
		return getStateHelper().get(Properties.VERTICAL_ALIGNMENT)!=null;
	}

	public void setVerticalAlignment(java.lang.String verticalAlignment) {
		getStateHelper().put(Properties.VERTICAL_ALIGNMENT, verticalAlignment);
	}

	public java.lang.String getBorderType() {
		return getBorderType(null);
	}

	/**
	 * See {@link #getBorderType() getBorderType()} for more details
	 */
	public java.lang.String getBorderType(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.BORDER_TYPE);
	}

	/**
	 * Returns <code>true</code> if the attribute "borderType" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBorderTypeSetted() {
		return getStateHelper().get(Properties.BORDER_TYPE)!=null;
	}

	public void setBorderType(java.lang.String borderType) {
		getStateHelper().put(Properties.BORDER_TYPE, borderType);
	}

}
