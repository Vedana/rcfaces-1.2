package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.component.TreeComponent;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class TreeTag extends AbstractInputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(TreeTag.class);

	private ValueExpression doubleClickListeners;
	private ValueExpression required;
	private ValueExpression horizontalScrollPosition;
	private ValueExpression verticalScrollPosition;
	private ValueExpression border;
	private ValueExpression readOnly;
	private ValueExpression showValue;
	private ValueExpression checkable;
	private ValueExpression checkCardinality;
	private ValueExpression checkListeners;
	private ValueExpression checkedValues;
	private ValueExpression clientCheckFullState;
	private ValueExpression selectable;
	private ValueExpression selectionCardinality;
	private ValueExpression selectionListeners;
	private ValueExpression selectedValues;
	private ValueExpression clientSelectionFullState;
	private ValueExpression preloadedLevelDepth;
	private ValueExpression expandable;
	private ValueExpression expandedValues;
	private ValueExpression defaultImageURL;
	private ValueExpression defaultSelectedImageURL;
	private ValueExpression defaultExpandedImageURL;
	private ValueExpression defaultDisabledImageURL;
	private ValueExpression defaultLeafImageURL;
	private ValueExpression defaultSelectedLeafImageURL;
	private ValueExpression defaultExpandedLeafImageURL;
	private ValueExpression defaultDisabledLeafImageURL;
	private ValueExpression hideRootExpandSign;
	private ValueExpression cursorValue;
	private ValueExpression expansionUseValue;
	public String getComponentType() {
		return TreeComponent.COMPONENT_TYPE;
	}

	public final void setDoubleClickListener(ValueExpression doubleClickListeners) {
		this.doubleClickListeners = doubleClickListeners;
	}

	public final void setRequired(ValueExpression required) {
		this.required = required;
	}

	public final void setHorizontalScrollPosition(ValueExpression horizontalScrollPosition) {
		this.horizontalScrollPosition = horizontalScrollPosition;
	}

	public final void setVerticalScrollPosition(ValueExpression verticalScrollPosition) {
		this.verticalScrollPosition = verticalScrollPosition;
	}

	public final void setBorder(ValueExpression border) {
		this.border = border;
	}

	public final void setReadOnly(ValueExpression readOnly) {
		this.readOnly = readOnly;
	}

	public final void setShowValue(ValueExpression showValue) {
		this.showValue = showValue;
	}

	public final void setCheckable(ValueExpression checkable) {
		this.checkable = checkable;
	}

	public final void setCheckCardinality(ValueExpression checkCardinality) {
		this.checkCardinality = checkCardinality;
	}

	public final void setCheckListener(ValueExpression checkListeners) {
		this.checkListeners = checkListeners;
	}

	public final void setCheckedValues(ValueExpression checkedValues) {
		this.checkedValues = checkedValues;
	}

	public final void setClientCheckFullState(ValueExpression clientCheckFullState) {
		this.clientCheckFullState = clientCheckFullState;
	}

	public final void setSelectable(ValueExpression selectable) {
		this.selectable = selectable;
	}

	public final void setSelectionCardinality(ValueExpression selectionCardinality) {
		this.selectionCardinality = selectionCardinality;
	}

	public final void setSelectionListener(ValueExpression selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public final void setSelectedValues(ValueExpression selectedValues) {
		this.selectedValues = selectedValues;
	}

	public final void setClientSelectionFullState(ValueExpression clientSelectionFullState) {
		this.clientSelectionFullState = clientSelectionFullState;
	}

	public final void setPreloadedLevelDepth(ValueExpression preloadedLevelDepth) {
		this.preloadedLevelDepth = preloadedLevelDepth;
	}

	public final void setExpandable(ValueExpression expandable) {
		this.expandable = expandable;
	}

	public final void setExpandedValues(ValueExpression expandedValues) {
		this.expandedValues = expandedValues;
	}

	public final void setDefaultImageURL(ValueExpression defaultImageURL) {
		this.defaultImageURL = defaultImageURL;
	}

	public final void setDefaultSelectedImageURL(ValueExpression defaultSelectedImageURL) {
		this.defaultSelectedImageURL = defaultSelectedImageURL;
	}

	public final void setDefaultExpandedImageURL(ValueExpression defaultExpandedImageURL) {
		this.defaultExpandedImageURL = defaultExpandedImageURL;
	}

	public final void setDefaultDisabledImageURL(ValueExpression defaultDisabledImageURL) {
		this.defaultDisabledImageURL = defaultDisabledImageURL;
	}

	public final void setDefaultLeafImageURL(ValueExpression defaultLeafImageURL) {
		this.defaultLeafImageURL = defaultLeafImageURL;
	}

	public final void setDefaultSelectedLeafImageURL(ValueExpression defaultSelectedLeafImageURL) {
		this.defaultSelectedLeafImageURL = defaultSelectedLeafImageURL;
	}

	public final void setDefaultExpandedLeafImageURL(ValueExpression defaultExpandedLeafImageURL) {
		this.defaultExpandedLeafImageURL = defaultExpandedLeafImageURL;
	}

	public final void setDefaultDisabledLeafImageURL(ValueExpression defaultDisabledLeafImageURL) {
		this.defaultDisabledLeafImageURL = defaultDisabledLeafImageURL;
	}

	public final void setHideRootExpandSign(ValueExpression hideRootExpandSign) {
		this.hideRootExpandSign = hideRootExpandSign;
	}

	public final void setCursorValue(ValueExpression cursorValue) {
		this.cursorValue = cursorValue;
	}

	public final void setExpansionUseValue(ValueExpression expansionUseValue) {
		this.expansionUseValue = expansionUseValue;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (TreeComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  required='"+required+"'");
			LOG.debug("  horizontalScrollPosition='"+horizontalScrollPosition+"'");
			LOG.debug("  verticalScrollPosition='"+verticalScrollPosition+"'");
			LOG.debug("  border='"+border+"'");
			LOG.debug("  readOnly='"+readOnly+"'");
			LOG.debug("  showValue='"+showValue+"'");
			LOG.debug("  checkable='"+checkable+"'");
			LOG.debug("  checkCardinality='"+checkCardinality+"'");
			LOG.debug("  checkedValues='"+checkedValues+"'");
			LOG.debug("  clientCheckFullState='"+clientCheckFullState+"'");
			LOG.debug("  selectable='"+selectable+"'");
			LOG.debug("  selectionCardinality='"+selectionCardinality+"'");
			LOG.debug("  selectedValues='"+selectedValues+"'");
			LOG.debug("  clientSelectionFullState='"+clientSelectionFullState+"'");
			LOG.debug("  preloadedLevelDepth='"+preloadedLevelDepth+"'");
			LOG.debug("  expandable='"+expandable+"'");
			LOG.debug("  expandedValues='"+expandedValues+"'");
			LOG.debug("  defaultImageURL='"+defaultImageURL+"'");
			LOG.debug("  defaultSelectedImageURL='"+defaultSelectedImageURL+"'");
			LOG.debug("  defaultExpandedImageURL='"+defaultExpandedImageURL+"'");
			LOG.debug("  defaultDisabledImageURL='"+defaultDisabledImageURL+"'");
			LOG.debug("  defaultLeafImageURL='"+defaultLeafImageURL+"'");
			LOG.debug("  defaultSelectedLeafImageURL='"+defaultSelectedLeafImageURL+"'");
			LOG.debug("  defaultExpandedLeafImageURL='"+defaultExpandedLeafImageURL+"'");
			LOG.debug("  defaultDisabledLeafImageURL='"+defaultDisabledLeafImageURL+"'");
			LOG.debug("  hideRootExpandSign='"+hideRootExpandSign+"'");
			LOG.debug("  cursorValue='"+cursorValue+"'");
			LOG.debug("  expansionUseValue='"+expansionUseValue+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof TreeComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'TreeComponent'.");
		}

		TreeComponent component = (TreeComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (doubleClickListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.DOUBLE_CLICK_LISTENER_TYPE, doubleClickListeners);
		}

		if (required != null) {
			if (required.isLiteralText()==false) {
				component.setValueExpression(Properties.REQUIRED, required);

			} else {
				component.setRequired(getBool(required.getExpressionString()));
			}
		}

		if (horizontalScrollPosition != null) {
			if (horizontalScrollPosition.isLiteralText()==false) {
				component.setValueExpression(Properties.HORIZONTAL_SCROLL_POSITION, horizontalScrollPosition);

			} else {
				component.setHorizontalScrollPosition(getInt(horizontalScrollPosition.getExpressionString()));
			}
		}

		if (verticalScrollPosition != null) {
			if (verticalScrollPosition.isLiteralText()==false) {
				component.setValueExpression(Properties.VERTICAL_SCROLL_POSITION, verticalScrollPosition);

			} else {
				component.setVerticalScrollPosition(getInt(verticalScrollPosition.getExpressionString()));
			}
		}

		if (border != null) {
			if (border.isLiteralText()==false) {
				component.setValueExpression(Properties.BORDER, border);

			} else {
				component.setBorder(getBool(border.getExpressionString()));
			}
		}

		if (readOnly != null) {
			if (readOnly.isLiteralText()==false) {
				component.setValueExpression(Properties.READ_ONLY, readOnly);

			} else {
				component.setReadOnly(getBool(readOnly.getExpressionString()));
			}
		}

		if (showValue != null) {
			if (showValue.isLiteralText()==false) {
				component.setValueExpression(Properties.SHOW_VALUE, showValue);

			} else {
				component.setShowValue(showValue.getExpressionString());
			}
		}

		if (checkable != null) {
			if (checkable.isLiteralText()==false) {
				component.setValueExpression(Properties.CHECKABLE, checkable);

			} else {
				component.setCheckable(getBool(checkable.getExpressionString()));
			}
		}

		if (checkCardinality != null) {
			if (checkCardinality.isLiteralText()==false) {
				component.setValueExpression(Properties.CHECK_CARDINALITY, checkCardinality);

			} else {
				component.setCheckCardinality(checkCardinality.getExpressionString());
			}
		}

		if (checkListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.CHECK_LISTENER_TYPE, checkListeners);
		}

		if (checkedValues != null) {
				component.setValueExpression(Properties.CHECKED_VALUES, checkedValues);
		}

		if (clientCheckFullState != null) {
			if (clientCheckFullState.isLiteralText()==false) {
				component.setValueExpression(Properties.CLIENT_CHECK_FULL_STATE, clientCheckFullState);

			} else {
				component.setClientCheckFullState(clientCheckFullState.getExpressionString());
			}
		}

		if (selectable != null) {
			if (selectable.isLiteralText()==false) {
				component.setValueExpression(Properties.SELECTABLE, selectable);

			} else {
				component.setSelectable(getBool(selectable.getExpressionString()));
			}
		}

		if (selectionCardinality != null) {
			if (selectionCardinality.isLiteralText()==false) {
				component.setValueExpression(Properties.SELECTION_CARDINALITY, selectionCardinality);

			} else {
				component.setSelectionCardinality(selectionCardinality.getExpressionString());
			}
		}

		if (selectionListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (selectedValues != null) {
				component.setValueExpression(Properties.SELECTED_VALUES, selectedValues);
		}

		if (clientSelectionFullState != null) {
			if (clientSelectionFullState.isLiteralText()==false) {
				component.setValueExpression(Properties.CLIENT_SELECTION_FULL_STATE, clientSelectionFullState);

			} else {
				component.setClientSelectionFullState(clientSelectionFullState.getExpressionString());
			}
		}

		if (preloadedLevelDepth != null) {
			if (preloadedLevelDepth.isLiteralText()==false) {
				component.setValueExpression(Properties.PRELOADED_LEVEL_DEPTH, preloadedLevelDepth);

			} else {
				component.setPreloadedLevelDepth(getInt(preloadedLevelDepth.getExpressionString()));
			}
		}

		if (expandable != null) {
			if (expandable.isLiteralText()==false) {
				component.setValueExpression(Properties.EXPANDABLE, expandable);

			} else {
				component.setExpandable(getBool(expandable.getExpressionString()));
			}
		}

		if (expandedValues != null) {
				component.setValueExpression(Properties.EXPANDED_VALUES, expandedValues);
		}

		if (defaultImageURL != null) {
			if (defaultImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.DEFAULT_IMAGE_URL, defaultImageURL);

			} else {
				component.setDefaultImageURL(defaultImageURL.getExpressionString());
			}
		}

		if (defaultSelectedImageURL != null) {
			if (defaultSelectedImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.DEFAULT_SELECTED_IMAGE_URL, defaultSelectedImageURL);

			} else {
				component.setDefaultSelectedImageURL(defaultSelectedImageURL.getExpressionString());
			}
		}

		if (defaultExpandedImageURL != null) {
			if (defaultExpandedImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.DEFAULT_EXPANDED_IMAGE_URL, defaultExpandedImageURL);

			} else {
				component.setDefaultExpandedImageURL(defaultExpandedImageURL.getExpressionString());
			}
		}

		if (defaultDisabledImageURL != null) {
			if (defaultDisabledImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.DEFAULT_DISABLED_IMAGE_URL, defaultDisabledImageURL);

			} else {
				component.setDefaultDisabledImageURL(defaultDisabledImageURL.getExpressionString());
			}
		}

		if (defaultLeafImageURL != null) {
			if (defaultLeafImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.DEFAULT_LEAF_IMAGE_URL, defaultLeafImageURL);

			} else {
				component.setDefaultLeafImageURL(defaultLeafImageURL.getExpressionString());
			}
		}

		if (defaultSelectedLeafImageURL != null) {
			if (defaultSelectedLeafImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.DEFAULT_SELECTED_LEAF_IMAGE_URL, defaultSelectedLeafImageURL);

			} else {
				component.setDefaultSelectedLeafImageURL(defaultSelectedLeafImageURL.getExpressionString());
			}
		}

		if (defaultExpandedLeafImageURL != null) {
			if (defaultExpandedLeafImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.DEFAULT_EXPANDED_LEAF_IMAGE_URL, defaultExpandedLeafImageURL);

			} else {
				component.setDefaultExpandedLeafImageURL(defaultExpandedLeafImageURL.getExpressionString());
			}
		}

		if (defaultDisabledLeafImageURL != null) {
			if (defaultDisabledLeafImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.DEFAULT_DISABLED_LEAF_IMAGE_URL, defaultDisabledLeafImageURL);

			} else {
				component.setDefaultDisabledLeafImageURL(defaultDisabledLeafImageURL.getExpressionString());
			}
		}

		if (hideRootExpandSign != null) {
			if (hideRootExpandSign.isLiteralText()==false) {
				component.setValueExpression(Properties.HIDE_ROOT_EXPAND_SIGN, hideRootExpandSign);

			} else {
				component.setHideRootExpandSign(getBool(hideRootExpandSign.getExpressionString()));
			}
		}

		if (cursorValue != null) {
			if (cursorValue.isLiteralText()==false) {
				component.setValueExpression(Properties.CURSOR_VALUE, cursorValue);

			} else {
				component.setCursorValue(cursorValue.getExpressionString());
			}
		}

		if (expansionUseValue != null) {
			if (expansionUseValue.isLiteralText()==false) {
				component.setValueExpression(Properties.EXPANSION_USE_VALUE, expansionUseValue);

			} else {
				component.setExpansionUseValue(getBool(expansionUseValue.getExpressionString()));
			}
		}
	}

	public void release() {
		doubleClickListeners = null;
		required = null;
		horizontalScrollPosition = null;
		verticalScrollPosition = null;
		border = null;
		readOnly = null;
		showValue = null;
		checkable = null;
		checkCardinality = null;
		checkListeners = null;
		checkedValues = null;
		clientCheckFullState = null;
		selectable = null;
		selectionCardinality = null;
		selectionListeners = null;
		selectedValues = null;
		clientSelectionFullState = null;
		preloadedLevelDepth = null;
		expandable = null;
		expandedValues = null;
		defaultImageURL = null;
		defaultSelectedImageURL = null;
		defaultExpandedImageURL = null;
		defaultDisabledImageURL = null;
		defaultLeafImageURL = null;
		defaultSelectedLeafImageURL = null;
		defaultExpandedLeafImageURL = null;
		defaultDisabledLeafImageURL = null;
		hideRootExpandSign = null;
		cursorValue = null;
		expansionUseValue = null;

		super.release();
	}

}
