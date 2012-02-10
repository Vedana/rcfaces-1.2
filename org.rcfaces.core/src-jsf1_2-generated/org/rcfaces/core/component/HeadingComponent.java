package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.TextComponent;
import javax.el.ValueExpression;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;

public class HeadingComponent extends TextComponent {

	private static final Log LOG = LogFactory.getLog(HeadingComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.heading";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(TextComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"level"}));
	}

	public HeadingComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public HeadingComponent(String componentId) {
		this();
		setId(componentId);
	}

	public int getLevel() {
		return getLevel(null);
	}

	public int getLevel(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.LEVEL, 0, facesContext);
	}

	public void setLevel(int level) {
		engine.setProperty(Properties.LEVEL, level);
	}

	/**
	 * Returns <code>true</code> if the attribute "level" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isLevelSetted() {
		return engine.isPropertySetted(Properties.LEVEL);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
