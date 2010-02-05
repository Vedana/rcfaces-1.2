package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.converter.HiddenModeConverter;
import java.util.Arrays;
import org.rcfaces.core.component.capability.IHorizontalTextPositionCapability;
import org.rcfaces.core.component.capability.ITextPositionCapability;
import org.rcfaces.core.internal.tools.ToolBarTools;
import org.rcfaces.core.internal.converter.InputTypeConverter;
import org.rcfaces.core.component.capability.ICheckEventCapability;
import org.rcfaces.core.component.AbstractInputComponent;
import org.rcfaces.core.internal.tools.CheckTools;
import org.rcfaces.core.component.capability.IDoubleClickEventCapability;
import org.rcfaces.core.component.capability.IMouseEventCapability;
import java.lang.String;
import javax.el.ValueExpression;
import javax.faces.convert.Converter;
import org.rcfaces.core.component.capability.ICheckedValuesCapability;
import org.rcfaces.core.component.capability.IDisabledCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.capability.IInitEventCapability;
import org.rcfaces.core.component.ToolBarComponent;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IBorderTypeCapability;
import org.rcfaces.core.internal.converter.TextPositionConverter;
import org.apache.commons.logging.Log;
import javax.faces.component.NamingContainer;
import org.rcfaces.core.component.capability.IReadOnlyCapability;

