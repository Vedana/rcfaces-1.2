package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.MenuRadioItemComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class MenuRadioItemTag extends MenuCheckItemTag {

private static final Log LOG=LogFactory.getLog(MenuRadioItemTag.class);
	private String groupName;
	public String getComponentType() {
		return MenuRadioItemComponent.COMPONENT_TYPE;
	}

	public final String getGroupName() {
		return groupName;
	}

	public final void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	protected void setProperties(UIComponent uiComponent) {
		super.setProperties(uiComponent);

		if ((uiComponent instanceof MenuRadioItemComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'MenuRadioItemComponent'.");
		}

		MenuRadioItemComponent component = (MenuRadioItemComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (groupName != null) {
			if (isValueReference(groupName)) {
				ValueBinding vb = application.createValueBinding(groupName);

				component.setGroupName(vb);
			} else {
				component.setGroupName(groupName);
			}
		}
	}

	public void release() {
		groupName = null;

		super.release();
	}

}
