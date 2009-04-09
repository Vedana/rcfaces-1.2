package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.rcfaces.core.component.capability.ICheckedValuesCapability;
import org.rcfaces.core.internal.tools.CollectionTools.IComponentValueTypeCapability;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.internal.capability.ICheckComponent;
import java.util.Arrays;
import org.rcfaces.core.component.capability.IPreloadedLevelDepthCapability;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.internal.tools.CollectionTools.IComponentValueType;
import org.rcfaces.core.internal.component.CameliaSelectManyComponent;
import org.rcfaces.core.component.capability.ICheckEventCapability;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.component.iterator.IMenuItemIterator;
import org.rcfaces.core.internal.tools.MenuTools;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.capability.IMenuEventCapability;
import org.rcfaces.core.internal.tools.CheckTools;

/**
 * <p>The menu Component provides a way of creating desktop style menus on web pages. It allows sub-menus, check and radio menu items and image menus. It also provides pop-up menus.</p>
 * <p>The menu Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text, font &amp; images</li>
 * <li>Help</li>
 * <li>Visibility, Read-Only, Disabled</li>
 * <li>Events Handling</li>
 * </ul>
 * </p>
 */
public class MenuComponent extends CameliaSelectManyComponent implements 
	IPreloadedLevelDepthCapability,
	IMenuEventCapability,
	ISelectionEventCapability,
	ICheckEventCapability,
	ICheckedValuesCapability,
	ICheckComponent,
	IComponentValueTypeCapability,
	IMenuComponent {

	private static final Log LOG = LogFactory.getLog(MenuComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.menu";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaSelectManyComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"selectionListener","menuId","converter","checkedValues","checkListener","preloadedLevelDepth","removeAllWhenShown","menuListener"}));
	}

	public MenuComponent() {
		setRendererType(null);
	}

	public MenuComponent(String componentId) {
		this();
		setId(componentId);
	}

	public IMenuItemIterator listMenuItems() {


		return MenuTools.listMenuItems(this);
		
	}

	public IComponentValueType getComponentValueType() {


				return MenuTools.MENU_VALUE_TYPE;
			
	}

	public void check(Object value) {


				CheckTools.check(null, this, value);
			
	}

	public void checkAll() {


				CheckTools.checkAll(null, this);
			
	}

	public void uncheck(Object value) {


				CheckTools.uncheck(null, this, value);
			
	}

	public void uncheckAll() {


				CheckTools.uncheckAll(null, this);
			
	}

	public int getPreloadedLevelDepth() {
		return getPreloadedLevelDepth(null);
	}

	/**
	 * See {@link #getPreloadedLevelDepth() getPreloadedLevelDepth()} for more details
	 */
	public int getPreloadedLevelDepth(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.PRELOADED_LEVEL_DEPTH,-1, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "preloadedLevelDepth" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isPreloadedLevelDepthSetted() {
		return engine.isPropertySetted(Properties.PRELOADED_LEVEL_DEPTH);
	}

	public void setPreloadedLevelDepth(int preloadedLevelDepth) {
		engine.setProperty(Properties.PRELOADED_LEVEL_DEPTH, preloadedLevelDepth);
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

	public java.lang.Object getCheckedValues() {
		return getCheckedValues(null);
	}

	/**
	 * See {@link #getCheckedValues() getCheckedValues()} for more details
	 */
	public java.lang.Object getCheckedValues(javax.faces.context.FacesContext facesContext) {
		return engine.getProperty(Properties.CHECKED_VALUES, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "checkedValues" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCheckedValuesSetted() {
		return engine.isPropertySetted(Properties.CHECKED_VALUES);
	}

	public void setCheckedValues(java.lang.Object checkedValues) {
		engine.setProperty(Properties.CHECKED_VALUES, checkedValues);
	}

	/**
	 * Return the type of the property represented by the {@link ValueExpression}, relative to the specified {@link javax.faces.context.FacesContext}.
	 */
	public Class getCheckedValuesType(javax.faces.context.FacesContext facesContext) {
		ValueExpression valueExpression=engine.getValueExpressionProperty(Properties.CHECKED_VALUES);
		if (valueExpression==null) {
			return null;
		}
		if (facesContext==null) {
			facesContext=javax.faces.context.FacesContext.getCurrentInstance();
		}
		return valueExpression.getType(facesContext.getELContext());
	}

	public int getCheckedValuesCount() {


			return CheckTools.getCount(getCheckedValues());
		
	}

	public Object[] listCheckedValues() {


			return CheckTools.listValues(getCheckedValues(), getValue());
		
	}

	public Object getFirstCheckedValue() {


			return CheckTools.getFirst(getCheckedValues(), getValue());
		
	}

	/**
	 * Returns an id used to retreive a specific menu. For example, "#node" is used to call a menu on a tree node while "#body" is used for the menu boody.
	 * @return menu Id
	 */
	public String getMenuId() {
		return getMenuId(null);
	}

	/**
	 * Returns an id used to retreive a specific menu. For example, "#node" is used to call a menu on a tree node while "#body" is used for the menu boody.
	 * @return menu Id
	 */
	public String getMenuId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MENU_ID, facesContext);
	}

	/**
	 * Sets an id used to retreive a specific menu. For example, "#node" is used to call a menu on a tree node while "#body" is used for the menu boody.
	 * @param menuId menu Id
	 */
	public void setMenuId(String menuId) {
		engine.setProperty(Properties.MENU_ID, menuId);
	}

	/**
	 * Sets an id used to retreive a specific menu. For example, "#node" is used to call a menu on a tree node while "#body" is used for the menu boody.
	 * @param menuId menu Id
	 */
	/**
	 * Returns <code>true</code> if the attribute "menuId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isMenuIdSetted() {
		return engine.isPropertySetted(Properties.MENU_ID);
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
