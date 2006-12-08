package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.SuggestTextEntryComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class SuggestTextEntryTag extends TextEntryTag implements Tag {


	private static final Log LOG=LogFactory.getLog(SuggestTextEntryTag.class);

	private String filterProperties;
	private String maxResultNumber;
	private String suggestionListeners;
	private String menuListeners;
	private String suggestionDelayMs;
	private String suggestionMinChars;
	private String caseSensitive;
	private String forceProposal;
	private String suggestionValue;
	private String suggestionConverter;
	public String getComponentType() {
		return SuggestTextEntryComponent.COMPONENT_TYPE;
	}

	public final String getFilterProperties() {
		return filterProperties;
	}

	public final void setFilterProperties(String filterProperties) {
		this.filterProperties = filterProperties;
	}

	public final String getMaxResultNumber() {
		return maxResultNumber;
	}

	public final void setMaxResultNumber(String maxResultNumber) {
		this.maxResultNumber = maxResultNumber;
	}

	public final String getSuggestionListener() {
		return suggestionListeners;
	}

	public final void setSuggestionListener(String suggestionListeners) {
		this.suggestionListeners = suggestionListeners;
	}

	public final String getMenuListener() {
		return menuListeners;
	}

	public final void setMenuListener(String menuListeners) {
		this.menuListeners = menuListeners;
	}

	public final String getSuggestionDelayMs() {
		return suggestionDelayMs;
	}

	public final void setSuggestionDelayMs(String suggestionDelayMs) {
		this.suggestionDelayMs = suggestionDelayMs;
	}

	public final String getSuggestionMinChars() {
		return suggestionMinChars;
	}

	public final void setSuggestionMinChars(String suggestionMinChars) {
		this.suggestionMinChars = suggestionMinChars;
	}

	public final String getCaseSensitive() {
		return caseSensitive;
	}

	public final void setCaseSensitive(String caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	public final String getForceProposal() {
		return forceProposal;
	}

	public final void setForceProposal(String forceProposal) {
		this.forceProposal = forceProposal;
	}

	public final String getSuggestionValue() {
		return suggestionValue;
	}

	public final void setSuggestionValue(String suggestionValue) {
		this.suggestionValue = suggestionValue;
	}

	public final String getSuggestionConverter() {
		return suggestionConverter;
	}

	public final void setSuggestionConverter(String suggestionConverter) {
		this.suggestionConverter = suggestionConverter;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (SuggestTextEntryComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  filterProperties='"+filterProperties+"'");
			LOG.debug("  maxResultNumber='"+maxResultNumber+"'");
			LOG.debug("  suggestionDelayMs='"+suggestionDelayMs+"'");
			LOG.debug("  suggestionMinChars='"+suggestionMinChars+"'");
			LOG.debug("  caseSensitive='"+caseSensitive+"'");
			LOG.debug("  forceProposal='"+forceProposal+"'");
			LOG.debug("  suggestionValue='"+suggestionValue+"'");
			LOG.debug("  suggestionConverter='"+suggestionConverter+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof SuggestTextEntryComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'SuggestTextEntryComponent'.");
		}

		SuggestTextEntryComponent component = (SuggestTextEntryComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (filterProperties != null) {
				ValueBinding vb = application.createValueBinding(filterProperties);

				component.setFilterProperties(vb);
		}

		if (maxResultNumber != null) {
			if (isValueReference(maxResultNumber)) {
				ValueBinding vb = application.createValueBinding(maxResultNumber);

				component.setMaxResultNumber(vb);
			} else {
				component.setMaxResultNumber(getInt(maxResultNumber));
			}
		}

		if (suggestionListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.SUGGESTION_LISTENER_TYPE, suggestionListeners);
		}

		if (menuListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.MENU_LISTENER_TYPE, menuListeners);
		}

		if (suggestionDelayMs != null) {
			if (isValueReference(suggestionDelayMs)) {
				ValueBinding vb = application.createValueBinding(suggestionDelayMs);
				component.setSuggestionDelayMs(vb);
			} else {
				component.setSuggestionDelayMs(getInt(suggestionDelayMs));
			}
		}

		if (suggestionMinChars != null) {
			if (isValueReference(suggestionMinChars)) {
				ValueBinding vb = application.createValueBinding(suggestionMinChars);
				component.setSuggestionMinChars(vb);
			} else {
				component.setSuggestionMinChars(getInt(suggestionMinChars));
			}
		}

		if (caseSensitive != null) {
			if (isValueReference(caseSensitive)) {
				ValueBinding vb = application.createValueBinding(caseSensitive);
				component.setCaseSensitive(vb);
			} else {
				component.setCaseSensitive(getBool(caseSensitive));
			}
		}

		if (forceProposal != null) {
			if (isValueReference(forceProposal)) {
				ValueBinding vb = application.createValueBinding(forceProposal);
				component.setForceProposal(vb);
			} else {
				component.setForceProposal(getBool(forceProposal));
			}
		}

		if (suggestionValue != null) {
			if (isValueReference(suggestionValue)) {
				ValueBinding vb = application.createValueBinding(suggestionValue);
				component.setSuggestionValue(vb);
			} else {
				component.setSuggestionValue(suggestionValue);
			}
		}

		if (suggestionConverter != null) {
			if (isValueReference(suggestionConverter)) {
				ValueBinding vb = application.createValueBinding(suggestionConverter);
				component.setSuggestionConverter(vb);
			} else {
				component.setSuggestionConverter(suggestionConverter);
			}
		}
	}

	public void release() {
		filterProperties = null;
		maxResultNumber = null;
		suggestionListeners = null;
		menuListeners = null;
		suggestionDelayMs = null;
		suggestionMinChars = null;
		caseSensitive = null;
		forceProposal = null;
		suggestionValue = null;
		suggestionConverter = null;

		super.release();
	}

}
