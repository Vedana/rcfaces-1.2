package org.rcfaces.renderkit.svg.component;

import org.rcfaces.core.component.capability.IFocusBlurEventCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.capability.IUnlockedClientAttributesCapability;
import org.rcfaces.renderkit.svg.component.Properties;
import java.lang.String;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IAccessKeyCapability;
import org.rcfaces.core.component.capability.ITabIndexCapability;
import org.rcfaces.core.component.capability.IKeyEventCapability;
import org.rcfaces.core.component.capability.IValidationEventCapability;
import org.rcfaces.core.component.capability.IImmediateCapability;
import org.rcfaces.core.component.capability.IFocusStyleClassCapability;
import org.rcfaces.core.internal.converter.FilterPropertiesConverter;
import javax.el.ValueExpression;
import org.rcfaces.core.component.capability.IDisabledCapability;
import org.rcfaces.core.component.capability.IFilterCapability;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.AbstractOutputComponent;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.core.component.capability.ICaptionCapability;
import java.util.Set;
import java.util.Arrays;

public class SVGComponent extends AbstractOutputComponent implements 
	IDisabledCapability,
	IAccessKeyCapability,
	ITabIndexCapability,
	IFocusBlurEventCapability,
	IKeyEventCapability,
	IUnlockedClientAttributesCapability,
	IImmediateCapability,
	IValidationEventCapability,
	ISelectionEventCapability,
	IFocusStyleClassCapability,
	ICaptionCapability,
	IFilterCapability {

	private static final Log LOG = LogFactory.getLog(SVGComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.svg.SVG";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractOutputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"focusStyleClass","filterProperties","accessKey","blurListener","svgURL","tabIndex","keyUpListener","focusListener","caption","keyPressListener","unlockedClientAttributeNames","immediate","selectionListener","keyDownListener","disabled","validationListener"}));
	}

	public SVGComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public SVGComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void setFilterProperties(String properties) {


			IFilterProperties filterProperties=(IFilterProperties)FilterPropertiesConverter.SINGLETON.getAsObject(null, this, properties);
			
			setFilterProperties(filterProperties);
		
	}

	public boolean isDisabled() {
		return isDisabled(null);
	}

	/**
	 * See {@link #isDisabled() isDisabled()} for more details
	 */
	public boolean isDisabled(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.DISABLED, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "disabled" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDisabledSetted() {
		return engine.isPropertySetted(Properties.DISABLED);
	}

	public void setDisabled(boolean disabled) {
		engine.setProperty(Properties.DISABLED, disabled);
	}

	public java.lang.String getAccessKey() {
		return getAccessKey(null);
	}

	/**
	 * See {@link #getAccessKey() getAccessKey()} for more details
	 */
	public java.lang.String getAccessKey(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ACCESS_KEY, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "accessKey" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAccessKeySetted() {
		return engine.isPropertySetted(Properties.ACCESS_KEY);
	}

	public void setAccessKey(java.lang.String accessKey) {
		engine.setProperty(Properties.ACCESS_KEY, accessKey);
	}

	public java.lang.Integer getTabIndex() {
		return getTabIndex(null);
	}

	/**
	 * See {@link #getTabIndex() getTabIndex()} for more details
	 */
	public java.lang.Integer getTabIndex(javax.faces.context.FacesContext facesContext) {
		return engine.getIntegerProperty(Properties.TAB_INDEX, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "tabIndex" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTabIndexSetted() {
		return engine.isPropertySetted(Properties.TAB_INDEX);
	}

	public void setTabIndex(java.lang.Integer tabIndex) {
		engine.setProperty(Properties.TAB_INDEX, tabIndex);
	}

	public final void addBlurListener(org.rcfaces.core.event.IBlurListener listener) {
		addFacesListener(listener);
	}

	public final void removeBlurListener(org.rcfaces.core.event.IBlurListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listBlurListeners() {
		return getFacesListeners(org.rcfaces.core.event.IBlurListener.class);
	}

	public final void addFocusListener(org.rcfaces.core.event.IFocusListener listener) {
		addFacesListener(listener);
	}

	public final void removeFocusListener(org.rcfaces.core.event.IFocusListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listFocusListeners() {
		return getFacesListeners(org.rcfaces.core.event.IFocusListener.class);
	}

	public final void addKeyUpListener(org.rcfaces.core.event.IKeyUpListener listener) {
		addFacesListener(listener);
	}

	public final void removeKeyUpListener(org.rcfaces.core.event.IKeyUpListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listKeyUpListeners() {
		return getFacesListeners(org.rcfaces.core.event.IKeyUpListener.class);
	}

	public final void addKeyDownListener(org.rcfaces.core.event.IKeyDownListener listener) {
		addFacesListener(listener);
	}

	public final void removeKeyDownListener(org.rcfaces.core.event.IKeyDownListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listKeyDownListeners() {
		return getFacesListeners(org.rcfaces.core.event.IKeyDownListener.class);
	}

	public final void addKeyPressListener(org.rcfaces.core.event.IKeyPressListener listener) {
		addFacesListener(listener);
	}

	public final void removeKeyPressListener(org.rcfaces.core.event.IKeyPressListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listKeyPressListeners() {
		return getFacesListeners(org.rcfaces.core.event.IKeyPressListener.class);
	}

	public final void addValidationListener(org.rcfaces.core.event.IValidationListener listener) {
		addFacesListener(listener);
	}

	public final void removeValidationListener(org.rcfaces.core.event.IValidationListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listValidationListeners() {
		return getFacesListeners(org.rcfaces.core.event.IValidationListener.class);
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

	public java.lang.String getCaption() {
		return getCaption(null);
	}

	/**
	 * See {@link #getCaption() getCaption()} for more details
	 */
	public java.lang.String getCaption(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.CAPTION, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "caption" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCaptionSetted() {
		return engine.isPropertySetted(Properties.CAPTION);
	}

	public void setCaption(java.lang.String caption) {
		engine.setProperty(Properties.CAPTION, caption);
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

	public boolean isImmediate() {
		return isImmediate(null);
	}

	public boolean isImmediate(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.IMMEDIATE, false, facesContext);
	}

	public void setImmediate(boolean immediate) {
		engine.setProperty(Properties.IMMEDIATE, immediate);
	}

	/**
	 * Returns <code>true</code> if the attribute "immediate" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isImmediateSetted() {
		return engine.isPropertySetted(Properties.IMMEDIATE);
	}

	public String getSvgURL() {
		return getSvgURL(null);
	}

	public String getSvgURL(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.SVG_URL, facesContext);
		return s;
	}

	public void setSvgURL(String svgURL) {
		engine.setProperty(Properties.SVG_URL, svgURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "svgURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSvgURLSetted() {
		return engine.isPropertySetted(Properties.SVG_URL);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
