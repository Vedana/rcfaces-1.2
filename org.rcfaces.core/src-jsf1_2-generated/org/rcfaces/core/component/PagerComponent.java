package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.capability.IForCapability;
import org.rcfaces.core.component.AbstractBasicComponent;
import org.rcfaces.core.component.capability.IPagerMessageCapability;

/**
 * <p>The pager Component is linked to the <a href="/comps/dataGridComponent.html">dataGrid Component</a>.
 * It shows informations about the result set (ex: number of available pages)
 * and can give direct access to a specific page ("a la Google").</p>
 * <p>The pager Component has the following capabilities :
 * <ul>
 * <li>IRequiredCapability</li>
 * <li>IAutoTabCapability</li>
 * <li>IValueChangeEventCapability</li>
 * <li>IFocusStyleClassCapability</li>
 * <li>ISelectionEventCapability</li>
 * <li>IReadOnlyCapability</li>
 * <li>INumberFormatTypeCapability</li>
 * <li>ILiteralLocaleCapability</li>
 * <li>IComponentLocaleCapability</li>
 * <li>ISeverityStyleClassCapability</li>
 * <li>IAlternateTextCapability</li>
 * <li>IValidationParameters</li>
 * </ul>
 * </p>
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/PagerComponent.html">pager</a> renderer is linked to the <a href="/jsdoc/index.html?f_pager.html">f_pager</a> javascript class. f_pager extends f_component, fa_pager</p>
 * 
 * <p> Table of component style classes: </p>
 * <table border="1" cellpadding="3" cellspacing="0" width="100%">
 * <tbody>
 * 
 * <tr style="text-align:left">
 * <td bgcolor="#eeeeee"  width="33%">Style Name</td>
 * <td bgcolor="#eeeeee" " width="50%">Description</td>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td bgcolor="#ffffff" width="33%">f_pager</td>
 * <td width="50%">Defines styles for the wrapper DIV element</td>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td bgcolor="#ffffff" width="33%">f_pager_button_first</td>
 * <td width="50%">Defines styles for the wrapper SPAN element</td>
 * </tr>
 * <tr  style="text-align:left">
 * <td bgcolor="#ffffff" width="33%">f_pager_button_next</td>
 * <td width="50%">Defines styles for the wrapper SPAN element</td>
 * </tr>
 * <tr  style="text-align:left">
 * <td bgcolor="#ffffff" width="33%">f_pager_button_last</td>
 * <td width="50%">Defines styles for the wrapper SPAN element</td>
 * </tr>
 * <tr  style="text-align:left">
 * <td bgcolor="#ffffff" width="33%">f_pager_button_prev</td>
 * <td width="50%">Defines styles for the wrapper SPAN element</td>
 * </tr>
 * 
 * </tbody>
 * </table>
 */
public class PagerComponent extends AbstractBasicComponent implements 
	IForCapability,
	IPagerMessageCapability {

	private static final Log LOG = LogFactory.getLog(PagerComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.pager";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractBasicComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"message","oneResultMessage","for","noPagedMessage","manyResultsMessage","zeroResultMessage"}));
	}

	public PagerComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public PagerComponent(String componentId) {
		this();
		setId(componentId);
	}

	public java.lang.String getFor() {
		return getFor(null);
	}

	/**
	 * See {@link #getFor() getFor()} for more details
	 */
	public java.lang.String getFor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOR, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "for" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isForSetted() {
		return engine.isPropertySetted(Properties.FOR);
	}

	public void setFor(java.lang.String forValue) {
		engine.setProperty(Properties.FOR, forValue);
	}

	public java.lang.String getManyResultsMessage() {
		return getManyResultsMessage(null);
	}

	/**
	 * See {@link #getManyResultsMessage() getManyResultsMessage()} for more details
	 */
	public java.lang.String getManyResultsMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MANY_RESULTS_MESSAGE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "manyResultsMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isManyResultsMessageSetted() {
		return engine.isPropertySetted(Properties.MANY_RESULTS_MESSAGE);
	}

	public void setManyResultsMessage(java.lang.String manyResultsMessage) {
		engine.setProperty(Properties.MANY_RESULTS_MESSAGE, manyResultsMessage);
	}

	public java.lang.String getMessage() {
		return getMessage(null);
	}

	/**
	 * See {@link #getMessage() getMessage()} for more details
	 */
	public java.lang.String getMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MESSAGE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "message" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMessageSetted() {
		return engine.isPropertySetted(Properties.MESSAGE);
	}

	public void setMessage(java.lang.String message) {
		engine.setProperty(Properties.MESSAGE, message);
	}

	public java.lang.String getOneResultMessage() {
		return getOneResultMessage(null);
	}

	/**
	 * See {@link #getOneResultMessage() getOneResultMessage()} for more details
	 */
	public java.lang.String getOneResultMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ONE_RESULT_MESSAGE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "oneResultMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isOneResultMessageSetted() {
		return engine.isPropertySetted(Properties.ONE_RESULT_MESSAGE);
	}

	public void setOneResultMessage(java.lang.String oneResultMessage) {
		engine.setProperty(Properties.ONE_RESULT_MESSAGE, oneResultMessage);
	}

	public java.lang.String getZeroResultMessage() {
		return getZeroResultMessage(null);
	}

	/**
	 * See {@link #getZeroResultMessage() getZeroResultMessage()} for more details
	 */
	public java.lang.String getZeroResultMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ZERO_RESULT_MESSAGE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "zeroResultMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isZeroResultMessageSetted() {
		return engine.isPropertySetted(Properties.ZERO_RESULT_MESSAGE);
	}

	public void setZeroResultMessage(java.lang.String zeroResultMessage) {
		engine.setProperty(Properties.ZERO_RESULT_MESSAGE, zeroResultMessage);
	}

	/**
	 * Returns a string specifying the message to display when there is no row to display.
	 * @return message
	 */
	public String getNoPagedMessage() {
		return getNoPagedMessage(null);
	}

	/**
	 * Returns a string specifying the message to display when there is no row to display.
	 * @return message
	 */
	public String getNoPagedMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.NO_PAGED_MESSAGE, facesContext);
	}

	/**
	 * Steps a string specifying the message to display when there is no row to display.
	 * @param noPagedMessage message
	 */
	public void setNoPagedMessage(String noPagedMessage) {
		engine.setProperty(Properties.NO_PAGED_MESSAGE, noPagedMessage);
	}

	/**
	 * Steps a string specifying the message to display when there is no row to display.
	 * @param noPagedMessage message
	 */
	/**
	 * Returns <code>true</code> if the attribute "noPagedMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isNoPagedMessageSetted() {
		return engine.isPropertySetted(Properties.NO_PAGED_MESSAGE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
