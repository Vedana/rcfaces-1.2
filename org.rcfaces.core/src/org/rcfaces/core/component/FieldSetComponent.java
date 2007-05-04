package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ImageAccessorTools;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.capability.IFontCapability;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IBorderTypeCapability;
import org.rcfaces.core.component.capability.IVerticalAlignmentCapability;
import org.rcfaces.core.internal.capability.IImageAccessorsCapability;
import org.rcfaces.core.component.capability.IImageSizeCapability;
import org.rcfaces.core.component.AbstractOutputComponent;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.component.capability.ITextAlignmentCapability;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.component.capability.IImageCapability;
import org.rcfaces.core.component.capability.IVariableScopeCapability;

/**
 * <p>The fieldSet Component is a container with a title (text and/or picture).</p>
 * <p>The fieldSet Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text, font &amp; image</li>
 * <li>Margin &amp; border</li>
 * <li>Help</li>
 * <li>Visibility</li>
 * <li>Background Image</li>
 * <li>Events Handling</li>
 * <li>Contextual Menu</li>
 * <li>Async Render (AJAX)</li>
 * </ul>
 * </p>
 */
public class FieldSetComponent extends AbstractOutputComponent implements 
	IFontCapability,
	ITextCapability,
	ITextAlignmentCapability,
	IVerticalAlignmentCapability,
	IBorderTypeCapability,
	IImageCapability,
	IImageSizeCapability,
	IVariableScopeCapability,
	IImageAccessorsCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.fieldSet";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractOutputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"fontUnderline","imageHeight","imageURL","scopeValue","verticalAlignment","fontSize","fontItalic","fontBold","text","scopeVar","textAlignment","imageWidth","borderType","fontName"}));
	}
	protected static final String CAMELIA_VALUE_ALIAS="text";

	public FieldSetComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public FieldSetComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final IContentAccessors getImageAccessors(FacesContext facesContext) {


			return ImageAccessorTools.createImageAccessors(facesContext, this, engine);
		
	}

	public java.lang.Boolean getFontBold() {
		return getFontBold(null);
	}

	/**
	 * See {@link #getFontBold() getFontBold()} for more details
	 */
	public java.lang.Boolean getFontBold(javax.faces.context.FacesContext facesContext) {
		return engine.getBooleanProperty(Properties.FONT_BOLD, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontBold" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontBoldSetted() {
		return engine.isPropertySetted(Properties.FONT_BOLD);
	}

	public void setFontBold(java.lang.Boolean fontBold) {
		engine.setProperty(Properties.FONT_BOLD, fontBold);
	}

	/**
	 * See {@link #setFontBold(Boolean) setFontBold(Boolean)} for more details
	 */
	public void setFontBold(ValueBinding fontBold) {
		engine.setProperty(Properties.FONT_BOLD, fontBold);
	}

	public java.lang.Boolean getFontItalic() {
		return getFontItalic(null);
	}

	/**
	 * See {@link #getFontItalic() getFontItalic()} for more details
	 */
	public java.lang.Boolean getFontItalic(javax.faces.context.FacesContext facesContext) {
		return engine.getBooleanProperty(Properties.FONT_ITALIC, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontItalic" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontItalicSetted() {
		return engine.isPropertySetted(Properties.FONT_ITALIC);
	}

	public void setFontItalic(java.lang.Boolean fontItalic) {
		engine.setProperty(Properties.FONT_ITALIC, fontItalic);
	}

	/**
	 * See {@link #setFontItalic(Boolean) setFontItalic(Boolean)} for more details
	 */
	public void setFontItalic(ValueBinding fontItalic) {
		engine.setProperty(Properties.FONT_ITALIC, fontItalic);
	}

	public java.lang.String getFontName() {
		return getFontName(null);
	}

	/**
	 * See {@link #getFontName() getFontName()} for more details
	 */
	public java.lang.String getFontName(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FONT_NAME, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontName" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontNameSetted() {
		return engine.isPropertySetted(Properties.FONT_NAME);
	}

	public void setFontName(java.lang.String fontName) {
		engine.setProperty(Properties.FONT_NAME, fontName);
	}

	/**
	 * See {@link #setFontName(String) setFontName(String)} for more details
	 */
	public void setFontName(ValueBinding fontName) {
		engine.setProperty(Properties.FONT_NAME, fontName);
	}

	public java.lang.String getFontSize() {
		return getFontSize(null);
	}

	/**
	 * See {@link #getFontSize() getFontSize()} for more details
	 */
	public java.lang.String getFontSize(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FONT_SIZE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontSize" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontSizeSetted() {
		return engine.isPropertySetted(Properties.FONT_SIZE);
	}

	public void setFontSize(java.lang.String fontSize) {
		engine.setProperty(Properties.FONT_SIZE, fontSize);
	}

	/**
	 * See {@link #setFontSize(String) setFontSize(String)} for more details
	 */
	public void setFontSize(ValueBinding fontSize) {
		engine.setProperty(Properties.FONT_SIZE, fontSize);
	}

	public java.lang.Boolean getFontUnderline() {
		return getFontUnderline(null);
	}

	/**
	 * See {@link #getFontUnderline() getFontUnderline()} for more details
	 */
	public java.lang.Boolean getFontUnderline(javax.faces.context.FacesContext facesContext) {
		return engine.getBooleanProperty(Properties.FONT_UNDERLINE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontUnderline" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontUnderlineSetted() {
		return engine.isPropertySetted(Properties.FONT_UNDERLINE);
	}

	public void setFontUnderline(java.lang.Boolean fontUnderline) {
		engine.setProperty(Properties.FONT_UNDERLINE, fontUnderline);
	}

	/**
	 * See {@link #setFontUnderline(Boolean) setFontUnderline(Boolean)} for more details
	 */
	public void setFontUnderline(ValueBinding fontUnderline) {
		engine.setProperty(Properties.FONT_UNDERLINE, fontUnderline);
	}

	public java.lang.String getText() {
		return getText(null);
	}

	/**
	 * See {@link #getText() getText()} for more details
	 */
	public java.lang.String getText(javax.faces.context.FacesContext facesContext) {
		return org.rcfaces.core.internal.tools.ValuesTools.valueToString(this, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "text" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextSetted() {
		return engine.isPropertySetted(Properties.TEXT);
	}

	public void setText(java.lang.String text) {
		setValue(text);
	}

	/**
	 * See {@link #setText(String) setText(String)} for more details
	 */
	public void setText(ValueBinding text) {
		setValue(text);
	}

	public java.lang.String getTextAlignment() {
		return getTextAlignment(null);
	}

	/**
	 * See {@link #getTextAlignment() getTextAlignment()} for more details
	 */
	public java.lang.String getTextAlignment(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TEXT_ALIGNMENT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "textAlignment" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextAlignmentSetted() {
		return engine.isPropertySetted(Properties.TEXT_ALIGNMENT);
	}

	public void setTextAlignment(java.lang.String textAlignment) {
		engine.setProperty(Properties.TEXT_ALIGNMENT, textAlignment);
	}

	/**
	 * See {@link #setTextAlignment(String) setTextAlignment(String)} for more details
	 */
	public void setTextAlignment(ValueBinding textAlignment) {
		engine.setProperty(Properties.TEXT_ALIGNMENT, textAlignment);
	}

	public java.lang.String getVerticalAlignment() {
		return getVerticalAlignment(null);
	}

	/**
	 * See {@link #getVerticalAlignment() getVerticalAlignment()} for more details
	 */
	public java.lang.String getVerticalAlignment(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.VERTICAL_ALIGNMENT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "verticalAlignment" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isVerticalAlignmentSetted() {
		return engine.isPropertySetted(Properties.VERTICAL_ALIGNMENT);
	}

	public void setVerticalAlignment(java.lang.String verticalAlignment) {
		engine.setProperty(Properties.VERTICAL_ALIGNMENT, verticalAlignment);
	}

	/**
	 * See {@link #setVerticalAlignment(String) setVerticalAlignment(String)} for more details
	 */
	public void setVerticalAlignment(ValueBinding verticalAlignment) {
		engine.setProperty(Properties.VERTICAL_ALIGNMENT, verticalAlignment);
	}

	public java.lang.String getBorderType() {
		return getBorderType(null);
	}

	/**
	 * See {@link #getBorderType() getBorderType()} for more details
	 */
	public java.lang.String getBorderType(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BORDER_TYPE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "borderType" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBorderTypeSetted() {
		return engine.isPropertySetted(Properties.BORDER_TYPE);
	}

	public void setBorderType(java.lang.String borderType) {
		engine.setProperty(Properties.BORDER_TYPE, borderType);
	}

	/**
	 * See {@link #setBorderType(String) setBorderType(String)} for more details
	 */
	public void setBorderType(ValueBinding borderType) {
		engine.setProperty(Properties.BORDER_TYPE, borderType);
	}

	public java.lang.String getImageURL() {
		return getImageURL(null);
	}

	/**
	 * See {@link #getImageURL() getImageURL()} for more details
	 */
	public java.lang.String getImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.IMAGE_URL, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "imageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImageURLSetted() {
		return engine.isPropertySetted(Properties.IMAGE_URL);
	}

	public void setImageURL(java.lang.String imageURL) {
		engine.setProperty(Properties.IMAGE_URL, imageURL);
	}

	/**
	 * See {@link #setImageURL(String) setImageURL(String)} for more details
	 */
	public void setImageURL(ValueBinding imageURL) {
		engine.setProperty(Properties.IMAGE_URL, imageURL);
	}

	public final IContentAccessors getImageAccessors() {


			return getImageAccessors(null);
		
	}

	public int getImageHeight() {
		return getImageHeight(null);
	}

	/**
	 * See {@link #getImageHeight() getImageHeight()} for more details
	 */
	public int getImageHeight(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.IMAGE_HEIGHT,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "imageHeight" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImageHeightSetted() {
		return engine.isPropertySetted(Properties.IMAGE_HEIGHT);
	}

	public void setImageHeight(int imageHeight) {
		engine.setProperty(Properties.IMAGE_HEIGHT, imageHeight);
	}

	/**
	 * See {@link #setImageHeight(int) setImageHeight(int)} for more details
	 */
	public void setImageHeight(ValueBinding imageHeight) {
		engine.setProperty(Properties.IMAGE_HEIGHT, imageHeight);
	}

	public int getImageWidth() {
		return getImageWidth(null);
	}

	/**
	 * See {@link #getImageWidth() getImageWidth()} for more details
	 */
	public int getImageWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.IMAGE_WIDTH,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "imageWidth" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImageWidthSetted() {
		return engine.isPropertySetted(Properties.IMAGE_WIDTH);
	}

	public void setImageWidth(int imageWidth) {
		engine.setProperty(Properties.IMAGE_WIDTH, imageWidth);
	}

	/**
	 * See {@link #setImageWidth(int) setImageWidth(int)} for more details
	 */
	public void setImageWidth(ValueBinding imageWidth) {
		engine.setProperty(Properties.IMAGE_WIDTH, imageWidth);
	}

	public javax.faces.el.ValueBinding getScopeValue() {
		return getScopeValue(null);
	}

	/**
	 * See {@link #getScopeValue() getScopeValue()} for more details
	 */
	public javax.faces.el.ValueBinding getScopeValue(javax.faces.context.FacesContext facesContext) {
		return engine.getValueBindingProperty(Properties.SCOPE_VALUE);
	}

	/**
	 * Returns <code>true</code> if the attribute "scopeValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isScopeValueSetted() {
		return engine.isPropertySetted(Properties.SCOPE_VALUE);
	}

	public void setScopeValue(javax.faces.el.ValueBinding scopeValue) {
		engine.setProperty(Properties.SCOPE_VALUE, scopeValue);
	}

	public java.lang.String getScopeVar() {
		return getScopeVar(null);
	}

	/**
	 * See {@link #getScopeVar() getScopeVar()} for more details
	 */
	public java.lang.String getScopeVar(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SCOPE_VAR, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "scopeVar" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isScopeVarSetted() {
		return engine.isPropertySetted(Properties.SCOPE_VAR);
	}

	public void setScopeVar(java.lang.String scopeVar) {
		engine.setProperty(Properties.SCOPE_VAR, scopeVar);
	}

	/**
	 * See {@link #setScopeVar(String) setScopeVar(String)} for more details
	 */
	public void setScopeVar(ValueBinding scopeVar) {
		engine.setProperty(Properties.SCOPE_VAR, scopeVar);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}

	protected String getCameliaValueAlias() {
		return CAMELIA_VALUE_ALIAS;
	}
}
