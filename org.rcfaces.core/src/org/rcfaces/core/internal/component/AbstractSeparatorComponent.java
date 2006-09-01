package org.rcfaces.core.internal.component;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.ISeparatorComponent;
import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.converter.HiddenModeConverter;

public abstract class AbstractSeparatorComponent extends CameliaBaseComponent implements 
	IVisibilityCapability,
	ISeparatorComponent {



	public final void setHiddenMode(String hiddenMode) {


			setHiddenMode(((Integer)HiddenModeConverter.SINGLETON.getAsObject(null, null, hiddenMode)).intValue());
		
	}

	public final java.lang.Boolean getVisible() {
		return getVisible(null);
	}

	public final java.lang.Boolean getVisible(javax.faces.context.FacesContext facesContext) {
		return engine.getBooleanProperty(Properties.VISIBLE, facesContext);
	}

	public final void setVisible(java.lang.Boolean visible) {
		engine.setProperty(Properties.VISIBLE, visible);
	}

	public final void setVisible(ValueBinding visible) {
		engine.setProperty(Properties.VISIBLE, visible);
	}

	public final int getHiddenMode() {
		return getHiddenMode(null);
	}

	public final int getHiddenMode(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.HIDDEN_MODE,0, facesContext);
	}

	public final void setHiddenMode(int hiddenMode) {
		engine.setProperty(Properties.HIDDEN_MODE, hiddenMode);
	}

	public final void setHiddenMode(ValueBinding hiddenMode) {
		engine.setProperty(Properties.HIDDEN_MODE, hiddenMode);
	}

	public void release() {
		super.release();
	}
}
