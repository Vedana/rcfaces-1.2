package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IMenuCapability;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.converter.HiddenModeConverter;
import java.util.Arrays;
import org.rcfaces.core.component.capability.IHorizontalTextPositionCapability;
import org.rcfaces.core.component.capability.IShowDropDownMarkCapability;
import org.rcfaces.core.component.capability.ITextPositionCapability;
import org.rcfaces.core.internal.tools.ToolBarTools;
import org.rcfaces.core.component.capability.ICheckEventCapability;
import org.rcfaces.core.component.AbstractInputComponent;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.internal.tools.CheckTools;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.component.capability.IDoubleClickEventCapability;
import org.rcfaces.core.component.capability.IMouseEventCapability;
import java.lang.String;
import javax.faces.convert.Converter;
import org.rcfaces.core.component.capability.ICheckedValuesCapability;
import org.rcfaces.core.component.capability.IDisabledCapability;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.capability.IInitEventCapability;
import org.rcfaces.core.component.ToolBarComponent;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IBorderTypeCapability;
import org.rcfaces.core.component.capability.IVerticalAlignmentCapability;
import org.rcfaces.core.internal.converter.TextPositionConverter;
import org.apache.commons.logging.Log;
import org.rcfaces.core.internal.tools.MenuTools;
import javax.faces.component.NamingContainer;
import org.rcfaces.core.component.capability.IReadOnlyCapability;

public class ItemsToolFolderComponent extends AbstractInputComponent implements 
	IInitEventCapability,
	IMenuCapability,
	IMouseEventCapability,
	IDoubleClickEventCapability,
	ITextPositionCapability,
	IBorderTypeCapability,
	ISelectionEventCapability,
	ICheckEventCapability,
	ICheckedValuesCapability,
	IDisabledCapability,
	IReadOnlyCapability,
	IVerticalAlignmentCapability,
	IShowDropDownMarkCapability,
	NamingContainer {

	private static final Log LOG = LogFactory.getLog(ItemsToolFolderComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.itemsToolFolder";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractInputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"selectionListener","verticalAlignment","disabled","doubleClickListener","mouseOverListener","itemHiddenMode","showDropDownMark","checkListener","checkedValues","initListener","mouseOutListener","borderType","readOnly","textPosition"}));
	}

	public ItemsToolFolderComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ItemsToolFolderComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void setItemHiddenMode(String hiddenMode) {


			setItemHiddenMode(((Integer)HiddenModeConverter.SINGLETON.getAsObject(null, this, hiddenMode)).intValue());
		
	}

	protected Converter getTextPositionConverter() {


				return TextPositionConverter.SINGLETON;
			
	}

	public void setTextPosition(String textPosition) {


			setTextPosition(((Integer)getTextPositionConverter().getAsObject(null, this, textPosition)).intValue());
		
	}

	public ToolBarComponent getToolBar() {


		return (ToolBarComponent)getParent();
		
	}

	public IMenuComponent getMenu(String menuId) {


		return MenuTools.getMenu(this, menuId);
		
	}

	public IMenuComponent getMenu() {


		return MenuTools.getMenu(this);
		
	}

	public IMenuIterator listMenus() {


		return MenuTools.listMenus(this);
		
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

	public int getTextPosition() {
		return getTextPosition(null);
	}

	/**
	 * See {@link #getTextPosition() getTextPosition()} for more details
	 */
	public int getTextPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.TEXT_POSITION,IHorizontalTextPositionCapability.DEFAULT_POSITION, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "textPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextPositionSetted() {
		return engine.isPropertySetted(Properties.TEXT_POSITION);
	}

	public void setTextPosition(int textPosition) {
		engine.setProperty(Properties.TEXT_POSITION, textPosition);
	}

	public java.lang.String getBorderType() {
		return getBorderType(null);
	}

	/**
	 * See {@link #getBorderType() getBorderType()} for more details
	 */
	public java.lang.String getBorderType(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BORDER_TYPE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "borderType" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBorderTypeSetted() {
		return engine.isPropertySetted(Properties.BORDER_TYPE);
	}

	public void setBorderType(java.lang.String borderType) {
		engine.setProperty(Properties.BORDER_TYPE, borderType);
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
	 * Return the type of the property represented by the {@link ValueBinding}, relative to the specified {@link javax.faces.context.FacesContext}.
	 */
	public Class getCheckedValuesType(javax.faces.context.FacesContext facesContext) {
		ValueBinding valueBinding=engine.getValueBindingProperty(Properties.CHECKED_VALUES);
		if (valueBinding==null) {
			return null;
		}
		if (facesContext==null) {
			facesContext=javax.faces.context.FacesContext.getCurrentInstance();
		}
		return valueBinding.getType(facesContext);
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

	public boolean isReadOnly() {
		return isReadOnly(null);
	}

	/**
	 * See {@link #isReadOnly() isReadOnly()} for more details
	 */
	public boolean isReadOnly(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.READ_ONLY, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "readOnly" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isReadOnlySetted() {
		return engine.isPropertySetted(Properties.READ_ONLY);
	}

	public void setReadOnly(boolean readOnly) {
		engine.setProperty(Properties.READ_ONLY, readOnly);
	}

	public java.lang.String getVerticalAlignment() {
		return getVerticalAlignment(null);
	}

	/**
	 * See {@link #getVerticalAlignment() getVerticalAlignment()} for more details
	 */
	public java.lang.String getVerticalAlignment(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.VERTICAL_ALIGNMENT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "verticalAlignment" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isVerticalAlignmentSetted() {
		return engine.isPropertySetted(Properties.VERTICAL_ALIGNMENT);
	}

	public void setVerticalAlignment(java.lang.String verticalAlignment) {
		engine.setProperty(Properties.VERTICAL_ALIGNMENT, verticalAlignment);
	}

	public boolean isShowDropDownMark() {
		return isShowDropDownMark(null);
	}

	/**
	 * See {@link #isShowDropDownMark() isShowDropDownMark()} for more details
	 */
	public boolean isShowDropDownMark(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.SHOW_DROP_DOWN_MARK, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "showDropDownMark" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isShowDropDownMarkSetted() {
		return engine.isPropertySetted(Properties.SHOW_DROP_DOWN_MARK);
	}

	public void setShowDropDownMark(boolean showDropDownMark) {
		engine.setProperty(Properties.SHOW_DROP_DOWN_MARK, showDropDownMark);
	}

	public int getItemHiddenMode() {
		return getItemHiddenMode(null);
	}

	public int getItemHiddenMode(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.ITEM_HIDDEN_MODE, 0, facesContext);
	}

	public void setItemHiddenMode(int itemHiddenMode) {
		engine.setProperty(Properties.ITEM_HIDDEN_MODE, itemHiddenMode);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemHiddenMode" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemHiddenModeSetted() {
		return engine.isPropertySetted(Properties.ITEM_HIDDEN_MODE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
