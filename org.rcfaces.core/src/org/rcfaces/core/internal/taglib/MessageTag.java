package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.MessageComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class MessageTag extends AbstractMessageTag implements Tag {


	private static final Log LOG=LogFactory.getLog(MessageTag.class);

	private String imageURL;
	private String imageHeight;
	private String imageWidth;
	private String infoStyleClass;
	private String infoImageURL;
	private String errorStyleClass;
	private String errorImageURL;
	private String warnStyleClass;
	private String warnImageURL;
	private String fatalStyleClass;
	private String fatalImageURL;
	private String bundleVar;
	private String noMessageText;
	public String getComponentType() {
		return MessageComponent.COMPONENT_TYPE;
	}

	public final String getImageURL() {
		return imageURL;
	}

	public final void setImageURL(String imageURL) {
		this.imageURL = imageURL;
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

	public final String getInfoStyleClass() {
		return infoStyleClass;
	}

	public final void setInfoStyleClass(String infoStyleClass) {
		this.infoStyleClass = infoStyleClass;
	}

	public final String getInfoImageURL() {
		return infoImageURL;
	}

	public final void setInfoImageURL(String infoImageURL) {
		this.infoImageURL = infoImageURL;
	}

	public final String getErrorStyleClass() {
		return errorStyleClass;
	}

	public final void setErrorStyleClass(String errorStyleClass) {
		this.errorStyleClass = errorStyleClass;
	}

	public final String getErrorImageURL() {
		return errorImageURL;
	}

	public final void setErrorImageURL(String errorImageURL) {
		this.errorImageURL = errorImageURL;
	}

	public final String getWarnStyleClass() {
		return warnStyleClass;
	}

	public final void setWarnStyleClass(String warnStyleClass) {
		this.warnStyleClass = warnStyleClass;
	}

	public final String getWarnImageURL() {
		return warnImageURL;
	}

	public final void setWarnImageURL(String warnImageURL) {
		this.warnImageURL = warnImageURL;
	}

	public final String getFatalStyleClass() {
		return fatalStyleClass;
	}

	public final void setFatalStyleClass(String fatalStyleClass) {
		this.fatalStyleClass = fatalStyleClass;
	}

	public final String getFatalImageURL() {
		return fatalImageURL;
	}

	public final void setFatalImageURL(String fatalImageURL) {
		this.fatalImageURL = fatalImageURL;
	}

	public final String getBundleVar() {
		return bundleVar;
	}

	public final void setBundleVar(String bundleVar) {
		this.bundleVar = bundleVar;
	}

	public final String getNoMessageText() {
		return noMessageText;
	}

	public final void setNoMessageText(String noMessageText) {
		this.noMessageText = noMessageText;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (MessageComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  imageURL='"+imageURL+"'");
			LOG.debug("  imageHeight='"+imageHeight+"'");
			LOG.debug("  imageWidth='"+imageWidth+"'");
			LOG.debug("  infoStyleClass='"+infoStyleClass+"'");
			LOG.debug("  infoImageURL='"+infoImageURL+"'");
			LOG.debug("  errorStyleClass='"+errorStyleClass+"'");
			LOG.debug("  errorImageURL='"+errorImageURL+"'");
			LOG.debug("  warnStyleClass='"+warnStyleClass+"'");
			LOG.debug("  warnImageURL='"+warnImageURL+"'");
			LOG.debug("  fatalStyleClass='"+fatalStyleClass+"'");
			LOG.debug("  fatalImageURL='"+fatalImageURL+"'");
			LOG.debug("  bundleVar='"+bundleVar+"'");
			LOG.debug("  noMessageText='"+noMessageText+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof MessageComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'MessageComponent'.");
		}

		MessageComponent component = (MessageComponent) uiComponent;
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

		if (infoStyleClass != null) {
			if (isValueReference(infoStyleClass)) {
				ValueBinding vb = application.createValueBinding(infoStyleClass);
				component.setInfoStyleClass(vb);
			} else {
				component.setInfoStyleClass(infoStyleClass);
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

		if (errorStyleClass != null) {
			if (isValueReference(errorStyleClass)) {
				ValueBinding vb = application.createValueBinding(errorStyleClass);
				component.setErrorStyleClass(vb);
			} else {
				component.setErrorStyleClass(errorStyleClass);
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

		if (warnStyleClass != null) {
			if (isValueReference(warnStyleClass)) {
				ValueBinding vb = application.createValueBinding(warnStyleClass);
				component.setWarnStyleClass(vb);
			} else {
				component.setWarnStyleClass(warnStyleClass);
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

		if (fatalStyleClass != null) {
			if (isValueReference(fatalStyleClass)) {
				ValueBinding vb = application.createValueBinding(fatalStyleClass);
				component.setFatalStyleClass(vb);
			} else {
				component.setFatalStyleClass(fatalStyleClass);
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

		if (bundleVar != null) {
			if (isValueReference(bundleVar)) {
				ValueBinding vb = application.createValueBinding(bundleVar);
				component.setBundleVar(vb);
			} else {
				component.setBundleVar(bundleVar);
			}
		}

		if (noMessageText != null) {
			if (isValueReference(noMessageText)) {
				ValueBinding vb = application.createValueBinding(noMessageText);
				component.setNoMessageText(vb);
			} else {
				component.setNoMessageText(noMessageText);
			}
		}
	}

	public void release() {
		imageURL = null;
		imageHeight = null;
		imageWidth = null;
		infoStyleClass = null;
		infoImageURL = null;
		errorStyleClass = null;
		errorImageURL = null;
		warnStyleClass = null;
		warnImageURL = null;
		fatalStyleClass = null;
		fatalImageURL = null;
		bundleVar = null;
		noMessageText = null;

		super.release();
	}

}
