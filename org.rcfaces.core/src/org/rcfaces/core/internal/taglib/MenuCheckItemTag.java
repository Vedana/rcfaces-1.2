package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.MenuCheckItemComponent;

public class MenuCheckItemTag extends MenuItemTag implements Tag {


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
		if (LOG.isDebugEnabled()) {
			if (MenuCheckItemComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  checked='"+checked+"'");
		}
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
