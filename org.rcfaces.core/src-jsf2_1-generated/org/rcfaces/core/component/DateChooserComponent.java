package org.rcfaces.core.component;

import org.rcfaces.core.internal.converter.LiteralDateConverter;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.CalendarLayoutConverter;
import org.rcfaces.core.component.familly.IContentAccessors;
import java.lang.String;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.ICalendarLayoutCapability;
import java.util.Date;
import org.rcfaces.core.component.capability.IForCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ImageAccessorTools;
import org.rcfaces.core.component.capability.IHorizontalTextPositionCapability;
import javax.faces.convert.Converter;
import org.rcfaces.core.component.AbstractCalendarComponent;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.component.capability.IValueChangeEventCapability;
import org.rcfaces.core.component.familly.IImageButtonFamilly;
import java.util.Collection;
import org.rcfaces.core.internal.converter.TextPositionConverter;

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

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractCalendarComponent.BEHAVIOR_EVENT_NAMES;

	public DateChooserComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public DateChooserComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void setHomeDate(String date) {


			getStateHelper().put(Properties.HOME_DATE, date);
		
	}

	public Date getHomeDate(FacesContext facesContext) {


			Object value=getStateHelper().eval(Properties.HOME_DATE, facesContext);
			if (value instanceof String) {
				value=LiteralDateConverter.SINGLETON.getAsObject(facesContext, this, (String)value);
			}
			
			return (Date)value;
		
	}

	public void setDefaultSelectedDate(String date) {


			getStateHelper().put(Properties.DEFAULT_SELECTED_DATE, date);
		
	}

	public Date getDefaultSelectedDate(FacesContext facesContext) {


			Object value=getStateHelper().eval(Properties.DEFAULT_SELECTED_DATE, facesContext);
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
		return (String)getStateHelper().eval(Properties.FOR);
	}

	/**
	 * Returns <code>true</code> if the attribute "for" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isForSetted() {
		return getStateHelper().get(Properties.FOR)!=null;
	}

	public void setFor(java.lang.String forValue) {
		getStateHelper().put(Properties.FOR, forValue);
	}

	public int getCalendarLayout() {
		return getCalendarLayout(null);
	}

	/**
	 * See {@link #getCalendarLayout() getCalendarLayout()} for more details
	 */
	public int getCalendarLayout(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.CALENDAR_LAYOUT, ICalendarLayoutCapability.DEFAULT_LAYOUT);
	}

	/**
	 * Returns <code>true</code> if the attribute "calendarLayout" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCalendarLayoutSetted() {
		return getStateHelper().get(Properties.CALENDAR_LAYOUT)!=null;
	}

	public void setCalendarLayout(int calendarLayout) {
		getStateHelper().put(Properties.CALENDAR_LAYOUT, calendarLayout);
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
		return (String)getStateHelper().eval(Properties.FOR_VALUE_FORMAT);
	}

	/**
	 * Sets a string specifying the format to use for the value that will be send to the component specified in the <b>for</b> property.
	 * @param forValueFormat value format
	 */
	public void setForValueFormat(String forValueFormat) {
		 getStateHelper().put(Properties.FOR_VALUE_FORMAT, forValueFormat);
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
		return getStateHelper().get(Properties.FOR_VALUE_FORMAT)!=null;
	}

	public java.util.Date getHomeDate() {
		return getHomeDate(null);
	}

	public void setHomeDate(java.util.Date homeDate) {
		 getStateHelper().put(Properties.HOME_DATE, homeDate);
	}

	/**
	 * Returns <code>true</code> if the attribute "homeDate" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isHomeDateSetted() {
		return getStateHelper().get(Properties.HOME_DATE)!=null;
	}

	public java.util.Date getDefaultSelectedDate() {
		return getDefaultSelectedDate(null);
	}

	public void setDefaultSelectedDate(java.util.Date defaultSelectedDate) {
		 getStateHelper().put(Properties.DEFAULT_SELECTED_DATE, defaultSelectedDate);
	}

	/**
	 * Returns <code>true</code> if the attribute "defaultSelectedDate" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDefaultSelectedDateSetted() {
		return getStateHelper().get(Properties.DEFAULT_SELECTED_DATE)!=null;
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
		return (String)getStateHelper().eval(Properties.HOME_DATE_LABEL);
	}

	/**
	 * Sets a string specifying the label associated with the home button.
	 * @param homeDateLabel the label to use
	 */
	public void setHomeDateLabel(String homeDateLabel) {
		 getStateHelper().put(Properties.HOME_DATE_LABEL, homeDateLabel);
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
		return getStateHelper().get(Properties.HOME_DATE_LABEL)!=null;
	}

	public String getPopupStyleClass() {
		return getPopupStyleClass(null);
	}

	public String getPopupStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.POPUP_STYLE_CLASS);
	}

	public void setPopupStyleClass(String popupStyleClass) {
		 getStateHelper().put(Properties.POPUP_STYLE_CLASS, popupStyleClass);
	}

	/**
	 * Returns <code>true</code> if the attribute "popupStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPopupStyleClassSetted() {
		return getStateHelper().get(Properties.POPUP_STYLE_CLASS)!=null;
	}

}