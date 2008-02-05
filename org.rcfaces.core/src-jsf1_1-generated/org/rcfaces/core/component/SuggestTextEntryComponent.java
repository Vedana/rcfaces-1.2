package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.faces.convert.Converter;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.TextEntryComponent;
import javax.faces.el.ValueBinding;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.capability.IMaxResultNumberCapability;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.component.capability.IMenuEventCapability;
import org.rcfaces.core.component.capability.ISuggestionEventCapability;

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

	public static final String COMPONENT_TYPE="org.rcfaces.core.suggestTextEntry";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(TextEntryComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"caseSensitive","suggestionDelayMs","filterProperties","moreResultsMessage","suggestionConverter","suggestionListener","suggestionValue","forceProposal","maxResultNumber","orderedItems","menuListener","suggestionMinChars"}));
	}

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

	public int getMaxResultNumber() {
		return getMaxResultNumber(null);
	}

	/**
	 * See {@link #getMaxResultNumber() getMaxResultNumber()} for more details
	 */
	public int getMaxResultNumber(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.MAX_RESULT_NUMBER,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "maxResultNumber" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMaxResultNumberSetted() {
		return engine.isPropertySetted(Properties.MAX_RESULT_NUMBER);
	}

	public void setMaxResultNumber(int maxResultNumber) {
		engine.setProperty(Properties.MAX_RESULT_NUMBER, maxResultNumber);
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
		return engine.getBoolProperty(Properties.CASE_SENSITIVE, false, facesContext);
	}

	/**
	 * Sets a boolean value indicating if the component should consider the case of the user input when using the data.
	 * @param caseSensitive <code>true</code> if case sensitive
	 */
	public void setCaseSensitive(boolean caseSensitive) {
		engine.setProperty(Properties.CASE_SENSITIVE, caseSensitive);
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
		return engine.isPropertySetted(Properties.CASE_SENSITIVE);
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
		return engine.getBoolProperty(Properties.FORCE_PROPOSAL, false, facesContext);
	}

	/**
	 * Sets a boolean value indicating if the first proposed value must be automatically appended to the current entry.
	 * @param forceProposal if proposal has to be forced
	 */
	public void setForceProposal(boolean forceProposal) {
		engine.setProperty(Properties.FORCE_PROPOSAL, forceProposal);
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
		return engine.isPropertySetted(Properties.FORCE_PROPOSAL);
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
		return engine.getValue(Properties.SUGGESTION_VALUE, facesContext);
	}

	/**
	 * Sets a string value containing the value for the selected select item object.
	 * @param suggestionValue value selected
	 */
	public void setSuggestionValue(Object suggestionValue) {
		engine.setValue(Properties.SUGGESTION_VALUE, suggestionValue);
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
		return engine.isPropertySetted(Properties.SUGGESTION_VALUE);
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
		return (javax.faces.convert.Converter)engine.getValue(Properties.SUGGESTION_CONVERTER, facesContext);
	}

	/**
	 * Sets a string specifying a converter id or a binding to a converter object. This converter will be used for the values of the list items.
	 * @param suggestionConverter converter id
	 */
	public void setSuggestionConverter(javax.faces.convert.Converter suggestionConverter) {
		engine.setProperty(Properties.SUGGESTION_CONVERTER, suggestionConverter);
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
		return engine.isPropertySetted(Properties.SUGGESTION_CONVERTER);
	}

	public String getMoreResultsMessage() {
		return getMoreResultsMessage(null);
	}

	public String getMoreResultsMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MORE_RESULTS_MESSAGE, facesContext);
	}

	public void setMoreResultsMessage(String moreResultsMessage) {
		engine.setProperty(Properties.MORE_RESULTS_MESSAGE, moreResultsMessage);
	}

	/**
	 * Returns <code>true</code> if the attribute "moreResultsMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isMoreResultsMessageSetted() {
		return engine.isPropertySetted(Properties.MORE_RESULTS_MESSAGE);
	}

	public boolean isOrderedItems() {
		return isOrderedItems(null);
	}

	public boolean isOrderedItems(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.ORDERED_ITEMS, true, facesContext);
	}

	public void setOrderedItems(boolean orderedItems) {
		engine.setProperty(Properties.ORDERED_ITEMS, orderedItems);
	}

	/**
	 * Returns <code>true</code> if the attribute "orderedItems" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isOrderedItemsSetted() {
		return engine.isPropertySetted(Properties.ORDERED_ITEMS);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
