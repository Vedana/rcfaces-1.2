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
	private ValueExpression autoFocus;
	private ValueExpression autoFocusFrom;
	public String getComponentType() {
		return FocusManagerComponent.COMPONENT_TYPE;
	}

	public void setFocusId(ValueExpression focusId) {
		this.focusId = focusId;
	}

	public void setSetFocusIfMessage(ValueExpression setFocusIfMessage) {
		this.setFocusIfMessage = setFocusIfMessage;
	}

	public void setAutoFocus(ValueExpression autoFocus) {
		this.autoFocus = autoFocus;
	}

	public void setAutoFocusFrom(ValueExpression autoFocusFrom) {
		this.autoFocusFrom = autoFocusFrom;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (FocusManagerComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  focusId='"+focusId+"'");
			LOG.debug("  setFocusIfMessage='"+setFocusIfMessage+"'");
			LOG.debug("  autoFocus='"+autoFocus+"'");
			LOG.debug("  autoFocusFrom='"+autoFocusFrom+"'");
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

		if (autoFocus != null) {
			if (autoFocus.isLiteralText()==false) {
				component.setValueExpression(Properties.AUTO_FOCUS, autoFocus);

			} else {
				component.setAutoFocus(getBool(autoFocus.getExpressionString()));
			}
		}

		if (autoFocusFrom != null) {
			if (autoFocusFrom.isLiteralText()==false) {
				component.setValueExpression(Properties.AUTO_FOCUS_FROM, autoFocusFrom);

			} else {
				component.setAutoFocusFrom(autoFocusFrom.getExpressionString());
			}
		}
	}

	public void release() {
		focusId = null;
		setFocusIfMessage = null;
		autoFocus = null;
		autoFocusFrom = null;

		super.release();
	}

}
