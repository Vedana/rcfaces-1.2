package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IRadioValueCapability;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.MenuCheckItemComponent;

public class MenuRadioItemComponent extends MenuCheckItemComponent implements 
	IRadioValueCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.menuRadioItem";


	public MenuRadioItemComponent() {
		setRendererType(null);
	}

	public MenuRadioItemComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final java.lang.Object getRadioValue() {
		return getRadioValue(null);
	}

	public final java.lang.Object getRadioValue(javax.faces.context.FacesContext facesContext) {
		return engine.getProperty(Properties.RADIO_VALUE, facesContext);
	}

	public final void setRadioValue(java.lang.Object radioValue) {
		engine.setProperty(Properties.RADIO_VALUE, radioValue);
	}

	public final void setRadioValue(ValueBinding radioValue) {
		engine.setProperty(Properties.RADIO_VALUE, radioValue);
	}

	public final java.lang.String getGroupName() {
		return getGroupName(null);
	}

	public final java.lang.String getGroupName(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.GROUP_NAME, facesContext);
	}

	public final void setGroupName(java.lang.String groupName) {
		engine.setProperty(Properties.GROUP_NAME, groupName);
	}

	public final void setGroupName(ValueBinding groupName) {
		engine.setProperty(Properties.GROUP_NAME, groupName);
	}

	public void release() {
		super.release();
	}
}
