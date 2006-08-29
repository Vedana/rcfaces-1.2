package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.MenuCheckItemComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class MenuCheckItemTag extends MenuItemTag {

private static final Log LOG=LogFactory.getLog(MenuCheckItemTag.class);
	private String checked;
	public String getComponentType() {
		return MenuCheckItemComponent.COMPONENT_TYPE;
	}

	public final String getChecked() {
		return checked;
	}

	public final void setChecked(String checked) {
		this.checked = checked;
	}

	protected void setProperties(UIComponent uiComponent) {
		super.setProperties(uiComponent);

		if ((uiComponent instanceof MenuCheckItemComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'MenuCheckItemComponent'.");
		}

		MenuCheckItemComponent component = (MenuCheckItemComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (checked != null) {
			if (isValueReference(checked)) {
				ValueBinding vb = application.createValueBinding(checked);

				component.setChecked(vb);
			} else {
				component.setChecked(getBool(checked));
			}
		}
	}

	public void release() {
		checked = null;

		super.release();
	}

}
