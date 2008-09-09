package org.rcfaces.renderkit.html.component;

import org.rcfaces.core.internal.component.CameliaBaseComponent;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;
import javax.faces.el.ValueBinding;
import org.rcfaces.renderkit.html.component.Properties;

public class NameSpaceComponent extends CameliaBaseComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.html.nameSpace";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"prefix","uri"}));
	}

	public NameSpaceComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public NameSpaceComponent(String componentId) {
		this();
		setId(componentId);
	}

	public String getUri() {
		return getUri(null);
	}

	public String getUri(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.URI, facesContext);
	}

	public void setUri(String uri) {
		engine.setProperty(Properties.URI, uri);
	}

	/**
	 * Returns <code>true</code> if the attribute "uri" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isUriSetted() {
		return engine.isPropertySetted(Properties.URI);
	}

	public String getPrefix() {
		return getPrefix(null);
	}

	public String getPrefix(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.PREFIX, facesContext);
	}

	public void setPrefix(String prefix) {
		engine.setProperty(Properties.PREFIX, prefix);
	}

	/**
	 * Returns <code>true</code> if the attribute "prefix" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPrefixSetted() {
		return engine.isPropertySetted(Properties.PREFIX);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
