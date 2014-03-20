package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.component.MessageComponent;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class MessageTag extends AbstractMessageTag implements Tag {


	private static final Log LOG=LogFactory.getLog(MessageTag.class);

	private ValueExpression imageHeight;
	private ValueExpression imageWidth;
	private ValueExpression text;
	private ValueExpression errorStyleClass;
	private ValueExpression fatalStyleClass;
	private ValueExpression infoStyleClass;
	private ValueExpression warnStyleClass;
	private ValueExpression errorImageURL;
	private ValueExpression fatalImageURL;
	private ValueExpression infoImageURL;
	private ValueExpression warnImageURL;
	private ValueExpression imageURL;
	private ValueExpression caption;
	private ValueExpression showIfMessage;
	private ValueExpression showActiveComponentMessage;
	public String getComponentType() {
		return MessageComponent.COMPONENT_TYPE;
	}

	public void setImageHeight(ValueExpression imageHeight) {
		this.imageHeight = imageHeight;
	}

	public void setImageWidth(ValueExpression imageWidth) {
		this.imageWidth = imageWidth;
	}

	public void setText(ValueExpression text) {
		this.text = text;
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

	public void setImageURL(ValueExpression imageURL) {
		this.imageURL = imageURL;
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

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (MessageComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  imageHeight='"+imageHeight+"'");
			LOG.debug("  imageWidth='"+imageWidth+"'");
			LOG.debug("  text='"+text+"'");
			LOG.debug("  errorStyleClass='"+errorStyleClass+"'");
			LOG.debug("  fatalStyleClass='"+fatalStyleClass+"'");
			LOG.debug("  infoStyleClass='"+infoStyleClass+"'");
			LOG.debug("  warnStyleClass='"+warnStyleClass+"'");
			LOG.debug("  errorImageURL='"+errorImageURL+"'");
			LOG.debug("  fatalImageURL='"+fatalImageURL+"'");
			LOG.debug("  infoImageURL='"+infoImageURL+"'");
			LOG.debug("  warnImageURL='"+warnImageURL+"'");
			LOG.debug("  imageURL='"+imageURL+"'");
			LOG.debug("  caption='"+caption+"'");
			LOG.debug("  showIfMessage='"+showIfMessage+"'");
			LOG.debug("  showActiveComponentMessage='"+showActiveComponentMessage+"'");
		}
		if ((uiComponent instanceof MessageComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'MessageComponent'.");
		}

		super.setProperties(uiComponent);

		MessageComponent component = (MessageComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (imageHeight != null) {
			if (imageHeight.isLiteralText()==false) {
				component.setValueExpression(Properties.IMAGE_HEIGHT, imageHeight);

			} else {
				component.setImageHeight(getInt(imageHeight.getExpressionString()));
			}
		}

		if (imageWidth != null) {
			if (imageWidth.isLiteralText()==false) {
				component.setValueExpression(Properties.IMAGE_WIDTH, imageWidth);

			} else {
				component.setImageWidth(getInt(imageWidth.getExpressionString()));
			}
		}

		if (text != null) {
			if (text.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT, text);

			} else {
				component.setText(text.getExpressionString());
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

		if (imageURL != null) {
			if (imageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.IMAGE_URL, imageURL);

			} else {
				component.setImageURL(imageURL.getExpressionString());
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
	}

	public void release() {
		imageHeight = null;
		imageWidth = null;
		text = null;
		errorStyleClass = null;
		fatalStyleClass = null;
		infoStyleClass = null;
		warnStyleClass = null;
		errorImageURL = null;
		fatalImageURL = null;
		infoImageURL = null;
		warnImageURL = null;
		imageURL = null;
		caption = null;
		showIfMessage = null;
		showActiveComponentMessage = null;

		super.release();
	}

}
