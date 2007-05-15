package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.CheckButtonComponent;
import org.rcfaces.core.internal.tools.ListenersTools;

public class CheckButtonTag extends AbstractInputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(CheckButtonTag.class);

	private String text;
	private String textDirection;
	private String textPosition;
	private String selectionListeners;
	private String readOnly;
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

				component.setText(vb);
			} else {
				component.setText(text);
			}
		}

		if (textDirection != null) {
			if (isValueReference(textDirection)) {
				ValueBinding vb = application.createValueBinding(textDirection);

				component.setTextDirection(vb);
			} else {
				component.setTextDirection(getInt(textDirection));
			}
		}

		if (textPosition != null) {
			if (isValueReference(textPosition)) {
				ValueBinding vb = application.createValueBinding(textPosition);

				component.setTextPosition(vb);
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

				component.setReadOnly(vb);
			} else {
				component.setReadOnly(getBool(readOnly));
			}
		}

		if (selected != null) {
			if (isValueReference(selected)) {
				ValueBinding vb = application.createValueBinding(selected);

				component.setSelected(vb);
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
		selected = null;

		super.release();
	}

}
