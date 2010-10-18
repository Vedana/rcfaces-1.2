package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ItemsToolFolderComponent;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class ItemsToolFolderTag extends AbstractInputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ItemsToolFolderTag.class);

	private ValueExpression doubleClickListeners;
	private ValueExpression textPosition;
	private ValueExpression borderType;
	private ValueExpression selectionListeners;
	private ValueExpression checkListeners;
	private ValueExpression checkedValues;
	private ValueExpression readOnly;
	private ValueExpression verticalAlignment;
	private ValueExpression showDropDownMark;
	private ValueExpression defaultItemLookId;
	private ValueExpression defaultItemStyleClass;
	private ValueExpression itemHiddenMode;
	public String getComponentType() {
		return ItemsToolFolderComponent.COMPONENT_TYPE;
	}

	public final void setDoubleClickListener(ValueExpression doubleClickListeners) {
		this.doubleClickListeners = doubleClickListeners;
	}

	public final void setTextPosition(ValueExpression textPosition) {
		this.textPosition = textPosition;
	}

	public final void setBorderType(ValueExpression borderType) {
		this.borderType = borderType;
	}

	public final void setSelectionListener(ValueExpression selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public final void setCheckListener(ValueExpression checkListeners) {
		this.checkListeners = checkListeners;
	}

	public final void setCheckedValues(ValueExpression checkedValues) {
		this.checkedValues = checkedValues;
	}

	public final void setReadOnly(ValueExpression readOnly) {
		this.readOnly = readOnly;
	}

	public final void setVerticalAlignment(ValueExpression verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

	public final void setShowDropDownMark(ValueExpression showDropDownMark) {
		this.showDropDownMark = showDropDownMark;
	}

	public final void setDefaultItemLookId(ValueExpression defaultItemLookId) {
		this.defaultItemLookId = defaultItemLookId;
	}

	public final void setDefaultItemStyleClass(ValueExpression defaultItemStyleClass) {
		this.defaultItemStyleClass = defaultItemStyleClass;
	}

	public final void setItemHiddenMode(ValueExpression itemHiddenMode) {
		this.itemHiddenMode = itemHiddenMode;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ItemsToolFolderComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  textPosition='"+textPosition+"'");
			LOG.debug("  borderType='"+borderType+"'");
			LOG.debug("  checkedValues='"+checkedValues+"'");
			LOG.debug("  readOnly='"+readOnly+"'");
			LOG.debug("  verticalAlignment='"+verticalAlignment+"'");
			LOG.debug("  showDropDownMark='"+showDropDownMark+"'");
			LOG.debug("  defaultItemLookId='"+defaultItemLookId+"'");
			LOG.debug("  defaultItemStyleClass='"+defaultItemStyleClass+"'");
			LOG.debug("  itemHiddenMode='"+itemHiddenMode+"'");
		}
		if ((uiComponent instanceof ItemsToolFolderComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ItemsToolFolderComponent'.");
		}

		super.setProperties(uiComponent);

		ItemsToolFolderComponent component = (ItemsToolFolderComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

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

		if (verticalAlignment != null) {
			if (verticalAlignment.isLiteralText()==false) {
				component.setValueExpression(Properties.VERTICAL_ALIGNMENT, verticalAlignment);

			} else {
				component.setVerticalAlignment(verticalAlignment.getExpressionString());
			}
		}

		if (showDropDownMark != null) {
			if (showDropDownMark.isLiteralText()==false) {
				component.setValueExpression(Properties.SHOW_DROP_DOWN_MARK, showDropDownMark);

			} else {
				component.setShowDropDownMark(getBool(showDropDownMark.getExpressionString()));
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

		if (itemHiddenMode != null) {
			if (itemHiddenMode.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_HIDDEN_MODE, itemHiddenMode);

			} else {
				component.setItemHiddenMode(itemHiddenMode.getExpressionString());
			}
		}
	}

	public void release() {
		doubleClickListeners = null;
		textPosition = null;
		borderType = null;
		selectionListeners = null;
		checkListeners = null;
		checkedValues = null;
		readOnly = null;
		verticalAlignment = null;
		showDropDownMark = null;
		defaultItemLookId = null;
		defaultItemStyleClass = null;
		itemHiddenMode = null;

		super.release();
	}

}
