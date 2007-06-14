package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.component.MessagesComponent;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class MessagesTag extends AbstractMessagesTag implements Tag {


	private static final Log LOG=LogFactory.getLog(MessagesTag.class);

	private String errorStyleClass;
	private String fatalStyleClass;
	private String infoStyleClass;
	private String warnStyleClass;
	private String showIfMessage;
	private String showActiveComponentMessage;
	public String getComponentType() {
		return MessagesComponent.COMPONENT_TYPE;
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
			if (MessagesComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  errorStyleClass='"+errorStyleClass+"'");
			LOG.debug("  fatalStyleClass='"+fatalStyleClass+"'");
			LOG.debug("  infoStyleClass='"+infoStyleClass+"'");
			LOG.debug("  warnStyleClass='"+warnStyleClass+"'");
			LOG.debug("  showIfMessage='"+showIfMessage+"'");
			LOG.debug("  showActiveComponentMessage='"+showActiveComponentMessage+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof MessagesComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'MessagesComponent'.");
		}

		MessagesComponent component = (MessagesComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

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

		if (showIfMessage != null) {
			if (isValueReference(showIfMessage)) {
				ValueBinding vb = application.createValueBinding(showIfMessage);
				component.setValueBinding(Properties.SHOW_IF_MESSAGE, vb);

			} else {
				component.setShowIfMessage(getBool(showIfMessage));
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
		errorStyleClass = null;
		fatalStyleClass = null;
		infoStyleClass = null;
		warnStyleClass = null;
		showIfMessage = null;
		showActiveComponentMessage = null;

		super.release();
	}

}
