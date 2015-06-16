package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import java.util.Map;
import java.util.Collections;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IRequiredCapability;
import org.rcfaces.core.component.capability.IBorderCapability;
import java.util.HashMap;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.GridTools;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.converter.FilterPropertiesConverter;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.apache.commons.logging.Log;
import java.util.Set;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.capability.IEditableCapability;
import java.lang.String;
import org.rcfaces.core.component.AbstractGridComponent;
import org.rcfaces.core.component.iterator.IColumnIterator;
import org.rcfaces.core.component.capability.IEmptyDataMessageCapability;
import org.rcfaces.core.component.capability.IEmptyMessageCapability;
import org.rcfaces.core.internal.component.IDataMapAccessor;
import org.rcfaces.core.internal.capability.IGridComponent;
import javax.el.ValueExpression;
import org.rcfaces.core.component.capability.IDisabledCapability;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.component.capability.IMaxTextLengthCapability;
import org.rcfaces.core.model.IFilterProperties;
import java.util.Arrays;
import org.rcfaces.core.internal.manager.IValidationParameters;

public class KeyEntryComponent extends AbstractGridComponent implements 
	IEmptyMessageCapability,
	IEmptyDataMessageCapability,
	ISelectionEventCapability,
	IDisabledCapability,
	IRequiredCapability,
	IReadOnlyCapability,
	IBorderCapability,
	IMaxTextLengthCapability,
	IEditableCapability,
	IFilterCapability,
	IGridComponent,
	IValidationParameters {

	private static final Log LOG = LogFactory.getLog(KeyEntryComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.keyEntry";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractGridComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"noValueFormatLabel","filterProperties","suggestionMinChars","valueFormat","valueFormatDescription","valueFormatTooltip","rowCountVar","valueFormatLabel","emptyDataMessage","forceValidation","editable","emptyMessage","selectionListener","maxTextLength","readOnly","labelColumnId","forLabel","suggestionDelayMs","selectedValue","valueColumnId","required","border","rowIndexVar","disabled"}));
	}

	public KeyEntryComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public KeyEntryComponent(String componentId) {
		this();
		setId(componentId);
	}

	public int getValidationParametersCount() {

		 
		 return getValidationParametersCount(null);
		
	}

	public boolean isClientSideValidationParameter(String name) {


		return isClientSideValidationParameter(name, null);
		
	}

	public Map getValidationParametersMap() {


		return getValidationParametersMap(null);
		
	}

	public void setValidationParameter(String name, ValueExpression value, boolean client) {


		setValidationParameterData(name, value, client);
		
	}

	public String getValidationParameter(String name) {


		 return getValidationParameter(name, null);
		
	}

	public Map getClientValidationParametersMap() {


		return getClientValidationParametersMap(null);
		
	}

	public String removeValidationParameter(String name) {


		FacesContext facesContext=getFacesContext();

		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return null;
		}
 
 		IDataMapAccessor clientMapAccessor=engine.getDataMapAccessor(facesContext, "ClientValidationParameter", false);
		if (clientMapAccessor!=null) {
			clientMapAccessor.removeData(name, facesContext);
		}
            
		return (String)dataMapAccessor.removeData(name, facesContext);
		
	}

	public String setValidationParameter(String name, String value, boolean client) {


		return (String)setValidationParameterData(name, value, client);
		
	}

	public IColumnIterator listColumns() {


				return GridTools.listColumns(this, javax.faces.component.UIColumn.class);
			
	}

	public IColumnIterator listOrderedVisibledColumns() {


				return GridTools.listOrderedVisibledColumns(this,
				javax.faces.component.UIColumn.class);
			
	}

	public void setFilterProperties(String properties) {


			IFilterProperties filterProperties=(IFilterProperties)FilterPropertiesConverter.SINGLETON.getAsObject(null, this, properties);
			
			setFilterProperties(filterProperties);
		
	}

	public String getValidationParameter(String name, FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}

		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return null;
		}
            
		return (String)dataMapAccessor.getData(name, facesContext);
		
	}

	public int getValidationParametersCount(FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}
		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return 0;
		}
		 
		return dataMapAccessor.getDataCount();
		
	}

	public Map getValidationParametersMap(FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}
		
		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return Collections.EMPTY_MAP;
		}
            
		return dataMapAccessor.getDataMap(facesContext);
		
	}

	public Map getClientValidationParametersMap(FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}
		
		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return Collections.EMPTY_MAP;
		}
            
		Map map=dataMapAccessor.getDataMap(facesContext);
		if (map.isEmpty()) {
			return Collections.EMPTY_MAP;
		}
		
		IDataMapAccessor clientMapAccessor=engine.getDataMapAccessor(facesContext, "ClientValidationParameter", false);
		if (clientMapAccessor==null) {
			if (Constants.READ_ONLY_COLLECTION_LOCK_ENABLED) {
				map=Collections.unmodifiableMap(map);
			}
			return map;
		}
		
		Map client=clientMapAccessor.getDataMap(facesContext);
		if (client==null || client.isEmpty()) {
		
			if (Constants.READ_ONLY_COLLECTION_LOCK_ENABLED) {
				map=Collections.unmodifiableMap(map);
			}
			return map;
		}
		
		Map fmap=new HashMap(map);
		if (map.keySet().removeAll(client.keySet())==false) {
			if (Constants.READ_ONLY_COLLECTION_LOCK_ENABLED) {
				map=Collections.unmodifiableMap(map);
			}
			return map;
		}
		
		if (fmap.isEmpty()) {
			return Collections.EMPTY_MAP;
		}
		
		if (Constants.READ_ONLY_COLLECTION_LOCK_ENABLED) {
			fmap=Collections.unmodifiableMap(fmap);
		}
		
		return fmap;
		
	}

	private Object setValidationParameterData(String name, Object value, boolean client) {


		FacesContext facesContext=getFacesContext();
		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(facesContext, "ValidationParameter", true);
		if (client) {
			// On retire la limitation au niveau client si besoin !
			IDataMapAccessor clientMapAccessor=engine.getDataMapAccessor(facesContext, "ClientValidationParameter", false);
			if (clientMapAccessor!=null) {
				clientMapAccessor.removeData(name, facesContext);
			}
		} else {
			IDataMapAccessor clientMapAccessor=engine.getDataMapAccessor(facesContext, "ClientValidationParameter", true);
			clientMapAccessor.setData(name, Boolean.FALSE, facesContext);
		}
            
		return dataMapAccessor.setData(name, value, facesContext);
		
	}

	public boolean isClientSideValidationParameter(String name, FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}
		
		IDataMapAccessor clientMapAccessor=engine.getDataMapAccessor(facesContext, "ClientValidationParameter", false);
		if (clientMapAccessor==null) {
			return false;
		}
		return (clientMapAccessor.getData(name, facesContext)==null);
		
	}

	public java.lang.String getEmptyMessage() {
		return getEmptyMessage(null);
	}

	/**
	 * See {@link #getEmptyMessage() getEmptyMessage()} for more details
	 */
	public java.lang.String getEmptyMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.EMPTY_MESSAGE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "emptyMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isEmptyMessageSetted() {
		return engine.isPropertySetted(Properties.EMPTY_MESSAGE);
	}

	public void setEmptyMessage(java.lang.String emptyMessage) {
		engine.setProperty(Properties.EMPTY_MESSAGE, emptyMessage);
	}

	public java.lang.String getEmptyDataMessage() {
		return getEmptyDataMessage(null);
	}

	/**
	 * See {@link #getEmptyDataMessage() getEmptyDataMessage()} for more details
	 */
	public java.lang.String getEmptyDataMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.EMPTY_DATA_MESSAGE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "emptyDataMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isEmptyDataMessageSetted() {
		return engine.isPropertySetted(Properties.EMPTY_DATA_MESSAGE);
	}

	public void setEmptyDataMessage(java.lang.String emptyDataMessage) {
		engine.setProperty(Properties.EMPTY_DATA_MESSAGE, emptyDataMessage);
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

	public boolean isDisabled() {
		return isDisabled(null);
	}

	/**
	 * See {@link #isDisabled() isDisabled()} for more details
	 */
	public boolean isDisabled(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.DISABLED, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "disabled" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDisabledSetted() {
		return engine.isPropertySetted(Properties.DISABLED);
	}

	public void setDisabled(boolean disabled) {
		engine.setProperty(Properties.DISABLED, disabled);
	}

	public boolean isRequired() {
		return isRequired(null);
	}

	/**
	 * See {@link #isRequired() isRequired()} for more details
	 */
	public boolean isRequired(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.REQUIRED, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "required" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRequiredSetted() {
		return engine.isPropertySetted(Properties.REQUIRED);
	}

	public void setRequired(boolean required) {
		engine.setProperty(Properties.REQUIRED, required);
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

	public boolean isBorder() {
		return isBorder(null);
	}

	/**
	 * See {@link #isBorder() isBorder()} for more details
	 */
	public boolean isBorder(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.BORDER, true, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "border" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBorderSetted() {
		return engine.isPropertySetted(Properties.BORDER);
	}

	public void setBorder(boolean border) {
		engine.setProperty(Properties.BORDER, border);
	}

	public int getMaxTextLength() {
		return getMaxTextLength(null);
	}

	/**
	 * See {@link #getMaxTextLength() getMaxTextLength()} for more details
	 */
	public int getMaxTextLength(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.MAX_TEXT_LENGTH,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "maxTextLength" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMaxTextLengthSetted() {
		return engine.isPropertySetted(Properties.MAX_TEXT_LENGTH);
	}

	public void setMaxTextLength(int maxTextLength) {
		engine.setProperty(Properties.MAX_TEXT_LENGTH, maxTextLength);
	}

	public boolean isEditable() {
		return isEditable(null);
	}

	/**
	 * See {@link #isEditable() isEditable()} for more details
	 */
	public boolean isEditable(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.EDITABLE, true, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "editable" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isEditableSetted() {
		return engine.isPropertySetted(Properties.EDITABLE);
	}

	public void setEditable(boolean editable) {
		engine.setProperty(Properties.EDITABLE, editable);
	}

	public org.rcfaces.core.model.IFilterProperties getFilterProperties() {
		return getFilterProperties(null);
	}

	/**
	 * See {@link #getFilterProperties() getFilterProperties()} for more details
	 */
	public org.rcfaces.core.model.IFilterProperties getFilterProperties(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.model.IFilterProperties)engine.getProperty(Properties.FILTER_PROPERTIES, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "filterProperties" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFilterPropertiesSetted() {
		return engine.isPropertySetted(Properties.FILTER_PROPERTIES);
	}

	public void setFilterProperties(org.rcfaces.core.model.IFilterProperties filterProperties) {
		engine.setProperty(Properties.FILTER_PROPERTIES, filterProperties);
	}

	/**
	 * Returns an int value specifying the delay in milliseconds before showing the suggestion list.
	 * @return delay in milliseconds
	 */
	public int getSuggestionDelayMs() {
		return getSuggestionDelayMs(null);
	}

	/**
	 * Returns an int value specifying the delay in milliseconds before showing the suggestion list.
	 * @return delay in milliseconds
	 */
	public int getSuggestionDelayMs(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.SUGGESTION_DELAY_MS, 0, facesContext);
	}

	/**
	 * Sets an int value specifying the delay in milliseconds before showing the suggestion list.
	 * @param suggestionDelayMs delay in milliseconds
	 */
	public void setSuggestionDelayMs(int suggestionDelayMs) {
		engine.setProperty(Properties.SUGGESTION_DELAY_MS, suggestionDelayMs);
	}

	/**
	 * Sets an int value specifying the delay in milliseconds before showing the suggestion list.
	 * @param suggestionDelayMs delay in milliseconds
	 */
	/**
	 * Returns <code>true</code> if the attribute "suggestionDelayMs" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSuggestionDelayMsSetted() {
		return engine.isPropertySetted(Properties.SUGGESTION_DELAY_MS);
	}

	/**
	 * Returns an int value specifying the minimum number of characters before the suggestion is calculated.
	 * @return minimum number of characters
	 */
	public int getSuggestionMinChars() {
		return getSuggestionMinChars(null);
	}

	/**
	 * Returns an int value specifying the minimum number of characters before the suggestion is calculated.
	 * @return minimum number of characters
	 */
	public int getSuggestionMinChars(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.SUGGESTION_MIN_CHARS, 0, facesContext);
	}

	/**
	 * Sets an int value specifying the minimum number of characters before the suggestion is calculated.
	 * @param suggestionMinChars minimum number of characters
	 */
	public void setSuggestionMinChars(int suggestionMinChars) {
		engine.setProperty(Properties.SUGGESTION_MIN_CHARS, suggestionMinChars);
	}

	/**
	 * Sets an int value specifying the minimum number of characters before the suggestion is calculated.
	 * @param suggestionMinChars minimum number of characters
	 */
	/**
	 * Returns <code>true</code> if the attribute "suggestionMinChars" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSuggestionMinCharsSetted() {
		return engine.isPropertySetted(Properties.SUGGESTION_MIN_CHARS);
	}

	/**
	 * Returns a string value specifying the name of the variable receiving the number of rows.
	 * @return variable name
	 */
	public String getRowCountVar() {
		return getRowCountVar(null);
	}

	/**
	 * Returns a string value specifying the name of the variable receiving the number of rows.
	 * @return variable name
	 */
	public String getRowCountVar(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.ROW_COUNT_VAR, facesContext);
		return s;
	}

	/**
	 * Sets a string value specifying the name of the variable receiving the number of rows.
	 * @param rowCountVar variable name
	 */
	public void setRowCountVar(String rowCountVar) {
		engine.setProperty(Properties.ROW_COUNT_VAR, rowCountVar);
	}

	/**
	 * Sets a string value specifying the name of the variable receiving the number of rows.
	 * @param rowCountVar variable name
	 */
	/**
	 * Returns <code>true</code> if the attribute "rowCountVar" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isRowCountVarSetted() {
		return engine.isPropertySetted(Properties.ROW_COUNT_VAR);
	}

	/**
	 * Returns a string value specifying the name of the variable receiving the index of the current row.
	 * @return variable name
	 */
	public String getRowIndexVar() {
		return getRowIndexVar(null);
	}

	/**
	 * Returns a string value specifying the name of the variable receiving the index of the current row.
	 * @return variable name
	 */
	public String getRowIndexVar(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.ROW_INDEX_VAR, facesContext);
		return s;
	}

	/**
	 * Sets a string value specifying the name of the variable receiving the index of the current row.
	 * @param rowIndexVar variable name
	 */
	public void setRowIndexVar(String rowIndexVar) {
		engine.setProperty(Properties.ROW_INDEX_VAR, rowIndexVar);
	}

	/**
	 * Sets a string value specifying the name of the variable receiving the index of the current row.
	 * @param rowIndexVar variable name
	 */
	/**
	 * Returns <code>true</code> if the attribute "rowIndexVar" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isRowIndexVarSetted() {
		return engine.isPropertySetted(Properties.ROW_INDEX_VAR);
	}

	public String getValueColumnId() {
		return getValueColumnId(null);
	}

	public String getValueColumnId(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.VALUE_COLUMN_ID, facesContext);
		return s;
	}

	public void setValueColumnId(String valueColumnId) {
		engine.setProperty(Properties.VALUE_COLUMN_ID, valueColumnId);
	}

	/**
	 * Returns <code>true</code> if the attribute "valueColumnId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isValueColumnIdSetted() {
		return engine.isPropertySetted(Properties.VALUE_COLUMN_ID);
	}

	public String getLabelColumnId() {
		return getLabelColumnId(null);
	}

	public String getLabelColumnId(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.LABEL_COLUMN_ID, facesContext);
		return s;
	}

	public void setLabelColumnId(String labelColumnId) {
		engine.setProperty(Properties.LABEL_COLUMN_ID, labelColumnId);
	}

	/**
	 * Returns <code>true</code> if the attribute "labelColumnId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isLabelColumnIdSetted() {
		return engine.isPropertySetted(Properties.LABEL_COLUMN_ID);
	}

	public Object getSelectedValue() {
		return getSelectedValue(null);
	}

	public Object getSelectedValue(javax.faces.context.FacesContext facesContext) {
		return engine.getValue(Properties.SELECTED_VALUE, facesContext);
	}

	public void setSelectedValue(Object selectedValue) {
		engine.setValue(Properties.SELECTED_VALUE, selectedValue);
	}

	/**
	 * Returns <code>true</code> if the attribute "selectedValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSelectedValueSetted() {
		return engine.isPropertySetted(Properties.SELECTED_VALUE);
	}

	public String getValueFormat() {
		return getValueFormat(null);
	}

	public String getValueFormat(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.VALUE_FORMAT, facesContext);
		return s;
	}

	public void setValueFormat(String valueFormat) {
		engine.setProperty(Properties.VALUE_FORMAT, valueFormat);
	}

	/**
	 * Returns <code>true</code> if the attribute "valueFormat" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isValueFormatSetted() {
		return engine.isPropertySetted(Properties.VALUE_FORMAT);
	}

	public String getValueFormatTooltip() {
		return getValueFormatTooltip(null);
	}

	public String getValueFormatTooltip(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.VALUE_FORMAT_TOOLTIP, facesContext);
		return s;
	}

	public void setValueFormatTooltip(String valueFormatTooltip) {
		engine.setProperty(Properties.VALUE_FORMAT_TOOLTIP, valueFormatTooltip);
	}

	/**
	 * Returns <code>true</code> if the attribute "valueFormatTooltip" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isValueFormatTooltipSetted() {
		return engine.isPropertySetted(Properties.VALUE_FORMAT_TOOLTIP);
	}

	public String getValueFormatDescription() {
		return getValueFormatDescription(null);
	}

	public String getValueFormatDescription(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.VALUE_FORMAT_DESCRIPTION, facesContext);
		return s;
	}

	public void setValueFormatDescription(String valueFormatDescription) {
		engine.setProperty(Properties.VALUE_FORMAT_DESCRIPTION, valueFormatDescription);
	}

	/**
	 * Returns <code>true</code> if the attribute "valueFormatDescription" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isValueFormatDescriptionSetted() {
		return engine.isPropertySetted(Properties.VALUE_FORMAT_DESCRIPTION);
	}

	public boolean isForceValidation() {
		return isForceValidation(null);
	}

	public boolean isForceValidation(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.FORCE_VALIDATION, false, facesContext);
	}

	public void setForceValidation(boolean forceValidation) {
		engine.setProperty(Properties.FORCE_VALIDATION, forceValidation);
	}

	/**
	 * Returns <code>true</code> if the attribute "forceValidation" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isForceValidationSetted() {
		return engine.isPropertySetted(Properties.FORCE_VALIDATION);
	}

	public String getForLabel() {
		return getForLabel(null);
	}

	public String getForLabel(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.FOR_LABEL, facesContext);
		return s;
	}

	public void setForLabel(String forLabel) {
		engine.setProperty(Properties.FOR_LABEL, forLabel);
	}

	/**
	 * Returns <code>true</code> if the attribute "forLabel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isForLabelSetted() {
		return engine.isPropertySetted(Properties.FOR_LABEL);
	}

	public String getValueFormatLabel() {
		return getValueFormatLabel(null);
	}

	public String getValueFormatLabel(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.VALUE_FORMAT_LABEL, facesContext);
		return s;
	}

	public void setValueFormatLabel(String valueFormatLabel) {
		engine.setProperty(Properties.VALUE_FORMAT_LABEL, valueFormatLabel);
	}

	/**
	 * Returns <code>true</code> if the attribute "valueFormatLabel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isValueFormatLabelSetted() {
		return engine.isPropertySetted(Properties.VALUE_FORMAT_LABEL);
	}

	public String getNoValueFormatLabel() {
		return getNoValueFormatLabel(null);
	}

	public String getNoValueFormatLabel(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.NO_VALUE_FORMAT_LABEL, facesContext);
		return s;
	}

	public void setNoValueFormatLabel(String noValueFormatLabel) {
		engine.setProperty(Properties.NO_VALUE_FORMAT_LABEL, noValueFormatLabel);
	}

	/**
	 * Returns <code>true</code> if the attribute "noValueFormatLabel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isNoValueFormatLabelSetted() {
		return engine.isPropertySetted(Properties.NO_VALUE_FORMAT_LABEL);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
