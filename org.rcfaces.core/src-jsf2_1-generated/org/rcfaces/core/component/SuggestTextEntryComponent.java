package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.ISuggestionEventCapability;
import java.lang.String;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.TextEntryComponent;
import javax.faces.convert.Converter;
import org.rcfaces.core.component.capability.IMaxResultNumberCapability;
import org.rcfaces.core.internal.converter.FilterPropertiesConverter;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.model.IFilterProperties;
import java.util.Set;
import java.util.Arrays;
import java.util.Collection;
import org.rcfaces.core.component.capability.IMenuEventCapability;
import org.rcfaces.core.internal.tools.ComponentTools;

/**
 * <p>The suggestTextEntry is a <a href="/comps/textEntryCOmponent.html">textEntry Component</a> with an autosuggestion feature that shows in the form of a dropdown list.</p>
 * <p>The suggestTextEntry Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text &amp; font</li>
 * <li>Help</li>
 * <li>Visibility, Read-Only, Disabled</li>
 * <li>Events Handling</li>
 * </ul>
 * </p>
 */
public class SuggestTextEntryComponent extends TextEntryComponent implements 
	IFilterCapability,
	IMaxResultNumberCapability,
	ISuggestionEventCapability,
	IMenuEventCapability {

	private static final Log LOG = LogFactory.getLog(SuggestTextEntryComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.suggestTextEntry";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=TextEntryComponent.BEHAVIOR_EVENT_NAMES;

	public SuggestTextEntryComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public SuggestTextEntryComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void setSuggestionConverter(String converterId) {

			
			setSuggestionConverter(converterId, null);
		
	}

	public void setSuggestionConverter(String converterId, FacesContext facesContext) {


			Converter converter=ComponentTools.createConverter(facesContext, converterId);

			setSuggestionConverter(converter);
		
	}

	public void setFilterProperties(String properties) {


			IFilterProperties filterProperties=(IFilterProperties)FilterPropertiesConverter.SINGLETON.getAsObject(null, this, properties);
			
			setFilterProperties(filterProperties);
		
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

	public int getMaxResultNumber() {
		return getMaxResultNumber(null);
	}

	/**
	 * See {@link #getMaxResultNumber() getMaxResultNumber()} for more details
	 */
	public int getMaxResultNumber(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.MAX_RESULT_NUMBER, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "maxResultNumber" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMaxResultNumberSetted() {
		return getStateHelper().get(Properties.MAX_RESULT_NUMBER)!=null;
	}

	public void setMaxResultNumber(int maxResultNumber) {
		getStateHelper().put(Properties.MAX_RESULT_NUMBER, maxResultNumber);
	}

	public final void addSuggestionListener(org.rcfaces.core.event.ISuggestionListener listener) {
		addFacesListener(listener);
	}

	public final void removeSuggestionListener(org.rcfaces.core.event.ISuggestionListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listSuggestionListeners() {
		return getFacesListeners(org.rcfaces.core.event.ISuggestionListener.class);
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
	 * Returns a boolean value indicating if the component should consider the case of the user input when using the data.
	 * @return true if case sensitive
	 */
	public boolean isCaseSensitive() {
		return isCaseSensitive(null);
	}

	/**
	 * Returns a boolean value indicating if the component should consider the case of the user input when using the data.
	 * @return true if case sensitive
	 */
	public boolean isCaseSensitive(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.CASE_SENSITIVE, false);
	}

	/**
	 * Sets a boolean value indicating if the component should consider the case of the user input when using the data.
	 * @param caseSensitive <code>true</code> if case sensitive
	 */
	public void setCaseSensitive(boolean caseSensitive) {
		 getStateHelper().put(Properties.CASE_SENSITIVE, caseSensitive);
	}

	/**
	 * Sets a boolean value indicating if the component should consider the case of the user input when using the data.
	 * @param caseSensitive <code>true</code> if case sensitive
	 */
	/**
	 * Returns <code>true</code> if the attribute "caseSensitive" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isCaseSensitiveSetted() {
		return getStateHelper().get(Properties.CASE_SENSITIVE)!=null;
	}

	/**
	 * Returns a boolean value indicating if the first proposed value must be automatically appended to the current entry.
	 * @return true if proposal is forced
	 */
	public boolean isForceProposal() {
		return isForceProposal(null);
	}

	/**
	 * Returns a boolean value indicating if the first proposed value must be automatically appended to the current entry.
	 * @return true if proposal is forced
	 */
	public boolean isForceProposal(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.FORCE_PROPOSAL, false);
	}

	/**
	 * Sets a boolean value indicating if the first proposed value must be automatically appended to the current entry.
	 * @param forceProposal if proposal has to be forced
	 */
	public void setForceProposal(boolean forceProposal) {
		 getStateHelper().put(Properties.FORCE_PROPOSAL, forceProposal);
	}

	/**
	 * Sets a boolean value indicating if the first proposed value must be automatically appended to the current entry.
	 * @param forceProposal if proposal has to be forced
	 */
	/**
	 * Returns <code>true</code> if the attribute "forceProposal" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isForceProposalSetted() {
		return getStateHelper().get(Properties.FORCE_PROPOSAL)!=null;
	}

	/**
	 * Returns a string value containing the value for the selected select item object.
	 * @return value selected
	 */
	public Object getSuggestionValue() {
		return getSuggestionValue(null);
	}

	/**
	 * Returns a string value containing the value for the selected select item object.
	 * @return value selected
	 */
	public Object getSuggestionValue(javax.faces.context.FacesContext facesContext) {
		return getStateHelper().eval(Properties.SUGGESTION_VALUE);
	}

	/**
	 * Sets a string value containing the value for the selected select item object.
	 * @param suggestionValue value selected
	 */
	public void setSuggestionValue(Object suggestionValue) {
		 getStateHelper().put(Properties.SUGGESTION_VALUE, suggestionValue);
	}

	/**
	 * Sets a string value containing the value for the selected select item object.
	 * @param suggestionValue value selected
	 */
	/**
	 * Returns <code>true</code> if the attribute "suggestionValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSuggestionValueSetted() {
		return getStateHelper().get(Properties.SUGGESTION_VALUE)!=null;
	}

	/**
	 * Returns a string specifying a converter id or a binding to a converter object. This converter will be used for the values of the list items.
	 * @return converter id
	 */
	public javax.faces.convert.Converter getSuggestionConverter() {
		return getSuggestionConverter(null);
	}

	/**
	 * Returns a string specifying a converter id or a binding to a converter object. This converter will be used for the values of the list items.
	 * @return converter id
	 */
	public javax.faces.convert.Converter getSuggestionConverter(javax.faces.context.FacesContext facesContext) {
		return (javax.faces.convert.Converter)getStateHelper().eval(Properties.SUGGESTION_CONVERTER);
	}

	/**
	 * Sets a string specifying a converter id or a binding to a converter object. This converter will be used for the values of the list items.
	 * @param suggestionConverter converter id
	 */
	public void setSuggestionConverter(javax.faces.convert.Converter suggestionConverter) {
		 getStateHelper().put(Properties.SUGGESTION_CONVERTER, suggestionConverter);
	}

	/**
	 * Sets a string specifying a converter id or a binding to a converter object. This converter will be used for the values of the list items.
	 * @param suggestionConverter converter id
	 */
	/**
	 * Returns <code>true</code> if the attribute "suggestionConverter" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSuggestionConverterSetted() {
		return getStateHelper().get(Properties.SUGGESTION_CONVERTER)!=null;
	}

	public String getMoreResultsMessage() {
		return getMoreResultsMessage(null);
	}

	public String getMoreResultsMessage(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.MORE_RESULTS_MESSAGE);
	}

	public void setMoreResultsMessage(String moreResultsMessage) {
		 getStateHelper().put(Properties.MORE_RESULTS_MESSAGE, moreResultsMessage);
	}

	/**
	 * Returns <code>true</code> if the attribute "moreResultsMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isMoreResultsMessageSetted() {
		return getStateHelper().get(Properties.MORE_RESULTS_MESSAGE)!=null;
	}

	public boolean isOrderedItems() {
		return isOrderedItems(null);
	}

	public boolean isOrderedItems(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.ORDERED_ITEMS, true);
	}

	public void setOrderedItems(boolean orderedItems) {
		 getStateHelper().put(Properties.ORDERED_ITEMS, orderedItems);
	}

	/**
	 * Returns <code>true</code> if the attribute "orderedItems" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isOrderedItemsSetted() {
		return getStateHelper().get(Properties.ORDERED_ITEMS)!=null;
	}

}
