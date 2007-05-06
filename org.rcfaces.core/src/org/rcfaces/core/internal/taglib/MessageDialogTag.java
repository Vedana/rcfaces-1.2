package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.MessageDialogComponent;
import org.rcfaces.core.internal.taglib.AbstractInputTag;

public class MessageDialogTag extends AbstractInputTag implements Tag {

	private static final Log LOG=LogFactory.getLog(MessageDialogTag.class);

	private String title;
	private String text;
	private String defaultValue;
	private String jsCallback;
	private String imageUrl;
	private String priority;
	
	public String getComponentType() {
		return MessageDialogComponent.COMPONENT_TYPE;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("  title='"+title+"'");
			LOG.debug("  text='"+text+"'");
			LOG.debug("  defaultValue='"+defaultValue+"'");
			LOG.debug("  imageUrl='"+imageUrl+"'");
			LOG.debug("  priority='"+priority+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof MessageDialogComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'MessageBoxComponent'.");
		}

		MessageDialogComponent component = (MessageDialogComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (title != null) {
			if (isValueReference(title)) {
				ValueBinding vb = application.createValueBinding(title);

				component.setTitle(vb);
			} else {
				component.setTitle(title);
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

		if (defaultValue != null) {
			if (isValueReference(defaultValue)) {
				ValueBinding vb = application.createValueBinding(defaultValue);

				component.setDefaultValue(vb);
			} else {
				component.setDefaultValue(defaultValue);
			}
		}

		if (jsCallback != null) {
			if (isValueReference(jsCallback)) {
				ValueBinding vb = application.createValueBinding(jsCallback);

				component.setJsCallback(vb);
			} else {
				component.setJsCallback(jsCallback);
			}
		}
		
		if (imageUrl != null) {
			if (isValueReference(imageUrl)) {
				ValueBinding vb = application.createValueBinding(imageUrl);

				component.setImageURL(vb);
			} else {
				component.setImageURL(imageUrl);
			}
		}
		
		if (priority != null) {
			if (isValueReference(priority)) {
				ValueBinding vb = application.createValueBinding(priority);

				component.setPriority(vb);
			} else {
				try {
					component.setPriority(Integer.parseInt(priority));
				} catch (NumberFormatException nfe) {
					LOG.error("bad value for messageDialog.priority : "+priority, nfe);
				}
			}
		}
	}

	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the text
	 */
	public String geText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the jsCallback
	 */
	public String getJsCallback() {
		return jsCallback;
	}

	/**
	 * @param jsCallback the jsCallback to set
	 */
	public void setJsCallback(String jsCallback) {
		this.jsCallback = jsCallback;
	}

	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void release() {
		title = null;
		text = null;
		defaultValue = null;
		jsCallback = null;

		super.release();
	}

}
