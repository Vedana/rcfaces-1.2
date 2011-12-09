package org.rcfaces.core.component;

import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IStatesImageCapability;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IAccessKeyCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.IMenuComponent;
import org.apache.commons.logging.Log;
import java.util.Set;
import java.util.Collection;
import org.rcfaces.core.component.capability.IFontCapability;
import org.rcfaces.core.component.familly.IContentAccessors;
import java.lang.String;
import org.rcfaces.core.internal.tools.MenuTools;
import org.rcfaces.core.component.TabbedPaneComponent;
import org.rcfaces.core.internal.tools.ImageAccessorTools;
import org.rcfaces.core.component.capability.ITextDirectionCapability;
import javax.el.ValueExpression;
import org.rcfaces.core.component.capability.IDisabledCapability;
import java.util.HashSet;
import org.rcfaces.core.component.CardComponent;
import java.util.Arrays;
import org.rcfaces.core.internal.capability.IImageAccessorsCapability;
import org.rcfaces.core.component.capability.IMenuCapability;
import org.rcfaces.core.component.capability.ITextCapability;

/**
 * An element belonging to a <a href="/comps/tabbedPaneComponent.html">tabbedPane Component</a> and holding the components.
 */
public class TabComponent extends CardComponent implements 
	ITextCapability,
	ITextDirectionCapability,
	IFontCapability,
	IDisabledCapability,
	IStatesImageCapability,
	IAccessKeyCapability,
	IMenuCapability,
	IImageAccessorsCapability {

	private static final Log LOG = LogFactory.getLog(TabComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.tab";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=CardComponent.BEHAVIOR_EVENT_NAMES;

	public TabComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public TabComponent(String componentId) {
		this();
		setId(componentId);
	}

	public IContentAccessors getImageAccessors(FacesContext facesContext) {


			return ImageAccessorTools.createImageAccessors(facesContext, this, getComponentEngine());
		
	}

	public TabbedPaneComponent getTabbedPane() {


				return (TabbedPaneComponent)getCardBox();
			
	}

	public java.lang.String getText() {
		return getText(null);
	}

	/**
	 * See {@link #getText() getText()} for more details
	 */
	public java.lang.String getText(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.TEXT);
	}

	/**
	 * Returns <code>true</code> if the attribute "text" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextSetted() {
		return getStateHelper().get(Properties.TEXT)!=null;
	}

	public void setText(java.lang.String text) {
		getStateHelper().put(Properties.TEXT, text);
	}

	public int getTextDirection() {
		return getTextDirection(null);
	}

	/**
	 * See {@link #getTextDirection() getTextDirection()} for more details
	 */
	public int getTextDirection(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.TEXT_DIRECTION, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "textDirection" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextDirectionSetted() {
		return getStateHelper().get(Properties.TEXT_DIRECTION)!=null;
	}

	public void setTextDirection(int textDirection) {
		getStateHelper().put(Properties.TEXT_DIRECTION, textDirection);
	}

	public java.lang.Boolean getFontBold() {
		return getFontBold(null);
	}

	/**
	 * See {@link #getFontBold() getFontBold()} for more details
	 */
	public java.lang.Boolean getFontBold(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.FONT_BOLD);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontBold" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontBoldSetted() {
		return getStateHelper().get(Properties.FONT_BOLD)!=null;
	}

	public void setFontBold(java.lang.Boolean fontBold) {
		getStateHelper().put(Properties.FONT_BOLD, fontBold);
	}

	public java.lang.Boolean getFontItalic() {
		return getFontItalic(null);
	}

	/**
	 * See {@link #getFontItalic() getFontItalic()} for more details
	 */
	public java.lang.Boolean getFontItalic(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.FONT_ITALIC);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontItalic" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontItalicSetted() {
		return getStateHelper().get(Properties.FONT_ITALIC)!=null;
	}

	public void setFontItalic(java.lang.Boolean fontItalic) {
		getStateHelper().put(Properties.FONT_ITALIC, fontItalic);
	}

	public java.lang.String getFontName() {
		return getFontName(null);
	}

	/**
	 * See {@link #getFontName() getFontName()} for more details
	 */
	public java.lang.String getFontName(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.FONT_NAME);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontName" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontNameSetted() {
		return getStateHelper().get(Properties.FONT_NAME)!=null;
	}

	public void setFontName(java.lang.String fontName) {
		getStateHelper().put(Properties.FONT_NAME, fontName);
	}

	public java.lang.String getFontSize() {
		return getFontSize(null);
	}

	/**
	 * See {@link #getFontSize() getFontSize()} for more details
	 */
	public java.lang.String getFontSize(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.FONT_SIZE);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontSize" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontSizeSetted() {
		return getStateHelper().get(Properties.FONT_SIZE)!=null;
	}

	public void setFontSize(java.lang.String fontSize) {
		getStateHelper().put(Properties.FONT_SIZE, fontSize);
	}

	public java.lang.Boolean getFontUnderline() {
		return getFontUnderline(null);
	}

	/**
	 * See {@link #getFontUnderline() getFontUnderline()} for more details
	 */
	public java.lang.Boolean getFontUnderline(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.FONT_UNDERLINE);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontUnderline" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontUnderlineSetted() {
		return getStateHelper().get(Properties.FONT_UNDERLINE)!=null;
	}

	public void setFontUnderline(java.lang.Boolean fontUnderline) {
		getStateHelper().put(Properties.FONT_UNDERLINE, fontUnderline);
	}

	public boolean isDisabled() {
		return isDisabled(null);
	}

	/**
	 * See {@link #isDisabled() isDisabled()} for more details
	 */
	public boolean isDisabled(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.DISABLED, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "disabled" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDisabledSetted() {
		return getStateHelper().get(Properties.DISABLED)!=null;
	}

	public void setDisabled(boolean disabled) {
		getStateHelper().put(Properties.DISABLED, disabled);
	}

	public java.lang.String getDisabledImageURL() {
		return getDisabledImageURL(null);
	}

	/**
	 * See {@link #getDisabledImageURL() getDisabledImageURL()} for more details
	 */
	public java.lang.String getDisabledImageURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.DISABLED_IMAGE_URL);
	}

	/**
	 * Returns <code>true</code> if the attribute "disabledImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDisabledImageURLSetted() {
		return getStateHelper().get(Properties.DISABLED_IMAGE_URL)!=null;
	}

	public void setDisabledImageURL(java.lang.String disabledImageURL) {
		getStateHelper().put(Properties.DISABLED_IMAGE_URL, disabledImageURL);
	}

	public java.lang.String getHoverImageURL() {
		return getHoverImageURL(null);
	}

	/**
	 * See {@link #getHoverImageURL() getHoverImageURL()} for more details
	 */
	public java.lang.String getHoverImageURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.HOVER_IMAGE_URL);
	}

	/**
	 * Returns <code>true</code> if the attribute "hoverImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHoverImageURLSetted() {
		return getStateHelper().get(Properties.HOVER_IMAGE_URL)!=null;
	}

	public void setHoverImageURL(java.lang.String hoverImageURL) {
		getStateHelper().put(Properties.HOVER_IMAGE_URL, hoverImageURL);
	}

	public java.lang.String getSelectedImageURL() {
		return getSelectedImageURL(null);
	}

	/**
	 * See {@link #getSelectedImageURL() getSelectedImageURL()} for more details
	 */
	public java.lang.String getSelectedImageURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.SELECTED_IMAGE_URL);
	}

	/**
	 * Returns <code>true</code> if the attribute "selectedImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSelectedImageURLSetted() {
		return getStateHelper().get(Properties.SELECTED_IMAGE_URL)!=null;
	}

	public void setSelectedImageURL(java.lang.String selectedImageURL) {
		getStateHelper().put(Properties.SELECTED_IMAGE_URL, selectedImageURL);
	}

	public java.lang.String getImageURL() {
		return getImageURL(null);
	}

	/**
	 * See {@link #getImageURL() getImageURL()} for more details
	 */
	public java.lang.String getImageURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.IMAGE_URL);
	}

	/**
	 * Returns <code>true</code> if the attribute "imageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImageURLSetted() {
		return getStateHelper().get(Properties.IMAGE_URL)!=null;
	}

	public void setImageURL(java.lang.String imageURL) {
		getStateHelper().put(Properties.IMAGE_URL, imageURL);
	}

	public IContentAccessors getImageAccessors() {


			return getImageAccessors(null);
		
	}

	public java.lang.String getAccessKey() {
		return getAccessKey(null);
	}

	/**
	 * See {@link #getAccessKey() getAccessKey()} for more details
	 */
	public java.lang.String getAccessKey(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ACCESS_KEY);
	}

	/**
	 * Returns <code>true</code> if the attribute "accessKey" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAccessKeySetted() {
		return getStateHelper().get(Properties.ACCESS_KEY)!=null;
	}

	public void setAccessKey(java.lang.String accessKey) {
		getStateHelper().put(Properties.ACCESS_KEY, accessKey);
	}

	public IMenuComponent getMenu() {


		return MenuTools.getMenu(this);
		
	}

	public IMenuComponent getMenu(String menuId) {


		return MenuTools.getMenu(this, menuId);
		
	}

	public IMenuIterator listMenus() {


		return MenuTools.listMenus(this);
		
	}

}
