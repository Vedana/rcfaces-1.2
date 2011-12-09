package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import java.util.Map;
import org.rcfaces.core.component.capability.IComponentLocaleCapability;
import java.util.Collections;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.ILiteralLocaleCapability;
import org.rcfaces.core.internal.converter.NumberFormatTypeConverter;
import org.rcfaces.core.component.capability.IRequiredCapability;
import java.util.HashMap;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.component.capability.IFocusStyleClassCapability;
import org.rcfaces.core.component.capability.INumberFormatTypeCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.internal.converter.LocaleConverter;
import org.apache.commons.logging.Log;
import java.util.Locale;
import java.util.Set;
import org.rcfaces.core.component.capability.IValueChangeEventCapability;
import java.util.Collection;
import org.rcfaces.core.component.capability.ISeverityStyleClassCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import java.lang.Object;
import java.lang.String;
import org.rcfaces.core.internal.converter.LiteralNumberConverter;
import org.rcfaces.core.component.capability.IAlternateTextCapability;
import org.rcfaces.core.internal.component.IDataMapAccessor;
import java.lang.Number;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IAutoTabCapability;
import java.util.Arrays;
import org.rcfaces.core.internal.manager.IValidationParameters;
import org.rcfaces.core.component.AbstractInputComponent;

/**
 * <b>NOT COMPLETE</b>
 */
