package org.rcfaces.core.component;

import org.rcfaces.core.internal.converter.LiteralDateConverter;
import org.rcfaces.core.internal.component.Properties;
import java.util.Map;
import org.apache.commons.logging.LogFactory;
import java.util.Collections;
import org.rcfaces.core.component.capability.IRequiredCapability;
import java.util.HashMap;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.capability.IFocusStyleClassCapability;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.component.AbstractCalendarComponent;
import org.apache.commons.logging.Log;
import java.util.Set;
import org.rcfaces.core.component.capability.IValueChangeEventCapability;
import org.rcfaces.core.component.capability.ISeverityStyleClassCapability;
import org.rcfaces.core.component.capability.IDateFormatCapability;
import java.lang.Object;
import java.lang.String;
import java.util.Date;
import org.rcfaces.core.internal.component.IDataMapAccessor;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IAutoTabCapability;
import java.util.Arrays;
import org.rcfaces.core.internal.manager.IValidationParameters;

/**
 * <p>The dateEntry Component is a specialized <a href="/comps/textEntryComponent.html">textEntry Component</a>. it sports auto-completion related to the validity of the numbers entered as a date.</p>
 * <p>The dateEntry Component has the following capabilities :
 * <ul>
 * <li>PIRequiredCapability</li>
 * <li>IAutoTabCapability</li>
 * <li>IValueChangeEventCapability</li>
 * <li>IFocusStyleClassCapability</li>
 * <li>ISeverityStyleClassCapability</li>
 * <li>IDateFormatCapability</li>
 * <li>IValidationParameters</li>
 * </ul>
 * </p>
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/DateEntryComponent.html">dateEntry</a> renderer is link to the <a href="/jsdocs/index.html?f_dateEntry.html" target="_blank">f_dateEntry</a> javascript class. f_dateEntry extends f_compositeNumEntry, fa_calendarPopup</p>
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
 * <td width="33%">f_dateEntry</td>
 * <td id="ermvsh" width="50%">Defines styles for the wrapper DIV element</td>
 * </tr>
 * <tr  style="text-align:left">
 * <td width="33%">f_dateEntry_dd</td>
 * <td id="ermvsh" width="50%">Defines styles for day INPUT element.</td>
 * </tr>
 * <tr  style="text-align:left">
 * <td width="33%">f_dateEntry_MM</td>
 * <td id="ermvsh" width="50%">Defines styles for month INPUT element.</td>
 * </tr>
 * <tr  style="text-align:left">
 * <td width="33%">f_dateEntry_yyyy</td>
 * <td id="ermvsh" width="50%">Defines styles for years INPUT element.</td>
 * </tr>
 * </tbody>
 * </table>
 */
