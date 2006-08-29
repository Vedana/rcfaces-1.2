package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.component.ComboExComponent;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class ComboExTag extends ComboTag {

private static final Log LOG=LogFactory.getLog(ComboExTag.class);
	private String editable;
	private String imageURL;
	private String text;
	private String readOnly;
	private String maxTextLength;
	private String columnNumber;
	private String autoCompletion;
	private String popupRowNumber;
	public String getComponentType() {
		return ComboExComponent.COMPONENT_TYPE;
	}

	public final String getEditable() {
		return editable;
	}

	public final void setEditable(String editable) {
		this.editable = editable;
	}

	public final String getImageURL() {
		return imageURL;
	}

	public final void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}

	public final String getReadOnly() {
		return readOnly;
	}

	public final void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	public final String getMaxTextLength() {
		return maxTextLength;
	}

	public final void setMaxTextLength(String maxTextLength) {
		this.maxTextLength = maxTextLength;
	}

	public final String getColumnNumber() {
		return columnNumber;
	}

	public final void setColumnNumber(String columnNumber) {
		this.columnNumber = columnNumber;
	}

	public final String getAutoCompletion() {
		return autoCompletion;
	}

	public final void setAutoCompletion(String autoCompletion) {
		this.autoCompletion = autoCompletion;
	}

	public final String getPopupRowNumber() {
		return popupRowNumber;
	}

	public final void setPopupRowNumber(String popupRowNumber) {
		this.popupRowNumber = popupRowNumber;
	}

	protected void setProperties(UIComponent uiComponent) {
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ComboExComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ComboExComponent'.");
		}

		ComboExComponent component = (ComboExComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (editable != null) {
			if (isValueReference(editable)) {
				ValueBinding vb = application.createValueBinding(editable);

				component.setEditable(vb);
			} else {
				component.setEditable(getBool(editable));
			}
		}

		if (imageURL != null) {
			if (isValueReference(imageURL)) {
				ValueBinding vb = application.createValueBinding(imageURL);

				component.setImageURL(vb);
			} else {
				component.setImageURL(imageURL);
			}
		}

		if (text != null) {
			if (isValueReference(text)) {
				ValueBinding vb = application.createValueBinding(text);

				component.setText(vb);
			} else {
				component.setText(text);
			}
		}

		if (readOnly != null) {
			if (isValueReference(readOnly)) {
				ValueBinding vb = application.createValueBinding(readOnly);

				component.setReadOnly(vb);
			} else {
				component.setReadOnly(getBool(readOnly));
			}
		}

		if (maxTextLength != null) {
			if (isValueReference(maxTextLength)) {
				ValueBinding vb = application.createValueBinding(maxTextLength);
				component.setMaxTextLength(vb);
			} else {
				component.setMaxTextLength(getInt(maxTextLength));
			}
		}

		if (columnNumber != null) {
			if (isValueReference(columnNumber)) {
				ValueBinding vb = application.createValueBinding(columnNumber);
				component.setColumnNumber(vb);
			} else {
				component.setColumnNumber(getInt(columnNumber));
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

		if (popupRowNumber != null) {
			if (isValueReference(popupRowNumber)) {
				ValueBinding vb = application.createValueBinding(popupRowNumber);
				component.setPopupRowNumber(vb);
			} else {
				component.setPopupRowNumber(getInt(popupRowNumber));
			}
		}
	}

	public void release() {
		editable = null;
		imageURL = null;
		text = null;
		readOnly = null;
		maxTextLength = null;
		columnNumber = null;
		autoCompletion = null;
		popupRowNumber = null;

		super.release();
	}

}
