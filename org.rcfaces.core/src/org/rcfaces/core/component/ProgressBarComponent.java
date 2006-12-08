package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.faces.el.ValueBinding;
import java.util.Arrays;
import org.rcfaces.core.component.AbstractOutputComponent;
import java.util.Set;
import java.util.HashSet;

public class ProgressBarComponent extends AbstractOutputComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.progressBar";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractOutputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"indeterminate","minimum","maximum"}));
	}

	public ProgressBarComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ProgressBarComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final double getMinimum() {
		return getMinimum(null);
	}

	public final double getMinimum(javax.faces.context.FacesContext facesContext) {
		return engine.getDoubleProperty(Properties.MINIMUM, 0.0, facesContext);
	}

	public final void setMinimum(double minimum) {
		engine.setProperty(Properties.MINIMUM, minimum);
	}

	public final void setMinimum(ValueBinding minimum) {
		engine.setProperty(Properties.MINIMUM, minimum);
	}

	public final boolean isMinimumSetted() {
		return engine.isPropertySetted(Properties.MINIMUM);
	}

	public final double getMaximum() {
		return getMaximum(null);
	}

	public final double getMaximum(javax.faces.context.FacesContext facesContext) {
		return engine.getDoubleProperty(Properties.MAXIMUM, 0.0, facesContext);
	}

	public final void setMaximum(double maximum) {
		engine.setProperty(Properties.MAXIMUM, maximum);
	}

	public final void setMaximum(ValueBinding maximum) {
		engine.setProperty(Properties.MAXIMUM, maximum);
	}

	public final boolean isMaximumSetted() {
		return engine.isPropertySetted(Properties.MAXIMUM);
	}

	public final boolean isIndeterminate() {
		return isIndeterminate(null);
	}

	public final boolean isIndeterminate(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.INDETERMINATE, false, facesContext);
	}

	public final void setIndeterminate(boolean indeterminate) {
		engine.setProperty(Properties.INDETERMINATE, indeterminate);
	}

	public final void setIndeterminate(ValueBinding indeterminate) {
		engine.setProperty(Properties.INDETERMINATE, indeterminate);
	}

	public final boolean isIndeterminateSetted() {
		return engine.isPropertySetted(Properties.INDETERMINATE);
	}

	public void release() {
		super.release();
	}
	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
