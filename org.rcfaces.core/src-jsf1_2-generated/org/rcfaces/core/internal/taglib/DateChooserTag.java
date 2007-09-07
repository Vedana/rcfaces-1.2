package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.DateChooserComponent;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class DateChooserTag extends AbstractCalendarTag implements Tag {


	private static final Log LOG=LogFactory.getLog(DateChooserTag.class);

	private ValueExpression imageURL;
	private ValueExpression disabledImageURL;
	private ValueExpression hoverImageURL;
	private ValueExpression selectedImageURL;
	private ValueExpression border;
	private ValueExpression borderType;
	private ValueExpression text;
	private ValueExpression textPosition;
	private ValueExpression imageHeight;
	private ValueExpression imageWidth;
	private ValueExpression valueChangeListeners;
	private ValueExpression forValue;
	private ValueExpression forValueFormat;
	private ValueExpression homeDate;
	private ValueExpression homeDateLabel;
	public String getComponentType() {
		return DateChooserComponent.COMPONENT_TYPE;
	}

	public final void setImageURL(ValueExpression imageURL) {
		this.imageURL = imageURL;
	}

	public final void setDisabledImageURL(ValueExpression disabledImageURL) {
		this.disabledImageURL = disabledImageURL;
	}

	public final void setHoverImageURL(ValueExpression hoverImageURL) {
		this.hoverImageURL = hoverImageURL;
	}

	public final void setSelectedImageURL(ValueExpression selectedImageURL) {
		this.selectedImageURL = selectedImageURL;
	}

	public final void setBorder(ValueExpression border) {
		this.border = border;
	}

	public final void setBorderType(ValueExpression borderType) {
		this.borderType = borderType;
	}

	public final void setText(ValueExpression text) {
		this.text = text;
	}

	public final void setTextPosition(ValueExpression textPosition) {
		this.textPosition = textPosition;
	}

	public final void setImageHeight(ValueExpression imageHeight) {
		this.imageHeight = imageHeight;
	}

	public final void setImageWidth(ValueExpression imageWidth) {
		this.imageWidth = imageWidth;
	}

	public final void setValueChangeListener(ValueExpression valueChangeListeners) {
		this.valueChangeListeners = valueChangeListeners;
	}

	public final void setFor(ValueExpression forValue) {
		this.forValue = forValue;
	}

	public final void setForValueFormat(ValueExpression forValueFormat) {
		this.forValueFormat = forValueFormat;
	}

	public final void setHomeDate(ValueExpression homeDate) {
		this.homeDate = homeDate;
	}

	public final void setHomeDateLabel(ValueExpression homeDateLabel) {
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

		if (imageURL != null) {
			if (imageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.IMAGE_URL, imageURL);

			} else {
				component.setImageURL(imageURL.getExpressionString());
			}
		}

		if (disabledImageURL != null) {
			if (disabledImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.DISABLED_IMAGE_URL, disabledImageURL);

			} else {
				component.setDisabledImageURL(disabledImageURL.getExpressionString());
			}
		}

		if (hoverImageURL != null) {
			if (hoverImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.HOVER_IMAGE_URL, hoverImageURL);

			} else {
				component.setHoverImageURL(hoverImageURL.getExpressionString());
			}
		}

		if (selectedImageURL != null) {
			if (selectedImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.SELECTED_IMAGE_URL, selectedImageURL);

			} else {
				component.setSelectedImageURL(selectedImageURL.getExpressionString());
			}
		}

		if (border != null) {
			if (border.isLiteralText()==false) {
				component.setValueExpression(Properties.BORDER, border);

			} else {
				component.setBorder(getBool(border.getExpressionString()));
			}
		}

		if (borderType != null) {
			if (borderType.isLiteralText()==false) {
				component.setValueExpression(Properties.BORDER_TYPE, borderType);

			} else {
				component.setBorderType(borderType.getExpressionString());
			}
		}

		if (text != null) {
			if (text.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT, text);

			} else {
				component.setText(text.getExpressionString());
			}
		}

		if (textPosition != null) {
			if (textPosition.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT_POSITION, textPosition);

			} else {
				component.setTextPosition(textPosition.getExpressionString());
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

		if (valueChangeListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.VALUE_CHANGE_LISTENER_TYPE, valueChangeListeners);
		}

		if (forValue != null) {
			if (forValue.isLiteralText()==false) {
				component.setValueExpression(Properties.FOR, forValue);

			} else {
				component.setFor(forValue.getExpressionString());
			}
		}

		if (forValueFormat != null) {
			if (forValueFormat.isLiteralText()==false) {
				component.setValueExpression(Properties.FOR_VALUE_FORMAT, forValueFormat);

			} else {
				component.setForValueFormat(forValueFormat.getExpressionString());
			}
		}

		if (homeDate != null) {
			if (homeDate.isLiteralText()==false) {
				component.setValueExpression(Properties.HOME_DATE, homeDate);

			} else {
				component.setHomeDate(homeDate.getExpressionString());
			}
		}

		if (homeDateLabel != null) {
			if (homeDateLabel.isLiteralText()==false) {
				component.setValueExpression(Properties.HOME_DATE_LABEL, homeDateLabel);

			} else {
				component.setHomeDateLabel(homeDateLabel.getExpressionString());
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
