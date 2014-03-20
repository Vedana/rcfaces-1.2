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

/**
 * A menu item with a radiobutton (see attribute GroupName)
 */
public class MenuRadioItemComponent extends MenuCheckItemComponent implements 
	IRadioValueCapability {

	private static final Log LOG = LogFactory.getLog(MenuRadioItemComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.menuRadioItem";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(MenuCheckItemComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"groupName","radioValue"}));
	}

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
		return engine.getProperty(Properties.RADIO_VALUE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "radioValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRadioValueSetted() {
		return engine.isPropertySetted(Properties.RADIO_VALUE);
	}

	public void setRadioValue(java.lang.Object radioValue) {
		engine.setProperty(Properties.RADIO_VALUE, radioValue);
	}

	public java.lang.String getGroupName() {
		return getGroupName(null);
	}

	/**
	 * See {@link #getGroupName() getGroupName()} for more details
	 */
	public java.lang.String getGroupName(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.GROUP_NAME, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "groupName" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isGroupNameSetted() {
		return engine.isPropertySetted(Properties.GROUP_NAME);
	}

	public void setGroupName(java.lang.String groupName) {
		engine.setProperty(Properties.GROUP_NAME, groupName);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
