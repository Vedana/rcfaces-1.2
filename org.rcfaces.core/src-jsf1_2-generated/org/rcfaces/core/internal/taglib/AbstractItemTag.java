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
import org.rcfaces.core.component.AbstractItemComponent;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public abstract class AbstractItemTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(AbstractItemTag.class);

	private ValueExpression disabled;
	private ValueExpression unlockedClientAttributeNames;
	private ValueExpression itemValue;
	private ValueExpression itemLabel;
	private ValueExpression itemDescription;
	private ValueExpression itemDisabled;
	public void setDisabled(ValueExpression disabled) {
		this.disabled = disabled;
	}

	public void setUnlockedClientAttributeNames(ValueExpression unlockedClientAttributeNames) {
		this.unlockedClientAttributeNames = unlockedClientAttributeNames;
	}

	public void setItemValue(ValueExpression itemValue) {
		this.itemValue = itemValue;
	}

	public void setItemLabel(ValueExpression itemLabel) {
		this.itemLabel = itemLabel;
	}

	public void setItemDescription(ValueExpression itemDescription) {
		this.itemDescription = itemDescription;
	}

	public void setItemDisabled(ValueExpression itemDisabled) {
		this.itemDisabled = itemDisabled;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("  disabled='"+disabled+"'");
			LOG.debug("  unlockedClientAttributeNames='"+unlockedClientAttributeNames+"'");
			LOG.debug("  itemValue='"+itemValue+"'");
			LOG.debug("  itemLabel='"+itemLabel+"'");
			LOG.debug("  itemDescription='"+itemDescription+"'");
			LOG.debug("  itemDisabled='"+itemDisabled+"'");
		}
		if ((uiComponent instanceof AbstractItemComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'AbstractItemComponent'.");
		}

		super.setProperties(uiComponent);

		AbstractItemComponent component = (AbstractItemComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (disabled != null) {
			if (disabled.isLiteralText()==false) {
				component.setValueExpression(Properties.DISABLED, disabled);

			} else {
				component.setDisabled(getBool(disabled.getExpressionString()));
			}
		}

		if (unlockedClientAttributeNames != null) {
			if (unlockedClientAttributeNames.isLiteralText()==false) {
				component.setValueExpression(Properties.UNLOCKED_CLIENT_ATTRIBUTE_NAMES, unlockedClientAttributeNames);

			} else {
				component.setUnlockedClientAttributeNames(unlockedClientAttributeNames.getExpressionString());
			}
		}

		if (itemValue != null) {
			if (itemValue.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_VALUE, itemValue);

			} else {
				component.setItemValue(itemValue.getExpressionString());
			}
		}

		if (itemLabel != null) {
			if (itemLabel.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_LABEL, itemLabel);

			} else {
				component.setItemLabel(itemLabel.getExpressionString());
			}
		}

		if (itemDescription != null) {
			if (itemDescription.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_DESCRIPTION, itemDescription);

			} else {
				component.setItemDescription(itemDescription.getExpressionString());
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
		disabled = null;
		unlockedClientAttributeNames = null;
		itemValue = null;
		itemLabel = null;
		itemDescription = null;
		itemDisabled = null;

		super.release();
	}

}
