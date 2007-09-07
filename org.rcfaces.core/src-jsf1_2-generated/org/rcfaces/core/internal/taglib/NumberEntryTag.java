package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.NumberEntryComponent;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class NumberEntryTag extends AbstractInputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(NumberEntryTag.class);

	private ValueExpression required;
	private ValueExpression autoTab;
	private ValueExpression valueChangeListeners;
	private ValueExpression focusStyleClass;
	private ValueExpression selectionListeners;
	private ValueExpression readOnly;
	private ValueExpression numberFormatType;
	private ValueExpression literalLocale;
	private ValueExpression componentLocale;
	private ValueExpression errorStyleClass;
	private ValueExpression fatalStyleClass;
	private ValueExpression infoStyleClass;
	private ValueExpression warnStyleClass;
	private ValueExpression alternateText;
	private ValueExpression autoCompletion;
	private ValueExpression integerDigits;
	private ValueExpression fractionDigits;
	private ValueExpression numberFormat;
	private ValueExpression integerStep;
	private ValueExpression fractionStep;
	private ValueExpression number;
	private ValueExpression minimum;
	private ValueExpression defaultNumber;
	private ValueExpression maximum;
	public String getComponentType() {
		return NumberEntryComponent.COMPONENT_TYPE;
	}

	public final void setRequired(ValueExpression required) {
		this.required = required;
	}

	public final void setAutoTab(ValueExpression autoTab) {
		this.autoTab = autoTab;
	}

	public final void setValueChangeListener(ValueExpression valueChangeListeners) {
		this.valueChangeListeners = valueChangeListeners;
	}

	public final void setFocusStyleClass(ValueExpression focusStyleClass) {
		this.focusStyleClass = focusStyleClass;
	}

	public final void setSelectionListener(ValueExpression selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public final void setReadOnly(ValueExpression readOnly) {
		this.readOnly = readOnly;
	}

	public final void setNumberFormatType(ValueExpression numberFormatType) {
		this.numberFormatType = numberFormatType;
	}

	public final void setLiteralLocale(ValueExpression literalLocale) {
		this.literalLocale = literalLocale;
	}

	public final void setComponentLocale(ValueExpression componentLocale) {
		this.componentLocale = componentLocale;
	}

	public final void setErrorStyleClass(ValueExpression errorStyleClass) {
		this.errorStyleClass = errorStyleClass;
	}

	public final void setFatalStyleClass(ValueExpression fatalStyleClass) {
		this.fatalStyleClass = fatalStyleClass;
	}

	public final void setInfoStyleClass(ValueExpression infoStyleClass) {
		this.infoStyleClass = infoStyleClass;
	}

	public final void setWarnStyleClass(ValueExpression warnStyleClass) {
		this.warnStyleClass = warnStyleClass;
	}

	public final void setAlternateText(ValueExpression alternateText) {
		this.alternateText = alternateText;
	}

	public final void setAutoCompletion(ValueExpression autoCompletion) {
		this.autoCompletion = autoCompletion;
	}

	public final void setIntegerDigits(ValueExpression integerDigits) {
		this.integerDigits = integerDigits;
	}

	public final void setFractionDigits(ValueExpression fractionDigits) {
		this.fractionDigits = fractionDigits;
	}

	public final void setNumberFormat(ValueExpression numberFormat) {
		this.numberFormat = numberFormat;
	}

	public final void setIntegerStep(ValueExpression integerStep) {
		this.integerStep = integerStep;
	}

	public final void setFractionStep(ValueExpression fractionStep) {
		this.fractionStep = fractionStep;
	}

	public final void setNumber(ValueExpression number) {
		this.number = number;
	}

	public final void setMinimum(ValueExpression minimum) {
		this.minimum = minimum;
	}

	public final void setDefaultNumber(ValueExpression defaultNumber) {
		this.defaultNumber = defaultNumber;
	}

	public final void setMaximum(ValueExpression maximum) {
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
			LOG.debug("  literalLocale='"+literalLocale+"'");
			LOG.debug("  componentLocale='"+componentLocale+"'");
			LOG.debug("  errorStyleClass='"+errorStyleClass+"'");
			LOG.debug("  fatalStyleClass='"+fatalStyleClass+"'");
			LOG.debug("  infoStyleClass='"+infoStyleClass+"'");
			LOG.debug("  warnStyleClass='"+warnStyleClass+"'");
			LOG.debug("  alternateText='"+alternateText+"'");
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
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'NumberEntryComponent'.");
		}

		NumberEntryComponent component = (NumberEntryComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (required != null) {
			if (required.isLiteralText()==false) {
				component.setValueExpression(Properties.REQUIRED, required);

			} else {
				component.setRequired(getBool(required.getExpressionString()));
			}
		}

		if (autoTab != null) {
			if (autoTab.isLiteralText()==false) {
				component.setValueExpression(Properties.AUTO_TAB, autoTab);

			} else {
				component.setAutoTab(getBool(autoTab.getExpressionString()));
			}
		}

		if (valueChangeListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.VALUE_CHANGE_LISTENER_TYPE, valueChangeListeners);
		}

		if (focusStyleClass != null) {
			if (focusStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.FOCUS_STYLE_CLASS, focusStyleClass);

			} else {
				component.setFocusStyleClass(focusStyleClass.getExpressionString());
			}
		}

		if (selectionListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (readOnly != null) {
			if (readOnly.isLiteralText()==false) {
				component.setValueExpression(Properties.READ_ONLY, readOnly);

			} else {
				component.setReadOnly(getBool(readOnly.getExpressionString()));
			}
		}

		if (numberFormatType != null) {
			if (numberFormatType.isLiteralText()==false) {
				component.setValueExpression(Properties.NUMBER_FORMAT_TYPE, numberFormatType);

			} else {
				component.setNumberFormatType(numberFormatType.getExpressionString());
			}
		}

		if (literalLocale != null) {
			if (literalLocale.isLiteralText()==false) {
				component.setValueExpression(Properties.LITERAL_LOCALE, literalLocale);

			} else {
				component.setLiteralLocale(literalLocale.getExpressionString());
			}
		}

		if (componentLocale != null) {
			if (componentLocale.isLiteralText()==false) {
				component.setValueExpression(Properties.COMPONENT_LOCALE, componentLocale);

			} else {
				component.setComponentLocale(componentLocale.getExpressionString());
			}
		}

		if (errorStyleClass != null) {
			if (errorStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.ERROR_STYLE_CLASS, errorStyleClass);

			} else {
				component.setErrorStyleClass(errorStyleClass.getExpressionString());
			}
		}

		if (fatalStyleClass != null) {
			if (fatalStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.FATAL_STYLE_CLASS, fatalStyleClass);

			} else {
				component.setFatalStyleClass(fatalStyleClass.getExpressionString());
			}
		}

		if (infoStyleClass != null) {
			if (infoStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.INFO_STYLE_CLASS, infoStyleClass);

			} else {
				component.setInfoStyleClass(infoStyleClass.getExpressionString());
			}
		}

		if (warnStyleClass != null) {
			if (warnStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.WARN_STYLE_CLASS, warnStyleClass);

			} else {
				component.setWarnStyleClass(warnStyleClass.getExpressionString());
			}
		}

		if (alternateText != null) {
			if (alternateText.isLiteralText()==false) {
				component.setValueExpression(Properties.ALTERNATE_TEXT, alternateText);

			} else {
				component.setAlternateText(alternateText.getExpressionString());
			}
		}

		if (autoCompletion != null) {
			if (autoCompletion.isLiteralText()==false) {
				component.setValueExpression(Properties.AUTO_COMPLETION, autoCompletion);

			} else {
				component.setAutoCompletion(getBool(autoCompletion.getExpressionString()));
			}
		}

		if (integerDigits != null) {
			if (integerDigits.isLiteralText()==false) {
				component.setValueExpression(Properties.INTEGER_DIGITS, integerDigits);

			} else {
				component.setIntegerDigits(getInt(integerDigits.getExpressionString()));
			}
		}

		if (fractionDigits != null) {
			if (fractionDigits.isLiteralText()==false) {
				component.setValueExpression(Properties.FRACTION_DIGITS, fractionDigits);

			} else {
				component.setFractionDigits(getInt(fractionDigits.getExpressionString()));
			}
		}

		if (numberFormat != null) {
			if (numberFormat.isLiteralText()==false) {
				component.setValueExpression(Properties.NUMBER_FORMAT, numberFormat);

			} else {
				component.setNumberFormat(numberFormat.getExpressionString());
			}
		}

		if (integerStep != null) {
			if (integerStep.isLiteralText()==false) {
				component.setValueExpression(Properties.INTEGER_STEP, integerStep);

			} else {
				component.setIntegerStep(integerStep.getExpressionString());
			}
		}

		if (fractionStep != null) {
			if (fractionStep.isLiteralText()==false) {
				component.setValueExpression(Properties.FRACTION_STEP, fractionStep);

			} else {
				component.setFractionStep(fractionStep.getExpressionString());
			}
		}

		if (number != null) {
			if (number.isLiteralText()==false) {
				component.setValueExpression(Properties.VALUE, number);

			} else {
				component.setNumber(number.getExpressionString());
			}
		}

		if (minimum != null) {
			if (minimum.isLiteralText()==false) {
				component.setValueExpression(Properties.MINIMUM, minimum);

			} else {
				component.setMinimum(minimum.getExpressionString());
			}
		}

		if (defaultNumber != null) {
			if (defaultNumber.isLiteralText()==false) {
				component.setValueExpression(Properties.DEFAULT_NUMBER, defaultNumber);

			} else {
				component.setDefaultNumber(defaultNumber.getExpressionString());
			}
		}

		if (maximum != null) {
			if (maximum.isLiteralText()==false) {
				component.setValueExpression(Properties.MAXIMUM, maximum);

			} else {
				component.setMaximum(maximum.getExpressionString());
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
