package org.rcfaces.core.internal.taglib;

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
import org.rcfaces.core.component.ToolItemComponent;
import javax.faces.context.FacesContext;

public class ToolItemTag extends UIImageItemTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ToolItemTag.class);

	private ValueExpression groupName;
	private ValueExpression inputType;
	private ValueExpression imageHeight;
	private ValueExpression imageWidth;
	private ValueExpression lookId;
	private ValueExpression borderType;
	private ValueExpression textPosition;
	private ValueExpression accessKey;
	private ValueExpression width;
	private ValueExpression styleClass;
	private ValueExpression immediate;
	public String getComponentType() {
		return ToolItemComponent.COMPONENT_TYPE;
	}

	public final void setGroupName(ValueExpression groupName) {
		this.groupName = groupName;
	}

	public final void setInputType(ValueExpression inputType) {
		this.inputType = inputType;
	}

	public final void setImageHeight(ValueExpression imageHeight) {
		this.imageHeight = imageHeight;
	}

	public final void setImageWidth(ValueExpression imageWidth) {
		this.imageWidth = imageWidth;
	}

	public final void setLookId(ValueExpression lookId) {
		this.lookId = lookId;
	}

	public final void setBorderType(ValueExpression borderType) {
		this.borderType = borderType;
	}

	public final void setTextPosition(ValueExpression textPosition) {
		this.textPosition = textPosition;
	}

	public final void setAccessKey(ValueExpression accessKey) {
		this.accessKey = accessKey;
	}

	public final void setWidth(ValueExpression width) {
		this.width = width;
	}

	public final void setStyleClass(ValueExpression styleClass) {
		this.styleClass = styleClass;
	}

	public final void setImmediate(ValueExpression immediate) {
		this.immediate = immediate;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ToolItemComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  groupName='"+groupName+"'");
			LOG.debug("  inputType='"+inputType+"'");
			LOG.debug("  imageHeight='"+imageHeight+"'");
			LOG.debug("  imageWidth='"+imageWidth+"'");
			LOG.debug("  lookId='"+lookId+"'");
			LOG.debug("  borderType='"+borderType+"'");
			LOG.debug("  textPosition='"+textPosition+"'");
			LOG.debug("  accessKey='"+accessKey+"'");
			LOG.debug("  width='"+width+"'");
			LOG.debug("  styleClass='"+styleClass+"'");
			LOG.debug("  immediate='"+immediate+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ToolItemComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ToolItemComponent'.");
		}

		ToolItemComponent component = (ToolItemComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (groupName != null) {
			if (groupName.isLiteralText()==false) {
				component.setValueExpression(Properties.GROUP_NAME, groupName);

			} else {
				component.setGroupName(groupName.getExpressionString());
			}
		}

		if (inputType != null) {
			if (inputType.isLiteralText()==false) {
				component.setValueExpression(Properties.INPUT_TYPE, inputType);

			} else {
				component.setInputType(inputType.getExpressionString());
			}
		}

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

		if (lookId != null) {
			if (lookId.isLiteralText()==false) {
				component.setValueExpression(Properties.LOOK_ID, lookId);

			} else {
				component.setLookId(lookId.getExpressionString());
			}
		}

		if (borderType != null) {
			if (borderType.isLiteralText()==false) {
				component.setValueExpression(Properties.BORDER_TYPE, borderType);

			} else {
				component.setBorderType(borderType.getExpressionString());
			}
		}

		if (textPosition != null) {
			if (textPosition.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT_POSITION, textPosition);

			} else {
				component.setTextPosition(textPosition.getExpressionString());
			}
		}

		if (accessKey != null) {
			if (accessKey.isLiteralText()==false) {
				component.setValueExpression(Properties.ACCESS_KEY, accessKey);

			} else {
				component.setAccessKey(accessKey.getExpressionString());
			}
		}

		if (width != null) {
			if (width.isLiteralText()==false) {
				component.setValueExpression(Properties.WIDTH, width);

			} else {
				component.setWidth(width.getExpressionString());
			}
		}

		if (styleClass != null) {
			if (styleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.STYLE_CLASS, styleClass);

			} else {
				component.setStyleClass(styleClass.getExpressionString());
			}
		}

		if (immediate != null) {
			if (immediate.isLiteralText()==false) {
				component.setValueExpression(Properties.IMMEDIATE, immediate);

			} else {
				component.setImmediate(getBool(immediate.getExpressionString()));
			}
		}
	}

	public void release() {
		groupName = null;
		inputType = null;
		imageHeight = null;
		imageWidth = null;
		lookId = null;
		borderType = null;
		textPosition = null;
		accessKey = null;
		width = null;
		styleClass = null;
		immediate = null;

		super.release();
	}

}
