package org.rcfaces.core.component;

import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.internal.component.Properties;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IRequiredCapability;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.component.capability.IFocusStyleClassCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.apache.commons.logging.Log;
import java.util.Set;
import org.rcfaces.core.component.capability.IValueChangeEventCapability;
import java.util.Collection;
import org.rcfaces.core.component.capability.ISeverityStyleClassCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import java.lang.String;
import org.rcfaces.core.internal.tools.MenuTools;
import org.rcfaces.core.component.capability.IEmptyMessageCapability;
import org.rcfaces.core.component.capability.IAlternateTextCapability;
import org.rcfaces.core.component.capability.ITextDirectionCapability;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IMaxTextLengthCapability;
import java.util.Arrays;
import org.rcfaces.core.component.AbstractInputComponent;
import org.rcfaces.core.component.capability.IMenuCapability;
import org.rcfaces.core.component.capability.ITextCapability;

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
	ITextDirectionCapability,
	IEmptyMessageCapability,
	IReadOnlyCapability,
	IValueChangeEventCapability,
	IMenuCapability,
	IFocusStyleClassCapability,
	ISeverityStyleClassCapability,
	ISelectionEventCapability,
	IAlternateTextCapability,
	IMaxTextLengthCapability {

	private static final Log LOG = LogFactory.getLog(TextAreaComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.textArea";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractInputComponent.BEHAVIOR_EVENT_NAMES;

	public TextAreaComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public TextAreaComponent(String componentId) {
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
		return getStateHelper().get(Properties.TEXT)!=null;
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
		return (Integer)getStateHelper().eval(Properties.TEXT_DIRECTION, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "textDirection" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextDirectionSetted() {
		return getStateHelper().get(Properties.TEXT_DIRECTION)!=null;
	}

	public void setTextDirection(int textDirection) {
		getStateHelper().put(Properties.TEXT_DIRECTION, textDirection);
	}

	public java.lang.String getEmptyMessage() {
		return getEmptyMessage(null);
	}

	/**
	 * See {@link #getEmptyMessage() getEmptyMessage()} for more details
	 */
	public java.lang.String getEmptyMessage(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.EMPTY_MESSAGE);
	}

	/**
	 * Returns <code>true</code> if the attribute "emptyMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isEmptyMessageSetted() {
		return getStateHelper().get(Properties.EMPTY_MESSAGE)!=null;
	}

	public void setEmptyMessage(java.lang.String emptyMessage) {
		getStateHelper().put(Properties.EMPTY_MESSAGE, emptyMessage);
	}

	public boolean isReadOnly() {
		return isReadOnly(null);
	}

	/**
	 * See {@link #isReadOnly() isReadOnly()} for more details
	 */
	public boolean isReadOnly(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.READ_ONLY, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "readOnly" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isReadOnlySetted() {
		return getStateHelper().get(Properties.READ_ONLY)!=null;
	}

	public void setReadOnly(boolean readOnly) {
		getStateHelper().put(Properties.READ_ONLY, readOnly);
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
		return (String)getStateHelper().eval(Properties.FOCUS_STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "focusStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFocusStyleClassSetted() {
		return getStateHelper().get(Properties.FOCUS_STYLE_CLASS)!=null;
	}

	public void setFocusStyleClass(java.lang.String focusStyleClass) {
		getStateHelper().put(Properties.FOCUS_STYLE_CLASS, focusStyleClass);
	}

	public java.lang.String getErrorStyleClass() {
		return getErrorStyleClass(null);
	}

	/**
	 * See {@link #getErrorStyleClass() getErrorStyleClass()} for more details
	 */
	public java.lang.String getErrorStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ERROR_STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "errorStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isErrorStyleClassSetted() {
		return getStateHelper().get(Properties.ERROR_STYLE_CLASS)!=null;
	}

	public void setErrorStyleClass(java.lang.String errorStyleClass) {
		getStateHelper().put(Properties.ERROR_STYLE_CLASS, errorStyleClass);
	}

	public java.lang.String getFatalStyleClass() {
		return getFatalStyleClass(null);
	}

	/**
	 * See {@link #getFatalStyleClass() getFatalStyleClass()} for more details
	 */
	public java.lang.String getFatalStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.FATAL_STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "fatalStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFatalStyleClassSetted() {
		return getStateHelper().get(Properties.FATAL_STYLE_CLASS)!=null;
	}

	public void setFatalStyleClass(java.lang.String fatalStyleClass) {
		getStateHelper().put(Properties.FATAL_STYLE_CLASS, fatalStyleClass);
	}

	public java.lang.String getInfoStyleClass() {
		return getInfoStyleClass(null);
	}

	/**
	 * See {@link #getInfoStyleClass() getInfoStyleClass()} for more details
	 */
	public java.lang.String getInfoStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.INFO_STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "infoStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isInfoStyleClassSetted() {
		return getStateHelper().get(Properties.INFO_STYLE_CLASS)!=null;
	}

	public void setInfoStyleClass(java.lang.String infoStyleClass) {
		getStateHelper().put(Properties.INFO_STYLE_CLASS, infoStyleClass);
	}

	public java.lang.String getWarnStyleClass() {
		return getWarnStyleClass(null);
	}

	/**
	 * See {@link #getWarnStyleClass() getWarnStyleClass()} for more details
	 */
	public java.lang.String getWarnStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.WARN_STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "warnStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isWarnStyleClassSetted() {
		return getStateHelper().get(Properties.WARN_STYLE_CLASS)!=null;
	}

	public void setWarnStyleClass(java.lang.String warnStyleClass) {
		getStateHelper().put(Properties.WARN_STYLE_CLASS, warnStyleClass);
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
		return (String)getStateHelper().eval(Properties.ALTERNATE_TEXT);
	}

	/**
	 * Returns <code>true</code> if the attribute "alternateText" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAlternateTextSetted() {
		return getStateHelper().get(Properties.ALTERNATE_TEXT)!=null;
	}

	public void setAlternateText(java.lang.String alternateText) {
		getStateHelper().put(Properties.ALTERNATE_TEXT, alternateText);
	}

	public int getMaxTextLength() {
		return getMaxTextLength(null);
	}

	/**
	 * See {@link #getMaxTextLength() getMaxTextLength()} for more details
	 */
	public int getMaxTextLength(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.MAX_TEXT_LENGTH, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "maxTextLength" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMaxTextLengthSetted() {
		return getStateHelper().get(Properties.MAX_TEXT_LENGTH)!=null;
	}

	public void setMaxTextLength(int maxTextLength) {
		getStateHelper().put(Properties.MAX_TEXT_LENGTH, maxTextLength);
	}

	/**
	 * Returns an int value holding the width of the component in characters : x characters translates into y pixels width.
	 * @return width in characters
	 */
	public int getColumnNumber() {
		return getColumnNumber(null);
	}

	/**
	 * Returns an int value holding the width of the component in characters : x characters translates into y pixels width.
	 * @return width in characters
	 */
	public int getColumnNumber(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.COLUMN_NUMBER, 0);
	}

	/**
	 * Sets an int value holding the width of the component in characters : x characters translates into y pixels width.
	 * @param columnNumber width in characters
	 */
	public void setColumnNumber(int columnNumber) {
		 getStateHelper().put(Properties.COLUMN_NUMBER, columnNumber);
	}

	/**
	 * Sets an int value holding the width of the component in characters : x characters translates into y pixels width.
	 * @param columnNumber width in characters
	 */
	/**
	 * Returns <code>true</code> if the attribute "columnNumber" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isColumnNumberSetted() {
		return getStateHelper().get(Properties.COLUMN_NUMBER)!=null;
	}

	/**
	 * Returns an int value specifying the number of rows to be displayed.
	 * @return number of rows
	 */
	public int getRowNumber() {
		return getRowNumber(null);
	}

	/**
	 * Returns an int value specifying the number of rows to be displayed.
	 * @return number of rows
	 */
	public int getRowNumber(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.ROW_NUMBER, 0);
	}

	/**
	 * Sets an int value specifying the number of rows to be displayed.
	 * @param rowNumber number of rows
	 */
	public void setRowNumber(int rowNumber) {
		 getStateHelper().put(Properties.ROW_NUMBER, rowNumber);
	}

	/**
	 * Sets an int value specifying the number of rows to be displayed.
	 * @param rowNumber number of rows
	 */
	/**
	 * Returns <code>true</code> if the attribute "rowNumber" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isRowNumberSetted() {
		return getStateHelper().get(Properties.ROW_NUMBER)!=null;
	}

	public boolean isIgnoreWhenFull() {
		return isIgnoreWhenFull(null);
	}

	public boolean isIgnoreWhenFull(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.IGNORE_WHEN_FULL, false);
	}

	public void setIgnoreWhenFull(boolean ignoreWhenFull) {
		 getStateHelper().put(Properties.IGNORE_WHEN_FULL, ignoreWhenFull);
	}

	/**
	 * Returns <code>true</code> if the attribute "ignoreWhenFull" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isIgnoreWhenFullSetted() {
		return getStateHelper().get(Properties.IGNORE_WHEN_FULL)!=null;
	}

}
