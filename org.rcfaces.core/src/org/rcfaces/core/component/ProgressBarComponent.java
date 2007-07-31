package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.rcfaces.core.internal.component.Properties;

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

	public static final String COMPONENT_TYPE="org.rcfaces.core.progressBar";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractInputComponent.CAMELIA_ATTRIBUTES);
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

	public double getMinimum() {
		return getMinimum(null);
	}

	public double getMinimum(javax.faces.context.FacesContext facesContext) {
		return engine.getDoubleProperty(Properties.MINIMUM, 0.0, facesContext);
	}

	public void setMinimum(double minimum) {
		engine.setProperty(Properties.MINIMUM, minimum);
	}

	/**
	 * Returns <code>true</code> if the attribute "minimum" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isMinimumSetted() {
		return engine.isPropertySetted(Properties.MINIMUM);
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
		return engine.getDoubleProperty(Properties.MAXIMUM, 0.0, facesContext);
	}

	/**
	 * Sets an int value specifying the upper limit for the value of the component.
	 * @param maximum maximum
	 */
	public void setMaximum(double maximum) {
		engine.setProperty(Properties.MAXIMUM, maximum);
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
		return engine.isPropertySetted(Properties.MAXIMUM);
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
		return engine.getBoolProperty(Properties.INDETERMINATE, false, facesContext);
	}

	/**
	 * Sets a boolean value indicating wether the value is not determinated.
	 * @param indeterminate true if not determinated
	 */
	public void setIndeterminate(boolean indeterminate) {
		engine.setProperty(Properties.INDETERMINATE, indeterminate);
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
		return engine.isPropertySetted(Properties.INDETERMINATE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
