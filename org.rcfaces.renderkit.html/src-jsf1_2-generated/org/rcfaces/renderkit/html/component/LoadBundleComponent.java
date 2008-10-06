package org.rcfaces.renderkit.html.component;

import javax.el.ValueExpression;
import org.rcfaces.renderkit.html.component.Properties;
import java.util.Arrays;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import java.util.Set;
import java.util.HashSet;

public class LoadBundleComponent extends CameliaBaseComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.html.loadBundle";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"override","baseName","serverScope","bundleName","side"}));
	}

	public LoadBundleComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public LoadBundleComponent(String componentId) {
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

	public String getSide() {
		return getSide(null);
	}

	public String getSide(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SIDE, facesContext);
	}

	public void setSide(String side) {
		engine.setProperty(Properties.SIDE, side);
	}

	/**
	 * Returns <code>true</code> if the attribute "side" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSideSetted() {
		return engine.isPropertySetted(Properties.SIDE);
	}

	public String getServerScope() {
		return getServerScope(null);
	}

	public String getServerScope(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SERVER_SCOPE, facesContext);
	}

	public void setServerScope(String serverScope) {
		engine.setProperty(Properties.SERVER_SCOPE, serverScope);
	}

	/**
	 * Returns <code>true</code> if the attribute "serverScope" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isServerScopeSetted() {
		return engine.isPropertySetted(Properties.SERVER_SCOPE);
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
