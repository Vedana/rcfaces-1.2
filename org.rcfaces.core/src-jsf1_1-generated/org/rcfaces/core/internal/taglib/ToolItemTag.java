package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.component.ToolItemComponent;
import javax.faces.context.FacesContext;

public class ToolItemTag extends UIImageItemTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ToolItemTag.class);

	private String groupName;
	private String inputType;
	private String imageHeight;
	private String imageWidth;
	private String lookId;
	private String borderType;
	private String textPosition;
	private String accessKey;
	private String width;
	private String styleClass;
	private String immediate;
	public String getComponentType() {
		return ToolItemComponent.COMPONENT_TYPE;
	}

	public final String getGroupName() {
		return groupName;
	}

	public final void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public final String getInputType() {
		return inputType;
	}

	public final void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public final String getImageHeight() {
		return imageHeight;
	}

	public final void setImageHeight(String imageHeight) {
		this.imageHeight = imageHeight;
	}

	public final String getImageWidth() {
		return imageWidth;
	}

	public final void setImageWidth(String imageWidth) {
		this.imageWidth = imageWidth;
	}

	public final String getLookId() {
		return lookId;
	}

	public final void setLookId(String lookId) {
		this.lookId = lookId;
	}

	public final String getBorderType() {
		return borderType;
	}

	public final void setBorderType(String borderType) {
		this.borderType = borderType;
	}

	public final String getTextPosition() {
		return textPosition;
	}

	public final void setTextPosition(String textPosition) {
		this.textPosition = textPosition;
	}

	public final String getAccessKey() {
		return accessKey;
	}

	public final void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public final String getWidth() {
		return width;
	}

	public final void setWidth(String width) {
		this.width = width;
	}

	public final String getStyleClass() {
		return styleClass;
	}

	public final void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public final String getImmediate() {
		return immediate;
	}

	public final void setImmediate(String immediate) {
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
		Application application = facesContext.getApplication();

		if (groupName != null) {
			if (isValueReference(groupName)) {
				ValueBinding vb = application.createValueBinding(groupName);
				component.setValueBinding(Properties.GROUP_NAME, vb);

			} else {
				component.setGroupName(groupName);
			}
		}

		if (inputType != null) {
			if (isValueReference(inputType)) {
				ValueBinding vb = application.createValueBinding(inputType);
				component.setValueBinding(Properties.INPUT_TYPE, vb);

			} else {
				component.setInputType(inputType);
			}
		}

		if (imageHeight != null) {
			if (isValueReference(imageHeight)) {
				ValueBinding vb = application.createValueBinding(imageHeight);
				component.setValueBinding(Properties.IMAGE_HEIGHT, vb);

			} else {
				component.setImageHeight(getInt(imageHeight));
			}
		}

		if (imageWidth != null) {
			if (isValueReference(imageWidth)) {
				ValueBinding vb = application.createValueBinding(imageWidth);
				component.setValueBinding(Properties.IMAGE_WIDTH, vb);

			} else {
				component.setImageWidth(getInt(imageWidth));
			}
		}

		if (lookId != null) {
			if (isValueReference(lookId)) {
				ValueBinding vb = application.createValueBinding(lookId);
				component.setValueBinding(Properties.LOOK_ID, vb);

			} else {
				component.setLookId(lookId);
			}
		}

		if (borderType != null) {
			if (isValueReference(borderType)) {
				ValueBinding vb = application.createValueBinding(borderType);
				component.setValueBinding(Properties.BORDER_TYPE, vb);

			} else {
				component.setBorderType(borderType);
			}
		}

		if (textPosition != null) {
			if (isValueReference(textPosition)) {
				ValueBinding vb = application.createValueBinding(textPosition);
				component.setValueBinding(Properties.TEXT_POSITION, vb);

			} else {
				component.setTextPosition(textPosition);
			}
		}

		if (accessKey != null) {
			if (isValueReference(accessKey)) {
				ValueBinding vb = application.createValueBinding(accessKey);
				component.setValueBinding(Properties.ACCESS_KEY, vb);

			} else {
				component.setAccessKey(accessKey);
			}
		}

		if (width != null) {
			if (isValueReference(width)) {
				ValueBinding vb = application.createValueBinding(width);
				component.setValueBinding(Properties.WIDTH, vb);

			} else {
				component.setWidth(width);
			}
		}

		if (styleClass != null) {
			if (isValueReference(styleClass)) {
				ValueBinding vb = application.createValueBinding(styleClass);
				component.setValueBinding(Properties.STYLE_CLASS, vb);

			} else {
				component.setStyleClass(styleClass);
			}
		}

		if (immediate != null) {
			if (isValueReference(immediate)) {
				ValueBinding vb = application.createValueBinding(immediate);
				component.setValueBinding(Properties.IMMEDIATE, vb);

			} else {
				component.setImmediate(getBool(immediate));
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
