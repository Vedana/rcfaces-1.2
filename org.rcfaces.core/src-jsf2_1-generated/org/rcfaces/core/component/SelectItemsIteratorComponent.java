package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.CameliaItemsComponent;
import org.rcfaces.core.internal.component.Properties;
import java.lang.Object;
import org.apache.commons.logging.LogFactory;
import java.lang.String;
import org.rcfaces.core.internal.manager.IClientDataManager;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.component.IDataMapAccessor;
import org.rcfaces.core.internal.manager.IServerDataManager;
import org.rcfaces.core.internal.tools.SelectItemsIteratorTools;
import javax.faces.FacesException;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import java.util.Set;
import java.util.Arrays;
import java.util.Collection;

public class SelectItemsIteratorComponent extends CameliaItemsComponent implements 
	IClientDataManager,
	IServerDataManager {

	private static final Log LOG = LogFactory.getLog(SelectItemsIteratorComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.selectItemsIterator";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=CameliaItemsComponent.BEHAVIOR_EVENT_NAMES;

	public SelectItemsIteratorComponent() {
		setRendererType(null);
	}

	public SelectItemsIteratorComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void setClientData(String name, ValueExpression value) {


		IDataMapAccessor dataMapAccessor=getDataMapAccessor(null, "clientData", true);
            
		dataMapAccessor.setData(name, value, null);
		
	}

	public String setClientData(String name, String value) {


		IDataMapAccessor dataMapAccessor=getDataMapAccessor(null, "clientData", true);
            
		return (String)dataMapAccessor.setData(name, value, null);
		
	}

	public void setServerData(String name, ValueExpression value) {


		IDataMapAccessor dataMapAccessor=getDataMapAccessor(null, "serverData", true);
            
		dataMapAccessor.setData(name, value, null);
		
	}

	public Object setServerData(String name, Object value) {


		IDataMapAccessor dataMapAccessor=getDataMapAccessor(null, "serverData", true);
            
		return dataMapAccessor.setData(name, value, null);
		
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


			return getComponentEngine().getDataMapAccessor(context, "clientData", false);
			
	}

	public IDataMapAccessor getServerMapAccessor(FacesContext context) {


			return getComponentEngine().getDataMapAccessor(context, "serverData", false);
			
	}

	public Object getItems() {
		return getItems(null);
	}

	public Object getItems(javax.faces.context.FacesContext facesContext) {
		return getStateHelper().eval(Properties.ITEMS);
	}

	public void setItems(Object items) {
		 getStateHelper().put(Properties.ITEMS, items);
	}

	/**
	 * Returns <code>true</code> if the attribute "items" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemsSetted() {
		return getStateHelper().get(Properties.ITEMS)!=null;
	}

	public String getVar() {
		return getVar(null);
	}

	public String getVar(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.VAR);
	}

	public void setVar(String var) {
		 getStateHelper().put(Properties.VAR, var);
	}

	/**
	 * Returns <code>true</code> if the attribute "var" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isVarSetted() {
		return getStateHelper().get(Properties.VAR)!=null;
	}

	public String getItemIndexVar() {
		return getItemIndexVar(null);
	}

	public String getItemIndexVar(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ITEM_INDEX_VAR);
	}

	public void setItemIndexVar(String itemIndexVar) {
		 getStateHelper().put(Properties.ITEM_INDEX_VAR, itemIndexVar);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemIndexVar" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemIndexVarSetted() {
		return getStateHelper().get(Properties.ITEM_INDEX_VAR)!=null;
	}

	public String getItemLabel() {
		return getItemLabel(null);
	}

	public String getItemLabel(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ITEM_LABEL);
	}

	public void setItemLabel(String itemLabel) {
		 getStateHelper().put(Properties.ITEM_LABEL, itemLabel);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemLabel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemLabelSetted() {
		return getStateHelper().get(Properties.ITEM_LABEL)!=null;
	}

	public Object getItemValue() {
		return getItemValue(null);
	}

	public Object getItemValue(javax.faces.context.FacesContext facesContext) {
		return getStateHelper().eval(Properties.ITEM_VALUE);
	}

	public void setItemValue(Object itemValue) {
		 getStateHelper().put(Properties.ITEM_VALUE, itemValue);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemValueSetted() {
		return getStateHelper().get(Properties.ITEM_VALUE)!=null;
	}

	public boolean isItemDisabled() {
		return isItemDisabled(null);
	}

	public boolean isItemDisabled(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.ITEM_DISABLED, false);
	}

	public void setItemDisabled(boolean itemDisabled) {
		 getStateHelper().put(Properties.ITEM_DISABLED, itemDisabled);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemDisabled" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemDisabledSetted() {
		return getStateHelper().get(Properties.ITEM_DISABLED)!=null;
	}

	public String getItemDescription() {
		return getItemDescription(null);
	}

	public String getItemDescription(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ITEM_DESCRIPTION);
	}

	public void setItemDescription(String itemDescription) {
		 getStateHelper().put(Properties.ITEM_DESCRIPTION, itemDescription);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemDescription" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemDescriptionSetted() {
		return getStateHelper().get(Properties.ITEM_DESCRIPTION)!=null;
	}

	public String getItemAccessKey() {
		return getItemAccessKey(null);
	}

	public String getItemAccessKey(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ITEM_ACCESS_KEY);
	}

	public void setItemAccessKey(String itemAccessKey) {
		 getStateHelper().put(Properties.ITEM_ACCESS_KEY, itemAccessKey);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemAccessKey" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemAccessKeySetted() {
		return getStateHelper().get(Properties.ITEM_ACCESS_KEY)!=null;
	}

	public String getItemAcceleratorKey() {
		return getItemAcceleratorKey(null);
	}

	public String getItemAcceleratorKey(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ITEM_ACCELERATOR_KEY);
	}

	public void setItemAcceleratorKey(String itemAcceleratorKey) {
		 getStateHelper().put(Properties.ITEM_ACCELERATOR_KEY, itemAcceleratorKey);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemAcceleratorKey" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemAcceleratorKeySetted() {
		return getStateHelper().get(Properties.ITEM_ACCELERATOR_KEY)!=null;
	}

	public String getItemGroupName() {
		return getItemGroupName(null);
	}

	public String getItemGroupName(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ITEM_GROUP_NAME);
	}

	public void setItemGroupName(String itemGroupName) {
		 getStateHelper().put(Properties.ITEM_GROUP_NAME, itemGroupName);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemGroupName" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemGroupNameSetted() {
		return getStateHelper().get(Properties.ITEM_GROUP_NAME)!=null;
	}

	public String getItemInputType() {
		return getItemInputType(null);
	}

	public String getItemInputType(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ITEM_INPUT_TYPE);
	}

	public void setItemInputType(String itemInputType) {
		 getStateHelper().put(Properties.ITEM_INPUT_TYPE, itemInputType);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemInputType" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemInputTypeSetted() {
		return getStateHelper().get(Properties.ITEM_INPUT_TYPE)!=null;
	}

	public String getItemStyleClass() {
		return getItemStyleClass(null);
	}

	public String getItemStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ITEM_STYLE_CLASS);
	}

	public void setItemStyleClass(String itemStyleClass) {
		 getStateHelper().put(Properties.ITEM_STYLE_CLASS, itemStyleClass);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemStyleClassSetted() {
		return getStateHelper().get(Properties.ITEM_STYLE_CLASS)!=null;
	}

	public String getItemImageURL() {
		return getItemImageURL(null);
	}

	public String getItemImageURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ITEM_IMAGE_URL);
	}

	public void setItemImageURL(String itemImageURL) {
		 getStateHelper().put(Properties.ITEM_IMAGE_URL, itemImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemImageURLSetted() {
		return getStateHelper().get(Properties.ITEM_IMAGE_URL)!=null;
	}

	public String getItemDisabledImageURL() {
		return getItemDisabledImageURL(null);
	}

	public String getItemDisabledImageURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ITEM_DISABLED_IMAGE_URL);
	}

	public void setItemDisabledImageURL(String itemDisabledImageURL) {
		 getStateHelper().put(Properties.ITEM_DISABLED_IMAGE_URL, itemDisabledImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemDisabledImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemDisabledImageURLSetted() {
		return getStateHelper().get(Properties.ITEM_DISABLED_IMAGE_URL)!=null;
	}

	public String getItemHoverImageURL() {
		return getItemHoverImageURL(null);
	}

	public String getItemHoverImageURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ITEM_HOVER_IMAGE_URL);
	}

	public void setItemHoverImageURL(String itemHoverImageURL) {
		 getStateHelper().put(Properties.ITEM_HOVER_IMAGE_URL, itemHoverImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemHoverImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemHoverImageURLSetted() {
		return getStateHelper().get(Properties.ITEM_HOVER_IMAGE_URL)!=null;
	}

	public String getItemSelectedImageURL() {
		return getItemSelectedImageURL(null);
	}

	public String getItemSelectedImageURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ITEM_SELECTED_IMAGE_URL);
	}

	public void setItemSelectedImageURL(String itemSelectedImageURL) {
		 getStateHelper().put(Properties.ITEM_SELECTED_IMAGE_URL, itemSelectedImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemSelectedImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemSelectedImageURLSetted() {
		return getStateHelper().get(Properties.ITEM_SELECTED_IMAGE_URL)!=null;
	}

	public String getItemExpandedImageURL() {
		return getItemExpandedImageURL(null);
	}

	public String getItemExpandedImageURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ITEM_EXPANDED_IMAGE_URL);
	}

	public void setItemExpandedImageURL(String itemExpandedImageURL) {
		 getStateHelper().put(Properties.ITEM_EXPANDED_IMAGE_URL, itemExpandedImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemExpandedImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemExpandedImageURLSetted() {
		return getStateHelper().get(Properties.ITEM_EXPANDED_IMAGE_URL)!=null;
	}

	public boolean isItemVisibility() {
		return isItemVisibility(null);
	}

	public boolean isItemVisibility(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.ITEM_VISIBILITY, false);
	}

	public void setItemVisibility(boolean itemVisibility) {
		 getStateHelper().put(Properties.ITEM_VISIBILITY, itemVisibility);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemVisibility" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemVisibilitySetted() {
		return getStateHelper().get(Properties.ITEM_VISIBILITY)!=null;
	}

}
