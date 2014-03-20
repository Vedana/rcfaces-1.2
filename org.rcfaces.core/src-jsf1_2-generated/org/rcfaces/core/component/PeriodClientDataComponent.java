package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;

public class PeriodClientDataComponent extends CameliaBaseComponent {

	private static final Log LOG = LogFactory.getLog(PeriodClientDataComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.periodClientData";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"name","value"}));
	}

	public PeriodClientDataComponent() {
		setRendererType(null);
	}

	public PeriodClientDataComponent(String componentId) {
		this();
		setId(componentId);
	}

	public String getName() {
		return getName(null);
	}

	public String getName(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.NAME, facesContext);
		return s;
	}

	public void setName(String name) {
		engine.setProperty(Properties.NAME, name);
	}

	/**
	 * Returns <code>true</code> if the attribute "name" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isNameSetted() {
		return engine.isPropertySetted(Properties.NAME);
	}

	public String getValue() {
		return getValue(null);
	}

	public String getValue(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.VALUE, facesContext);
		return s;
	}

	public void setValue(String value) {
		engine.setProperty(Properties.VALUE, value);
	}

	/**
	 * Returns <code>true</code> if the attribute "value" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isValueSetted() {
		return engine.isPropertySetted(Properties.VALUE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
