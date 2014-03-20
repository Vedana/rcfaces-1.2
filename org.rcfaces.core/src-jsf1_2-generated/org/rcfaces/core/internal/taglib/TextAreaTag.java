package org.rcfaces.core.internal.taglib;

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
import org.rcfaces.core.component.TextAreaComponent;
import javax.faces.context.FacesContext;

public class TextAreaTag extends AbstractInputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(TextAreaTag.class);

	private ValueExpression required;
	private ValueExpression text;
	private ValueExpression textDirection;
	private ValueExpression emptyMessage;
	private ValueExpression readOnly;
	private ValueExpression valueChangeListeners;
	private ValueExpression focusStyleClass;
	private ValueExpression errorStyleClass;
	private ValueExpression fatalStyleClass;
	private ValueExpression infoStyleClass;
	private ValueExpression warnStyleClass;
	private ValueExpression selectionListeners;
	private ValueExpression alternateText;
	private ValueExpression maxTextLength;
	private ValueExpression columnNumber;
	private ValueExpression rowNumber;
	private ValueExpression ignoreWhenFull;
	private ValueExpression actionListeners;
	private ValueExpression action;
	public String getComponentType() {
		return TextAreaComponent.COMPONENT_TYPE;
	}

	public void setRequired(ValueExpression required) {
		this.required = required;
	}

	public void setText(ValueExpression text) {
		this.text = text;
	}

	public void setTextDirection(ValueExpression textDirection) {
		this.textDirection = textDirection;
	}

	public void setEmptyMessage(ValueExpression emptyMessage) {
		this.emptyMessage = emptyMessage;
	}

	public void setReadOnly(ValueExpression readOnly) {
		this.readOnly = readOnly;
	}

	public void setValueChangeListener(ValueExpression valueChangeListeners) {
		this.valueChangeListeners = valueChangeListeners;
	}

	public void setFocusStyleClass(ValueExpression focusStyleClass) {
		this.focusStyleClass = focusStyleClass;
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

	public void setSelectionListener(ValueExpression selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public void setAlternateText(ValueExpression alternateText) {
		this.alternateText = alternateText;
	}

	public void setMaxTextLength(ValueExpression maxTextLength) {
		this.maxTextLength = maxTextLength;
	}

	public void setColumnNumber(ValueExpression columnNumber) {
		this.columnNumber = columnNumber;
	}

	public void setRowNumber(ValueExpression rowNumber) {
		this.rowNumber = rowNumber;
	}

	public void setIgnoreWhenFull(ValueExpression ignoreWhenFull) {
		this.ignoreWhenFull = ignoreWhenFull;
	}

	public final void setAction(ValueExpression action) {
		this.action=action;
	}

	public final void setActionListener(ValueExpression listeners) {
		this.actionListeners = listeners;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (TextAreaComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  required='"+required+"'");
			LOG.debug("  text='"+text+"'");
			LOG.debug("  textDirection='"+textDirection+"'");
			LOG.debug("  emptyMessage='"+emptyMessage+"'");
			LOG.debug("  readOnly='"+readOnly+"'");
			LOG.debug("  focusStyleClass='"+focusStyleClass+"'");
			LOG.debug("  errorStyleClass='"+errorStyleClass+"'");
			LOG.debug("  fatalStyleClass='"+fatalStyleClass+"'");
			LOG.debug("  infoStyleClass='"+infoStyleClass+"'");
			LOG.debug("  warnStyleClass='"+warnStyleClass+"'");
			LOG.debug("  alternateText='"+alternateText+"'");
			LOG.debug("  maxTextLength='"+maxTextLength+"'");
			LOG.debug("  columnNumber='"+columnNumber+"'");
			LOG.debug("  rowNumber='"+rowNumber+"'");
			LOG.debug("  ignoreWhenFull='"+ignoreWhenFull+"'");
			LOG.debug("  action='"+action+"'");
			LOG.debug("  actionListeners='"+actionListeners+"'");
		}
		if ((uiComponent instanceof TextAreaComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'TextAreaComponent'.");
		}

		super.setProperties(uiComponent);

		TextAreaComponent component = (TextAreaComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (required != null) {
			if (required.isLiteralText()==false) {
				component.setValueExpression(Properties.REQUIRED, required);

			} else {
				component.setRequired(getBool(required.getExpressionString()));
			}
		}

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

		if (emptyMessage != null) {
			if (emptyMessage.isLiteralText()==false) {
				component.setValueExpression(Properties.EMPTY_MESSAGE, emptyMessage);

			} else {
				component.setEmptyMessage(emptyMessage.getExpressionString());
			}
		}

		if (readOnly != null) {
			if (readOnly.isLiteralText()==false) {
				component.setValueExpression(Properties.READ_ONLY, readOnly);

			} else {
				component.setReadOnly(getBool(readOnly.getExpressionString()));
			}
		}

		if (valueChangeListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.VALUE_CHANGE_LISTENER_TYPE, valueChangeListeners);
		}

		if (focusStyleClass != null) {
			if (focusStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.FOCUS_STYLE_CLASS, focusStyleClass);

			} else {
				component.setFocusStyleClass(focusStyleClass.getExpressionString());
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

		if (selectionListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (alternateText != null) {
			if (alternateText.isLiteralText()==false) {
				component.setValueExpression(Properties.ALTERNATE_TEXT, alternateText);

			} else {
				component.setAlternateText(alternateText.getExpressionString());
			}
		}

		if (maxTextLength != null) {
			if (maxTextLength.isLiteralText()==false) {
				component.setValueExpression(Properties.MAX_TEXT_LENGTH, maxTextLength);

			} else {
				component.setMaxTextLength(getInt(maxTextLength.getExpressionString()));
			}
		}

		if (columnNumber != null) {
			if (columnNumber.isLiteralText()==false) {
				component.setValueExpression(Properties.COLUMN_NUMBER, columnNumber);

			} else {
				component.setColumnNumber(getInt(columnNumber.getExpressionString()));
			}
		}

		if (rowNumber != null) {
			if (rowNumber.isLiteralText()==false) {
				component.setValueExpression(Properties.ROW_NUMBER, rowNumber);

			} else {
				component.setRowNumber(getInt(rowNumber.getExpressionString()));
			}
		}

		if (ignoreWhenFull != null) {
			if (ignoreWhenFull.isLiteralText()==false) {
				component.setValueExpression(Properties.IGNORE_WHEN_FULL, ignoreWhenFull);

			} else {
				component.setIgnoreWhenFull(getBool(ignoreWhenFull.getExpressionString()));
			}
		}

		if (action != null) {
			ListenersTools1_2.parseAction(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, action);
		}

		if (actionListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, actionListeners, true);
		}
	}

	public void release() {
		required = null;
		text = null;
		textDirection = null;
		emptyMessage = null;
		readOnly = null;
		valueChangeListeners = null;
		focusStyleClass = null;
		errorStyleClass = null;
		fatalStyleClass = null;
		infoStyleClass = null;
		warnStyleClass = null;
		selectionListeners = null;
		alternateText = null;
		maxTextLength = null;
		columnNumber = null;
		rowNumber = null;
		ignoreWhenFull = null;
		action = null;
		actionListeners = null;

		super.release();
	}

}
