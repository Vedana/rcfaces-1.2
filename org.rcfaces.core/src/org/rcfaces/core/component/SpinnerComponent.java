package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.TextEntryComponent;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class SpinnerComponent extends TextEntryComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.spinner";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(TextEntryComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"minimum","step","cycleValue","maximum"}));
	}

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

	/**
	 * Returns <code>true</code> if the attribute "minimum" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMinimumSetted() {
		return engine.isPropertySetted(Properties.MINIMUM);
	}

	/**
	 * Returns an int value specifying the upper limit for the value of the component.
	 * @return maximum
	 */
	public final double getMaximum() {
		return getMaximum(null);
	}

	/**
	 * Returns an int value specifying the upper limit for the value of the component.
	 * @return maximum
	 */
	public final double getMaximum(javax.faces.context.FacesContext facesContext) {
		return engine.getDoubleProperty(Properties.MAXIMUM, 0.0, facesContext);
	}

	/**
	 * Sets an int value specifying the upper limit for the value of the component.
	 * @param maximum maximum
	 */
	public final void setMaximum(double maximum) {
		engine.setProperty(Properties.MAXIMUM, maximum);
	}

	/**
	 * Sets an int value specifying the upper limit for the value of the component.
	 * @param maximum maximum
	 */
	public final void setMaximum(ValueBinding maximum) {
		engine.setProperty(Properties.MAXIMUM, maximum);
	}

	/**
	 * Returns <code>true</code> if the attribute "maximum" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMaximumSetted() {
		return engine.isPropertySetted(Properties.MAXIMUM);
	}

	/**
	 * Returns a boolean value indicating wether to start from the beginning when reaching the end or not. And vice versa.
	 * @return true if the component should cycle
	 */
	public final boolean isCycleValue() {
		return isCycleValue(null);
	}

	/**
	 * Returns a boolean value indicating wether to start from the beginning when reaching the end or not. And vice versa.
	 * @return true if the component should cycle
	 */
	public final boolean isCycleValue(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.CYCLE_VALUE, false, facesContext);
	}

	/**
	 * Sets a boolean value indicating wether to start from the beginning when reaching the end or not. And vice versa.
	 * @param cycleValue true if the component should cycle
	 */
	public final void setCycleValue(boolean cycleValue) {
		engine.setProperty(Properties.CYCLE_VALUE, cycleValue);
	}

	/**
	 * Sets a boolean value indicating wether to start from the beginning when reaching the end or not. And vice versa.
	 * @param cycleValue true if the component should cycle
	 */
	public final void setCycleValue(ValueBinding cycleValue) {
		engine.setProperty(Properties.CYCLE_VALUE, cycleValue);
	}

	/**
	 * Returns <code>true</code> if the attribute "cycleValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
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

	/**
	 * Returns <code>true</code> if the attribute "step" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isStepSetted() {
		return engine.isPropertySetted(Properties.STEP);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
