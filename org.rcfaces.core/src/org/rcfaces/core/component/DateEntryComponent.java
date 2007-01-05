package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IValueChangeEventCapability;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.context.FacesContext;
import java.util.Map;
import java.util.HashMap;
import javax.faces.el.ValueBinding;
import java.util.Date;
import java.util.Collections;
import java.util.Arrays;
import org.rcfaces.core.component.capability.IAutoTabCapability;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.internal.component.IDataMapAccessor;
import org.rcfaces.core.component.AbstractCalendarComponent;
import org.rcfaces.core.internal.manager.IValidationParameters;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.converter.LiteralDateConverter;
import org.rcfaces.core.component.capability.IFocusStyleClassCapability;
import org.rcfaces.core.component.capability.IRequiredCapability;

/**
 * <p>The dateEntry Component is a specialized <a href="/comps/textEntryComponent.html">textEntry Component</a>. it sports auto-completion related to the validity of the numbers entered as a date.</p>
 * <p>The dateEntry Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text, font &amp; separators</li>
 * <li>Margin &amp; border</li>
 * <li>Help</li>
 * <li>Visibility, Read-Only, Disabled</li>
 * <li>Events Handling</li>
 * <li>Calendar functions</li>
 * </ul>
 * </p>
 */
public class DateEntryComponent extends AbstractCalendarComponent implements 
	IRequiredCapability,
	IAutoTabCapability,
	IValueChangeEventCapability,
	IFocusStyleClassCapability,
	IValidationParameters {

	public static final String COMPONENT_TYPE="org.rcfaces.core.dateEntry";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractCalendarComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"autoCompletion","required","dateFormat","valueChangeListener","focusStyleClass","showCalendarOnFocus","autoTab","defaultDate"}));
	}

	public DateEntryComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public DateEntryComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final boolean isClientSideValidationParameter(String name) {


		return isClientSideValidationParameter(name, null);
		
	}

	public final Map getValidationParametersMap() {


		return getValidationParametersMap(null);
		
	}

	public final String setValidationParameter(String name, String value, boolean client) {


		return (String)setValidationParameterData(name, value, client);
		
	}

	public final String getValidationParameter(String name) {


		 return getValidationParameter(name, null);
		
	}

	public final String removeValidationParameter(String name) {


		FacesContext facesContext=getFacesContext();

		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return null;
		}
 
 		IDataMapAccessor clientMapAccessor=engine.getDataMapAccessor(facesContext, "ClientValidationParameter", false);
		if (clientMapAccessor!=null) {
			clientMapAccessor.removeData(name, facesContext);
		}
            
		return (String)dataMapAccessor.removeData(name, facesContext);
		
	}

	public final void setValidationParameter(String name, ValueBinding value, boolean client) {


		setValidationParameterData(name, value, client);
		
	}

	public final Map getClientValidationParametersMap() {


		return getClientValidationParametersMap(null);
		
	}

	public final int getValidationParametersCount() {

		 
		 return getValidationParametersCount(null);
		
	}

	public final void setDefaultDate(String date) {


			engine.setProperty(Properties.DEFAULT_DATE, date);
		
	}

	public final Date getDefaultDate(FacesContext facesContext) {


			Object value=engine.getProperty(Properties.DEFAULT_DATE, facesContext);
			if (value instanceof String) {
				value=LiteralDateConverter.SINGLETON.getAsObject(facesContext, this, (String)value);
			}
			
			return (Date)value;
		
	}

	public final String getValidationParameter(String name, FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}

		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return null;
		}
            
		return (String)dataMapAccessor.getData(name, facesContext);
		
	}

	public final int getValidationParametersCount(FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}
		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return 0;
		}
		 
		return dataMapAccessor.getDataCount();
		
	}

	public final Map getValidationParametersMap(FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}
		
		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return Collections.EMPTY_MAP;
		}
            
		return dataMapAccessor.getDataMap(facesContext);
		
	}

	public final Map getClientValidationParametersMap(FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}
		
		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return Collections.EMPTY_MAP;
		}
            
		Map map=dataMapAccessor.getDataMap(facesContext);
		if (map.isEmpty()) {
			return Collections.EMPTY_MAP;
		}
		
		IDataMapAccessor clientMapAccessor=engine.getDataMapAccessor(facesContext, "ClientValidationParameter", false);
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

	private final Object setValidationParameterData(String name, Object value, boolean client) {


		FacesContext facesContext=getFacesContext();
		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(facesContext, "ValidationParameter", true);
		if (client) {
			// On retire la limitation au niveau client si besoin !
			IDataMapAccessor clientMapAccessor=engine.getDataMapAccessor(facesContext, "ClientValidationParameter", false);
			if (clientMapAccessor!=null) {
				clientMapAccessor.removeData(name, facesContext);
			}
		} else {
			IDataMapAccessor clientMapAccessor=engine.getDataMapAccessor(facesContext, "ClientValidationParameter", true);
			clientMapAccessor.setData(name, Boolean.FALSE, facesContext);
		}
            
		return dataMapAccessor.setData(name, value, facesContext);
		
	}

	public final boolean isClientSideValidationParameter(String name, FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}
		
		IDataMapAccessor clientMapAccessor=engine.getDataMapAccessor(facesContext, "ClientValidationParameter", false);
		if (clientMapAccessor==null) {
			return false;
		}
		return (clientMapAccessor.getData(name, facesContext)==null);
		
	}

	public final boolean isAutoTab() {
		return isAutoTab(null);
	}

	/**
	 * See {@link #isAutoTab() isAutoTab()} for more details
	 */
	public final boolean isAutoTab(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.AUTO_TAB, false, facesContext);
	}

	public final void setAutoTab(boolean autoTab) {
		engine.setProperty(Properties.AUTO_TAB, autoTab);
	}

	/**
	 * See {@link #setAutoTab(boolean) setAutoTab(boolean)} for more details
	 */
	public final void setAutoTab(ValueBinding autoTab) {
		engine.setProperty(Properties.AUTO_TAB, autoTab);
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

	/**
	 * Returns a boolean value indicating wether the associated <a href="/comps/dateCHooserComponent.html">dateChooser Component</a> should be automatically opened when the component gets the focus.
	 * @return true if calendar is to be shown
	 */
	public final boolean isShowCalendarOnFocus() {
		return isShowCalendarOnFocus(null);
	}

	/**
	 * Returns a boolean value indicating wether the associated <a href="/comps/dateCHooserComponent.html">dateChooser Component</a> should be automatically opened when the component gets the focus.
	 * @return true if calendar is to be shown
	 */
	public final boolean isShowCalendarOnFocus(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.SHOW_CALENDAR_ON_FOCUS, false, facesContext);
	}

	/**
	 * Sets a boolean value indicating wether the associated <a href="/comps/dateCHooserComponent.html">dateChooser Component</a> should be automatically opened when the component gets the focus.
	 * @param showCalendarOnFocus true if calendar is shown
	 */
	public final void setShowCalendarOnFocus(boolean showCalendarOnFocus) {
		engine.setProperty(Properties.SHOW_CALENDAR_ON_FOCUS, showCalendarOnFocus);
	}

	/**
	 * Sets a boolean value indicating wether the associated <a href="/comps/dateCHooserComponent.html">dateChooser Component</a> should be automatically opened when the component gets the focus.
	 * @param showCalendarOnFocus true if calendar is shown
	 */
	public final void setShowCalendarOnFocus(ValueBinding showCalendarOnFocus) {
		engine.setProperty(Properties.SHOW_CALENDAR_ON_FOCUS, showCalendarOnFocus);
	}

	/**
	 * Returns <code>true</code> if the attribute "showCalendarOnFocus" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isShowCalendarOnFocusSetted() {
		return engine.isPropertySetted(Properties.SHOW_CALENDAR_ON_FOCUS);
	}

	/**
	 * Returns a boolean value indicating if the component should complete automaticaly the user entry.
	 * @return true if the user entry must completed
	 */
	public final boolean isAutoCompletion() {
		return isAutoCompletion(null);
	}

	/**
	 * Returns a boolean value indicating if the component should complete automaticaly the user entry.
	 * @return true if the user entry must completed
	 */
	public final boolean isAutoCompletion(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.AUTO_COMPLETION, false, facesContext);
	}

	/**
	 * Sets a boolean value indicating if the component should complete automaticaly the user entry.
	 * @param autoCompletion true if the user entry must completed
	 */
	public final void setAutoCompletion(boolean autoCompletion) {
		engine.setProperty(Properties.AUTO_COMPLETION, autoCompletion);
	}

	/**
	 * Sets a boolean value indicating if the component should complete automaticaly the user entry.
	 * @param autoCompletion true if the user entry must completed
	 */
	public final void setAutoCompletion(ValueBinding autoCompletion) {
		engine.setProperty(Properties.AUTO_COMPLETION, autoCompletion);
	}

	/**
	 * Returns <code>true</code> if the attribute "autoCompletion" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAutoCompletionSetted() {
		return engine.isPropertySetted(Properties.AUTO_COMPLETION);
	}

	/**
	 * Returns a string specifying the format to apply to the date value.
	 * @return date format
	 */
	public final String getDateFormat() {
		return getDateFormat(null);
	}

	/**
	 * Returns a string specifying the format to apply to the date value.
	 * @return date format
	 */
	public final String getDateFormat(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DATE_FORMAT, facesContext);
	}

	/**
	 * Sets a string specifying the format to apply to the date value.
	 * @param dateFormat format
	 */
	public final void setDateFormat(String dateFormat) {
		engine.setProperty(Properties.DATE_FORMAT, dateFormat);
	}

	/**
	 * Sets a string specifying the format to apply to the date value.
	 * @param dateFormat format
	 */
	public final void setDateFormat(ValueBinding dateFormat) {
		engine.setProperty(Properties.DATE_FORMAT, dateFormat);
	}

	/**
	 * Returns <code>true</code> if the attribute "dateFormat" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDateFormatSetted() {
		return engine.isPropertySetted(Properties.DATE_FORMAT);
	}

	/**
	 * Returns the date selected by default.
	 * @return default date
	 */
	public final java.util.Date getDefaultDate() {
		return getDefaultDate(null);
	}

	/**
	 * Sets the date to select by default.
	 * @param defaultDate default date
	 */
	public final void setDefaultDate(java.util.Date defaultDate) {
		engine.setProperty(Properties.DEFAULT_DATE, defaultDate);
	}

	/**
	 * Sets the date to select by default.
	 * @param defaultDate default date
	 */
	public final void setDefaultDate(ValueBinding defaultDate) {
		engine.setProperty(Properties.DEFAULT_DATE, defaultDate);
	}

	/**
	 * Returns <code>true</code> if the attribute "defaultDate" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDefaultDateSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_DATE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
