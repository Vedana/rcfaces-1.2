package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IAccessKeyCapability;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.capability.IFontCapability;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.component.AbstractOutputComponent;
import org.rcfaces.core.component.capability.ITextAlignmentCapability;
import org.rcfaces.core.component.capability.IForCapability;

public class TextComponent extends AbstractOutputComponent implements 
	ITextCapability,
	IFontCapability,
	ITextAlignmentCapability,
	IAccessKeyCapability,
	IForCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.text";


	public TextComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public TextComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final java.lang.String getText() {
		return getText(null);
	}

	public final java.lang.String getText(javax.faces.context.FacesContext facesContext) {
		return org.rcfaces.core.internal.tools.ValuesTools.valueToString(this, facesContext);
	}

	public final void setText(java.lang.String text) {
		setValue(text);
	}

	public final void setText(ValueBinding text) {
		setValue(text);
	}

	public final java.lang.Boolean getFontBold() {
		return getFontBold(null);
	}

	public final java.lang.Boolean getFontBold(javax.faces.context.FacesContext facesContext) {
		return engine.getBooleanProperty(Properties.FONT_BOLD, facesContext);
	}

	public final void setFontBold(java.lang.Boolean fontBold) {
		engine.setProperty(Properties.FONT_BOLD, fontBold);
	}

	public final void setFontBold(ValueBinding fontBold) {
		engine.setProperty(Properties.FONT_BOLD, fontBold);
	}

	public final java.lang.Boolean getFontItalic() {
		return getFontItalic(null);
	}

	public final java.lang.Boolean getFontItalic(javax.faces.context.FacesContext facesContext) {
		return engine.getBooleanProperty(Properties.FONT_ITALIC, facesContext);
	}

	public final void setFontItalic(java.lang.Boolean fontItalic) {
		engine.setProperty(Properties.FONT_ITALIC, fontItalic);
	}

	public final void setFontItalic(ValueBinding fontItalic) {
		engine.setProperty(Properties.FONT_ITALIC, fontItalic);
	}

	public final java.lang.String getFontName() {
		return getFontName(null);
	}

	public final java.lang.String getFontName(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FONT_NAME, facesContext);
	}

	public final void setFontName(java.lang.String fontName) {
		engine.setProperty(Properties.FONT_NAME, fontName);
	}

	public final void setFontName(ValueBinding fontName) {
		engine.setProperty(Properties.FONT_NAME, fontName);
	}

	public final java.lang.String getFontSize() {
		return getFontSize(null);
	}

	public final java.lang.String getFontSize(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FONT_SIZE, facesContext);
	}

	public final void setFontSize(java.lang.String fontSize) {
		engine.setProperty(Properties.FONT_SIZE, fontSize);
	}

	public final void setFontSize(ValueBinding fontSize) {
		engine.setProperty(Properties.FONT_SIZE, fontSize);
	}

	public final java.lang.Boolean getFontUnderline() {
		return getFontUnderline(null);
	}

	public final java.lang.Boolean getFontUnderline(javax.faces.context.FacesContext facesContext) {
		return engine.getBooleanProperty(Properties.FONT_UNDERLINE, facesContext);
	}

	public final void setFontUnderline(java.lang.Boolean fontUnderline) {
		engine.setProperty(Properties.FONT_UNDERLINE, fontUnderline);
	}

	public final void setFontUnderline(ValueBinding fontUnderline) {
		engine.setProperty(Properties.FONT_UNDERLINE, fontUnderline);
	}

	public final java.lang.String getTextAlignment() {
		return getTextAlignment(null);
	}

	public final java.lang.String getTextAlignment(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TEXT_ALIGNMENT, facesContext);
	}

	public final void setTextAlignment(java.lang.String textAlignment) {
		engine.setProperty(Properties.TEXT_ALIGNMENT, textAlignment);
	}

	public final void setTextAlignment(ValueBinding textAlignment) {
		engine.setProperty(Properties.TEXT_ALIGNMENT, textAlignment);
	}

	public final java.lang.String getAccessKey() {
		return getAccessKey(null);
	}

	public final java.lang.String getAccessKey(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ACCESS_KEY, facesContext);
	}

	public final void setAccessKey(java.lang.String accessKey) {
		engine.setProperty(Properties.ACCESS_KEY, accessKey);
	}

	public final void setAccessKey(ValueBinding accessKey) {
		engine.setProperty(Properties.ACCESS_KEY, accessKey);
	}

	public final java.lang.String getFor() {
		return getFor(null);
	}

	public final java.lang.String getFor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOR, facesContext);
	}

	public final void setFor(java.lang.String forValue) {
		engine.setProperty(Properties.FOR, forValue);
	}

	public final void setFor(ValueBinding forValue) {
		engine.setProperty(Properties.FOR, forValue);
	}

	public final String getType() {
		return getType(null);
	}

	public final String getType(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TYPE, facesContext);
	}

	public final void setType(String type) {
		engine.setProperty(Properties.TYPE, type);
	}

	public final void setType(ValueBinding type) {
		engine.setProperty(Properties.TYPE, type);
	}

	public final boolean isTypeSetted() {
		return engine.isPropertySetted(Properties.TYPE);
	}

	public void release() {
		super.release();
	}
}
