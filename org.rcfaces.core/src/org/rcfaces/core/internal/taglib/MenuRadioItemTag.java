package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.MenuRadioItemComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class MenuRadioItemTag extends MenuCheckItemTag implements Tag {


	private static final Log LOG=LogFactory.getLog(MenuRadioItemTag.class);

	private String radioValue;
	private String groupName;
	public String getComponentType() {
		return MenuRadioItemComponent.COMPONENT_TYPE;
	}

	public final String getRadioValue() {
		return radioValue;
	}

	public final void setRadioValue(String radioValue) {
		this.radioValue = radioValue;
	}

	public final String getGroupName() {
		return groupName;
	}

	public final void setGroupName(String groupName) {
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
		Application application = facesContext.getApplication();

		if (radioValue != null) {
			if (isValueReference(radioValue)) {
				ValueBinding vb = application.createValueBinding(radioValue);
				component.setValueBinding(Properties.RADIO_VALUE, vb);

			} else {
				component.setRadioValue(radioValue);
			}
		}

		if (groupName != null) {
			if (isValueReference(groupName)) {
				ValueBinding vb = application.createValueBinding(groupName);
				component.setValueBinding(Properties.GROUP_NAME, vb);

			} else {
				component.setGroupName(groupName);
			}
		}
	}

	public void release() {
		radioValue = null;
		groupName = null;

		super.release();
	}

}
