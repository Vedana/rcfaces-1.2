package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.component.RadioButtonComponent;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class RadioButtonTag extends AbstractInputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(RadioButtonTag.class);

	private ValueExpression text;
	private ValueExpression textDirection;
	private ValueExpression textPosition;
	private ValueExpression selectionListeners;
	private ValueExpression readOnly;
	private ValueExpression alternateText;
	private ValueExpression errorStyleClass;
	private ValueExpression fatalStyleClass;
	private ValueExpression infoStyleClass;
	private ValueExpression warnStyleClass;
	private ValueExpression focusStyleClass;
	private ValueExpression selected;
	private ValueExpression radioValue;
	private ValueExpression groupName;
	private ValueExpression required;
	public String getComponentType() {
		return RadioButtonComponent.COMPONENT_TYPE;
	}

	public void setText(ValueExpression text) {
		this.text = text;
	}

	public void setTextDirection(ValueExpression textDirection) {
		this.textDirection = textDirection;
	}

	public void setTextPosition(ValueExpression textPosition) {
		this.textPosition = textPosition;
	}

	public void setSelectionListener(ValueExpression selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public void setReadOnly(ValueExpression readOnly) {
		this.readOnly = readOnly;
	}

	public void setAlternateText(ValueExpression alternateText) {
		this.alternateText = alternateText;
	}

	public void setErrorStyleClass(ValueExpression errorStyleClass) {
		this.errorStyleClass = errorStyleClass;
	}

	public void setFatalStyleClass(ValueExpression fatalStyleClass) {
		this.fatalStyleClass = fatalStyleClass;
	}

	public void setInfoStyleClass(ValueExpression infoStyleClass) {
		this.infoStyleClass = infoStyleClass;
	}

	public void setWarnStyleClass(ValueExpression warnStyleClass) {
		this.warnStyleClass = warnStyleClass;
	}

	public void setFocusStyleClass(ValueExpression focusStyleClass) {
		this.focusStyleClass = focusStyleClass;
	}

	public void setSelected(ValueExpression selected) {
		this.selected = selected;
	}

	public void setRadioValue(ValueExpression radioValue) {
		this.radioValue = radioValue;
	}

	public void setGroupName(ValueExpression groupName) {
		this.groupName = groupName;
	}

	public void setRequired(ValueExpression required) {
		this.required = required;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (RadioButtonComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  text='"+text+"'");
			LOG.debug("  textDirection='"+textDirection+"'");
			LOG.debug("  textPosition='"+textPosition+"'");
			LOG.debug("  readOnly='"+readOnly+"'");
			LOG.debug("  alternateText='"+alternateText+"'");
			LOG.debug("  errorStyleClass='"+errorStyleClass+"'");
			LOG.debug("  fatalStyleClass='"+fatalStyleClass+"'");
			LOG.debug("  infoStyleClass='"+infoStyleClass+"'");
			LOG.debug("  warnStyleClass='"+warnStyleClass+"'");
			LOG.debug("  focusStyleClass='"+focusStyleClass+"'");
			LOG.debug("  selected='"+selected+"'");
			LOG.debug("  radioValue='"+radioValue+"'");
			LOG.debug("  groupName='"+groupName+"'");
			LOG.debug("  required='"+required+"'");
		}
		if ((uiComponent instanceof RadioButtonComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'RadioButtonComponent'.");
		}

		super.setProperties(uiComponent);

		RadioButtonComponent component = (RadioButtonComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (text != null) {
			if (text.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT, text);

			} else {
				component.setText(text.getExpressionString());
			}
		}

		if (textDirection != null) {
			if (textDirection.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT_DIRECTION, textDirection);

			} else {
				component.setTextDirection(getInt(textDirection.getExpressionString()));
			}
		}

		if (textPosition != null) {
			if (textPosition.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT_POSITION, textPosition);

			} else {
				component.setTextPosition(textPosition.getExpressionString());
			}
		}

		if (selectionListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (readOnly != null) {
			if (readOnly.isLiteralText()==false) {
				component.setValueExpression(Properties.READ_ONLY, readOnly);

			} else {
				component.setReadOnly(getBool(readOnly.getExpressionString()));
			}
		}

		if (alternateText != null) {
			if (alternateText.isLiteralText()==false) {
				component.setValueExpression(Properties.ALTERNATE_TEXT, alternateText);

			} else {
				component.setAlternateText(alternateText.getExpressionString());
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

		if (focusStyleClass != null) {
			if (focusStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.FOCUS_STYLE_CLASS, focusStyleClass);

			} else {
				component.setFocusStyleClass(focusStyleClass.getExpressionString());
			}
		}

		if (selected != null) {
			if (selected.isLiteralText()==false) {
				component.setValueExpression(Properties.SELECTED, selected);

			} else {
				component.setSelected(getBool(selected.getExpressionString()));
			}
		}

		if (radioValue != null) {
			if (radioValue.isLiteralText()==false) {
				component.setValueExpression(Properties.RADIO_VALUE, radioValue);

			} else {
				component.setRadioValue(radioValue.getExpressionString());
			}
		}

		if (groupName != null) {
			if (groupName.isLiteralText()==false) {
				component.setValueExpression(Properties.GROUP_NAME, groupName);

			} else {
				component.setGroupName(groupName.getExpressionString());
			}
		}

		if (required != null) {
			if (required.isLiteralText()==false) {
				component.setValueExpression(Properties.REQUIRED, required);

			} else {
				component.setRequired(getBool(required.getExpressionString()));
			}
		}
	}

	public void release() {
		text = null;
		textDirection = null;
		textPosition = null;
		selectionListeners = null;
		readOnly = null;
		alternateText = null;
		errorStyleClass = null;
		fatalStyleClass = null;
		infoStyleClass = null;
		warnStyleClass = null;
		focusStyleClass = null;
		selected = null;
		radioValue = null;
		groupName = null;
		required = null;

		super.release();
	}

}
