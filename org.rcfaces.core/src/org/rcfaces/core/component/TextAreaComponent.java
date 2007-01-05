package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IEmptyMessageCapability;
import org.rcfaces.core.component.capability.IFocusStyleClassCapability;
import org.rcfaces.core.component.capability.IMenuCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.component.capability.IRequiredCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.capability.ISeverityStyleClassCapability;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.component.capability.IValueChangeEventCapability;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.MenuTools;

/**
 * <p>The textAreaEntry Component is based on the standard HTML tag &lt;TEXTAREA&gt; and is a <a href="/comps/textEntryComponent.html">textEntry Component</a>.</p>
 * <p>The textAreaEntry Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text &amp; font</li>
 * <li>Help</li>
 * <li>Visibility, Read-Only, Disabled</li>
 * <li>Events Handling</li>
 * </ul>
 * </p>
 */
public class TextAreaComponent extends AbstractInputComponent implements 
	IRequiredCapability,
	ITextCapability,
	IEmptyMessageCapability,
	IReadOnlyCapability,
	IValueChangeEventCapability,
	IMenuCapability,
	IFocusStyleClassCapability,
	ISeverityStyleClassCapability,
	ISelectionEventCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.textArea";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractInputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"errorStyleClass","selectionListener","fatalStyleClass","required","valueChangeListener","columnNumber","warnStyleClass","styleClass","text","rowNumber","infoStyleClass","readOnly","focusStyleClass","emptyMessage"}));
	}

	public TextAreaComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public TextAreaComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final java.lang.String getText() {
		return getText(null);
	}

	/**
	 * See {@link #getText() getText()} for more details
	 */
	public final java.lang.String getText(javax.faces.context.FacesContext facesContext) {
		return org.rcfaces.core.internal.tools.ValuesTools.valueToString(this, facesContext);
	}

	public final void setText(java.lang.String text) {
		setValue(text);
	}

	/**
	 * See {@link #setText(String) setText(String)} for more details
	 */
	public final void setText(ValueBinding text) {
		setValue(text);
	}

	public final java.lang.String getEmptyMessage() {
		return getEmptyMessage(null);
	}

	/**
	 * See {@link #getEmptyMessage() getEmptyMessage()} for more details
	 */
	public final java.lang.String getEmptyMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.EMPTY_MESSAGE, facesContext);
	}

	public final void setEmptyMessage(java.lang.String emptyMessage) {
		engine.setProperty(Properties.EMPTY_MESSAGE, emptyMessage);
	}

	/**
	 * See {@link #setEmptyMessage(String) setEmptyMessage(String)} for more details
	 */
	public final void setEmptyMessage(ValueBinding emptyMessage) {
		engine.setProperty(Properties.EMPTY_MESSAGE, emptyMessage);
	}

	public final boolean isReadOnly() {
		return isReadOnly(null);
	}

	/**
	 * See {@link #isReadOnly() isReadOnly()} for more details
	 */
	public final boolean isReadOnly(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.READ_ONLY, false, facesContext);
	}

	public final void setReadOnly(boolean readOnly) {
		engine.setProperty(Properties.READ_ONLY, readOnly);
	}

	/**
	 * See {@link #setReadOnly(boolean) setReadOnly(boolean)} for more details
	 */
	public final void setReadOnly(ValueBinding readOnly) {
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

	public final IMenuComponent getMenu(String menuId) {


		return MenuTools.getMenu(this, menuId);
		
	}

	public final IMenuComponent getMenu() {


		return MenuTools.getMenu(this);
		
	}

	public final IMenuIterator listMenus() {


		return MenuTools.listMenus(this);
		
	}

	public final java.lang.String getFocusStyleClass() {
		return getFocusStyleClass(null);
	}

	/**
	 * See {@link #getFocusStyleClass() getFocusStyleClass()} for more details
	 */
	public final java.lang.String getFocusStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOCUS_STYLE_CLASS, facesContext);
	}

	public final void setFocusStyleClass(java.lang.String focusStyleClass) {
		engine.setProperty(Properties.FOCUS_STYLE_CLASS, focusStyleClass);
	}

	/**
	 * See {@link #setFocusStyleClass(String) setFocusStyleClass(String)} for more details
	 */
	public final void setFocusStyleClass(ValueBinding focusStyleClass) {
		engine.setProperty(Properties.FOCUS_STYLE_CLASS, focusStyleClass);
	}

	public final java.lang.String getErrorStyleClass() {
		return getErrorStyleClass(null);
	}

	/**
	 * See {@link #getErrorStyleClass() getErrorStyleClass()} for more details
	 */
	public final java.lang.String getErrorStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ERROR_STYLE_CLASS, facesContext);
	}

	public final void setErrorStyleClass(java.lang.String errorStyleClass) {
		engine.setProperty(Properties.ERROR_STYLE_CLASS, errorStyleClass);
	}

	/**
	 * See {@link #setErrorStyleClass(String) setErrorStyleClass(String)} for more details
	 */
	public final void setErrorStyleClass(ValueBinding errorStyleClass) {
		engine.setProperty(Properties.ERROR_STYLE_CLASS, errorStyleClass);
	}

	public final java.lang.String getFatalStyleClass() {
		return getFatalStyleClass(null);
	}

	/**
	 * See {@link #getFatalStyleClass() getFatalStyleClass()} for more details
	 */
	public final java.lang.String getFatalStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FATAL_STYLE_CLASS, facesContext);
	}

	public final void setFatalStyleClass(java.lang.String fatalStyleClass) {
		engine.setProperty(Properties.FATAL_STYLE_CLASS, fatalStyleClass);
	}

	/**
	 * See {@link #setFatalStyleClass(String) setFatalStyleClass(String)} for more details
	 */
	public final void setFatalStyleClass(ValueBinding fatalStyleClass) {
		engine.setProperty(Properties.FATAL_STYLE_CLASS, fatalStyleClass);
	}

	public final java.lang.String getInfoStyleClass() {
		return getInfoStyleClass(null);
	}

	/**
	 * See {@link #getInfoStyleClass() getInfoStyleClass()} for more details
	 */
	public final java.lang.String getInfoStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.INFO_STYLE_CLASS, facesContext);
	}

	public final void setInfoStyleClass(java.lang.String infoStyleClass) {
		engine.setProperty(Properties.INFO_STYLE_CLASS, infoStyleClass);
	}

	/**
	 * See {@link #setInfoStyleClass(String) setInfoStyleClass(String)} for more details
	 */
	public final void setInfoStyleClass(ValueBinding infoStyleClass) {
		engine.setProperty(Properties.INFO_STYLE_CLASS, infoStyleClass);
	}

	public final java.lang.String getWarnStyleClass() {
		return getWarnStyleClass(null);
	}

	/**
	 * See {@link #getWarnStyleClass() getWarnStyleClass()} for more details
	 */
	public final java.lang.String getWarnStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.WARN_STYLE_CLASS, facesContext);
	}

	public final void setWarnStyleClass(java.lang.String warnStyleClass) {
		engine.setProperty(Properties.WARN_STYLE_CLASS, warnStyleClass);
	}

	/**
	 * See {@link #setWarnStyleClass(String) setWarnStyleClass(String)} for more details
	 */
	public final void setWarnStyleClass(ValueBinding warnStyleClass) {
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

	/**
	 * Returns an int value holding the width of the component in characters : x characters translates into y pixels width.
	 * @return width in characters
	 */
	public final int getColumnNumber() {
		return getColumnNumber(null);
	}

	/**
	 * Returns an int value holding the width of the component in characters : x characters translates into y pixels width.
	 * @return width in characters
	 */
	public final int getColumnNumber(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.COLUMN_NUMBER, 0, facesContext);
	}

	/**
	 * Sets an int value holding the width of the component in characters : x characters translates into y pixels width.
	 * @param columnNumber width in characters
	 */
	public final void setColumnNumber(int columnNumber) {
		engine.setProperty(Properties.COLUMN_NUMBER, columnNumber);
	}

	/**
	 * Sets an int value holding the width of the component in characters : x characters translates into y pixels width.
	 * @param columnNumber width in characters
	 */
	public final void setColumnNumber(ValueBinding columnNumber) {
		engine.setProperty(Properties.COLUMN_NUMBER, columnNumber);
	}

	/**
	 * Returns <code>true</code> if the attribute "columnNumber" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isColumnNumberSetted() {
		return engine.isPropertySetted(Properties.COLUMN_NUMBER);
	}

	/**
	 * Returns an int value specifying the number of rows to be displayed.
	 * @return number of rows
	 */
	public final int getRowNumber() {
		return getRowNumber(null);
	}

	/**
	 * Returns an int value specifying the number of rows to be displayed.
	 * @return number of rows
	 */
	public final int getRowNumber(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.ROW_NUMBER, 0, facesContext);
	}

	/**
	 * Sets an int value specifying the number of rows to be displayed.
	 * @param rowNumber number of rows
	 */
	public final void setRowNumber(int rowNumber) {
		engine.setProperty(Properties.ROW_NUMBER, rowNumber);
	}

	/**
	 * Sets an int value specifying the number of rows to be displayed.
	 * @param rowNumber number of rows
	 */
	public final void setRowNumber(ValueBinding rowNumber) {
		engine.setProperty(Properties.ROW_NUMBER, rowNumber);
	}

	/**
	 * Returns <code>true</code> if the attribute "rowNumber" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRowNumberSetted() {
		return engine.isPropertySetted(Properties.ROW_NUMBER);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
