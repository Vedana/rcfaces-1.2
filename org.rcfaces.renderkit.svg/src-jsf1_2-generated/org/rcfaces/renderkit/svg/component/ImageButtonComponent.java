package org.rcfaces.renderkit.svg.component;

import org.rcfaces.core.component.capability.IFocusBlurEventCapability;
import org.rcfaces.renderkit.svg.component.ImageComponent;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.capability.IUnlockedClientAttributesCapability;
import org.rcfaces.renderkit.svg.component.Properties;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IAccessKeyCapability;
import org.rcfaces.core.component.capability.ITabIndexCapability;
import org.rcfaces.core.component.capability.IKeyEventCapability;
import org.rcfaces.core.component.capability.IValidationEventCapability;
import org.rcfaces.core.component.capability.IImmediateCapability;
import org.rcfaces.core.component.capability.IFocusStyleClassCapability;
import javax.el.ValueExpression;
import org.rcfaces.core.component.capability.IDisabledCapability;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import java.util.Set;
import java.util.Arrays;

/**
 * <p>The imageButton Component is a <a href="/comps/buttonComponent.html">button Component</a> that can show an image.</p>
 * <p>The imageButton Component has the following capabilities :
 * <ul>
 * <li>IImageButtonFamilly </li>
 * <li>IImageAccessorsCapability </li>
 * </ul>
 * </p>
 * 
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/ImageButtonComponent.html">imageButton</a> renderer is linked to the <a href="/jsdocs/index.html?f_imageButton.html" target="_blank">f_imageButton</a> javascript class. f_imageButton extends f_component, fa_readOnly, fa_disabled, fa_tabIndex, fa_borderType, fa_images, fa_immediate, fa_value, fa_aria</p>
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
 * <td width="33%">f_imageButton</td>
 * <td width="50%">Defines styles for the wrapper IMG element</td>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td width="33%">f_imageButton_text</td>
 * <td width="50%">Defines styles for the wrapper SPAN element for text</td>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td width="33%">f_imageButton_image</td>
 * <td width="50%">Defines styles for the wrapper IMG element</td>
 * </tr>
 * 
 * 
 * </tbody>
 * </table>
 */
public class ImageButtonComponent extends ImageComponent implements 
	IDisabledCapability,
	IAccessKeyCapability,
	ITabIndexCapability,
	IFocusBlurEventCapability,
	IKeyEventCapability,
	IUnlockedClientAttributesCapability,
	IImmediateCapability,
	IValidationEventCapability,
	ISelectionEventCapability,
	IFocusStyleClassCapability {

	private static final Log LOG = LogFactory.getLog(ImageButtonComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.svg.imageButton";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(ImageComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"focusStyleClass","accessKey","blurListener","tabIndex","keyUpListener","focusListener","keyPressListener","unlockedClientAttributeNames","immediate","selectionListener","keyDownListener","disabled","validationListener"}));
	}

	public ImageButtonComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ImageButtonComponent(String componentId) {
		this();
		setId(componentId);
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

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
