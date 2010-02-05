package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import org.rcfaces.core.component.SelectItemsIteratorComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class SelectItemsIteratorTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(SelectItemsIteratorTag.class);

	private String items;
	private String var;
	private String itemIndexVar;
	private String itemLabel;
	private String itemValue;
	private String itemDisabled;
	private String itemDescription;
	private String itemAccessKey;
	private String itemAcceleratorKey;
	private String itemGroupName;
	private String itemInputType;
	private String itemStyleClass;
	private String itemImageURL;
	private String itemDisabledImageURL;
	private String itemHoverImageURL;
	private String itemSelectedImageURL;
	private String itemExpandedImageURL;
	private String itemVisibility;
	public String getComponentType() {
		return SelectItemsIteratorComponent.COMPONENT_TYPE;
	}

	public final void setItems(String items) {
		this.items = items;
	}

	public final void setVar(String var) {
		this.var = var;
	}

	public final void setItemIndexVar(String itemIndexVar) {
		this.itemIndexVar = itemIndexVar;
	}

	public final void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}

	public final void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public final void setItemDisabled(String itemDisabled) {
		this.itemDisabled = itemDisabled;
	}

	public final void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public final void setItemAccessKey(String itemAccessKey) {
		this.itemAccessKey = itemAccessKey;
	}

	public final void setItemAcceleratorKey(String itemAcceleratorKey) {
		this.itemAcceleratorKey = itemAcceleratorKey;
	}

	public final void setItemGroupName(String itemGroupName) {
		this.itemGroupName = itemGroupName;
	}

	public final void setItemInputType(String itemInputType) {
		this.itemInputType = itemInputType;
	}

	public final void setItemStyleClass(String itemStyleClass) {
		this.itemStyleClass = itemStyleClass;
	}

	public final void setItemImageURL(String itemImageURL) {
		this.itemImageURL = itemImageURL;
	}

	public final void setItemDisabledImageURL(String itemDisabledImageURL) {
		this.itemDisabledImageURL = itemDisabledImageURL;
	}

	public final void setItemHoverImageURL(String itemHoverImageURL) {
		this.itemHoverImageURL = itemHoverImageURL;
	}

	public final void setItemSelectedImageURL(String itemSelectedImageURL) {
		this.itemSelectedImageURL = itemSelectedImageURL;
	}

	public final void setItemExpandedImageURL(String itemExpandedImageURL) {
		this.itemExpandedImageURL = itemExpandedImageURL;
	}

	public final void setItemVisibility(String itemVisibility) {
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
		if ((uiComponent instanceof SelectItemsIteratorComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'SelectItemsIteratorComponent'.");
		}

		super.setProperties(uiComponent);

		SelectItemsIteratorComponent component = (SelectItemsIteratorComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (items != null) {
			if (isValueReference(items)) {
				ValueBinding vb = application.createValueBinding(items);
				component.setValueBinding(Properties.ITEMS, vb);

			} else {
				throw new javax.faces.FacesException("Attribute 'items' accept only a binding expression !");
			}
		}

		if (var != null) {
			if (isValueReference(var)) {
				throw new javax.faces.FacesException("Attribute 'var' does not accept binding !");
			}
				component.setVar(var);
		}

		if (itemIndexVar != null) {
			if (isValueReference(itemIndexVar)) {
				throw new javax.faces.FacesException("Attribute 'itemIndexVar' does not accept binding !");
			}
				component.setItemIndexVar(itemIndexVar);
		}

		if (itemLabel != null) {
			if (isValueReference(itemLabel)) {
				ValueBinding vb = application.createValueBinding(itemLabel);
				component.setValueBinding(Properties.ITEM_LABEL, vb);

			} else {
				component.setItemLabel(itemLabel);
			}
		}

		if (itemValue != null) {
			if (isValueReference(itemValue)) {
				ValueBinding vb = application.createValueBinding(itemValue);
				component.setValueBinding(Properties.ITEM_VALUE, vb);

			} else {
				component.setItemValue(itemValue);
			}
		}

		if (itemDisabled != null) {
			if (isValueReference(itemDisabled)) {
				ValueBinding vb = application.createValueBinding(itemDisabled);
				component.setValueBinding(Properties.ITEM_DISABLED, vb);

			} else {
				component.setItemDisabled(getBool(itemDisabled));
			}
		}

		if (itemDescription != null) {
			if (isValueReference(itemDescription)) {
				ValueBinding vb = application.createValueBinding(itemDescription);
				component.setValueBinding(Properties.ITEM_DESCRIPTION, vb);

			} else {
				component.setItemDescription(itemDescription);
			}
		}

		if (itemAccessKey != null) {
			if (isValueReference(itemAccessKey)) {
				ValueBinding vb = application.createValueBinding(itemAccessKey);
				component.setValueBinding(Properties.ITEM_ACCESS_KEY, vb);

			} else {
				component.setItemAccessKey(itemAccessKey);
			}
		}

		if (itemAcceleratorKey != null) {
			if (isValueReference(itemAcceleratorKey)) {
				ValueBinding vb = application.createValueBinding(itemAcceleratorKey);
				component.setValueBinding(Properties.ITEM_ACCELERATOR_KEY, vb);

			} else {
				component.setItemAcceleratorKey(itemAcceleratorKey);
			}
		}

		if (itemGroupName != null) {
			if (isValueReference(itemGroupName)) {
				ValueBinding vb = application.createValueBinding(itemGroupName);
				component.setValueBinding(Properties.ITEM_GROUP_NAME, vb);

			} else {
				component.setItemGroupName(itemGroupName);
			}
		}

		if (itemInputType != null) {
			if (isValueReference(itemInputType)) {
				ValueBinding vb = application.createValueBinding(itemInputType);
				component.setValueBinding(Properties.ITEM_INPUT_TYPE, vb);

			} else {
				component.setItemInputType(itemInputType);
			}
		}

		if (itemStyleClass != null) {
			if (isValueReference(itemStyleClass)) {
				ValueBinding vb = application.createValueBinding(itemStyleClass);
				component.setValueBinding(Properties.ITEM_STYLE_CLASS, vb);

			} else {
				component.setItemStyleClass(itemStyleClass);
			}
		}

		if (itemImageURL != null) {
			if (isValueReference(itemImageURL)) {
				ValueBinding vb = application.createValueBinding(itemImageURL);
				component.setValueBinding(Properties.ITEM_IMAGE_URL, vb);

			} else {
				component.setItemImageURL(itemImageURL);
			}
		}

		if (itemDisabledImageURL != null) {
			if (isValueReference(itemDisabledImageURL)) {
				ValueBinding vb = application.createValueBinding(itemDisabledImageURL);
				component.setValueBinding(Properties.ITEM_DISABLED_IMAGE_URL, vb);

			} else {
				component.setItemDisabledImageURL(itemDisabledImageURL);
			}
		}

		if (itemHoverImageURL != null) {
			if (isValueReference(itemHoverImageURL)) {
				ValueBinding vb = application.createValueBinding(itemHoverImageURL);
				component.setValueBinding(Properties.ITEM_HOVER_IMAGE_URL, vb);

			} else {
				component.setItemHoverImageURL(itemHoverImageURL);
			}
		}

		if (itemSelectedImageURL != null) {
			if (isValueReference(itemSelectedImageURL)) {
				ValueBinding vb = application.createValueBinding(itemSelectedImageURL);
				component.setValueBinding(Properties.ITEM_SELECTED_IMAGE_URL, vb);

			} else {
				component.setItemSelectedImageURL(itemSelectedImageURL);
			}
		}

		if (itemExpandedImageURL != null) {
			if (isValueReference(itemExpandedImageURL)) {
				ValueBinding vb = application.createValueBinding(itemExpandedImageURL);
				component.setValueBinding(Properties.ITEM_EXPANDED_IMAGE_URL, vb);

			} else {
				component.setItemExpandedImageURL(itemExpandedImageURL);
			}
		}

		if (itemVisibility != null) {
			if (isValueReference(itemVisibility)) {
				ValueBinding vb = application.createValueBinding(itemVisibility);
				component.setValueBinding(Properties.ITEM_VISIBILITY, vb);

			} else {
				component.setItemVisibility(getBool(itemVisibility));
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
