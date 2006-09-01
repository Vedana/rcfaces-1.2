package org.rcfaces.core.component;

import java.util.Date;

import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IValueChangeEventCapability;
import org.rcfaces.core.component.familly.IImageButtonFamilly;
import org.rcfaces.core.internal.component.AbstractCalendarComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.DateConverter;
import org.rcfaces.core.internal.converter.TextPositionConverter;

public class DateChooserComponent extends AbstractCalendarComponent implements 
	IImageButtonFamilly,
	IValueChangeEventCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.dateChooser";


	public DateChooserComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public DateChooserComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final void setHomeDate(String date) {


				setHomeDate((Date)DateConverter.SINGLETON.getAsObject(null, this, date));
			
	}

	protected final Converter getTextPositionConverter() {


				return TextPositionConverter.SINGLETON;
			
	}

	public final void setTextPosition(String textPosition) {


			setTextPosition(((Integer)getTextPositionConverter().getAsObject(null, null, textPosition)).intValue());
		
	}

	public final java.lang.String getImageURL() {
		return getImageURL(null);
	}

	public final java.lang.String getImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.IMAGE_URL, facesContext);
	}

	public final void setImageURL(java.lang.String imageURL) {
		engine.setProperty(Properties.IMAGE_URL, imageURL);
	}

	public final void setImageURL(ValueBinding imageURL) {
		engine.setProperty(Properties.IMAGE_URL, imageURL);
	}

	public final java.lang.String getDisabledImageURL() {
		return getDisabledImageURL(null);
	}

	public final java.lang.String getDisabledImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DISABLED_IMAGE_URL, facesContext);
	}

	public final void setDisabledImageURL(java.lang.String disabledImageURL) {
		engine.setProperty(Properties.DISABLED_IMAGE_URL, disabledImageURL);
	}

	public final void setDisabledImageURL(ValueBinding disabledImageURL) {
		engine.setProperty(Properties.DISABLED_IMAGE_URL, disabledImageURL);
	}

	public final java.lang.String getHoverImageURL() {
		return getHoverImageURL(null);
	}

	public final java.lang.String getHoverImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.HOVER_IMAGE_URL, facesContext);
	}

	public final void setHoverImageURL(java.lang.String hoverImageURL) {
		engine.setProperty(Properties.HOVER_IMAGE_URL, hoverImageURL);
	}

	public final void setHoverImageURL(ValueBinding hoverImageURL) {
		engine.setProperty(Properties.HOVER_IMAGE_URL, hoverImageURL);
	}

	public final java.lang.String getSelectedImageURL() {
		return getSelectedImageURL(null);
	}

	public final java.lang.String getSelectedImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SELECTED_IMAGE_URL, facesContext);
	}

	public final void setSelectedImageURL(java.lang.String selectedImageURL) {
		engine.setProperty(Properties.SELECTED_IMAGE_URL, selectedImageURL);
	}

	public final void setSelectedImageURL(ValueBinding selectedImageURL) {
		engine.setProperty(Properties.SELECTED_IMAGE_URL, selectedImageURL);
	}

	public final boolean isBorder() {
		return isBorder(null);
	}

	public final boolean isBorder(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.BORDER, true, facesContext);
	}

	public final void setBorder(boolean border) {
		engine.setProperty(Properties.BORDER, border);
	}

	public final void setBorder(ValueBinding border) {
		engine.setProperty(Properties.BORDER, border);
	}

	public final java.lang.String getBorderType() {
		return getBorderType(null);
	}

	public final java.lang.String getBorderType(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BORDER_TYPE, facesContext);
	}

	public final void setBorderType(java.lang.String borderType) {
		engine.setProperty(Properties.BORDER_TYPE, borderType);
	}

	public final void setBorderType(ValueBinding borderType) {
		engine.setProperty(Properties.BORDER_TYPE, borderType);
	}

	public final java.lang.String getText() {
		return getText(null);
	}

	public final java.lang.String getText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TEXT, facesContext);
	}

	public final void setText(java.lang.String text) {
		engine.setProperty(Properties.TEXT, text);
	}

	public final void setText(ValueBinding text) {
		engine.setProperty(Properties.TEXT, text);
	}

	public final int getTextPosition() {
		return getTextPosition(null);
	}

	public final int getTextPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.TEXT_POSITION,0, facesContext);
	}

	public final void setTextPosition(int textPosition) {
		engine.setProperty(Properties.TEXT_POSITION, textPosition);
	}

	public final void setTextPosition(ValueBinding textPosition) {
		engine.setProperty(Properties.TEXT_POSITION, textPosition);
	}

	public final int getImageWidth() {
		return getImageWidth(null);
	}

	public final int getImageWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.IMAGE_WIDTH,0, facesContext);
	}

	public final void setImageWidth(int imageWidth) {
		engine.setProperty(Properties.IMAGE_WIDTH, imageWidth);
	}

	public final void setImageWidth(ValueBinding imageWidth) {
		engine.setProperty(Properties.IMAGE_WIDTH, imageWidth);
	}

	public final int getImageHeight() {
		return getImageHeight(null);
	}

	public final int getImageHeight(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.IMAGE_HEIGHT,0, facesContext);
	}

	public final void setImageHeight(int imageHeight) {
		engine.setProperty(Properties.IMAGE_HEIGHT, imageHeight);
	}

	public final void setImageHeight(ValueBinding imageHeight) {
		engine.setProperty(Properties.IMAGE_HEIGHT, imageHeight);
	}

	public final void addValueChangeListener(javax.faces.event.ValueChangeListener listener) {
		addFacesListener(listener);
	}

	public final void removeValueChangeListener(javax.faces.event.ValueChangeListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listValueChangeListeners() {
		return getFacesListeners(javax.faces.event.ValueChangeListener.class);
	}

	public final String getFor() {
		return getFor(null);
	}

	public final String getFor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOR, facesContext);
	}

	public final void setFor(String forVal) {
		engine.setProperty(Properties.FOR, forVal);
	}

	public final void setFor(ValueBinding forVal) {
		engine.setProperty(Properties.FOR, forVal);
	}

	public final boolean isForSetted() {
		return engine.isPropertySetted(Properties.FOR);
	}

	public final String getForValueFormat() {
		return getForValueFormat(null);
	}

	public final String getForValueFormat(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOR_VALUE_FORMAT, facesContext);
	}

	public final void setForValueFormat(String forValueFormat) {
		engine.setProperty(Properties.FOR_VALUE_FORMAT, forValueFormat);
	}

	public final void setForValueFormat(ValueBinding forValueFormat) {
		engine.setProperty(Properties.FOR_VALUE_FORMAT, forValueFormat);
	}

	public final boolean isForValueFormatSetted() {
		return engine.isPropertySetted(Properties.FOR_VALUE_FORMAT);
	}

	public final java.util.Date getHomeDate() {
		return getHomeDate(null);
	}

	public final java.util.Date getHomeDate(javax.faces.context.FacesContext facesContext) {
		return (java.util.Date)engine.getValue(Properties.HOME_DATE, facesContext);
	}

	public final void setHomeDate(java.util.Date homeDate) {
		engine.setProperty(Properties.HOME_DATE, homeDate);
	}

	public final void setHomeDate(ValueBinding homeDate) {
		engine.setProperty(Properties.HOME_DATE, homeDate);
	}

	public final boolean isHomeDateSetted() {
		return engine.isPropertySetted(Properties.HOME_DATE);
	}

	public final String getHomeDateLabel() {
		return getHomeDateLabel(null);
	}

	public final String getHomeDateLabel(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.HOME_DATE_LABEL, facesContext);
	}

	public final void setHomeDateLabel(String homeDateLabel) {
		engine.setProperty(Properties.HOME_DATE_LABEL, homeDateLabel);
	}

	public final void setHomeDateLabel(ValueBinding homeDateLabel) {
		engine.setProperty(Properties.HOME_DATE_LABEL, homeDateLabel);
	}

	public final boolean isHomeDateLabelSetted() {
		return engine.isPropertySetted(Properties.HOME_DATE_LABEL);
	}

	public void release() {
		super.release();
	}
}
