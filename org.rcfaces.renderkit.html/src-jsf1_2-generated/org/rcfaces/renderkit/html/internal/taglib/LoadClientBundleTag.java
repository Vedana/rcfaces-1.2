package org.rcfaces.renderkit.html.internal.taglib;

import javax.faces.application.Application;
import org.rcfaces.renderkit.html.component.LoadClientBundleComponent;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class LoadClientBundleTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(LoadClientBundleTag.class);

	private ValueExpression bundleName;
	private ValueExpression baseName;
	private ValueExpression serverSide;
	private ValueExpression override;
	public String getComponentType() {
		return LoadClientBundleComponent.COMPONENT_TYPE;
	}

	public final void setBundleName(ValueExpression bundleName) {
		this.bundleName = bundleName;
	}

	public final void setBaseName(ValueExpression baseName) {
		this.baseName = baseName;
	}

	public final void setServerSide(ValueExpression serverSide) {
		this.serverSide = serverSide;
	}

	public final void setOverride(ValueExpression override) {
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

		if (bundleName != null) {
			if (bundleName.isLiteralText()==false) {
				component.setValueExpression(Properties.BUNDLE_NAME, bundleName);

			} else {
				component.setBundleName(bundleName.getExpressionString());
			}
		}

		if (baseName != null) {
			if (baseName.isLiteralText()==false) {
				component.setValueExpression(Properties.BASE_NAME, baseName);

			} else {
				component.setBaseName(baseName.getExpressionString());
			}
		}

		if (serverSide != null) {
			if (serverSide.isLiteralText()==false) {
				component.setValueExpression(Properties.SERVER_SIDE, serverSide);

			} else {
				component.setServerSide(getBool(serverSide.getExpressionString()));
			}
		}

		if (override != null) {
			if (override.isLiteralText()==false) {
				component.setValueExpression(Properties.OVERRIDE, override);

			} else {
				component.setOverride(getBool(override.getExpressionString()));
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
