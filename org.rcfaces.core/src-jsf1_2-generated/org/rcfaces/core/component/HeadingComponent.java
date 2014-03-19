package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.TextComponent;
import javax.el.ValueExpression;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IHeadingLevelCapability;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;

public class HeadingComponent extends TextComponent implements 
	IHeadingLevelCapability {

	private static final Log LOG = LogFactory.getLog(HeadingComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.heading";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(TextComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"headingLevel"}));
	}

	public HeadingComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public HeadingComponent(String componentId) {
		this();
		setId(componentId);
	}

	public java.lang.String getHeadingLevel() {
		return getHeadingLevel(null);
	}

	/**
	 * See {@link #getHeadingLevel() getHeadingLevel()} for more details
	 */
	public java.lang.String getHeadingLevel(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.HEADING_LEVEL, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "headingLevel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHeadingLevelSetted() {
		return engine.isPropertySetted(Properties.HEADING_LEVEL);
	}

	public void setHeadingLevel(java.lang.String headingLevel) {
		engine.setProperty(Properties.HEADING_LEVEL, headingLevel);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
