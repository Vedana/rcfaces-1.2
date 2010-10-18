package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.FocusManagerComponent;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class FocusManagerTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(FocusManagerTag.class);

	private ValueExpression focusId;
	private ValueExpression setFocusIfMessage;
	public String getComponentType() {
		return FocusManagerComponent.COMPONENT_TYPE;
	}

	public final void setFocusId(ValueExpression focusId) {
		this.focusId = focusId;
	}

	public final void setSetFocusIfMessage(ValueExpression setFocusIfMessage) {
		this.setFocusIfMessage = setFocusIfMessage;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (FocusManagerComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  focusId='"+focusId+"'");
			LOG.debug("  setFocusIfMessage='"+setFocusIfMessage+"'");
		}
		if ((uiComponent instanceof FocusManagerComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'FocusManagerComponent'.");
		}

		super.setProperties(uiComponent);

		FocusManagerComponent component = (FocusManagerComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (focusId != null) {
			if (focusId.isLiteralText()==false) {
				component.setValueExpression(Properties.FOCUS_ID, focusId);

			} else {
				component.setFocusId(focusId.getExpressionString());
			}
		}

		if (setFocusIfMessage != null) {
			if (setFocusIfMessage.isLiteralText()==false) {
				component.setValueExpression(Properties.SET_FOCUS_IF_MESSAGE, setFocusIfMessage);

			} else {
				component.setSetFocusIfMessage(getBool(setFocusIfMessage.getExpressionString()));
			}
		}
	}

	public void release() {
		focusId = null;
		setFocusIfMessage = null;

		super.release();
	}

}
