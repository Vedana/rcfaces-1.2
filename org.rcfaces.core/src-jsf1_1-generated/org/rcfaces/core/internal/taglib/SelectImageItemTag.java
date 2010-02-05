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
import javax.faces.component.UIComponent;
import org.rcfaces.core.component.SelectImageItemComponent;
import javax.faces.application.Application;

public class SelectImageItemTag extends CameliaTag implements Tag {


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

	public final void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public final void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public final void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}

	public final void setItemDisabled(String itemDisabled) {
		this.itemDisabled = itemDisabled;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (SelectImageItemComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  imageURL='"+imageURL+"'");
			LOG.debug("  itemValue='"+itemValue+"'");
			LOG.debug("  itemDescription='"+itemDescription+"'");
			LOG.debug("  itemLabel='"+itemLabel+"'");
			LOG.debug("  itemDisabled='"+itemDisabled+"'");
		}
		if ((uiComponent instanceof SelectImageItemComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'SelectImageItemComponent'.");
		}

		super.setProperties(uiComponent);

		SelectImageItemComponent component = (SelectImageItemComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (imageURL != null) {
			if (isValueReference(imageURL)) {
				ValueBinding vb = application.createValueBinding(imageURL);
				component.setValueBinding(Properties.IMAGE_URL, vb);

			} else {
				component.setImageURL(imageURL);
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

		if (itemDescription != null) {
			if (isValueReference(itemDescription)) {
				ValueBinding vb = application.createValueBinding(itemDescription);
				component.setValueBinding(Properties.ITEM_DESCRIPTION, vb);

			} else {
				component.setItemDescription(itemDescription);
			}
		}

		if (itemLabel != null) {
			if (isValueReference(itemLabel)) {
				ValueBinding vb = application.createValueBinding(itemLabel);
				component.setValueBinding(Properties.ITEM_LABEL, vb);

			} else {
				component.setItemLabel(itemLabel);
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
