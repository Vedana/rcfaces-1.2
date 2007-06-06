package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IAutoTabCapability;
import org.rcfaces.core.component.capability.IComponentLocaleCapability;
import org.rcfaces.core.component.capability.IComponentTimeZoneCapability;
import org.rcfaces.core.component.capability.IFocusStyleClassCapability;
import org.rcfaces.core.component.capability.ILiteralLocaleCapability;
import org.rcfaces.core.component.capability.ILiteralTimeZoneCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.component.capability.IRequiredCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.capability.ISeverityStyleClassCapability;
import org.rcfaces.core.component.capability.IValueChangeEventCapability;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.component.IDataMapAccessor;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.LiteralTimeConverter;
import org.rcfaces.core.internal.converter.LocaleConverter;
import org.rcfaces.core.internal.converter.TimeZoneConverter;
import org.rcfaces.core.internal.manager.IValidationParameters;
import org.rcfaces.core.lang.IAdaptable;
import org.rcfaces.core.lang.Time;

/**
 * <p>The timeEntry Component is a specialized <a href="/comps/textEntryComponent.html">textEntry Component</a>. it sports auto-completion related to the validity of the numbers entered as a time.</p>
 * <p>The timeEntry Component has the following capabilities :
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
public class TimeEntryComponent extends AbstractInputComponent implements 
	IRequiredCapability,
	IAutoTabCapability,
	IValueChangeEventCapability,
	IFocusStyleClassCapability,
	ISelectionEventCapability,
	IReadOnlyCapability,
	ILiteralLocaleCapability,
	ILiteralTimeZoneCapability,
	IComponentLocaleCapability,
	IComponentTimeZoneCapability,
	ISeverityStyleClassCapability,
	IValidationParameters {

	public static final String COMPONENT_TYPE="org.rcfaces.core.timeEntry";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractInputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"selectionListener","minTime","time","warnStyleClass","defaultTime","hourStep","styleClass","infoStyleClass","componentTimeZone","secondStep","millisStep","minuteStep","errorStyleClass","fatalStyleClass","autoCompletion","componentLocale","required","timeFormat","valueChangeListener","literalTimeZone","literalLocale","readOnly","focusStyleClass","autoTab","maxTime"}));
	}
	protected static final String CAMELIA_VALUE_ALIAS="time";

	public TimeEntryComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public TimeEntryComponent(String componentId) {
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

	public final Time getTime() {


				Object submittedValue=getSubmittedExternalValue();
				if (submittedValue!=null) {
					return (Time)submittedValue;
				}

				Object value=getValue();
				
				if (value==null) {
					return null;
				}
				
				if (value instanceof Time) {
					return (Time)value;
				}
				
				if (value instanceof String) {
					return (Time)LiteralTimeConverter.SINGLETON.getAsObject(null, this, (String)value);
				}

				if (value instanceof IAdaptable) {
					Time adapted=(Time)((IAdaptable)value).getAdapter(Time.class, this);
					if (adapted!=null) {
						return adapted;
					}
				}

				throw new FacesException("Value of timeEntry is not a time ! ("+value+")");
			
	}

	public final void setTime(Time time) {


				setValue(time);
			
	}

	public final void setTime(String time) {


			Time timeValue=(Time)LiteralTimeConverter.SINGLETON.getAsObject(null, this, time);
			setTime(timeValue);
		
	}

	public final void setMinTime(String time) {


			Time timeValue=(Time)LiteralTimeConverter.SINGLETON.getAsObject(null, this, time);
			setMinTime(timeValue);
		
	}

	public final void setDefaultTime(String time) {


			Time timeValue=(Time)LiteralTimeConverter.SINGLETON.getAsObject(null, this, time);
			setDefaultTime(timeValue);
		
	}

	public final void setMaxTime(String time) {


			Time timeValue=(Time)LiteralTimeConverter.SINGLETON.getAsObject(null, this, time);
			setMaxTime(timeValue);
		
	}

	public final void setLiteralLocale(String locale) {


		setLiteralLocale((Locale)LocaleConverter.SINGLETON.getAsObject(null, this, locale));
		
	}

	public final void setComponentLocale(String locale) {


		setComponentLocale((Locale)LocaleConverter.SINGLETON.getAsObject(null, this, locale));
		
	}

	public final void setLiteralTimeZone(String timeZone) {


		setLiteralTimeZone((TimeZone)TimeZoneConverter.SINGLETON.getAsObject(null, this, timeZone));
		
	}

	public final void setComponentTimeZone(String timeZone) {


		setComponentTimeZone((TimeZone)TimeZoneConverter.SINGLETON.getAsObject(null, this, timeZone));
		
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

	public final void addSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		addFacesListener(listener);
	}

	public final void removeSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listSelectionListeners() {
		return getFacesListeners(org.rcfaces.core.event.ISelectionListener.class);
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

	public java.util.Locale getLiteralLocale() {
		return getLiteralLocale(null);
	}

	/**
	 * See {@link #getLiteralLocale() getLiteralLocale()} for more details
	 */
	public java.util.Locale getLiteralLocale(javax.faces.context.FacesContext facesContext) {
		return (java.util.Locale)engine.getProperty(Properties.LITERAL_LOCALE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "literalLocale" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isLiteralLocaleSetted() {
		return engine.isPropertySetted(Properties.LITERAL_LOCALE);
	}

	public void setLiteralLocale(java.util.Locale literalLocale) {
		engine.setProperty(Properties.LITERAL_LOCALE, literalLocale);
	}

	public java.util.TimeZone getLiteralTimeZone() {
		return getLiteralTimeZone(null);
	}

	/**
	 * See {@link #getLiteralTimeZone() getLiteralTimeZone()} for more details
	 */
	public java.util.TimeZone getLiteralTimeZone(javax.faces.context.FacesContext facesContext) {
		return (java.util.TimeZone)engine.getProperty(Properties.LITERAL_TIME_ZONE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "literalTimeZone" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isLiteralTimeZoneSetted() {
		return engine.isPropertySetted(Properties.LITERAL_TIME_ZONE);
	}

	public void setLiteralTimeZone(java.util.TimeZone literalTimeZone) {
		engine.setProperty(Properties.LITERAL_TIME_ZONE, literalTimeZone);
	}

	public java.util.Locale getComponentLocale() {
		return getComponentLocale(null);
	}

	/**
	 * See {@link #getComponentLocale() getComponentLocale()} for more details
	 */
	public java.util.Locale getComponentLocale(javax.faces.context.FacesContext facesContext) {
		return (java.util.Locale)engine.getProperty(Properties.COMPONENT_LOCALE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "componentLocale" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isComponentLocaleSetted() {
		return engine.isPropertySetted(Properties.COMPONENT_LOCALE);
	}

	public void setComponentLocale(java.util.Locale componentLocale) {
		engine.setProperty(Properties.COMPONENT_LOCALE, componentLocale);
	}

	public java.util.TimeZone getComponentTimeZone() {
		return getComponentTimeZone(null);
	}

	/**
	 * See {@link #getComponentTimeZone() getComponentTimeZone()} for more details
	 */
	public java.util.TimeZone getComponentTimeZone(javax.faces.context.FacesContext facesContext) {
		return (java.util.TimeZone)engine.getProperty(Properties.COMPONENT_TIME_ZONE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "componentTimeZone" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isComponentTimeZoneSetted() {
		return engine.isPropertySetted(Properties.COMPONENT_TIME_ZONE);
	}

	public void setComponentTimeZone(java.util.TimeZone componentTimeZone) {
		engine.setProperty(Properties.COMPONENT_TIME_ZONE, componentTimeZone);
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
		return engine.getBoolProperty(Properties.AUTO_COMPLETION, true, facesContext);
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
	 * Returns a time value indicating the minimum acceptable time for the component. The first accepted value is greater than minTime.
	 * @return min Time
	 */
	public Time getMinTime() {
		return getMinTime(null);
	}

	/**
	 * Returns a time value indicating the minimum acceptable time for the component. The first accepted value is greater than minTime.
	 * @return min Time
	 */
	public Time getMinTime(javax.faces.context.FacesContext facesContext) {
		return (Time)engine.getValue(Properties.MIN_TIME, facesContext);
	}

	/**
	 * Sets a time value indicating the minimum acceptable time for the component. The first accepted value is greater than minTime.
	 * @param minTime min Time
	 */
	public void setMinTime(Time minTime) {
		engine.setProperty(Properties.MIN_TIME, minTime);
	}

	/**
	 * Sets a time value indicating the minimum acceptable time for the component. The first accepted value is greater than minTime.
	 * @param minTime min Time
	 */
	/**
	 * Returns <code>true</code> if the attribute "minTime" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isMinTimeSetted() {
		return engine.isPropertySetted(Properties.MIN_TIME);
	}

	/**
	 * Returns the default value.
	 * @return default value
	 */
	public Time getDefaultTime() {
		return getDefaultTime(null);
	}

	/**
	 * Returns the default value.
	 * @return default value
	 */
	public Time getDefaultTime(javax.faces.context.FacesContext facesContext) {
		return (Time)engine.getValue(Properties.DEFAULT_TIME, facesContext);
	}

	/**
	 * Sets the default value.
	 * @param defaultTime default value
	 */
	public void setDefaultTime(Time defaultTime) {
		engine.setProperty(Properties.DEFAULT_TIME, defaultTime);
	}

	/**
	 * Sets the default value.
	 * @param defaultTime default value
	 */
	/**
	 * Returns <code>true</code> if the attribute "defaultTime" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDefaultTimeSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_TIME);
	}

	/**
	 * Returns a time value indicating the maximum acceptable time for the component. The last accepted value is lesser than maxTime.
	 * @return max time
	 */
	public Time getMaxTime() {
		return getMaxTime(null);
	}

	/**
	 * Returns a time value indicating the maximum acceptable time for the component. The last accepted value is lesser than maxTime.
	 * @return max time
	 */
	public Time getMaxTime(javax.faces.context.FacesContext facesContext) {
		return (Time)engine.getValue(Properties.MAX_TIME, facesContext);
	}

	/**
	 * Sets a time value indicating the maximum acceptable time for the component. The last accepted value is lesser than maxTime.
	 * @param maxTime max time
	 */
	public void setMaxTime(Time maxTime) {
		engine.setProperty(Properties.MAX_TIME, maxTime);
	}

	/**
	 * Sets a time value indicating the maximum acceptable time for the component. The last accepted value is lesser than maxTime.
	 * @param maxTime max time
	 */
	/**
	 * Returns <code>true</code> if the attribute "maxTime" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isMaxTimeSetted() {
		return engine.isPropertySetted(Properties.MAX_TIME);
	}

	public String getTimeFormat() {
		return getTimeFormat(null);
	}

	public String getTimeFormat(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TIME_FORMAT, facesContext);
	}

	public void setTimeFormat(String timeFormat) {
		engine.setProperty(Properties.TIME_FORMAT, timeFormat);
	}

	/**
	 * Returns <code>true</code> if the attribute "timeFormat" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isTimeFormatSetted() {
		return engine.isPropertySetted(Properties.TIME_FORMAT);
	}

	/**
	 * Returns an int value specifying the step used for the hour field when increasing/decreasing the value with the up/down arrows.
	 * @return step used
	 */
	public String getHourStep() {
		return getHourStep(null);
	}

	/**
	 * Returns an int value specifying the step used for the hour field when increasing/decreasing the value with the up/down arrows.
	 * @return step used
	 */
	public String getHourStep(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.HOUR_STEP, facesContext);
	}

	/**
	 * Sets an int value specifying the step used for the hour field when increasing/decreasing the value with the up/down arrows.
	 * @param hourStep stop to use
	 */
	public void setHourStep(String hourStep) {
		engine.setProperty(Properties.HOUR_STEP, hourStep);
	}

	/**
	 * Sets an int value specifying the step used for the hour field when increasing/decreasing the value with the up/down arrows.
	 * @param hourStep stop to use
	 */
	/**
	 * Returns <code>true</code> if the attribute "hourStep" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isHourStepSetted() {
		return engine.isPropertySetted(Properties.HOUR_STEP);
	}

	/**
	 * Returns an int value specifying the step used for the minute field when increasing/decreasing the value with the up/down arrows.
	 * @return step
	 */
	public String getMinuteStep() {
		return getMinuteStep(null);
	}

	/**
	 * Returns an int value specifying the step used for the minute field when increasing/decreasing the value with the up/down arrows.
	 * @return step
	 */
	public String getMinuteStep(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MINUTE_STEP, facesContext);
	}

	/**
	 * Steps an int value specifying the step used for the minute field when increasing/decreasing the value with the up/down arrows.
	 * @param minuteStep step
	 */
	public void setMinuteStep(String minuteStep) {
		engine.setProperty(Properties.MINUTE_STEP, minuteStep);
	}

	/**
	 * Steps an int value specifying the step used for the minute field when increasing/decreasing the value with the up/down arrows.
	 * @param minuteStep step
	 */
	/**
	 * Returns <code>true</code> if the attribute "minuteStep" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isMinuteStepSetted() {
		return engine.isPropertySetted(Properties.MINUTE_STEP);
	}

	/**
	 * Returns an int value specifying the step used for the second field when increasing/decreasing the value with the up/down arrows.
	 * @return step
	 */
	public String getSecondStep() {
		return getSecondStep(null);
	}

	/**
	 * Returns an int value specifying the step used for the second field when increasing/decreasing the value with the up/down arrows.
	 * @return step
	 */
	public String getSecondStep(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SECOND_STEP, facesContext);
	}

	/**
	 * Sets an int value specifying the step used for the second field when increasing/decreasing the value with the up/down arrows.
	 * @param secondStep step
	 */
	public void setSecondStep(String secondStep) {
		engine.setProperty(Properties.SECOND_STEP, secondStep);
	}

	/**
	 * Sets an int value specifying the step used for the second field when increasing/decreasing the value with the up/down arrows.
	 * @param secondStep step
	 */
	/**
	 * Returns <code>true</code> if the attribute "secondStep" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSecondStepSetted() {
		return engine.isPropertySetted(Properties.SECOND_STEP);
	}

	/**
	 * Returns an int value specifying the step used for the millisecond field when increasing/decreasing the value with the up/down arrows.
	 * @return step
	 */
	public String getMillisStep() {
		return getMillisStep(null);
	}

	/**
	 * Returns an int value specifying the step used for the millisecond field when increasing/decreasing the value with the up/down arrows.
	 * @return step
	 */
	public String getMillisStep(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MILLIS_STEP, facesContext);
	}

	/**
	 * Sets an int value specifying the step used for the millisecond field when increasing/decreasing the value with the up/down arrows.
	 * @param millisStep step
	 */
	public void setMillisStep(String millisStep) {
		engine.setProperty(Properties.MILLIS_STEP, millisStep);
	}

	/**
	 * Sets an int value specifying the step used for the millisecond field when increasing/decreasing the value with the up/down arrows.
	 * @param millisStep step
	 */
	/**
	 * Returns <code>true</code> if the attribute "millisStep" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isMillisStepSetted() {
		return engine.isPropertySetted(Properties.MILLIS_STEP);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}

	protected String getCameliaValueAlias() {
		return CAMELIA_VALUE_ALIAS;
	}
}
