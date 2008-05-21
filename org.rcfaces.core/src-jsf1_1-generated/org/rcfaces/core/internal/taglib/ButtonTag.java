package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.ButtonComponent;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class ButtonTag extends AbstractCommandTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ButtonTag.class);

	private String text;
	private String textDirection;
	private String selectionListeners;
	private String readOnly;
	private String alternateText;
	private String focusStyleClass;
	private String action;
	private String actionListeners;
	public String getComponentType() {
		return ButtonComponent.COMPONENT_TYPE;
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

	public final void setAction(String action) {
		this.action=action;
	}

	public final String getAction() {
		return action;
	}

	public final void setActionListener(String listeners) {
		this.actionListeners = listeners;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ButtonComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  text='"+text+"'");
			LOG.debug("  textDirection='"+textDirection+"'");
			LOG.debug("  readOnly='"+readOnly+"'");
			LOG.debug("  alternateText='"+alternateText+"'");
			LOG.debug("  focusStyleClass='"+focusStyleClass+"'");
			LOG.debug("  action='"+action+"'");
			LOG.debug("  actionListeners='"+actionListeners+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ButtonComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ButtonComponent'.");
		}

		ButtonComponent component = (ButtonComponent) uiComponent;
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

		if (action != null) {
			ListenersTools1_1.parseAction(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, action);
		}

		if (actionListeners != null) {
			ListenersTools1_1.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, actionListeners, true);
		}
	}

	public void release() {
		text = null;
		textDirection = null;
		selectionListeners = null;
		readOnly = null;
		alternateText = null;
		focusStyleClass = null;
		action = null;
		actionListeners = null;

		super.release();
	}

}
