package org.rcfaces.core.component;

import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.internal.component.Properties;
import java.lang.String;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.MenuTools;
import org.rcfaces.core.component.capability.IRequiredCapability;
import org.rcfaces.core.component.capability.IAlternateTextCapability;
import org.rcfaces.core.component.capability.IEmptyMessageCapability;
import org.rcfaces.core.component.capability.IFocusStyleClassCapability;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.component.capability.ITextDirectionCapability;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.component.capability.IValueChangeEventCapability;
import org.rcfaces.core.component.AbstractInputComponent;
import org.rcfaces.core.component.capability.IMenuCapability;
import org.rcfaces.core.component.capability.ISeverityStyleClassCapability;
import org.rcfaces.core.component.capability.ITextCapability;

/**
 * <p>The textEditorToolFolder component is a container allowing to edit text and have a specific toolBar </p>
 * 
 * <p>The textEditorToolFolder Component has the following capabilities :
 * <ul>
 * <li>IRequiredCapability</li>
 * <li>ITextCapability</li>
 * <li>ITextDirectionCapability</li>
 * <li>IEmptyMessageCapability</li>
 * <li>IReadOnlyCapability</li>
 * <li>IValueChangeEventCapability</li>
 * <li>IMenuCapability</li>
 * <li>IFocusStyleClassCapability</li>
 * <li>ISeverityStyleClassCapability</li>
 * <li>ISelectionEventCapability</li>
 * <li>IAlternateTextCapability</li>
 * </ul>
 * </p>
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/TextEditorComponent.html">textEditorToolFolder</a> renderer is linked to the <a href="/jsdocs/index.html?f_textEditor.html" target="_blank">f_textEditor</a> javascript class. f_textEditor extends f_component</p>
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
 * <td width="33%">f_textEditor</td>
 * <td width="50%">Defines styles for the wrapper DIV element</td>
 * </tr>
 * </tbody>
 * </table>
 */
public class TextEditorComponent extends AbstractInputComponent implements 
	IRequiredCapability,
	ITextCapability,
	ITextDirectionCapability,
	IEmptyMessageCapability,
	IReadOnlyCapability,
	IValueChangeEventCapability,
	IMenuCapability,
	IFocusStyleClassCapability,
	ISeverityStyleClassCapability,
	ISelectionEventCapability,
	IAlternateTextCapability {

	private static final Log LOG = LogFactory.getLog(TextEditorComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.textEditor";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractInputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"focusStyleClass","text","fatalStyleClass","styleClass","textDirection","emptyMessage","selectionListener","valueChangeListener","readOnly","errorStyleClass","warnStyleClass","alternateText","infoStyleClass","valueMimeType","required"}));
	}
	protected static final String CAMELIA_VALUE_ALIAS="text";

	public TextEditorComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public TextEditorComponent(String componentId) {
		this();
		setId(componentId);
	}

	public java.lang.String getText() {
		return getText(null);
	}

	/**
	 * See {@link #getText() getText()} for more details
	 */
	public java.lang.String getText(javax.faces.context.FacesContext facesContext) {
		return org.rcfaces.core.internal.tools.ValuesTools.valueToString(this, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "text" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextSetted() {
		return engine.isPropertySetted(Properties.TEXT);
	}

	public void setText(java.lang.String text) {
		if (org.rcfaces.core.internal.listener.CameliaPhaseListener.isApplyingRequestValues()) {
			setSubmittedExternalValue(text);
		} else {
			setValue(text);
		}
	}

	public int getTextDirection() {
		return getTextDirection(null);
	}

	/**
	 * See {@link #getTextDirection() getTextDirection()} for more details
	 */
	public int getTextDirection(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.TEXT_DIRECTION,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "textDirection" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextDirectionSetted() {
		return engine.isPropertySetted(Properties.TEXT_DIRECTION);
	}

	public void setTextDirection(int textDirection) {
		engine.setProperty(Properties.TEXT_DIRECTION, textDirection);
	}

	public java.lang.String getEmptyMessage() {
		return getEmptyMessage(null);
	}

	/**
	 * See {@link #getEmptyMessage() getEmptyMessage()} for more details
	 */
	public java.lang.String getEmptyMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.EMPTY_MESSAGE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "emptyMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isEmptyMessageSetted() {
		return engine.isPropertySetted(Properties.EMPTY_MESSAGE);
	}

	public void setEmptyMessage(java.lang.String emptyMessage) {
		engine.setProperty(Properties.EMPTY_MESSAGE, emptyMessage);
	}

	public boolean isReadOnly() {
		return isReadOnly(null);
	}

	/**
	 * See {@link #isReadOnly() isReadOnly()} for more details
	 */
	public boolean isReadOnly(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.READ_ONLY, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "readOnly" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isReadOnlySetted() {
		return engine.isPropertySetted(Properties.READ_ONLY);
	}

	public void setReadOnly(boolean readOnly) {
		engine.setProperty(Properties.READ_ONLY, readOnly);
	}

	public final void addValueChangeListener(javax.faces.event.ValueChangeListener listener) {
		addFacesListener(listener);
	}

	public final void removeValueChangeListener(javax.faces.event.ValueChangeListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listValueChangeListeners() {
		return getFacesListeners(javax.faces.event.ValueChangeListener.class);
	}

	public IMenuComponent getMenu() {


		return MenuTools.getMenu(this);
		
	}

	public IMenuComponent getMenu(String menuId) {


		return MenuTools.getMenu(this, menuId);
		
	}

	public IMenuIterator listMenus() {


		return MenuTools.listMenus(this);
		
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

	public final void addSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		addFacesListener(listener);
	}

	public final void removeSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listSelectionListeners() {
		return getFacesListeners(org.rcfaces.core.event.ISelectionListener.class);
	}

	public java.lang.String getAlternateText() {
		return getAlternateText(null);
	}

	/**
	 * See {@link #getAlternateText() getAlternateText()} for more details
	 */
	public java.lang.String getAlternateText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ALTERNATE_TEXT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "alternateText" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAlternateTextSetted() {
		return engine.isPropertySetted(Properties.ALTERNATE_TEXT);
	}

	public void setAlternateText(java.lang.String alternateText) {
		engine.setProperty(Properties.ALTERNATE_TEXT, alternateText);
	}

	public String getValueMimeType() {
		return getValueMimeType(null);
	}

	public String getValueMimeType(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.VALUE_MIME_TYPE, facesContext);
		return s;
	}

	public void setValueMimeType(String valueMimeType) {
		engine.setProperty(Properties.VALUE_MIME_TYPE, valueMimeType);
	}

	/**
	 * Returns <code>true</code> if the attribute "valueMimeType" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isValueMimeTypeSetted() {
		return engine.isPropertySetted(Properties.VALUE_MIME_TYPE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}

	protected String getCameliaValueAlias() {
		return CAMELIA_VALUE_ALIAS;
	}
}
