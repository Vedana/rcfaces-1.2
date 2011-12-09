package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.rcfaces.core.component.capability.IRadioValueCapability;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.MenuCheckItemComponent;
import java.util.Collection;

/**
 * A menu item with a radiobutton (see attribute GroupName)
 */
public class MenuRadioItemComponent extends MenuCheckItemComponent implements 
	IRadioValueCapability {

	private static final Log LOG = LogFactory.getLog(MenuRadioItemComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.menuRadioItem";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=MenuCheckItemComponent.BEHAVIOR_EVENT_NAMES;

	public MenuRadioItemComponent() {
		setRendererType(null);
	}

	public MenuRadioItemComponent(String componentId) {
		this();
		setId(componentId);
	}

	public java.lang.Object getRadioValue() {
		return getRadioValue(null);
	}

	/**
	 * See {@link #getRadioValue() getRadioValue()} for more details
	 */
	public java.lang.Object getRadioValue(javax.faces.context.FacesContext facesContext) {
		return getStateHelper().eval(Properties.RADIO_VALUE);
	}

	/**
	 * Returns <code>true</code> if the attribute "radioValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRadioValueSetted() {
		return getStateHelper().get(Properties.RADIO_VALUE)!=null;
	}

	public void setRadioValue(java.lang.Object radioValue) {
		getStateHelper().put(Properties.RADIO_VALUE, radioValue);
	}

	public java.lang.String getGroupName() {
		return getGroupName(null);
	}

	/**
	 * See {@link #getGroupName() getGroupName()} for more details
	 */
	public java.lang.String getGroupName(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.GROUP_NAME);
	}

	/**
	 * Returns <code>true</code> if the attribute "groupName" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isGroupNameSetted() {
		return getStateHelper().get(Properties.GROUP_NAME)!=null;
	}

	public void setGroupName(java.lang.String groupName) {
		getStateHelper().put(Properties.GROUP_NAME, groupName);
	}

}
