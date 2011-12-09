package org.rcfaces.core.component;

import org.rcfaces.core.component.TextEntryComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import java.util.Collection;

/**
 * <p>The spinner Component is a <a href="/comps/textEntryComponent.html">textEntry Component</a> that accepts numbers and whose value can be incremented or decremented (by a parametrable step) with buttons or the keyboard arrows.</p>
 * <p>The spinner Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text &amp; font</li>
 * <li>Help</li>
 * <li>Visibility, Read-Only, Disabled</li>
 * <li>Events Handling</li>
 * <li>limits and step</li>
 * </ul>
 * </p>
 */
public class SpinnerComponent extends TextEntryComponent {

	private static final Log LOG = LogFactory.getLog(SpinnerComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.spinner";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=TextEntryComponent.BEHAVIOR_EVENT_NAMES;

	public SpinnerComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public SpinnerComponent(String componentId) {
		this();
		setId(componentId);
	}

	public double getMinimum() {
		return getMinimum(null);
	}

	public double getMinimum(javax.faces.context.FacesContext facesContext) {
		return (Double)getStateHelper().eval(Properties.MINIMUM, 0.0);
	}

	public void setMinimum(double minimum) {
		 getStateHelper().put(Properties.MINIMUM, minimum);
	}

	/**
	 * Returns <code>true</code> if the attribute "minimum" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isMinimumSetted() {
		return getStateHelper().get(Properties.MINIMUM)!=null;
	}

	/**
	 * Returns an int value specifying the upper limit for the value of the component.
	 * @return maximum
	 */
	public double getMaximum() {
		return getMaximum(null);
	}

	/**
	 * Returns an int value specifying the upper limit for the value of the component.
	 * @return maximum
	 */
	public double getMaximum(javax.faces.context.FacesContext facesContext) {
		return (Double)getStateHelper().eval(Properties.MAXIMUM, 0.0);
	}

	/**
	 * Sets an int value specifying the upper limit for the value of the component.
	 * @param maximum maximum
	 */
	public void setMaximum(double maximum) {
		 getStateHelper().put(Properties.MAXIMUM, maximum);
	}

	/**
	 * Sets an int value specifying the upper limit for the value of the component.
	 * @param maximum maximum
	 */
	/**
	 * Returns <code>true</code> if the attribute "maximum" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isMaximumSetted() {
		return getStateHelper().get(Properties.MAXIMUM)!=null;
	}

	/**
	 * Returns a boolean value indicating wether to start from the beginning when reaching the end or not. And vice versa.
	 * @return true if the component should cycle
	 */
	public boolean isCycleValue() {
		return isCycleValue(null);
	}

	/**
	 * Returns a boolean value indicating wether to start from the beginning when reaching the end or not. And vice versa.
	 * @return true if the component should cycle
	 */
	public boolean isCycleValue(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.CYCLE_VALUE, false);
	}

	/**
	 * Sets a boolean value indicating wether to start from the beginning when reaching the end or not. And vice versa.
	 * @param cycleValue true if the component should cycle
	 */
	public void setCycleValue(boolean cycleValue) {
		 getStateHelper().put(Properties.CYCLE_VALUE, cycleValue);
	}

	/**
	 * Sets a boolean value indicating wether to start from the beginning when reaching the end or not. And vice versa.
	 * @param cycleValue true if the component should cycle
	 */
	/**
	 * Returns <code>true</code> if the attribute "cycleValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isCycleValueSetted() {
		return getStateHelper().get(Properties.CYCLE_VALUE)!=null;
	}

	public String getStep() {
		return getStep(null);
	}

	public String getStep(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.STEP);
	}

	public void setStep(String step) {
		 getStateHelper().put(Properties.STEP, step);
	}

	/**
	 * Returns <code>true</code> if the attribute "step" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isStepSetted() {
		return getStateHelper().get(Properties.STEP)!=null;
	}

}
