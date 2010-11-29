package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.internal.component.Properties;
import java.lang.String;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IRequiredCapability;
import org.rcfaces.core.component.capability.IFocusStyleClassCapability;
import org.rcfaces.core.internal.converter.FilterPropertiesConverter;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.model.IFilterProperties;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.component.AbstractInputComponent;
import org.rcfaces.core.component.capability.ISeverityStyleClassCapability;

/**
 * <p>The combo Component is equivalent to the standard HTML tag &lt;SELECT SIZE=1&gt;.</p>
 * <p>The combo Component has the following capabilities :
 * <ul>
 * <li>ISelectionEventCapability</li>
 * <li>IRequiredCapability</li>
 * <li>IFocusStyleClassCapability</li>
 * <li>ISeverityStyleClassCapability</li>
 * <li>IFilterCapability </li>
 * </ul>
 * </p>
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/ComboComponent.html">comboComponent</a> renderer is linked to the <a href="/jsdoc/index.html?f_combo.html" target="_blank">f_combo</a> javascript class. f_combo extends f_abstractList, fa_selectionProvider</p>
 * 
 * <p> Table of component style classes: </p>
 * <table border="1" cellpadding="3" cellspacing="0" width="100%">
 * <tbody>
 * 
 * <tr style="text-align:left">
 * <td bgcolor="#eeeeee"  width="33%">Style Name</td>
 * <td bgcolor="#eeeeee"  width="50%">Description</td>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td bgcolor="#ffffff" width="33%">f_combo</td>
 * <td width="50%">Defines styles for the wrapper Select element</td>
 * </tr>
 * 
 * </tbody>
 * </table>
 */
public class ComboComponent extends AbstractInputComponent implements 
	ISelectionEventCapability,
	IRequiredCapability,
	IFocusStyleClassCapability,
	ISeverityStyleClassCapability,
	IFilterCapability {

	private static final Log LOG = LogFactory.getLog(ComboComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.combo";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractInputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"focusStyleClass","selectionListener","filterProperties","fatalStyleClass","warnStyleClass","errorStyleClass","infoStyleClass","styleClass","required"}));
	}
	protected static final String CAMELIA_VALUE_ALIAS="value";

	public ComboComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ComboComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void setFilterProperties(String properties) {


			IFilterProperties filterProperties=(IFilterProperties)FilterPropertiesConverter.SINGLETON.getAsObject(null, this, properties);
			
			setFilterProperties(filterProperties);
		
	}

	public final void addSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		addFacesListener(listener);
	}

	public final void removeSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listSelectionListeners() {
		return getFacesListeners(org.rcfaces.core.event.ISelectionListener.class);
	}

	public java.lang.String getFocusStyleClass() {
		return getFocusStyleClass(null);
	}

	/**
	 * See {@link #getFocusStyleClass() getFocusStyleClass()} for more details
	 */
	public java.lang.String getFocusStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOCUS_STYLE_CLASS, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "focusStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFocusStyleClassSetted() {
		return engine.isPropertySetted(Properties.FOCUS_STYLE_CLASS);
	}

	public void setFocusStyleClass(java.lang.String focusStyleClass) {
		engine.setProperty(Properties.FOCUS_STYLE_CLASS, focusStyleClass);
	}

	public java.lang.String getErrorStyleClass() {
		return getErrorStyleClass(null);
	}

	/**
	 * See {@link #getErrorStyleClass() getErrorStyleClass()} for more details
	 */
	public java.lang.String getErrorStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ERROR_STYLE_CLASS, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "errorStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isErrorStyleClassSetted() {
		return engine.isPropertySetted(Properties.ERROR_STYLE_CLASS);
	}

	public void setErrorStyleClass(java.lang.String errorStyleClass) {
		engine.setProperty(Properties.ERROR_STYLE_CLASS, errorStyleClass);
	}

	public java.lang.String getFatalStyleClass() {
		return getFatalStyleClass(null);
	}

	/**
	 * See {@link #getFatalStyleClass() getFatalStyleClass()} for more details
	 */
	public java.lang.String getFatalStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FATAL_STYLE_CLASS, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "fatalStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFatalStyleClassSetted() {
		return engine.isPropertySetted(Properties.FATAL_STYLE_CLASS);
	}

	public void setFatalStyleClass(java.lang.String fatalStyleClass) {
		engine.setProperty(Properties.FATAL_STYLE_CLASS, fatalStyleClass);
	}

	public java.lang.String getInfoStyleClass() {
		return getInfoStyleClass(null);
	}

	/**
	 * See {@link #getInfoStyleClass() getInfoStyleClass()} for more details
	 */
	public java.lang.String getInfoStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.INFO_STYLE_CLASS, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "infoStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isInfoStyleClassSetted() {
		return engine.isPropertySetted(Properties.INFO_STYLE_CLASS);
	}

	public void setInfoStyleClass(java.lang.String infoStyleClass) {
		engine.setProperty(Properties.INFO_STYLE_CLASS, infoStyleClass);
	}

	public java.lang.String getWarnStyleClass() {
		return getWarnStyleClass(null);
	}

	/**
	 * See {@link #getWarnStyleClass() getWarnStyleClass()} for more details
	 */
	public java.lang.String getWarnStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.WARN_STYLE_CLASS, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "warnStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isWarnStyleClassSetted() {
		return engine.isPropertySetted(Properties.WARN_STYLE_CLASS);
	}

	public void setWarnStyleClass(java.lang.String warnStyleClass) {
		engine.setProperty(Properties.WARN_STYLE_CLASS, warnStyleClass);
	}

	public org.rcfaces.core.model.IFilterProperties getFilterProperties() {
		return getFilterProperties(null);
	}

	/**
	 * See {@link #getFilterProperties() getFilterProperties()} for more details
	 */
	public org.rcfaces.core.model.IFilterProperties getFilterProperties(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.model.IFilterProperties)engine.getProperty(Properties.FILTER_PROPERTIES, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "filterProperties" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFilterPropertiesSetted() {
		return engine.isPropertySetted(Properties.FILTER_PROPERTIES);
	}

	public void setFilterProperties(org.rcfaces.core.model.IFilterProperties filterProperties) {
		engine.setProperty(Properties.FILTER_PROPERTIES, filterProperties);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}

	protected String getCameliaValueAlias() {
		return CAMELIA_VALUE_ALIAS;
	}
}
