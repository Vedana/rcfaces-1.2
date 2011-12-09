package org.rcfaces.core.component;

import java.util.TimeZone;
import org.rcfaces.core.internal.converter.LiteralDateConverter;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.WeekDaysConverter;
import org.rcfaces.core.lang.IAdaptable;
import org.rcfaces.core.component.capability.IComponentLocaleCapability;
import org.rcfaces.core.component.capability.ILiteralLocaleCapability;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IComponentTimeZoneCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.converter.TimeZoneConverter;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import javax.faces.FacesException;
import org.apache.commons.logging.Log;
import org.rcfaces.core.internal.converter.LocaleConverter;
import java.util.Locale;
import java.util.Set;
import java.util.Collection;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.capability.IClientDatesStrategyCapability;
import org.rcfaces.core.internal.converter.LiteralTwoDigitYearConverter;
import org.rcfaces.core.internal.converter.ClientDatesStrategyConverter;
import java.util.Date;
import javax.el.ValueExpression;
import org.rcfaces.core.component.capability.ILiteralTimeZoneCapability;
import java.util.HashSet;
import java.util.Arrays;
import org.rcfaces.core.component.AbstractInputComponent;

/**
 * Technical component, used as a basis for building new RCFaces components.
 */
public abstract class AbstractCalendarComponent extends AbstractInputComponent implements 
	ISelectionEventCapability,
	IReadOnlyCapability,
	ILiteralLocaleCapability,
	ILiteralTimeZoneCapability,
	IComponentLocaleCapability,
	IComponentTimeZoneCapability,
	IClientDatesStrategyCapability {

	private static final Log LOG = LogFactory.getLog(AbstractCalendarComponent.class);

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractInputComponent.BEHAVIOR_EVENT_NAMES;


	public Date getDate() {


				Object submittedValue=getSubmittedExternalValue();
				if (submittedValue!=null) {
					return (Date)submittedValue;
				}
			
				Object value=getValue();

				if (value==null) {
					return null;
				}			

				if (value instanceof Date) {
					return (Date)value;
				}
								
				if (value instanceof String) {
					return (Date)LiteralDateConverter.SINGLETON.getAsObject(null, this, (String)value);
				}				

				if (value instanceof IAdaptable) {
					Date adapted=(Date)((IAdaptable)value).getAdapter(Date.class, this);
					if (adapted!=null) {
						return adapted;
					}
				}


				throw new FacesException("Value of AbstractCalendar is not a date ! ("+value+")");
			
	}

	public void setDate(Date date) {


				setValue(date);
			
	}

	public void setTwoDigitYearStart(String date) {


			getStateHelper().put(Properties.TWO_DIGIT_YEAR_START, date);
		
	}

	public Date getTwoDigitYearStart(FacesContext facesContext) {


			Object value=getStateHelper().eval(Properties.TWO_DIGIT_YEAR_START, facesContext);
			if (value instanceof String) {
				value=LiteralTwoDigitYearConverter.SINGLETON.getAsObject(facesContext, this, (String)value);
			}
			
			return (Date)value;
		
	}

	public void setClientDatesStrategy(String strategy) {


			setClientDatesStrategy(((Integer)ClientDatesStrategyConverter.SINGLETON.getAsObject(null, this, strategy)).intValue());
		
	}

	public void setMinDate(String date) {


			getStateHelper().put(Properties.MIN_DATE, date);
		
	}

	public Date getMinDate(FacesContext facesContext) {


			Object value=getStateHelper().eval(Properties.MIN_DATE, facesContext);
			if (value instanceof String) {
				value=LiteralDateConverter.SINGLETON.getAsObject(facesContext, this, (String)value);
			}
			
			return (Date)value;
		
	}

	public void setMaxDate(String date) {


			getStateHelper().put(Properties.MAX_DATE, date);
		
	}

	public Date getMaxDate(FacesContext facesContext) {


			Object value=getStateHelper().eval(Properties.MAX_DATE, facesContext);
			if (value instanceof String) {
				value=LiteralDateConverter.SINGLETON.getAsObject(facesContext, this, (String)value);
			}
			
			return (Date)value;
		
	}

	public void setLiteralLocale(String locale) {


		setLiteralLocale((Locale)LocaleConverter.SINGLETON.getAsObject(null, this, locale));
		
	}

	public void setComponentLocale(String locale) {


		setComponentLocale((Locale)LocaleConverter.SINGLETON.getAsObject(null, this, locale));
		
	}

	public void setLiteralTimeZone(String timeZone) {


		setLiteralTimeZone((TimeZone)TimeZoneConverter.SINGLETON.getAsObject(null, this, timeZone));
		
	}

	public void setComponentTimeZone(String timeZone) {


		setComponentTimeZone((TimeZone)TimeZoneConverter.SINGLETON.getAsObject(null, this, timeZone));
		
	}

	public void setCursorDate(String date) {


			getStateHelper().put(Properties.CURSOR_DATE, date);
		
	}

	public Date getCursorDate(FacesContext facesContext) {


			Object value=getStateHelper().eval(Properties.CURSOR_DATE, facesContext);
			if (value instanceof String) {
				value=LiteralDateConverter.SINGLETON.getAsObject(facesContext, this, (String)value);
			}
			
			return (Date)value;
		
	}

	public void setDisabledWeekDays(String disabledWeekDays) {


		setDisabledWeekDays(((Integer)WeekDaysConverter.SINGLETON.getAsObject(null, this, disabledWeekDays)).intValue());
		
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

	public java.util.Locale getLiteralLocale() {
		return getLiteralLocale(null);
	}

	/**
	 * See {@link #getLiteralLocale() getLiteralLocale()} for more details
	 */
	public java.util.Locale getLiteralLocale(javax.faces.context.FacesContext facesContext) {
		return (java.util.Locale)getStateHelper().eval(Properties.LITERAL_LOCALE);
	}

	/**
	 * Returns <code>true</code> if the attribute "literalLocale" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isLiteralLocaleSetted() {
		return getStateHelper().get(Properties.LITERAL_LOCALE)!=null;
	}

	public void setLiteralLocale(java.util.Locale literalLocale) {
		getStateHelper().put(Properties.LITERAL_LOCALE, literalLocale);
	}

	public java.util.TimeZone getLiteralTimeZone() {
		return getLiteralTimeZone(null);
	}

	/**
	 * See {@link #getLiteralTimeZone() getLiteralTimeZone()} for more details
	 */
	public java.util.TimeZone getLiteralTimeZone(javax.faces.context.FacesContext facesContext) {
		return (java.util.TimeZone)getStateHelper().eval(Properties.LITERAL_TIME_ZONE);
	}

	/**
	 * Returns <code>true</code> if the attribute "literalTimeZone" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isLiteralTimeZoneSetted() {
		return getStateHelper().get(Properties.LITERAL_TIME_ZONE)!=null;
	}

	public void setLiteralTimeZone(java.util.TimeZone literalTimeZone) {
		getStateHelper().put(Properties.LITERAL_TIME_ZONE, literalTimeZone);
	}

	public java.util.Locale getComponentLocale() {
		return getComponentLocale(null);
	}

	/**
	 * See {@link #getComponentLocale() getComponentLocale()} for more details
	 */
	public java.util.Locale getComponentLocale(javax.faces.context.FacesContext facesContext) {
		return (java.util.Locale)getStateHelper().eval(Properties.COMPONENT_LOCALE);
	}

	/**
	 * Returns <code>true</code> if the attribute "componentLocale" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isComponentLocaleSetted() {
		return getStateHelper().get(Properties.COMPONENT_LOCALE)!=null;
	}

	public void setComponentLocale(java.util.Locale componentLocale) {
		getStateHelper().put(Properties.COMPONENT_LOCALE, componentLocale);
	}

	public java.util.TimeZone getComponentTimeZone() {
		return getComponentTimeZone(null);
	}

	/**
	 * See {@link #getComponentTimeZone() getComponentTimeZone()} for more details
	 */
	public java.util.TimeZone getComponentTimeZone(javax.faces.context.FacesContext facesContext) {
		return (java.util.TimeZone)getStateHelper().eval(Properties.COMPONENT_TIME_ZONE);
	}

	/**
	 * Returns <code>true</code> if the attribute "componentTimeZone" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isComponentTimeZoneSetted() {
		return getStateHelper().get(Properties.COMPONENT_TIME_ZONE)!=null;
	}

	public void setComponentTimeZone(java.util.TimeZone componentTimeZone) {
		getStateHelper().put(Properties.COMPONENT_TIME_ZONE, componentTimeZone);
	}

	public int getClientDatesStrategy() {
		return getClientDatesStrategy(null);
	}

	/**
	 * See {@link #getClientDatesStrategy() getClientDatesStrategy()} for more details
	 */
	public int getClientDatesStrategy(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.CLIENT_DATES_STRATEGY, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "clientDatesStrategy" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isClientDatesStrategySetted() {
		return getStateHelper().get(Properties.CLIENT_DATES_STRATEGY)!=null;
	}

	public void setClientDatesStrategy(int clientDatesStrategy) {
		getStateHelper().put(Properties.CLIENT_DATES_STRATEGY, clientDatesStrategy);
	}

	/**
	 * Returns a string value specifying the year considered as base for selecting a date when the associated dateEntry's value's year has only two digits.
	 * @return pivot year
	 */
	public java.util.Date getTwoDigitYearStart() {
		return getTwoDigitYearStart(null);
	}

	/**
	 * Sets a string value specifying the year considered as base for selecting a date when the associated dateEntry's value's year has only two digits.
	 * @param twoDigitYearStart pivot year
	 */
	public void setTwoDigitYearStart(java.util.Date twoDigitYearStart) {
		 getStateHelper().put(Properties.TWO_DIGIT_YEAR_START, twoDigitYearStart);
	}

	/**
	 * Sets a string value specifying the year considered as base for selecting a date when the associated dateEntry's value's year has only two digits.
	 * @param twoDigitYearStart pivot year
	 */
	/**
	 * Returns <code>true</code> if the attribute "twoDigitYearStart" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isTwoDigitYearStartSetted() {
		return getStateHelper().get(Properties.TWO_DIGIT_YEAR_START)!=null;
	}

	/**
	 * Returns a date value indicating the minimum acceptable date for the component. the first accepted value is minDate plus one day.
	 * @return min date
	 */
	public java.util.Date getMinDate() {
		return getMinDate(null);
	}

	/**
	 * Sets a date value indicating the minimum acceptable date for the component. the first accepted value is minDate plus one day.
	 * @param minDate min date
	 */
	public void setMinDate(java.util.Date minDate) {
		 getStateHelper().put(Properties.MIN_DATE, minDate);
	}

	/**
	 * Sets a date value indicating the minimum acceptable date for the component. the first accepted value is minDate plus one day.
	 * @param minDate min date
	 */
	/**
	 * Returns <code>true</code> if the attribute "minDate" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isMinDateSetted() {
		return getStateHelper().get(Properties.MIN_DATE)!=null;
	}

	/**
	 * Returns a date value indicating the maximum acceptable date for the component. The last accepted value is maxDate minus one day.
	 * @return max date
	 */
	public java.util.Date getMaxDate() {
		return getMaxDate(null);
	}

	/**
	 * Sets a date value indicating the maximum acceptable date for the component. The last accepted value is maxDate minus one day.
	 * @param maxDate max date
	 */
	public void setMaxDate(java.util.Date maxDate) {
		 getStateHelper().put(Properties.MAX_DATE, maxDate);
	}

	/**
	 * Sets a date value indicating the maximum acceptable date for the component. The last accepted value is maxDate minus one day.
	 * @param maxDate max date
	 */
	/**
	 * Returns <code>true</code> if the attribute "maxDate" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isMaxDateSetted() {
		return getStateHelper().get(Properties.MAX_DATE)!=null;
	}

	public java.util.Date getCursorDate() {
		return getCursorDate(null);
	}

	public void setCursorDate(java.util.Date cursorDate) {
		 getStateHelper().put(Properties.CURSOR_DATE, cursorDate);
	}

	/**
	 * Returns <code>true</code> if the attribute "cursorDate" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isCursorDateSetted() {
		return getStateHelper().get(Properties.CURSOR_DATE)!=null;
	}

	/**
	 * Returns a list of disabled week days (depending on the locale specified for the component).
	 * @return list of days
	 */
	public int getDisabledWeekDays() {
		return getDisabledWeekDays(null);
	}

	/**
	 * Returns a list of disabled week days (depending on the locale specified for the component).
	 * @return list of days
	 */
	public int getDisabledWeekDays(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.DISABLED_WEEK_DAYS, 0);
	}

	/**
	 * Sets a list of disabled week days (depending on the locale specified for the component).
	 * @param disabledWeekDays list of days
	 */
	public void setDisabledWeekDays(int disabledWeekDays) {
		 getStateHelper().put(Properties.DISABLED_WEEK_DAYS, disabledWeekDays);
	}

	/**
	 * Sets a list of disabled week days (depending on the locale specified for the component).
	 * @param disabledWeekDays list of days
	 */
	/**
	 * Returns <code>true</code> if the attribute "disabledWeekDays" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDisabledWeekDaysSetted() {
		return getStateHelper().get(Properties.DISABLED_WEEK_DAYS)!=null;
	}

}
