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
	private ValueExpression showPopupForOneResult;
	private ValueExpression disableProposals;
	private ValueExpression inputFormat;
	private ValueExpression labelFormat;
	private ValueExpression descriptionFormat;
	private ValueExpression popupWidth;
	private ValueExpression popupHeight;
	public String getComponentType() {
		return SuggestTextEntryComponent.COMPONENT_TYPE;
	}

	public void setFilterProperties(ValueExpression filterProperties) {
		this.filterProperties = filterProperties;
	}

	public void setMaxResultNumber(ValueExpression maxResultNumber) {
		this.maxResultNumber = maxResultNumber;
	}

	public void setSuggestionListener(ValueExpression suggestionListeners) {
		this.suggestionListeners = suggestionListeners;
	}

	public void setMenuListener(ValueExpression menuListeners) {
		this.menuListeners = menuListeners;
	}

	public void setSuggestionDelayMs(ValueExpression suggestionDelayMs) {
		this.suggestionDelayMs = suggestionDelayMs;
	}

	public void setSuggestionMinChars(ValueExpression suggestionMinChars) {
		this.suggestionMinChars = suggestionMinChars;
	}

	public void setCaseSensitive(ValueExpression caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	public void setForceProposal(ValueExpression forceProposal) {
		this.forceProposal = forceProposal;
	}

	public void setSuggestionValue(ValueExpression suggestionValue) {
		this.suggestionValue = suggestionValue;
	}

	public void setSuggestionConverter(ValueExpression suggestionConverter) {
		this.suggestionConverter = suggestionConverter;
	}

	public void setMoreResultsMessage(ValueExpression moreResultsMessage) {
		this.moreResultsMessage = moreResultsMessage;
	}

	public void setOrderedItems(ValueExpression orderedItems) {
		this.orderedItems = orderedItems;
	}

	public void setShowPopupForOneResult(ValueExpression showPopupForOneResult) {
		this.showPopupForOneResult = showPopupForOneResult;
	}

	public void setDisableProposals(ValueExpression disableProposals) {
		this.disableProposals = disableProposals;
	}

	public void setInputFormat(ValueExpression inputFormat) {
		this.inputFormat = inputFormat;
	}

	public void setLabelFormat(ValueExpression labelFormat) {
		this.labelFormat = labelFormat;
	}

	public void setDescriptionFormat(ValueExpression descriptionFormat) {
		this.descriptionFormat = descriptionFormat;
	}

	public void setPopupWidth(ValueExpression popupWidth) {
		this.popupWidth = popupWidth;
	}

	public void setPopupHeight(ValueExpression popupHeight) {
		this.popupHeight = popupHeight;
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
			LOG.debug("  showPopupForOneResult='"+showPopupForOneResult+"'");
			LOG.debug("  disableProposals='"+disableProposals+"'");
			LOG.debug("  inputFormat='"+inputFormat+"'");
			LOG.debug("  labelFormat='"+labelFormat+"'");
			LOG.debug("  descriptionFormat='"+descriptionFormat+"'");
			LOG.debug("  popupWidth='"+popupWidth+"'");
			LOG.debug("  popupHeight='"+popupHeight+"'");
		}
		if ((uiComponent instanceof SuggestTextEntryComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'SuggestTextEntryComponent'.");
		}

		super.setProperties(uiComponent);

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

		if (showPopupForOneResult != null) {
			if (showPopupForOneResult.isLiteralText()==false) {
				component.setValueExpression(Properties.SHOW_POPUP_FOR_ONE_RESULT, showPopupForOneResult);

			} else {
				component.setShowPopupForOneResult(getBool(showPopupForOneResult.getExpressionString()));
			}
		}

		if (disableProposals != null) {
			if (disableProposals.isLiteralText()==false) {
				component.setValueExpression(Properties.DISABLE_PROPOSALS, disableProposals);

			} else {
				component.setDisableProposals(getBool(disableProposals.getExpressionString()));
			}
		}

		if (inputFormat != null) {
			if (inputFormat.isLiteralText()==false) {
				component.setValueExpression(Properties.INPUT_FORMAT, inputFormat);

			} else {
				component.setInputFormat(inputFormat.getExpressionString());
			}
		}

		if (labelFormat != null) {
			if (labelFormat.isLiteralText()==false) {
				component.setValueExpression(Properties.LABEL_FORMAT, labelFormat);

			} else {
				component.setLabelFormat(labelFormat.getExpressionString());
			}
		}

		if (descriptionFormat != null) {
			if (descriptionFormat.isLiteralText()==false) {
				component.setValueExpression(Properties.DESCRIPTION_FORMAT, descriptionFormat);

			} else {
				component.setDescriptionFormat(descriptionFormat.getExpressionString());
			}
		}

		if (popupWidth != null) {
			if (popupWidth.isLiteralText()==false) {
				component.setValueExpression(Properties.POPUP_WIDTH, popupWidth);

			} else {
				component.setPopupWidth(getInt(popupWidth.getExpressionString()));
			}
		}

		if (popupHeight != null) {
			if (popupHeight.isLiteralText()==false) {
				component.setValueExpression(Properties.POPUP_HEIGHT, popupHeight);

			} else {
				component.setPopupHeight(getInt(popupHeight.getExpressionString()));
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
		showPopupForOneResult = null;
		disableProposals = null;
		inputFormat = null;
		labelFormat = null;
		descriptionFormat = null;
		popupWidth = null;
		popupHeight = null;

		super.release();
	}

}
