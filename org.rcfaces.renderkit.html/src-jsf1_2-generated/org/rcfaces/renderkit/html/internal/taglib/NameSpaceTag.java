package org.rcfaces.renderkit.html.internal.taglib;

import javax.faces.application.Application;
import org.rcfaces.renderkit.html.component.NameSpaceComponent;
import javax.faces.component.UIComponent;
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

public class NameSpaceTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(NameSpaceTag.class);

	private ValueExpression uri;
	private ValueExpression prefix;
	public String getComponentType() {
		return NameSpaceComponent.COMPONENT_TYPE;
	}

	public void setUri(ValueExpression uri) {
		this.uri = uri;
	}

	public void setPrefix(ValueExpression prefix) {
		this.prefix = prefix;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (NameSpaceComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  uri='"+uri+"'");
			LOG.debug("  prefix='"+prefix+"'");
		}
		if ((uiComponent instanceof NameSpaceComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'NameSpaceComponent'.");
		}

		super.setProperties(uiComponent);

		NameSpaceComponent component = (NameSpaceComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (uri != null) {
			if (uri.isLiteralText()==false) {
				component.setValueExpression(Properties.URI, uri);

			} else {
				component.setUri(uri.getExpressionString());
			}
		}

		if (prefix != null) {
			if (prefix.isLiteralText()==false) {
				component.setValueExpression(Properties.PREFIX, prefix);

			} else {
				component.setPrefix(prefix.getExpressionString());
			}
		}
	}

	public void release() {
		uri = null;
		prefix = null;

		super.release();
	}

}
