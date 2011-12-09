package org.rcfaces.core.component;

import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.internal.component.Properties;
import java.util.Map;
import java.util.Collections;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IRequiredCapability;
import java.util.HashMap;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.component.capability.IFocusStyleClassCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.apache.commons.logging.Log;
import java.util.Set;
import org.rcfaces.core.component.capability.IValueChangeEventCapability;
import java.util.Collection;
import org.rcfaces.core.component.capability.ISeverityStyleClassCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import java.lang.Object;
import java.lang.String;
import org.rcfaces.core.internal.tools.MenuTools;
import org.rcfaces.core.internal.tools.ClientValidatorTools;
import org.rcfaces.core.internal.component.IDataMapAccessor;
import org.rcfaces.core.component.capability.IEmptyMessageCapability;
import org.rcfaces.core.component.capability.IAlternateTextCapability;
import org.rcfaces.core.component.capability.ITextDirectionCapability;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IMaxTextLengthCapability;
import org.rcfaces.core.component.capability.IAutoTabCapability;
import java.util.Arrays;
import org.rcfaces.core.internal.manager.IValidationParameters;
import org.rcfaces.core.component.AbstractInputComponent;
import org.rcfaces.core.component.capability.IMenuCapability;
import org.rcfaces.core.component.capability.IClientValidationCapability;
import org.rcfaces.core.component.capability.ITextCapability;

