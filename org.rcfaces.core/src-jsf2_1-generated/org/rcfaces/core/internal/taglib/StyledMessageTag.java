package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.component.StyledMessageComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class StyledMessageTag extends AbstractBasicTag implements Tag {


	private static final Log LOG=LogFactory.getLog(StyledMessageTag.class);

	private ValueExpression backgroundImageHorizontalPosition;
	private ValueExpression backgroundImageHorizontalRepeat;
	private ValueExpression backgroundImageURL;
	private ValueExpression backgroundImageVerticalPosition;
	private ValueExpression backgroundImageVerticalRepeat;
	private ValueExpression border;
	private ValueExpression mouseOutListeners;
	private ValueExpression mouseOverListeners;
	private ValueExpression initListeners;
	private ValueExpression forValue;
	private ValueExpression errorStyleClass;
	private ValueExpression fatalStyleClass;
	private ValueExpression infoStyleClass;
	private ValueExpression warnStyleClass;
	private ValueExpression showIfMessage;
	private ValueExpression setFocusIfMessage;
	public String getComponentType() {
		return StyledMessageComponent.COMPONENT_TYPE;
	}

	public final void setBackgroundImageHorizontalPosition(ValueExpression backgroundImageHorizontalPosition) {
		this.backgroundImageHorizontalPosition = backgroundImageHorizontalPosition;
	}

	public final void setBackgroundImageHorizontalRepeat(ValueExpression backgroundImageHorizontalRepeat) {
		this.backgroundImageHorizontalRepeat = backgroundImageHorizontalRepeat;
	}

	public final void setBackgroundImageURL(ValueExpression backgroundImageURL) {
		this.backgroundImageURL = backgroundImageURL;
	}

	public final void setBackgroundImageVerticalPosition(ValueExpression backgroundImageVerticalPosition) {
		this.backgroundImageVerticalPosition = backgroundImageVerticalPosition;
	}

	public final void setBackgroundImageVerticalRepeat(ValueExpression backgroundImageVerticalRepeat) {
		this.backgroundImageVerticalRepeat = backgroundImageVerticalRepeat;
	}

	public final void setBorder(ValueExpression border) {
		this.border = border;
	}

	public final void setMouseOutListener(ValueExpression mouseOutListeners) {
		this.mouseOutListeners = mouseOutListeners;
	}

	public final void setMouseOverListener(ValueExpression mouseOverListeners) {
		this.mouseOverListeners = mouseOverListeners;
	}

	public final void setInitListener(ValueExpression initListeners) {
		this.initListeners = initListeners;
	}

	public final void setFor(ValueExpression forValue) {
		this.forValue = forValue;
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

	public void setShowIfMessage(ValueExpression showIfMessage) {
		this.showIfMessage = showIfMessage;
	}

	public void setSetFocusIfMessage(ValueExpression setFocusIfMessage) {
		this.setFocusIfMessage = setFocusIfMessage;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (StyledMessageComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  backgroundImageHorizontalPosition='"+backgroundImageHorizontalPosition+"'");
			LOG.debug("  backgroundImageHorizontalRepeat='"+backgroundImageHorizontalRepeat+"'");
			LOG.debug("  backgroundImageURL='"+backgroundImageURL+"'");
			LOG.debug("  backgroundImageVerticalPosition='"+backgroundImageVerticalPosition+"'");
			LOG.debug("  backgroundImageVerticalRepeat='"+backgroundImageVerticalRepeat+"'");
			LOG.debug("  border='"+border+"'");
			LOG.debug("  forValue='"+forValue+"'");
			LOG.debug("  errorStyleClass='"+errorStyleClass+"'");
			LOG.debug("  fatalStyleClass='"+fatalStyleClass+"'");
			LOG.debug("  infoStyleClass='"+infoStyleClass+"'");
			LOG.debug("  warnStyleClass='"+warnStyleClass+"'");
			LOG.debug("  showIfMessage='"+showIfMessage+"'");
			LOG.debug("  setFocusIfMessage='"+setFocusIfMessage+"'");
		}
		if ((uiComponent instanceof StyledMessageComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'StyledMessageComponent'.");
		}

		super.setProperties(uiComponent);

		StyledMessageComponent component = (StyledMessageComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (backgroundImageHorizontalPosition != null) {
			if (backgroundImageHorizontalPosition.isLiteralText()==false) {
				component.setValueExpression(Properties.BACKGROUND_IMAGE_HORIZONTAL_POSITION, backgroundImageHorizontalPosition);

			} else {
				component.setBackgroundImageHorizontalPosition(backgroundImageHorizontalPosition.getExpressionString());
			}
		}

		if (backgroundImageHorizontalRepeat != null) {
			if (backgroundImageHorizontalRepeat.isLiteralText()==false) {
				component.setValueExpression(Properties.BACKGROUND_IMAGE_HORIZONTAL_REPEAT, backgroundImageHorizontalRepeat);

			} else {
				component.setBackgroundImageHorizontalRepeat(getBool(backgroundImageHorizontalRepeat.getExpressionString()));
			}
		}

		if (backgroundImageURL != null) {
			if (backgroundImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.BACKGROUND_IMAGE_URL, backgroundImageURL);

			} else {
				component.setBackgroundImageURL(backgroundImageURL.getExpressionString());
			}
		}

		if (backgroundImageVerticalPosition != null) {
			if (backgroundImageVerticalPosition.isLiteralText()==false) {
				component.setValueExpression(Properties.BACKGROUND_IMAGE_VERTICAL_POSITION, backgroundImageVerticalPosition);

			} else {
				component.setBackgroundImageVerticalPosition(backgroundImageVerticalPosition.getExpressionString());
			}
		}

		if (backgroundImageVerticalRepeat != null) {
			if (backgroundImageVerticalRepeat.isLiteralText()==false) {
				component.setValueExpression(Properties.BACKGROUND_IMAGE_VERTICAL_REPEAT, backgroundImageVerticalRepeat);

			} else {
				component.setBackgroundImageVerticalRepeat(getBool(backgroundImageVerticalRepeat.getExpressionString()));
			}
		}

		if (border != null) {
			if (border.isLiteralText()==false) {
				component.setValueExpression(Properties.BORDER, border);

			} else {
				component.setBorder(getBool(border.getExpressionString()));
			}
		}

		if (mouseOutListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.MOUSE_OUT_LISTENER_TYPE, mouseOutListeners);
		}

		if (mouseOverListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.MOUSE_OVER_LISTENER_TYPE, mouseOverListeners);
		}

		if (initListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.INIT_LISTENER_TYPE, initListeners);
		}

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

		if (showIfMessage != null) {
			if (showIfMessage.isLiteralText()==false) {
				component.setValueExpression(Properties.SHOW_IF_MESSAGE, showIfMessage);

			} else {
				component.setShowIfMessage(getBool(showIfMessage.getExpressionString()));
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
		backgroundImageHorizontalPosition = null;
		backgroundImageHorizontalRepeat = null;
		backgroundImageURL = null;
		backgroundImageVerticalPosition = null;
		backgroundImageVerticalRepeat = null;
		border = null;
		mouseOutListeners = null;
		mouseOverListeners = null;
		initListeners = null;
		forValue = null;
		errorStyleClass = null;
		fatalStyleClass = null;
		infoStyleClass = null;
		warnStyleClass = null;
		showIfMessage = null;
		setFocusIfMessage = null;

		super.release();
	}

}