public class ItemsListComponent extends AbstractInputComponent implements 
	IInitEventCapability,
	IMouseEventCapability,
	IDoubleClickEventCapability,
	ITextPositionCapability,
	IBorderTypeCapability,
	ISelectionEventCapability,
	ICheckEventCapability,
	ICheckedValuesCapability,
	IDisabledCapability,
	IReadOnlyCapability,
	NamingContainer {

	private static final Log LOG = LogFactory.getLog(ItemsListComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.itemsList";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractInputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"defaultDisabledImageURL","selectionListener","defaultImageURL","disabled","doubleClickListener","mouseOverListener","defaultItemGroupName","itemHiddenMode","defaultItemStyleClass","defaultItemInputType","itemPadding","defaultHoverImageURL","checkListener","checkedValues","defaultSelectedImageURL","initListener","defaultItemLookId","mouseOutListener","borderType","readOnly","textPosition"}));
	}

	public ItemsListComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ItemsListComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void setItemHiddenMode(String hiddenMode) {


			setItemHiddenMode(((Integer)HiddenModeConverter.SINGLETON.getAsObject(null, this, hiddenMode)).intValue());
		
	}

	protected Converter getTextPositionConverter() {


				return TextPositionConverter.SINGLETON;
			
	}

	public void setDefaultItemInputType(String inputType) {


			setDefaultItemInputType(((Integer)InputTypeConverter.SINGLETON.getAsObject(null, this, inputType)).intValue());
		
	}

	public void setTextPosition(String textPosition) {


			setTextPosition(((Integer)getTextPositionConverter().getAsObject(null, this, textPosition)).intValue());
		
	}

	public ToolBarComponent getToolBar() {


		return (ToolBarComponent)getParent();
		
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

	/**
	 * Returns an url string pointing to the default image.
	 * @return image url
	 */
	public String getDefaultImageURL() {
		return getDefaultImageURL(null);
	}

	/**
	 * Returns an url string pointing to the default image.
	 * @return image url
	 */
	public String getDefaultImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_IMAGE_URL, facesContext);
	}

	/**
	 * Sets an url string pointing to the default image.
	 * @param defaultImageURL image url
	 */
	public void setDefaultImageURL(String defaultImageURL) {
		engine.setProperty(Properties.DEFAULT_IMAGE_URL, defaultImageURL);
	}

	/**
	 * Sets an url string pointing to the default image.
	 * @param defaultImageURL image url
	 */
	/**
	 * Returns <code>true</code> if the attribute "defaultImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDefaultImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_IMAGE_URL);
	}

	/**
	 * Returns an url string pointing to the default image for the selected state.
	 * @return selected image url
	 */
	public String getDefaultSelectedImageURL() {
		return getDefaultSelectedImageURL(null);
	}

	/**
	 * Returns an url string pointing to the default image for the selected state.
	 * @return selected image url
	 */
	public String getDefaultSelectedImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_SELECTED_IMAGE_URL, facesContext);
	}

	/**
	 * Sets an url string pointing to the default image for the selected state.
	 * @param defaultSelectedImageURL selected image url
	 */
	public void setDefaultSelectedImageURL(String defaultSelectedImageURL) {
		engine.setProperty(Properties.DEFAULT_SELECTED_IMAGE_URL, defaultSelectedImageURL);
	}

	/**
	 * Sets an url string pointing to the default image for the selected state.
	 * @param defaultSelectedImageURL selected image url
	 */
	/**
	 * Returns <code>true</code> if the attribute "defaultSelectedImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDefaultSelectedImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_SELECTED_IMAGE_URL);
	}

	public String getDefaultHoverImageURL() {
		return getDefaultHoverImageURL(null);
	}

	public String getDefaultHoverImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_HOVER_IMAGE_URL, facesContext);
	}

	public void setDefaultHoverImageURL(String defaultHoverImageURL) {
		engine.setProperty(Properties.DEFAULT_HOVER_IMAGE_URL, defaultHoverImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "defaultHoverImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDefaultHoverImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_HOVER_IMAGE_URL);
	}

	/**
	 * Returns an url string pointing to the default image for the disabled state.
	 * @return disabled image url
	 */
	public String getDefaultDisabledImageURL() {
		return getDefaultDisabledImageURL(null);
	}

	/**
	 * Returns an url string pointing to the default image for the disabled state.
	 * @return disabled image url
	 */
	public String getDefaultDisabledImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_DISABLED_IMAGE_URL, facesContext);
	}

	/**
	 * Sets an url string pointing to the default image for the disabled state.
	 * @param defaultDisabledImageURL disabled image url
	 */
	public void setDefaultDisabledImageURL(String defaultDisabledImageURL) {
		engine.setProperty(Properties.DEFAULT_DISABLED_IMAGE_URL, defaultDisabledImageURL);
	}

	/**
	 * Sets an url string pointing to the default image for the disabled state.
	 * @param defaultDisabledImageURL disabled image url
	 */
	/**
	 * Returns <code>true</code> if the attribute "defaultDisabledImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDefaultDisabledImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_DISABLED_IMAGE_URL);
	}

	public int getDefaultItemInputType() {
		return getDefaultItemInputType(null);
	}

	public int getDefaultItemInputType(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.DEFAULT_ITEM_INPUT_TYPE, 0, facesContext);
	}

	public void setDefaultItemInputType(int defaultItemInputType) {
		engine.setProperty(Properties.DEFAULT_ITEM_INPUT_TYPE, defaultItemInputType);
	}

	/**
	 * Returns <code>true</code> if the attribute "defaultItemInputType" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDefaultItemInputTypeSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_ITEM_INPUT_TYPE);
	}

	public String getDefaultItemLookId() {
		return getDefaultItemLookId(null);
	}

	public String getDefaultItemLookId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_ITEM_LOOK_ID, facesContext);
	}

	public void setDefaultItemLookId(String defaultItemLookId) {
		engine.setProperty(Properties.DEFAULT_ITEM_LOOK_ID, defaultItemLookId);
	}

	/**
	 * Returns <code>true</code> if the attribute "defaultItemLookId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDefaultItemLookIdSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_ITEM_LOOK_ID);
	}

	public String getDefaultItemStyleClass() {
		return getDefaultItemStyleClass(null);
	}

	public String getDefaultItemStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_ITEM_STYLE_CLASS, facesContext);
	}

	public void setDefaultItemStyleClass(String defaultItemStyleClass) {
		engine.setProperty(Properties.DEFAULT_ITEM_STYLE_CLASS, defaultItemStyleClass);
	}

	/**
	 * Returns <code>true</code> if the attribute "defaultItemStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDefaultItemStyleClassSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_ITEM_STYLE_CLASS);
	}

	public String getDefaultItemGroupName() {
		return getDefaultItemGroupName(null);
	}

	public String getDefaultItemGroupName(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_ITEM_GROUP_NAME, facesContext);
	}

	public void setDefaultItemGroupName(String defaultItemGroupName) {
		engine.setProperty(Properties.DEFAULT_ITEM_GROUP_NAME, defaultItemGroupName);
	}

	/**
	 * Returns <code>true</code> if the attribute "defaultItemGroupName" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDefaultItemGroupNameSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_ITEM_GROUP_NAME);
	}

	public int getItemPadding() {
		return getItemPadding(null);
	}

	public int getItemPadding(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.ITEM_PADDING, 0, facesContext);
	}

	public void setItemPadding(int itemPadding) {
		engine.setProperty(Properties.ITEM_PADDING, itemPadding);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemPadding" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemPaddingSetted() {
		return engine.isPropertySetted(Properties.ITEM_PADDING);
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
