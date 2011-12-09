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
import java.util.Collection;
import org.rcfaces.core.component.capability.IPagerMessageCapability;

/**
 * <p>The pager Component is linked to the <a href="/comps/dataGridComponent.html">dataGrid Component</a>.
 * It shows informations about the result set (ex: number of available pages)
 * and can give direct access to a specific page ("a la Google").</p>
 * <p>The pager Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text &amp; font</li>
 * <li>Help</li>
 * <li>Visibility, Read-Only, Disabled</li>
 * <li>Events Handling</li>
 * <li>Association with another component</li>
 * </ul>
 * </p>
 */
public class PagerComponent extends AbstractBasicComponent implements 
	IForCapability,
	IPagerMessageCapability {

	private static final Log LOG = LogFactory.getLog(PagerComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.pager";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractBasicComponent.BEHAVIOR_EVENT_NAMES;

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
		return (String)getStateHelper().eval(Properties.FOR);
	}

	/**
	 * Returns <code>true</code> if the attribute "for" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isForSetted() {
		return getStateHelper().get(Properties.FOR)!=null;
	}

	public void setFor(java.lang.String forValue) {
		getStateHelper().put(Properties.FOR, forValue);
	}

	public java.lang.String getManyResultsMessage() {
		return getManyResultsMessage(null);
	}

	/**
	 * See {@link #getManyResultsMessage() getManyResultsMessage()} for more details
	 */
	public java.lang.String getManyResultsMessage(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.MANY_RESULTS_MESSAGE);
	}

	/**
	 * Returns <code>true</code> if the attribute "manyResultsMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isManyResultsMessageSetted() {
		return getStateHelper().get(Properties.MANY_RESULTS_MESSAGE)!=null;
	}

	public void setManyResultsMessage(java.lang.String manyResultsMessage) {
		getStateHelper().put(Properties.MANY_RESULTS_MESSAGE, manyResultsMessage);
	}

	public java.lang.String getMessage() {
		return getMessage(null);
	}

	/**
	 * See {@link #getMessage() getMessage()} for more details
	 */
	public java.lang.String getMessage(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.MESSAGE);
	}

	/**
	 * Returns <code>true</code> if the attribute "message" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMessageSetted() {
		return getStateHelper().get(Properties.MESSAGE)!=null;
	}

	public void setMessage(java.lang.String message) {
		getStateHelper().put(Properties.MESSAGE, message);
	}

	public java.lang.String getOneResultMessage() {
		return getOneResultMessage(null);
	}

	/**
	 * See {@link #getOneResultMessage() getOneResultMessage()} for more details
	 */
	public java.lang.String getOneResultMessage(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ONE_RESULT_MESSAGE);
	}

	/**
	 * Returns <code>true</code> if the attribute "oneResultMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isOneResultMessageSetted() {
		return getStateHelper().get(Properties.ONE_RESULT_MESSAGE)!=null;
	}

	public void setOneResultMessage(java.lang.String oneResultMessage) {
		getStateHelper().put(Properties.ONE_RESULT_MESSAGE, oneResultMessage);
	}

	public java.lang.String getZeroResultMessage() {
		return getZeroResultMessage(null);
	}

	/**
	 * See {@link #getZeroResultMessage() getZeroResultMessage()} for more details
	 */
	public java.lang.String getZeroResultMessage(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ZERO_RESULT_MESSAGE);
	}

	/**
	 * Returns <code>true</code> if the attribute "zeroResultMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isZeroResultMessageSetted() {
		return getStateHelper().get(Properties.ZERO_RESULT_MESSAGE)!=null;
	}

	public void setZeroResultMessage(java.lang.String zeroResultMessage) {
		getStateHelper().put(Properties.ZERO_RESULT_MESSAGE, zeroResultMessage);
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
		return (String)getStateHelper().eval(Properties.NO_PAGED_MESSAGE);
	}

	/**
	 * Steps a string specifying the message to display when there is no row to display.
	 * @param noPagedMessage message
	 */
	public void setNoPagedMessage(String noPagedMessage) {
		 getStateHelper().put(Properties.NO_PAGED_MESSAGE, noPagedMessage);
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
		return getStateHelper().get(Properties.NO_PAGED_MESSAGE)!=null;
	}

}
