package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.MessageComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class MessageTag extends AbstractMessageTag implements Tag {


	private static final Log LOG=LogFactory.getLog(MessageTag.class);

	private String imageHeight;
	private String imageWidth;
	private String text;
	private String errorStyleClass;
	private String fatalStyleClass;
	private String infoStyleClass;
	private String warnStyleClass;
	private String errorImageURL;
	private String fatalImageURL;
	private String infoImageURL;
	private String warnImageURL;
	private String imageURL;
	private String setFocusIfMessage;
	private String showIfMessage;
	private String showActiveComponentMessage;
	public String getComponentType() {
		return MessageComponent.COMPONENT_TYPE;
	}

	public final String getImageHeight() {
		return imageHeight;
	}

	public final void setImageHeight(String imageHeight) {
		this.imageHeight = imageHeight;
	}

	public final String getImageWidth() {
		return imageWidth;
	}

	public final void setImageWidth(String imageWidth) {
		this.imageWidth = imageWidth;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
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

	public final String getErrorImageURL() {
		return errorImageURL;
	}

	public final void setErrorImageURL(String errorImageURL) {
		this.errorImageURL = errorImageURL;
	}

	public final String getFatalImageURL() {
		return fatalImageURL;
	}

	public final void setFatalImageURL(String fatalImageURL) {
		this.fatalImageURL = fatalImageURL;
	}

	public final String getInfoImageURL() {
		return infoImageURL;
	}

	public final void setInfoImageURL(String infoImageURL) {
		this.infoImageURL = infoImageURL;
	}

	public final String getWarnImageURL() {
		return warnImageURL;
	}

	public final void setWarnImageURL(String warnImageURL) {
		this.warnImageURL = warnImageURL;
	}

	public final String getImageURL() {
		return imageURL;
	}

	public final void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public final String getSetFocusIfMessage() {
		return setFocusIfMessage;
	}

	public final void setSetFocusIfMessage(String setFocusIfMessage) {
		this.setFocusIfMessage = setFocusIfMessage;
	}

	public final String getShowIfMessage() {
		return showIfMessage;
	}

	public final void setShowIfMessage(String showIfMessage) {
		this.showIfMessage = showIfMessage;
	}

	public final String getShowActiveComponentMessage() {
		return showActiveComponentMessage;
	}

	public final void setShowActiveComponentMessage(String showActiveComponentMessage) {
		this.showActiveComponentMessage = showActiveComponentMessage;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (MessageComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  imageHeight='"+imageHeight+"'");
			LOG.debug("  imageWidth='"+imageWidth+"'");
			LOG.debug("  text='"+text+"'");
			LOG.debug("  errorStyleClass='"+errorStyleClass+"'");
			LOG.debug("  fatalStyleClass='"+fatalStyleClass+"'");
			LOG.debug("  infoStyleClass='"+infoStyleClass+"'");
			LOG.debug("  warnStyleClass='"+warnStyleClass+"'");
			LOG.debug("  errorImageURL='"+errorImageURL+"'");
			LOG.debug("  fatalImageURL='"+fatalImageURL+"'");
			LOG.debug("  infoImageURL='"+infoImageURL+"'");
			LOG.debug("  warnImageURL='"+warnImageURL+"'");
			LOG.debug("  imageURL='"+imageURL+"'");
			LOG.debug("  setFocusIfMessage='"+setFocusIfMessage+"'");
			LOG.debug("  showIfMessage='"+showIfMessage+"'");
			LOG.debug("  showActiveComponentMessage='"+showActiveComponentMessage+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof MessageComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'MessageComponent'.");
		}

		MessageComponent component = (MessageComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (imageHeight != null) {
			if (isValueReference(imageHeight)) {
				ValueBinding vb = application.createValueBinding(imageHeight);

				component.setImageHeight(vb);
			} else {
				component.setImageHeight(getInt(imageHeight));
			}
		}

		if (imageWidth != null) {
			if (isValueReference(imageWidth)) {
				ValueBinding vb = application.createValueBinding(imageWidth);

				component.setImageWidth(vb);
			} else {
				component.setImageWidth(getInt(imageWidth));
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

		if (errorStyleClass != null) {
			if (isValueReference(errorStyleClass)) {
				ValueBinding vb = application.createValueBinding(errorStyleClass);

				component.setErrorStyleClass(vb);
			} else {
				component.setErrorStyleClass(errorStyleClass);
			}
		}

		if (fatalStyleClass != null) {
			if (isValueReference(fatalStyleClass)) {
				ValueBinding vb = application.createValueBinding(fatalStyleClass);

				component.setFatalStyleClass(vb);
			} else {
				component.setFatalStyleClass(fatalStyleClass);
			}
		}

		if (infoStyleClass != null) {
			if (isValueReference(infoStyleClass)) {
				ValueBinding vb = application.createValueBinding(infoStyleClass);

				component.setInfoStyleClass(vb);
			} else {
				component.setInfoStyleClass(infoStyleClass);
			}
		}

		if (warnStyleClass != null) {
			if (isValueReference(warnStyleClass)) {
				ValueBinding vb = application.createValueBinding(warnStyleClass);

				component.setWarnStyleClass(vb);
			} else {
				component.setWarnStyleClass(warnStyleClass);
			}
		}

		if (errorImageURL != null) {
			if (isValueReference(errorImageURL)) {
				ValueBinding vb = application.createValueBinding(errorImageURL);

				component.setErrorImageURL(vb);
			} else {
				component.setErrorImageURL(errorImageURL);
			}
		}

		if (fatalImageURL != null) {
			if (isValueReference(fatalImageURL)) {
				ValueBinding vb = application.createValueBinding(fatalImageURL);

				component.setFatalImageURL(vb);
			} else {
				component.setFatalImageURL(fatalImageURL);
			}
		}

		if (infoImageURL != null) {
			if (isValueReference(infoImageURL)) {
				ValueBinding vb = application.createValueBinding(infoImageURL);

				component.setInfoImageURL(vb);
			} else {
				component.setInfoImageURL(infoImageURL);
			}
		}

		if (warnImageURL != null) {
			if (isValueReference(warnImageURL)) {
				ValueBinding vb = application.createValueBinding(warnImageURL);

				component.setWarnImageURL(vb);
			} else {
				component.setWarnImageURL(warnImageURL);
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

		if (setFocusIfMessage != null) {
			if (isValueReference(setFocusIfMessage)) {
				ValueBinding vb = application.createValueBinding(setFocusIfMessage);
				component.setSetFocusIfMessage(vb);
			} else {
				component.setSetFocusIfMessage(getBool(setFocusIfMessage));
			}
		}

		if (showIfMessage != null) {
			if (isValueReference(showIfMessage)) {
				ValueBinding vb = application.createValueBinding(showIfMessage);
				component.setShowIfMessage(vb);
			} else {
				component.setShowIfMessage(getBool(showIfMessage));
			}
		}

		if (showActiveComponentMessage != null) {
			if (isValueReference(showActiveComponentMessage)) {
				ValueBinding vb = application.createValueBinding(showActiveComponentMessage);
				component.setShowActiveComponentMessage(vb);
			} else {
				component.setShowActiveComponentMessage(getBool(showActiveComponentMessage));
			}
		}
	}

	public void release() {
		imageHeight = null;
		imageWidth = null;
		text = null;
		errorStyleClass = null;
		fatalStyleClass = null;
		infoStyleClass = null;
		warnStyleClass = null;
		errorImageURL = null;
		fatalImageURL = null;
		infoImageURL = null;
		warnImageURL = null;
		imageURL = null;
		setFocusIfMessage = null;
		showIfMessage = null;
		showActiveComponentMessage = null;

		super.release();
	}

}
