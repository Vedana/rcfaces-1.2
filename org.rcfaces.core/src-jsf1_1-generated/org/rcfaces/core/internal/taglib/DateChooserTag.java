package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.DateChooserComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class DateChooserTag extends AbstractCalendarTag implements Tag {


	private static final Log LOG=LogFactory.getLog(DateChooserTag.class);

	private String imageURL;
	private String disabledImageURL;
	private String hoverImageURL;
	private String selectedImageURL;
	private String border;
	private String borderType;
	private String text;
	private String textPosition;
	private String imageHeight;
	private String imageWidth;
	private String valueChangeListeners;
	private String forValue;
	private String forValueFormat;
	private String homeDate;
	private String homeDateLabel;
	public String getComponentType() {
		return DateChooserComponent.COMPONENT_TYPE;
	}

	public final String getImageURL() {
		return imageURL;
	}

	public final void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public final String getDisabledImageURL() {
		return disabledImageURL;
	}

	public final void setDisabledImageURL(String disabledImageURL) {
		this.disabledImageURL = disabledImageURL;
	}

	public final String getHoverImageURL() {
		return hoverImageURL;
	}

	public final void setHoverImageURL(String hoverImageURL) {
		this.hoverImageURL = hoverImageURL;
	}

	public final String getSelectedImageURL() {
		return selectedImageURL;
	}

	public final void setSelectedImageURL(String selectedImageURL) {
		this.selectedImageURL = selectedImageURL;
	}

	public final String getBorder() {
		return border;
	}

	public final void setBorder(String border) {
		this.border = border;
	}

	public final String getBorderType() {
		return borderType;
	}

	public final void setBorderType(String borderType) {
		this.borderType = borderType;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}

	public final String getTextPosition() {
		return textPosition;
	}

	public final void setTextPosition(String textPosition) {
		this.textPosition = textPosition;
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

	public final String getValueChangeListener() {
		return valueChangeListeners;
	}

	public final void setValueChangeListener(String valueChangeListeners) {
		this.valueChangeListeners = valueChangeListeners;
	}

	public final String getFor() {
		return forValue;
	}

	public final void setFor(String forValue) {
		this.forValue = forValue;
	}

	public final void setForValueFormat(String forValueFormat) {
		this.forValueFormat = forValueFormat;
	}

	public final void setHomeDate(String homeDate) {
		this.homeDate = homeDate;
	}

	public final void setHomeDateLabel(String homeDateLabel) {
		this.homeDateLabel = homeDateLabel;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (DateChooserComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  imageURL='"+imageURL+"'");
			LOG.debug("  disabledImageURL='"+disabledImageURL+"'");
			LOG.debug("  hoverImageURL='"+hoverImageURL+"'");
			LOG.debug("  selectedImageURL='"+selectedImageURL+"'");
			LOG.debug("  border='"+border+"'");
			LOG.debug("  borderType='"+borderType+"'");
			LOG.debug("  text='"+text+"'");
			LOG.debug("  textPosition='"+textPosition+"'");
			LOG.debug("  imageHeight='"+imageHeight+"'");
			LOG.debug("  imageWidth='"+imageWidth+"'");
			LOG.debug("  forValue='"+forValue+"'");
			LOG.debug("  forValueFormat='"+forValueFormat+"'");
			LOG.debug("  homeDate='"+homeDate+"'");
			LOG.debug("  homeDateLabel='"+homeDateLabel+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof DateChooserComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'DateChooserComponent'.");
		}

		DateChooserComponent component = (DateChooserComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (imageURL != null) {
			if (isValueReference(imageURL)) {
				ValueBinding vb = application.createValueBinding(imageURL);
				component.setValueBinding(Properties.IMAGE_URL, vb);

			} else {
				component.setImageURL(imageURL);
			}
		}

		if (disabledImageURL != null) {
			if (isValueReference(disabledImageURL)) {
				ValueBinding vb = application.createValueBinding(disabledImageURL);
				component.setValueBinding(Properties.DISABLED_IMAGE_URL, vb);

			} else {
				component.setDisabledImageURL(disabledImageURL);
			}
		}

		if (hoverImageURL != null) {
			if (isValueReference(hoverImageURL)) {
				ValueBinding vb = application.createValueBinding(hoverImageURL);
				component.setValueBinding(Properties.HOVER_IMAGE_URL, vb);

			} else {
				component.setHoverImageURL(hoverImageURL);
			}
		}

		if (selectedImageURL != null) {
			if (isValueReference(selectedImageURL)) {
				ValueBinding vb = application.createValueBinding(selectedImageURL);
				component.setValueBinding(Properties.SELECTED_IMAGE_URL, vb);

			} else {
				component.setSelectedImageURL(selectedImageURL);
			}
		}

		if (border != null) {
			if (isValueReference(border)) {
				ValueBinding vb = application.createValueBinding(border);
				component.setValueBinding(Properties.BORDER, vb);

			} else {
				component.setBorder(getBool(border));
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

		if (text != null) {
			if (isValueReference(text)) {
				ValueBinding vb = application.createValueBinding(text);
				component.setValueBinding(Properties.TEXT, vb);

			} else {
				component.setText(text);
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

		if (valueChangeListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.VALUE_CHANGE_LISTENER_TYPE, valueChangeListeners);
		}

		if (forValue != null) {
			if (isValueReference(forValue)) {
				ValueBinding vb = application.createValueBinding(forValue);
				component.setValueBinding(Properties.FOR, vb);

			} else {
				component.setFor(forValue);
			}
		}

		if (forValueFormat != null) {
			if (isValueReference(forValueFormat)) {
				ValueBinding vb = application.createValueBinding(forValueFormat);
				component.setValueBinding(Properties.FOR_VALUE_FORMAT, vb);

			} else {
				component.setForValueFormat(forValueFormat);
			}
		}

		if (homeDate != null) {
			if (isValueReference(homeDate)) {
				ValueBinding vb = application.createValueBinding(homeDate);
				component.setValueBinding(Properties.HOME_DATE, vb);

			} else {
				component.setHomeDate(homeDate);
			}
		}

		if (homeDateLabel != null) {
			if (isValueReference(homeDateLabel)) {
				ValueBinding vb = application.createValueBinding(homeDateLabel);
				component.setValueBinding(Properties.HOME_DATE_LABEL, vb);

			} else {
				component.setHomeDateLabel(homeDateLabel);
			}
		}
	}

	public void release() {
		imageURL = null;
		disabledImageURL = null;
		hoverImageURL = null;
		selectedImageURL = null;
		border = null;
		borderType = null;
		text = null;
		textPosition = null;
		imageHeight = null;
		imageWidth = null;
		valueChangeListeners = null;
		forValue = null;
		forValueFormat = null;
		homeDate = null;
		homeDateLabel = null;

		super.release();
	}

}
