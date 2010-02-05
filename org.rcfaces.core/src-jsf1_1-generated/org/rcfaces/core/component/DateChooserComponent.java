package org.rcfaces.core.component;

import java.lang.String;
import org.rcfaces.core.component.capability.IValueChangeEventCapability;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.convert.Converter;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.converter.CalendarLayoutConverter;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.tools.ImageAccessorTools;
import java.util.Date;
import java.util.Arrays;
import org.rcfaces.core.component.capability.ICalendarLayoutCapability;
import java.util.Set;
import org.rcfaces.core.component.capability.IHorizontalTextPositionCapability;
import java.util.HashSet;
import org.rcfaces.core.component.AbstractCalendarComponent;
import org.rcfaces.core.internal.converter.TextPositionConverter;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.familly.IImageButtonFamilly;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.internal.converter.LiteralDateConverter;
import org.rcfaces.core.component.capability.IForCapability;

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
	IForCapability,
	ICalendarLayoutCapability {

	private static final Log LOG = LogFactory.getLog(DateChooserComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.dateChooser";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractCalendarComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"selectionListener","hoverImageURL","imageHeight","imageURL","disabledImageURL","disabled","calendarLayout","valueChangeListener","homeDate","homeDateLabel","text","imageWidth","selectedImageURL","border","forValueFormat","borderType","readOnly","popupStyleClass","textPosition","for"}));
	}

	public DateChooserComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public DateChooserComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void setHomeDate(String date) {


			engine.setProperty(Properties.HOME_DATE, date);
		
	}

	public Date getHomeDate(FacesContext facesContext) {


			Object value=engine.getProperty(Properties.HOME_DATE, facesContext);
			if (value instanceof String) {
				value=LiteralDateConverter.SINGLETON.getAsObject(facesContext, this, (String)value);
			}
			
			return (Date)value;
		
	}

	protected Converter getTextPositionConverter() {


				return TextPositionConverter.SINGLETON;
			
	}

	public void setTextPosition(String textPosition) {


			setTextPosition(((Integer)getTextPositionConverter().getAsObject(null, this, textPosition)).intValue());
		
	}

	public void setCalendarLayout(String layout) {


			setCalendarLayout(((Integer)CalendarLayoutConverter.SINGLETON.getAsObject(null, this, layout)).intValue());
		
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

	public IContentAccessors getImageAccessors() {


			return getImageAccessors(null);
		
	}

	public IContentAccessors getImageAccessors(FacesContext facesContext) {


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

	public java.lang.String getFor() {
		return getFor(null);
	}

	/**
	 * See {@link #getFor() getFor()} for more details
	 */
	public java.lang.String getFor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOR, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "for" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isForSetted() {
		return engine.isPropertySetted(Properties.FOR);
	}

	public void setFor(java.lang.String forValue) {
		engine.setProperty(Properties.FOR, forValue);
	}

	public int getCalendarLayout() {
		return getCalendarLayout(null);
	}

	/**
	 * See {@link #getCalendarLayout() getCalendarLayout()} for more details
	 */
	public int getCalendarLayout(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.CALENDAR_LAYOUT,ICalendarLayoutCapability.DEFAULT_LAYOUT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "calendarLayout" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCalendarLayoutSetted() {
		return engine.isPropertySetted(Properties.CALENDAR_LAYOUT);
	}

	public void setCalendarLayout(int calendarLayout) {
		engine.setProperty(Properties.CALENDAR_LAYOUT, calendarLayout);
	}

	/**
	 * Returns a string specifying the format to use for the value that will be send to the component specified in the <b>for</b> property.
	 * @return value format
	 */
	public String getForValueFormat() {
		return getForValueFormat(null);
	}

	/**
	 * Returns a string specifying the format to use for the value that will be send to the component specified in the <b>for</b> property.
	 * @return value format
	 */
	public String getForValueFormat(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOR_VALUE_FORMAT, facesContext);
	}

	/**
	 * Sets a string specifying the format to use for the value that will be send to the component specified in the <b>for</b> property.
	 * @param forValueFormat value format
	 */
	public void setForValueFormat(String forValueFormat) {
		engine.setProperty(Properties.FOR_VALUE_FORMAT, forValueFormat);
	}

	/**
	 * Sets a string specifying the format to use for the value that will be send to the component specified in the <b>for</b> property.
	 * @param forValueFormat value format
	 */
	/**
	 * Returns <code>true</code> if the attribute "forValueFormat" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isForValueFormatSetted() {
		return engine.isPropertySetted(Properties.FOR_VALUE_FORMAT);
	}

	public java.util.Date getHomeDate() {
		return getHomeDate(null);
	}

	public void setHomeDate(java.util.Date homeDate) {
		engine.setProperty(Properties.HOME_DATE, homeDate);
	}

	/**
	 * Returns <code>true</code> if the attribute "homeDate" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isHomeDateSetted() {
		return engine.isPropertySetted(Properties.HOME_DATE);
	}

	/**
	 * Returns a string specifying the label associated with the home button.
	 * @return the label used
	 */
	public String getHomeDateLabel() {
		return getHomeDateLabel(null);
	}

	/**
	 * Returns a string specifying the label associated with the home button.
	 * @return the label used
	 */
	public String getHomeDateLabel(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.HOME_DATE_LABEL, facesContext);
	}

	/**
	 * Sets a string specifying the label associated with the home button.
	 * @param homeDateLabel the label to use
	 */
	public void setHomeDateLabel(String homeDateLabel) {
		engine.setProperty(Properties.HOME_DATE_LABEL, homeDateLabel);
	}

	/**
	 * Sets a string specifying the label associated with the home button.
	 * @param homeDateLabel the label to use
	 */
	/**
	 * Returns <code>true</code> if the attribute "homeDateLabel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isHomeDateLabelSetted() {
		return engine.isPropertySetted(Properties.HOME_DATE_LABEL);
	}

	public String getPopupStyleClass() {
		return getPopupStyleClass(null);
	}

	public String getPopupStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.POPUP_STYLE_CLASS, facesContext);
	}

	public void setPopupStyleClass(String popupStyleClass) {
		engine.setProperty(Properties.POPUP_STYLE_CLASS, popupStyleClass);
	}

	/**
	 * Returns <code>true</code> if the attribute "popupStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPopupStyleClassSetted() {
		return engine.isPropertySetted(Properties.POPUP_STYLE_CLASS);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
