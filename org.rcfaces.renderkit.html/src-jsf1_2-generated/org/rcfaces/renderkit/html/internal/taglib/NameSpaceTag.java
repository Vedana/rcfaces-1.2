package org.rcfaces.renderkit.html.internal.taglib;

import javax.faces.component.UIComponent;
import org.rcfaces.renderkit.html.component.NameSpaceComponent;
import org.rcfaces.core.internal.component.Properties;
import org.apache.commons.logging.LogFactory;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;
import javax.faces.application.Application;
import javax.el.ValueExpression;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;
import org.rcfaces.core.internal.tools.ListenersTools1_2;

public class NameSpaceTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(NameSpaceTag.class);

	private ValueExpression uri;
	private ValueExpression prefix;
	public String getComponentType() {
		return NameSpaceComponent.COMPONENT_TYPE;
	}

	public final void setUri(ValueExpression uri) {
		this.uri = uri;
	}

	public final void setPrefix(ValueExpression prefix) {
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
		super.setProperties(uiComponent);

		if ((uiComponent instanceof NameSpaceComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'NameSpaceComponent'.");
		}

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
