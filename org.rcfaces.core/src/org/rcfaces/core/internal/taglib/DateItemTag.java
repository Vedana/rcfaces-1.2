package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.component.DateItemComponent;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class DateItemTag extends AbstractItemTag {

private static final Log LOG=LogFactory.getLog(DateItemTag.class);
	private String text;
	private String styleClass;
	private String date;
	public String getComponentType() {
		return DateItemComponent.COMPONENT_TYPE;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}

	public final String getStyleClass() {
		return styleClass;
	}

	public final void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public final String getDate() {
		return date;
	}

	public final void setDate(String date) {
		this.date = date;
	}

	protected void setProperties(UIComponent uiComponent) {
		super.setProperties(uiComponent);

		if ((uiComponent instanceof DateItemComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'DateItemComponent'.");
		}

		DateItemComponent component = (DateItemComponent) uiComponent;
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

		if (styleClass != null) {
			if (isValueReference(styleClass)) {
				ValueBinding vb = application.createValueBinding(styleClass);

				component.setStyleClass(vb);
			} else {
				component.setStyleClass(styleClass);
			}
		}

		if (date != null) {
			if (isValueReference(date)) {
				ValueBinding vb = application.createValueBinding(date);
				component.setDate(vb);
			} else {
				component.setDate(date);
			}
		}
	}

	public void release() {
		text = null;
		styleClass = null;
		date = null;

		super.release();
	}

}
