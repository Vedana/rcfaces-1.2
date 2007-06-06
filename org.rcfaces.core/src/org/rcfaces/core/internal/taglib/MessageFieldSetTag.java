package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.MessageFieldSetComponent;
import org.rcfaces.core.internal.component.Properties;

public class MessageFieldSetTag extends FieldSetTag implements Tag {


	private static final Log LOG=LogFactory.getLog(MessageFieldSetTag.class);

	private String forValue;
	private String errorStyleClass;
	private String fatalStyleClass;
	private String infoStyleClass;
	private String warnStyleClass;
	private String errorImageURL;
	private String fatalImageURL;
	private String infoImageURL;
	private String warnImageURL;
	private String setFocusIfMessage;
	private String showActiveComponentMessage;
	public String getComponentType() {
		return MessageFieldSetComponent.COMPONENT_TYPE;
	}

	public final String getFor() {
		return forValue;
	}

	public final void setFor(String forValue) {
		this.forValue = forValue;
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

	public final String getSetFocusIfMessage() {
		return setFocusIfMessage;
	}

	public final void setSetFocusIfMessage(String setFocusIfMessage) {
		this.setFocusIfMessage = setFocusIfMessage;
	}

	public final String getShowActiveComponentMessage() {
		return showActiveComponentMessage;
	}

	public final void setShowActiveComponentMessage(String showActiveComponentMessage) {
		this.showActiveComponentMessage = showActiveComponentMessage;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (MessageFieldSetComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  forValue='"+forValue+"'");
			LOG.debug("  errorStyleClass='"+errorStyleClass+"'");
			LOG.debug("  fatalStyleClass='"+fatalStyleClass+"'");
			LOG.debug("  infoStyleClass='"+infoStyleClass+"'");
			LOG.debug("  warnStyleClass='"+warnStyleClass+"'");
			LOG.debug("  errorImageURL='"+errorImageURL+"'");
			LOG.debug("  fatalImageURL='"+fatalImageURL+"'");
			LOG.debug("  infoImageURL='"+infoImageURL+"'");
			LOG.debug("  warnImageURL='"+warnImageURL+"'");
			LOG.debug("  setFocusIfMessage='"+setFocusIfMessage+"'");
			LOG.debug("  showActiveComponentMessage='"+showActiveComponentMessage+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof MessageFieldSetComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'MessageFieldSetComponent'.");
		}

		MessageFieldSetComponent component = (MessageFieldSetComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (forValue != null) {
			if (isValueReference(forValue)) {
				ValueBinding vb = application.createValueBinding(forValue);
				component.setValueBinding(Properties.FOR, vb);

			} else {
				component.setFor(forValue);
			}
		}

		if (errorStyleClass != null) {
			if (isValueReference(errorStyleClass)) {
				ValueBinding vb = application.createValueBinding(errorStyleClass);
				component.setValueBinding(Properties.ERROR_STYLE_CLASS, vb);

			} else {
				component.setErrorStyleClass(errorStyleClass);
			}
		}

		if (fatalStyleClass != null) {
			if (isValueReference(fatalStyleClass)) {
				ValueBinding vb = application.createValueBinding(fatalStyleClass);
				component.setValueBinding(Properties.FATAL_STYLE_CLASS, vb);

			} else {
				component.setFatalStyleClass(fatalStyleClass);
			}
		}

		if (infoStyleClass != null) {
			if (isValueReference(infoStyleClass)) {
				ValueBinding vb = application.createValueBinding(infoStyleClass);
				component.setValueBinding(Properties.INFO_STYLE_CLASS, vb);

			} else {
				component.setInfoStyleClass(infoStyleClass);
			}
		}

		if (warnStyleClass != null) {
			if (isValueReference(warnStyleClass)) {
				ValueBinding vb = application.createValueBinding(warnStyleClass);
				component.setValueBinding(Properties.WARN_STYLE_CLASS, vb);

			} else {
				component.setWarnStyleClass(warnStyleClass);
			}
		}

		if (errorImageURL != null) {
			if (isValueReference(errorImageURL)) {
				ValueBinding vb = application.createValueBinding(errorImageURL);
				component.setValueBinding(Properties.ERROR_IMAGE_URL, vb);

			} else {
				component.setErrorImageURL(errorImageURL);
			}
		}

		if (fatalImageURL != null) {
			if (isValueReference(fatalImageURL)) {
				ValueBinding vb = application.createValueBinding(fatalImageURL);
				component.setValueBinding(Properties.FATAL_IMAGE_URL, vb);

			} else {
				component.setFatalImageURL(fatalImageURL);
			}
		}

		if (infoImageURL != null) {
			if (isValueReference(infoImageURL)) {
				ValueBinding vb = application.createValueBinding(infoImageURL);
				component.setValueBinding(Properties.INFO_IMAGE_URL, vb);

			} else {
				component.setInfoImageURL(infoImageURL);
			}
		}

		if (warnImageURL != null) {
			if (isValueReference(warnImageURL)) {
				ValueBinding vb = application.createValueBinding(warnImageURL);
				component.setValueBinding(Properties.WARN_IMAGE_URL, vb);

			} else {
				component.setWarnImageURL(warnImageURL);
			}
		}

		if (setFocusIfMessage != null) {
			if (isValueReference(setFocusIfMessage)) {
				ValueBinding vb = application.createValueBinding(setFocusIfMessage);
				component.setValueBinding(Properties.SET_FOCUS_IF_MESSAGE, vb);

			} else {
				component.setSetFocusIfMessage(getBool(setFocusIfMessage));
			}
		}

		if (showActiveComponentMessage != null) {
			if (isValueReference(showActiveComponentMessage)) {
				ValueBinding vb = application.createValueBinding(showActiveComponentMessage);
				component.setValueBinding(Properties.SHOW_ACTIVE_COMPONENT_MESSAGE, vb);

			} else {
				component.setShowActiveComponentMessage(getBool(showActiveComponentMessage));
			}
		}
	}

	public void release() {
		forValue = null;
		errorStyleClass = null;
		fatalStyleClass = null;
		infoStyleClass = null;
		warnStyleClass = null;
		errorImageURL = null;
		fatalImageURL = null;
		infoImageURL = null;
		warnImageURL = null;
		setFocusIfMessage = null;
		showActiveComponentMessage = null;

		super.release();
	}

}
