package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.CheckButtonComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class CheckButtonTag extends AbstractInputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(CheckButtonTag.class);

	private String text;
	private String textDirection;
	private String textPosition;
	private String selectionListeners;
	private String readOnly;
	private String alternateText;
	private String focusStyleClass;
	private String errorStyleClass;
	private String fatalStyleClass;
	private String infoStyleClass;
	private String warnStyleClass;
	private String selected;
	public String getComponentType() {
		return CheckButtonComponent.COMPONENT_TYPE;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}

	public final String getTextDirection() {
		return textDirection;
	}

	public final void setTextDirection(String textDirection) {
		this.textDirection = textDirection;
	}

	public final String getTextPosition() {
		return textPosition;
	}

	public final void setTextPosition(String textPosition) {
		this.textPosition = textPosition;
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

	public final String getAlternateText() {
		return alternateText;
	}

	public final void setAlternateText(String alternateText) {
		this.alternateText = alternateText;
	}

	public final String getFocusStyleClass() {
		return focusStyleClass;
	}

	public final void setFocusStyleClass(String focusStyleClass) {
		this.focusStyleClass = focusStyleClass;
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

	public final String getSelected() {
		return selected;
	}

	public final void setSelected(String selected) {
		this.selected = selected;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (CheckButtonComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  text='"+text+"'");
			LOG.debug("  textDirection='"+textDirection+"'");
			LOG.debug("  textPosition='"+textPosition+"'");
			LOG.debug("  readOnly='"+readOnly+"'");
			LOG.debug("  alternateText='"+alternateText+"'");
			LOG.debug("  focusStyleClass='"+focusStyleClass+"'");
			LOG.debug("  errorStyleClass='"+errorStyleClass+"'");
			LOG.debug("  fatalStyleClass='"+fatalStyleClass+"'");
			LOG.debug("  infoStyleClass='"+infoStyleClass+"'");
			LOG.debug("  warnStyleClass='"+warnStyleClass+"'");
			LOG.debug("  selected='"+selected+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof CheckButtonComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'CheckButtonComponent'.");
		}

		CheckButtonComponent component = (CheckButtonComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (text != null) {
			if (isValueReference(text)) {
				ValueBinding vb = application.createValueBinding(text);
				component.setValueBinding(Properties.TEXT, vb);

			} else {
				component.setText(text);
			}
		}

		if (textDirection != null) {
			if (isValueReference(textDirection)) {
				ValueBinding vb = application.createValueBinding(textDirection);
				component.setValueBinding(Properties.TEXT_DIRECTION, vb);

			} else {
				component.setTextDirection(getInt(textDirection));
			}
		}

		if (textPosition != null) {
			if (isValueReference(textPosition)) {
				ValueBinding vb = application.createValueBinding(textPosition);
				component.setValueBinding(Properties.TEXT_POSITION, vb);

			} else {
				component.setTextPosition(textPosition);
			}
		}

		if (selectionListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (readOnly != null) {
			if (isValueReference(readOnly)) {
				ValueBinding vb = application.createValueBinding(readOnly);
				component.setValueBinding(Properties.READ_ONLY, vb);

			} else {
				component.setReadOnly(getBool(readOnly));
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

		if (focusStyleClass != null) {
			if (isValueReference(focusStyleClass)) {
				ValueBinding vb = application.createValueBinding(focusStyleClass);
				component.setValueBinding(Properties.FOCUS_STYLE_CLASS, vb);

			} else {
				component.setFocusStyleClass(focusStyleClass);
			}
		}

		if (errorStyleClass != null) {
			if (isValueReference(errorStyleClass)) {
				ValueBinding vb = application.createValueBinding(errorStyleClass);
				component.setValueBinding(Properties.ERROR_STYLE_CLASS, vb);

			} else {
				component.setErrorStyleClass(errorStyleClass);
			}
		}

		if (fatalStyleClass != null) {
			if (isValueReference(fatalStyleClass)) {
				ValueBinding vb = application.createValueBinding(fatalStyleClass);
				component.setValueBinding(Properties.FATAL_STYLE_CLASS, vb);

			} else {
				component.setFatalStyleClass(fatalStyleClass);
			}
		}

		if (infoStyleClass != null) {
			if (isValueReference(infoStyleClass)) {
				ValueBinding vb = application.createValueBinding(infoStyleClass);
				component.setValueBinding(Properties.INFO_STYLE_CLASS, vb);

			} else {
				component.setInfoStyleClass(infoStyleClass);
			}
		}

		if (warnStyleClass != null) {
			if (isValueReference(warnStyleClass)) {
				ValueBinding vb = application.createValueBinding(warnStyleClass);
				component.setValueBinding(Properties.WARN_STYLE_CLASS, vb);

			} else {
				component.setWarnStyleClass(warnStyleClass);
			}
		}

		if (selected != null) {
			if (isValueReference(selected)) {
				ValueBinding vb = application.createValueBinding(selected);
				component.setValueBinding(Properties.SELECTED, vb);

			} else {
				component.setSelected(getBool(selected));
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
		focusStyleClass = null;
		errorStyleClass = null;
		fatalStyleClass = null;
		infoStyleClass = null;
		warnStyleClass = null;
		selected = null;

		super.release();
	}

}
