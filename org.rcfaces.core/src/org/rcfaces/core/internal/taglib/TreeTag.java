package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import org.rcfaces.core.component.TreeComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class TreeTag extends AbstractInputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(TreeTag.class);

	private String doubleClickListeners;
	private String required;
	private String horizontalScrollPosition;
	private String verticalScrollPosition;
	private String border;
	private String readOnly;
	private String showValue;
	private String checkable;
	private String checkCardinality;
	private String checkListeners;
	private String checkedValues;
	private String selectable;
	private String selectionCardinality;
	private String selectionListeners;
	private String selectedValues;
	private String preloadedLevelDepth;
	private String expandable;
	private String expandedValues;
	private String defaultImageURL;
	private String defaultSelectedImageURL;
	private String defaultExpandedImageURL;
	private String defaultDisabledImageURL;
	private String defaultLeafImageURL;
	private String defaultSelectedLeafImageURL;
	private String defaultExpandedLeafImageURL;
	private String defaultDisabledLeafImageURL;
	private String hideRootExpandSign;
	private String cursorValue;
	private String expansionUseValue;
	private String clientSelectionFullState;
	private String clientCheckFullState;
	public String getComponentType() {
		return TreeComponent.COMPONENT_TYPE;
	}

	public final String getDoubleClickListener() {
		return doubleClickListeners;
	}

	public final void setDoubleClickListener(String doubleClickListeners) {
		this.doubleClickListeners = doubleClickListeners;
	}

	public final String getRequired() {
		return required;
	}

	public final void setRequired(String required) {
		this.required = required;
	}

	public final String getHorizontalScrollPosition() {
		return horizontalScrollPosition;
	}

	public final void setHorizontalScrollPosition(String horizontalScrollPosition) {
		this.horizontalScrollPosition = horizontalScrollPosition;
	}

	public final String getVerticalScrollPosition() {
		return verticalScrollPosition;
	}

	public final void setVerticalScrollPosition(String verticalScrollPosition) {
		this.verticalScrollPosition = verticalScrollPosition;
	}

	public final String getBorder() {
		return border;
	}

	public final void setBorder(String border) {
		this.border = border;
	}

	public final String getReadOnly() {
		return readOnly;
	}

	public final void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	public final String getShowValue() {
		return showValue;
	}

	public final void setShowValue(String showValue) {
		this.showValue = showValue;
	}

	public final String getCheckable() {
		return checkable;
	}

	public final void setCheckable(String checkable) {
		this.checkable = checkable;
	}

	public final String getCheckCardinality() {
		return checkCardinality;
	}

	public final void setCheckCardinality(String checkCardinality) {
		this.checkCardinality = checkCardinality;
	}

	public final String getCheckListener() {
		return checkListeners;
	}

	public final void setCheckListener(String checkListeners) {
		this.checkListeners = checkListeners;
	}

	public final String getCheckedValues() {
		return checkedValues;
	}

	public final void setCheckedValues(String checkedValues) {
		this.checkedValues = checkedValues;
	}

	public final String getSelectable() {
		return selectable;
	}

	public final void setSelectable(String selectable) {
		this.selectable = selectable;
	}

	public final String getSelectionCardinality() {
		return selectionCardinality;
	}

	public final void setSelectionCardinality(String selectionCardinality) {
		this.selectionCardinality = selectionCardinality;
	}

	public final String getSelectionListener() {
		return selectionListeners;
	}

	public final void setSelectionListener(String selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public final String getSelectedValues() {
		return selectedValues;
	}

	public final void setSelectedValues(String selectedValues) {
		this.selectedValues = selectedValues;
	}

	public final String getPreloadedLevelDepth() {
		return preloadedLevelDepth;
	}

	public final void setPreloadedLevelDepth(String preloadedLevelDepth) {
		this.preloadedLevelDepth = preloadedLevelDepth;
	}

	public final String getExpandable() {
		return expandable;
	}

	public final void setExpandable(String expandable) {
		this.expandable = expandable;
	}

	public final String getExpandedValues() {
		return expandedValues;
	}

	public final void setExpandedValues(String expandedValues) {
		this.expandedValues = expandedValues;
	}

	public final String getDefaultImageURL() {
		return defaultImageURL;
	}

	public final void setDefaultImageURL(String defaultImageURL) {
		this.defaultImageURL = defaultImageURL;
	}

	public final String getDefaultSelectedImageURL() {
		return defaultSelectedImageURL;
	}

	public final void setDefaultSelectedImageURL(String defaultSelectedImageURL) {
		this.defaultSelectedImageURL = defaultSelectedImageURL;
	}

	public final String getDefaultExpandedImageURL() {
		return defaultExpandedImageURL;
	}

	public final void setDefaultExpandedImageURL(String defaultExpandedImageURL) {
		this.defaultExpandedImageURL = defaultExpandedImageURL;
	}

	public final String getDefaultDisabledImageURL() {
		return defaultDisabledImageURL;
	}

	public final void setDefaultDisabledImageURL(String defaultDisabledImageURL) {
		this.defaultDisabledImageURL = defaultDisabledImageURL;
	}

	public final String getDefaultLeafImageURL() {
		return defaultLeafImageURL;
	}

	public final void setDefaultLeafImageURL(String defaultLeafImageURL) {
		this.defaultLeafImageURL = defaultLeafImageURL;
	}

	public final String getDefaultSelectedLeafImageURL() {
		return defaultSelectedLeafImageURL;
	}

	public final void setDefaultSelectedLeafImageURL(String defaultSelectedLeafImageURL) {
		this.defaultSelectedLeafImageURL = defaultSelectedLeafImageURL;
	}

	public final String getDefaultExpandedLeafImageURL() {
		return defaultExpandedLeafImageURL;
	}

	public final void setDefaultExpandedLeafImageURL(String defaultExpandedLeafImageURL) {
		this.defaultExpandedLeafImageURL = defaultExpandedLeafImageURL;
	}

	public final String getDefaultDisabledLeafImageURL() {
		return defaultDisabledLeafImageURL;
	}

	public final void setDefaultDisabledLeafImageURL(String defaultDisabledLeafImageURL) {
		this.defaultDisabledLeafImageURL = defaultDisabledLeafImageURL;
	}

	public final String getHideRootExpandSign() {
		return hideRootExpandSign;
	}

	public final void setHideRootExpandSign(String hideRootExpandSign) {
		this.hideRootExpandSign = hideRootExpandSign;
	}

	public final String getCursorValue() {
		return cursorValue;
	}

	public final void setCursorValue(String cursorValue) {
		this.cursorValue = cursorValue;
	}

	public final String getExpansionUseValue() {
		return expansionUseValue;
	}

	public final void setExpansionUseValue(String expansionUseValue) {
		this.expansionUseValue = expansionUseValue;
	}

	public final String getClientSelectionFullState() {
		return clientSelectionFullState;
	}

	public final void setClientSelectionFullState(String clientSelectionFullState) {
		this.clientSelectionFullState = clientSelectionFullState;
	}

	public final String getClientCheckFullState() {
		return clientCheckFullState;
	}

	public final void setClientCheckFullState(String clientCheckFullState) {
		this.clientCheckFullState = clientCheckFullState;
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
			LOG.debug("  selectable='"+selectable+"'");
			LOG.debug("  selectionCardinality='"+selectionCardinality+"'");
			LOG.debug("  selectedValues='"+selectedValues+"'");
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
			LOG.debug("  clientSelectionFullState='"+clientSelectionFullState+"'");
			LOG.debug("  clientCheckFullState='"+clientCheckFullState+"'");
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
		Application application = facesContext.getApplication();

		if (doubleClickListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.DOUBLE_CLICK_LISTENER_TYPE, doubleClickListeners);
		}

		if (required != null) {
			if (isValueReference(required)) {
				ValueBinding vb = application.createValueBinding(required);

				component.setRequired(vb);
			} else {
				component.setRequired(getBool(required));
			}
		}

		if (horizontalScrollPosition != null) {
			if (isValueReference(horizontalScrollPosition)) {
				ValueBinding vb = application.createValueBinding(horizontalScrollPosition);

				component.setHorizontalScrollPosition(vb);
			} else {
				component.setHorizontalScrollPosition(getInt(horizontalScrollPosition));
			}
		}

		if (verticalScrollPosition != null) {
			if (isValueReference(verticalScrollPosition)) {
				ValueBinding vb = application.createValueBinding(verticalScrollPosition);

				component.setVerticalScrollPosition(vb);
			} else {
				component.setVerticalScrollPosition(getInt(verticalScrollPosition));
			}
		}

		if (border != null) {
			if (isValueReference(border)) {
				ValueBinding vb = application.createValueBinding(border);

				component.setBorder(vb);
			} else {
				component.setBorder(getBool(border));
			}
		}

		if (readOnly != null) {
			if (isValueReference(readOnly)) {
				ValueBinding vb = application.createValueBinding(readOnly);

				component.setReadOnly(vb);
			} else {
				component.setReadOnly(getBool(readOnly));
			}
		}

		if (showValue != null) {
			if (isValueReference(showValue)) {
				ValueBinding vb = application.createValueBinding(showValue);

				component.setShowValue(vb);
			} else {
				component.setShowValue(showValue);
			}
		}

		if (checkable != null) {
			if (isValueReference(checkable)) {
				ValueBinding vb = application.createValueBinding(checkable);

				component.setCheckable(vb);
			} else {
				component.setCheckable(getBool(checkable));
			}
		}

		if (checkCardinality != null) {
			if (isValueReference(checkCardinality)) {
				ValueBinding vb = application.createValueBinding(checkCardinality);

				component.setCheckCardinality(vb);
			} else {
				component.setCheckCardinality(checkCardinality);
			}
		}

		if (checkListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.CHECK_LISTENER_TYPE, checkListeners);
		}

		if (checkedValues != null) {
				ValueBinding vb = application.createValueBinding(checkedValues);

				component.setCheckedValues(vb);
		}

		if (selectable != null) {
			if (isValueReference(selectable)) {
				ValueBinding vb = application.createValueBinding(selectable);

				component.setSelectable(vb);
			} else {
				component.setSelectable(getBool(selectable));
			}
		}

		if (selectionCardinality != null) {
			if (isValueReference(selectionCardinality)) {
				ValueBinding vb = application.createValueBinding(selectionCardinality);

				component.setSelectionCardinality(vb);
			} else {
				component.setSelectionCardinality(selectionCardinality);
			}
		}

		if (selectionListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (selectedValues != null) {
				ValueBinding vb = application.createValueBinding(selectedValues);

				component.setSelectedValues(vb);
		}

		if (preloadedLevelDepth != null) {
			if (isValueReference(preloadedLevelDepth)) {
				ValueBinding vb = application.createValueBinding(preloadedLevelDepth);

				component.setPreloadedLevelDepth(vb);
			} else {
				component.setPreloadedLevelDepth(getInt(preloadedLevelDepth));
			}
		}

		if (expandable != null) {
			if (isValueReference(expandable)) {
				ValueBinding vb = application.createValueBinding(expandable);

				component.setExpandable(vb);
			} else {
				component.setExpandable(getBool(expandable));
			}
		}

		if (expandedValues != null) {
				ValueBinding vb = application.createValueBinding(expandedValues);

				component.setExpandedValues(vb);
		}

		if (defaultImageURL != null) {
			if (isValueReference(defaultImageURL)) {
				ValueBinding vb = application.createValueBinding(defaultImageURL);
				component.setDefaultImageURL(vb);
			} else {
				component.setDefaultImageURL(defaultImageURL);
			}
		}

		if (defaultSelectedImageURL != null) {
			if (isValueReference(defaultSelectedImageURL)) {
				ValueBinding vb = application.createValueBinding(defaultSelectedImageURL);
				component.setDefaultSelectedImageURL(vb);
			} else {
				component.setDefaultSelectedImageURL(defaultSelectedImageURL);
			}
		}

		if (defaultExpandedImageURL != null) {
			if (isValueReference(defaultExpandedImageURL)) {
				ValueBinding vb = application.createValueBinding(defaultExpandedImageURL);
				component.setDefaultExpandedImageURL(vb);
			} else {
				component.setDefaultExpandedImageURL(defaultExpandedImageURL);
			}
		}

		if (defaultDisabledImageURL != null) {
			if (isValueReference(defaultDisabledImageURL)) {
				ValueBinding vb = application.createValueBinding(defaultDisabledImageURL);
				component.setDefaultDisabledImageURL(vb);
			} else {
				component.setDefaultDisabledImageURL(defaultDisabledImageURL);
			}
		}

		if (defaultLeafImageURL != null) {
			if (isValueReference(defaultLeafImageURL)) {
				ValueBinding vb = application.createValueBinding(defaultLeafImageURL);
				component.setDefaultLeafImageURL(vb);
			} else {
				component.setDefaultLeafImageURL(defaultLeafImageURL);
			}
		}

		if (defaultSelectedLeafImageURL != null) {
			if (isValueReference(defaultSelectedLeafImageURL)) {
				ValueBinding vb = application.createValueBinding(defaultSelectedLeafImageURL);
				component.setDefaultSelectedLeafImageURL(vb);
			} else {
				component.setDefaultSelectedLeafImageURL(defaultSelectedLeafImageURL);
			}
		}

		if (defaultExpandedLeafImageURL != null) {
			if (isValueReference(defaultExpandedLeafImageURL)) {
				ValueBinding vb = application.createValueBinding(defaultExpandedLeafImageURL);
				component.setDefaultExpandedLeafImageURL(vb);
			} else {
				component.setDefaultExpandedLeafImageURL(defaultExpandedLeafImageURL);
			}
		}

		if (defaultDisabledLeafImageURL != null) {
			if (isValueReference(defaultDisabledLeafImageURL)) {
				ValueBinding vb = application.createValueBinding(defaultDisabledLeafImageURL);
				component.setDefaultDisabledLeafImageURL(vb);
			} else {
				component.setDefaultDisabledLeafImageURL(defaultDisabledLeafImageURL);
			}
		}

		if (hideRootExpandSign != null) {
			if (isValueReference(hideRootExpandSign)) {
				ValueBinding vb = application.createValueBinding(hideRootExpandSign);
				component.setHideRootExpandSign(vb);
			} else {
				component.setHideRootExpandSign(getBool(hideRootExpandSign));
			}
		}

		if (cursorValue != null) {
			if (isValueReference(cursorValue)) {
				ValueBinding vb = application.createValueBinding(cursorValue);
				component.setCursorValue(vb);
			} else {
				component.setCursorValue(cursorValue);
			}
		}

		if (expansionUseValue != null) {
			if (isValueReference(expansionUseValue)) {
				ValueBinding vb = application.createValueBinding(expansionUseValue);
				component.setExpansionUseValue(vb);
			} else {
				component.setExpansionUseValue(getBool(expansionUseValue));
			}
		}

		if (clientSelectionFullState != null) {
			if (isValueReference(clientSelectionFullState)) {
				ValueBinding vb = application.createValueBinding(clientSelectionFullState);
				component.setClientSelectionFullState(vb);
			} else {
				component.setClientSelectionFullState(getBool(clientSelectionFullState));
			}
		}

		if (clientCheckFullState != null) {
			if (isValueReference(clientCheckFullState)) {
				ValueBinding vb = application.createValueBinding(clientCheckFullState);
				component.setClientCheckFullState(vb);
			} else {
				component.setClientCheckFullState(getBool(clientCheckFullState));
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
		selectable = null;
		selectionCardinality = null;
		selectionListeners = null;
		selectedValues = null;
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
		clientSelectionFullState = null;
		clientCheckFullState = null;

		super.release();
	}

}
