package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;

public class ToolTipManagerComponent extends CameliaBaseComponent {

	private static final Log LOG = LogFactory.getLog(ToolTipManagerComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.toolTipManager";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"delay"}));
	}

	public ToolTipManagerComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ToolTipManagerComponent(String componentId) {
		this();
		setId(componentId);
	}

	public int getDelay() {
		return getDelay(null);
	}

	public int getDelay(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.DELAY, -1, facesContext);
	}

	public void setDelay(int delay) {
		engine.setProperty(Properties.DELAY, delay);
	}

	/**
	 * Returns <code>true</code> if the attribute "delay" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDelaySetted() {
		return engine.isPropertySetted(Properties.DELAY);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
