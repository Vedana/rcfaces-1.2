package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.TextEntryComponent;

public class SpinnerComponent extends TextEntryComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.spinner";


	public SpinnerComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public SpinnerComponent(String componentId) {
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

	public final boolean isCycleValue() {
		return isCycleValue(null);
	}

	public final boolean isCycleValue(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.CYCLE_VALUE, false, facesContext);
	}

	public final void setCycleValue(boolean cycleValue) {
		engine.setProperty(Properties.CYCLE_VALUE, cycleValue);
	}

	public final void setCycleValue(ValueBinding cycleValue) {
		engine.setProperty(Properties.CYCLE_VALUE, cycleValue);
	}

	public final boolean isCycleValueSetted() {
		return engine.isPropertySetted(Properties.CYCLE_VALUE);
	}

	public final String getStep() {
		return getStep(null);
	}

	public final String getStep(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.STEP, facesContext);
	}

	public final void setStep(String step) {
		engine.setProperty(Properties.STEP, step);
	}

	public final void setStep(ValueBinding step) {
		engine.setProperty(Properties.STEP, step);
	}

	public final boolean isStepSetted() {
		return engine.isPropertySetted(Properties.STEP);
	}

	public void release() {
		super.release();
	}
}
