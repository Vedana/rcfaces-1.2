package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import java.util.Arrays;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import java.util.Set;
import java.util.HashSet;

public class PeriodClientDataComponent extends CameliaBaseComponent {

	private static final Log LOG = LogFactory.getLog(PeriodClientDataComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.periodClientData";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"value","name"}));
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
		return engine.getStringProperty(Properties.NAME, facesContext);
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
		return engine.getStringProperty(Properties.VALUE, facesContext);
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
