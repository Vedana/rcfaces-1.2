package org.rcfaces.core.component;

import java.lang.String;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.ILiteralTimeZoneCapability;
import org.rcfaces.core.component.SelectItemComponent;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import javax.faces.el.ValueBinding;
import javax.faces.FacesException;
import java.util.TimeZone;
import java.util.Date;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.Locale;
import org.rcfaces.core.component.capability.ILiteralLocaleCapability;
import org.rcfaces.core.internal.converter.LocaleConverter;
import org.rcfaces.core.component.capability.IMenuPopupIdCapability;
import org.rcfaces.core.internal.converter.TimeZoneConverter;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.internal.converter.LiteralDateConverter;
import org.rcfaces.core.lang.IAdaptable;

/**
 * An item specialized for date values.
 */
public class DateItemComponent extends SelectItemComponent implements 
	ITextCapability,
	IStyleClassCapability,
	IMenuPopupIdCapability,
	ILiteralLocaleCapability,
	ILiteralTimeZoneCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.dateItem";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(SelectItemComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"menuPopupId","literalTimeZone","styleClass","literalLocale","text","date"}));
	}

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
		return engine.isPropertySetted(Properties.TEXT);
	}

	public java.lang.String getStyleClass() {
		return getStyleClass(null);
	}

	/**
	 * See {@link #getStyleClass() getStyleClass()} for more details
	 */
	public java.lang.String getStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.STYLE_CLASS, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "styleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isStyleClassSetted() {
		return engine.isPropertySetted(Properties.STYLE_CLASS);
	}

	public void setStyleClass(java.lang.String styleClass) {
		engine.setProperty(Properties.STYLE_CLASS, styleClass);
	}

	public java.lang.String getMenuPopupId() {
		return getMenuPopupId(null);
	}

	/**
	 * See {@link #getMenuPopupId() getMenuPopupId()} for more details
	 */
	public java.lang.String getMenuPopupId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MENU_POPUP_ID, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "menuPopupId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMenuPopupIdSetted() {
		return engine.isPropertySetted(Properties.MENU_POPUP_ID);
	}

	public void setMenuPopupId(java.lang.String menuPopupId) {
		engine.setProperty(Properties.MENU_POPUP_ID, menuPopupId);
	}

	public java.util.Locale getLiteralLocale() {
		return getLiteralLocale(null);
	}

	/**
	 * See {@link #getLiteralLocale() getLiteralLocale()} for more details
	 */
	public java.util.Locale getLiteralLocale(javax.faces.context.FacesContext facesContext) {
		return (java.util.Locale)engine.getProperty(Properties.LITERAL_LOCALE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "literalLocale" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isLiteralLocaleSetted() {
		return engine.isPropertySetted(Properties.LITERAL_LOCALE);
	}

	public void setLiteralLocale(java.util.Locale literalLocale) {
		engine.setProperty(Properties.LITERAL_LOCALE, literalLocale);
	}

	public java.util.TimeZone getLiteralTimeZone() {
		return getLiteralTimeZone(null);
	}

	/**
	 * See {@link #getLiteralTimeZone() getLiteralTimeZone()} for more details
	 */
	public java.util.TimeZone getLiteralTimeZone(javax.faces.context.FacesContext facesContext) {
		return (java.util.TimeZone)engine.getProperty(Properties.LITERAL_TIME_ZONE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "literalTimeZone" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isLiteralTimeZoneSetted() {
		return engine.isPropertySetted(Properties.LITERAL_TIME_ZONE);
	}

	public void setLiteralTimeZone(java.util.TimeZone literalTimeZone) {
		engine.setProperty(Properties.LITERAL_TIME_ZONE, literalTimeZone);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}

	public void setValueBinding(String name, ValueBinding binding) {
		if (Properties.TEXT.equals(name)) {
			name=Properties.ITEM_LABEL;

		} else if (Properties.DATE.equals(name)) {
			name=Properties.ITEM_VALUE;
		}
		super.setValueBinding(name, binding);
	}
}
