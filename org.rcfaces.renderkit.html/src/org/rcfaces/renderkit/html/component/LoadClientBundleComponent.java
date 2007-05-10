package org.rcfaces.renderkit.html.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.internal.component.CameliaBaseComponent;
import org.rcfaces.core.internal.component.Properties;

/**
 * Loads a property bundle on the client side.
 */
public class LoadClientBundleComponent extends CameliaBaseComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.html.loadClientBundle";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"override","baseName","bundleName","serverSide"}));
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
	public final String getBundleName() {
		return getBundleName(null);
	}

	/**
	 * Returns a string value specifying the name to be used on the client side to access the resource bundle.
	 * @return bundle name
	 */
	public final String getBundleName(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BUNDLE_NAME, facesContext);
	}

	/**
	 * Sets a string value specifying the name to be used on the client side to access the resource bundle.
	 * @param bundleName bundle name
	 */
	public final void setBundleName(String bundleName) {
		engine.setProperty(Properties.BUNDLE_NAME, bundleName);
	}

	/**
	 * Sets a string value specifying the name to be used on the client side to access the resource bundle.
	 * @param bundleName bundle name
	 */
	public final void setBundleName(ValueBinding bundleName) {
		engine.setProperty(Properties.BUNDLE_NAME, bundleName);
	}

	/**
	 * Returns <code>true</code> if the attribute "bundleName" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBundleNameSetted() {
		return engine.isPropertySetted(Properties.BUNDLE_NAME);
	}

	/**
	 * Returns a string indicating the fully qualified name of a resources bundle (property file).
	 * @return base name of the resource bundle to be loaded.
	 */
	public final String getBaseName() {
		return getBaseName(null);
	}

	/**
	 * Returns a string indicating the fully qualified name of a resources bundle (property file).
	 * @return base name of the resource bundle to be loaded.
	 */
	public final String getBaseName(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BASE_NAME, facesContext);
	}

	/**
	 * Sets a string indicating the fully qualified name of a resources bundle (property file).
	 * @param baseName base name of the resource bundle to be loaded.
	 */
	public final void setBaseName(String baseName) {
		engine.setProperty(Properties.BASE_NAME, baseName);
	}

	/**
	 * Sets a string indicating the fully qualified name of a resources bundle (property file).
	 * @param baseName base name of the resource bundle to be loaded.
	 */
	public final void setBaseName(ValueBinding baseName) {
		engine.setProperty(Properties.BASE_NAME, baseName);
	}

	/**
	 * Returns <code>true</code> if the attribute "baseName" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBaseNameSetted() {
		return engine.isPropertySetted(Properties.BASE_NAME);
	}

	/**
	 * Returns a boolean value indicating wether the resource is available on the server side.
	 * @return true if the resource is available on the server side
	 */
	public final boolean isServerSide() {
		return isServerSide(null);
	}

	/**
	 * Returns a boolean value indicating wether the resource is available on the server side.
	 * @return true if the resource is available on the server side
	 */
	public final boolean isServerSide(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.SERVER_SIDE, false, facesContext);
	}

	/**
	 * Sets a boolean value indicating wether the resource is available on the server side.
	 * @param serverSide true if the resource is available on the server side
	 */
	public final void setServerSide(boolean serverSide) {
		engine.setProperty(Properties.SERVER_SIDE, serverSide);
	}

	/**
	 * Sets a boolean value indicating wether the resource is available on the server side.
	 * @param serverSide true if the resource is available on the server side
	 */
	public final void setServerSide(ValueBinding serverSide) {
		engine.setProperty(Properties.SERVER_SIDE, serverSide);
	}

	/**
	 * Returns <code>true</code> if the attribute "serverSide" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isServerSideSetted() {
		return engine.isPropertySetted(Properties.SERVER_SIDE);
	}

	public final boolean isOverride() {
		return isOverride(null);
	}

	public final boolean isOverride(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.OVERRIDE, false, facesContext);
	}

	public final void setOverride(boolean override) {
		engine.setProperty(Properties.OVERRIDE, override);
	}

	public final void setOverride(ValueBinding override) {
		engine.setProperty(Properties.OVERRIDE, override);
	}

	/**
	 * Returns <code>true</code> if the attribute "override" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isOverrideSetted() {
		return engine.isPropertySetted(Properties.OVERRIDE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
