package org.rcfaces.renderkit.html.component;

import org.rcfaces.core.internal.component.Properties;
import javax.faces.el.ValueBinding;
import java.util.Arrays;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import java.util.Set;
import java.util.HashSet;

public class LoadClientBundleComponent extends CameliaBaseComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.html.loadClientBundle";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"baseName","bundleName","serverSide"}));
	}

	public LoadClientBundleComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public LoadClientBundleComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final String getBundleName() {
		return getBundleName(null);
	}

	public final String getBundleName(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BUNDLE_NAME, facesContext);
	}

	public final void setBundleName(String bundleName) {
		engine.setProperty(Properties.BUNDLE_NAME, bundleName);
	}

	public final void setBundleName(ValueBinding bundleName) {
		engine.setProperty(Properties.BUNDLE_NAME, bundleName);
	}

	public final boolean isBundleNameSetted() {
		return engine.isPropertySetted(Properties.BUNDLE_NAME);
	}

	public final String getBaseName() {
		return getBaseName(null);
	}

	public final String getBaseName(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BASE_NAME, facesContext);
	}

	public final void setBaseName(String baseName) {
		engine.setProperty(Properties.BASE_NAME, baseName);
	}

	public final void setBaseName(ValueBinding baseName) {
		engine.setProperty(Properties.BASE_NAME, baseName);
	}

	public final boolean isBaseNameSetted() {
		return engine.isPropertySetted(Properties.BASE_NAME);
	}

	public final boolean isServerSide() {
		return isServerSide(null);
	}

	public final boolean isServerSide(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.SERVER_SIDE, false, facesContext);
	}

	public final void setServerSide(boolean serverSide) {
		engine.setProperty(Properties.SERVER_SIDE, serverSide);
	}

	public final void setServerSide(ValueBinding serverSide) {
		engine.setProperty(Properties.SERVER_SIDE, serverSide);
	}

	public final boolean isServerSideSetted() {
		return engine.isPropertySetted(Properties.SERVER_SIDE);
	}

	public void release() {
		super.release();
	}
	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
