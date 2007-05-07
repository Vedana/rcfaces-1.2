package org.rcfaces.renderkit.html.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.internal.component.CameliaBaseComponent;
import org.rcfaces.core.internal.component.Properties;

public class NameSpaceComponent extends CameliaBaseComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.html.nameSpace";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"uri","prefix"}));
	}

	public NameSpaceComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public NameSpaceComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final String getUri() {
		return getUri(null);
	}

	public final String getUri(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.URI, facesContext);
	}

	public final void setUri(String uri) {
		engine.setProperty(Properties.URI, uri);
	}

	public final void setUri(ValueBinding uri) {
		engine.setProperty(Properties.URI, uri);
	}

	/**
	 * Returns <code>true</code> if the attribute "uri" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isUriSetted() {
		return engine.isPropertySetted(Properties.URI);
	}

	public final String getPrefix() {
		return getPrefix(null);
	}

	public final String getPrefix(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.PREFIX, facesContext);
	}

	public final void setPrefix(String prefix) {
		engine.setProperty(Properties.PREFIX, prefix);
	}

	public final void setPrefix(ValueBinding prefix) {
		engine.setProperty(Properties.PREFIX, prefix);
	}

	/**
	 * Returns <code>true</code> if the attribute "prefix" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isPrefixSetted() {
		return engine.isPropertySetted(Properties.PREFIX);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
