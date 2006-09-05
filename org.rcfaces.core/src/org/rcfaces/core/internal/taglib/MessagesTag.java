package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.component.MessagesComponent;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class MessagesTag extends AbstractMessagesTag {


	private static final Log LOG=LogFactory.getLog(MessagesTag.class);

	private String infoStyleClass;
	private String errorStyleClass;
	private String warnStyleClass;
	private String fatalStyleClass;
	private String bundleVar;
	public String getComponentType() {
		return MessagesComponent.COMPONENT_TYPE;
	}

	public final String getInfoStyleClass() {
		return infoStyleClass;
	}

	public final void setInfoStyleClass(String infoStyleClass) {
		this.infoStyleClass = infoStyleClass;
	}

	public final String getErrorStyleClass() {
		return errorStyleClass;
	}

	public final void setErrorStyleClass(String errorStyleClass) {
		this.errorStyleClass = errorStyleClass;
	}

	public final String getWarnStyleClass() {
		return warnStyleClass;
	}

	public final void setWarnStyleClass(String warnStyleClass) {
		this.warnStyleClass = warnStyleClass;
	}

	public final String getFatalStyleClass() {
		return fatalStyleClass;
	}

	public final void setFatalStyleClass(String fatalStyleClass) {
		this.fatalStyleClass = fatalStyleClass;
	}

	public final String getBundleVar() {
		return bundleVar;
	}

	public final void setBundleVar(String bundleVar) {
		this.bundleVar = bundleVar;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (MessagesComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  infoStyleClass='"+infoStyleClass+"'");
			LOG.debug("  errorStyleClass='"+errorStyleClass+"'");
			LOG.debug("  warnStyleClass='"+warnStyleClass+"'");
			LOG.debug("  fatalStyleClass='"+fatalStyleClass+"'");
			LOG.debug("  bundleVar='"+bundleVar+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof MessagesComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'MessagesComponent'.");
		}

		MessagesComponent component = (MessagesComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (infoStyleClass != null) {
			if (isValueReference(infoStyleClass)) {
				ValueBinding vb = application.createValueBinding(infoStyleClass);
				component.setInfoStyleClass(vb);
			} else {
				component.setInfoStyleClass(infoStyleClass);
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

		if (warnStyleClass != null) {
			if (isValueReference(warnStyleClass)) {
				ValueBinding vb = application.createValueBinding(warnStyleClass);
				component.setWarnStyleClass(vb);
			} else {
				component.setWarnStyleClass(warnStyleClass);
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

		if (bundleVar != null) {
			if (isValueReference(bundleVar)) {
				ValueBinding vb = application.createValueBinding(bundleVar);
				component.setBundleVar(vb);
			} else {
				component.setBundleVar(bundleVar);
			}
		}
	}

	public void release() {
		infoStyleClass = null;
		errorStyleClass = null;
		warnStyleClass = null;
		fatalStyleClass = null;
		bundleVar = null;

		super.release();
	}

}