/**
 * <p>The textEntry Component is based on the standard HTML tag &lt;INPUT TYPE="text"&gt;.</p>
 * <p>The textEntry Component has the following capabilities :
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
public class TextEntryComponent extends AbstractInputComponent implements 
	IRequiredCapability,
	IAutoTabCapability,
	ITextCapability,
	ITextDirectionCapability,
	IEmptyMessageCapability,
	IReadOnlyCapability,
	IValueChangeEventCapability,
	IMenuCapability,
	IFocusStyleClassCapability,
	ISeverityStyleClassCapability,
	IAlternateTextCapability,
	IMaxTextLengthCapability,
	IClientValidationCapability,
	ISelectionEventCapability,
	IValidationParameters {

	private static final Log LOG = LogFactory.getLog(TextEntryComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.textEntry";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractInputComponent.BEHAVIOR_EVENT_NAMES;

	public TextEntryComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public TextEntryComponent(String componentId) {
		this();
		setId(componentId);
	}

	public int getValidationParametersCount() {

		 
		 return getValidationParametersCount(null);
		
	}

	public boolean isClientSideValidationParameter(String name) {


		return isClientSideValidationParameter(name, null);
		
	}

	public Map getValidationParametersMap() {


		return getValidationParametersMap(null);
		
	}

	public void setValidationParameter(String name, ValueExpression value, boolean client) {


		setValidationParameterData(name, value, client);
		
	}

	public String getValidationParameter(String name) {


		 return getValidationParameter(name, null);
		
	}

	public Map getClientValidationParametersMap() {


		return getClientValidationParametersMap(null);
		
	}

	public String removeValidationParameter(String name) {


		FacesContext facesContext=getFacesContext();

		IDataMapAccessor dataMapAccessor=getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return null;
		}
 
 		IDataMapAccessor clientMapAccessor=getDataMapAccessor(facesContext, "ClientValidationParameter", false);
		if (clientMapAccessor!=null) {
			clientMapAccessor.removeData(name, facesContext);
		}
            
		return (String)dataMapAccessor.removeData(name, facesContext);
		
	}

	public String setValidationParameter(String name, String value, boolean client) {


		return (String)setValidationParameterData(name, value, client);
		
	}

	public String getValidationParameter(String name, FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}

		IDataMapAccessor dataMapAccessor=getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return null;
		}
            
		return (String)dataMapAccessor.getData(name, facesContext);
		
	}

	public int getValidationParametersCount(FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}
		IDataMapAccessor dataMapAccessor=getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return 0;
		}
		 
		return dataMapAccessor.getDataCount();
		
	}

	public Map getValidationParametersMap(FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}
		
		IDataMapAccessor dataMapAccessor=getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return Collections.EMPTY_MAP;
		}
            
		return dataMapAccessor.getDataMap(facesContext);
		
	}

	public Map getClientValidationParametersMap(FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}
		
		IDataMapAccessor dataMapAccessor=getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return Collections.EMPTY_MAP;
		}
            
		Map map=dataMapAccessor.getDataMap(facesContext);
		if (map.isEmpty()) {
			return Collections.EMPTY_MAP;
		}
		
		IDataMapAccessor clientMapAccessor=getDataMapAccessor(facesContext, "ClientValidationParameter", false);
		if (clientMapAccessor==null) {
			if (Constants.READ_ONLY_COLLECTION_LOCK_ENABLED) {
				map=Collections.unmodifiableMap(map);
			}
			return map;
		}
		
		Map client=clientMapAccessor.getDataMap(facesContext);
		if (client==null || client.isEmpty()) {
		
			if (Constants.READ_ONLY_COLLECTION_LOCK_ENABLED) {
				map=Collections.unmodifiableMap(map);
			}
			return map;
		}
		
		Map fmap=new HashMap(map);
		if (map.keySet().removeAll(client.keySet())==false) {
			if (Constants.READ_ONLY_COLLECTION_LOCK_ENABLED) {
				map=Collections.unmodifiableMap(map);
			}
			return map;
		}
		
		if (fmap.isEmpty()) {
			return Collections.EMPTY_MAP;
		}
		
		if (Constants.READ_ONLY_COLLECTION_LOCK_ENABLED) {
			fmap=Collections.unmodifiableMap(fmap);
		}
		
		return fmap;
		
	}

	private Object setValidationParameterData(String name, Object value, boolean client) {


		FacesContext facesContext=getFacesContext();
		IDataMapAccessor dataMapAccessor=getDataMapAccessor(facesContext, "ValidationParameter", true);
		if (client) {
			// On retire la limitation au niveau client si besoin !
			IDataMapAccessor clientMapAccessor=getDataMapAccessor(facesContext, "ClientValidationParameter", false);
			if (clientMapAccessor!=null) {
				clientMapAccessor.removeData(name, facesContext);
			}
		} else {
			IDataMapAccessor clientMapAccessor=getDataMapAccessor(facesContext, "ClientValidationParameter", true);
			clientMapAccessor.setData(name, Boolean.FALSE, facesContext);
		}
            
		return dataMapAccessor.setData(name, value, facesContext);
		
	}

	public boolean isClientSideValidationParameter(String name, FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}
		
		IDataMapAccessor clientMapAccessor=getDataMapAccessor(facesContext, "ClientValidationParameter", false);
		if (clientMapAccessor==null) {
			return false;
		}
		return (clientMapAccessor.getData(name, facesContext)==null);
		
	}

	public void setClientValidator(String clientValidator) {


				getStateHelper().put(Properties.CLIENT_VALIDATOR, clientValidator);
				
				ClientValidatorTools.setClientValidator(null, this);
			
	}

	public boolean isAutoTab() {
		return isAutoTab(null);
	}

	/**
	 * See {@link #isAutoTab() isAutoTab()} for more details
	 */
	public boolean isAutoTab(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.AUTO_TAB, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "autoTab" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAutoTabSetted() {
		return getStateHelper().get(Properties.AUTO_TAB)!=null;
	}

	public void setAutoTab(boolean autoTab) {
		getStateHelper().put(Properties.AUTO_TAB, autoTab);
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

	public java.lang.String getClientValidator() {
		return getClientValidator(null);
	}

	/**
	 * See {@link #getClientValidator() getClientValidator()} for more details
	 */
	public java.lang.String getClientValidator(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.CLIENT_VALIDATOR);
	}

	/**
	 * Returns <code>true</code> if the attribute "clientValidator" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isClientValidatorSetted() {
		return getStateHelper().get(Properties.CLIENT_VALIDATOR)!=null;
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
	 * Returns a boolean value indicating if the component should complete automaticaly the user entry.
	 * @return true if the user entry must completed
	 */
	public boolean isAutoCompletion() {
		return isAutoCompletion(null);
	}

	/**
	 * Returns a boolean value indicating if the component should complete automaticaly the user entry.
	 * @return true if the user entry must completed
	 */
	public boolean isAutoCompletion(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.AUTO_COMPLETION, true);
	}

	/**
	 * Sets a boolean value indicating if the component should complete automaticaly the user entry.
	 * @param autoCompletion true if the user entry must completed
	 */
	public void setAutoCompletion(boolean autoCompletion) {
		 getStateHelper().put(Properties.AUTO_COMPLETION, autoCompletion);
	}

	/**
	 * Sets a boolean value indicating if the component should complete automaticaly the user entry.
	 * @param autoCompletion true if the user entry must completed
	 */
	/**
	 * Returns <code>true</code> if the attribute "autoCompletion" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isAutoCompletionSetted() {
		return getStateHelper().get(Properties.AUTO_COMPLETION)!=null;
	}

}
