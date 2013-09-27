package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.TextPositionConverter
			;
import org.rcfaces.core.component.familly.IContentAccessors;
import java.lang.String;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IShowDropDownMarkCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ImageAccessorTools;
import org.rcfaces.core.component.capability.IAudioDescriptionCapability;
import org.rcfaces.core.component.capability.IHorizontalTextPositionCapability;
import javax.faces.convert.Converter;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.component.AbstractMenuComponent;
import org.rcfaces.core.component.familly.IImageButtonFamilly;

/**
 * <p>The imageCombo Component is a <a href="/comps/comboComponent.html">combo Component</a> with an image added to the text.</p>
 * <p>The imageCombo Component has the following capabilities :
 * <ul>
 * <li>IImageButtonFamilly</li>
 * <li>IShowDropDownMarkCapability</li>
 * </ul>
 * </p>
 * 
 * 
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/ImageComboComponent.html">imageCombo</a> renderer is linked to the <a href="/jsdocs/index.html?f_imageCombo.html" target="_blank">f_imageCombo</a> javascript class. f_imageCombo extends f_imageButton, fa_subMenu, fa_itemsWrapper</p>
 * 
 * <p> Table of component style classes: </p>
 * <table border="1" cellpadding="3" cellspacing="0" width="100%">
 * <tbody>
 * 
 * <tr style="text-align:left">
 * <th  width="33%">Style Name</th>
 * <th width="50%">Description</th>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td width="33%">f_imageCombo</td>
 * <td width="50%">Defines styles for the wrapper DIV element</td>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td width="33%">f_imageCheckButton_ctext</td>
 * <td width="50%">Defines styles for the  Text element of the combo</td>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td width="33%">f_imageCheckButton_cimage</td>
 * <td width="50%">Defines styles for the IMG element of the combo</td>
 * </tr>
 * <tr  style="text-align:left">
 * <td width="33%">f_menu_popup</td>
 * <td width="50%">Defines styles for the pop-up element of the combo</td>
 * </tr>
 * 
 * </tbody>
 * </table>
 */
public class ImageComboComponent extends AbstractMenuComponent implements 
	IImageButtonFamilly,
	IShowDropDownMarkCapability,
	IAudioDescriptionCapability {

	private static final Log LOG = LogFactory.getLog(ImageComboComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.imageCombo";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractMenuComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"disabledImageURL","imageHeight","text","popupRowNumber","showDropDownMark","borderType","textPosition","hoverImageURL","tabIndex","audioDescription","selectedImageURL","selectionListener","readOnly","border","imageURL","disabled","imageWidth"}));
	}

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

	public boolean isBorder() {
		return isBorder(null);
	}

	/**
	 * See {@link #isBorder() isBorder()} for more details
	 */
	public boolean isBorder(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.BORDER, true, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "border" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBorderSetted() {
		return engine.isPropertySetted(Properties.BORDER);
	}

	public void setBorder(boolean border) {
		engine.setProperty(Properties.BORDER, border);
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

	public int getTextPosition() {
		return getTextPosition(null);
	}

	/**
	 * See {@link #getTextPosition() getTextPosition()} for more details
	 */
	public int getTextPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.TEXT_POSITION,IHorizontalTextPositionCapability.DEFAULT_POSITION, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "textPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextPositionSetted() {
		return engine.isPropertySetted(Properties.TEXT_POSITION);
	}

	public void setTextPosition(int textPosition) {
		engine.setProperty(Properties.TEXT_POSITION, textPosition);
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

	public IContentAccessors getImageAccessors(FacesContext facesContext) {


			return ImageAccessorTools.createImageAccessors(facesContext, this, engine);
		
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
		return engine.getBoolProperty(Properties.SHOW_DROP_DOWN_MARK, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "showDropDownMark" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isShowDropDownMarkSetted() {
		return engine.isPropertySetted(Properties.SHOW_DROP_DOWN_MARK);
	}

	public void setShowDropDownMark(boolean showDropDownMark) {
		engine.setProperty(Properties.SHOW_DROP_DOWN_MARK, showDropDownMark);
	}

	public java.lang.String getAudioDescription() {
		return getAudioDescription(null);
	}

	/**
	 * See {@link #getAudioDescription() getAudioDescription()} for more details
	 */
	public java.lang.String getAudioDescription(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.AUDIO_DESCRIPTION, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "audioDescription" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAudioDescriptionSetted() {
		return engine.isPropertySetted(Properties.AUDIO_DESCRIPTION);
	}

	public void setAudioDescription(java.lang.String audioDescription) {
		engine.setProperty(Properties.AUDIO_DESCRIPTION, audioDescription);
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
		return engine.getIntProperty(Properties.POPUP_ROW_NUMBER, 0, facesContext);
	}

	/**
	 * Experimental : do not use!
	 */
	public void setPopupRowNumber(int popupRowNumber) {
		engine.setProperty(Properties.POPUP_ROW_NUMBER, popupRowNumber);
	}

	/**
	 * Experimental : do not use!
	 */
	/**
	 * Returns <code>true</code> if the attribute "popupRowNumber" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPopupRowNumberSetted() {
		return engine.isPropertySetted(Properties.POPUP_ROW_NUMBER);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
