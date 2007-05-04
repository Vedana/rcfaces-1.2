package org.rcfaces.core.component;

import java.lang.String;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IAccessKeyCapability;
import org.rcfaces.core.component.capability.IDisabledCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.capability.IMenuCapability;
import org.rcfaces.core.component.CardComponent;
import org.rcfaces.core.internal.tools.ImageAccessorTools;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.capability.IFontCapability;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.internal.capability.IImageAccessorsCapability;
import org.rcfaces.core.component.TabbedPaneComponent;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.internal.tools.MenuTools;
import org.rcfaces.core.component.capability.IStatesImageCapability;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.component.familly.IContentAccessors;

/**
 * An element belonging to a <a href="/comps/tabbedPaneComponent.html">tabbedPane Component</a> and holding the components.
 */
public class TabComponent extends CardComponent implements 
	ITextCapability,
	IFontCapability,
	IDisabledCapability,
	IStatesImageCapability,
	IAccessKeyCapability,
	IMenuCapability,
	IImageAccessorsCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.tab";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CardComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"fontBold","hoverImageURL","fontUnderline","imageURL","text","disabledImageURL","selectedImageURL","disabled","fontSize","accessKey","fontName","fontItalic"}));
	}

	public TabComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public TabComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final IContentAccessors getImageAccessors(FacesContext facesContext) {


			return ImageAccessorTools.createImageAccessors(facesContext, this, engine);
		
	}

	public final TabbedPaneComponent getTabbedPane() {


				return (TabbedPaneComponent)getCardBox();
			
	}

	public java.lang.String getText() {
		return getText(null);
	}

	/**
	 * See {@link #getText() getText()} for more details
	 */
	public java.lang.String getText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TEXT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "text" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextSetted() {
		return engine.isPropertySetted(Properties.TEXT);
	}

	public void setText(java.lang.String text) {
		engine.setProperty(Properties.TEXT, text);
	}

	/**
	 * See {@link #setText(String) setText(String)} for more details
	 */
	public void setText(ValueBinding text) {
		engine.setProperty(Properties.TEXT, text);
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

	public boolean isDisabled() {
		return isDisabled(null);
	}

	/**
	 * See {@link #isDisabled() isDisabled()} for more details
	 */
	public boolean isDisabled(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.DISABLED, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "disabled" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDisabledSetted() {
		return engine.isPropertySetted(Properties.DISABLED);
	}

	public void setDisabled(boolean disabled) {
		engine.setProperty(Properties.DISABLED, disabled);
	}

	/**
	 * See {@link #setDisabled(boolean) setDisabled(boolean)} for more details
	 */
	public void setDisabled(ValueBinding disabled) {
		engine.setProperty(Properties.DISABLED, disabled);
	}

	public java.lang.String getDisabledImageURL() {
		return getDisabledImageURL(null);
	}

	/**
	 * See {@link #getDisabledImageURL() getDisabledImageURL()} for more details
	 */
	public java.lang.String getDisabledImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DISABLED_IMAGE_URL, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "disabledImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDisabledImageURLSetted() {
		return engine.isPropertySetted(Properties.DISABLED_IMAGE_URL);
	}

	public void setDisabledImageURL(java.lang.String disabledImageURL) {
		engine.setProperty(Properties.DISABLED_IMAGE_URL, disabledImageURL);
	}

	/**
	 * See {@link #setDisabledImageURL(String) setDisabledImageURL(String)} for more details
	 */
	public void setDisabledImageURL(ValueBinding disabledImageURL) {
		engine.setProperty(Properties.DISABLED_IMAGE_URL, disabledImageURL);
	}

	public java.lang.String getHoverImageURL() {
		return getHoverImageURL(null);
	}

	/**
	 * See {@link #getHoverImageURL() getHoverImageURL()} for more details
	 */
	public java.lang.String getHoverImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.HOVER_IMAGE_URL, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "hoverImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHoverImageURLSetted() {
		return engine.isPropertySetted(Properties.HOVER_IMAGE_URL);
	}

	public void setHoverImageURL(java.lang.String hoverImageURL) {
		engine.setProperty(Properties.HOVER_IMAGE_URL, hoverImageURL);
	}

	/**
	 * See {@link #setHoverImageURL(String) setHoverImageURL(String)} for more details
	 */
	public void setHoverImageURL(ValueBinding hoverImageURL) {
		engine.setProperty(Properties.HOVER_IMAGE_URL, hoverImageURL);
	}

	public java.lang.String getSelectedImageURL() {
		return getSelectedImageURL(null);
	}

	/**
	 * See {@link #getSelectedImageURL() getSelectedImageURL()} for more details
	 */
	public java.lang.String getSelectedImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SELECTED_IMAGE_URL, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "selectedImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSelectedImageURLSetted() {
		return engine.isPropertySetted(Properties.SELECTED_IMAGE_URL);
	}

	public void setSelectedImageURL(java.lang.String selectedImageURL) {
		engine.setProperty(Properties.SELECTED_IMAGE_URL, selectedImageURL);
	}

	/**
	 * See {@link #setSelectedImageURL(String) setSelectedImageURL(String)} for more details
	 */
	public void setSelectedImageURL(ValueBinding selectedImageURL) {
		engine.setProperty(Properties.SELECTED_IMAGE_URL, selectedImageURL);
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

	public java.lang.String getAccessKey() {
		return getAccessKey(null);
	}

	/**
	 * See {@link #getAccessKey() getAccessKey()} for more details
	 */
	public java.lang.String getAccessKey(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ACCESS_KEY, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "accessKey" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAccessKeySetted() {
		return engine.isPropertySetted(Properties.ACCESS_KEY);
	}

	public void setAccessKey(java.lang.String accessKey) {
		engine.setProperty(Properties.ACCESS_KEY, accessKey);
	}

	/**
	 * See {@link #setAccessKey(String) setAccessKey(String)} for more details
	 */
	public void setAccessKey(ValueBinding accessKey) {
		engine.setProperty(Properties.ACCESS_KEY, accessKey);
	}

	public final IMenuComponent getMenu(String menuId) {


		return MenuTools.getMenu(this, menuId);
		
	}

	public final IMenuComponent getMenu() {


		return MenuTools.getMenu(this);
		
	}

	public final IMenuIterator listMenus() {


		return MenuTools.listMenus(this);
		
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
