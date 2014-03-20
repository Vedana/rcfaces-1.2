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
import org.rcfaces.core.internal.tools.ListenersTools1_2;
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
	private ValueExpression calendarLayout;
	private ValueExpression forValueFormat;
	private ValueExpression homeDate;
	private ValueExpression defaultSelectedDate;
	private ValueExpression homeDateLabel;
	private ValueExpression popupStyleClass;
	public String getComponentType() {
		return DateChooserComponent.COMPONENT_TYPE;
	}

	public void setImageURL(ValueExpression imageURL) {
		this.imageURL = imageURL;
	}

	public void setDisabledImageURL(ValueExpression disabledImageURL) {
		this.disabledImageURL = disabledImageURL;
	}

	public void setHoverImageURL(ValueExpression hoverImageURL) {
		this.hoverImageURL = hoverImageURL;
	}

	public void setSelectedImageURL(ValueExpression selectedImageURL) {
		this.selectedImageURL = selectedImageURL;
	}

	public void setBorder(ValueExpression border) {
		this.border = border;
	}

	public void setBorderType(ValueExpression borderType) {
		this.borderType = borderType;
	}

	public void setText(ValueExpression text) {
		this.text = text;
	}

	public void setTextPosition(ValueExpression textPosition) {
		this.textPosition = textPosition;
	}

	public void setImageHeight(ValueExpression imageHeight) {
		this.imageHeight = imageHeight;
	}

	public void setImageWidth(ValueExpression imageWidth) {
		this.imageWidth = imageWidth;
	}

	public void setValueChangeListener(ValueExpression valueChangeListeners) {
		this.valueChangeListeners = valueChangeListeners;
	}

	public void setFor(ValueExpression forValue) {
		this.forValue = forValue;
	}

	public void setCalendarLayout(ValueExpression calendarLayout) {
		this.calendarLayout = calendarLayout;
	}

	public void setForValueFormat(ValueExpression forValueFormat) {
		this.forValueFormat = forValueFormat;
	}

	public void setHomeDate(ValueExpression homeDate) {
		this.homeDate = homeDate;
	}

	public void setDefaultSelectedDate(ValueExpression defaultSelectedDate) {
		this.defaultSelectedDate = defaultSelectedDate;
	}

	public void setHomeDateLabel(ValueExpression homeDateLabel) {
		this.homeDateLabel = homeDateLabel;
	}

	public void setPopupStyleClass(ValueExpression popupStyleClass) {
		this.popupStyleClass = popupStyleClass;
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
			LOG.debug("  calendarLayout='"+calendarLayout+"'");
			LOG.debug("  forValueFormat='"+forValueFormat+"'");
			LOG.debug("  homeDate='"+homeDate+"'");
			LOG.debug("  defaultSelectedDate='"+defaultSelectedDate+"'");
			LOG.debug("  homeDateLabel='"+homeDateLabel+"'");
			LOG.debug("  popupStyleClass='"+popupStyleClass+"'");
		}
		if ((uiComponent instanceof DateChooserComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'DateChooserComponent'.");
		}

		super.setProperties(uiComponent);

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
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.VALUE_CHANGE_LISTENER_TYPE, valueChangeListeners);
		}

		if (forValue != null) {
			if (forValue.isLiteralText()==false) {
				component.setValueExpression(Properties.FOR, forValue);

			} else {
				component.setFor(forValue.getExpressionString());
			}
		}

		if (calendarLayout != null) {
			if (calendarLayout.isLiteralText()==false) {
				component.setValueExpression(Properties.CALENDAR_LAYOUT, calendarLayout);

			} else {
				component.setCalendarLayout(calendarLayout.getExpressionString());
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

		if (defaultSelectedDate != null) {
			if (defaultSelectedDate.isLiteralText()==false) {
				component.setValueExpression(Properties.DEFAULT_SELECTED_DATE, defaultSelectedDate);

			} else {
				component.setDefaultSelectedDate(defaultSelectedDate.getExpressionString());
			}
		}

		if (homeDateLabel != null) {
			if (homeDateLabel.isLiteralText()==false) {
				component.setValueExpression(Properties.HOME_DATE_LABEL, homeDateLabel);

			} else {
				component.setHomeDateLabel(homeDateLabel.getExpressionString());
			}
		}

		if (popupStyleClass != null) {
			if (popupStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.POPUP_STYLE_CLASS, popupStyleClass);

			} else {
				component.setPopupStyleClass(popupStyleClass.getExpressionString());
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
		calendarLayout = null;
		forValueFormat = null;
		homeDate = null;
		defaultSelectedDate = null;
		homeDateLabel = null;
		popupStyleClass = null;

		super.release();
	}

}
