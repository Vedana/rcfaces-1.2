package org.rcfaces.core.component;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.ComboComponent;
import org.rcfaces.core.component.capability.IEditableCapability;
import org.rcfaces.core.component.capability.IImageCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.internal.component.Properties;

public class ComboExComponent extends ComboComponent implements 
	IEditableCapability,
	IImageCapability,
	ITextCapability,
	IReadOnlyCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.comboEx";


	public ComboExComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ComboExComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final boolean isEditable() {
		return isEditable(null);
	}

	public final boolean isEditable(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.EDITABLE, false, facesContext);
	}

	public final void setEditable(boolean editable) {
		engine.setProperty(Properties.EDITABLE, editable);
	}

	public final void setEditable(ValueBinding editable) {
		engine.setProperty(Properties.EDITABLE, editable);
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

	public final boolean isReadOnly() {
		return isReadOnly(null);
	}

	public final boolean isReadOnly(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.READ_ONLY, false, facesContext);
	}

	public final void setReadOnly(boolean readOnly) {
		engine.setProperty(Properties.READ_ONLY, readOnly);
	}

	public final void setReadOnly(ValueBinding readOnly) {
		engine.setProperty(Properties.READ_ONLY, readOnly);
	}

	public final int getMaxTextLength() {
		return getMaxTextLength(null);
	}

	public final int getMaxTextLength(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.MAX_TEXT_LENGTH, 0, facesContext);
	}

	public final void setMaxTextLength(int maxTextLength) {
		engine.setProperty(Properties.MAX_TEXT_LENGTH, maxTextLength);
	}

	public final void setMaxTextLength(ValueBinding maxTextLength) {
		engine.setProperty(Properties.MAX_TEXT_LENGTH, maxTextLength);
	}

	public final boolean isMaxTextLengthSetted() {
		return engine.isPropertySetted(Properties.MAX_TEXT_LENGTH);
	}

	public final int getColumnNumber() {
		return getColumnNumber(null);
	}

	public final int getColumnNumber(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.COLUMN_NUMBER, 0, facesContext);
	}

	public final void setColumnNumber(int columnNumber) {
		engine.setProperty(Properties.COLUMN_NUMBER, columnNumber);
	}

	public final void setColumnNumber(ValueBinding columnNumber) {
		engine.setProperty(Properties.COLUMN_NUMBER, columnNumber);
	}

	public final boolean isColumnNumberSetted() {
		return engine.isPropertySetted(Properties.COLUMN_NUMBER);
	}

	public final boolean isAutoCompletion() {
		return isAutoCompletion(null);
	}

	public final boolean isAutoCompletion(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.AUTO_COMPLETION, false, facesContext);
	}

	public final void setAutoCompletion(boolean autoCompletion) {
		engine.setProperty(Properties.AUTO_COMPLETION, autoCompletion);
	}

	public final void setAutoCompletion(ValueBinding autoCompletion) {
		engine.setProperty(Properties.AUTO_COMPLETION, autoCompletion);
	}

	public final boolean isAutoCompletionSetted() {
		return engine.isPropertySetted(Properties.AUTO_COMPLETION);
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
