package org.rcfaces.renderkit.svg.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.renderkit.svg.component.NodeComponent;
import org.rcfaces.renderkit.svg.component.Properties;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;

public abstract class NodeTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(NodeTag.class);

	private ValueExpression rendered;
	private ValueExpression targetId;
	public final void setRendered(ValueExpression rendered) {
		this.rendered = rendered;
	}

	public final void setTargetId(ValueExpression targetId) {
		this.targetId = targetId;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("  rendered='"+rendered+"'");
			LOG.debug("  targetId='"+targetId+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof NodeComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'NodeComponent'.");
		}

		NodeComponent component = (NodeComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (rendered != null) {
			if (rendered.isLiteralText()==false) {
				component.setValueExpression(Properties.RENDERED, rendered);

			} else {
				component.setRendered(getBool(rendered.getExpressionString()));
			}
		}

		if (targetId != null) {
			if (targetId.isLiteralText()==false) {
				component.setValueExpression(Properties.TARGET_ID, targetId);

			} else {
				component.setTargetId(targetId.getExpressionString());
			}
		}
	}

	public void release() {
		rendered = null;
		targetId = null;

		super.release();
	}

}
