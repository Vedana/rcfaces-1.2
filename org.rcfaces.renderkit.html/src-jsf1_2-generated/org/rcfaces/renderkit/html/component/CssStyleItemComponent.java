package org.rcfaces.renderkit.html.component;

import javax.el.ValueExpression;
import org.rcfaces.renderkit.html.component.capability.IUserAgentVaryCapability;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.FileItemComponent;
import org.rcfaces.renderkit.html.component.Properties;

public class CssStyleItemComponent extends FileItemComponent implements 
	IUserAgentVaryCapability {

	private static final Log LOG = LogFactory.getLog(CssStyleItemComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.html.cssStyleItem";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(FileItemComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"userAgent"}));
	}

	public CssStyleItemComponent() {
		setRendererType(null);
	}

	public CssStyleItemComponent(String componentId) {
		this();
		setId(componentId);
	}

	public java.lang.String getUserAgent() {
		return getUserAgent(null);
	}

	/**
	 * See {@link #getUserAgent() getUserAgent()} for more details
	 */
	public java.lang.String getUserAgent(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.USER_AGENT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "userAgent" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isUserAgentSetted() {
		return engine.isPropertySetted(Properties.USER_AGENT);
	}

	public void setUserAgent(java.lang.String userAgent) {
		engine.setProperty(Properties.USER_AGENT, userAgent);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
