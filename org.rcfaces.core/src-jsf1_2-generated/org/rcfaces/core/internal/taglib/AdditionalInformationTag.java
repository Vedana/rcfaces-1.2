package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import org.rcfaces.core.component.AdditionalInformationComponent;
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

public class AdditionalInformationTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(AdditionalInformationTag.class);

	private ValueExpression propertyChangeListeners;
	private ValueExpression userEventListeners;
	private ValueExpression errorListeners;
	private ValueExpression ariaLabel;
	private ValueExpression ariaLevel;
	private ValueExpression waiRole;
	private ValueExpression marginBottom;
	private ValueExpression marginLeft;
	private ValueExpression marginRight;
	private ValueExpression marginTop;
	private ValueExpression backgroundColor;
	private ValueExpression foregroundColor;
	private ValueExpression backgroundImageHorizontalPosition;
	private ValueExpression backgroundImageHorizontalRepeat;
	private ValueExpression backgroundImageURL;
	private ValueExpression backgroundImageVerticalPosition;
	private ValueExpression backgroundImageVerticalRepeat;
	private ValueExpression lookId;
	private ValueExpression styleClass;
	private ValueExpression height;
	private ValueExpression mouseOutListeners;
	private ValueExpression mouseOverListeners;
	private ValueExpression initListeners;
	private ValueExpression loadListeners;
	private ValueExpression scopeSaveValue;
	private ValueExpression scopeValue;
	private ValueExpression scopeVar;
	private ValueExpression margins;
	public String getComponentType() {
		return AdditionalInformationComponent.COMPONENT_TYPE;
	}

	public final void setPropertyChangeListener(ValueExpression propertyChangeListeners) {
		this.propertyChangeListeners = propertyChangeListeners;
	}

	public final void setUserEventListener(ValueExpression userEventListeners) {
		this.userEventListeners = userEventListeners;
	}

	public final void setErrorListener(ValueExpression errorListeners) {
		this.errorListeners = errorListeners;
	}

	public final void setAriaLabel(ValueExpression ariaLabel) {
		this.ariaLabel = ariaLabel;
	}

	public final void setAriaLevel(ValueExpression ariaLevel) {
		this.ariaLevel = ariaLevel;
	}

	public final void setWaiRole(ValueExpression waiRole) {
		this.waiRole = waiRole;
	}

	public final void setMarginBottom(ValueExpression marginBottom) {
		this.marginBottom = marginBottom;
	}

	public final void setMarginLeft(ValueExpression marginLeft) {
		this.marginLeft = marginLeft;
	}

	public final void setMarginRight(ValueExpression marginRight) {
		this.marginRight = marginRight;
	}

	public final void setMarginTop(ValueExpression marginTop) {
		this.marginTop = marginTop;
	}

	public final void setBackgroundColor(ValueExpression backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public final void setForegroundColor(ValueExpression foregroundColor) {
		this.foregroundColor = foregroundColor;
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

	public final void setLookId(ValueExpression lookId) {
		this.lookId = lookId;
	}

	public final void setStyleClass(ValueExpression styleClass) {
		this.styleClass = styleClass;
	}

	public final void setHeight(ValueExpression height) {
		this.height = height;
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

	public final void setLoadListener(ValueExpression loadListeners) {
		this.loadListeners = loadListeners;
	}

	public final void setScopeSaveValue(ValueExpression scopeSaveValue) {
		this.scopeSaveValue = scopeSaveValue;
	}

	public final void setScopeValue(ValueExpression scopeValue) {
		this.scopeValue = scopeValue;
	}

	public final void setScopeVar(ValueExpression scopeVar) {
		this.scopeVar = scopeVar;
	}

	public final void setMargins(ValueExpression margins) {
		this.margins = margins;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (AdditionalInformationComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  ariaLabel='"+ariaLabel+"'");
			LOG.debug("  ariaLevel='"+ariaLevel+"'");
			LOG.debug("  waiRole='"+waiRole+"'");
			LOG.debug("  marginBottom='"+marginBottom+"'");
			LOG.debug("  marginLeft='"+marginLeft+"'");
			LOG.debug("  marginRight='"+marginRight+"'");
			LOG.debug("  marginTop='"+marginTop+"'");
			LOG.debug("  backgroundColor='"+backgroundColor+"'");
			LOG.debug("  foregroundColor='"+foregroundColor+"'");
			LOG.debug("  backgroundImageHorizontalPosition='"+backgroundImageHorizontalPosition+"'");
			LOG.debug("  backgroundImageHorizontalRepeat='"+backgroundImageHorizontalRepeat+"'");
			LOG.debug("  backgroundImageURL='"+backgroundImageURL+"'");
			LOG.debug("  backgroundImageVerticalPosition='"+backgroundImageVerticalPosition+"'");
			LOG.debug("  backgroundImageVerticalRepeat='"+backgroundImageVerticalRepeat+"'");
			LOG.debug("  lookId='"+lookId+"'");
			LOG.debug("  styleClass='"+styleClass+"'");
			LOG.debug("  height='"+height+"'");
			LOG.debug("  scopeSaveValue='"+scopeSaveValue+"'");
			LOG.debug("  scopeValue='"+scopeValue+"'");
			LOG.debug("  scopeVar='"+scopeVar+"'");
			LOG.debug("  margins='"+margins+"'");
		}
		if ((uiComponent instanceof AdditionalInformationComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'AdditionalInformationComponent'.");
		}

		super.setProperties(uiComponent);

		AdditionalInformationComponent component = (AdditionalInformationComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (propertyChangeListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.PROPERTY_CHANGE_LISTENER_TYPE, propertyChangeListeners);
		}

		if (userEventListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.USER_EVENT_LISTENER_TYPE, userEventListeners);
		}

		if (errorListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.ERROR_LISTENER_TYPE, errorListeners);
		}

		if (ariaLabel != null) {
			if (ariaLabel.isLiteralText()==false) {
				component.setValueExpression(Properties.ARIA_LABEL, ariaLabel);

			} else {
				component.setAriaLabel(ariaLabel.getExpressionString());
			}
		}

		if (ariaLevel != null) {
			if (ariaLevel.isLiteralText()==false) {
				component.setValueExpression(Properties.ARIA_LEVEL, ariaLevel);

			} else {
				component.setAriaLevel(getInt(ariaLevel.getExpressionString()));
			}
		}

		if (waiRole != null) {
			if (waiRole.isLiteralText()==false) {
				component.setValueExpression(Properties.WAI_ROLE, waiRole);

			} else {
				component.setWaiRole(waiRole.getExpressionString());
			}
		}

		if (marginBottom != null) {
			if (marginBottom.isLiteralText()==false) {
				component.setValueExpression(Properties.MARGIN_BOTTOM, marginBottom);

			} else {
				component.setMarginBottom(marginBottom.getExpressionString());
			}
		}

		if (marginLeft != null) {
			if (marginLeft.isLiteralText()==false) {
				component.setValueExpression(Properties.MARGIN_LEFT, marginLeft);

			} else {
				component.setMarginLeft(marginLeft.getExpressionString());
			}
		}

		if (marginRight != null) {
			if (marginRight.isLiteralText()==false) {
				component.setValueExpression(Properties.MARGIN_RIGHT, marginRight);

			} else {
				component.setMarginRight(marginRight.getExpressionString());
			}
		}

		if (marginTop != null) {
			if (marginTop.isLiteralText()==false) {
				component.setValueExpression(Properties.MARGIN_TOP, marginTop);

			} else {
				component.setMarginTop(marginTop.getExpressionString());
			}
		}

		if (backgroundColor != null) {
			if (backgroundColor.isLiteralText()==false) {
				component.setValueExpression(Properties.BACKGROUND_COLOR, backgroundColor);

			} else {
				component.setBackgroundColor(backgroundColor.getExpressionString());
			}
		}

		if (foregroundColor != null) {
			if (foregroundColor.isLiteralText()==false) {
				component.setValueExpression(Properties.FOREGROUND_COLOR, foregroundColor);

			} else {
				component.setForegroundColor(foregroundColor.getExpressionString());
			}
		}

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

		if (lookId != null) {
			if (lookId.isLiteralText()==false) {
				component.setValueExpression(Properties.LOOK_ID, lookId);

			} else {
				component.setLookId(lookId.getExpressionString());
			}
		}

		if (styleClass != null) {
			if (styleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.STYLE_CLASS, styleClass);

			} else {
				component.setStyleClass(styleClass.getExpressionString());
			}
		}

		if (height != null) {
			if (height.isLiteralText()==false) {
				component.setValueExpression(Properties.HEIGHT, height);

			} else {
				component.setHeight(height.getExpressionString());
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

		if (loadListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.LOAD_LISTENER_TYPE, loadListeners);
		}

		if (scopeSaveValue != null) {
			if (scopeSaveValue.isLiteralText()==false) {
				component.setValueExpression(Properties.SCOPE_SAVE_VALUE, scopeSaveValue);

			} else {
				component.setScopeSaveValue(getBool(scopeSaveValue.getExpressionString()));
			}
		}

		if (scopeValue != null) {
			if (scopeValue.isLiteralText()==false) {
				component.setValueExpression(Properties.SCOPE_VALUE, scopeValue);

			} else {
				component.setScopeValue(scopeValue.getExpressionString());
			}
		}

		if (scopeVar != null) {
			if (scopeVar.isLiteralText()==false) {
				component.setValueExpression(Properties.SCOPE_VAR, scopeVar);

			} else {
				component.setScopeVar(scopeVar.getExpressionString());
			}
		}

		if (margins != null) {
			if (margins.isLiteralText()==false) {
				throw new javax.faces.FacesException("Attribute 'margins' does not accept binding !");
			}
				component.setMargins(margins.getExpressionString());
		}
	}

	public void release() {
		propertyChangeListeners = null;
		userEventListeners = null;
		errorListeners = null;
		ariaLabel = null;
		ariaLevel = null;
		waiRole = null;
		marginBottom = null;
		marginLeft = null;
		marginRight = null;
		marginTop = null;
		backgroundColor = null;
		foregroundColor = null;
		backgroundImageHorizontalPosition = null;
		backgroundImageHorizontalRepeat = null;
		backgroundImageURL = null;
		backgroundImageVerticalPosition = null;
		backgroundImageVerticalRepeat = null;
		lookId = null;
		styleClass = null;
		height = null;
		mouseOutListeners = null;
		mouseOverListeners = null;
		initListeners = null;
		loadListeners = null;
		scopeSaveValue = null;
		scopeValue = null;
		scopeVar = null;
		margins = null;

		super.release();
	}

}
