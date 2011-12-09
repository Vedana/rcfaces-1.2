package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.MenuRadioItemComponent;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

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
		if ((uiComponent instanceof MenuRadioItemComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'MenuRadioItemComponent'.");
		}

		super.setProperties(uiComponent);

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
