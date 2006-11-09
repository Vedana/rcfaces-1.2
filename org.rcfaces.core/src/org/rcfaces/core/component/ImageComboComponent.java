package org.rcfaces.core.component;

import java.lang.String;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.convert.Converter;
import org.rcfaces.core.internal.converter.TextPositionConverter;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ImageAccessorTools;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.familly.IImageButtonFamilly;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.component.AbstractMenuComponent;

public class ImageComboComponent extends AbstractMenuComponent implements 
	IImageButtonFamilly {

	public static final String COMPONENT_TYPE="org.rcfaces.core.imageCombo";


	public ImageComboComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ImageComboComponent(String componentId) {
		this();
		setId(componentId);
	}

	protected Converter getTextPositionConverter() {


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

	public final IContentAccessors getImageAccessors() {


			return getImageAccessors(null);
		
	}

	public final IContentAccessors getImageAccessors(FacesContext facesContext) {


			return ImageAccessorTools.createImageAccessors(facesContext, this, engine);
		
	}

	public final int getPopupRowNumber() {
		return getPopupRowNumber(null);
	}

	public final int getPopupRowNumber(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.POPUP_ROW_NUMBER, 0, facesContext);
	}

	public final void setPopupRowNumber(int popupRowNumber) {
		engine.setProperty(Properties.POPUP_ROW_NUMBER, popupRowNumber);
	}

	public final void setPopupRowNumber(ValueBinding popupRowNumber) {
		engine.setProperty(Properties.POPUP_ROW_NUMBER, popupRowNumber);
	}

	public final boolean isPopupRowNumberSetted() {
		return engine.isPropertySetted(Properties.POPUP_ROW_NUMBER);
	}

	public void release() {
		super.release();
	}
}
