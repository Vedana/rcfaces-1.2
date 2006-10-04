package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.ImageButtonComponent;

public class ImagePagerButtonComponent extends ImageButtonComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.imagePagerButton";


	public ImagePagerButtonComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ImagePagerButtonComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final String getFor() {
		return getFor(null);
	}

	public final String getFor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOR, facesContext);
	}

	public final void setFor(String forVal) {
		engine.setProperty(Properties.FOR, forVal);
	}

	public final void setFor(ValueBinding forVal) {
		engine.setProperty(Properties.FOR, forVal);
	}

	public final boolean isForSetted() {
		return engine.isPropertySetted(Properties.FOR);
	}

	public final String getType() {
		return getType(null);
	}

	public final String getType(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TYPE, facesContext);
	}

	public final void setType(String type) {
		engine.setProperty(Properties.TYPE, type);
	}

	public final void setType(ValueBinding type) {
		engine.setProperty(Properties.TYPE, type);
	}

	public final boolean isTypeSetted() {
		return engine.isPropertySetted(Properties.TYPE);
	}

	public final boolean isHideIfDisabled() {
		return isHideIfDisabled(null);
	}

	public final boolean isHideIfDisabled(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.HIDE_IF_DISABLED, false, facesContext);
	}

	public final void setHideIfDisabled(boolean hideIfDisabled) {
		engine.setProperty(Properties.HIDE_IF_DISABLED, hideIfDisabled);
	}

	public final void setHideIfDisabled(ValueBinding hideIfDisabled) {
		engine.setProperty(Properties.HIDE_IF_DISABLED, hideIfDisabled);
	}

	public final boolean isHideIfDisabledSetted() {
		return engine.isPropertySetted(Properties.HIDE_IF_DISABLED);
	}

	public void release() {
		super.release();
	}
}
