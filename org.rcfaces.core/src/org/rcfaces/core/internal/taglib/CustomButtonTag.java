package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.component.CustomButtonComponent;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class CustomButtonTag extends AbstractCommandTag {

private static final Log LOG=LogFactory.getLog(CustomButtonTag.class);
	private String borderType;
	private String border;
	private String selectionListeners;
	private String doubleClickListeners;
	private String readOnly;
	private String action;
	private String actionListeners;
	public String getComponentType() {
		return CustomButtonComponent.COMPONENT_TYPE;
	}

	public final String getBorderType() {
		return borderType;
	}

	public final void setBorderType(String borderType) {
		this.borderType = borderType;
	}

	public final String getBorder() {
		return border;
	}

	public final void setBorder(String border) {
		this.border = border;
	}

	public final String getSelectionListener() {
		return selectionListeners;
	}

	public final void setSelectionListener(String selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public final String getDoubleClickListener() {
		return doubleClickListeners;
	}

	public final void setDoubleClickListener(String doubleClickListeners) {
		this.doubleClickListeners = doubleClickListeners;
	}

	public final String getReadOnly() {
		return readOnly;
	}

	public final void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
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

	public final String getActionListener() {
		return actionListeners;
	}

	protected void setProperties(UIComponent uiComponent) {
		super.setProperties(uiComponent);

		if ((uiComponent instanceof CustomButtonComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'CustomButtonComponent'.");
		}

		CustomButtonComponent component = (CustomButtonComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (borderType != null) {
			if (isValueReference(borderType)) {
				ValueBinding vb = application.createValueBinding(borderType);

				component.setBorderType(vb);
			} else {
				component.setBorderType(borderType);
			}
		}

		if (border != null) {
			if (isValueReference(border)) {
				ValueBinding vb = application.createValueBinding(border);

				component.setBorder(vb);
			} else {
				component.setBorder(getBool(border));
			}
		}

		if (selectionListeners != null) {
			parseActionListener(application, component, SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (doubleClickListeners != null) {
			parseActionListener(application, component, DOUBLE_CLICK_LISTENER_TYPE, doubleClickListeners);
		}

		if (readOnly != null) {
			if (isValueReference(readOnly)) {
				ValueBinding vb = application.createValueBinding(readOnly);

				component.setReadOnly(vb);
			} else {
				component.setReadOnly(getBool(readOnly));
			}
		}

		if (action != null) {
			parseAction(application, component, SELECTION_LISTENER_TYPE, action, true);
		}

		if (actionListeners != null) {
			parseActionListener(application, component, SELECTION_LISTENER_TYPE, actionListeners, true);
		}
	}

	public void release() {
		borderType = null;
		border = null;
		selectionListeners = null;
		doubleClickListeners = null;
		readOnly = null;
		action = null;
		actionListeners = null;

		super.release();
	}

}
