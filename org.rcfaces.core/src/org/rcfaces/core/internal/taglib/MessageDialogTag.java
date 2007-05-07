package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import org.rcfaces.core.component.MessageDialogComponent;
import javax.faces.application.Application;

public class MessageDialogTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(MessageDialogTag.class);

	private String imageURL;
	private String styleClass;
	private String text;
	private String visible;
	private String title;
	private String defaultValue;
	private String callback;
	private String priority;
	private String value;
	private String converter;
	public String getComponentType() {
		return MessageDialogComponent.COMPONENT_TYPE;
	}

	public final String getImageURL() {
		return imageURL;
	}

	public final void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public final String getStyleClass() {
		return styleClass;
	}

	public final void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}

	public final String getVisible() {
		return visible;
	}

	public final void setVisible(String visible) {
		this.visible = visible;
	}

	public final String getTitle() {
		return title;
	}

	public final void setTitle(String title) {
		this.title = title;
	}

	public final String getDefaultValue() {
		return defaultValue;
	}

	public final void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public final String getCallback() {
		return callback;
	}

	public final void setCallback(String callback) {
		this.callback = callback;
	}

	public final String getPriority() {
		return priority;
	}

	public final void setPriority(String priority) {
		this.priority = priority;
	}

	public final String getValue() {
		return value;
	}

	public final void setValue(String value) {
		this.value = value;
	}

	public final String getConverter() {
		return converter;
	}

	public final void setConverter(String converter) {
		this.converter = converter;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (MessageDialogComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  imageURL='"+imageURL+"'");
			LOG.debug("  styleClass='"+styleClass+"'");
			LOG.debug("  text='"+text+"'");
			LOG.debug("  visible='"+visible+"'");
			LOG.debug("  title='"+title+"'");
			LOG.debug("  defaultValue='"+defaultValue+"'");
			LOG.debug("  callback='"+callback+"'");
			LOG.debug("  priority='"+priority+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof MessageDialogComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'MessageDialogComponent'.");
		}

		MessageDialogComponent component = (MessageDialogComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (imageURL != null) {
			if (isValueReference(imageURL)) {
				ValueBinding vb = application.createValueBinding(imageURL);

				component.setImageURL(vb);
			} else {
				component.setImageURL(imageURL);
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

		if (text != null) {
			if (isValueReference(text)) {
				ValueBinding vb = application.createValueBinding(text);

				component.setText(vb);
			} else {
				component.setText(text);
			}
		}

		if (visible != null) {
			if (isValueReference(visible)) {
				ValueBinding vb = application.createValueBinding(visible);

				component.setVisible(vb);
			} else {
				component.setVisible(getBool(visible));
			}
		}

		if (title != null) {
			if (isValueReference(title)) {
				ValueBinding vb = application.createValueBinding(title);
				component.setTitle(vb);
			} else {
				component.setTitle(title);
			}
		}

		if (defaultValue != null) {
			if (isValueReference(defaultValue)) {
				ValueBinding vb = application.createValueBinding(defaultValue);
				component.setDefaultValue(vb);
			} else {
				component.setDefaultValue(defaultValue);
			}
		}

		if (callback != null) {
			if (isValueReference(callback)) {
				ValueBinding vb = application.createValueBinding(callback);
				component.setCallback(vb);
			} else {
				component.setCallback(callback);
			}
		}

		if (priority != null) {
			if (isValueReference(priority)) {
				ValueBinding vb = application.createValueBinding(priority);
				component.setPriority(vb);
			} else {
				component.setPriority(getInt(priority));
			}
		}

		if (value != null) {
			if (isValueReference(value)) {
				ValueBinding vb = application.createValueBinding(value);
				component.setValue(vb);
			} else {
				component.setValue(value);
			}
		}

		if (converter != null) {
			if (isValueReference(converter)) {
				ValueBinding vb = application.createValueBinding(converter);
				component.setConverter(vb);
			} else {
				component.setConverter(converter);
			}
		}
	}

	public void release() {
		imageURL = null;
		styleClass = null;
		text = null;
		visible = null;
		title = null;
		defaultValue = null;
		callback = null;
		priority = null;
		value = null;
		converter = null;

		super.release();
	}

}
