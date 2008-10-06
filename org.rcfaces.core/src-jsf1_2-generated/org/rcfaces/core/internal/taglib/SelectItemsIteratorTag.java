package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import javax.el.ValueExpression;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;
import org.rcfaces.core.component.SelectItemsIteratorComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class SelectItemsIteratorTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(SelectItemsIteratorTag.class);

	private ValueExpression items;
	private ValueExpression var;
	private ValueExpression itemIndexVar;
	private ValueExpression itemLabel;
	private ValueExpression itemValue;
	private ValueExpression itemDisabled;
	private ValueExpression itemDescription;
	private ValueExpression itemAccessKey;
	private ValueExpression itemAcceleratorKey;
	private ValueExpression itemGroupName;
	private ValueExpression itemInputType;
	private ValueExpression itemStyleClass;
	private ValueExpression itemImageURL;
	private ValueExpression itemDisabledImageURL;
	private ValueExpression itemHoverImageURL;
	private ValueExpression itemSelectedImageURL;
	private ValueExpression itemExpandedImageURL;
	private ValueExpression itemVisibility;
	public String getComponentType() {
		return SelectItemsIteratorComponent.COMPONENT_TYPE;
	}

	public final void setItems(ValueExpression items) {
		this.items = items;
	}

	public final void setVar(ValueExpression var) {
		this.var = var;
	}

	public final void setItemIndexVar(ValueExpression itemIndexVar) {
		this.itemIndexVar = itemIndexVar;
	}

	public final void setItemLabel(ValueExpression itemLabel) {
		this.itemLabel = itemLabel;
	}

	public final void setItemValue(ValueExpression itemValue) {
		this.itemValue = itemValue;
	}

	public final void setItemDisabled(ValueExpression itemDisabled) {
		this.itemDisabled = itemDisabled;
	}

	public final void setItemDescription(ValueExpression itemDescription) {
		this.itemDescription = itemDescription;
	}

	public final void setItemAccessKey(ValueExpression itemAccessKey) {
		this.itemAccessKey = itemAccessKey;
	}

	public final void setItemAcceleratorKey(ValueExpression itemAcceleratorKey) {
		this.itemAcceleratorKey = itemAcceleratorKey;
	}

	public final void setItemGroupName(ValueExpression itemGroupName) {
		this.itemGroupName = itemGroupName;
	}

	public final void setItemInputType(ValueExpression itemInputType) {
		this.itemInputType = itemInputType;
	}

	public final void setItemStyleClass(ValueExpression itemStyleClass) {
		this.itemStyleClass = itemStyleClass;
	}

	public final void setItemImageURL(ValueExpression itemImageURL) {
		this.itemImageURL = itemImageURL;
	}

	public final void setItemDisabledImageURL(ValueExpression itemDisabledImageURL) {
		this.itemDisabledImageURL = itemDisabledImageURL;
	}

	public final void setItemHoverImageURL(ValueExpression itemHoverImageURL) {
		this.itemHoverImageURL = itemHoverImageURL;
	}

	public final void setItemSelectedImageURL(ValueExpression itemSelectedImageURL) {
		this.itemSelectedImageURL = itemSelectedImageURL;
	}

	public final void setItemExpandedImageURL(ValueExpression itemExpandedImageURL) {
		this.itemExpandedImageURL = itemExpandedImageURL;
	}

	public final void setItemVisibility(ValueExpression itemVisibility) {
		this.itemVisibility = itemVisibility;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (SelectItemsIteratorComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  items='"+items+"'");
			LOG.debug("  var='"+var+"'");
			LOG.debug("  itemIndexVar='"+itemIndexVar+"'");
			LOG.debug("  itemLabel='"+itemLabel+"'");
			LOG.debug("  itemValue='"+itemValue+"'");
			LOG.debug("  itemDisabled='"+itemDisabled+"'");
			LOG.debug("  itemDescription='"+itemDescription+"'");
			LOG.debug("  itemAccessKey='"+itemAccessKey+"'");
			LOG.debug("  itemAcceleratorKey='"+itemAcceleratorKey+"'");
			LOG.debug("  itemGroupName='"+itemGroupName+"'");
			LOG.debug("  itemInputType='"+itemInputType+"'");
			LOG.debug("  itemStyleClass='"+itemStyleClass+"'");
			LOG.debug("  itemImageURL='"+itemImageURL+"'");
			LOG.debug("  itemDisabledImageURL='"+itemDisabledImageURL+"'");
			LOG.debug("  itemHoverImageURL='"+itemHoverImageURL+"'");
			LOG.debug("  itemSelectedImageURL='"+itemSelectedImageURL+"'");
			LOG.debug("  itemExpandedImageURL='"+itemExpandedImageURL+"'");
			LOG.debug("  itemVisibility='"+itemVisibility+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof SelectItemsIteratorComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'SelectItemsIteratorComponent'.");
		}

		SelectItemsIteratorComponent component = (SelectItemsIteratorComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (items != null) {
			if (items.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEMS, items);

			} else {
				throw new javax.faces.FacesException("Attribute 'items' accept only a binding expression !");
			}
		}

		if (var != null) {
			if (var.isLiteralText()==false) {
				throw new javax.faces.FacesException("Attribute 'var' does not accept binding !");
			}
				component.setVar(var.getExpressionString());
		}

		if (itemIndexVar != null) {
			if (itemIndexVar.isLiteralText()==false) {
				throw new javax.faces.FacesException("Attribute 'itemIndexVar' does not accept binding !");
			}
				component.setItemIndexVar(itemIndexVar.getExpressionString());
		}

		if (itemLabel != null) {
			if (itemLabel.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_LABEL, itemLabel);

			} else {
				component.setItemLabel(itemLabel.getExpressionString());
			}
		}

		if (itemValue != null) {
			if (itemValue.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_VALUE, itemValue);

			} else {
				component.setItemValue(itemValue.getExpressionString());
			}
		}

		if (itemDisabled != null) {
			if (itemDisabled.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_DISABLED, itemDisabled);

			} else {
				component.setItemDisabled(getBool(itemDisabled.getExpressionString()));
			}
		}

		if (itemDescription != null) {
			if (itemDescription.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_DESCRIPTION, itemDescription);

			} else {
				component.setItemDescription(itemDescription.getExpressionString());
			}
		}

		if (itemAccessKey != null) {
			if (itemAccessKey.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_ACCESS_KEY, itemAccessKey);

			} else {
				component.setItemAccessKey(itemAccessKey.getExpressionString());
			}
		}

		if (itemAcceleratorKey != null) {
			if (itemAcceleratorKey.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_ACCELERATOR_KEY, itemAcceleratorKey);

			} else {
				component.setItemAcceleratorKey(itemAcceleratorKey.getExpressionString());
			}
		}

		if (itemGroupName != null) {
			if (itemGroupName.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_GROUP_NAME, itemGroupName);

			} else {
				component.setItemGroupName(itemGroupName.getExpressionString());
			}
		}

		if (itemInputType != null) {
			if (itemInputType.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_INPUT_TYPE, itemInputType);

			} else {
				component.setItemInputType(itemInputType.getExpressionString());
			}
		}

		if (itemStyleClass != null) {
			if (itemStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_STYLE_CLASS, itemStyleClass);

			} else {
				component.setItemStyleClass(itemStyleClass.getExpressionString());
			}
		}

		if (itemImageURL != null) {
			if (itemImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_IMAGE_URL, itemImageURL);

			} else {
				component.setItemImageURL(itemImageURL.getExpressionString());
			}
		}

		if (itemDisabledImageURL != null) {
			if (itemDisabledImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_DISABLED_IMAGE_URL, itemDisabledImageURL);

			} else {
				component.setItemDisabledImageURL(itemDisabledImageURL.getExpressionString());
			}
		}

		if (itemHoverImageURL != null) {
			if (itemHoverImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_HOVER_IMAGE_URL, itemHoverImageURL);

			} else {
				component.setItemHoverImageURL(itemHoverImageURL.getExpressionString());
			}
		}

		if (itemSelectedImageURL != null) {
			if (itemSelectedImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_SELECTED_IMAGE_URL, itemSelectedImageURL);

			} else {
				component.setItemSelectedImageURL(itemSelectedImageURL.getExpressionString());
			}
		}

		if (itemExpandedImageURL != null) {
			if (itemExpandedImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_EXPANDED_IMAGE_URL, itemExpandedImageURL);

			} else {
				component.setItemExpandedImageURL(itemExpandedImageURL.getExpressionString());
			}
		}

		if (itemVisibility != null) {
			if (itemVisibility.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_VISIBILITY, itemVisibility);

			} else {
				component.setItemVisibility(getBool(itemVisibility.getExpressionString()));
			}
		}
	}

	public void release() {
		items = null;
		var = null;
		itemIndexVar = null;
		itemLabel = null;
		itemValue = null;
		itemDisabled = null;
		itemDescription = null;
		itemAccessKey = null;
		itemAcceleratorKey = null;
		itemGroupName = null;
		itemInputType = null;
		itemStyleClass = null;
		itemImageURL = null;
		itemDisabledImageURL = null;
		itemHoverImageURL = null;
		itemSelectedImageURL = null;
		itemExpandedImageURL = null;
		itemVisibility = null;

		super.release();
	}

}