public class NumberEntryComponent extends AbstractInputComponent implements 
	IRequiredCapability,
	IAutoTabCapability,
	IValueChangeEventCapability,
	IFocusStyleClassCapability,
	ISelectionEventCapability,
	IReadOnlyCapability,
	INumberFormatTypeCapability,
	ILiteralLocaleCapability,
	IComponentLocaleCapability,
	ISeverityStyleClassCapability,
	IAlternateTextCapability,
	IValidationParameters {

	private static final Log LOG = LogFactory.getLog(NumberEntryComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.numberEntry";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractInputComponent.BEHAVIOR_EVENT_NAMES;

	public NumberEntryComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public NumberEntryComponent(String componentId) {
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

	public void setValue(Object value) {


				if (value instanceof String) {
					value=LiteralNumberConverter.SINGLETON.getAsObject(null, this, (String)value);
				}
				
				super.setValue(value);
			
	}

	public void setNumberFormatType(String formatType) {


			setNumberFormatType(((Integer)NumberFormatTypeConverter.SINGLETON.getAsObject(null, this, formatType)).intValue());
			
	}

	public Number getNumber() {


				Object submittedValue=getSubmittedExternalValue();
				if (submittedValue!=null) {
					return (Number)submittedValue;
				}
			
				return (Number)getValue();
			
	}

	public void setNumber(double number) {


				setValue(new Double(number));
			
	}

	public void setNumber(long number) {


				setValue(new Long(number));
			
	}

	public void setNumber(String number) {


				setValue(number);
			
	}

	public void setMinimum(String number) {


			Number numberObject=(Number)LiteralNumberConverter.SINGLETON.getAsObject(null, this, number);
			setMinimum(numberObject);
		
	}

	public void setDefaultNumber(String number) {


			Number numberObject=(Number)LiteralNumberConverter.SINGLETON.getAsObject(null, this, number);
			setDefaultNumber(numberObject);
		
	}

	public void setMaximum(String number) {


			Number numberObject=(Number)LiteralNumberConverter.SINGLETON.getAsObject(null, this, number);
			setMaximum(numberObject);
		
	}

	public void setLiteralLocale(String locale) {


		setLiteralLocale((Locale)LocaleConverter.SINGLETON.getAsObject(null, this, locale));
		
	}

	public void setComponentLocale(String locale) {


		setComponentLocale((Locale)LocaleConverter.SINGLETON.getAsObject(null, this, locale));
		
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

	public int getNumberFormatType() {
		return getNumberFormatType(null);
	}

	/**
	 * See {@link #getNumberFormatType() getNumberFormatType()} for more details
	 */
	public int getNumberFormatType(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.NUMBER_FORMAT_TYPE, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "numberFormatType" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isNumberFormatTypeSetted() {
		return getStateHelper().get(Properties.NUMBER_FORMAT_TYPE)!=null;
	}

	public void setNumberFormatType(int numberFormatType) {
		getStateHelper().put(Properties.NUMBER_FORMAT_TYPE, numberFormatType);
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

	/**
	 * Returns an int value indicating the number of digits to show before the decimal separator.
	 * @return number of integer digits
	 */
	public int getIntegerDigits() {
		return getIntegerDigits(null);
	}

	/**
	 * Returns an int value indicating the number of digits to show before the decimal separator.
	 * @return number of integer digits
	 */
	public int getIntegerDigits(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.INTEGER_DIGITS, 0);
	}

	/**
	 * Sets an int value indicating the number of digits to show before the decimal separator.
	 * @param integerDigits number of integer digits
	 */
	public void setIntegerDigits(int integerDigits) {
		 getStateHelper().put(Properties.INTEGER_DIGITS, integerDigits);
	}

	/**
	 * Sets an int value indicating the number of digits to show before the decimal separator.
	 * @param integerDigits number of integer digits
	 */
	/**
	 * Returns <code>true</code> if the attribute "integerDigits" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isIntegerDigitsSetted() {
		return getStateHelper().get(Properties.INTEGER_DIGITS)!=null;
	}

	/**
	 * Returns an int value indicating the number of digits to show after the decimal separator.
	 * @return number of fraction digits
	 */
	public int getFractionDigits() {
		return getFractionDigits(null);
	}

	/**
	 * Returns an int value indicating the number of digits to show after the decimal separator.
	 * @return number of fraction digits
	 */
	public int getFractionDigits(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.FRACTION_DIGITS, 0);
	}

	/**
	 * Sets an int value indicating the number of digits to show after the decimal separator.
	 * @param fractionDigits number of fraction digits
	 */
	public void setFractionDigits(int fractionDigits) {
		 getStateHelper().put(Properties.FRACTION_DIGITS, fractionDigits);
	}

	/**
	 * Sets an int value indicating the number of digits to show after the decimal separator.
	 * @param fractionDigits number of fraction digits
	 */
	/**
	 * Returns <code>true</code> if the attribute "fractionDigits" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isFractionDigitsSetted() {
		return getStateHelper().get(Properties.FRACTION_DIGITS)!=null;
	}

	/**
	 * Returns a string value specifying the format to use (compliant with java format).
	 * @return number format
	 */
	public String getNumberFormat() {
		return getNumberFormat(null);
	}

	/**
	 * Returns a string value specifying the format to use (compliant with java format).
	 * @return number format
	 */
	public String getNumberFormat(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.NUMBER_FORMAT);
	}

	/**
	 * Sets a string value specifying the format to use (compliant with java format).
	 * @param numberFormat number format
	 */
	public void setNumberFormat(String numberFormat) {
		 getStateHelper().put(Properties.NUMBER_FORMAT, numberFormat);
	}

	/**
	 * Sets a string value specifying the format to use (compliant with java format).
	 * @param numberFormat number format
	 */
	/**
	 * Returns <code>true</code> if the attribute "numberFormat" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isNumberFormatSetted() {
		return getStateHelper().get(Properties.NUMBER_FORMAT)!=null;
	}

	/**
	 * Returns an int value specifying the step used for the integer field when increasing/decreasing the value with the up/down arrows.
	 * @return step
	 */
	public String getIntegerStep() {
		return getIntegerStep(null);
	}

	/**
	 * Returns an int value specifying the step used for the integer field when increasing/decreasing the value with the up/down arrows.
	 * @return step
	 */
	public String getIntegerStep(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.INTEGER_STEP);
	}

	/**
	 * Sets an int value specifying the step used for the integer field when increasing/decreasing the value with the up/down arrows.
	 * @param integerStep step
	 */
	public void setIntegerStep(String integerStep) {
		 getStateHelper().put(Properties.INTEGER_STEP, integerStep);
	}

	/**
	 * Sets an int value specifying the step used for the integer field when increasing/decreasing the value with the up/down arrows.
	 * @param integerStep step
	 */
	/**
	 * Returns <code>true</code> if the attribute "integerStep" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isIntegerStepSetted() {
		return getStateHelper().get(Properties.INTEGER_STEP)!=null;
	}

	/**
	 * Experimental Do not use.
	 */
	public String getFractionStep() {
		return getFractionStep(null);
	}

	/**
	 * Experimental Do not use.
	 */
	public String getFractionStep(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.FRACTION_STEP);
	}

	/**
	 * Experimental Do not use.
	 */
	public void setFractionStep(String fractionStep) {
		 getStateHelper().put(Properties.FRACTION_STEP, fractionStep);
	}

	/**
	 * Experimental Do not use.
	 */
	/**
	 * Returns <code>true</code> if the attribute "fractionStep" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isFractionStepSetted() {
		return getStateHelper().get(Properties.FRACTION_STEP)!=null;
	}

	public Number getMinimum() {
		return getMinimum(null);
	}

	public Number getMinimum(javax.faces.context.FacesContext facesContext) {
		return (Number)getStateHelper().eval(Properties.MINIMUM);
	}

	public void setMinimum(Number minimum) {
		 getStateHelper().put(Properties.MINIMUM, minimum);
	}

	/**
	 * Returns <code>true</code> if the attribute "minimum" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isMinimumSetted() {
		return getStateHelper().get(Properties.MINIMUM)!=null;
	}

	/**
	 * Returns the default value.
	 * @return default value
	 */
	public Number getDefaultNumber() {
		return getDefaultNumber(null);
	}

	/**
	 * Returns the default value.
	 * @return default value
	 */
	public Number getDefaultNumber(javax.faces.context.FacesContext facesContext) {
		return (Number)getStateHelper().eval(Properties.DEFAULT_NUMBER);
	}

	/**
	 * Sets the default value.
	 * @param defaultNumber default value
	 */
	public void setDefaultNumber(Number defaultNumber) {
		 getStateHelper().put(Properties.DEFAULT_NUMBER, defaultNumber);
	}

	/**
	 * Sets the default value.
	 * @param defaultNumber default value
	 */
	/**
	 * Returns <code>true</code> if the attribute "defaultNumber" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDefaultNumberSetted() {
		return getStateHelper().get(Properties.DEFAULT_NUMBER)!=null;
	}

	/**
	 * Returns an int value specifying the upper limit for the value of the component.
	 * @return maximum
	 */
	public Number getMaximum() {
		return getMaximum(null);
	}

	/**
	 * Returns an int value specifying the upper limit for the value of the component.
	 * @return maximum
	 */
	public Number getMaximum(javax.faces.context.FacesContext facesContext) {
		return (Number)getStateHelper().eval(Properties.MAXIMUM);
	}

	/**
	 * Sets an int value specifying the upper limit for the value of the component.
	 * @param maximum maximum
	 */
	public void setMaximum(Number maximum) {
		 getStateHelper().put(Properties.MAXIMUM, maximum);
	}

	/**
	 * Sets an int value specifying the upper limit for the value of the component.
	 * @param maximum maximum
	 */
	/**
	 * Returns <code>true</code> if the attribute "maximum" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isMaximumSetted() {
		return getStateHelper().get(Properties.MAXIMUM)!=null;
	}

}
