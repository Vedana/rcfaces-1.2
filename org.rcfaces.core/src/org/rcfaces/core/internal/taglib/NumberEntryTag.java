package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.component.NumberEntryComponent;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class NumberEntryTag extends AbstractInputTag implements Tag {

    private static final Log LOG = LogFactory.getLog(NumberEntryTag.class);

    private String required;

    private String autoTab;

    private String valueChangeListeners;

    private String focusStyleClass;

    private String selectionListeners;

    private String readOnly;

    private String numberFormatType;

    private String literalLocale;

    private String componentLocale;

    private String errorStyleClass;

    private String fatalStyleClass;

    private String infoStyleClass;

    private String warnStyleClass;

    private String alternateText;

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

    public final String getLiteralLocale() {
        return literalLocale;
    }

    public final void setLiteralLocale(String literalLocale) {
        this.literalLocale = literalLocale;
    }

    public final String getComponentLocale() {
        return componentLocale;
    }

    public final void setComponentLocale(String componentLocale) {
        this.componentLocale = componentLocale;
    }

    public final String getErrorStyleClass() {
        return errorStyleClass;
    }

    public final void setErrorStyleClass(String errorStyleClass) {
        this.errorStyleClass = errorStyleClass;
    }

    public final String getFatalStyleClass() {
        return fatalStyleClass;
    }

    public final void setFatalStyleClass(String fatalStyleClass) {
        this.fatalStyleClass = fatalStyleClass;
    }

    public final String getInfoStyleClass() {
        return infoStyleClass;
    }

    public final void setInfoStyleClass(String infoStyleClass) {
        this.infoStyleClass = infoStyleClass;
    }

    public final String getWarnStyleClass() {
        return warnStyleClass;
    }

    public final void setWarnStyleClass(String warnStyleClass) {
        this.warnStyleClass = warnStyleClass;
    }

    public final String getAlternateText() {
        return alternateText;
    }

    public final void setAlternateText(String alternateText) {
        this.alternateText = alternateText;
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
            if (NumberEntryComponent.COMPONENT_TYPE == getComponentType()) {
                LOG.debug("Component id='" + getId() + "' type='"
                        + getComponentType() + "'.");
            }
            LOG.debug("  required='" + required + "'");
            LOG.debug("  autoTab='" + autoTab + "'");
            LOG.debug("  focusStyleClass='" + focusStyleClass + "'");
            LOG.debug("  readOnly='" + readOnly + "'");
            LOG.debug("  numberFormatType='" + numberFormatType + "'");
            LOG.debug("  literalLocale='" + literalLocale + "'");
            LOG.debug("  componentLocale='" + componentLocale + "'");
            LOG.debug("  errorStyleClass='" + errorStyleClass + "'");
            LOG.debug("  fatalStyleClass='" + fatalStyleClass + "'");
            LOG.debug("  infoStyleClass='" + infoStyleClass + "'");
            LOG.debug("  warnStyleClass='" + warnStyleClass + "'");
            LOG.debug("  alternateText='" + alternateText + "'");
            LOG.debug("  autoCompletion='" + autoCompletion + "'");
            LOG.debug("  integerDigits='" + integerDigits + "'");
            LOG.debug("  fractionDigits='" + fractionDigits + "'");
            LOG.debug("  numberFormat='" + numberFormat + "'");
            LOG.debug("  integerStep='" + integerStep + "'");
            LOG.debug("  fractionStep='" + fractionStep + "'");
            LOG.debug("  number='" + number + "'");
            LOG.debug("  minimum='" + minimum + "'");
            LOG.debug("  defaultNumber='" + defaultNumber + "'");
            LOG.debug("  maximum='" + maximum + "'");
        }
        super.setProperties(uiComponent);

        if ((uiComponent instanceof NumberEntryComponent) == false) {
            if (uiComponent instanceof UIViewRoot) {
                throw new IllegalStateException(
                        "The first component of the page must be a UIViewRoot component !");
            }
            throw new IllegalStateException(
                    "Component specified by tag is not instanceof of 'NumberEntryComponent'.");
        }

        NumberEntryComponent component = (NumberEntryComponent) uiComponent;
        FacesContext facesContext = getFacesContext();
        Application application = facesContext.getApplication();

        if (required != null) {
            if (isValueReference(required)) {
                ValueBinding vb = application.createValueBinding(required);
                component.setValueBinding(Properties.REQUIRED, vb);

            } else {
                component.setRequired(getBool(required));
            }
        }

        if (autoTab != null) {
            if (isValueReference(autoTab)) {
                ValueBinding vb = application.createValueBinding(autoTab);
                component.setValueBinding(Properties.AUTO_TAB, vb);

            } else {
                component.setAutoTab(getBool(autoTab));
            }
        }

        if (valueChangeListeners != null) {
            ListenersTools.parseListener(facesContext, component,
                    ListenersTools.VALUE_CHANGE_LISTENER_TYPE,
                    valueChangeListeners);
        }

        if (focusStyleClass != null) {
            if (isValueReference(focusStyleClass)) {
                ValueBinding vb = application
                        .createValueBinding(focusStyleClass);
                component.setValueBinding(Properties.FOCUS_STYLE_CLASS, vb);

            } else {
                component.setFocusStyleClass(focusStyleClass);
            }
        }

        if (selectionListeners != null) {
            ListenersTools.parseListener(facesContext, component,
                    ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
        }

        if (readOnly != null) {
            if (isValueReference(readOnly)) {
                ValueBinding vb = application.createValueBinding(readOnly);
                component.setValueBinding(Properties.READ_ONLY, vb);

            } else {
                component.setReadOnly(getBool(readOnly));
            }
        }

        if (numberFormatType != null) {
            if (isValueReference(numberFormatType)) {
                ValueBinding vb = application
                        .createValueBinding(numberFormatType);
                component.setValueBinding(Properties.NUMBER_FORMAT_TYPE, vb);

            } else {
                component.setNumberFormatType(numberFormatType);
            }
        }

        if (literalLocale != null) {
            if (isValueReference(literalLocale)) {
                ValueBinding vb = application.createValueBinding(literalLocale);
                component.setValueBinding(Properties.LITERAL_LOCALE, vb);

            } else {
                component.setLiteralLocale(literalLocale);
            }
        }

        if (componentLocale != null) {
            if (isValueReference(componentLocale)) {
                ValueBinding vb = application
                        .createValueBinding(componentLocale);
                component.setValueBinding(Properties.COMPONENT_LOCALE, vb);

            } else {
                component.setComponentLocale(componentLocale);
            }
        }

        if (errorStyleClass != null) {
            if (isValueReference(errorStyleClass)) {
                ValueBinding vb = application
                        .createValueBinding(errorStyleClass);
                component.setValueBinding(Properties.ERROR_STYLE_CLASS, vb);

            } else {
                component.setErrorStyleClass(errorStyleClass);
            }
        }

        if (fatalStyleClass != null) {
            if (isValueReference(fatalStyleClass)) {
                ValueBinding vb = application
                        .createValueBinding(fatalStyleClass);
                component.setValueBinding(Properties.FATAL_STYLE_CLASS, vb);

            } else {
                component.setFatalStyleClass(fatalStyleClass);
            }
        }

        if (infoStyleClass != null) {
            if (isValueReference(infoStyleClass)) {
                ValueBinding vb = application
                        .createValueBinding(infoStyleClass);
                component.setValueBinding(Properties.INFO_STYLE_CLASS, vb);

            } else {
                component.setInfoStyleClass(infoStyleClass);
            }
        }

        if (warnStyleClass != null) {
            if (isValueReference(warnStyleClass)) {
                ValueBinding vb = application
                        .createValueBinding(warnStyleClass);
                component.setValueBinding(Properties.WARN_STYLE_CLASS, vb);

            } else {
                component.setWarnStyleClass(warnStyleClass);
            }
        }

        if (alternateText != null) {
            if (isValueReference(alternateText)) {
                ValueBinding vb = application.createValueBinding(alternateText);
                component.setValueBinding(Properties.ALTERNATE_TEXT, vb);

            } else {
                component.setAlternateText(alternateText);
            }
        }

        if (autoCompletion != null) {
            if (isValueReference(autoCompletion)) {
                ValueBinding vb = application
                        .createValueBinding(autoCompletion);
                component.setValueBinding(Properties.AUTO_COMPLETION, vb);

            } else {
                component.setAutoCompletion(getBool(autoCompletion));
            }
        }

        if (integerDigits != null) {
            if (isValueReference(integerDigits)) {
                ValueBinding vb = application.createValueBinding(integerDigits);
                component.setValueBinding(Properties.INTEGER_DIGITS, vb);

            } else {
                component.setIntegerDigits(getInt(integerDigits));
            }
        }

        if (fractionDigits != null) {
            if (isValueReference(fractionDigits)) {
                ValueBinding vb = application
                        .createValueBinding(fractionDigits);
                component.setValueBinding(Properties.FRACTION_DIGITS, vb);

            } else {
                component.setFractionDigits(getInt(fractionDigits));
            }
        }

        if (numberFormat != null) {
            if (isValueReference(numberFormat)) {
                ValueBinding vb = application.createValueBinding(numberFormat);
                component.setValueBinding(Properties.NUMBER_FORMAT, vb);

            } else {
                component.setNumberFormat(numberFormat);
            }
        }

        if (integerStep != null) {
            if (isValueReference(integerStep)) {
                ValueBinding vb = application.createValueBinding(integerStep);
                component.setValueBinding(Properties.INTEGER_STEP, vb);

            } else {
                component.setIntegerStep(integerStep);
            }
        }

        if (fractionStep != null) {
            if (isValueReference(fractionStep)) {
                ValueBinding vb = application.createValueBinding(fractionStep);
                component.setValueBinding(Properties.FRACTION_STEP, vb);

            } else {
                component.setFractionStep(fractionStep);
            }
        }

        if (number != null) {
            if (isValueReference(number)) {
                ValueBinding vb = application.createValueBinding(number);
                component.setValueBinding(Properties.VALUE, vb);

            } else {
                component.setNumber(number);
            }
        }

        if (minimum != null) {
            if (isValueReference(minimum)) {
                ValueBinding vb = application.createValueBinding(minimum);
                component.setValueBinding(Properties.MINIMUM, vb);

            } else {
                component.setMinimum(minimum);
            }
        }

        if (defaultNumber != null) {
            if (isValueReference(defaultNumber)) {
                ValueBinding vb = application.createValueBinding(defaultNumber);
                component.setValueBinding(Properties.DEFAULT_NUMBER, vb);

            } else {
                component.setDefaultNumber(defaultNumber);
            }
        }

        if (maximum != null) {
            if (isValueReference(maximum)) {
                ValueBinding vb = application.createValueBinding(maximum);
                component.setValueBinding(Properties.MAXIMUM, vb);

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
        literalLocale = null;
        componentLocale = null;
        errorStyleClass = null;
        fatalStyleClass = null;
        infoStyleClass = null;
        warnStyleClass = null;
        alternateText = null;
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
