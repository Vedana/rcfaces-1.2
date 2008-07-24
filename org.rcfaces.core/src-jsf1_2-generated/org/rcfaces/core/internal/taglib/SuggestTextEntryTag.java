package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.SuggestTextEntryComponent;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class SuggestTextEntryTag extends TextEntryTag implements Tag {


	private static final Log LOG=LogFactory.getLog(SuggestTextEntryTag.class);

	private ValueExpression filterProperties;
	private ValueExpression maxResultNumber;
	private ValueExpression suggestionListeners;
	private ValueExpression menuListeners;
	private ValueExpression suggestionDelayMs;
	private ValueExpression suggestionMinChars;
	private ValueExpression caseSensitive;
	private ValueExpression forceProposal;
	private ValueExpression suggestionValue;
	private ValueExpression suggestionConverter;
	private ValueExpression moreResultsMessage;
	private ValueExpression orderedItems;
	public String getComponentType() {
		return SuggestTextEntryComponent.COMPONENT_TYPE;
	}

	public final void setFilterProperties(ValueExpression filterProperties) {
		this.filterProperties = filterProperties;
	}

	public final void setMaxResultNumber(ValueExpression maxResultNumber) {
		this.maxResultNumber = maxResultNumber;
	}

	public final void setSuggestionListener(ValueExpression suggestionListeners) {
		this.suggestionListeners = suggestionListeners;
	}

	public final void setMenuListener(ValueExpression menuListeners) {
		this.menuListeners = menuListeners;
	}

	public final void setSuggestionDelayMs(ValueExpression suggestionDelayMs) {
		this.suggestionDelayMs = suggestionDelayMs;
	}

	public final void setSuggestionMinChars(ValueExpression suggestionMinChars) {
		this.suggestionMinChars = suggestionMinChars;
	}

	public final void setCaseSensitive(ValueExpression caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	public final void setForceProposal(ValueExpression forceProposal) {
		this.forceProposal = forceProposal;
	}

	public final void setSuggestionValue(ValueExpression suggestionValue) {
		this.suggestionValue = suggestionValue;
	}

	public final void setSuggestionConverter(ValueExpression suggestionConverter) {
		this.suggestionConverter = suggestionConverter;
	}

	public final void setMoreResultsMessage(ValueExpression moreResultsMessage) {
		this.moreResultsMessage = moreResultsMessage;
	}

	public final void setOrderedItems(ValueExpression orderedItems) {
		this.orderedItems = orderedItems;
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
			LOG.debug("  moreResultsMessage='"+moreResultsMessage+"'");
			LOG.debug("  orderedItems='"+orderedItems+"'");
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

		if (filterProperties != null) {
			if (filterProperties.isLiteralText()==false) {
				component.setValueExpression(Properties.FILTER_PROPERTIES, filterProperties);

			} else {
				component.setFilterProperties(filterProperties.getExpressionString());
			}
		}

		if (maxResultNumber != null) {
			if (maxResultNumber.isLiteralText()==false) {
				component.setValueExpression(Properties.MAX_RESULT_NUMBER, maxResultNumber);

			} else {
				component.setMaxResultNumber(getInt(maxResultNumber.getExpressionString()));
			}
		}

		if (suggestionListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.SUGGESTION_LISTENER_TYPE, suggestionListeners);
		}

		if (menuListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.MENU_LISTENER_TYPE, menuListeners);
		}

		if (suggestionDelayMs != null) {
			if (suggestionDelayMs.isLiteralText()==false) {
				component.setValueExpression(Properties.SUGGESTION_DELAY_MS, suggestionDelayMs);

			} else {
				component.setSuggestionDelayMs(getInt(suggestionDelayMs.getExpressionString()));
			}
		}

		if (suggestionMinChars != null) {
			if (suggestionMinChars.isLiteralText()==false) {
				component.setValueExpression(Properties.SUGGESTION_MIN_CHARS, suggestionMinChars);

			} else {
				component.setSuggestionMinChars(getInt(suggestionMinChars.getExpressionString()));
			}
		}

		if (caseSensitive != null) {
			if (caseSensitive.isLiteralText()==false) {
				component.setValueExpression(Properties.CASE_SENSITIVE, caseSensitive);

			} else {
				component.setCaseSensitive(getBool(caseSensitive.getExpressionString()));
			}
		}

		if (forceProposal != null) {
			if (forceProposal.isLiteralText()==false) {
				component.setValueExpression(Properties.FORCE_PROPOSAL, forceProposal);

			} else {
				component.setForceProposal(getBool(forceProposal.getExpressionString()));
			}
		}

		if (suggestionValue != null) {
			if (suggestionValue.isLiteralText()==false) {
				component.setValueExpression(Properties.SUGGESTION_VALUE, suggestionValue);

			} else {
				component.setSuggestionValue(suggestionValue.getExpressionString());
			}
		}

		if (suggestionConverter != null) {
			if (suggestionConverter.isLiteralText()==false) {
				component.setValueExpression(Properties.SUGGESTION_CONVERTER, suggestionConverter);

			} else {
				component.setSuggestionConverter(suggestionConverter.getExpressionString());
			}
		}

		if (moreResultsMessage != null) {
			if (moreResultsMessage.isLiteralText()==false) {
				component.setValueExpression(Properties.MORE_RESULTS_MESSAGE, moreResultsMessage);

			} else {
				component.setMoreResultsMessage(moreResultsMessage.getExpressionString());
			}
		}

		if (orderedItems != null) {
			if (orderedItems.isLiteralText()==false) {
				component.setValueExpression(Properties.ORDERED_ITEMS, orderedItems);

			} else {
				component.setOrderedItems(getBool(orderedItems.getExpressionString()));
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
		moreResultsMessage = null;
		orderedItems = null;

		super.release();
	}

}
