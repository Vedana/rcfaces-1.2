package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.component.capability.IMaxResultNumberCapability;
import org.rcfaces.core.component.capability.IMenuEventCapability;
import org.rcfaces.core.component.capability.ISuggestionEventCapability;
import org.rcfaces.core.internal.component.Properties;
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

	public static final String COMPONENT_TYPE="org.rcfaces.core.suggestTextEntry";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(TextEntryComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"caseSensitive","suggestionDelayMs","filterProperties","suggestionConverter","suggestionListener","suggestionValue","forceProposal","maxResultNumber","menuListener","suggestionMinChars"}));
	}

	public SuggestTextEntryComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public SuggestTextEntryComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final void setSuggestionConverter(String converterId) {

			
			setSuggestionConverter(converterId, null);
		
	}

	public final void setSuggestionConverter(String converterId, FacesContext facesContext) {


			Converter converter=ComponentTools.createConverter(facesContext, converterId);

			setSuggestionConverter(converter);
		
	}

	public final org.rcfaces.core.model.IFilterProperties getFilterProperties() {
		return getFilterProperties(null);
	}

	/**
	 * See {@link #getFilterProperties() getFilterProperties()} for more details
	 */
	public final org.rcfaces.core.model.IFilterProperties getFilterProperties(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.model.IFilterProperties)engine.getProperty(Properties.FILTER_PROPERTIES, facesContext);
	}

	public final void setFilterProperties(org.rcfaces.core.model.IFilterProperties filterProperties) {
		engine.setProperty(Properties.FILTER_PROPERTIES, filterProperties);
	}

	/**
	 * See {@link #setFilterProperties(org.rcfaces.core.model.IFilterProperties) setFilterProperties(org.rcfaces.core.model.IFilterProperties)} for more details
	 */
	public final void setFilterProperties(ValueBinding filterProperties) {
		engine.setProperty(Properties.FILTER_PROPERTIES, filterProperties);
	}

	public final int getMaxResultNumber() {
		return getMaxResultNumber(null);
	}

	/**
	 * See {@link #getMaxResultNumber() getMaxResultNumber()} for more details
	 */
	public final int getMaxResultNumber(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.MAX_RESULT_NUMBER,0, facesContext);
	}

	public final void setMaxResultNumber(int maxResultNumber) {
		engine.setProperty(Properties.MAX_RESULT_NUMBER, maxResultNumber);
	}

	/**
	 * See {@link #setMaxResultNumber(int) setMaxResultNumber(int)} for more details
	 */
	public final void setMaxResultNumber(ValueBinding maxResultNumber) {
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
	public final int getSuggestionDelayMs() {
		return getSuggestionDelayMs(null);
	}

	/**
	 * Returns an int value specifying the delay in milliseconds before showing the suggestion list.
	 * @return delay in milliseconds
	 */
	public final int getSuggestionDelayMs(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.SUGGESTION_DELAY_MS, 0, facesContext);
	}

	/**
	 * Sets an int value specifying the delay in milliseconds before showing the suggestion list.
	 * @param suggestionDelayMs delay in milliseconds
	 */
	public final void setSuggestionDelayMs(int suggestionDelayMs) {
		engine.setProperty(Properties.SUGGESTION_DELAY_MS, suggestionDelayMs);
	}

	/**
	 * Sets an int value specifying the delay in milliseconds before showing the suggestion list.
	 * @param suggestionDelayMs delay in milliseconds
	 */
	public final void setSuggestionDelayMs(ValueBinding suggestionDelayMs) {
		engine.setProperty(Properties.SUGGESTION_DELAY_MS, suggestionDelayMs);
	}

	/**
	 * Returns <code>true</code> if the attribute "suggestionDelayMs" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSuggestionDelayMsSetted() {
		return engine.isPropertySetted(Properties.SUGGESTION_DELAY_MS);
	}

	/**
	 * Returns an int value specifying the minimum number of characters before the suggestion is calculated.
	 * @return minimum number of characters
	 */
	public final int getSuggestionMinChars() {
		return getSuggestionMinChars(null);
	}

	/**
	 * Returns an int value specifying the minimum number of characters before the suggestion is calculated.
	 * @return minimum number of characters
	 */
	public final int getSuggestionMinChars(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.SUGGESTION_MIN_CHARS, 0, facesContext);
	}

	/**
	 * Sets an int value specifying the minimum number of characters before the suggestion is calculated.
	 * @param suggestionMinChars minimum number of characters
	 */
	public final void setSuggestionMinChars(int suggestionMinChars) {
		engine.setProperty(Properties.SUGGESTION_MIN_CHARS, suggestionMinChars);
	}

	/**
	 * Sets an int value specifying the minimum number of characters before the suggestion is calculated.
	 * @param suggestionMinChars minimum number of characters
	 */
	public final void setSuggestionMinChars(ValueBinding suggestionMinChars) {
		engine.setProperty(Properties.SUGGESTION_MIN_CHARS, suggestionMinChars);
	}

	/**
	 * Returns <code>true</code> if the attribute "suggestionMinChars" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSuggestionMinCharsSetted() {
		return engine.isPropertySetted(Properties.SUGGESTION_MIN_CHARS);
	}

	/**
	 * Returns a boolean value indicating if the component should consider the case of the user input when using the data.
	 * @return true if case sensitive
	 */
	public final boolean isCaseSensitive() {
		return isCaseSensitive(null);
	}

	/**
	 * Returns a boolean value indicating if the component should consider the case of the user input when using the data.
	 * @return true if case sensitive
	 */
	public final boolean isCaseSensitive(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.CASE_SENSITIVE, false, facesContext);
	}

	/**
	 * Sets a boolean value indicating if the component should consider the case of the user input when using the data.
	 * @param caseSensitive <code>true</code> if case sensitive
	 */
	public final void setCaseSensitive(boolean caseSensitive) {
		engine.setProperty(Properties.CASE_SENSITIVE, caseSensitive);
	}

	/**
	 * Sets a boolean value indicating if the component should consider the case of the user input when using the data.
	 * @param caseSensitive <code>true</code> if case sensitive
	 */
	public final void setCaseSensitive(ValueBinding caseSensitive) {
		engine.setProperty(Properties.CASE_SENSITIVE, caseSensitive);
	}

	/**
	 * Returns <code>true</code> if the attribute "caseSensitive" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCaseSensitiveSetted() {
		return engine.isPropertySetted(Properties.CASE_SENSITIVE);
	}

	/**
	 * Returns a boolean value indicating if the first proposed value must be automatically appended to the current entry.
	 * @return true if proposal is forced
	 */
	public final boolean isForceProposal() {
		return isForceProposal(null);
	}

	/**
	 * Returns a boolean value indicating if the first proposed value must be automatically appended to the current entry.
	 * @return true if proposal is forced
	 */
	public final boolean isForceProposal(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.FORCE_PROPOSAL, false, facesContext);
	}

	/**
	 * Sets a boolean value indicating if the first proposed value must be automatically appended to the current entry.
	 * @param forceProposal if proposal has to be forced
	 */
	public final void setForceProposal(boolean forceProposal) {
		engine.setProperty(Properties.FORCE_PROPOSAL, forceProposal);
	}

	/**
	 * Sets a boolean value indicating if the first proposed value must be automatically appended to the current entry.
	 * @param forceProposal if proposal has to be forced
	 */
	public final void setForceProposal(ValueBinding forceProposal) {
		engine.setProperty(Properties.FORCE_PROPOSAL, forceProposal);
	}

	/**
	 * Returns <code>true</code> if the attribute "forceProposal" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isForceProposalSetted() {
		return engine.isPropertySetted(Properties.FORCE_PROPOSAL);
	}

	/**
	 * Returns a string value containing the value for the selected select item object.
	 * @return value selected
	 */
	public final Object getSuggestionValue() {
		return getSuggestionValue(null);
	}

	/**
	 * Returns a string value containing the value for the selected select item object.
	 * @return value selected
	 */
	public final Object getSuggestionValue(javax.faces.context.FacesContext facesContext) {
		return engine.getValue(Properties.SUGGESTION_VALUE, facesContext);
	}

	/**
	 * Sets a string value containing the value for the selected select item object.
	 * @param suggestionValue value selected
	 */
	public final void setSuggestionValue(Object suggestionValue) {
		engine.setValue(Properties.SUGGESTION_VALUE, suggestionValue);
	}

	/**
	 * Sets a string value containing the value for the selected select item object.
	 * @param suggestionValue value selected
	 */
	public final void setSuggestionValue(ValueBinding suggestionValue) {
		engine.setValueBinding(Properties.SUGGESTION_VALUE, suggestionValue);
	}

	/**
	 * Returns <code>true</code> if the attribute "suggestionValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSuggestionValueSetted() {
		return engine.isPropertySetted(Properties.SUGGESTION_VALUE);
	}

	/**
	 * Returns a string specifying a converter id or a binding to a converter object. This converter will be used for the values of the list items.
	 * @return converter id
	 */
	public final javax.faces.convert.Converter getSuggestionConverter() {
		return getSuggestionConverter(null);
	}

	/**
	 * Returns a string specifying a converter id or a binding to a converter object. This converter will be used for the values of the list items.
	 * @return converter id
	 */
	public final javax.faces.convert.Converter getSuggestionConverter(javax.faces.context.FacesContext facesContext) {
		return (javax.faces.convert.Converter)engine.getValue(Properties.SUGGESTION_CONVERTER, facesContext);
	}

	/**
	 * Sets a string specifying a converter id or a binding to a converter object. This converter will be used for the values of the list items.
	 * @param suggestionConverter converter id
	 */
	public final void setSuggestionConverter(javax.faces.convert.Converter suggestionConverter) {
		engine.setProperty(Properties.SUGGESTION_CONVERTER, suggestionConverter);
	}

	/**
	 * Sets a string specifying a converter id or a binding to a converter object. This converter will be used for the values of the list items.
	 * @param suggestionConverter converter id
	 */
	public final void setSuggestionConverter(ValueBinding suggestionConverter) {
		engine.setProperty(Properties.SUGGESTION_CONVERTER, suggestionConverter);
	}

	/**
	 * Returns <code>true</code> if the attribute "suggestionConverter" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSuggestionConverterSetted() {
		return engine.isPropertySetted(Properties.SUGGESTION_CONVERTER);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
