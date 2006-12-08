package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IValueChangeEventCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.INumberFormatTypeCapability;
import java.lang.Object;
import org.rcfaces.core.converter.AbstractNumberConverter;
import org.rcfaces.core.component.capability.IAutoTabCapability;
import java.util.Arrays;
import java.util.Collections;
import org.rcfaces.core.internal.converter.NumberFormatTypeConverter;
import org.rcfaces.core.internal.component.IDataMapAccessor;
import org.rcfaces.core.component.AbstractInputComponent;
import org.rcfaces.core.component.capability.IFocusStyleClassCapability;
import org.rcfaces.core.component.capability.IRequiredCapability;
import java.lang.String;
import java.util.Map;
import javax.faces.context.FacesContext;
import java.util.HashMap;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.capability.ILocalizedAttributesCapability;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.internal.manager.IValidationParameters;
import java.util.Locale;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.converter.LocaleConverter;
import org.rcfaces.core.component.capability.IReadOnlyCapability;

public class NumberEntryComponent extends AbstractInputComponent implements 
	IRequiredCapability,
	IAutoTabCapability,
	IValueChangeEventCapability,
	IFocusStyleClassCapability,
	ISelectionEventCapability,
	IReadOnlyCapability,
	INumberFormatTypeCapability,
	ILocalizedAttributesCapability,
	IValidationParameters {

	public static final String COMPONENT_TYPE="org.rcfaces.core.numberEntry";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractInputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"selectionListener","autoCompletion","numberFormat","minimum","required","defaultNumber","numberFormatType","valueChangeListener","integerStep","fractionStep","attributesLocale","integerDigits","maximum","fractionDigits","readOnly","focusStyleClass","autoTab","number"}));
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
					FacesContext facesContext=FacesContext.getCurrentInstance();
					value=AbstractNumberConverter.SINGLETON.getAsObject(facesContext, this, (String)value);
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
			Number numberObject=(Number)AbstractNumberConverter.SINGLETON.getAsObject(facesContext, this, number);
			setMinimum(numberObject);
		
	}

	public final void setDefaultNumber(String number) {


			FacesContext facesContext=FacesContext.getCurrentInstance();
			Number numberObject=(Number)AbstractNumberConverter.SINGLETON.getAsObject(facesContext, this, number);
			setDefaultNumber(numberObject);
		
	}

	public final void setMaximum(String number) {


			FacesContext facesContext=FacesContext.getCurrentInstance();
			Number numberObject=(Number)AbstractNumberConverter.SINGLETON.getAsObject(facesContext, this, number);
			setMaximum(numberObject);
		
	}

	public final void setAttributesLocale(String locale) {


		setAttributesLocale((Locale)LocaleConverter.SINGLETON.getAsObject(null, this, locale));
		
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

	public final boolean isAutoTab(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.AUTO_TAB, false, facesContext);
	}

	public final void setAutoTab(boolean autoTab) {
		engine.setProperty(Properties.AUTO_TAB, autoTab);
	}

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

	public final java.lang.String getFocusStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOCUS_STYLE_CLASS, facesContext);
	}

	public final void setFocusStyleClass(java.lang.String focusStyleClass) {
		engine.setProperty(Properties.FOCUS_STYLE_CLASS, focusStyleClass);
	}

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

	public final boolean isReadOnly(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.READ_ONLY, false, facesContext);
	}

	public final void setReadOnly(boolean readOnly) {
		engine.setProperty(Properties.READ_ONLY, readOnly);
	}

	public final void setReadOnly(ValueBinding readOnly) {
		engine.setProperty(Properties.READ_ONLY, readOnly);
	}

	public final int getNumberFormatType() {
		return getNumberFormatType(null);
	}

	public final int getNumberFormatType(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.NUMBER_FORMAT_TYPE,0, facesContext);
	}

	public final void setNumberFormatType(int numberFormatType) {
		engine.setProperty(Properties.NUMBER_FORMAT_TYPE, numberFormatType);
	}

	public final void setNumberFormatType(ValueBinding numberFormatType) {
		engine.setProperty(Properties.NUMBER_FORMAT_TYPE, numberFormatType);
	}

	public final java.util.Locale getAttributesLocale() {
		return getAttributesLocale(null);
	}

	public final java.util.Locale getAttributesLocale(javax.faces.context.FacesContext facesContext) {
		return (java.util.Locale)engine.getProperty(Properties.ATTRIBUTES_LOCALE, facesContext);
	}

	public final void setAttributesLocale(java.util.Locale attributesLocale) {
		engine.setProperty(Properties.ATTRIBUTES_LOCALE, attributesLocale);
	}

	public final void setAttributesLocale(ValueBinding attributesLocale) {
		engine.setProperty(Properties.ATTRIBUTES_LOCALE, attributesLocale);
	}

	public final boolean isAutoCompletion() {
		return isAutoCompletion(null);
	}

	public final boolean isAutoCompletion(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.AUTO_COMPLETION, true, facesContext);
	}

	public final void setAutoCompletion(boolean autoCompletion) {
		engine.setProperty(Properties.AUTO_COMPLETION, autoCompletion);
	}

	public final void setAutoCompletion(ValueBinding autoCompletion) {
		engine.setProperty(Properties.AUTO_COMPLETION, autoCompletion);
	}

	public final boolean isAutoCompletionSetted() {
		return engine.isPropertySetted(Properties.AUTO_COMPLETION);
	}

	public final int getIntegerDigits() {
		return getIntegerDigits(null);
	}

	public final int getIntegerDigits(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.INTEGER_DIGITS, 0, facesContext);
	}

	public final void setIntegerDigits(int integerDigits) {
		engine.setProperty(Properties.INTEGER_DIGITS, integerDigits);
	}

	public final void setIntegerDigits(ValueBinding integerDigits) {
		engine.setProperty(Properties.INTEGER_DIGITS, integerDigits);
	}

	public final boolean isIntegerDigitsSetted() {
		return engine.isPropertySetted(Properties.INTEGER_DIGITS);
	}

	public final int getFractionDigits() {
		return getFractionDigits(null);
	}

	public final int getFractionDigits(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.FRACTION_DIGITS, 0, facesContext);
	}

	public final void setFractionDigits(int fractionDigits) {
		engine.setProperty(Properties.FRACTION_DIGITS, fractionDigits);
	}

	public final void setFractionDigits(ValueBinding fractionDigits) {
		engine.setProperty(Properties.FRACTION_DIGITS, fractionDigits);
	}

	public final boolean isFractionDigitsSetted() {
		return engine.isPropertySetted(Properties.FRACTION_DIGITS);
	}

	public final String getNumberFormat() {
		return getNumberFormat(null);
	}

	public final String getNumberFormat(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.NUMBER_FORMAT, facesContext);
	}

	public final void setNumberFormat(String numberFormat) {
		engine.setProperty(Properties.NUMBER_FORMAT, numberFormat);
	}

	public final void setNumberFormat(ValueBinding numberFormat) {
		engine.setProperty(Properties.NUMBER_FORMAT, numberFormat);
	}

	public final boolean isNumberFormatSetted() {
		return engine.isPropertySetted(Properties.NUMBER_FORMAT);
	}

	public final String getIntegerStep() {
		return getIntegerStep(null);
	}

	public final String getIntegerStep(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.INTEGER_STEP, facesContext);
	}

	public final void setIntegerStep(String integerStep) {
		engine.setProperty(Properties.INTEGER_STEP, integerStep);
	}

	public final void setIntegerStep(ValueBinding integerStep) {
		engine.setProperty(Properties.INTEGER_STEP, integerStep);
	}

	public final boolean isIntegerStepSetted() {
		return engine.isPropertySetted(Properties.INTEGER_STEP);
	}

	public final String getFractionStep() {
		return getFractionStep(null);
	}

	public final String getFractionStep(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FRACTION_STEP, facesContext);
	}

	public final void setFractionStep(String fractionStep) {
		engine.setProperty(Properties.FRACTION_STEP, fractionStep);
	}

	public final void setFractionStep(ValueBinding fractionStep) {
		engine.setProperty(Properties.FRACTION_STEP, fractionStep);
	}

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

	public final boolean isMinimumSetted() {
		return engine.isPropertySetted(Properties.MINIMUM);
	}

	public final Number getDefaultNumber() {
		return getDefaultNumber(null);
	}

	public final Number getDefaultNumber(javax.faces.context.FacesContext facesContext) {
		return (Number)engine.getValue(Properties.DEFAULT_NUMBER, facesContext);
	}

	public final void setDefaultNumber(Number defaultNumber) {
		engine.setProperty(Properties.DEFAULT_NUMBER, defaultNumber);
	}

	public final void setDefaultNumber(ValueBinding defaultNumber) {
		engine.setProperty(Properties.DEFAULT_NUMBER, defaultNumber);
	}

	public final boolean isDefaultNumberSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_NUMBER);
	}

	public final Number getMaximum() {
		return getMaximum(null);
	}

	public final Number getMaximum(javax.faces.context.FacesContext facesContext) {
		return (Number)engine.getValue(Properties.MAXIMUM, facesContext);
	}

	public final void setMaximum(Number maximum) {
		engine.setProperty(Properties.MAXIMUM, maximum);
	}

	public final void setMaximum(ValueBinding maximum) {
		engine.setProperty(Properties.MAXIMUM, maximum);
	}

	public final boolean isMaximumSetted() {
		return engine.isPropertySetted(Properties.MAXIMUM);
	}

	public void release() {
		super.release();
	}
	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
