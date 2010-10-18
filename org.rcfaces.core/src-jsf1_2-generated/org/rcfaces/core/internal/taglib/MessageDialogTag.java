package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.MessageDialogComponent;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class MessageDialogTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(MessageDialogTag.class);

	private ValueExpression imageURL;
	private ValueExpression styleClass;
	private ValueExpression text;
	private ValueExpression textDirection;
	private ValueExpression visible;
	private ValueExpression dialogPriority;
	private ValueExpression immediate;
	private ValueExpression validationListeners;
	private ValueExpression width;
	private ValueExpression height;
	private ValueExpression hiddenMode;
	private ValueExpression lookId;
	private ValueExpression ariaLevel;
	private ValueExpression waiRole;
	private ValueExpression selectionListeners;
	private ValueExpression userEventListeners;
	private ValueExpression title;
	private ValueExpression defaultValue;
	private ValueExpression value;
	private ValueExpression converter;
	public String getComponentType() {
		return MessageDialogComponent.COMPONENT_TYPE;
	}

	public final void setImageURL(ValueExpression imageURL) {
		this.imageURL = imageURL;
	}

	public final void setStyleClass(ValueExpression styleClass) {
		this.styleClass = styleClass;
	}

	public final void setText(ValueExpression text) {
		this.text = text;
	}

	public final void setTextDirection(ValueExpression textDirection) {
		this.textDirection = textDirection;
	}

	public final void setVisible(ValueExpression visible) {
		this.visible = visible;
	}

	public final void setDialogPriority(ValueExpression dialogPriority) {
		this.dialogPriority = dialogPriority;
	}

	public final void setImmediate(ValueExpression immediate) {
		this.immediate = immediate;
	}

	public final void setValidationListener(ValueExpression validationListeners) {
		this.validationListeners = validationListeners;
	}

	public final void setWidth(ValueExpression width) {
		this.width = width;
	}

	public final void setHeight(ValueExpression height) {
		this.height = height;
	}

	public final void setHiddenMode(ValueExpression hiddenMode) {
		this.hiddenMode = hiddenMode;
	}

	public final void setLookId(ValueExpression lookId) {
		this.lookId = lookId;
	}

	public final void setAriaLevel(ValueExpression ariaLevel) {
		this.ariaLevel = ariaLevel;
	}

	public final void setWaiRole(ValueExpression waiRole) {
		this.waiRole = waiRole;
	}

	public final void setSelectionListener(ValueExpression selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public final void setUserEventListener(ValueExpression userEventListeners) {
		this.userEventListeners = userEventListeners;
	}

	public final void setTitle(ValueExpression title) {
		this.title = title;
	}

	public final void setDefaultValue(ValueExpression defaultValue) {
		this.defaultValue = defaultValue;
	}

	public final void setValue(ValueExpression value) {
		this.value = value;
	}

	public final void setConverter(ValueExpression converter) {
		this.converter = converter;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (MessageDialogComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  imageURL='"+imageURL+"'");
			LOG.debug("  styleClass='"+styleClass+"'");
			LOG.debug("  text='"+text+"'");
			LOG.debug("  textDirection='"+textDirection+"'");
			LOG.debug("  visible='"+visible+"'");
			LOG.debug("  dialogPriority='"+dialogPriority+"'");
			LOG.debug("  immediate='"+immediate+"'");
			LOG.debug("  width='"+width+"'");
			LOG.debug("  height='"+height+"'");
			LOG.debug("  hiddenMode='"+hiddenMode+"'");
			LOG.debug("  lookId='"+lookId+"'");
			LOG.debug("  ariaLevel='"+ariaLevel+"'");
			LOG.debug("  waiRole='"+waiRole+"'");
			LOG.debug("  title='"+title+"'");
			LOG.debug("  defaultValue='"+defaultValue+"'");
		}
		if ((uiComponent instanceof MessageDialogComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'MessageDialogComponent'.");
		}

		super.setProperties(uiComponent);

		MessageDialogComponent component = (MessageDialogComponent) uiComponent;
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

		if (immediate != null) {
			if (immediate.isLiteralText()==false) {
				component.setValueExpression(Properties.IMMEDIATE, immediate);

			} else {
				component.setImmediate(getBool(immediate.getExpressionString()));
			}
		}

		if (validationListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.VALIDATION_LISTENER_TYPE, validationListeners);
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

		if (selectionListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (userEventListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.USER_EVENT_LISTENER_TYPE, userEventListeners);
		}

		if (title != null) {
			if (title.isLiteralText()==false) {
				component.setValueExpression(Properties.TITLE, title);

			} else {
				component.setTitle(title.getExpressionString());
			}
		}

		if (defaultValue != null) {
			if (defaultValue.isLiteralText()==false) {
				component.setValueExpression(Properties.DEFAULT_VALUE, defaultValue);

			} else {
				component.setDefaultValue(defaultValue.getExpressionString());
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
		immediate = null;
		validationListeners = null;
		width = null;
		height = null;
		hiddenMode = null;
		lookId = null;
		ariaLevel = null;
		waiRole = null;
		selectionListeners = null;
		userEventListeners = null;
		title = null;
		defaultValue = null;
		value = null;
		converter = null;

		super.release();
	}

}
