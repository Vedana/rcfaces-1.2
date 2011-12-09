package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.AbstractInputComponent;
import java.util.Collection;

/**
 * <p>The progressBar Component indicates the evolution of a task.</p>
 * <p>The progressBar Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>image</li>
 * <li>Margin &amp; border</li>
 * <li>Help</li>
 * <li>Visibility</li>
 * </ul>
 * </p>
 */
public class ProgressBarComponent extends AbstractInputComponent {

	private static final Log LOG = LogFactory.getLog(ProgressBarComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.progressBar";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractInputComponent.BEHAVIOR_EVENT_NAMES;

	public ProgressBarComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ProgressBarComponent(String componentId) {
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
	 * Returns a boolean value indicating wether the value is not determinated.
	 * @return true if not determinated
	 */
	public boolean isIndeterminate() {
		return isIndeterminate(null);
	}

	/**
	 * Returns a boolean value indicating wether the value is not determinated.
	 * @return true if not determinated
	 */
	public boolean isIndeterminate(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.INDETERMINATE, false);
	}

	/**
	 * Sets a boolean value indicating wether the value is not determinated.
	 * @param indeterminate true if not determinated
	 */
	public void setIndeterminate(boolean indeterminate) {
		 getStateHelper().put(Properties.INDETERMINATE, indeterminate);
	}

	/**
	 * Sets a boolean value indicating wether the value is not determinated.
	 * @param indeterminate true if not determinated
	 */
	/**
	 * Returns <code>true</code> if the attribute "indeterminate" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isIndeterminateSetted() {
		return getStateHelper().get(Properties.INDETERMINATE)!=null;
	}

}
