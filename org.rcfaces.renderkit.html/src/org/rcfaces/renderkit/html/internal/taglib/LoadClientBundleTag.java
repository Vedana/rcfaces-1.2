package org.rcfaces.renderkit.html.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.rcfaces.renderkit.html.component.LoadClientBundleComponent;

public class LoadClientBundleTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(LoadClientBundleTag.class);

	private String bundleName;
	private String baseName;
	private String serverSide;
	private String override;
	public String getComponentType() {
		return LoadClientBundleComponent.COMPONENT_TYPE;
	}

	public final String getBundleName() {
		return bundleName;
	}

	public final void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}

	public final String getBaseName() {
		return baseName;
	}

	public final void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	public final String getServerSide() {
		return serverSide;
	}

	public final void setServerSide(String serverSide) {
		this.serverSide = serverSide;
	}

	public final String getOverride() {
		return override;
	}

	public final void setOverride(String override) {
		this.override = override;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (LoadClientBundleComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  bundleName='"+bundleName+"'");
			LOG.debug("  baseName='"+baseName+"'");
			LOG.debug("  serverSide='"+serverSide+"'");
			LOG.debug("  override='"+override+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof LoadClientBundleComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'LoadClientBundleComponent'.");
		}

		LoadClientBundleComponent component = (LoadClientBundleComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (bundleName != null) {
			if (isValueReference(bundleName)) {
				ValueBinding vb = application.createValueBinding(bundleName);
				component.setBundleName(vb);
			} else {
				component.setBundleName(bundleName);
			}
		}

		if (baseName != null) {
			if (isValueReference(baseName)) {
				ValueBinding vb = application.createValueBinding(baseName);
				component.setBaseName(vb);
			} else {
				component.setBaseName(baseName);
			}
		}

		if (serverSide != null) {
			if (isValueReference(serverSide)) {
				ValueBinding vb = application.createValueBinding(serverSide);
				component.setServerSide(vb);
			} else {
				component.setServerSide(getBool(serverSide));
			}
		}

		if (override != null) {
			if (isValueReference(override)) {
				ValueBinding vb = application.createValueBinding(override);
				component.setOverride(vb);
			} else {
				component.setOverride(getBool(override));
			}
		}
	}

	public void release() {
		bundleName = null;
		baseName = null;
		serverSide = null;
		override = null;

		super.release();
	}

}
