package org.rcfaces.renderkit.html.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.renderkit.html.component.LoadBundleComponent;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.renderkit.html.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class LoadBundleTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(LoadBundleTag.class);

	private ValueExpression bundleName;
	private ValueExpression baseName;
	private ValueExpression side;
	private ValueExpression serverScope;
	private ValueExpression override;
	public String getComponentType() {
		return LoadBundleComponent.COMPONENT_TYPE;
	}

	public void setBundleName(ValueExpression bundleName) {
		this.bundleName = bundleName;
	}

	public void setBaseName(ValueExpression baseName) {
		this.baseName = baseName;
	}

	public void setSide(ValueExpression side) {
		this.side = side;
	}

	public void setServerScope(ValueExpression serverScope) {
		this.serverScope = serverScope;
	}

	public void setOverride(ValueExpression override) {
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
		if ((uiComponent instanceof LoadBundleComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'LoadBundleComponent'.");
		}

		super.setProperties(uiComponent);

		LoadBundleComponent component = (LoadBundleComponent) uiComponent;
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

		if (side != null) {
			if (side.isLiteralText()==false) {
				component.setValueExpression(Properties.SIDE, side);

			} else {
				component.setSide(side.getExpressionString());
			}
		}

		if (serverScope != null) {
			if (serverScope.isLiteralText()==false) {
				component.setValueExpression(Properties.SERVER_SCOPE, serverScope);

			} else {
				component.setServerScope(serverScope.getExpressionString());
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
		side = null;
		serverScope = null;
		override = null;

		super.release();
	}

}
