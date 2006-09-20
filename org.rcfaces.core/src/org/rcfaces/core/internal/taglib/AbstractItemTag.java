package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.component.AbstractItemComponent;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public abstract class AbstractItemTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(AbstractItemTag.class);

	private String disabled;
	private String itemValue;
	private String itemLabel;
	private String itemDescription;
	private String itemDisabled;
	public final String getDisabled() {
		return disabled;
	}

	public final void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public final String getItemValue() {
		return itemValue;
	}

	public final void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public final String getItemLabel() {
		return itemLabel;
	}

	public final void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}

	public final String getItemDescription() {
		return itemDescription;
	}

	public final void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public final String getItemDisabled() {
		return itemDisabled;
	}

	public final void setItemDisabled(String itemDisabled) {
		this.itemDisabled = itemDisabled;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("  disabled='"+disabled+"'");
			LOG.debug("  itemValue='"+itemValue+"'");
			LOG.debug("  itemLabel='"+itemLabel+"'");
			LOG.debug("  itemDescription='"+itemDescription+"'");
			LOG.debug("  itemDisabled='"+itemDisabled+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof AbstractItemComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'AbstractItemComponent'.");
		}

		AbstractItemComponent component = (AbstractItemComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (disabled != null) {
			if (isValueReference(disabled)) {
				ValueBinding vb = application.createValueBinding(disabled);

				component.setDisabled(vb);
			} else {
				component.setDisabled(getBool(disabled));
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

		if (itemLabel != null) {
			if (isValueReference(itemLabel)) {
				ValueBinding vb = application.createValueBinding(itemLabel);
				component.setItemLabel(vb);
			} else {
				component.setItemLabel(itemLabel);
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
		disabled = null;
		itemValue = null;
		itemLabel = null;
		itemDescription = null;
		itemDisabled = null;

		super.release();
	}

}
