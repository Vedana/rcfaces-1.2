package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IRequiredCapability;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.internal.tools.GridTools;
import org.rcfaces.core.internal.converter.FilterPropertiesConverter;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.apache.commons.logging.Log;
import java.util.Set;
import java.util.Collection;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.capability.IEditableCapability;
import java.lang.String;
import org.rcfaces.core.component.AbstractGridComponent;
import org.rcfaces.core.component.iterator.IColumnIterator;
import org.rcfaces.core.component.capability.IEmptyDataMessageCapability;
import org.rcfaces.core.component.capability.IEmptyMessageCapability;
import org.rcfaces.core.internal.capability.IGridComponent;
import javax.el.ValueExpression;
import org.rcfaces.core.component.capability.IDisabledCapability;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.component.capability.IMaxTextLengthCapability;
import java.util.Arrays;
import org.rcfaces.core.model.IFilterProperties;

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
	IGridComponent {

	private static final Log LOG = LogFactory.getLog(KeyEntryComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.keyEntry";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractGridComponent.BEHAVIOR_EVENT_NAMES;

	public KeyEntryComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public KeyEntryComponent(String componentId) {
		this();
		setId(componentId);
	}

	public IColumnIterator listColumns() {


			return GridTools.listColumns(this, javax.faces.component.UIColumn.class);
			
	}

	public void setFilterProperties(String properties) {


			IFilterProperties filterProperties=(IFilterProperties)FilterPropertiesConverter.SINGLETON.getAsObject(null, this, properties);
			
			setFilterProperties(filterProperties);
		
	}

	public java.lang.String getEmptyMessage() {
		return getEmptyMessage(null);
	}

	/**
	 * See {@link #getEmptyMessage() getEmptyMessage()} for more details
	 */
	public java.lang.String getEmptyMessage(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.EMPTY_MESSAGE);
	}

	/**
	 * Returns <code>true</code> if the attribute "emptyMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isEmptyMessageSetted() {
		return getStateHelper().get(Properties.EMPTY_MESSAGE)!=null;
	}

	public void setEmptyMessage(java.lang.String emptyMessage) {
		getStateHelper().put(Properties.EMPTY_MESSAGE, emptyMessage);
	}

	public java.lang.String getEmptyDataMessage() {
		return getEmptyDataMessage(null);
	}

	/**
	 * See {@link #getEmptyDataMessage() getEmptyDataMessage()} for more details
	 */
	public java.lang.String getEmptyDataMessage(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.EMPTY_DATA_MESSAGE);
	}

	/**
	 * Returns <code>true</code> if the attribute "emptyDataMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isEmptyDataMessageSetted() {
		return getStateHelper().get(Properties.EMPTY_DATA_MESSAGE)!=null;
	}

	public void setEmptyDataMessage(java.lang.String emptyDataMessage) {
		getStateHelper().put(Properties.EMPTY_DATA_MESSAGE, emptyDataMessage);
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
		return (Boolean)getStateHelper().eval(Properties.DISABLED, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "disabled" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDisabledSetted() {
		return getStateHelper().get(Properties.DISABLED)!=null;
	}

	public void setDisabled(boolean disabled) {
		getStateHelper().put(Properties.DISABLED, disabled);
	}

	public boolean isRequired() {
		return isRequired(null);
	}

	/**
	 * See {@link #isRequired() isRequired()} for more details
	 */
	public boolean isRequired(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.REQUIRED, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "required" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRequiredSetted() {
		return getStateHelper().get(Properties.REQUIRED)!=null;
	}

	public void setRequired(boolean required) {
		getStateHelper().put(Properties.REQUIRED, required);
	}

	public boolean isReadOnly() {
		return isReadOnly(null);
	}

	/**
	 * See {@link #isReadOnly() isReadOnly()} for more details
	 */
	public boolean isReadOnly(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.READ_ONLY, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "readOnly" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isReadOnlySetted() {
		return getStateHelper().get(Properties.READ_ONLY)!=null;
	}

	public void setReadOnly(boolean readOnly) {
		getStateHelper().put(Properties.READ_ONLY, readOnly);
	}

	public boolean isBorder() {
		return isBorder(null);
	}

	/**
	 * See {@link #isBorder() isBorder()} for more details
	 */
	public boolean isBorder(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.BORDER, true);
	}

	/**
	 * Returns <code>true</code> if the attribute "border" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBorderSetted() {
		return getStateHelper().get(Properties.BORDER)!=null;
	}

	public void setBorder(boolean border) {
		getStateHelper().put(Properties.BORDER, border);
	}

	public int getMaxTextLength() {
		return getMaxTextLength(null);
	}

	/**
	 * See {@link #getMaxTextLength() getMaxTextLength()} for more details
	 */
	public int getMaxTextLength(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.MAX_TEXT_LENGTH, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "maxTextLength" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMaxTextLengthSetted() {
		return getStateHelper().get(Properties.MAX_TEXT_LENGTH)!=null;
	}

	public void setMaxTextLength(int maxTextLength) {
		getStateHelper().put(Properties.MAX_TEXT_LENGTH, maxTextLength);
	}

	public boolean isEditable() {
		return isEditable(null);
	}

	/**
	 * See {@link #isEditable() isEditable()} for more details
	 */
	public boolean isEditable(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.EDITABLE, true);
	}

	/**
	 * Returns <code>true</code> if the attribute "editable" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isEditableSetted() {
		return getStateHelper().get(Properties.EDITABLE)!=null;
	}

	public void setEditable(boolean editable) {
		getStateHelper().put(Properties.EDITABLE, editable);
	}

	public org.rcfaces.core.model.IFilterProperties getFilterProperties() {
		return getFilterProperties(null);
	}

	/**
	 * See {@link #getFilterProperties() getFilterProperties()} for more details
	 */
	public org.rcfaces.core.model.IFilterProperties getFilterProperties(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.model.IFilterProperties)getStateHelper().eval(Properties.FILTER_PROPERTIES);
	}

	/**
	 * Returns <code>true</code> if the attribute "filterProperties" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFilterPropertiesSetted() {
		return getStateHelper().get(Properties.FILTER_PROPERTIES)!=null;
	}

	public void setFilterProperties(org.rcfaces.core.model.IFilterProperties filterProperties) {
		getStateHelper().put(Properties.FILTER_PROPERTIES, filterProperties);
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
		return (Integer)getStateHelper().eval(Properties.SUGGESTION_DELAY_MS, 0);
	}

	/**
	 * Sets an int value specifying the delay in milliseconds before showing the suggestion list.
	 * @param suggestionDelayMs delay in milliseconds
	 */
	public void setSuggestionDelayMs(int suggestionDelayMs) {
		 getStateHelper().put(Properties.SUGGESTION_DELAY_MS, suggestionDelayMs);
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
		return getStateHelper().get(Properties.SUGGESTION_DELAY_MS)!=null;
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
		return (Integer)getStateHelper().eval(Properties.SUGGESTION_MIN_CHARS, 0);
	}

	/**
	 * Sets an int value specifying the minimum number of characters before the suggestion is calculated.
	 * @param suggestionMinChars minimum number of characters
	 */
	public void setSuggestionMinChars(int suggestionMinChars) {
		 getStateHelper().put(Properties.SUGGESTION_MIN_CHARS, suggestionMinChars);
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
		return getStateHelper().get(Properties.SUGGESTION_MIN_CHARS)!=null;
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
		return (String)getStateHelper().eval(Properties.ROW_COUNT_VAR);
	}

	/**
	 * Sets a string value specifying the name of the variable receiving the number of rows.
	 * @param rowCountVar variable name
	 */
	public void setRowCountVar(String rowCountVar) {
		 getStateHelper().put(Properties.ROW_COUNT_VAR, rowCountVar);
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
		return getStateHelper().get(Properties.ROW_COUNT_VAR)!=null;
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
		return (String)getStateHelper().eval(Properties.ROW_INDEX_VAR);
	}

	/**
	 * Sets a string value specifying the name of the variable receiving the index of the current row.
	 * @param rowIndexVar variable name
	 */
	public void setRowIndexVar(String rowIndexVar) {
		 getStateHelper().put(Properties.ROW_INDEX_VAR, rowIndexVar);
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
		return getStateHelper().get(Properties.ROW_INDEX_VAR)!=null;
	}

	public String getValueColumnId() {
		return getValueColumnId(null);
	}

	public String getValueColumnId(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.VALUE_COLUMN_ID);
	}

	public void setValueColumnId(String valueColumnId) {
		 getStateHelper().put(Properties.VALUE_COLUMN_ID, valueColumnId);
	}

	/**
	 * Returns <code>true</code> if the attribute "valueColumnId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isValueColumnIdSetted() {
		return getStateHelper().get(Properties.VALUE_COLUMN_ID)!=null;
	}

	public String getLabelColumnId() {
		return getLabelColumnId(null);
	}

	public String getLabelColumnId(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.LABEL_COLUMN_ID);
	}

	public void setLabelColumnId(String labelColumnId) {
		 getStateHelper().put(Properties.LABEL_COLUMN_ID, labelColumnId);
	}

	/**
	 * Returns <code>true</code> if the attribute "labelColumnId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isLabelColumnIdSetted() {
		return getStateHelper().get(Properties.LABEL_COLUMN_ID)!=null;
	}

	public Object getSelectedValue() {
		return getSelectedValue(null);
	}

	public Object getSelectedValue(javax.faces.context.FacesContext facesContext) {
		return getStateHelper().eval(Properties.SELECTED_VALUE);
	}

	public void setSelectedValue(Object selectedValue) {
		 getStateHelper().put(Properties.SELECTED_VALUE, selectedValue);
	}

	/**
	 * Returns <code>true</code> if the attribute "selectedValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSelectedValueSetted() {
		return getStateHelper().get(Properties.SELECTED_VALUE)!=null;
	}

	public String getValueFormat() {
		return getValueFormat(null);
	}

	public String getValueFormat(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.VALUE_FORMAT);
	}

	public void setValueFormat(String valueFormat) {
		 getStateHelper().put(Properties.VALUE_FORMAT, valueFormat);
	}

	/**
	 * Returns <code>true</code> if the attribute "valueFormat" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isValueFormatSetted() {
		return getStateHelper().get(Properties.VALUE_FORMAT)!=null;
	}

	public boolean isForceValidation() {
		return isForceValidation(null);
	}

	public boolean isForceValidation(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.FORCE_VALIDATION, false);
	}

	public void setForceValidation(boolean forceValidation) {
		 getStateHelper().put(Properties.FORCE_VALIDATION, forceValidation);
	}

	/**
	 * Returns <code>true</code> if the attribute "forceValidation" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isForceValidationSetted() {
		return getStateHelper().get(Properties.FORCE_VALIDATION)!=null;
	}

	public String getForLabel() {
		return getForLabel(null);
	}

	public String getForLabel(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.FOR_LABEL);
	}

	public void setForLabel(String forLabel) {
		 getStateHelper().put(Properties.FOR_LABEL, forLabel);
	}

	/**
	 * Returns <code>true</code> if the attribute "forLabel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isForLabelSetted() {
		return getStateHelper().get(Properties.FOR_LABEL)!=null;
	}

	public String getValueFormatLabel() {
		return getValueFormatLabel(null);
	}

	public String getValueFormatLabel(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.VALUE_FORMAT_LABEL);
	}

	public void setValueFormatLabel(String valueFormatLabel) {
		 getStateHelper().put(Properties.VALUE_FORMAT_LABEL, valueFormatLabel);
	}

	/**
	 * Returns <code>true</code> if the attribute "valueFormatLabel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isValueFormatLabelSetted() {
		return getStateHelper().get(Properties.VALUE_FORMAT_LABEL)!=null;
	}

	public String getNoValueFormatLabel() {
		return getNoValueFormatLabel(null);
	}

	public String getNoValueFormatLabel(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.NO_VALUE_FORMAT_LABEL);
	}

	public void setNoValueFormatLabel(String noValueFormatLabel) {
		 getStateHelper().put(Properties.NO_VALUE_FORMAT_LABEL, noValueFormatLabel);
	}

	/**
	 * Returns <code>true</code> if the attribute "noValueFormatLabel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isNoValueFormatLabelSetted() {
		return getStateHelper().get(Properties.NO_VALUE_FORMAT_LABEL)!=null;
	}

}
