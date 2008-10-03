package org.rcfaces.renderkit.svg.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.renderkit.svg.component.NodeComponent;
import org.rcfaces.renderkit.svg.component.Properties;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;

public abstract class NodeTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(NodeTag.class);

	private ValueExpression alternateText;
	private ValueExpression rendered;
	private ValueExpression selectable;
	private ValueExpression itemValue;
	private ValueExpression itemLabel;
	private ValueExpression itemDescription;
	private ValueExpression itemDisabled;
	private ValueExpression targetId;
	public final void setAlternateText(ValueExpression alternateText) {
		this.alternateText = alternateText;
	}

	public final void setRendered(ValueExpression rendered) {
		this.rendered = rendered;
	}

	public final void setSelectable(ValueExpression selectable) {
		this.selectable = selectable;
	}

	public final void setItemValue(ValueExpression itemValue) {
		this.itemValue = itemValue;
	}

	public final void setItemLabel(ValueExpression itemLabel) {
		this.itemLabel = itemLabel;
	}

	public final void setItemDescription(ValueExpression itemDescription) {
		this.itemDescription = itemDescription;
	}

	public final void setItemDisabled(ValueExpression itemDisabled) {
		this.itemDisabled = itemDisabled;
	}

	public final void setTargetId(ValueExpression targetId) {
		this.targetId = targetId;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("  alternateText='"+alternateText+"'");
			LOG.debug("  rendered='"+rendered+"'");
			LOG.debug("  selectable='"+selectable+"'");
			LOG.debug("  itemValue='"+itemValue+"'");
			LOG.debug("  itemLabel='"+itemLabel+"'");
			LOG.debug("  itemDescription='"+itemDescription+"'");
			LOG.debug("  itemDisabled='"+itemDisabled+"'");
			LOG.debug("  targetId='"+targetId+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof NodeComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'NodeComponent'.");
		}

		NodeComponent component = (NodeComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (alternateText != null) {
			if (alternateText.isLiteralText()==false) {
				component.setValueExpression(Properties.ALTERNATE_TEXT, alternateText);

			} else {
				component.setAlternateText(alternateText.getExpressionString());
			}
		}

		if (rendered != null) {
			if (rendered.isLiteralText()==false) {
				component.setValueExpression(Properties.RENDERED, rendered);

			} else {
				component.setRendered(getBool(rendered.getExpressionString()));
			}
		}

		if (selectable != null) {
			if (selectable.isLiteralText()==false) {
				component.setValueExpression(Properties.SELECTABLE, selectable);

			} else {
				component.setSelectable(getBool(selectable.getExpressionString()));
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

		if (targetId != null) {
			if (targetId.isLiteralText()==false) {
				component.setValueExpression(Properties.TARGET_ID, targetId);

			} else {
				component.setTargetId(targetId.getExpressionString());
			}
		}
	}

	public void release() {
		alternateText = null;
		rendered = null;
		selectable = null;
		itemValue = null;
		itemLabel = null;
		itemDescription = null;
		itemDisabled = null;
		targetId = null;

		super.release();
	}

}
