package org.rcfaces.core.component;

import java.util.TimeZone;
import org.rcfaces.core.internal.converter.LiteralDateConverter;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.lang.IAdaptable;
import org.rcfaces.core.component.capability.IMenuPopupIdCapability;
import java.lang.String;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.ILiteralLocaleCapability;
import java.util.Date;
import org.rcfaces.core.component.SelectItemComponent;
import org.rcfaces.core.internal.converter.TimeZoneConverter;
import javax.faces.FacesException;
import javax.el.ValueExpression;
import org.rcfaces.core.internal.converter.LocaleConverter;
import org.rcfaces.core.component.capability.ILiteralTimeZoneCapability;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import java.util.Locale;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import java.util.Set;
import java.util.Arrays;
import java.util.Collection;
import org.rcfaces.core.component.capability.ITextCapability;

/**
 * An item specialized for date values.
 */
public class DateItemComponent extends SelectItemComponent implements 
	ITextCapability,
	IStyleClassCapability,
	IMenuPopupIdCapability,
	ILiteralLocaleCapability,
	ILiteralTimeZoneCapability {

	private static final Log LOG = LogFactory.getLog(DateItemComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.dateItem";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=SelectItemComponent.BEHAVIOR_EVENT_NAMES;

	public DateItemComponent() {
		setRendererType(null);
	}

	public DateItemComponent(String componentId) {
		this();
		setId(componentId);
	}

	public Date getDate() {


				Object value=getItemValue();

				if (value==null) {
					return null;
				}			

				if (value instanceof Date) {
					return (Date)value;
				}
								
				if (value instanceof String) {
					return (Date)LiteralDateConverter.SINGLETON.getAsObject(null, this, (String)value);
				}				

				if (value instanceof IAdaptable) {
					Date adapted=(Date)((IAdaptable)value).getAdapter(Date.class, this);
					if (adapted!=null) {
						return adapted;
					}
				}

				throw new FacesException("ItemValue of DateItem is not a date ! ("+value+")");
			
	}

	public void setDate(Date date) {


				setItemValue(date);
			
	}

	public void setDate(String date) {


				setItemValue(date);
			
	}

	public void setText(String text) {


			setItemLabel(text);
			
	}

	public String getText() {


			return getItemLabel();
			
	}

	public void setLiteralLocale(String locale) {


		setLiteralLocale((Locale)LocaleConverter.SINGLETON.getAsObject(null, this, locale));
		
	}

	public void setLiteralTimeZone(String timeZone) {


		setLiteralTimeZone((TimeZone)TimeZoneConverter.SINGLETON.getAsObject(null, this, timeZone));
		
	}

	/**
	 * Returns <code>true</code> if the attribute "text" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextSetted() {
		return getStateHelper().get(Properties.TEXT)!=null;
	}

	public java.lang.String getStyleClass() {
		return getStyleClass(null);
	}

	/**
	 * See {@link #getStyleClass() getStyleClass()} for more details
	 */
	public java.lang.String getStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "styleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isStyleClassSetted() {
		return getStateHelper().get(Properties.STYLE_CLASS)!=null;
	}

	public void setStyleClass(java.lang.String styleClass) {
		getStateHelper().put(Properties.STYLE_CLASS, styleClass);
	}

	public java.lang.String getMenuPopupId() {
		return getMenuPopupId(null);
	}

	/**
	 * See {@link #getMenuPopupId() getMenuPopupId()} for more details
	 */
	public java.lang.String getMenuPopupId(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.MENU_POPUP_ID);
	}

	/**
	 * Returns <code>true</code> if the attribute "menuPopupId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMenuPopupIdSetted() {
		return getStateHelper().get(Properties.MENU_POPUP_ID)!=null;
	}

	public void setMenuPopupId(java.lang.String menuPopupId) {
		getStateHelper().put(Properties.MENU_POPUP_ID, menuPopupId);
	}

	public java.util.Locale getLiteralLocale() {
		return getLiteralLocale(null);
	}

	/**
	 * See {@link #getLiteralLocale() getLiteralLocale()} for more details
	 */
	public java.util.Locale getLiteralLocale(javax.faces.context.FacesContext facesContext) {
		return (java.util.Locale)getStateHelper().eval(Properties.LITERAL_LOCALE);
	}

	/**
	 * Returns <code>true</code> if the attribute "literalLocale" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isLiteralLocaleSetted() {
		return getStateHelper().get(Properties.LITERAL_LOCALE)!=null;
	}

	public void setLiteralLocale(java.util.Locale literalLocale) {
		getStateHelper().put(Properties.LITERAL_LOCALE, literalLocale);
	}

	public java.util.TimeZone getLiteralTimeZone() {
		return getLiteralTimeZone(null);
	}

	/**
	 * See {@link #getLiteralTimeZone() getLiteralTimeZone()} for more details
	 */
	public java.util.TimeZone getLiteralTimeZone(javax.faces.context.FacesContext facesContext) {
		return (java.util.TimeZone)getStateHelper().eval(Properties.LITERAL_TIME_ZONE);
	}

	/**
	 * Returns <code>true</code> if the attribute "literalTimeZone" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isLiteralTimeZoneSetted() {
		return getStateHelper().get(Properties.LITERAL_TIME_ZONE)!=null;
	}

	public void setLiteralTimeZone(java.util.TimeZone literalTimeZone) {
		getStateHelper().put(Properties.LITERAL_TIME_ZONE, literalTimeZone);
	}


	public void setValueExpression(String name, ValueExpression binding) {
		if (Properties.TEXT.toString().equals(name)) {
			name=Properties.ITEM_LABEL.toString();

		} else if (Properties.DATE.toString().equals(name)) {
			name=Properties.ITEM_VALUE.toString();
		}
		super.setValueExpression(name, binding);
	}
}
