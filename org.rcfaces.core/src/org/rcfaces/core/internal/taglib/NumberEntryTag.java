package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.component.NumberEntryComponent;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class NumberEntryTag extends AbstractInputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(NumberEntryTag.class);

	private String required;
	private String autoTab;
	private String valueChangeListeners;
	private String focusStyleClass;
	private String selectionListeners;
	private String readOnly;
	private String numberFormatType;
	private String attributesLocale;
	private String autoCompletion;
	private String integerDigits;
	private String fractionDigits;
	private String numberFormat;
	private String integerStep;
	private String fractionStep;
	private String number;
	private String minimum;
	private String defaultNumber;
	private String maximum;
	public String getComponentType() {
		return NumberEntryComponent.COMPONENT_TYPE;
	}

	public final String getRequired() {
		return required;
	}

	public final void setRequired(String required) {
		this.required = required;
	}

	public final String getAutoTab() {
		return autoTab;
	}

	public final void setAutoTab(String autoTab) {
		this.autoTab = autoTab;
	}

	public final String getValueChangeListener() {
		return valueChangeListeners;
	}

	public final void setValueChangeListener(String valueChangeListeners) {
		this.valueChangeListeners = valueChangeListeners;
	}

	public final String getFocusStyleClass() {
		return focusStyleClass;
	}

	public final void setFocusStyleClass(String focusStyleClass) {
		this.focusStyleClass = focusStyleClass;
	}

	public final String getSelectionListener() {
		return selectionListeners;
	}

	public final void setSelectionListener(String selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public final String getReadOnly() {
		return readOnly;
	}

	public final void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	public final String getNumberFormatType() {
		return numberFormatType;
	}

	public final void setNumberFormatType(String numberFormatType) {
		this.numberFormatType = numberFormatType;
	}

	public final String getAttributesLocale() {
		return attributesLocale;
	}

	public final void setAttributesLocale(String attributesLocale) {
		this.attributesLocale = attributesLocale;
	}

	public final String getAutoCompletion() {
		return autoCompletion;
	}

	public final void setAutoCompletion(String autoCompletion) {
		this.autoCompletion = autoCompletion;
	}

	public final String getIntegerDigits() {
		return integerDigits;
	}

	public final void setIntegerDigits(String integerDigits) {
		this.integerDigits = integerDigits;
	}

	public final String getFractionDigits() {
		return fractionDigits;
	}

	public final void setFractionDigits(String fractionDigits) {
		this.fractionDigits = fractionDigits;
	}

	public final String getNumberFormat() {
		return numberFormat;
	}

	public final void setNumberFormat(String numberFormat) {
		this.numberFormat = numberFormat;
	}

	public final String getIntegerStep() {
		return integerStep;
	}

	public final void setIntegerStep(String integerStep) {
		this.integerStep = integerStep;
	}

	public final String getFractionStep() {
		return fractionStep;
	}

	public final void setFractionStep(String fractionStep) {
		this.fractionStep = fractionStep;
	}

	public final String getNumber() {
		return number;
	}

	public final void setNumber(String number) {
		this.number = number;
	}

	public final String getMinimum() {
		return minimum;
	}

	public final void setMinimum(String minimum) {
		this.minimum = minimum;
	}

	public final String getDefaultNumber() {
		return defaultNumber;
	}

	public final void setDefaultNumber(String defaultNumber) {
		this.defaultNumber = defaultNumber;
	}

	public final String getMaximum() {
		return maximum;
	}

	public final void setMaximum(String maximum) {
		this.maximum = maximum;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (NumberEntryComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  required='"+required+"'");
			LOG.debug("  autoTab='"+autoTab+"'");
			LOG.debug("  focusStyleClass='"+focusStyleClass+"'");
			LOG.debug("  readOnly='"+readOnly+"'");
			LOG.debug("  numberFormatType='"+numberFormatType+"'");
			LOG.debug("  attributesLocale='"+attributesLocale+"'");
			LOG.debug("  autoCompletion='"+autoCompletion+"'");
			LOG.debug("  integerDigits='"+integerDigits+"'");
			LOG.debug("  fractionDigits='"+fractionDigits+"'");
			LOG.debug("  numberFormat='"+numberFormat+"'");
			LOG.debug("  integerStep='"+integerStep+"'");
			LOG.debug("  fractionStep='"+fractionStep+"'");
			LOG.debug("  number='"+number+"'");
			LOG.debug("  minimum='"+minimum+"'");
			LOG.debug("  defaultNumber='"+defaultNumber+"'");
			LOG.debug("  maximum='"+maximum+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof NumberEntryComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'NumberEntryComponent'.");
		}

		NumberEntryComponent component = (NumberEntryComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (required != null) {
			if (isValueReference(required)) {
				ValueBinding vb = application.createValueBinding(required);

				component.setRequired(vb);
			} else {
				component.setRequired(getBool(required));
			}
		}

		if (autoTab != null) {
			if (isValueReference(autoTab)) {
				ValueBinding vb = application.createValueBinding(autoTab);

				component.setAutoTab(vb);
			} else {
				component.setAutoTab(getBool(autoTab));
			}
		}

		if (valueChangeListeners != null) {
			Listeners.parseListener(facesContext, component, Listeners.VALUE_CHANGE_LISTENER_TYPE, valueChangeListeners);
		}

		if (focusStyleClass != null) {
			if (isValueReference(focusStyleClass)) {
				ValueBinding vb = application.createValueBinding(focusStyleClass);

				component.setFocusStyleClass(vb);
			} else {
				component.setFocusStyleClass(focusStyleClass);
			}
		}

		if (selectionListeners != null) {
			Listeners.parseListener(facesContext, component, Listeners.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (readOnly != null) {
			if (isValueReference(readOnly)) {
				ValueBinding vb = application.createValueBinding(readOnly);

				component.setReadOnly(vb);
			} else {
				component.setReadOnly(getBool(readOnly));
			}
		}

		if (numberFormatType != null) {
			if (isValueReference(numberFormatType)) {
				ValueBinding vb = application.createValueBinding(numberFormatType);

				component.setNumberFormatType(vb);
			} else {
				component.setNumberFormatType(numberFormatType);
			}
		}

		if (attributesLocale != null) {
			if (isValueReference(attributesLocale)) {
				ValueBinding vb = application.createValueBinding(attributesLocale);

				component.setAttributesLocale(vb);
			} else {
				component.setAttributesLocale(attributesLocale);
			}
		}

		if (autoCompletion != null) {
			if (isValueReference(autoCompletion)) {
				ValueBinding vb = application.createValueBinding(autoCompletion);
				component.setAutoCompletion(vb);
			} else {
				component.setAutoCompletion(getBool(autoCompletion));
			}
		}

		if (integerDigits != null) {
			if (isValueReference(integerDigits)) {
				ValueBinding vb = application.createValueBinding(integerDigits);
				component.setIntegerDigits(vb);
			} else {
				component.setIntegerDigits(getInt(integerDigits));
			}
		}

		if (fractionDigits != null) {
			if (isValueReference(fractionDigits)) {
				ValueBinding vb = application.createValueBinding(fractionDigits);
				component.setFractionDigits(vb);
			} else {
				component.setFractionDigits(getInt(fractionDigits));
			}
		}

		if (numberFormat != null) {
			if (isValueReference(numberFormat)) {
				ValueBinding vb = application.createValueBinding(numberFormat);
				component.setNumberFormat(vb);
			} else {
				component.setNumberFormat(numberFormat);
			}
		}

		if (integerStep != null) {
			if (isValueReference(integerStep)) {
				ValueBinding vb = application.createValueBinding(integerStep);
				component.setIntegerStep(vb);
			} else {
				component.setIntegerStep(integerStep);
			}
		}

		if (fractionStep != null) {
			if (isValueReference(fractionStep)) {
				ValueBinding vb = application.createValueBinding(fractionStep);
				component.setFractionStep(vb);
			} else {
				component.setFractionStep(fractionStep);
			}
		}

		if (number != null) {
			if (isValueReference(number)) {
				ValueBinding vb = application.createValueBinding(number);
				component.setNumber(vb);
			} else {
				component.setNumber(number);
			}
		}

		if (minimum != null) {
			if (isValueReference(minimum)) {
				ValueBinding vb = application.createValueBinding(minimum);
				component.setMinimum(vb);
			} else {
				component.setMinimum(minimum);
			}
		}

		if (defaultNumber != null) {
			if (isValueReference(defaultNumber)) {
				ValueBinding vb = application.createValueBinding(defaultNumber);
				component.setDefaultNumber(vb);
			} else {
				component.setDefaultNumber(defaultNumber);
			}
		}

		if (maximum != null) {
			if (isValueReference(maximum)) {
				ValueBinding vb = application.createValueBinding(maximum);
				component.setMaximum(vb);
			} else {
				component.setMaximum(maximum);
			}
		}
	}

	public void release() {
		required = null;
		autoTab = null;
		valueChangeListeners = null;
		focusStyleClass = null;
		selectionListeners = null;
		readOnly = null;
		numberFormatType = null;
		attributesLocale = null;
		autoCompletion = null;
		integerDigits = null;
		fractionDigits = null;
		numberFormat = null;
		integerStep = null;
		fractionStep = null;
		number = null;
		minimum = null;
		defaultNumber = null;
		maximum = null;

		super.release();
	}

}
