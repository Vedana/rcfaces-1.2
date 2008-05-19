package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import javax.el.ValueExpression;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;
import org.rcfaces.core.component.FocusManagerComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

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
		super.setProperties(uiComponent);

		if ((uiComponent instanceof FocusManagerComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'FocusManagerComponent'.");
		}

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
