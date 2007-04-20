package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ToolItemComponent;

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

				component.setGroupName(vb);
			} else {
				component.setGroupName(groupName);
			}
		}

		if (inputType != null) {
			if (isValueReference(inputType)) {
				ValueBinding vb = application.createValueBinding(inputType);

				component.setInputType(vb);
			} else {
				component.setInputType(inputType);
			}
		}

		if (imageHeight != null) {
			if (isValueReference(imageHeight)) {
				ValueBinding vb = application.createValueBinding(imageHeight);

				component.setImageHeight(vb);
			} else {
				component.setImageHeight(getInt(imageHeight));
			}
		}

		if (imageWidth != null) {
			if (isValueReference(imageWidth)) {
				ValueBinding vb = application.createValueBinding(imageWidth);

				component.setImageWidth(vb);
			} else {
				component.setImageWidth(getInt(imageWidth));
			}
		}

		if (lookId != null) {
			if (isValueReference(lookId)) {
				ValueBinding vb = application.createValueBinding(lookId);

				component.setLookId(vb);
			} else {
				component.setLookId(lookId);
			}
		}

		if (borderType != null) {
			if (isValueReference(borderType)) {
				ValueBinding vb = application.createValueBinding(borderType);

				component.setBorderType(vb);
			} else {
				component.setBorderType(borderType);
			}
		}

		if (textPosition != null) {
			if (isValueReference(textPosition)) {
				ValueBinding vb = application.createValueBinding(textPosition);

				component.setTextPosition(vb);
			} else {
				component.setTextPosition(textPosition);
			}
		}

		if (accessKey != null) {
			if (isValueReference(accessKey)) {
				ValueBinding vb = application.createValueBinding(accessKey);

				component.setAccessKey(vb);
			} else {
				component.setAccessKey(accessKey);
			}
		}

		if (width != null) {
			if (isValueReference(width)) {
				ValueBinding vb = application.createValueBinding(width);

				component.setWidth(vb);
			} else {
				component.setWidth(width);
			}
		}

		if (styleClass != null) {
			if (isValueReference(styleClass)) {
				ValueBinding vb = application.createValueBinding(styleClass);

				component.setStyleClass(vb);
			} else {
				component.setStyleClass(styleClass);
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

		super.release();
	}

}
