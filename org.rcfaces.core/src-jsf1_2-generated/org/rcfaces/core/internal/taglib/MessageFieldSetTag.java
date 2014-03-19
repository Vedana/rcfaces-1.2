package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.MessageFieldSetComponent;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class MessageFieldSetTag extends FieldSetTag implements Tag {


	private static final Log LOG=LogFactory.getLog(MessageFieldSetTag.class);

	private ValueExpression forValue;
	private ValueExpression errorStyleClass;
	private ValueExpression fatalStyleClass;
	private ValueExpression infoStyleClass;
	private ValueExpression warnStyleClass;
	private ValueExpression errorImageURL;
	private ValueExpression fatalImageURL;
	private ValueExpression infoImageURL;
	private ValueExpression warnImageURL;
	private ValueExpression setFocusIfMessage;
	private ValueExpression showActiveComponentMessage;
	public String getComponentType() {
		return MessageFieldSetComponent.COMPONENT_TYPE;
	}

	public void setFor(ValueExpression forValue) {
		this.forValue = forValue;
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

	public void setErrorImageURL(ValueExpression errorImageURL) {
		this.errorImageURL = errorImageURL;
	}

	public void setFatalImageURL(ValueExpression fatalImageURL) {
		this.fatalImageURL = fatalImageURL;
	}

	public void setInfoImageURL(ValueExpression infoImageURL) {
		this.infoImageURL = infoImageURL;
	}

	public void setWarnImageURL(ValueExpression warnImageURL) {
		this.warnImageURL = warnImageURL;
	}

	public void setSetFocusIfMessage(ValueExpression setFocusIfMessage) {
		this.setFocusIfMessage = setFocusIfMessage;
	}

	public void setShowActiveComponentMessage(ValueExpression showActiveComponentMessage) {
		this.showActiveComponentMessage = showActiveComponentMessage;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (MessageFieldSetComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  forValue='"+forValue+"'");
			LOG.debug("  errorStyleClass='"+errorStyleClass+"'");
			LOG.debug("  fatalStyleClass='"+fatalStyleClass+"'");
			LOG.debug("  infoStyleClass='"+infoStyleClass+"'");
			LOG.debug("  warnStyleClass='"+warnStyleClass+"'");
			LOG.debug("  errorImageURL='"+errorImageURL+"'");
			LOG.debug("  fatalImageURL='"+fatalImageURL+"'");
			LOG.debug("  infoImageURL='"+infoImageURL+"'");
			LOG.debug("  warnImageURL='"+warnImageURL+"'");
			LOG.debug("  setFocusIfMessage='"+setFocusIfMessage+"'");
			LOG.debug("  showActiveComponentMessage='"+showActiveComponentMessage+"'");
		}
		if ((uiComponent instanceof MessageFieldSetComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'MessageFieldSetComponent'.");
		}

		super.setProperties(uiComponent);

		MessageFieldSetComponent component = (MessageFieldSetComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (forValue != null) {
			if (forValue.isLiteralText()==false) {
				component.setValueExpression(Properties.FOR, forValue);

			} else {
				component.setFor(forValue.getExpressionString());
			}
		}

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

		if (errorImageURL != null) {
			if (errorImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.ERROR_IMAGE_URL, errorImageURL);

			} else {
				component.setErrorImageURL(errorImageURL.getExpressionString());
			}
		}

		if (fatalImageURL != null) {
			if (fatalImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.FATAL_IMAGE_URL, fatalImageURL);

			} else {
				component.setFatalImageURL(fatalImageURL.getExpressionString());
			}
		}

		if (infoImageURL != null) {
			if (infoImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.INFO_IMAGE_URL, infoImageURL);

			} else {
				component.setInfoImageURL(infoImageURL.getExpressionString());
			}
		}

		if (warnImageURL != null) {
			if (warnImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.WARN_IMAGE_URL, warnImageURL);

			} else {
				component.setWarnImageURL(warnImageURL.getExpressionString());
			}
		}

		if (setFocusIfMessage != null) {
			if (setFocusIfMessage.isLiteralText()==false) {
				component.setValueExpression(Properties.SET_FOCUS_IF_MESSAGE, setFocusIfMessage);

			} else {
				component.setSetFocusIfMessage(getBool(setFocusIfMessage.getExpressionString()));
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
		forValue = null;
		errorStyleClass = null;
		fatalStyleClass = null;
		infoStyleClass = null;
		warnStyleClass = null;
		errorImageURL = null;
		fatalImageURL = null;
		infoImageURL = null;
		warnImageURL = null;
		setFocusIfMessage = null;
		showActiveComponentMessage = null;

		super.release();
	}

}
