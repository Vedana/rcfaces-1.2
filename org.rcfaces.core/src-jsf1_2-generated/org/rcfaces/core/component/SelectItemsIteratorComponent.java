package org.rcfaces.core.component;

import java.lang.String;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import java.lang.Object;
import javax.faces.context.FacesContext;
import javax.faces.FacesException;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.internal.component.IDataMapAccessor;
import org.rcfaces.core.internal.tools.SelectItemsIteratorTools;
import org.rcfaces.core.internal.component.CameliaItemsComponent;
import org.rcfaces.core.internal.manager.IClientDataManager;
import org.rcfaces.core.internal.manager.IServerDataManager;

public class SelectItemsIteratorComponent extends CameliaItemsComponent implements 
	IServerDataManager,
	IClientDataManager {

	public static final String COMPONENT_TYPE="org.rcfaces.core.selectItemsIterator";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaItemsComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"var","itemSelectedImageURL","itemVisibility","itemDescription","itemAcceleratorKey","itemLabel","itemDisabledImageURL","itemValue","itemInputType","itemHoverImageURL","items","itemExpandedImageURL","itemIndexVar","itemImageURL","itemAccessKey","itemDisabled","itemStyleClass","itemGroupName"}));
	}

	public SelectItemsIteratorComponent() {
		setRendererType(null);
	}

	public SelectItemsIteratorComponent(String componentId) {
		this();
		setId(componentId);
	}

	public String setClientData(String name, String value) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "clientData", true);
            
		return (String)dataMapAccessor.setData(name, value, null);
		
	}

	public void setClientData(String name, ValueExpression value) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "clientData", true);
            
		dataMapAccessor.setData(name, value, null);
		
	}

	public Object setServerData(String name, Object value) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "serverData", true);
            
		return dataMapAccessor.setData(name, value, null);
		
	}

	public void setServerData(String name, ValueExpression value) {


		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(null, "serverData", true);
            
		dataMapAccessor.setData(name, value, null);
		
	}

	public void setValue(Object value) {


			throw new FacesException("Can not set value of 'SelectItemsIterator', use 'items' attribute to set the collection.");
			
	}

	public Object getValue() {


			return getValue(null);
			
	}

	public Object getValue(FacesContext context) {


			return SelectItemsIteratorTools.getValue(this, context);
			
	}

	public IDataMapAccessor getClientMapAccessor(FacesContext context) {


			return engine.getDataMapAccessor(context, "clientData", false);
			
	}

	public IDataMapAccessor getServerMapAccessor(FacesContext context) {


			return engine.getDataMapAccessor(context, "serverData", false);
			
	}

	public Object getItems() {
		return getItems(null);
	}

	public Object getItems(javax.faces.context.FacesContext facesContext) {
		return engine.getValue(Properties.ITEMS, facesContext);
	}

	public void setItems(Object items) {
		engine.setValue(Properties.ITEMS, items);
	}

	/**
	 * Returns <code>true</code> if the attribute "items" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemsSetted() {
		return engine.isPropertySetted(Properties.ITEMS);
	}

	public String getVar() {
		return getVar(null);
	}

	public String getVar(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.VAR, facesContext);
	}

	public void setVar(String var) {
		engine.setProperty(Properties.VAR, var);
	}

	/**
	 * Returns <code>true</code> if the attribute "var" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isVarSetted() {
		return engine.isPropertySetted(Properties.VAR);
	}

	public String getItemIndexVar() {
		return getItemIndexVar(null);
	}

	public String getItemIndexVar(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ITEM_INDEX_VAR, facesContext);
	}

	public void setItemIndexVar(String itemIndexVar) {
		engine.setProperty(Properties.ITEM_INDEX_VAR, itemIndexVar);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemIndexVar" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemIndexVarSetted() {
		return engine.isPropertySetted(Properties.ITEM_INDEX_VAR);
	}

	public String getItemLabel() {
		return getItemLabel(null);
	}

	public String getItemLabel(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ITEM_LABEL, facesContext);
	}

	public void setItemLabel(String itemLabel) {
		engine.setProperty(Properties.ITEM_LABEL, itemLabel);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemLabel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemLabelSetted() {
		return engine.isPropertySetted(Properties.ITEM_LABEL);
	}

	public Object getItemValue() {
		return getItemValue(null);
	}

	public Object getItemValue(javax.faces.context.FacesContext facesContext) {
		return engine.getValue(Properties.ITEM_VALUE, facesContext);
	}

	public void setItemValue(Object itemValue) {
		engine.setValue(Properties.ITEM_VALUE, itemValue);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemValueSetted() {
		return engine.isPropertySetted(Properties.ITEM_VALUE);
	}

	public boolean isItemDisabled() {
		return isItemDisabled(null);
	}

	public boolean isItemDisabled(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.ITEM_DISABLED, false, facesContext);
	}

	public void setItemDisabled(boolean itemDisabled) {
		engine.setProperty(Properties.ITEM_DISABLED, itemDisabled);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemDisabled" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemDisabledSetted() {
		return engine.isPropertySetted(Properties.ITEM_DISABLED);
	}

	public String getItemDescription() {
		return getItemDescription(null);
	}

	public String getItemDescription(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ITEM_DESCRIPTION, facesContext);
	}

	public void setItemDescription(String itemDescription) {
		engine.setProperty(Properties.ITEM_DESCRIPTION, itemDescription);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemDescription" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemDescriptionSetted() {
		return engine.isPropertySetted(Properties.ITEM_DESCRIPTION);
	}

	public String getItemAccessKey() {
		return getItemAccessKey(null);
	}

	public String getItemAccessKey(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ITEM_ACCESS_KEY, facesContext);
	}

	public void setItemAccessKey(String itemAccessKey) {
		engine.setProperty(Properties.ITEM_ACCESS_KEY, itemAccessKey);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemAccessKey" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemAccessKeySetted() {
		return engine.isPropertySetted(Properties.ITEM_ACCESS_KEY);
	}

	public String getItemAcceleratorKey() {
		return getItemAcceleratorKey(null);
	}

	public String getItemAcceleratorKey(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ITEM_ACCELERATOR_KEY, facesContext);
	}

	public void setItemAcceleratorKey(String itemAcceleratorKey) {
		engine.setProperty(Properties.ITEM_ACCELERATOR_KEY, itemAcceleratorKey);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemAcceleratorKey" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemAcceleratorKeySetted() {
		return engine.isPropertySetted(Properties.ITEM_ACCELERATOR_KEY);
	}

	public String getItemGroupName() {
		return getItemGroupName(null);
	}

	public String getItemGroupName(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ITEM_GROUP_NAME, facesContext);
	}

	public void setItemGroupName(String itemGroupName) {
		engine.setProperty(Properties.ITEM_GROUP_NAME, itemGroupName);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemGroupName" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemGroupNameSetted() {
		return engine.isPropertySetted(Properties.ITEM_GROUP_NAME);
	}

	public String getItemInputType() {
		return getItemInputType(null);
	}

	public String getItemInputType(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ITEM_INPUT_TYPE, facesContext);
	}

	public void setItemInputType(String itemInputType) {
		engine.setProperty(Properties.ITEM_INPUT_TYPE, itemInputType);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemInputType" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemInputTypeSetted() {
		return engine.isPropertySetted(Properties.ITEM_INPUT_TYPE);
	}

	public String getItemStyleClass() {
		return getItemStyleClass(null);
	}

	public String getItemStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ITEM_STYLE_CLASS, facesContext);
	}

	public void setItemStyleClass(String itemStyleClass) {
		engine.setProperty(Properties.ITEM_STYLE_CLASS, itemStyleClass);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemStyleClassSetted() {
		return engine.isPropertySetted(Properties.ITEM_STYLE_CLASS);
	}

	public String getItemImageURL() {
		return getItemImageURL(null);
	}

	public String getItemImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ITEM_IMAGE_URL, facesContext);
	}

	public void setItemImageURL(String itemImageURL) {
		engine.setProperty(Properties.ITEM_IMAGE_URL, itemImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemImageURLSetted() {
		return engine.isPropertySetted(Properties.ITEM_IMAGE_URL);
	}

	public String getItemDisabledImageURL() {
		return getItemDisabledImageURL(null);
	}

	public String getItemDisabledImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ITEM_DISABLED_IMAGE_URL, facesContext);
	}

	public void setItemDisabledImageURL(String itemDisabledImageURL) {
		engine.setProperty(Properties.ITEM_DISABLED_IMAGE_URL, itemDisabledImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemDisabledImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemDisabledImageURLSetted() {
		return engine.isPropertySetted(Properties.ITEM_DISABLED_IMAGE_URL);
	}

	public String getItemHoverImageURL() {
		return getItemHoverImageURL(null);
	}

	public String getItemHoverImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ITEM_HOVER_IMAGE_URL, facesContext);
	}

	public void setItemHoverImageURL(String itemHoverImageURL) {
		engine.setProperty(Properties.ITEM_HOVER_IMAGE_URL, itemHoverImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemHoverImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemHoverImageURLSetted() {
		return engine.isPropertySetted(Properties.ITEM_HOVER_IMAGE_URL);
	}

	public String getItemSelectedImageURL() {
		return getItemSelectedImageURL(null);
	}

	public String getItemSelectedImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ITEM_SELECTED_IMAGE_URL, facesContext);
	}

	public void setItemSelectedImageURL(String itemSelectedImageURL) {
		engine.setProperty(Properties.ITEM_SELECTED_IMAGE_URL, itemSelectedImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemSelectedImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemSelectedImageURLSetted() {
		return engine.isPropertySetted(Properties.ITEM_SELECTED_IMAGE_URL);
	}

	public String getItemExpandedImageURL() {
		return getItemExpandedImageURL(null);
	}

	public String getItemExpandedImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ITEM_EXPANDED_IMAGE_URL, facesContext);
	}

	public void setItemExpandedImageURL(String itemExpandedImageURL) {
		engine.setProperty(Properties.ITEM_EXPANDED_IMAGE_URL, itemExpandedImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemExpandedImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemExpandedImageURLSetted() {
		return engine.isPropertySetted(Properties.ITEM_EXPANDED_IMAGE_URL);
	}

	public boolean isItemVisibility() {
		return isItemVisibility(null);
	}

	public boolean isItemVisibility(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.ITEM_VISIBILITY, false, facesContext);
	}

	public void setItemVisibility(boolean itemVisibility) {
		engine.setProperty(Properties.ITEM_VISIBILITY, itemVisibility);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemVisibility" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemVisibilitySetted() {
		return engine.isPropertySetted(Properties.ITEM_VISIBILITY);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
