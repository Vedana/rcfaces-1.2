package org.rcfaces.renderkit.html.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.LogFactory;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.rcfaces.renderkit.html.component.CssStyleItemComponent;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.apache.commons.logging.Log;
import org.rcfaces.core.internal.taglib.FileItemTag;
import javax.faces.component.UIViewRoot;

public class CssStyleItemTag extends FileItemTag implements Tag {


	private static final Log LOG=LogFactory.getLog(CssStyleItemTag.class);

	private ValueExpression userAgent;
	public String getComponentType() {
		return CssStyleItemComponent.COMPONENT_TYPE;
	}

	public final void setUserAgent(ValueExpression userAgent) {
		this.userAgent = userAgent;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (CssStyleItemComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  userAgent='"+userAgent+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof CssStyleItemComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'CssStyleItemComponent'.");
		}

		CssStyleItemComponent component = (CssStyleItemComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (userAgent != null) {
			if (userAgent.isLiteralText()==false) {
				component.setValueExpression(Properties.USER_AGENT, userAgent);

			} else {
				component.setUserAgent(userAgent.getExpressionString());
			}
		}
	}

	public void release() {
		userAgent = null;

		super.release();
	}

}
