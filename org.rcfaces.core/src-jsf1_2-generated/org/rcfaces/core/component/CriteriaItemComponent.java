package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.SelectItemComponent;

public class CriteriaItemComponent extends SelectItemComponent {

	private static final Log LOG = LogFactory.getLog(CriteriaItemComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.criteriaItem";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(SelectItemComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"startingValue","logicalFilter"}));
	}

	public CriteriaItemComponent() {
		setRendererType(null);
	}

	public CriteriaItemComponent(String componentId) {
		this();
		setId(componentId);
	}

	public String getLogicalFilter() {
		return getLogicalFilter(null);
	}

	public String getLogicalFilter(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.LOGICAL_FILTER, facesContext);


return s;
	}

	public void setLogicalFilter(String logicalFilter) {
		engine.setProperty(Properties.LOGICAL_FILTER, logicalFilter);
	}

	/**
	 * Returns <code>true</code> if the attribute "logicalFilter" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isLogicalFilterSetted() {
		return engine.isPropertySetted(Properties.LOGICAL_FILTER);
	}

	public Object getStartingValue() {
		return getStartingValue(null);
	}

	public Object getStartingValue(javax.faces.context.FacesContext facesContext) {
		return engine.getValue(Properties.STARTING_VALUE, facesContext);
	}

	public void setStartingValue(Object startingValue) {
		engine.setValue(Properties.STARTING_VALUE, startingValue);
	}

	/**
	 * Returns <code>true</code> if the attribute "startingValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isStartingValueSetted() {
		return engine.isPropertySetted(Properties.STARTING_VALUE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
