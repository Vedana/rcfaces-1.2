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
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"showDelayMs","neighbourThresholdMs"}));
	}

	public ToolTipManagerComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ToolTipManagerComponent(String componentId) {
		this();
		setId(componentId);
	}

	public int getShowDelayMs() {
		return getShowDelayMs(null);
	}

	public int getShowDelayMs(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.SHOW_DELAY_MS, -1, facesContext);
	}

	public void setShowDelayMs(int showDelayMs) {
		engine.setProperty(Properties.SHOW_DELAY_MS, showDelayMs);
	}

	/**
	 * Returns <code>true</code> if the attribute "showDelayMs" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isShowDelayMsSetted() {
		return engine.isPropertySetted(Properties.SHOW_DELAY_MS);
	}

	public int getNeighbourThresholdMs() {
		return getNeighbourThresholdMs(null);
	}

	public int getNeighbourThresholdMs(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.NEIGHBOUR_THRESHOLD_MS, -1, facesContext);
	}

	public void setNeighbourThresholdMs(int neighbourThresholdMs) {
		engine.setProperty(Properties.NEIGHBOUR_THRESHOLD_MS, neighbourThresholdMs);
	}

	/**
	 * Returns <code>true</code> if the attribute "neighbourThresholdMs" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isNeighbourThresholdMsSetted() {
		return engine.isPropertySetted(Properties.NEIGHBOUR_THRESHOLD_MS);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
