package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.component.RadioButtonComponent;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class RadioButtonTag extends AbstractInputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(RadioButtonTag.class);

	private String text;
	private String textPosition;
	private String selectionListeners;
	private String readOnly;
	private String selected;
	private String groupName;
	private String required;
	public String getComponentType() {
		return RadioButtonComponent.COMPONENT_TYPE;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
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

	public final String getGroupName() {
		return groupName;
	}

	public final void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public final String getRequired() {
		return required;
	}

	public final void setRequired(String required) {
		this.required = required;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (RadioButtonComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  text='"+text+"'");
			LOG.debug("  textPosition='"+textPosition+"'");
			LOG.debug("  readOnly='"+readOnly+"'");
			LOG.debug("  selected='"+selected+"'");
			LOG.debug("  groupName='"+groupName+"'");
			LOG.debug("  required='"+required+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof RadioButtonComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'RadioButtonComponent'.");
		}

		RadioButtonComponent component = (RadioButtonComponent) uiComponent;
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

		if (textPosition != null) {
			if (isValueReference(textPosition)) {
				ValueBinding vb = application.createValueBinding(textPosition);

				component.setTextPosition(vb);
			} else {
				component.setTextPosition(textPosition);
			}
		}

		if (selectionListeners != null) {
			parseActionListener(application, component, SELECTION_LISTENER_TYPE, selectionListeners);
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

		if (groupName != null) {
			if (isValueReference(groupName)) {
				ValueBinding vb = application.createValueBinding(groupName);

				component.setGroupName(vb);
			} else {
				component.setGroupName(groupName);
			}
		}

		if (required != null) {
			if (isValueReference(required)) {
				ValueBinding vb = application.createValueBinding(required);

				component.setRequired(vb);
			} else {
				component.setRequired(getBool(required));
			}
		}
	}

	public void release() {
		text = null;
		textPosition = null;
		selectionListeners = null;
		readOnly = null;
		selected = null;
		groupName = null;
		required = null;

		super.release();
	}

}
