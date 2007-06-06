package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IValueChangeEventCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.INumberFormatTypeCapability;
import java.lang.Object;
import java.util.Collections;
import org.rcfaces.core.component.capability.IAutoTabCapability;
import java.util.Arrays;
import org.rcfaces.core.internal.converter.NumberFormatTypeConverter;
import org.rcfaces.core.internal.component.IDataMapAccessor;
import org.rcfaces.core.component.capability.ILiteralLocaleCapability;
import org.rcfaces.core.component.AbstractInputComponent;
import org.rcfaces.core.component.capability.IFocusStyleClassCapability;
import org.rcfaces.core.component.capability.IRequiredCapability;
import java.lang.String;
import org.rcfaces.core.component.capability.ISeverityStyleClassCapability;
import java.util.Map;
import javax.faces.context.FacesContext;
import java.util.HashMap;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import javax.faces.el.ValueBinding;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.internal.manager.IValidationParameters;
import java.util.Locale;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.converter.LocaleConverter;
import org.rcfaces.core.internal.converter.LiteralNumberConverter;
import org.rcfaces.core.component.capability.IComponentLocaleCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;

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
	IValidationParameters {

	public static final String COMPONENT_TYPE="org.rcfaces.core.numberEntry";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractInputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"selectionListener","errorStyleClass","numberFormat","autoCompletion","fatalStyleClass","minimum","required","componentLocale","defaultNumber","numberFormatType","valueChangeListener","integerStep","fractionStep","integerDigits","warnStyleClass","maximum","styleClass","literalLocale","infoStyleClass","fractionDigits","readOnly","focusStyleClass","autoTab","number"}));
	}
	protected static final String CAMELIA_VALUE_ALIAS="number";

	public NumberEntryComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public NumberEntryComponent(String componentId) {
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

	public final void setValue(Object value) {


				if (value instanceof String) {
					value=LiteralNumberConverter.SINGLETON.getAsObject(null, this, (String)value);
				}
				
				super.setValue(value);
			
	}

	public final void setNumberFormatType(String formatType) {


			setNumberFormatType(((Integer)NumberFormatTypeConverter.SINGLETON.getAsObject(null, this, formatType)).intValue());
			
	}

	public final Number getNumber() {


				Object submittedValue=getSubmittedExternalValue();
				if (submittedValue!=null) {
					return (Number)submittedValue;
				}
			
				return (Number)getValue();
			
	}

	public final void setNumber(double number) {


				setValue(new Double(number));
			
	}

	public final void setNumber(long number) {


				setValue(new Long(number));
			
	}

	public final void setNumber(String number) {


				setValue(number);
			
	}

	public final void setMinimum(String number) {


			Number numberObject=(Number)LiteralNumberConverter.SINGLETON.getAsObject(null, this, number);
			setMinimum(numberObject);
		
	}

	public final void setDefaultNumber(String number) {


			Number numberObject=(Number)LiteralNumberConverter.SINGLETON.getAsObject(null, this, number);
			setDefaultNumber(numberObject);
		
	}

	public final void setMaximum(String number) {


			Number numberObject=(Number)LiteralNumberConverter.SINGLETON.getAsObject(null, this, number);
			setMaximum(numberObject);
		
	}

	public final void setLiteralLocale(String locale) {


		setLiteralLocale((Locale)LocaleConverter.SINGLETON.getAsObject(null, this, locale));
		
	}

	public final void setComponentLocale(String locale) {


		setComponentLocale((Locale)LocaleConverter.SINGLETON.getAsObject(null, this, locale));
		
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

	public int getNumberFormatType() {
		return getNumberFormatType(null);
	}

	/**
	 * See {@link #getNumberFormatType() getNumberFormatType()} for more details
	 */
	public int getNumberFormatType(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.NUMBER_FORMAT_TYPE,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "numberFormatType" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isNumberFormatTypeSetted() {
		return engine.isPropertySetted(Properties.NUMBER_FORMAT_TYPE);
	}

	public void setNumberFormatType(int numberFormatType) {
		engine.setProperty(Properties.NUMBER_FORMAT_TYPE, numberFormatType);
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
		return engine.getIntProperty(Properties.INTEGER_DIGITS, 0, facesContext);
	}

	/**
	 * Sets an int value indicating the number of digits to show before the decimal separator.
	 * @param integerDigits number of integer digits
	 */
	public void setIntegerDigits(int integerDigits) {
		engine.setProperty(Properties.INTEGER_DIGITS, integerDigits);
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
		return engine.isPropertySetted(Properties.INTEGER_DIGITS);
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
		return engine.getIntProperty(Properties.FRACTION_DIGITS, 0, facesContext);
	}

	/**
	 * Sets an int value indicating the number of digits to show after the decimal separator.
	 * @param fractionDigits number of fraction digits
	 */
	public void setFractionDigits(int fractionDigits) {
		engine.setProperty(Properties.FRACTION_DIGITS, fractionDigits);
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
		return engine.isPropertySetted(Properties.FRACTION_DIGITS);
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
		return engine.getStringProperty(Properties.NUMBER_FORMAT, facesContext);
	}

	/**
	 * Sets a string value specifying the format to use (compliant with java format).
	 * @param numberFormat number format
	 */
	public void setNumberFormat(String numberFormat) {
		engine.setProperty(Properties.NUMBER_FORMAT, numberFormat);
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
		return engine.isPropertySetted(Properties.NUMBER_FORMAT);
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
		return engine.getStringProperty(Properties.INTEGER_STEP, facesContext);
	}

	/**
	 * Sets an int value specifying the step used for the integer field when increasing/decreasing the value with the up/down arrows.
	 * @param integerStep step
	 */
	public void setIntegerStep(String integerStep) {
		engine.setProperty(Properties.INTEGER_STEP, integerStep);
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
		return engine.isPropertySetted(Properties.INTEGER_STEP);
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
		return engine.getStringProperty(Properties.FRACTION_STEP, facesContext);
	}

	/**
	 * Experimental Do not use.
	 */
	public void setFractionStep(String fractionStep) {
		engine.setProperty(Properties.FRACTION_STEP, fractionStep);
	}

	/**
	 * Experimental Do not use.
	 */
	/**
	 * Returns <code>true</code> if the attribute "fractionStep" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isFractionStepSetted() {
		return engine.isPropertySetted(Properties.FRACTION_STEP);
	}

	public Number getMinimum() {
		return getMinimum(null);
	}

	public Number getMinimum(javax.faces.context.FacesContext facesContext) {
		return (Number)engine.getValue(Properties.MINIMUM, facesContext);
	}

	public void setMinimum(Number minimum) {
		engine.setProperty(Properties.MINIMUM, minimum);
	}

	/**
	 * Returns <code>true</code> if the attribute "minimum" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isMinimumSetted() {
		return engine.isPropertySetted(Properties.MINIMUM);
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
		return (Number)engine.getValue(Properties.DEFAULT_NUMBER, facesContext);
	}

	/**
	 * Sets the default value.
	 * @param defaultNumber default value
	 */
	public void setDefaultNumber(Number defaultNumber) {
		engine.setProperty(Properties.DEFAULT_NUMBER, defaultNumber);
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
		return engine.isPropertySetted(Properties.DEFAULT_NUMBER);
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
		return (Number)engine.getValue(Properties.MAXIMUM, facesContext);
	}

	/**
	 * Sets an int value specifying the upper limit for the value of the component.
	 * @param maximum maximum
	 */
	public void setMaximum(Number maximum) {
		engine.setProperty(Properties.MAXIMUM, maximum);
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
		return engine.isPropertySetted(Properties.MAXIMUM);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}

	protected String getCameliaValueAlias() {
		return CAMELIA_VALUE_ALIAS;
	}
}
