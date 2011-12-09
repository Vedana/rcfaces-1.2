package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import java.util.Collection;

public class PeriodClientDataComponent extends CameliaBaseComponent {

	private static final Log LOG = LogFactory.getLog(PeriodClientDataComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.periodClientData";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=CameliaBaseComponent.BEHAVIOR_EVENT_NAMES;

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
		return (String)getStateHelper().eval(Properties.NAME);
	}

	public void setName(String name) {
		 getStateHelper().put(Properties.NAME, name);
	}

	/**
	 * Returns <code>true</code> if the attribute "name" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isNameSetted() {
		return getStateHelper().get(Properties.NAME)!=null;
	}

	public String getValue() {
		return getValue(null);
	}

	public String getValue(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.VALUE);
	}

	public void setValue(String value) {
		 getStateHelper().put(Properties.VALUE, value);
	}

	/**
	 * Returns <code>true</code> if the attribute "value" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isValueSetted() {
		return getStateHelper().get(Properties.VALUE)!=null;
	}

}
