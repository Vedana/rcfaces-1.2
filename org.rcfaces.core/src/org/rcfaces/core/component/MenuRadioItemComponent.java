package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IRadioValueCapability;
import javax.faces.el.ValueBinding;
import java.util.Arrays;
import org.rcfaces.core.component.MenuCheckItemComponent;
import java.util.Set;
import java.util.HashSet;

/**
 * A menu item with a radiobutton (see attribute GroupName)
 */
public class MenuRadioItemComponent extends MenuCheckItemComponent implements 
	IRadioValueCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.menuRadioItem";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(MenuCheckItemComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"radioValue","groupName"}));
	}

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

	/**
	 * See {@link #getRadioValue() getRadioValue()} for more details
	 */
	public final java.lang.Object getRadioValue(javax.faces.context.FacesContext facesContext) {
		return engine.getProperty(Properties.RADIO_VALUE, facesContext);
	}

	public final void setRadioValue(java.lang.Object radioValue) {
		engine.setProperty(Properties.RADIO_VALUE, radioValue);
	}

	/**
	 * See {@link #setRadioValue(Object) setRadioValue(Object)} for more details
	 */
	public final void setRadioValue(ValueBinding radioValue) {
		engine.setProperty(Properties.RADIO_VALUE, radioValue);
	}

	public final java.lang.String getGroupName() {
		return getGroupName(null);
	}

	/**
	 * See {@link #getGroupName() getGroupName()} for more details
	 */
	public final java.lang.String getGroupName(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.GROUP_NAME, facesContext);
	}

	public final void setGroupName(java.lang.String groupName) {
		engine.setProperty(Properties.GROUP_NAME, groupName);
	}

	/**
	 * See {@link #setGroupName(String) setGroupName(String)} for more details
	 */
	public final void setGroupName(ValueBinding groupName) {
		engine.setProperty(Properties.GROUP_NAME, groupName);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
