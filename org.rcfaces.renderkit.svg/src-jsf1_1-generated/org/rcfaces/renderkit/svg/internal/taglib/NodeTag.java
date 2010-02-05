package org.rcfaces.renderkit.svg.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import org.rcfaces.renderkit.svg.component.NodeComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;

public abstract class NodeTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(NodeTag.class);

	private String alternateText;
	private String accessKey;
	private String tabIndex;
	private String rendered;
	private String selectable;
	private String itemValue;
	private String itemLabel;
	private String itemDescription;
	private String itemDisabled;
	private String targetId;
	public final String getAlternateText() {
		return alternateText;
	}

	public final void setAlternateText(String alternateText) {
		this.alternateText = alternateText;
	}

	public final String getAccessKey() {
		return accessKey;
	}

	public final void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public final String getTabIndex() {
		return tabIndex;
	}

	public final void setTabIndex(String tabIndex) {
		this.tabIndex = tabIndex;
	}

	public final void setRendered(String rendered) {
		this.rendered = rendered;
	}

	public final void setSelectable(String selectable) {
		this.selectable = selectable;
	}

	public final void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public final void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}

	public final void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public final void setItemDisabled(String itemDisabled) {
		this.itemDisabled = itemDisabled;
	}

	public final String getTargetId() {
		return targetId;
	}

	public final void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("  alternateText='"+alternateText+"'");
			LOG.debug("  accessKey='"+accessKey+"'");
			LOG.debug("  tabIndex='"+tabIndex+"'");
			LOG.debug("  rendered='"+rendered+"'");
			LOG.debug("  selectable='"+selectable+"'");
			LOG.debug("  itemValue='"+itemValue+"'");
			LOG.debug("  itemLabel='"+itemLabel+"'");
			LOG.debug("  itemDescription='"+itemDescription+"'");
			LOG.debug("  itemDisabled='"+itemDisabled+"'");
			LOG.debug("  targetId='"+targetId+"'");
		}
		if ((uiComponent instanceof NodeComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'NodeComponent'.");
		}

		super.setProperties(uiComponent);

		NodeComponent component = (NodeComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (alternateText != null) {
			if (isValueReference(alternateText)) {
				ValueBinding vb = application.createValueBinding(alternateText);
				component.setValueBinding(Properties.ALTERNATE_TEXT, vb);

			} else {
				component.setAlternateText(alternateText);
			}
		}

		if (accessKey != null) {
			if (isValueReference(accessKey)) {
				ValueBinding vb = application.createValueBinding(accessKey);
				component.setValueBinding(Properties.ACCESS_KEY, vb);

			} else {
				component.setAccessKey(accessKey);
			}
		}

		if (tabIndex != null) {
			if (isValueReference(tabIndex)) {
				ValueBinding vb = application.createValueBinding(tabIndex);
				component.setValueBinding(Properties.TAB_INDEX, vb);

			} else {
				component.setTabIndex(getInteger(tabIndex));
			}
		}

		if (rendered != null) {
			if (isValueReference(rendered)) {
				ValueBinding vb = application.createValueBinding(rendered);
				component.setValueBinding(Properties.RENDERED, vb);

			} else {
				component.setRendered(getBool(rendered));
			}
		}

		if (selectable != null) {
			if (isValueReference(selectable)) {
				ValueBinding vb = application.createValueBinding(selectable);
				component.setValueBinding(Properties.SELECTABLE, vb);

			} else {
				component.setSelectable(getBool(selectable));
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

		if (itemLabel != null) {
			if (isValueReference(itemLabel)) {
				ValueBinding vb = application.createValueBinding(itemLabel);
				component.setValueBinding(Properties.ITEM_LABEL, vb);

			} else {
				component.setItemLabel(itemLabel);
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

		if (itemDisabled != null) {
			if (isValueReference(itemDisabled)) {
				ValueBinding vb = application.createValueBinding(itemDisabled);
				component.setValueBinding(Properties.ITEM_DISABLED, vb);

			} else {
				component.setItemDisabled(getBool(itemDisabled));
			}
		}

		if (targetId != null) {
			if (isValueReference(targetId)) {
				ValueBinding vb = application.createValueBinding(targetId);
				component.setValueBinding(Properties.TARGET_ID, vb);

			} else {
				component.setTargetId(targetId);
			}
		}
	}

	public void release() {
		alternateText = null;
		accessKey = null;
		tabIndex = null;
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
