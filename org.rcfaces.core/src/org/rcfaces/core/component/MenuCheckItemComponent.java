package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.ICheckedCapability;
import javax.faces.el.ValueBinding;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.MenuItemComponent;

/**
 * A menu item with a check.
 */
public class MenuCheckItemComponent extends MenuItemComponent implements 
	ICheckedCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.menuCheckItem";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(MenuItemComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"checked"}));
	}

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

	/**
	 * See {@link #isChecked() isChecked()} for more details
	 */
	public final boolean isChecked(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.CHECKED, false, facesContext);
	}

	public final void setChecked(boolean checked) {
		engine.setProperty(Properties.CHECKED, checked);
	}

	/**
	 * See {@link #setChecked(boolean) setChecked(boolean)} for more details
	 */
	public final void setChecked(ValueBinding checked) {
		engine.setProperty(Properties.CHECKED, checked);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
