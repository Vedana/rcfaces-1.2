package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IForCapability;
import org.rcfaces.core.internal.component.Properties;

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
	IForCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.pager";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractBasicComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"manyResultsMessage","oneResultMessage","message","noPagedMessage","zeroResultMessage","for"}));
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

	/**
	 * See {@link #setFor(String) setFor(String)} for more details
	 */
	public void setFor(ValueBinding forValue) {
		engine.setProperty(Properties.FOR, forValue);
	}

	/**
	 * Returns a string value to be displayed by the component. It can embbed keywords (between braces : "{keyword}") :
	 * <ul><li>
	 * first: the index of the first row shown
	 * </li><li>
	 * position: the index of the first row shown
	 * </li><li>
	 * pageposition : the index of the page shown
	 * </li><li>
	 * last: a link to the last page
	 * </li><li>
	 * rowcount : the total number of rows
	 * </li><li>
	 * bfirst: a link to the first page
	 * </li><li>
	 * bnext: a link to the next page
	 * </li><li>
	 * blast: a link to the last page
	 * </li><li>
	 * bpages[:n] : a list of links to pages before and after the page shown (n specifies the number of indexes shown)
	 * </li><li>
	 * bprev: a link to the previous page
	 * </li></ul>
	 * ex: "{rowcount}' results, results from '{first}' to '{last}'.\n['{bfirst}'/'{bprev}'] '{bpages}' ['{bnext}'/'{blast}']'"
	 * @return message
	 */
	public final String getMessage() {
		return getMessage(null);
	}

	/**
	 * Returns a string value to be displayed by the component. It can embbed keywords (between braces : "{keyword}") :
	 * <ul><li>
	 * first: the index of the first row shown
	 * </li><li>
	 * position: the index of the first row shown
	 * </li><li>
	 * pageposition : the index of the page shown
	 * </li><li>
	 * last: a link to the last page
	 * </li><li>
	 * rowcount : the total number of rows
	 * </li><li>
	 * bfirst: a link to the first page
	 * </li><li>
	 * bnext: a link to the next page
	 * </li><li>
	 * blast: a link to the last page
	 * </li><li>
	 * bpages[:n] : a list of links to pages before and after the page shown (n specifies the number of indexes shown)
	 * </li><li>
	 * bprev: a link to the previous page
	 * </li></ul>
	 * ex: "{rowcount}' results, results from '{first}' to '{last}'.\n['{bfirst}'/'{bprev}'] '{bpages}' ['{bnext}'/'{blast}']'"
	 * @return message
	 */
	public final String getMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MESSAGE, facesContext);
	}

	/**
	 * Sets a string value to be displayed by the component. It can embbed keywords (between braces : "{keyword}") :
	 * <ul><li>
	 * first: the index of the first row shown
	 * </li><li>
	 * position: the index of the first row shown
	 * </li><li>
	 * pageposition : the index of the page shown
	 * </li><li>
	 * last: a link to the last page
	 * </li><li>
	 * rowcount : the total number of rows
	 * </li><li>
	 * bfirst: a link to the first page
	 * </li><li>
	 * bnext: a link to the next page
	 * </li><li>
	 * blast: a link to the last page
	 * </li><li>
	 * bpages[:n] : a list of links to pages before and after the page shown (n specifies the number of indexes shown)
	 * </li><li>
	 * bprev: a link to the previous page
	 * </li></ul>
	 * ex: "{rowcount}' results, results from '{first}' to '{last}'.\n['{bfirst}'/'{bprev}'] '{bpages}' ['{bnext}'/'{blast}']'"
	 * @param message message
	 */
	public final void setMessage(String message) {
		engine.setProperty(Properties.MESSAGE, message);
	}

	/**
	 * Sets a string value to be displayed by the component. It can embbed keywords (between braces : "{keyword}") :
	 * <ul><li>
	 * first: the index of the first row shown
	 * </li><li>
	 * position: the index of the first row shown
	 * </li><li>
	 * pageposition : the index of the page shown
	 * </li><li>
	 * last: a link to the last page
	 * </li><li>
	 * rowcount : the total number of rows
	 * </li><li>
	 * bfirst: a link to the first page
	 * </li><li>
	 * bnext: a link to the next page
	 * </li><li>
	 * blast: a link to the last page
	 * </li><li>
	 * bpages[:n] : a list of links to pages before and after the page shown (n specifies the number of indexes shown)
	 * </li><li>
	 * bprev: a link to the previous page
	 * </li></ul>
	 * ex: "{rowcount}' results, results from '{first}' to '{last}'.\n['{bfirst}'/'{bprev}'] '{bpages}' ['{bnext}'/'{blast}']'"
	 * @param message message
	 */
	public final void setMessage(ValueBinding message) {
		engine.setProperty(Properties.MESSAGE, message);
	}

	/**
	 * Returns <code>true</code> if the attribute "message" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMessageSetted() {
		return engine.isPropertySetted(Properties.MESSAGE);
	}

	/**
	 * Returns a string value to be displayed by the component when there's no result. It can embbed keywords (between braces : "{keyword}") :
	 * <ul><li>
	 * first: the index of the first row shown
	 * </li><li>
	 * position: the index of the first row shown
	 * </li><li>
	 * pageposition : the index of the page shown
	 * </li><li>
	 * last: a link to the last page
	 * </li><li>
	 * rowcount : the total number of rows
	 * </li><li>
	 * bfirst: a link to the first page
	 * </li><li>
	 * bnext: a link to the next page
	 * </li><li>
	 * blast: a link to the last page
	 * </li><li>
	 * bpages[:n] : a list of links to pages before and after the page shown (n specifies the number of indexes shown)
	 * </li><li>
	 * bprev: a link to the previous page
	 * </li></ul>
	 * ex: "No result.\nCome back later !"
	 * @return message
	 */
	public final String getZeroResultMessage() {
		return getZeroResultMessage(null);
	}

	/**
	 * Returns a string value to be displayed by the component when there's no result. It can embbed keywords (between braces : "{keyword}") :
	 * <ul><li>
	 * first: the index of the first row shown
	 * </li><li>
	 * position: the index of the first row shown
	 * </li><li>
	 * pageposition : the index of the page shown
	 * </li><li>
	 * last: a link to the last page
	 * </li><li>
	 * rowcount : the total number of rows
	 * </li><li>
	 * bfirst: a link to the first page
	 * </li><li>
	 * bnext: a link to the next page
	 * </li><li>
	 * blast: a link to the last page
	 * </li><li>
	 * bpages[:n] : a list of links to pages before and after the page shown (n specifies the number of indexes shown)
	 * </li><li>
	 * bprev: a link to the previous page
	 * </li></ul>
	 * ex: "No result.\nCome back later !"
	 * @return message
	 */
	public final String getZeroResultMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ZERO_RESULT_MESSAGE, facesContext);
	}

	/**
	 * Sets a string value to be displayed by the component when there's no result. It can embbed keywords (between braces : "{keyword}") :
	 * <ul><li>
	 * first: the index of the first row shown
	 * </li><li>
	 * position: the index of the first row shown
	 * </li><li>
	 * pageposition : the index of the page shown
	 * </li><li>
	 * last: a link to the last page
	 * </li><li>
	 * rowcount : the total number of rows
	 * </li><li>
	 * bfirst: a link to the first page
	 * </li><li>
	 * bnext: a link to the next page
	 * </li><li>
	 * blast: a link to the last page
	 * </li><li>
	 * bpages[:n] : a list of links to pages before and after the page shown (n specifies the number of indexes shown)
	 * </li><li>
	 * bprev: a link to the previous page
	 * </li></ul>
	 * ex: "No result.\nCome back later !"
	 * @param zeroResultMessage message
	 */
	public final void setZeroResultMessage(String zeroResultMessage) {
		engine.setProperty(Properties.ZERO_RESULT_MESSAGE, zeroResultMessage);
	}

	/**
	 * Sets a string value to be displayed by the component when there's no result. It can embbed keywords (between braces : "{keyword}") :
	 * <ul><li>
	 * first: the index of the first row shown
	 * </li><li>
	 * position: the index of the first row shown
	 * </li><li>
	 * pageposition : the index of the page shown
	 * </li><li>
	 * last: a link to the last page
	 * </li><li>
	 * rowcount : the total number of rows
	 * </li><li>
	 * bfirst: a link to the first page
	 * </li><li>
	 * bnext: a link to the next page
	 * </li><li>
	 * blast: a link to the last page
	 * </li><li>
	 * bpages[:n] : a list of links to pages before and after the page shown (n specifies the number of indexes shown)
	 * </li><li>
	 * bprev: a link to the previous page
	 * </li></ul>
	 * ex: "No result.\nCome back later !"
	 * @param zeroResultMessage message
	 */
	public final void setZeroResultMessage(ValueBinding zeroResultMessage) {
		engine.setProperty(Properties.ZERO_RESULT_MESSAGE, zeroResultMessage);
	}

	/**
	 * Returns <code>true</code> if the attribute "zeroResultMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isZeroResultMessageSetted() {
		return engine.isPropertySetted(Properties.ZERO_RESULT_MESSAGE);
	}

	/**
	 * Returns a string value to be displayed by the component when there's only one result. It can embbed keywords (between braces : "{keyword}") :
	 * <ul><li>
	 * first: the index of the first row shown
	 * </li><li>
	 * position: the index of the first row shown
	 * </li><li>
	 * pageposition : the index of the page shown
	 * </li><li>
	 * last: a link to the last page
	 * </li><li>
	 * rowcount : the total number of rows
	 * </li><li>
	 * bfirst: a link to the first page
	 * </li><li>
	 * bnext: a link to the next page
	 * </li><li>
	 * blast: a link to the last page
	 * </li><li>
	 * bpages[:n] : a list of links to pages before and after the page shown (n specifies the number of indexes shown)
	 * </li><li>
	 * bprev: a link to the previous page
	 * </li></ul>
	 * ex: "One result.\nCome back later !"
	 * ex: "{rowcount}' results, results from '{first}' to '{last}'.\n['{bfirst}'/'{bprev}'] '{bpages}' ['{bnext}'/'{blast}']'"
	 * @return message
	 */
	public final String getOneResultMessage() {
		return getOneResultMessage(null);
	}

	/**
	 * Returns a string value to be displayed by the component when there's only one result. It can embbed keywords (between braces : "{keyword}") :
	 * <ul><li>
	 * first: the index of the first row shown
	 * </li><li>
	 * position: the index of the first row shown
	 * </li><li>
	 * pageposition : the index of the page shown
	 * </li><li>
	 * last: a link to the last page
	 * </li><li>
	 * rowcount : the total number of rows
	 * </li><li>
	 * bfirst: a link to the first page
	 * </li><li>
	 * bnext: a link to the next page
	 * </li><li>
	 * blast: a link to the last page
	 * </li><li>
	 * bpages[:n] : a list of links to pages before and after the page shown (n specifies the number of indexes shown)
	 * </li><li>
	 * bprev: a link to the previous page
	 * </li></ul>
	 * ex: "One result.\nCome back later !"
	 * ex: "{rowcount}' results, results from '{first}' to '{last}'.\n['{bfirst}'/'{bprev}'] '{bpages}' ['{bnext}'/'{blast}']'"
	 * @return message
	 */
	public final String getOneResultMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ONE_RESULT_MESSAGE, facesContext);
	}

	/**
	 * Sets a string value to be displayed by the component when there's only one result. It can embbed keywords (between braces : "{keyword}") :
	 * <ul><li>
	 * first: the index of the first row shown
	 * </li><li>
	 * position: the index of the first row shown
	 * </li><li>
	 * pageposition : the index of the page shown
	 * </li><li>
	 * last: a link to the last page
	 * </li><li>
	 * rowcount : the total number of rows
	 * </li><li>
	 * bfirst: a link to the first page
	 * </li><li>
	 * bnext: a link to the next page
	 * </li><li>
	 * blast: a link to the last page
	 * </li><li>
	 * bpages[:n] : a list of links to pages before and after the page shown (n specifies the number of indexes shown)
	 * </li><li>
	 * bprev: a link to the previous page
	 * </li></ul>
	 * ex: "One result.\nCome back later !"
	 * ex: "{rowcount}' results, results from '{first}' to '{last}'.\n['{bfirst}'/'{bprev}'] '{bpages}' ['{bnext}'/'{blast}']'"
	 * @param oneResultMessage message
	 */
	public final void setOneResultMessage(String oneResultMessage) {
		engine.setProperty(Properties.ONE_RESULT_MESSAGE, oneResultMessage);
	}

	/**
	 * Sets a string value to be displayed by the component when there's only one result. It can embbed keywords (between braces : "{keyword}") :
	 * <ul><li>
	 * first: the index of the first row shown
	 * </li><li>
	 * position: the index of the first row shown
	 * </li><li>
	 * pageposition : the index of the page shown
	 * </li><li>
	 * last: a link to the last page
	 * </li><li>
	 * rowcount : the total number of rows
	 * </li><li>
	 * bfirst: a link to the first page
	 * </li><li>
	 * bnext: a link to the next page
	 * </li><li>
	 * blast: a link to the last page
	 * </li><li>
	 * bpages[:n] : a list of links to pages before and after the page shown (n specifies the number of indexes shown)
	 * </li><li>
	 * bprev: a link to the previous page
	 * </li></ul>
	 * ex: "One result.\nCome back later !"
	 * ex: "{rowcount}' results, results from '{first}' to '{last}'.\n['{bfirst}'/'{bprev}'] '{bpages}' ['{bnext}'/'{blast}']'"
	 * @param oneResultMessage message
	 */
	public final void setOneResultMessage(ValueBinding oneResultMessage) {
		engine.setProperty(Properties.ONE_RESULT_MESSAGE, oneResultMessage);
	}

	/**
	 * Returns <code>true</code> if the attribute "oneResultMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isOneResultMessageSetted() {
		return engine.isPropertySetted(Properties.ONE_RESULT_MESSAGE);
	}

	/**
	 * Returns a string value to be displayed by the component. It can embbed keywords (between braces : "{keyword}") :
	 * <ul><li>
	 * first: the index of the first row shown
	 * </li><li>
	 * position: the index of the first row shown
	 * </li><li>
	 * pageposition : the index of the page shown
	 * </li><li>
	 * last: a link to the last page
	 * </li><li>
	 * rowcount : the total number of rows
	 * </li><li>
	 * bfirst: a link to the first page
	 * </li><li>
	 * bnext: a link to the next page
	 * </li><li>
	 * blast: a link to the last page
	 * </li><li>
	 * bpages[:n] : a list of links to pages before and after the page shown (n specifies the number of indexes shown)
	 * </li><li>
	 * bprev: a link to the previous page
	 * </li></ul>
	 * @return message
	 */
	public final String getManyResultsMessage() {
		return getManyResultsMessage(null);
	}

	/**
	 * Returns a string value to be displayed by the component. It can embbed keywords (between braces : "{keyword}") :
	 * <ul><li>
	 * first: the index of the first row shown
	 * </li><li>
	 * position: the index of the first row shown
	 * </li><li>
	 * pageposition : the index of the page shown
	 * </li><li>
	 * last: a link to the last page
	 * </li><li>
	 * rowcount : the total number of rows
	 * </li><li>
	 * bfirst: a link to the first page
	 * </li><li>
	 * bnext: a link to the next page
	 * </li><li>
	 * blast: a link to the last page
	 * </li><li>
	 * bpages[:n] : a list of links to pages before and after the page shown (n specifies the number of indexes shown)
	 * </li><li>
	 * bprev: a link to the previous page
	 * </li></ul>
	 * @return message
	 */
	public final String getManyResultsMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MANY_RESULTS_MESSAGE, facesContext);
	}

	/**
	 * Sets a string value to be displayed by the component. It can embbed keywords (between braces : "{keyword}") :
	 * <ul><li>
	 * first: the index of the first row shown
	 * </li><li>
	 * position: the index of the first row shown
	 * </li><li>
	 * pageposition : the index of the page shown
	 * </li><li>
	 * last: a link to the last page
	 * </li><li>
	 * rowcount : the total number of rows
	 * </li><li>
	 * bfirst: a link to the first page
	 * </li><li>
	 * bnext: a link to the next page
	 * </li><li>
	 * blast: a link to the last page
	 * </li><li>
	 * bpages[:n] : a list of links to pages before and after the page shown (n specifies the number of indexes shown)
	 * </li><li>
	 * bprev: a link to the previous page
	 * </li></ul>
	 * @param manyResultsMessage message
	 */
	public final void setManyResultsMessage(String manyResultsMessage) {
		engine.setProperty(Properties.MANY_RESULTS_MESSAGE, manyResultsMessage);
	}

	/**
	 * Sets a string value to be displayed by the component. It can embbed keywords (between braces : "{keyword}") :
	 * <ul><li>
	 * first: the index of the first row shown
	 * </li><li>
	 * position: the index of the first row shown
	 * </li><li>
	 * pageposition : the index of the page shown
	 * </li><li>
	 * last: a link to the last page
	 * </li><li>
	 * rowcount : the total number of rows
	 * </li><li>
	 * bfirst: a link to the first page
	 * </li><li>
	 * bnext: a link to the next page
	 * </li><li>
	 * blast: a link to the last page
	 * </li><li>
	 * bpages[:n] : a list of links to pages before and after the page shown (n specifies the number of indexes shown)
	 * </li><li>
	 * bprev: a link to the previous page
	 * </li></ul>
	 * @param manyResultsMessage message
	 */
	public final void setManyResultsMessage(ValueBinding manyResultsMessage) {
		engine.setProperty(Properties.MANY_RESULTS_MESSAGE, manyResultsMessage);
	}

	/**
	 * Returns <code>true</code> if the attribute "manyResultsMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isManyResultsMessageSetted() {
		return engine.isPropertySetted(Properties.MANY_RESULTS_MESSAGE);
	}

	/**
	 * Returns a string specifying the message to display when there is no row to display.
	 * @return message
	 */
	public final String getNoPagedMessage() {
		return getNoPagedMessage(null);
	}

	/**
	 * Returns a string specifying the message to display when there is no row to display.
	 * @return message
	 */
	public final String getNoPagedMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.NO_PAGED_MESSAGE, facesContext);
	}

	/**
	 * Steps a string specifying the message to display when there is no row to display.
	 * @param noPagedMessage message
	 */
	public final void setNoPagedMessage(String noPagedMessage) {
		engine.setProperty(Properties.NO_PAGED_MESSAGE, noPagedMessage);
	}

	/**
	 * Steps a string specifying the message to display when there is no row to display.
	 * @param noPagedMessage message
	 */
	public final void setNoPagedMessage(ValueBinding noPagedMessage) {
		engine.setProperty(Properties.NO_PAGED_MESSAGE, noPagedMessage);
	}

	/**
	 * Returns <code>true</code> if the attribute "noPagedMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isNoPagedMessageSetted() {
		return engine.isPropertySetted(Properties.NO_PAGED_MESSAGE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
