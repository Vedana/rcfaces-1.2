package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.CameliaItemsComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;

public class CriteriaItemFillerComponent extends CameliaItemsComponent {

	private static final Log LOG = LogFactory.getLog(CriteriaItemFillerComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.criteriaItemFiller";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaItemsComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"itemValue","logicalFilter"}));
	}

	public CriteriaItemFillerComponent() {
		setRendererType(null);
	}

	public CriteriaItemFillerComponent(String componentId) {
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

	public Object getItemValue() {
		return getItemValue(null);
	}

	public Object getItemValue(javax.faces.context.FacesContext facesContext) {
		return engine.getValue(Properties.ITEM_VALUE, facesContext);
	}

	public void setItemValue(Object itemValue) {
		engine.setValue(Properties.ITEM_VALUE, itemValue);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemValueSetted() {
		return engine.isPropertySetted(Properties.ITEM_VALUE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
