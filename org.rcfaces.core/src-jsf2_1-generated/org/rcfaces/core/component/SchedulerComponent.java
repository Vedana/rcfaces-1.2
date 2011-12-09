package org.rcfaces.core.component;

import java.util.TimeZone;
import org.rcfaces.core.internal.converter.LiteralDateConverter;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.ILiteralLocaleCapability;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IWidthCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.capability.IImmediateCapability;
import org.rcfaces.core.internal.util.ComponentIterators;
import org.rcfaces.core.internal.converter.TimeZoneConverter;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import org.apache.commons.logging.Log;
import org.rcfaces.core.internal.converter.LocaleConverter;
import java.util.Locale;
import java.util.Set;
import java.util.Collection;
import org.rcfaces.core.component.capability.IScrollableCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import java.util.List;
import java.lang.String;
import java.util.Date;
import org.rcfaces.core.component.capability.IHeightCapability;
import org.rcfaces.core.lang.Time;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.rcfaces.core.component.capability.ILiteralTimeZoneCapability;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import org.rcfaces.core.internal.converter.LiteralTimeConverter;
import java.util.Arrays;

public class SchedulerComponent extends CameliaBaseComponent implements 
	IWidthCapability,
	IHeightCapability,
	IScrollableCapability,
	ILiteralLocaleCapability,
	ILiteralTimeZoneCapability,
	IStyleClassCapability,
	IImmediateCapability,
	ISelectionEventCapability {

	private static final Log LOG = LogFactory.getLog(SchedulerComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.scheduler";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=CameliaBaseComponent.BEHAVIOR_EVENT_NAMES;

	public SchedulerComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public SchedulerComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void setDateBegin(String date) {


			getStateHelper().put(Properties.DATE_BEGIN, date);
		
	}

	public Date getDateBegin(FacesContext facesContext) {


			Object value=getStateHelper().eval(Properties.DATE_BEGIN, facesContext);
			if (value instanceof String) {
				value=LiteralDateConverter.SINGLETON.getAsObject(facesContext, this, (String)value);
			}
			
			return (Date)value;
		
	}

	public void setHourBegin(String time) {


				Time timeValue=(Time)LiteralTimeConverter.SINGLETON.getAsObject(null, this, time);
				setHourBegin(timeValue);
			
	}

	public void setHourEnd(String time) {


				Time timeValue=(Time)LiteralTimeConverter.SINGLETON.getAsObject(null, this, time);
				setHourEnd(timeValue);
			
	}

	public void setPeriodBegin(String date) {


			getStateHelper().put(Properties.PERIOD_BEGIN, date);
		
	}

	public Date getPeriodBegin(FacesContext facesContext) {


			Object value=getStateHelper().eval(Properties.PERIOD_BEGIN, facesContext);
			if (value instanceof String) {
				value=LiteralDateConverter.SINGLETON.getAsObject(facesContext, this, (String)value);
			}
			
			return (Date)value;
		
	}

	public void setPeriodEnd(String date) {


			getStateHelper().put(Properties.PERIOD_END, date);
		
	}

	public Date getPeriodEnd(FacesContext facesContext) {


			Object value=getStateHelper().eval(Properties.PERIOD_END, facesContext);
			if (value instanceof String) {
				value=LiteralDateConverter.SINGLETON.getAsObject(facesContext, this, (String)value);
			}
			
			return (Date)value;
		
	}

	public void setLiteralLocale(String locale) {


		setLiteralLocale((Locale)LocaleConverter.SINGLETON.getAsObject(null, this, locale));
		
	}

	public void setLiteralTimeZone(String timeZone) {


		setLiteralTimeZone((TimeZone)TimeZoneConverter.SINGLETON.getAsObject(null, this, timeZone));
		
	}

	public List getSchedulerColumn() {


				return ComponentIterators.list(this, SchedulerColumnComponent.class);
			
	}

	public List getPeriodClientData() {


				return ComponentIterators.list(this, PeriodClientDataComponent.class);
			
	}

	public java.lang.String getWidth() {
		return getWidth(null);
	}

	/**
	 * See {@link #getWidth() getWidth()} for more details
	 */
	public java.lang.String getWidth(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.WIDTH);
	}

	/**
	 * Returns <code>true</code> if the attribute "width" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isWidthSetted() {
		return getStateHelper().get(Properties.WIDTH)!=null;
	}

	public void setWidth(java.lang.String width) {
		getStateHelper().put(Properties.WIDTH, width);
	}

	public java.lang.String getHeight() {
		return getHeight(null);
	}

	/**
	 * See {@link #getHeight() getHeight()} for more details
	 */
	public java.lang.String getHeight(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.HEIGHT);
	}

	/**
	 * Returns <code>true</code> if the attribute "height" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHeightSetted() {
		return getStateHelper().get(Properties.HEIGHT)!=null;
	}

	public void setHeight(java.lang.String height) {
		getStateHelper().put(Properties.HEIGHT, height);
	}

	public int getHorizontalScrollPosition() {
		return getHorizontalScrollPosition(null);
	}

	/**
	 * See {@link #getHorizontalScrollPosition() getHorizontalScrollPosition()} for more details
	 */
	public int getHorizontalScrollPosition(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.HORIZONTAL_SCROLL_POSITION, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "horizontalScrollPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHorizontalScrollPositionSetted() {
		return getStateHelper().get(Properties.HORIZONTAL_SCROLL_POSITION)!=null;
	}

	public void setHorizontalScrollPosition(int horizontalScrollPosition) {
		getStateHelper().put(Properties.HORIZONTAL_SCROLL_POSITION, horizontalScrollPosition);
	}

	public int getVerticalScrollPosition() {
		return getVerticalScrollPosition(null);
	}

	/**
	 * See {@link #getVerticalScrollPosition() getVerticalScrollPosition()} for more details
	 */
	public int getVerticalScrollPosition(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.VERTICAL_SCROLL_POSITION, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "verticalScrollPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isVerticalScrollPositionSetted() {
		return getStateHelper().get(Properties.VERTICAL_SCROLL_POSITION)!=null;
	}

	public void setVerticalScrollPosition(int verticalScrollPosition) {
		getStateHelper().put(Properties.VERTICAL_SCROLL_POSITION, verticalScrollPosition);
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

	public boolean isImmediate() {
		return isImmediate(null);
	}

	/**
	 * See {@link #isImmediate() isImmediate()} for more details
	 */
	public boolean isImmediate(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.IMMEDIATE, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "immediate" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImmediateSetted() {
		return getStateHelper().get(Properties.IMMEDIATE)!=null;
	}

	public void setImmediate(boolean immediate) {
		getStateHelper().put(Properties.IMMEDIATE, immediate);
	}

	public final void addSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		addFacesListener(listener);
	}

	public final void removeSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listSelectionListeners() {
		return getFacesListeners(org.rcfaces.core.event.ISelectionListener.class);
	}

	public java.util.Date getDateBegin() {
		return getDateBegin(null);
	}

	public void setDateBegin(java.util.Date dateBegin) {
		 getStateHelper().put(Properties.DATE_BEGIN, dateBegin);
	}

	/**
	 * Returns <code>true</code> if the attribute "dateBegin" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDateBeginSetted() {
		return getStateHelper().get(Properties.DATE_BEGIN)!=null;
	}

	public org.rcfaces.core.lang.Time getHourBegin() {
		return getHourBegin(null);
	}

	public org.rcfaces.core.lang.Time getHourBegin(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.lang.Time)getStateHelper().eval(Properties.HOUR_BEGIN);
	}

	public void setHourBegin(org.rcfaces.core.lang.Time hourBegin) {
		 getStateHelper().put(Properties.HOUR_BEGIN, hourBegin);
	}

	/**
	 * Returns <code>true</code> if the attribute "hourBegin" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isHourBeginSetted() {
		return getStateHelper().get(Properties.HOUR_BEGIN)!=null;
	}

	public org.rcfaces.core.lang.Time getHourEnd() {
		return getHourEnd(null);
	}

	public org.rcfaces.core.lang.Time getHourEnd(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.lang.Time)getStateHelper().eval(Properties.HOUR_END);
	}

	public void setHourEnd(org.rcfaces.core.lang.Time hourEnd) {
		 getStateHelper().put(Properties.HOUR_END, hourEnd);
	}

	/**
	 * Returns <code>true</code> if the attribute "hourEnd" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isHourEndSetted() {
		return getStateHelper().get(Properties.HOUR_END)!=null;
	}

	public int getPrimaryTick() {
		return getPrimaryTick(null);
	}

	public int getPrimaryTick(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.PRIMARY_TICK, 0);
	}

	public void setPrimaryTick(int primaryTick) {
		 getStateHelper().put(Properties.PRIMARY_TICK, primaryTick);
	}

	/**
	 * Returns <code>true</code> if the attribute "primaryTick" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPrimaryTickSetted() {
		return getStateHelper().get(Properties.PRIMARY_TICK)!=null;
	}

	public int getSecondaryTick() {
		return getSecondaryTick(null);
	}

	public int getSecondaryTick(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.SECONDARY_TICK, 0);
	}

	public void setSecondaryTick(int secondaryTick) {
		 getStateHelper().put(Properties.SECONDARY_TICK, secondaryTick);
	}

	/**
	 * Returns <code>true</code> if the attribute "secondaryTick" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSecondaryTickSetted() {
		return getStateHelper().get(Properties.SECONDARY_TICK)!=null;
	}

	public boolean isShowPrimaryTickLabel() {
		return isShowPrimaryTickLabel(null);
	}

	public boolean isShowPrimaryTickLabel(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.SHOW_PRIMARY_TICK_LABEL, true);
	}

	public void setShowPrimaryTickLabel(boolean showPrimaryTickLabel) {
		 getStateHelper().put(Properties.SHOW_PRIMARY_TICK_LABEL, showPrimaryTickLabel);
	}

	/**
	 * Returns <code>true</code> if the attribute "showPrimaryTickLabel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isShowPrimaryTickLabelSetted() {
		return getStateHelper().get(Properties.SHOW_PRIMARY_TICK_LABEL)!=null;
	}

	public boolean isShowSecondaryTickLabel() {
		return isShowSecondaryTickLabel(null);
	}

	public boolean isShowSecondaryTickLabel(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.SHOW_SECONDARY_TICK_LABEL, false);
	}

	public void setShowSecondaryTickLabel(boolean showSecondaryTickLabel) {
		 getStateHelper().put(Properties.SHOW_SECONDARY_TICK_LABEL, showSecondaryTickLabel);
	}

	/**
	 * Returns <code>true</code> if the attribute "showSecondaryTickLabel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isShowSecondaryTickLabelSetted() {
		return getStateHelper().get(Properties.SHOW_SECONDARY_TICK_LABEL)!=null;
	}

	public Object getPeriods() {
		return getPeriods(null);
	}

	public Object getPeriods(javax.faces.context.FacesContext facesContext) {
		return getStateHelper().eval(Properties.PERIODS);
	}

	public void setPeriods(Object periods) {
		 getStateHelper().put(Properties.PERIODS, periods);
	}

	/**
	 * Returns <code>true</code> if the attribute "periods" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPeriodsSetted() {
		return getStateHelper().get(Properties.PERIODS)!=null;
	}

	public java.util.Date getPeriodBegin() {
		return getPeriodBegin(null);
	}

	public void setPeriodBegin(java.util.Date periodBegin) {
		 getStateHelper().put(Properties.PERIOD_BEGIN, periodBegin);
	}

	/**
	 * Returns <code>true</code> if the attribute "periodBegin" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPeriodBeginSetted() {
		return getStateHelper().get(Properties.PERIOD_BEGIN)!=null;
	}

	public java.util.Date getPeriodEnd() {
		return getPeriodEnd(null);
	}

	public void setPeriodEnd(java.util.Date periodEnd) {
		 getStateHelper().put(Properties.PERIOD_END, periodEnd);
	}

	/**
	 * Returns <code>true</code> if the attribute "periodEnd" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPeriodEndSetted() {
		return getStateHelper().get(Properties.PERIOD_END)!=null;
	}

	public String getPeriodLabel() {
		return getPeriodLabel(null);
	}

	public String getPeriodLabel(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.PERIOD_LABEL);
	}

	public void setPeriodLabel(String periodLabel) {
		 getStateHelper().put(Properties.PERIOD_LABEL, periodLabel);
	}

	/**
	 * Returns <code>true</code> if the attribute "periodLabel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPeriodLabelSetted() {
		return getStateHelper().get(Properties.PERIOD_LABEL)!=null;
	}

	public String getPeriodStyle() {
		return getPeriodStyle(null);
	}

	public String getPeriodStyle(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.PERIOD_STYLE);
	}

	public void setPeriodStyle(String periodStyle) {
		 getStateHelper().put(Properties.PERIOD_STYLE, periodStyle);
	}

	/**
	 * Returns <code>true</code> if the attribute "periodStyle" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPeriodStyleSetted() {
		return getStateHelper().get(Properties.PERIOD_STYLE)!=null;
	}

	public boolean isPeriodSelectable() {
		return isPeriodSelectable(null);
	}

	public boolean isPeriodSelectable(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.PERIOD_SELECTABLE, false);
	}

	public void setPeriodSelectable(boolean periodSelectable) {
		 getStateHelper().put(Properties.PERIOD_SELECTABLE, periodSelectable);
	}

	/**
	 * Returns <code>true</code> if the attribute "periodSelectable" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPeriodSelectableSetted() {
		return getStateHelper().get(Properties.PERIOD_SELECTABLE)!=null;
	}

	public String getPeriodToolTip() {
		return getPeriodToolTip(null);
	}

	public String getPeriodToolTip(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.PERIOD_TOOL_TIP);
	}

	public void setPeriodToolTip(String periodToolTip) {
		 getStateHelper().put(Properties.PERIOD_TOOL_TIP, periodToolTip);
	}

	/**
	 * Returns <code>true</code> if the attribute "periodToolTip" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPeriodToolTipSetted() {
		return getStateHelper().get(Properties.PERIOD_TOOL_TIP)!=null;
	}

	public String getPeriodValue() {
		return getPeriodValue(null);
	}

	public String getPeriodValue(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.PERIOD_VALUE);
	}

	public void setPeriodValue(String periodValue) {
		 getStateHelper().put(Properties.PERIOD_VALUE, periodValue);
	}

	/**
	 * Returns <code>true</code> if the attribute "periodValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPeriodValueSetted() {
		return getStateHelper().get(Properties.PERIOD_VALUE)!=null;
	}

	public String getVar() {
		return getVar(null);
	}

	public String getVar(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.VAR);
	}

	public void setVar(String var) {
		 getStateHelper().put(Properties.VAR, var);
	}

	/**
	 * Returns <code>true</code> if the attribute "var" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isVarSetted() {
		return getStateHelper().get(Properties.VAR)!=null;
	}

}
