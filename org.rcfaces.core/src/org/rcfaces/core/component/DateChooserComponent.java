package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IForCapability;
import org.rcfaces.core.component.capability.IValueChangeEventCapability;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.component.familly.IImageButtonFamilly;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.DateConverter;
import org.rcfaces.core.internal.converter.TextPositionConverter;
import org.rcfaces.core.internal.tools.ImageAccessorTools;

/**
 * <p>The dateChooser Component is a button that shows a calendar and help the user to choose a date. It can be associated to a entry field, the choosen date is then automatically entered in the field. It works like an <a href="/comps/imageButtonComponent.html">Image Button Component</a>. The dateChooser Component does <b>not</b> provide an Entry field.</p>
 * <p>The dateChooser Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text, font &amp; image</li>
 * <li>Margin &amp; border</li>
 * <li>Help</li>
 * <li>Visibility, Read-Only, Disabled</li>
 * <li>Events Handling</li>
 * <li>Association with another component</li>
 * </ul>
 * </p>
 */
public class DateChooserComponent extends AbstractCalendarComponent implements 
	IImageButtonFamilly,
	IValueChangeEventCapability,
	IForCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.dateChooser";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractCalendarComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"selectionListener","hoverImageURL","imageHeight","imageURL","disabledImageURL","disabled","valueChangeListener","homeDate","homeDateLabel","text","imageWidth","selectedImageURL","border","forValueFormat","borderType","readOnly","textPosition","for"}));
	}

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

	/**
	 * See {@link #getImageURL() getImageURL()} for more details
	 */
	public final java.lang.String getImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.IMAGE_URL, facesContext);
	}

	public final void setImageURL(java.lang.String imageURL) {
		engine.setProperty(Properties.IMAGE_URL, imageURL);
	}

	/**
	 * See {@link #setImageURL(String) setImageURL(String)} for more details
	 */
	public final void setImageURL(ValueBinding imageURL) {
		engine.setProperty(Properties.IMAGE_URL, imageURL);
	}

	public final java.lang.String getDisabledImageURL() {
		return getDisabledImageURL(null);
	}

	/**
	 * See {@link #getDisabledImageURL() getDisabledImageURL()} for more details
	 */
	public final java.lang.String getDisabledImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DISABLED_IMAGE_URL, facesContext);
	}

	public final void setDisabledImageURL(java.lang.String disabledImageURL) {
		engine.setProperty(Properties.DISABLED_IMAGE_URL, disabledImageURL);
	}

	/**
	 * See {@link #setDisabledImageURL(String) setDisabledImageURL(String)} for more details
	 */
	public final void setDisabledImageURL(ValueBinding disabledImageURL) {
		engine.setProperty(Properties.DISABLED_IMAGE_URL, disabledImageURL);
	}

	public final java.lang.String getHoverImageURL() {
		return getHoverImageURL(null);
	}

	/**
	 * See {@link #getHoverImageURL() getHoverImageURL()} for more details
	 */
	public final java.lang.String getHoverImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.HOVER_IMAGE_URL, facesContext);
	}

	public final void setHoverImageURL(java.lang.String hoverImageURL) {
		engine.setProperty(Properties.HOVER_IMAGE_URL, hoverImageURL);
	}

	/**
	 * See {@link #setHoverImageURL(String) setHoverImageURL(String)} for more details
	 */
	public final void setHoverImageURL(ValueBinding hoverImageURL) {
		engine.setProperty(Properties.HOVER_IMAGE_URL, hoverImageURL);
	}

	public final java.lang.String getSelectedImageURL() {
		return getSelectedImageURL(null);
	}

	/**
	 * See {@link #getSelectedImageURL() getSelectedImageURL()} for more details
	 */
	public final java.lang.String getSelectedImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SELECTED_IMAGE_URL, facesContext);
	}

	public final void setSelectedImageURL(java.lang.String selectedImageURL) {
		engine.setProperty(Properties.SELECTED_IMAGE_URL, selectedImageURL);
	}

	/**
	 * See {@link #setSelectedImageURL(String) setSelectedImageURL(String)} for more details
	 */
	public final void setSelectedImageURL(ValueBinding selectedImageURL) {
		engine.setProperty(Properties.SELECTED_IMAGE_URL, selectedImageURL);
	}

	public final boolean isBorder() {
		return isBorder(null);
	}

	/**
	 * See {@link #isBorder() isBorder()} for more details
	 */
	public final boolean isBorder(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.BORDER, true, facesContext);
	}

	public final void setBorder(boolean border) {
		engine.setProperty(Properties.BORDER, border);
	}

	/**
	 * See {@link #setBorder(boolean) setBorder(boolean)} for more details
	 */
	public final void setBorder(ValueBinding border) {
		engine.setProperty(Properties.BORDER, border);
	}

	public final java.lang.String getBorderType() {
		return getBorderType(null);
	}

	/**
	 * See {@link #getBorderType() getBorderType()} for more details
	 */
	public final java.lang.String getBorderType(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BORDER_TYPE, facesContext);
	}

	public final void setBorderType(java.lang.String borderType) {
		engine.setProperty(Properties.BORDER_TYPE, borderType);
	}

	/**
	 * See {@link #setBorderType(String) setBorderType(String)} for more details
	 */
	public final void setBorderType(ValueBinding borderType) {
		engine.setProperty(Properties.BORDER_TYPE, borderType);
	}

	public final java.lang.String getText() {
		return getText(null);
	}

	/**
	 * See {@link #getText() getText()} for more details
	 */
	public final java.lang.String getText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TEXT, facesContext);
	}

	public final void setText(java.lang.String text) {
		engine.setProperty(Properties.TEXT, text);
	}

	/**
	 * See {@link #setText(String) setText(String)} for more details
	 */
	public final void setText(ValueBinding text) {
		engine.setProperty(Properties.TEXT, text);
	}

	public final int getTextPosition() {
		return getTextPosition(null);
	}

	/**
	 * See {@link #getTextPosition() getTextPosition()} for more details
	 */
	public final int getTextPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.TEXT_POSITION,0, facesContext);
	}

	public final void setTextPosition(int textPosition) {
		engine.setProperty(Properties.TEXT_POSITION, textPosition);
	}

	/**
	 * See {@link #setTextPosition(int) setTextPosition(int)} for more details
	 */
	public final void setTextPosition(ValueBinding textPosition) {
		engine.setProperty(Properties.TEXT_POSITION, textPosition);
	}

	public final int getImageHeight() {
		return getImageHeight(null);
	}

	/**
	 * See {@link #getImageHeight() getImageHeight()} for more details
	 */
	public final int getImageHeight(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.IMAGE_HEIGHT,0, facesContext);
	}

	public final void setImageHeight(int imageHeight) {
		engine.setProperty(Properties.IMAGE_HEIGHT, imageHeight);
	}

	/**
	 * See {@link #setImageHeight(int) setImageHeight(int)} for more details
	 */
	public final void setImageHeight(ValueBinding imageHeight) {
		engine.setProperty(Properties.IMAGE_HEIGHT, imageHeight);
	}

	public final int getImageWidth() {
		return getImageWidth(null);
	}

	/**
	 * See {@link #getImageWidth() getImageWidth()} for more details
	 */
	public final int getImageWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.IMAGE_WIDTH,0, facesContext);
	}

	public final void setImageWidth(int imageWidth) {
		engine.setProperty(Properties.IMAGE_WIDTH, imageWidth);
	}

	/**
	 * See {@link #setImageWidth(int) setImageWidth(int)} for more details
	 */
	public final void setImageWidth(ValueBinding imageWidth) {
		engine.setProperty(Properties.IMAGE_WIDTH, imageWidth);
	}

	public final IContentAccessors getImageAccessors() {


			return getImageAccessors(null);
		
	}

	public final IContentAccessors getImageAccessors(FacesContext facesContext) {


			return ImageAccessorTools.createImageAccessors(facesContext, this, engine);
		
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

	public final java.lang.String getFor() {
		return getFor(null);
	}

	/**
	 * See {@link #getFor() getFor()} for more details
	 */
	public final java.lang.String getFor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOR, facesContext);
	}

	public final void setFor(java.lang.String forValue) {
		engine.setProperty(Properties.FOR, forValue);
	}

	/**
	 * See {@link #setFor(String) setFor(String)} for more details
	 */
	public final void setFor(ValueBinding forValue) {
		engine.setProperty(Properties.FOR, forValue);
	}

	/**
	 * Returns a string specifying the format to use for the value that will be send to the component specified in the <b>for</b> property.
	 * @return value format
	 */
	public final String getForValueFormat() {
		return getForValueFormat(null);
	}

	/**
	 * Returns a string specifying the format to use for the value that will be send to the component specified in the <b>for</b> property.
	 * @return value format
	 */
	public final String getForValueFormat(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOR_VALUE_FORMAT, facesContext);
	}

	/**
	 * Sets a string specifying the format to use for the value that will be send to the component specified in the <b>for</b> property.
	 * @param forValueFormat value format
	 */
	public final void setForValueFormat(String forValueFormat) {
		engine.setProperty(Properties.FOR_VALUE_FORMAT, forValueFormat);
	}

	/**
	 * Sets a string specifying the format to use for the value that will be send to the component specified in the <b>for</b> property.
	 * @param forValueFormat value format
	 */
	public final void setForValueFormat(ValueBinding forValueFormat) {
		engine.setProperty(Properties.FOR_VALUE_FORMAT, forValueFormat);
	}

	/**
	 * Returns <code>true</code> if the attribute "forValueFormat" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
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

	/**
	 * Returns <code>true</code> if the attribute "homeDate" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHomeDateSetted() {
		return engine.isPropertySetted(Properties.HOME_DATE);
	}

	/**
	 * Returns a string specifying the label associated with the home button.
	 * @return the label used
	 */
	public final String getHomeDateLabel() {
		return getHomeDateLabel(null);
	}

	/**
	 * Returns a string specifying the label associated with the home button.
	 * @return the label used
	 */
	public final String getHomeDateLabel(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.HOME_DATE_LABEL, facesContext);
	}

	/**
	 * Sets a string specifying the label associated with the home button.
	 * @param homeDateLabel the label to use
	 */
	public final void setHomeDateLabel(String homeDateLabel) {
		engine.setProperty(Properties.HOME_DATE_LABEL, homeDateLabel);
	}

	/**
	 * Sets a string specifying the label associated with the home button.
	 * @param homeDateLabel the label to use
	 */
	public final void setHomeDateLabel(ValueBinding homeDateLabel) {
		engine.setProperty(Properties.HOME_DATE_LABEL, homeDateLabel);
	}

	/**
	 * Returns <code>true</code> if the attribute "homeDateLabel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHomeDateLabelSetted() {
		return engine.isPropertySetted(Properties.HOME_DATE_LABEL);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
