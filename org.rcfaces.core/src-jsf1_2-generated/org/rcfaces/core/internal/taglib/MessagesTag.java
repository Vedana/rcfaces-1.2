package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.MessagesComponent;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class MessagesTag extends AbstractMessagesTag implements Tag {


	private static final Log LOG=LogFactory.getLog(MessagesTag.class);

	private ValueExpression errorStyleClass;
	private ValueExpression fatalStyleClass;
	private ValueExpression infoStyleClass;
	private ValueExpression warnStyleClass;
	private ValueExpression showIfMessage;
	private ValueExpression showActiveComponentMessage;
	public String getComponentType() {
		return MessagesComponent.COMPONENT_TYPE;
	}

	public final void setErrorStyleClass(ValueExpression errorStyleClass) {
		this.errorStyleClass = errorStyleClass;
	}

	public final void setFatalStyleClass(ValueExpression fatalStyleClass) {
		this.fatalStyleClass = fatalStyleClass;
	}

	public final void setInfoStyleClass(ValueExpression infoStyleClass) {
		this.infoStyleClass = infoStyleClass;
	}

	public final void setWarnStyleClass(ValueExpression warnStyleClass) {
		this.warnStyleClass = warnStyleClass;
	}

	public final void setShowIfMessage(ValueExpression showIfMessage) {
		this.showIfMessage = showIfMessage;
	}

	public final void setShowActiveComponentMessage(ValueExpression showActiveComponentMessage) {
		this.showActiveComponentMessage = showActiveComponentMessage;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (MessagesComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  errorStyleClass='"+errorStyleClass+"'");
			LOG.debug("  fatalStyleClass='"+fatalStyleClass+"'");
			LOG.debug("  infoStyleClass='"+infoStyleClass+"'");
			LOG.debug("  warnStyleClass='"+warnStyleClass+"'");
			LOG.debug("  showIfMessage='"+showIfMessage+"'");
			LOG.debug("  showActiveComponentMessage='"+showActiveComponentMessage+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof MessagesComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'MessagesComponent'.");
		}

		MessagesComponent component = (MessagesComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (errorStyleClass != null) {
			if (errorStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.ERROR_STYLE_CLASS, errorStyleClass);

			} else {
				component.setErrorStyleClass(errorStyleClass.getExpressionString());
			}
		}

		if (fatalStyleClass != null) {
			if (fatalStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.FATAL_STYLE_CLASS, fatalStyleClass);

			} else {
				component.setFatalStyleClass(fatalStyleClass.getExpressionString());
			}
		}

		if (infoStyleClass != null) {
			if (infoStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.INFO_STYLE_CLASS, infoStyleClass);

			} else {
				component.setInfoStyleClass(infoStyleClass.getExpressionString());
			}
		}

		if (warnStyleClass != null) {
			if (warnStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.WARN_STYLE_CLASS, warnStyleClass);

			} else {
				component.setWarnStyleClass(warnStyleClass.getExpressionString());
			}
		}

		if (showIfMessage != null) {
			if (showIfMessage.isLiteralText()==false) {
				component.setValueExpression(Properties.SHOW_IF_MESSAGE, showIfMessage);

			} else {
				component.setShowIfMessage(getBool(showIfMessage.getExpressionString()));
			}
		}

		if (showActiveComponentMessage != null) {
			if (showActiveComponentMessage.isLiteralText()==false) {
				component.setValueExpression(Properties.SHOW_ACTIVE_COMPONENT_MESSAGE, showActiveComponentMessage);

			} else {
				component.setShowActiveComponentMessage(getBool(showActiveComponentMessage.getExpressionString()));
			}
		}
	}

	public void release() {
		errorStyleClass = null;
		fatalStyleClass = null;
		infoStyleClass = null;
		warnStyleClass = null;
		showIfMessage = null;
		showActiveComponentMessage = null;

		super.release();
	}

}
