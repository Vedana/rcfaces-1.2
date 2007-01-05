package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IValueChangeEventCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.INumberFormatTypeCapability;
import java.lang.Object;
import org.rcfaces.core.component.capability.IAutoTabCapability;
import java.util.Arrays;
import java.util.Collections;
import org.rcfaces.core.internal.converter.NumberFormatTypeConverter;
import org.rcfaces.core.internal.component.IDataMapAccessor;
import org.rcfaces.core.component.capability.ILiteralLocaleCapability;
import org.rcfaces.core.component.AbstractInputComponent;
import org.rcfaces.core.component.capability.IFocusStyleClassCapability;
import org.rcfaces.core.component.capability.IRequiredCapability;
import java.lang.String;
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
	IValidationParameters {

	public static final String COMPONENT_TYPE="org.rcfaces.core.numberEntry";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractInputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"selectionListener","autoCompletion","numberFormat","minimum","required","componentLocale","defaultNumber","numberFormatType","valueChangeListener","integerStep","fractionStep","integerDigits","maximum","literalLocale","fractionDigits","readOnly","focusStyleClass","autoTab","number"}));
	}

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


				return (Number)getValue();
			
	}

	public final void setNumber(double number) {


				setValue(new Double(number));
			
	}

	public final void setNumber(long number) {


				setValue(new Long(number));
			
	}

	public final void setNumber(ValueBinding valueBinding) {


			setValueBinding("value", valueBinding);
			
	}

	public final void setNumber(String number) {


				setValue(number);
			
	}

	public final void setMinimum(String number) {


			FacesContext facesContext=FacesContext.getCurrentInstance();
			Number numberObject=(Number)LiteralNumberConverter.SINGLETON.getAsObject(facesContext, this, number);
			setMinimum(numberObject);
		
	}

	public final void setDefaultNumber(String number) {


			FacesContext facesContext=FacesContext.getCurrentInstance();
			Number numberObject=(Number)LiteralNumberConverter.SINGLETON.getAsObject(facesContext, this, number);
			setDefaultNumber(numberObject);
		
	}

	public final void setMaximum(String number) {


			FacesContext facesContext=FacesContext.getCurrentInstance();
			Number numberObject=(Number)LiteralNumberConverter.SINGLETON.getAsObject(facesContext, this, number);
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

	public final int getNumberFormatType() {
		return getNumberFormatType(null);
	}

	/**
	 * See {@link #getNumberFormatType() getNumberFormatType()} for more details
	 */
	public final int getNumberFormatType(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.NUMBER_FORMAT_TYPE,0, facesContext);
	}

	public final void setNumberFormatType(int numberFormatType) {
		engine.setProperty(Properties.NUMBER_FORMAT_TYPE, numberFormatType);
	}

	/**
	 * See {@link #setNumberFormatType(int) setNumberFormatType(int)} for more details
	 */
	public final void setNumberFormatType(ValueBinding numberFormatType) {
		engine.setProperty(Properties.NUMBER_FORMAT_TYPE, numberFormatType);
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
	 * Returns an int value indicating the number of digits to show before the decimal separator.
	 * @return number of integer digits
	 */
	public final int getIntegerDigits() {
		return getIntegerDigits(null);
	}

	/**
	 * Returns an int value indicating the number of digits to show before the decimal separator.
	 * @return number of integer digits
	 */
	public final int getIntegerDigits(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.INTEGER_DIGITS, 0, facesContext);
	}

	/**
	 * Sets an int value indicating the number of digits to show before the decimal separator.
	 * @param integerDigits number of integer digits
	 */
	public final void setIntegerDigits(int integerDigits) {
		engine.setProperty(Properties.INTEGER_DIGITS, integerDigits);
	}

	/**
	 * Sets an int value indicating the number of digits to show before the decimal separator.
	 * @param integerDigits number of integer digits
	 */
	public final void setIntegerDigits(ValueBinding integerDigits) {
		engine.setProperty(Properties.INTEGER_DIGITS, integerDigits);
	}

	/**
	 * Returns <code>true</code> if the attribute "integerDigits" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isIntegerDigitsSetted() {
		return engine.isPropertySetted(Properties.INTEGER_DIGITS);
	}

	/**
	 * Returns an int value indicating the number of digits to show after the decimal separator.
	 * @return number of fraction digits
	 */
	public final int getFractionDigits() {
		return getFractionDigits(null);
	}

	/**
	 * Returns an int value indicating the number of digits to show after the decimal separator.
	 * @return number of fraction digits
	 */
	public final int getFractionDigits(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.FRACTION_DIGITS, 0, facesContext);
	}

	/**
	 * Sets an int value indicating the number of digits to show after the decimal separator.
	 * @param fractionDigits number of fraction digits
	 */
	public final void setFractionDigits(int fractionDigits) {
		engine.setProperty(Properties.FRACTION_DIGITS, fractionDigits);
	}

	/**
	 * Sets an int value indicating the number of digits to show after the decimal separator.
	 * @param fractionDigits number of fraction digits
	 */
	public final void setFractionDigits(ValueBinding fractionDigits) {
		engine.setProperty(Properties.FRACTION_DIGITS, fractionDigits);
	}

	/**
	 * Returns <code>true</code> if the attribute "fractionDigits" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFractionDigitsSetted() {
		return engine.isPropertySetted(Properties.FRACTION_DIGITS);
	}

	/**
	 * Returns a string value specifying the format to use (compliant with java format).
	 * @return number format
	 */
	public final String getNumberFormat() {
		return getNumberFormat(null);
	}

	/**
	 * Returns a string value specifying the format to use (compliant with java format).
	 * @return number format
	 */
	public final String getNumberFormat(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.NUMBER_FORMAT, facesContext);
	}

	/**
	 * Sets a string value specifying the format to use (compliant with java format).
	 * @param numberFormat number format
	 */
	public final void setNumberFormat(String numberFormat) {
		engine.setProperty(Properties.NUMBER_FORMAT, numberFormat);
	}

	/**
	 * Sets a string value specifying the format to use (compliant with java format).
	 * @param numberFormat number format
	 */
	public final void setNumberFormat(ValueBinding numberFormat) {
		engine.setProperty(Properties.NUMBER_FORMAT, numberFormat);
	}

	/**
	 * Returns <code>true</code> if the attribute "numberFormat" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isNumberFormatSetted() {
		return engine.isPropertySetted(Properties.NUMBER_FORMAT);
	}

	/**
	 * Returns an int value specifying the step used for the integer field when increasing/decreasing the value with the up/down arrows.
	 * @return step
	 */
	public final String getIntegerStep() {
		return getIntegerStep(null);
	}

	/**
	 * Returns an int value specifying the step used for the integer field when increasing/decreasing the value with the up/down arrows.
	 * @return step
	 */
	public final String getIntegerStep(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.INTEGER_STEP, facesContext);
	}

	/**
	 * Sets an int value specifying the step used for the integer field when increasing/decreasing the value with the up/down arrows.
	 * @param integerStep step
	 */
	public final void setIntegerStep(String integerStep) {
		engine.setProperty(Properties.INTEGER_STEP, integerStep);
	}

	/**
	 * Sets an int value specifying the step used for the integer field when increasing/decreasing the value with the up/down arrows.
	 * @param integerStep step
	 */
	public final void setIntegerStep(ValueBinding integerStep) {
		engine.setProperty(Properties.INTEGER_STEP, integerStep);
	}

	/**
	 * Returns <code>true</code> if the attribute "integerStep" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isIntegerStepSetted() {
		return engine.isPropertySetted(Properties.INTEGER_STEP);
	}

	/**
	 * Experimental Do not use.
	 */
	public final String getFractionStep() {
		return getFractionStep(null);
	}

	/**
	 * Experimental Do not use.
	 */
	public final String getFractionStep(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FRACTION_STEP, facesContext);
	}

	/**
	 * Experimental Do not use.
	 */
	public final void setFractionStep(String fractionStep) {
		engine.setProperty(Properties.FRACTION_STEP, fractionStep);
	}

	/**
	 * Experimental Do not use.
	 */
	public final void setFractionStep(ValueBinding fractionStep) {
		engine.setProperty(Properties.FRACTION_STEP, fractionStep);
	}

	/**
	 * Returns <code>true</code> if the attribute "fractionStep" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFractionStepSetted() {
		return engine.isPropertySetted(Properties.FRACTION_STEP);
	}

	public final Number getMinimum() {
		return getMinimum(null);
	}

	public final Number getMinimum(javax.faces.context.FacesContext facesContext) {
		return (Number)engine.getValue(Properties.MINIMUM, facesContext);
	}

	public final void setMinimum(Number minimum) {
		engine.setProperty(Properties.MINIMUM, minimum);
	}

	public final void setMinimum(ValueBinding minimum) {
		engine.setProperty(Properties.MINIMUM, minimum);
	}

	/**
	 * Returns <code>true</code> if the attribute "minimum" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMinimumSetted() {
		return engine.isPropertySetted(Properties.MINIMUM);
	}

	/**
	 * Returns the default value.
	 * @return default value
	 */
	public final Number getDefaultNumber() {
		return getDefaultNumber(null);
	}

	/**
	 * Returns the default value.
	 * @return default value
	 */
	public final Number getDefaultNumber(javax.faces.context.FacesContext facesContext) {
		return (Number)engine.getValue(Properties.DEFAULT_NUMBER, facesContext);
	}

	/**
	 * Sets the default value.
	 * @param defaultNumber default value
	 */
	public final void setDefaultNumber(Number defaultNumber) {
		engine.setProperty(Properties.DEFAULT_NUMBER, defaultNumber);
	}

	/**
	 * Sets the default value.
	 * @param defaultNumber default value
	 */
	public final void setDefaultNumber(ValueBinding defaultNumber) {
		engine.setProperty(Properties.DEFAULT_NUMBER, defaultNumber);
	}

	/**
	 * Returns <code>true</code> if the attribute "defaultNumber" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDefaultNumberSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_NUMBER);
	}

	/**
	 * Returns an int value specifying the upper limit for the value of the component.
	 * @return maximum
	 */
	public final Number getMaximum() {
		return getMaximum(null);
	}

	/**
	 * Returns an int value specifying the upper limit for the value of the component.
	 * @return maximum
	 */
	public final Number getMaximum(javax.faces.context.FacesContext facesContext) {
		return (Number)engine.getValue(Properties.MAXIMUM, facesContext);
	}

	/**
	 * Sets an int value specifying the upper limit for the value of the component.
	 * @param maximum maximum
	 */
	public final void setMaximum(Number maximum) {
		engine.setProperty(Properties.MAXIMUM, maximum);
	}

	/**
	 * Sets an int value specifying the upper limit for the value of the component.
	 * @param maximum maximum
	 */
	public final void setMaximum(ValueBinding maximum) {
		engine.setProperty(Properties.MAXIMUM, maximum);
	}

	/**
	 * Returns <code>true</code> if the attribute "maximum" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMaximumSetted() {
		return engine.isPropertySetted(Properties.MAXIMUM);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
