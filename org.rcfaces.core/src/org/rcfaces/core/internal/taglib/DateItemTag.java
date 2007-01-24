package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.DateItemComponent;

public class DateItemTag extends AbstractItemTag implements Tag {


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
		if (LOG.isDebugEnabled()) {
			if (DateItemComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  text='"+text+"'");
			LOG.debug("  styleClass='"+styleClass+"'");
			LOG.debug("  date='"+date+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof DateItemComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
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
