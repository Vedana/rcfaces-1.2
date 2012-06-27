package org.rcfaces.core.component;

import org.rcfaces.core.component.TextEntryComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;

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
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/SpinnerComponent.html">spinner</a> renderer is linked to the <a href="/jsdocs/index.html?f_spinner.html" target="_blank">f_spinner</a> javascript class. f_spinner extends f_textEntry, fa_spinner</p>
 * 
 * <p> Table of component style classes: </p>
 * <table border="1" cellpadding="3" cellspacing="0" width="100%">
 * <tbody>
 * 
 * <tr style="text-align:left">
 * <th  width="33%">Style Name</th>
 * <th width="50%">Description</th>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td width="33%">f_spinner</td>
 * <td width="50%">Defines styles for the wrapper element</td>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td width="33%">f_spinner_input</td>
 * <td width="50%">Defines styles for the INPUT element</td>
 * </tr>
 * <tr  style="text-align:left">
 * <td width="33%">f_spinner_up</td>
 * <td width="50%">Defines styles for the IMG element of the up button</td>
 * </tr>
 * <tr  style="text-align:left">
 * <td width="33%">f_spinner_down</td>
 * <td width="50%">Defines styles for the IMG elemen of the down button</td>
 * </tr>
 * 
 * </tbody>
 * </table>
 */
public class SpinnerComponent extends TextEntryComponent {

	private static final Log LOG = LogFactory.getLog(SpinnerComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.spinner";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(TextEntryComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"minimum","maximum","cycleValue","step"}));
	}

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
		return engine.getBoolProperty(Properties.CYCLE_VALUE, false, facesContext);
	}

	/**
	 * Sets a boolean value indicating wether to start from the beginning when reaching the end or not. And vice versa.
	 * @param cycleValue true if the component should cycle
	 */
	public void setCycleValue(boolean cycleValue) {
		engine.setProperty(Properties.CYCLE_VALUE, cycleValue);
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
		return engine.isPropertySetted(Properties.CYCLE_VALUE);
	}

	public String getStep() {
		return getStep(null);
	}

	public String getStep(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.STEP, facesContext);
	}

	public void setStep(String step) {
		engine.setProperty(Properties.STEP, step);
	}

	/**
	 * Returns <code>true</code> if the attribute "step" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isStepSetted() {
		return engine.isPropertySetted(Properties.STEP);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
