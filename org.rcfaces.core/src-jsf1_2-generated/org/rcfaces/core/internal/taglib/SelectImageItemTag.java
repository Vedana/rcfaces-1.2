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
import javax.faces.component.UIComponent;
import org.rcfaces.core.component.SelectImageItemComponent;
import javax.faces.application.Application;

public class SelectImageItemTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(SelectImageItemTag.class);

	private ValueExpression imageURL;
	private ValueExpression itemValue;
	private ValueExpression itemDescription;
	private ValueExpression itemLabel;
	private ValueExpression itemDisabled;
	public String getComponentType() {
		return SelectImageItemComponent.COMPONENT_TYPE;
	}

	public final void setImageURL(ValueExpression imageURL) {
		this.imageURL = imageURL;
	}

	public final void setItemValue(ValueExpression itemValue) {
		this.itemValue = itemValue;
	}

	public final void setItemDescription(ValueExpression itemDescription) {
		this.itemDescription = itemDescription;
	}

	public final void setItemLabel(ValueExpression itemLabel) {
		this.itemLabel = itemLabel;
	}

	public final void setItemDisabled(ValueExpression itemDisabled) {
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

		if (imageURL != null) {
			if (imageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.IMAGE_URL, imageURL);

			} else {
				component.setImageURL(imageURL.getExpressionString());
			}
		}

		if (itemValue != null) {
			if (itemValue.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_VALUE, itemValue);

			} else {
				component.setItemValue(itemValue.getExpressionString());
			}
		}

		if (itemDescription != null) {
			if (itemDescription.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_DESCRIPTION, itemDescription);

			} else {
				component.setItemDescription(itemDescription.getExpressionString());
			}
		}

		if (itemLabel != null) {
			if (itemLabel.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_LABEL, itemLabel);

			} else {
				component.setItemLabel(itemLabel.getExpressionString());
			}
		}

		if (itemDisabled != null) {
			if (itemDisabled.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_DISABLED, itemDisabled);

			} else {
				component.setItemDisabled(getBool(itemDisabled.getExpressionString()));
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
