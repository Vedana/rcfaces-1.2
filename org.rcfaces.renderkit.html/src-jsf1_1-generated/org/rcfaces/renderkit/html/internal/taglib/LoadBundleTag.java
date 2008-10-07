package org.rcfaces.renderkit.html.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.renderkit.html.component.LoadBundleComponent;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.rcfaces.renderkit.html.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;

public class LoadBundleTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(LoadBundleTag.class);

	private String bundleName;
	private String baseName;
	private String side;
	private String serverScope;
	private String override;
	public String getComponentType() {
		return LoadBundleComponent.COMPONENT_TYPE;
	}

	public final void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}

	public final void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	public final void setSide(String side) {
		this.side = side;
	}

	public final void setServerScope(String serverScope) {
		this.serverScope = serverScope;
	}

	public final void setOverride(String override) {
		this.override = override;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (LoadBundleComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  bundleName='"+bundleName+"'");
			LOG.debug("  baseName='"+baseName+"'");
			LOG.debug("  side='"+side+"'");
			LOG.debug("  serverScope='"+serverScope+"'");
			LOG.debug("  override='"+override+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof LoadBundleComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'LoadBundleComponent'.");
		}

		LoadBundleComponent component = (LoadBundleComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (bundleName != null) {
			if (isValueReference(bundleName)) {
				ValueBinding vb = application.createValueBinding(bundleName);
				component.setValueBinding(Properties.BUNDLE_NAME, vb);

			} else {
				component.setBundleName(bundleName);
			}
		}

		if (baseName != null) {
			if (isValueReference(baseName)) {
				ValueBinding vb = application.createValueBinding(baseName);
				component.setValueBinding(Properties.BASE_NAME, vb);

			} else {
				component.setBaseName(baseName);
			}
		}

		if (side != null) {
			if (isValueReference(side)) {
				ValueBinding vb = application.createValueBinding(side);
				component.setValueBinding(Properties.SIDE, vb);

			} else {
				component.setSide(side);
			}
		}

		if (serverScope != null) {
			if (isValueReference(serverScope)) {
				ValueBinding vb = application.createValueBinding(serverScope);
				component.setValueBinding(Properties.SERVER_SCOPE, vb);

			} else {
				component.setServerScope(serverScope);
			}
		}

		if (override != null) {
			if (isValueReference(override)) {
				ValueBinding vb = application.createValueBinding(override);
				component.setValueBinding(Properties.OVERRIDE, vb);

			} else {
				component.setOverride(getBool(override));
			}
		}
	}

	public void release() {
		bundleName = null;
		baseName = null;
		side = null;
		serverScope = null;
		override = null;

		super.release();
	}

}
