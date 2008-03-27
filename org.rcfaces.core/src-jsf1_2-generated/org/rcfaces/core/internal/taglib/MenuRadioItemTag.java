package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import javax.el.ValueExpression;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.MenuRadioItemComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class MenuRadioItemTag extends MenuCheckItemTag implements Tag {


	private static final Log LOG=LogFactory.getLog(MenuRadioItemTag.class);

	private ValueExpression radioValue;
	private ValueExpression groupName;
	public String getComponentType() {
		return MenuRadioItemComponent.COMPONENT_TYPE;
	}

	public final void setRadioValue(ValueExpression radioValue) {
		this.radioValue = radioValue;
	}

	public final void setGroupName(ValueExpression groupName) {
		this.groupName = groupName;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (MenuRadioItemComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  radioValue='"+radioValue+"'");
			LOG.debug("  groupName='"+groupName+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof MenuRadioItemComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'MenuRadioItemComponent'.");
		}

		MenuRadioItemComponent component = (MenuRadioItemComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (radioValue != null) {
			if (radioValue.isLiteralText()==false) {
				component.setValueExpression(Properties.RADIO_VALUE, radioValue);

			} else {
				component.setRadioValue(radioValue.getExpressionString());
			}
		}

		if (groupName != null) {
			if (groupName.isLiteralText()==false) {
				component.setValueExpression(Properties.GROUP_NAME, groupName);

			} else {
				component.setGroupName(groupName.getExpressionString());
			}
		}
	}

	public void release() {
		radioValue = null;
		groupName = null;

		super.release();
	}

}
