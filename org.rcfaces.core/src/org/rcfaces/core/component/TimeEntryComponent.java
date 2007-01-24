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
import org.rcfaces.core.component.capability.IValueChangeEventCapability;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.component.IDataMapAccessor;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.LiteralTimeConverter;
import org.rcfaces.core.internal.converter.LocaleConverter;
import org.rcfaces.core.internal.converter.TimeZoneConverter;
import org.rcfaces.core.internal.manager.IValidationParameters;
import org.rcfaces.core.model.IAdaptable;
import org.rcfaces.core.model.Time;

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
	IValidationParameters {

	public static final String COMPONENT_TYPE="org.rcfaces.core.timeEntry";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractInputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"selectionListener","minTime","autoCompletion","required","componentLocale","time","timeFormat","valueChangeListener","defaultTime","hourStep","literalTimeZone","literalLocale","readOnly","componentTimeZone","secondStep","focusStyleClass","millisStep","minuteStep","autoTab","maxTime"}));
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

	public final void setTime(ValueBinding valueBinding) {


			setValueBinding("value", valueBinding);
			
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

	public final void addSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		addFacesListener(listener);
	}

	public final void removeSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listSelectionListeners() {
		return getFacesListeners(org.rcfaces.core.event.ISelectionListener.class);
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

	public final java.util.Locale getLiteralLocale() {
		return getLiteralLocale(null);
	}

	/**
	 * See {@link #getLiteralLocale() getLiteralLocale()} for more details
	 */
	public final java.util.Locale getLiteralLocale(javax.faces.context.FacesContext facesContext) {
		return (java.util.Locale)engine.getProperty(Properties.LITERAL_LOCALE, facesContext);
	}

	public final void setLiteralLocale(java.util.Locale literalLocale) {
		engine.setProperty(Properties.LITERAL_LOCALE, literalLocale);
	}

	/**
	 * See {@link #setLiteralLocale(java.util.Locale) setLiteralLocale(java.util.Locale)} for more details
	 */
	public final void setLiteralLocale(ValueBinding literalLocale) {
		engine.setProperty(Properties.LITERAL_LOCALE, literalLocale);
	}

	public final java.util.TimeZone getLiteralTimeZone() {
		return getLiteralTimeZone(null);
	}

	/**
	 * See {@link #getLiteralTimeZone() getLiteralTimeZone()} for more details
	 */
	public final java.util.TimeZone getLiteralTimeZone(javax.faces.context.FacesContext facesContext) {
		return (java.util.TimeZone)engine.getProperty(Properties.LITERAL_TIME_ZONE, facesContext);
	}

	public final void setLiteralTimeZone(java.util.TimeZone literalTimeZone) {
		engine.setProperty(Properties.LITERAL_TIME_ZONE, literalTimeZone);
	}

	/**
	 * See {@link #setLiteralTimeZone(java.util.TimeZone) setLiteralTimeZone(java.util.TimeZone)} for more details
	 */
	public final void setLiteralTimeZone(ValueBinding literalTimeZone) {
		engine.setProperty(Properties.LITERAL_TIME_ZONE, literalTimeZone);
	}

	public final java.util.Locale getComponentLocale() {
		return getComponentLocale(null);
	}

	/**
	 * See {@link #getComponentLocale() getComponentLocale()} for more details
	 */
	public final java.util.Locale getComponentLocale(javax.faces.context.FacesContext facesContext) {
		return (java.util.Locale)engine.getProperty(Properties.COMPONENT_LOCALE, facesContext);
	}

	public final void setComponentLocale(java.util.Locale componentLocale) {
		engine.setProperty(Properties.COMPONENT_LOCALE, componentLocale);
	}

	/**
	 * See {@link #setComponentLocale(java.util.Locale) setComponentLocale(java.util.Locale)} for more details
	 */
	public final void setComponentLocale(ValueBinding componentLocale) {
		engine.setProperty(Properties.COMPONENT_LOCALE, componentLocale);
	}

	public final java.util.TimeZone getComponentTimeZone() {
		return getComponentTimeZone(null);
	}

	/**
	 * See {@link #getComponentTimeZone() getComponentTimeZone()} for more details
	 */
	public final java.util.TimeZone getComponentTimeZone(javax.faces.context.FacesContext facesContext) {
		return (java.util.TimeZone)engine.getProperty(Properties.COMPONENT_TIME_ZONE, facesContext);
	}

	public final void setComponentTimeZone(java.util.TimeZone componentTimeZone) {
		engine.setProperty(Properties.COMPONENT_TIME_ZONE, componentTimeZone);
	}

	/**
	 * See {@link #setComponentTimeZone(java.util.TimeZone) setComponentTimeZone(java.util.TimeZone)} for more details
	 */
	public final void setComponentTimeZone(ValueBinding componentTimeZone) {
		engine.setProperty(Properties.COMPONENT_TIME_ZONE, componentTimeZone);
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
		return engine.getBoolProperty(Properties.AUTO_COMPLETION, true, facesContext);
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
	 * Returns a time value indicating the minimum acceptable time for the component. The first accepted value is greater than minTime.
	 * @return min Time
	 */
	public final Time getMinTime() {
		return getMinTime(null);
	}

	/**
	 * Returns a time value indicating the minimum acceptable time for the component. The first accepted value is greater than minTime.
	 * @return min Time
	 */
	public final Time getMinTime(javax.faces.context.FacesContext facesContext) {
		return (Time)engine.getValue(Properties.MIN_TIME, facesContext);
	}

	/**
	 * Sets a time value indicating the minimum acceptable time for the component. The first accepted value is greater than minTime.
	 * @param minTime min Time
	 */
	public final void setMinTime(Time minTime) {
		engine.setProperty(Properties.MIN_TIME, minTime);
	}

	/**
	 * Sets a time value indicating the minimum acceptable time for the component. The first accepted value is greater than minTime.
	 * @param minTime min Time
	 */
	public final void setMinTime(ValueBinding minTime) {
		engine.setProperty(Properties.MIN_TIME, minTime);
	}

	/**
	 * Returns <code>true</code> if the attribute "minTime" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMinTimeSetted() {
		return engine.isPropertySetted(Properties.MIN_TIME);
	}

	/**
	 * Returns the default value.
	 * @return default value
	 */
	public final Time getDefaultTime() {
		return getDefaultTime(null);
	}

	/**
	 * Returns the default value.
	 * @return default value
	 */
	public final Time getDefaultTime(javax.faces.context.FacesContext facesContext) {
		return (Time)engine.getValue(Properties.DEFAULT_TIME, facesContext);
	}

	/**
	 * Sets the default value.
	 * @param defaultTime default value
	 */
	public final void setDefaultTime(Time defaultTime) {
		engine.setProperty(Properties.DEFAULT_TIME, defaultTime);
	}

	/**
	 * Sets the default value.
	 * @param defaultTime default value
	 */
	public final void setDefaultTime(ValueBinding defaultTime) {
		engine.setProperty(Properties.DEFAULT_TIME, defaultTime);
	}

	/**
	 * Returns <code>true</code> if the attribute "defaultTime" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDefaultTimeSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_TIME);
	}

	/**
	 * Returns a time value indicating the maximum acceptable time for the component. The last accepted value is lesser than maxTime.
	 * @return max time
	 */
	public final Time getMaxTime() {
		return getMaxTime(null);
	}

	/**
	 * Returns a time value indicating the maximum acceptable time for the component. The last accepted value is lesser than maxTime.
	 * @return max time
	 */
	public final Time getMaxTime(javax.faces.context.FacesContext facesContext) {
		return (Time)engine.getValue(Properties.MAX_TIME, facesContext);
	}

	/**
	 * Sets a time value indicating the maximum acceptable time for the component. The last accepted value is lesser than maxTime.
	 * @param maxTime max time
	 */
	public final void setMaxTime(Time maxTime) {
		engine.setProperty(Properties.MAX_TIME, maxTime);
	}

	/**
	 * Sets a time value indicating the maximum acceptable time for the component. The last accepted value is lesser than maxTime.
	 * @param maxTime max time
	 */
	public final void setMaxTime(ValueBinding maxTime) {
		engine.setProperty(Properties.MAX_TIME, maxTime);
	}

	/**
	 * Returns <code>true</code> if the attribute "maxTime" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMaxTimeSetted() {
		return engine.isPropertySetted(Properties.MAX_TIME);
	}

	public final String getTimeFormat() {
		return getTimeFormat(null);
	}

	public final String getTimeFormat(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TIME_FORMAT, facesContext);
	}

	public final void setTimeFormat(String timeFormat) {
		engine.setProperty(Properties.TIME_FORMAT, timeFormat);
	}

	public final void setTimeFormat(ValueBinding timeFormat) {
		engine.setProperty(Properties.TIME_FORMAT, timeFormat);
	}

	/**
	 * Returns <code>true</code> if the attribute "timeFormat" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTimeFormatSetted() {
		return engine.isPropertySetted(Properties.TIME_FORMAT);
	}

	/**
	 * Returns an int value specifying the step used for the hour field when increasing/decreasing the value with the up/down arrows.
	 * @return step used
	 */
	public final String getHourStep() {
		return getHourStep(null);
	}

	/**
	 * Returns an int value specifying the step used for the hour field when increasing/decreasing the value with the up/down arrows.
	 * @return step used
	 */
	public final String getHourStep(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.HOUR_STEP, facesContext);
	}

	/**
	 * Sets an int value specifying the step used for the hour field when increasing/decreasing the value with the up/down arrows.
	 * @param hourStep stop to use
	 */
	public final void setHourStep(String hourStep) {
		engine.setProperty(Properties.HOUR_STEP, hourStep);
	}

	/**
	 * Sets an int value specifying the step used for the hour field when increasing/decreasing the value with the up/down arrows.
	 * @param hourStep stop to use
	 */
	public final void setHourStep(ValueBinding hourStep) {
		engine.setProperty(Properties.HOUR_STEP, hourStep);
	}

	/**
	 * Returns <code>true</code> if the attribute "hourStep" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHourStepSetted() {
		return engine.isPropertySetted(Properties.HOUR_STEP);
	}

	/**
	 * Returns an int value specifying the step used for the minute field when increasing/decreasing the value with the up/down arrows.
	 * @return step
	 */
	public final String getMinuteStep() {
		return getMinuteStep(null);
	}

	/**
	 * Returns an int value specifying the step used for the minute field when increasing/decreasing the value with the up/down arrows.
	 * @return step
	 */
	public final String getMinuteStep(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MINUTE_STEP, facesContext);
	}

	/**
	 * Steps an int value specifying the step used for the minute field when increasing/decreasing the value with the up/down arrows.
	 * @param minuteStep step
	 */
	public final void setMinuteStep(String minuteStep) {
		engine.setProperty(Properties.MINUTE_STEP, minuteStep);
	}

	/**
	 * Steps an int value specifying the step used for the minute field when increasing/decreasing the value with the up/down arrows.
	 * @param minuteStep step
	 */
	public final void setMinuteStep(ValueBinding minuteStep) {
		engine.setProperty(Properties.MINUTE_STEP, minuteStep);
	}

	/**
	 * Returns <code>true</code> if the attribute "minuteStep" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMinuteStepSetted() {
		return engine.isPropertySetted(Properties.MINUTE_STEP);
	}

	/**
	 * Returns an int value specifying the step used for the second field when increasing/decreasing the value with the up/down arrows.
	 * @return step
	 */
	public final String getSecondStep() {
		return getSecondStep(null);
	}

	/**
	 * Returns an int value specifying the step used for the second field when increasing/decreasing the value with the up/down arrows.
	 * @return step
	 */
	public final String getSecondStep(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SECOND_STEP, facesContext);
	}

	/**
	 * Sets an int value specifying the step used for the second field when increasing/decreasing the value with the up/down arrows.
	 * @param secondStep step
	 */
	public final void setSecondStep(String secondStep) {
		engine.setProperty(Properties.SECOND_STEP, secondStep);
	}

	/**
	 * Sets an int value specifying the step used for the second field when increasing/decreasing the value with the up/down arrows.
	 * @param secondStep step
	 */
	public final void setSecondStep(ValueBinding secondStep) {
		engine.setProperty(Properties.SECOND_STEP, secondStep);
	}

	/**
	 * Returns <code>true</code> if the attribute "secondStep" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSecondStepSetted() {
		return engine.isPropertySetted(Properties.SECOND_STEP);
	}

	/**
	 * Returns an int value specifying the step used for the millisecond field when increasing/decreasing the value with the up/down arrows.
	 * @return step
	 */
	public final String getMillisStep() {
		return getMillisStep(null);
	}

	/**
	 * Returns an int value specifying the step used for the millisecond field when increasing/decreasing the value with the up/down arrows.
	 * @return step
	 */
	public final String getMillisStep(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MILLIS_STEP, facesContext);
	}

	/**
	 * Sets an int value specifying the step used for the millisecond field when increasing/decreasing the value with the up/down arrows.
	 * @param millisStep step
	 */
	public final void setMillisStep(String millisStep) {
		engine.setProperty(Properties.MILLIS_STEP, millisStep);
	}

	/**
	 * Sets an int value specifying the step used for the millisecond field when increasing/decreasing the value with the up/down arrows.
	 * @param millisStep step
	 */
	public final void setMillisStep(ValueBinding millisStep) {
		engine.setProperty(Properties.MILLIS_STEP, millisStep);
	}

	/**
	 * Returns <code>true</code> if the attribute "millisStep" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMillisStepSetted() {
		return engine.isPropertySetted(Properties.MILLIS_STEP);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}

	protected String getCameliaValueAlias() {
		return CAMELIA_VALUE_ALIAS;
	}
}
