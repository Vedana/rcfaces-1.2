package org.rcfaces.core.internal.taglib;

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
import org.rcfaces.core.component.ItemsListComponent;
import javax.faces.context.FacesContext;

public class ItemsListTag extends AbstractInputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ItemsListTag.class);

	private ValueExpression headingZone;
	private ValueExpression headingLevel;
	private ValueExpression doubleClickListeners;
	private ValueExpression textPosition;
	private ValueExpression borderType;
	private ValueExpression selectionListeners;
	private ValueExpression checkListeners;
	private ValueExpression checkedValues;
	private ValueExpression readOnly;
	private ValueExpression defaultImageURL;
	private ValueExpression defaultSelectedImageURL;
	private ValueExpression defaultHoverImageURL;
	private ValueExpression defaultDisabledImageURL;
	private ValueExpression defaultItemInputType;
	private ValueExpression defaultItemLookId;
	private ValueExpression defaultItemStyleClass;
	private ValueExpression defaultItemGroupName;
	private ValueExpression itemPadding;
	private ValueExpression itemHiddenMode;
	public String getComponentType() {
		return ItemsListComponent.COMPONENT_TYPE;
	}

	public void setHeadingZone(ValueExpression headingZone) {
		this.headingZone = headingZone;
	}

	public void setHeadingLevel(ValueExpression headingLevel) {
		this.headingLevel = headingLevel;
	}

	public void setDoubleClickListener(ValueExpression doubleClickListeners) {
		this.doubleClickListeners = doubleClickListeners;
	}

	public void setTextPosition(ValueExpression textPosition) {
		this.textPosition = textPosition;
	}

	public void setBorderType(ValueExpression borderType) {
		this.borderType = borderType;
	}

	public void setSelectionListener(ValueExpression selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public void setCheckListener(ValueExpression checkListeners) {
		this.checkListeners = checkListeners;
	}

	public void setCheckedValues(ValueExpression checkedValues) {
		this.checkedValues = checkedValues;
	}

	public void setReadOnly(ValueExpression readOnly) {
		this.readOnly = readOnly;
	}

	public void setDefaultImageURL(ValueExpression defaultImageURL) {
		this.defaultImageURL = defaultImageURL;
	}

	public void setDefaultSelectedImageURL(ValueExpression defaultSelectedImageURL) {
		this.defaultSelectedImageURL = defaultSelectedImageURL;
	}

	public void setDefaultHoverImageURL(ValueExpression defaultHoverImageURL) {
		this.defaultHoverImageURL = defaultHoverImageURL;
	}

	public void setDefaultDisabledImageURL(ValueExpression defaultDisabledImageURL) {
		this.defaultDisabledImageURL = defaultDisabledImageURL;
	}

	public void setDefaultItemInputType(ValueExpression defaultItemInputType) {
		this.defaultItemInputType = defaultItemInputType;
	}

	public void setDefaultItemLookId(ValueExpression defaultItemLookId) {
		this.defaultItemLookId = defaultItemLookId;
	}

	public void setDefaultItemStyleClass(ValueExpression defaultItemStyleClass) {
		this.defaultItemStyleClass = defaultItemStyleClass;
	}

	public void setDefaultItemGroupName(ValueExpression defaultItemGroupName) {
		this.defaultItemGroupName = defaultItemGroupName;
	}

	public void setItemPadding(ValueExpression itemPadding) {
		this.itemPadding = itemPadding;
	}

	public void setItemHiddenMode(ValueExpression itemHiddenMode) {
		this.itemHiddenMode = itemHiddenMode;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ItemsListComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  headingZone='"+headingZone+"'");
			LOG.debug("  headingLevel='"+headingLevel+"'");
			LOG.debug("  textPosition='"+textPosition+"'");
			LOG.debug("  borderType='"+borderType+"'");
			LOG.debug("  checkedValues='"+checkedValues+"'");
			LOG.debug("  readOnly='"+readOnly+"'");
			LOG.debug("  defaultImageURL='"+defaultImageURL+"'");
			LOG.debug("  defaultSelectedImageURL='"+defaultSelectedImageURL+"'");
			LOG.debug("  defaultHoverImageURL='"+defaultHoverImageURL+"'");
			LOG.debug("  defaultDisabledImageURL='"+defaultDisabledImageURL+"'");
			LOG.debug("  defaultItemInputType='"+defaultItemInputType+"'");
			LOG.debug("  defaultItemLookId='"+defaultItemLookId+"'");
			LOG.debug("  defaultItemStyleClass='"+defaultItemStyleClass+"'");
			LOG.debug("  defaultItemGroupName='"+defaultItemGroupName+"'");
			LOG.debug("  itemPadding='"+itemPadding+"'");
			LOG.debug("  itemHiddenMode='"+itemHiddenMode+"'");
		}
		if ((uiComponent instanceof ItemsListComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ItemsListComponent'.");
		}

		super.setProperties(uiComponent);

		ItemsListComponent component = (ItemsListComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (headingZone != null) {
			if (headingZone.isLiteralText()==false) {
				component.setValueExpression(Properties.HEADING_ZONE, headingZone);

			} else {
				component.setHeadingZone(getBool(headingZone.getExpressionString()));
			}
		}

		if (headingLevel != null) {
			if (headingLevel.isLiteralText()==false) {
				component.setValueExpression(Properties.HEADING_LEVEL, headingLevel);

			} else {
				component.setHeadingLevel(headingLevel.getExpressionString());
			}
		}

		if (doubleClickListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.DOUBLE_CLICK_LISTENER_TYPE, doubleClickListeners);
		}

		if (textPosition != null) {
			if (textPosition.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT_POSITION, textPosition);

			} else {
				component.setTextPosition(textPosition.getExpressionString());
			}
		}

		if (borderType != null) {
			if (borderType.isLiteralText()==false) {
				component.setValueExpression(Properties.BORDER_TYPE, borderType);

			} else {
				component.setBorderType(borderType.getExpressionString());
			}
		}

		if (selectionListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (checkListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.CHECK_LISTENER_TYPE, checkListeners);
		}

		if (checkedValues != null) {
				component.setValueExpression(Properties.CHECKED_VALUES, checkedValues);
		}

		if (readOnly != null) {
			if (readOnly.isLiteralText()==false) {
				component.setValueExpression(Properties.READ_ONLY, readOnly);

			} else {
				component.setReadOnly(getBool(readOnly.getExpressionString()));
			}
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

		if (defaultHoverImageURL != null) {
			if (defaultHoverImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.DEFAULT_HOVER_IMAGE_URL, defaultHoverImageURL);

			} else {
				component.setDefaultHoverImageURL(defaultHoverImageURL.getExpressionString());
			}
		}

		if (defaultDisabledImageURL != null) {
			if (defaultDisabledImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.DEFAULT_DISABLED_IMAGE_URL, defaultDisabledImageURL);

			} else {
				component.setDefaultDisabledImageURL(defaultDisabledImageURL.getExpressionString());
			}
		}

		if (defaultItemInputType != null) {
			if (defaultItemInputType.isLiteralText()==false) {
				component.setValueExpression(Properties.DEFAULT_ITEM_INPUT_TYPE, defaultItemInputType);

			} else {
				component.setDefaultItemInputType(defaultItemInputType.getExpressionString());
			}
		}

		if (defaultItemLookId != null) {
			if (defaultItemLookId.isLiteralText()==false) {
				component.setValueExpression(Properties.DEFAULT_ITEM_LOOK_ID, defaultItemLookId);

			} else {
				component.setDefaultItemLookId(defaultItemLookId.getExpressionString());
			}
		}

		if (defaultItemStyleClass != null) {
			if (defaultItemStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.DEFAULT_ITEM_STYLE_CLASS, defaultItemStyleClass);

			} else {
				component.setDefaultItemStyleClass(defaultItemStyleClass.getExpressionString());
			}
		}

		if (defaultItemGroupName != null) {
			if (defaultItemGroupName.isLiteralText()==false) {
				component.setValueExpression(Properties.DEFAULT_ITEM_GROUP_NAME, defaultItemGroupName);

			} else {
				component.setDefaultItemGroupName(defaultItemGroupName.getExpressionString());
			}
		}

		if (itemPadding != null) {
			if (itemPadding.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_PADDING, itemPadding);

			} else {
				component.setItemPadding(getInt(itemPadding.getExpressionString()));
			}
		}

		if (itemHiddenMode != null) {
			if (itemHiddenMode.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_HIDDEN_MODE, itemHiddenMode);

			} else {
				component.setItemHiddenMode(itemHiddenMode.getExpressionString());
			}
		}
	}

	public void release() {
		headingZone = null;
		headingLevel = null;
		doubleClickListeners = null;
		textPosition = null;
		borderType = null;
		selectionListeners = null;
		checkListeners = null;
		checkedValues = null;
		readOnly = null;
		defaultImageURL = null;
		defaultSelectedImageURL = null;
		defaultHoverImageURL = null;
		defaultDisabledImageURL = null;
		defaultItemInputType = null;
		defaultItemLookId = null;
		defaultItemStyleClass = null;
		defaultItemGroupName = null;
		itemPadding = null;
		itemHiddenMode = null;

		super.release();
	}

}
