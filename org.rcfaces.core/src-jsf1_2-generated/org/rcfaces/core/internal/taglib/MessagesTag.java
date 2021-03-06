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
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class MessagesTag extends AbstractMessagesTag implements Tag {


	private static final Log LOG=LogFactory.getLog(MessagesTag.class);

	private ValueExpression errorStyleClass;
	private ValueExpression fatalStyleClass;
	private ValueExpression infoStyleClass;
	private ValueExpression warnStyleClass;
	private ValueExpression caption;
	private ValueExpression showIfMessage;
	private ValueExpression showActiveComponentMessage;
	private ValueExpression maxCount;
	public String getComponentType() {
		return MessagesComponent.COMPONENT_TYPE;
	}

	public void setErrorStyleClass(ValueExpression errorStyleClass) {
		this.errorStyleClass = errorStyleClass;
	}

	public void setFatalStyleClass(ValueExpression fatalStyleClass) {
		this.fatalStyleClass = fatalStyleClass;
	}

	public void setInfoStyleClass(ValueExpression infoStyleClass) {
		this.infoStyleClass = infoStyleClass;
	}

	public void setWarnStyleClass(ValueExpression warnStyleClass) {
		this.warnStyleClass = warnStyleClass;
	}

	public void setCaption(ValueExpression caption) {
		this.caption = caption;
	}

	public void setShowIfMessage(ValueExpression showIfMessage) {
		this.showIfMessage = showIfMessage;
	}

	public void setShowActiveComponentMessage(ValueExpression showActiveComponentMessage) {
		this.showActiveComponentMessage = showActiveComponentMessage;
	}

	public void setMaxCount(ValueExpression maxCount) {
		this.maxCount = maxCount;
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
			LOG.debug("  caption='"+caption+"'");
			LOG.debug("  showIfMessage='"+showIfMessage+"'");
			LOG.debug("  showActiveComponentMessage='"+showActiveComponentMessage+"'");
			LOG.debug("  maxCount='"+maxCount+"'");
		}
		if ((uiComponent instanceof MessagesComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'MessagesComponent'.");
		}

		super.setProperties(uiComponent);

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

		if (caption != null) {
			if (caption.isLiteralText()==false) {
				component.setValueExpression(Properties.CAPTION, caption);

			} else {
				component.setCaption(caption.getExpressionString());
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

		if (maxCount != null) {
			if (maxCount.isLiteralText()==false) {
				component.setValueExpression(Properties.MAX_COUNT, maxCount);

			} else {
				component.setMaxCount(getInt(maxCount.getExpressionString()));
			}
		}
	}

	public void release() {
		errorStyleClass = null;
		fatalStyleClass = null;
		infoStyleClass = null;
		warnStyleClass = null;
		caption = null;
		showIfMessage = null;
		showActiveComponentMessage = null;
		maxCount = null;

		super.release();
	}

}
