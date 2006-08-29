package org.rcfaces.core.component;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.MenuItemComponent;
import org.rcfaces.core.component.capability.ICheckedCapability;
import org.rcfaces.core.internal.component.Properties;

public class MenuCheckItemComponent extends MenuItemComponent implements 
	ICheckedCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.menuCheckItem";


	public MenuCheckItemComponent() {
		setRendererType(null);
	}

	public MenuCheckItemComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final boolean isChecked() {
		return isChecked(null);
	}

	public final boolean isChecked(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.CHECKED, false, facesContext);
	}

	public final void setChecked(boolean checked) {
		engine.setProperty(Properties.CHECKED, checked);
	}

	public final void setChecked(ValueBinding checked) {
		engine.setProperty(Properties.CHECKED, checked);
	}

	public void release() {
		super.release();
	}
}
