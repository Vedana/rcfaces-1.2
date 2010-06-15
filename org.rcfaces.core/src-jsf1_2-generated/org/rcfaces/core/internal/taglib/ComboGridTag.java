package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import javax.el.ValueExpression;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.ComboGridComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class ComboGridTag extends KeyEntryTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ComboGridTag.class);

	private ValueExpression selectionListeners;
	private ValueExpression additionalInformationListeners;
	private ValueExpression additionalInformationValues;
	private ValueExpression clientAdditionalInformationFullState;
	private ValueExpression additionalInformationCardinality;
	private ValueExpression rowStyleClass;
	private ValueExpression manyResultsMessage;
	private ValueExpression message;
	private ValueExpression oneResultMessage;
	private ValueExpression zeroResultMessage;
	private ValueExpression paged;
	private ValueExpression headerVisible;
	private ValueExpression popupWidth;
	private ValueExpression popupHeight;
	private ValueExpression pagerStyleClass;
	private ValueExpression pagerLookId;
	private ValueExpression popupStyleClass;
	private ValueExpression gridStyleClass;
	private ValueExpression gridLookId;
	private ValueExpression searchFieldVisible;
	public String getComponentType() {
		return ComboGridComponent.COMPONENT_TYPE;
	}

	public final void setSelectionListener(ValueExpression selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public final void setAdditionalInformationListener(ValueExpression additionalInformationListeners) {
		this.additionalInformationListeners = additionalInformationListeners;
	}

	public final void setAdditionalInformationValues(ValueExpression additionalInformationValues) {
		this.additionalInformationValues = additionalInformationValues;
	}

	public final void setClientAdditionalInformationFullState(ValueExpression clientAdditionalInformationFullState) {
		this.clientAdditionalInformationFullState = clientAdditionalInformationFullState;
	}

	public final void setAdditionalInformationCardinality(ValueExpression additionalInformationCardinality) {
		this.additionalInformationCardinality = additionalInformationCardinality;
	}

	public final void setRowStyleClass(ValueExpression rowStyleClass) {
		this.rowStyleClass = rowStyleClass;
	}

	public final void setManyResultsMessage(ValueExpression manyResultsMessage) {
		this.manyResultsMessage = manyResultsMessage;
	}

	public final void setMessage(ValueExpression message) {
		this.message = message;
	}

	public final void setOneResultMessage(ValueExpression oneResultMessage) {
		this.oneResultMessage = oneResultMessage;
	}

	public final void setZeroResultMessage(ValueExpression zeroResultMessage) {
		this.zeroResultMessage = zeroResultMessage;
	}

	public final void setPaged(ValueExpression paged) {
		this.paged = paged;
	}

	public final void setHeaderVisible(ValueExpression headerVisible) {
		this.headerVisible = headerVisible;
	}

	public final void setPopupWidth(ValueExpression popupWidth) {
		this.popupWidth = popupWidth;
	}

	public final void setPopupHeight(ValueExpression popupHeight) {
		this.popupHeight = popupHeight;
	}

	public final void setPagerStyleClass(ValueExpression pagerStyleClass) {
		this.pagerStyleClass = pagerStyleClass;
	}

	public final void setPagerLookId(ValueExpression pagerLookId) {
		this.pagerLookId = pagerLookId;
	}

	public final void setPopupStyleClass(ValueExpression popupStyleClass) {
		this.popupStyleClass = popupStyleClass;
	}

	public final void setGridStyleClass(ValueExpression gridStyleClass) {
		this.gridStyleClass = gridStyleClass;
	}

	public final void setGridLookId(ValueExpression gridLookId) {
		this.gridLookId = gridLookId;
	}

	public final void setSearchFieldVisible(ValueExpression searchFieldVisible) {
		this.searchFieldVisible = searchFieldVisible;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ComboGridComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  additionalInformationValues='"+additionalInformationValues+"'");
			LOG.debug("  clientAdditionalInformationFullState='"+clientAdditionalInformationFullState+"'");
			LOG.debug("  additionalInformationCardinality='"+additionalInformationCardinality+"'");
			LOG.debug("  rowStyleClass='"+rowStyleClass+"'");
			LOG.debug("  manyResultsMessage='"+manyResultsMessage+"'");
			LOG.debug("  message='"+message+"'");
			LOG.debug("  oneResultMessage='"+oneResultMessage+"'");
			LOG.debug("  zeroResultMessage='"+zeroResultMessage+"'");
			LOG.debug("  paged='"+paged+"'");
			LOG.debug("  headerVisible='"+headerVisible+"'");
			LOG.debug("  popupWidth='"+popupWidth+"'");
			LOG.debug("  popupHeight='"+popupHeight+"'");
			LOG.debug("  pagerStyleClass='"+pagerStyleClass+"'");
			LOG.debug("  pagerLookId='"+pagerLookId+"'");
			LOG.debug("  popupStyleClass='"+popupStyleClass+"'");
			LOG.debug("  gridStyleClass='"+gridStyleClass+"'");
			LOG.debug("  gridLookId='"+gridLookId+"'");
			LOG.debug("  searchFieldVisible='"+searchFieldVisible+"'");
		}
		if ((uiComponent instanceof ComboGridComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ComboGridComponent'.");
		}

		super.setProperties(uiComponent);

		ComboGridComponent component = (ComboGridComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (selectionListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (additionalInformationListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.ADDITIONAL_INFORMATION_LISTENER_TYPE, additionalInformationListeners);
		}

		if (additionalInformationValues != null) {
				component.setValueExpression(Properties.ADDITIONAL_INFORMATION_VALUES, additionalInformationValues);
		}

		if (clientAdditionalInformationFullState != null) {
			if (clientAdditionalInformationFullState.isLiteralText()==false) {
				component.setValueExpression(Properties.CLIENT_ADDITIONAL_INFORMATION_FULL_STATE, clientAdditionalInformationFullState);

			} else {
				component.setClientAdditionalInformationFullState(clientAdditionalInformationFullState.getExpressionString());
			}
		}

		if (additionalInformationCardinality != null) {
			if (additionalInformationCardinality.isLiteralText()==false) {
				component.setValueExpression(Properties.ADDITIONAL_INFORMATION_CARDINALITY, additionalInformationCardinality);

			} else {
				component.setAdditionalInformationCardinality(getInt(additionalInformationCardinality.getExpressionString()));
			}
		}

		if (rowStyleClass != null) {
			if (rowStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.ROW_STYLE_CLASS, rowStyleClass);

			} else {
				component.setRowStyleClass(rowStyleClass.getExpressionString());
			}
		}

		if (manyResultsMessage != null) {
			if (manyResultsMessage.isLiteralText()==false) {
				component.setValueExpression(Properties.MANY_RESULTS_MESSAGE, manyResultsMessage);

			} else {
				component.setManyResultsMessage(manyResultsMessage.getExpressionString());
			}
		}

		if (message != null) {
			if (message.isLiteralText()==false) {
				component.setValueExpression(Properties.MESSAGE, message);

			} else {
				component.setMessage(message.getExpressionString());
			}
		}

		if (oneResultMessage != null) {
			if (oneResultMessage.isLiteralText()==false) {
				component.setValueExpression(Properties.ONE_RESULT_MESSAGE, oneResultMessage);

			} else {
				component.setOneResultMessage(oneResultMessage.getExpressionString());
			}
		}

		if (zeroResultMessage != null) {
			if (zeroResultMessage.isLiteralText()==false) {
				component.setValueExpression(Properties.ZERO_RESULT_MESSAGE, zeroResultMessage);

			} else {
				component.setZeroResultMessage(zeroResultMessage.getExpressionString());
			}
		}

		if (paged != null) {
			if (paged.isLiteralText()==false) {
				component.setValueExpression(Properties.PAGED, paged);

			} else {
				component.setPaged(getBool(paged.getExpressionString()));
			}
		}

		if (headerVisible != null) {
			if (headerVisible.isLiteralText()==false) {
				component.setValueExpression(Properties.HEADER_VISIBLE, headerVisible);

			} else {
				component.setHeaderVisible(getBool(headerVisible.getExpressionString()));
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

		if (pagerStyleClass != null) {
			if (pagerStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.PAGER_STYLE_CLASS, pagerStyleClass);

			} else {
				component.setPagerStyleClass(pagerStyleClass.getExpressionString());
			}
		}

		if (pagerLookId != null) {
			if (pagerLookId.isLiteralText()==false) {
				component.setValueExpression(Properties.PAGER_LOOK_ID, pagerLookId);

			} else {
				component.setPagerLookId(pagerLookId.getExpressionString());
			}
		}

		if (popupStyleClass != null) {
			if (popupStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.POPUP_STYLE_CLASS, popupStyleClass);

			} else {
				component.setPopupStyleClass(popupStyleClass.getExpressionString());
			}
		}

		if (gridStyleClass != null) {
			if (gridStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.GRID_STYLE_CLASS, gridStyleClass);

			} else {
				component.setGridStyleClass(gridStyleClass.getExpressionString());
			}
		}

		if (gridLookId != null) {
			if (gridLookId.isLiteralText()==false) {
				component.setValueExpression(Properties.GRID_LOOK_ID, gridLookId);

			} else {
				component.setGridLookId(gridLookId.getExpressionString());
			}
		}

		if (searchFieldVisible != null) {
			if (searchFieldVisible.isLiteralText()==false) {
				component.setValueExpression(Properties.SEARCH_FIELD_VISIBLE, searchFieldVisible);

			} else {
				component.setSearchFieldVisible(getBool(searchFieldVisible.getExpressionString()));
			}
		}
	}

	public void release() {
		selectionListeners = null;
		additionalInformationListeners = null;
		additionalInformationValues = null;
		clientAdditionalInformationFullState = null;
		additionalInformationCardinality = null;
		rowStyleClass = null;
		manyResultsMessage = null;
		message = null;
		oneResultMessage = null;
		zeroResultMessage = null;
		paged = null;
		headerVisible = null;
		popupWidth = null;
		popupHeight = null;
		pagerStyleClass = null;
		pagerLookId = null;
		popupStyleClass = null;
		gridStyleClass = null;
		gridLookId = null;
		searchFieldVisible = null;

		super.release();
	}

}
