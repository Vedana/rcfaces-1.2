package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.ViewDialogComponent;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class ViewDialogTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ViewDialogTag.class);

	private ValueExpression imageURL;
	private ValueExpression styleClass;
	private ValueExpression text;
	private ValueExpression textDirection;
	private ValueExpression visible;
	private ValueExpression dialogPriority;
	private ValueExpression width;
	private ValueExpression height;
	private ValueExpression hiddenMode;
	private ValueExpression lookId;
	private ValueExpression ariaLabel;
	private ValueExpression ariaLevel;
	private ValueExpression waiRole;
	private ValueExpression closeListeners;
	private ValueExpression closable;
	private ValueExpression immediate;
	private ValueExpression viewURL;
	private ValueExpression shellDecoratorName;
	private ValueExpression returnFocusClientId;
	private ValueExpression value;
	private ValueExpression converter;
	public String getComponentType() {
		return ViewDialogComponent.COMPONENT_TYPE;
	}

	public void setImageURL(ValueExpression imageURL) {
		this.imageURL = imageURL;
	}

	public void setStyleClass(ValueExpression styleClass) {
		this.styleClass = styleClass;
	}

	public void setText(ValueExpression text) {
		this.text = text;
	}

	public void setTextDirection(ValueExpression textDirection) {
		this.textDirection = textDirection;
	}

	public void setVisible(ValueExpression visible) {
		this.visible = visible;
	}

	public void setDialogPriority(ValueExpression dialogPriority) {
		this.dialogPriority = dialogPriority;
	}

	public void setWidth(ValueExpression width) {
		this.width = width;
	}

	public void setHeight(ValueExpression height) {
		this.height = height;
	}

	public void setHiddenMode(ValueExpression hiddenMode) {
		this.hiddenMode = hiddenMode;
	}

	public void setLookId(ValueExpression lookId) {
		this.lookId = lookId;
	}

	public void setAriaLabel(ValueExpression ariaLabel) {
		this.ariaLabel = ariaLabel;
	}

	public void setAriaLevel(ValueExpression ariaLevel) {
		this.ariaLevel = ariaLevel;
	}

	public void setWaiRole(ValueExpression waiRole) {
		this.waiRole = waiRole;
	}

	public void setCloseListener(ValueExpression closeListeners) {
		this.closeListeners = closeListeners;
	}

	public void setClosable(ValueExpression closable) {
		this.closable = closable;
	}

	public void setImmediate(ValueExpression immediate) {
		this.immediate = immediate;
	}

	public void setViewURL(ValueExpression viewURL) {
		this.viewURL = viewURL;
	}

	public void setShellDecoratorName(ValueExpression shellDecoratorName) {
		this.shellDecoratorName = shellDecoratorName;
	}

	public void setReturnFocusClientId(ValueExpression returnFocusClientId) {
		this.returnFocusClientId = returnFocusClientId;
	}

	public final void setValue(ValueExpression value) {
		this.value = value;
	}

	public final void setConverter(ValueExpression converter) {
		this.converter = converter;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ViewDialogComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  imageURL='"+imageURL+"'");
			LOG.debug("  styleClass='"+styleClass+"'");
			LOG.debug("  text='"+text+"'");
			LOG.debug("  textDirection='"+textDirection+"'");
			LOG.debug("  visible='"+visible+"'");
			LOG.debug("  dialogPriority='"+dialogPriority+"'");
			LOG.debug("  width='"+width+"'");
			LOG.debug("  height='"+height+"'");
			LOG.debug("  hiddenMode='"+hiddenMode+"'");
			LOG.debug("  lookId='"+lookId+"'");
			LOG.debug("  ariaLabel='"+ariaLabel+"'");
			LOG.debug("  ariaLevel='"+ariaLevel+"'");
			LOG.debug("  waiRole='"+waiRole+"'");
			LOG.debug("  closable='"+closable+"'");
			LOG.debug("  immediate='"+immediate+"'");
			LOG.debug("  viewURL='"+viewURL+"'");
			LOG.debug("  shellDecoratorName='"+shellDecoratorName+"'");
			LOG.debug("  returnFocusClientId='"+returnFocusClientId+"'");
		}
		if ((uiComponent instanceof ViewDialogComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ViewDialogComponent'.");
		}

		super.setProperties(uiComponent);

		ViewDialogComponent component = (ViewDialogComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (imageURL != null) {
			if (imageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.IMAGE_URL, imageURL);

			} else {
				component.setImageURL(imageURL.getExpressionString());
			}
		}

		if (styleClass != null) {
			if (styleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.STYLE_CLASS, styleClass);

			} else {
				component.setStyleClass(styleClass.getExpressionString());
			}
		}

		if (text != null) {
			if (text.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT, text);

			} else {
				component.setText(text.getExpressionString());
			}
		}

		if (textDirection != null) {
			if (textDirection.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT_DIRECTION, textDirection);

			} else {
				component.setTextDirection(getInt(textDirection.getExpressionString()));
			}
		}

		if (visible != null) {
			if (visible.isLiteralText()==false) {
				component.setValueExpression(Properties.VISIBLE, visible);

			} else {
				component.setVisible(getBool(visible.getExpressionString()));
			}
		}

		if (dialogPriority != null) {
			if (dialogPriority.isLiteralText()==false) {
				component.setValueExpression(Properties.DIALOG_PRIORITY, dialogPriority);

			} else {
				component.setDialogPriority(getInt(dialogPriority.getExpressionString()));
			}
		}

		if (width != null) {
			if (width.isLiteralText()==false) {
				component.setValueExpression(Properties.WIDTH, width);

			} else {
				component.setWidth(width.getExpressionString());
			}
		}

		if (height != null) {
			if (height.isLiteralText()==false) {
				component.setValueExpression(Properties.HEIGHT, height);

			} else {
				component.setHeight(height.getExpressionString());
			}
		}

		if (hiddenMode != null) {
			if (hiddenMode.isLiteralText()==false) {
				component.setValueExpression(Properties.HIDDEN_MODE, hiddenMode);

			} else {
				component.setHiddenMode(hiddenMode.getExpressionString());
			}
		}

		if (lookId != null) {
			if (lookId.isLiteralText()==false) {
				component.setValueExpression(Properties.LOOK_ID, lookId);

			} else {
				component.setLookId(lookId.getExpressionString());
			}
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

		if (closeListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.CLOSE_LISTENER_TYPE, closeListeners);
		}

		if (closable != null) {
			if (closable.isLiteralText()==false) {
				component.setValueExpression(Properties.CLOSABLE, closable);

			} else {
				component.setClosable(getBool(closable.getExpressionString()));
			}
		}

		if (immediate != null) {
			if (immediate.isLiteralText()==false) {
				component.setValueExpression(Properties.IMMEDIATE, immediate);

			} else {
				component.setImmediate(getBool(immediate.getExpressionString()));
			}
		}

		if (viewURL != null) {
			if (viewURL.isLiteralText()==false) {
				component.setValueExpression(Properties.VIEW_URL, viewURL);

			} else {
				component.setViewURL(viewURL.getExpressionString());
			}
		}

		if (shellDecoratorName != null) {
			if (shellDecoratorName.isLiteralText()==false) {
				component.setValueExpression(Properties.SHELL_DECORATOR_NAME, shellDecoratorName);

			} else {
				component.setShellDecoratorName(shellDecoratorName.getExpressionString());
			}
		}

		if (returnFocusClientId != null) {
			if (returnFocusClientId.isLiteralText()==false) {
				component.setValueExpression(Properties.RETURN_FOCUS_CLIENT_ID, returnFocusClientId);

			} else {
				component.setReturnFocusClientId(returnFocusClientId.getExpressionString());
			}
		}

		if (value != null) {
			if (value.isLiteralText()==false) {
				component.setValueExpression(Properties.VALUE, value);

			} else {
				component.setValue(value.getExpressionString());
			}
		}

		if (converter != null) {
			if (converter.isLiteralText()==false) {
				component.setValueExpression(Properties.CONVERTER, converter);

			} else {
				component.setConverter(converter.getExpressionString());
			}
		}
	}

	public void release() {
		imageURL = null;
		styleClass = null;
		text = null;
		textDirection = null;
		visible = null;
		dialogPriority = null;
		width = null;
		height = null;
		hiddenMode = null;
		lookId = null;
		ariaLabel = null;
		ariaLevel = null;
		waiRole = null;
		closeListeners = null;
		closable = null;
		immediate = null;
		viewURL = null;
		shellDecoratorName = null;
		returnFocusClientId = null;
		value = null;
		converter = null;

		super.release();
	}

}
