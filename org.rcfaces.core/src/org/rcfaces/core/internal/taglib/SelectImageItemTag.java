package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import org.rcfaces.core.component.SelectImageItemComponent;
import javax.faces.application.Application;

public class SelectImageItemTag extends CameliaTag {

private static final Log LOG=LogFactory.getLog(SelectImageItemTag.class);
	private String imageURL;
	private String itemValue;
	private String itemDescription;
	private String itemLabel;
	private String itemDisabled;
	public String getComponentType() {
		return SelectImageItemComponent.COMPONENT_TYPE;
	}

	public final String getImageURL() {
		return imageURL;
	}

	public final void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public final String getItemValue() {
		return itemValue;
	}

	public final void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public final String getItemDescription() {
		return itemDescription;
	}

	public final void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public final String getItemLabel() {
		return itemLabel;
	}

	public final void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}

	public final String getItemDisabled() {
		return itemDisabled;
	}

	public final void setItemDisabled(String itemDisabled) {
		this.itemDisabled = itemDisabled;
	}

	protected void setProperties(UIComponent uiComponent) {
		super.setProperties(uiComponent);

		if ((uiComponent instanceof SelectImageItemComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'SelectImageItemComponent'.");
		}

		SelectImageItemComponent component = (SelectImageItemComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (imageURL != null) {
			if (isValueReference(imageURL)) {
				ValueBinding vb = application.createValueBinding(imageURL);

				component.setImageURL(vb);
			} else {
				component.setImageURL(imageURL);
			}
		}

		if (itemValue != null) {
			if (isValueReference(itemValue)) {
				ValueBinding vb = application.createValueBinding(itemValue);
				component.setItemValue(vb);
			} else {
				component.setItemValue(itemValue);
			}
		}

		if (itemDescription != null) {
			if (isValueReference(itemDescription)) {
				ValueBinding vb = application.createValueBinding(itemDescription);
				component.setItemDescription(vb);
			} else {
				component.setItemDescription(itemDescription);
			}
		}

		if (itemLabel != null) {
			if (isValueReference(itemLabel)) {
				ValueBinding vb = application.createValueBinding(itemLabel);
				component.setItemLabel(vb);
			} else {
				component.setItemLabel(itemLabel);
			}
		}

		if (itemDisabled != null) {
			if (isValueReference(itemDisabled)) {
				ValueBinding vb = application.createValueBinding(itemDisabled);
				component.setItemDisabled(vb);
			} else {
				component.setItemDisabled(getBool(itemDisabled));
			}
		}
	}

	public void release() {
		imageURL = null;
		itemValue = null;
		itemDescription = null;
		itemLabel = null;
		itemDisabled = null;

		super.release();
	}

}
