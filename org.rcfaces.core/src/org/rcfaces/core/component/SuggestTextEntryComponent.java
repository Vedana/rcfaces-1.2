package org.rcfaces.core.component;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.component.capability.IMaxResultNumberCapability;
import org.rcfaces.core.component.capability.IMenuEventCapability;
import org.rcfaces.core.component.capability.ISuggestionEventCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ComponentTools;

public class SuggestTextEntryComponent extends TextEntryComponent implements
        IFilterCapability, IMaxResultNumberCapability,
        ISuggestionEventCapability, IMenuEventCapability {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.suggestTextEntry";

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

    public final void setSuggestionConverter(String converterId,
            FacesContext facesContext) {

        Converter converter = ComponentTools.createConverter(facesContext,
                converterId);

        setSuggestionConverter(converter);

    }

    public final org.rcfaces.core.model.IFilterProperties getFilterProperties() {
        return getFilterProperties(null);
    }

    public final org.rcfaces.core.model.IFilterProperties getFilterProperties(
            javax.faces.context.FacesContext facesContext) {
        return (org.rcfaces.core.model.IFilterProperties) engine.getProperty(
                Properties.FILTER_PROPERTIES, facesContext);
    }

    public final void setFilterProperties(
            org.rcfaces.core.model.IFilterProperties filterProperties) {
        engine.setProperty(Properties.FILTER_PROPERTIES, filterProperties);
    }

    public final void setFilterProperties(ValueBinding filterProperties) {
        engine.setProperty(Properties.FILTER_PROPERTIES, filterProperties);
    }

    public final int getMaxResultNumber() {
        return getMaxResultNumber(null);
    }

    public final int getMaxResultNumber(
            javax.faces.context.FacesContext facesContext) {
        return engine.getIntProperty(Properties.MAX_RESULT_NUMBER, 0,
                facesContext);
    }

    public final void setMaxResultNumber(int maxResultNumber) {
        engine.setProperty(Properties.MAX_RESULT_NUMBER, maxResultNumber);
    }

    public final void setMaxResultNumber(ValueBinding maxResultNumber) {
        engine.setProperty(Properties.MAX_RESULT_NUMBER, maxResultNumber);
    }

    public final void addSuggestionListener(
            org.rcfaces.core.event.ISuggestionListener listener) {
        addFacesListener(listener);
    }

    public final void removeSuggestionListener(
            org.rcfaces.core.event.ISuggestionListener listener) {
        removeFacesListener(listener);
    }

    public final javax.faces.event.FacesListener[] listSuggestionListeners() {
        return getFacesListeners(org.rcfaces.core.event.ISuggestionListener.class);
    }

    public final void addMenuListener(
            org.rcfaces.core.event.IMenuListener listener) {
        addFacesListener(listener);
    }

    public final void removeMenuListener(
            org.rcfaces.core.event.IMenuListener listener) {
        removeFacesListener(listener);
    }

    public final javax.faces.event.FacesListener[] listMenuListeners() {
        return getFacesListeners(org.rcfaces.core.event.IMenuListener.class);
    }

    public final int getSuggestionDelayMs() {
        return getSuggestionDelayMs(null);
    }

    public final int getSuggestionDelayMs(
            javax.faces.context.FacesContext facesContext) {
        return engine.getIntProperty(Properties.SUGGESTION_DELAY_MS, 0,
                facesContext);
    }

    public final void setSuggestionDelayMs(int suggestionDelayMs) {
        engine.setProperty(Properties.SUGGESTION_DELAY_MS, suggestionDelayMs);
    }

    public final void setSuggestionDelayMs(ValueBinding suggestionDelayMs) {
        engine.setProperty(Properties.SUGGESTION_DELAY_MS, suggestionDelayMs);
    }

    public final boolean isSuggestionDelayMsSetted() {
        return engine.isPropertySetted(Properties.SUGGESTION_DELAY_MS);
    }

    public final int getSuggestionMinChars() {
        return getSuggestionMinChars(null);
    }

    public final int getSuggestionMinChars(
            javax.faces.context.FacesContext facesContext) {
        return engine.getIntProperty(Properties.SUGGESTION_MIN_CHARS, 0,
                facesContext);
    }

    public final void setSuggestionMinChars(int suggestionMinChars) {
        engine.setProperty(Properties.SUGGESTION_MIN_CHARS, suggestionMinChars);
    }

    public final void setSuggestionMinChars(ValueBinding suggestionMinChars) {
        engine.setProperty(Properties.SUGGESTION_MIN_CHARS, suggestionMinChars);
    }

    public final boolean isSuggestionMinCharsSetted() {
        return engine.isPropertySetted(Properties.SUGGESTION_MIN_CHARS);
    }

    public final boolean isCaseSensitive() {
        return isCaseSensitive(null);
    }

    public final boolean isCaseSensitive(
            javax.faces.context.FacesContext facesContext) {
        return engine.getBoolProperty(Properties.CASE_SENSITIVE, false,
                facesContext);
    }

    public final void setCaseSensitive(boolean caseSensitive) {
        engine.setProperty(Properties.CASE_SENSITIVE, caseSensitive);
    }

    public final void setCaseSensitive(ValueBinding caseSensitive) {
        engine.setProperty(Properties.CASE_SENSITIVE, caseSensitive);
    }

    public final boolean isCaseSensitiveSetted() {
        return engine.isPropertySetted(Properties.CASE_SENSITIVE);
    }

    public final boolean isForceProposal() {
        return isForceProposal(null);
    }

    public final boolean isForceProposal(
            javax.faces.context.FacesContext facesContext) {
        return engine.getBoolProperty(Properties.FORCE_PROPOSAL, false,
                facesContext);
    }

    public final void setForceProposal(boolean forceProposal) {
        engine.setProperty(Properties.FORCE_PROPOSAL, forceProposal);
    }

    public final void setForceProposal(ValueBinding forceProposal) {
        engine.setProperty(Properties.FORCE_PROPOSAL, forceProposal);
    }

    public final boolean isForceProposalSetted() {
        return engine.isPropertySetted(Properties.FORCE_PROPOSAL);
    }

    public final Object getSuggestionValue() {
        return getSuggestionValue(null);
    }

    public final Object getSuggestionValue(
            javax.faces.context.FacesContext facesContext) {
        return engine.getValue(Properties.SUGGESTION_VALUE, facesContext);
    }

    public final void setSuggestionValue(Object suggestionValue) {
        engine.setValue(Properties.SUGGESTION_VALUE, suggestionValue);
    }

    public final void setSuggestionValue(ValueBinding suggestionValue) {
        engine.setValueBinding(Properties.SUGGESTION_VALUE, suggestionValue);
    }

    public final boolean isSuggestionValueSetted() {
        return engine.isPropertySetted(Properties.SUGGESTION_VALUE);
    }

    public final javax.faces.convert.Converter getSuggestionConverter() {
        return getSuggestionConverter(null);
    }

    public final javax.faces.convert.Converter getSuggestionConverter(
            javax.faces.context.FacesContext facesContext) {
        return (javax.faces.convert.Converter) engine.getValue(
                Properties.SUGGESTION_CONVERTER, facesContext);
    }

    public final void setSuggestionConverter(
            javax.faces.convert.Converter suggestionConverter) {
        engine
                .setProperty(Properties.SUGGESTION_CONVERTER,
                        suggestionConverter);
    }

    public final void setSuggestionConverter(ValueBinding suggestionConverter) {
        engine
                .setProperty(Properties.SUGGESTION_CONVERTER,
                        suggestionConverter);
    }

    public final boolean isSuggestionConverterSetted() {
        return engine.isPropertySetted(Properties.SUGGESTION_CONVERTER);
    }

    public void release() {
        super.release();
    }
}