public class DateEntryComponent extends AbstractCalendarComponent implements 
	IRequiredCapability,
	IAutoTabCapability,
	IValueChangeEventCapability,
	IFocusStyleClassCapability,
	ISeverityStyleClassCapability,
	IDateFormatCapability,
	IValidationParameters {

	private static final Log LOG = LogFactory.getLog(DateEntryComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.dateEntry";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractCalendarComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"autoTab","focusStyleClass","fatalStyleClass","styleClass","showCalendarOnFocus","defaultDate","valueChangeListener","errorStyleClass","warnStyleClass","autoCompletion","infoStyleClass","dateFormat","required"}));
	}

	public DateEntryComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public DateEntryComponent(String componentId) {
		this();
		setId(componentId);
	}

	public int getValidationParametersCount() {

		 
		 return getValidationParametersCount(null);
		
	}

	public String setValidationParameter(String name, String value, boolean client) {


		return (String)setValidationParameterData(name, value, client);
		
	}

	public boolean isClientSideValidationParameter(String name) {


		return isClientSideValidationParameter(name, null);
		
	}

	public Map getValidationParametersMap() {


		return getValidationParametersMap(null);
		
	}

	public String getValidationParameter(String name) {


		 return getValidationParameter(name, null);
		
	}

	public Map getClientValidationParametersMap() {


		return getClientValidationParametersMap(null);
		
	}

	public String removeValidationParameter(String name) {


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

	public void setValidationParameter(String name, ValueExpression value, boolean client) {


		setValidationParameterData(name, value, client);
		
	}

	public void setDefaultDate(String date) {


			engine.setProperty(Properties.DEFAULT_DATE, date);
		
	}

	public Date getDefaultDate(FacesContext facesContext) {


			Object value=engine.getProperty(Properties.DEFAULT_DATE, facesContext);
			if (value instanceof String) {
				value=LiteralDateConverter.SINGLETON.getAsObject(facesContext, this, (String)value);
			}
			
			return (Date)value;
		
	}

	public String getValidationParameter(String name, FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}

		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return null;
		}
            
		return (String)dataMapAccessor.getData(name, facesContext);
		
	}

	public int getValidationParametersCount(FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}
		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return 0;
		}
		 
		return dataMapAccessor.getDataCount();
		
	}

	public Map getValidationParametersMap(FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}
		
		IDataMapAccessor dataMapAccessor=engine.getDataMapAccessor(facesContext, "ValidationParameter", false);
		if (dataMapAccessor==null) {
			return Collections.EMPTY_MAP;
		}
            
		return dataMapAccessor.getDataMap(facesContext);
		
	}

	public Map getClientValidationParametersMap(FacesContext facesContext) {


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

	private Object setValidationParameterData(String name, Object value, boolean client) {


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

	public boolean isClientSideValidationParameter(String name, FacesContext facesContext) {


		if (facesContext==null) {
			facesContext=getFacesContext();
		}
		
		IDataMapAccessor clientMapAccessor=engine.getDataMapAccessor(facesContext, "ClientValidationParameter", false);
		if (clientMapAccessor==null) {
			return false;
		}
		return (clientMapAccessor.getData(name, facesContext)==null);
		
	}

	public boolean isAutoTab() {
		return isAutoTab(null);
	}

	/**
	 * See {@link #isAutoTab() isAutoTab()} for more details
	 */
	public boolean isAutoTab(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.AUTO_TAB, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "autoTab" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAutoTabSetted() {
		return engine.isPropertySetted(Properties.AUTO_TAB);
	}

	public void setAutoTab(boolean autoTab) {
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

	public java.lang.String getDateFormat() {
		return getDateFormat(null);
	}

	/**
	 * See {@link #getDateFormat() getDateFormat()} for more details
	 */
	public java.lang.String getDateFormat(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DATE_FORMAT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "dateFormat" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDateFormatSetted() {
		return engine.isPropertySetted(Properties.DATE_FORMAT);
	}

	public void setDateFormat(java.lang.String dateFormat) {
		engine.setProperty(Properties.DATE_FORMAT, dateFormat);
	}

	/**
	 * Returns a boolean value indicating wether the associated <a href="/comps/dateCHooserComponent.html">dateChooser Component</a> should be automatically opened when the component gets the focus.
	 * @return true if calendar is to be shown
	 */
	public boolean isShowCalendarOnFocus() {
		return isShowCalendarOnFocus(null);
	}

	/**
	 * Returns a boolean value indicating wether the associated <a href="/comps/dateCHooserComponent.html">dateChooser Component</a> should be automatically opened when the component gets the focus.
	 * @return true if calendar is to be shown
	 */
	public boolean isShowCalendarOnFocus(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.SHOW_CALENDAR_ON_FOCUS, false, facesContext);
	}

	/**
	 * Sets a boolean value indicating wether the associated <a href="/comps/dateCHooserComponent.html">dateChooser Component</a> should be automatically opened when the component gets the focus.
	 * @param showCalendarOnFocus true if calendar is shown
	 */
	public void setShowCalendarOnFocus(boolean showCalendarOnFocus) {
		engine.setProperty(Properties.SHOW_CALENDAR_ON_FOCUS, showCalendarOnFocus);
	}

	/**
	 * Sets a boolean value indicating wether the associated <a href="/comps/dateCHooserComponent.html">dateChooser Component</a> should be automatically opened when the component gets the focus.
	 * @param showCalendarOnFocus true if calendar is shown
	 */
	/**
	 * Returns <code>true</code> if the attribute "showCalendarOnFocus" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isShowCalendarOnFocusSetted() {
		return engine.isPropertySetted(Properties.SHOW_CALENDAR_ON_FOCUS);
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
		return engine.getBoolProperty(Properties.AUTO_COMPLETION, false, facesContext);
	}

	/**
	 * Sets a boolean value indicating if the component should complete automaticaly the user entry.
	 * @param autoCompletion true if the user entry must completed
	 */
	public void setAutoCompletion(boolean autoCompletion) {
		engine.setProperty(Properties.AUTO_COMPLETION, autoCompletion);
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
		return engine.isPropertySetted(Properties.AUTO_COMPLETION);
	}

	/**
	 * Returns the date selected by default.
	 * @return default date
	 */
	public java.util.Date getDefaultDate() {
		return getDefaultDate(null);
	}

	/**
	 * Sets the date to select by default.
	 * @param defaultDate default date
	 */
	public void setDefaultDate(java.util.Date defaultDate) {
		engine.setProperty(Properties.DEFAULT_DATE, defaultDate);
	}

	/**
	 * Sets the date to select by default.
	 * @param defaultDate default date
	 */
	/**
	 * Returns <code>true</code> if the attribute "defaultDate" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDefaultDateSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_DATE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
