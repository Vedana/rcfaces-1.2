package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.familly.IContentAccessors;
import java.lang.String;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IShowDropDownMarkCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ImageAccessorTools;
import org.rcfaces.core.component.capability.IHorizontalTextPositionCapability;
import javax.faces.convert.Converter;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.component.AbstractMenuComponent;
import org.rcfaces.core.component.familly.IImageButtonFamilly;
import java.util.Collection;
import org.rcfaces.core.internal.converter.TextPositionConverter;

/**
 * <p>The imageCombo Component is a <a href="/comps/comboComponent.html">combo Component</a> with an image added to the text.</p>
 * <p>The imageCombo Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text, font &amp; image</li>
 * <li>Help</li>
 * <li>Visibility, Read-Only, Disabled</li>
 * <li>Events Handling</li>
 * </ul>
 * </p>
 */
public class ImageComboComponent extends AbstractMenuComponent implements 
	IImageButtonFamilly,
	IShowDropDownMarkCapability {

	private static final Log LOG = LogFactory.getLog(ImageComboComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.imageCombo";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractMenuComponent.BEHAVIOR_EVENT_NAMES;

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

	public void setTextPosition(String textPosition) {


			setTextPosition(((Integer)getTextPositionConverter().getAsObject(null, this, textPosition)).intValue());
		
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

	public boolean isBorder() {
		return isBorder(null);
	}

	/**
	 * See {@link #isBorder() isBorder()} for more details
	 */
	public boolean isBorder(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.BORDER, true);
	}

	/**
	 * Returns <code>true</code> if the attribute "border" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBorderSetted() {
		return getStateHelper().get(Properties.BORDER)!=null;
	}

	public void setBorder(boolean border) {
		getStateHelper().put(Properties.BORDER, border);
	}

	public java.lang.String getBorderType() {
		return getBorderType(null);
	}

	/**
	 * See {@link #getBorderType() getBorderType()} for more details
	 */
	public java.lang.String getBorderType(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.BORDER_TYPE);
	}

	/**
	 * Returns <code>true</code> if the attribute "borderType" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBorderTypeSetted() {
		return getStateHelper().get(Properties.BORDER_TYPE)!=null;
	}

	public void setBorderType(java.lang.String borderType) {
		getStateHelper().put(Properties.BORDER_TYPE, borderType);
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

	public int getTextPosition() {
		return getTextPosition(null);
	}

	/**
	 * See {@link #getTextPosition() getTextPosition()} for more details
	 */
	public int getTextPosition(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.TEXT_POSITION, IHorizontalTextPositionCapability.DEFAULT_POSITION);
	}

	/**
	 * Returns <code>true</code> if the attribute "textPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextPositionSetted() {
		return getStateHelper().get(Properties.TEXT_POSITION)!=null;
	}

	public void setTextPosition(int textPosition) {
		getStateHelper().put(Properties.TEXT_POSITION, textPosition);
	}

	public int getImageHeight() {
		return getImageHeight(null);
	}

	/**
	 * See {@link #getImageHeight() getImageHeight()} for more details
	 */
	public int getImageHeight(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.IMAGE_HEIGHT, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "imageHeight" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImageHeightSetted() {
		return getStateHelper().get(Properties.IMAGE_HEIGHT)!=null;
	}

	public void setImageHeight(int imageHeight) {
		getStateHelper().put(Properties.IMAGE_HEIGHT, imageHeight);
	}

	public int getImageWidth() {
		return getImageWidth(null);
	}

	/**
	 * See {@link #getImageWidth() getImageWidth()} for more details
	 */
	public int getImageWidth(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.IMAGE_WIDTH, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "imageWidth" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImageWidthSetted() {
		return getStateHelper().get(Properties.IMAGE_WIDTH)!=null;
	}

	public void setImageWidth(int imageWidth) {
		getStateHelper().put(Properties.IMAGE_WIDTH, imageWidth);
	}

	public IContentAccessors getImageAccessors(FacesContext facesContext) {


			return ImageAccessorTools.createImageAccessors(facesContext, this, getComponentEngine());
		
	}

	public IContentAccessors getImageAccessors() {


			return getImageAccessors(null);
		
	}

	public boolean isShowDropDownMark() {
		return isShowDropDownMark(null);
	}

	/**
	 * See {@link #isShowDropDownMark() isShowDropDownMark()} for more details
	 */
	public boolean isShowDropDownMark(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.SHOW_DROP_DOWN_MARK, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "showDropDownMark" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isShowDropDownMarkSetted() {
		return getStateHelper().get(Properties.SHOW_DROP_DOWN_MARK)!=null;
	}

	public void setShowDropDownMark(boolean showDropDownMark) {
		getStateHelper().put(Properties.SHOW_DROP_DOWN_MARK, showDropDownMark);
	}

	/**
	 * Experimental : do not use!
	 */
	public int getPopupRowNumber() {
		return getPopupRowNumber(null);
	}

	/**
	 * Experimental : do not use!
	 */
	public int getPopupRowNumber(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.POPUP_ROW_NUMBER, 0);
	}

	/**
	 * Experimental : do not use!
	 */
	public void setPopupRowNumber(int popupRowNumber) {
		 getStateHelper().put(Properties.POPUP_ROW_NUMBER, popupRowNumber);
	}

	/**
	 * Experimental : do not use!
	 */
	/**
	 * Returns <code>true</code> if the attribute "popupRowNumber" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPopupRowNumberSetted() {
		return getStateHelper().get(Properties.POPUP_ROW_NUMBER)!=null;
	}

}