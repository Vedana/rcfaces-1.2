package org.rcfaces.renderkit.html.component;

import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;

/**
 * Loads a property bundle on the client side.
 */
public class LoadClientBundleComponent extends CameliaBaseComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.html.loadClientBundle";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"override","serverSide","baseName","bundleName"}));
	}

	public LoadClientBundleComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public LoadClientBundleComponent(String componentId) {
		this();
		setId(componentId);
	}

	/**
	 * Returns a string value specifying the name to be used on the client side to access the resource bundle.
	 * @return bundle name
	 */
	public String getBundleName() {
		return getBundleName(null);
	}

	/**
	 * Returns a string value specifying the name to be used on the client side to access the resource bundle.
	 * @return bundle name
	 */
	public String getBundleName(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BUNDLE_NAME, facesContext);
	}

	/**
	 * Sets a string value specifying the name to be used on the client side to access the resource bundle.
	 * @param bundleName bundle name
	 */
	public void setBundleName(String bundleName) {
		engine.setProperty(Properties.BUNDLE_NAME, bundleName);
	}

	/**
	 * Sets a string value specifying the name to be used on the client side to access the resource bundle.
	 * @param bundleName bundle name
	 */
	/**
	 * Returns <code>true</code> if the attribute "bundleName" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isBundleNameSetted() {
		return engine.isPropertySetted(Properties.BUNDLE_NAME);
	}

	/**
	 * Returns a string indicating the fully qualified name of a resources bundle (property file).
	 * @return base name of the resource bundle to be loaded.
	 */
	public String getBaseName() {
		return getBaseName(null);
	}

	/**
	 * Returns a string indicating the fully qualified name of a resources bundle (property file).
	 * @return base name of the resource bundle to be loaded.
	 */
	public String getBaseName(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BASE_NAME, facesContext);
	}

	/**
	 * Sets a string indicating the fully qualified name of a resources bundle (property file).
	 * @param baseName base name of the resource bundle to be loaded.
	 */
	public void setBaseName(String baseName) {
		engine.setProperty(Properties.BASE_NAME, baseName);
	}

	/**
	 * Sets a string indicating the fully qualified name of a resources bundle (property file).
	 * @param baseName base name of the resource bundle to be loaded.
	 */
	/**
	 * Returns <code>true</code> if the attribute "baseName" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isBaseNameSetted() {
		return engine.isPropertySetted(Properties.BASE_NAME);
	}

	/**
	 * Returns a boolean value indicating wether the resource is available on the server side.
	 * @return true if the resource is available on the server side
	 */
	public boolean isServerSide() {
		return isServerSide(null);
	}

	/**
	 * Returns a boolean value indicating wether the resource is available on the server side.
	 * @return true if the resource is available on the server side
	 */
	public boolean isServerSide(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.SERVER_SIDE, false, facesContext);
	}

	/**
	 * Sets a boolean value indicating wether the resource is available on the server side.
	 * @param serverSide true if the resource is available on the server side
	 */
	public void setServerSide(boolean serverSide) {
		engine.setProperty(Properties.SERVER_SIDE, serverSide);
	}

	/**
	 * Sets a boolean value indicating wether the resource is available on the server side.
	 * @param serverSide true if the resource is available on the server side
	 */
	/**
	 * Returns <code>true</code> if the attribute "serverSide" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isServerSideSetted() {
		return engine.isPropertySetted(Properties.SERVER_SIDE);
	}

	public boolean isOverride() {
		return isOverride(null);
	}

	public boolean isOverride(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.OVERRIDE, false, facesContext);
	}

	public void setOverride(boolean override) {
		engine.setProperty(Properties.OVERRIDE, override);
	}

	/**
	 * Returns <code>true</code> if the attribute "override" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isOverrideSetted() {
		return engine.isPropertySetted(Properties.OVERRIDE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
