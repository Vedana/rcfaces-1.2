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
import org.rcfaces.core.component.capability.IScrollableCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import java.util.List;
import java.lang.String;
import java.util.Date;
import org.rcfaces.core.component.capability.ITabIndexCapability;
import org.rcfaces.core.component.capability.IHeightCapability;
import org.rcfaces.core.lang.Time;
import javax.el.ValueExpression;
import org.rcfaces.core.component.capability.ILiteralTimeZoneCapability;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import org.rcfaces.core.internal.converter.LiteralTimeConverter;
import java.util.Arrays;

/**
 * <p>The scheduler Component shows a planning</p>
 * <p>The scheduler Component has the following capabilities :
 * <ul>
 * <li>IWidthCapability</li>
 * <li>IHeightCapability</li>
 * <li>IScrollableCapability</li>
 * <li>ILiteralLocaleCapability</li>
 * <li>ILiteralTimeZoneCapability</li>
 * <li>IStyleClassCapability</li>
 * <li>ITabIndexCapability</li>
 * <li>IImmediateCapability</li>
 * <li>ISelectionEventCapability </li>
 * </ul>
 * </p>
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/SchedulerComponent.html">scheduler</a> renderer is linked to the <a href="/jsdocs/index.html?f_scheduler.html" target="_blank">f_scheduler</a> javascript class. f_scheduler extends f_component, fa_items, fa_selectionManager</p>
 * 
 * <p> Table of component style classes: </p>
 * <table border="1" cellpadding="3" cellspacing="0" width="100%">
 * <tbody>
 * 
 * <tr style="text-align:left">
 * <th  width="33%">Style Name</th>
 * <th  width="50%">Description</th>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td width="33%">f_scheduler</td>
 * <td width="50%">Defines styles for the wrapper DIV element</td>
 * </tr>
 * 
 * </tbody>
 * </table>
 */
public class SchedulerComponent extends CameliaBaseComponent implements 
	IWidthCapability,
	IHeightCapability,
	IScrollableCapability,
	ILiteralLocaleCapability,
	ILiteralTimeZoneCapability,
	IStyleClassCapability,
	ITabIndexCapability,
	IImmediateCapability,
	ISelectionEventCapability {

	private static final Log LOG = LogFactory.getLog(SchedulerComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.scheduler";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"hourBegin","secondaryTick","periods","var","tabIndex","selectionListener","literalLocale","height","periodStyle","verticalScrollPosition","showSecondaryTickLabel","periodEnd","periodToolTip","literalTimeZone","hourEnd","periodSelectable","periodLabel","primaryTick","periodValue","styleClass","width","showPrimaryTickLabel","dateBegin","horizontalScrollPosition","immediate","periodBegin"}));
	}

	public SchedulerComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public SchedulerComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void setDateBegin(String date) {


			engine.setProperty(Properties.DATE_BEGIN, date);
		
	}

	public Date getDateBegin(FacesContext facesContext) {


			Object value=engine.getProperty(Properties.DATE_BEGIN, facesContext);
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


			engine.setProperty(Properties.PERIOD_BEGIN, date);
		
	}

	public Date getPeriodBegin(FacesContext facesContext) {


			Object value=engine.getProperty(Properties.PERIOD_BEGIN, facesContext);
			if (value instanceof String) {
				value=LiteralDateConverter.SINGLETON.getAsObject(facesContext, this, (String)value);
			}
			
			return (Date)value;
		
	}

	public void setPeriodEnd(String date) {


			engine.setProperty(Properties.PERIOD_END, date);
		
	}

	public Date getPeriodEnd(FacesContext facesContext) {


			Object value=engine.getProperty(Properties.PERIOD_END, facesContext);
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
		return engine.getStringProperty(Properties.WIDTH, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "width" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isWidthSetted() {
		return engine.isPropertySetted(Properties.WIDTH);
	}

	public void setWidth(java.lang.String width) {
		engine.setProperty(Properties.WIDTH, width);
	}

	public java.lang.String getHeight() {
		return getHeight(null);
	}

	/**
	 * See {@link #getHeight() getHeight()} for more details
	 */
	public java.lang.String getHeight(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.HEIGHT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "height" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHeightSetted() {
		return engine.isPropertySetted(Properties.HEIGHT);
	}

	public void setHeight(java.lang.String height) {
		engine.setProperty(Properties.HEIGHT, height);
	}

	public int getHorizontalScrollPosition() {
		return getHorizontalScrollPosition(null);
	}

	/**
	 * See {@link #getHorizontalScrollPosition() getHorizontalScrollPosition()} for more details
	 */
	public int getHorizontalScrollPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.HORIZONTAL_SCROLL_POSITION,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "horizontalScrollPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHorizontalScrollPositionSetted() {
		return engine.isPropertySetted(Properties.HORIZONTAL_SCROLL_POSITION);
	}

	public void setHorizontalScrollPosition(int horizontalScrollPosition) {
		engine.setProperty(Properties.HORIZONTAL_SCROLL_POSITION, horizontalScrollPosition);
	}

	public int getVerticalScrollPosition() {
		return getVerticalScrollPosition(null);
	}

	/**
	 * See {@link #getVerticalScrollPosition() getVerticalScrollPosition()} for more details
	 */
	public int getVerticalScrollPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.VERTICAL_SCROLL_POSITION,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "verticalScrollPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isVerticalScrollPositionSetted() {
		return engine.isPropertySetted(Properties.VERTICAL_SCROLL_POSITION);
	}

	public void setVerticalScrollPosition(int verticalScrollPosition) {
		engine.setProperty(Properties.VERTICAL_SCROLL_POSITION, verticalScrollPosition);
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

	public java.lang.Integer getTabIndex() {
		return getTabIndex(null);
	}

	/**
	 * See {@link #getTabIndex() getTabIndex()} for more details
	 */
	public java.lang.Integer getTabIndex(javax.faces.context.FacesContext facesContext) {
		return engine.getIntegerProperty(Properties.TAB_INDEX, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "tabIndex" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTabIndexSetted() {
		return engine.isPropertySetted(Properties.TAB_INDEX);
	}

	public void setTabIndex(java.lang.Integer tabIndex) {
		engine.setProperty(Properties.TAB_INDEX, tabIndex);
	}

	public boolean isImmediate() {
		return isImmediate(null);
	}

	/**
	 * See {@link #isImmediate() isImmediate()} for more details
	 */
	public boolean isImmediate(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.IMMEDIATE, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "immediate" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImmediateSetted() {
		return engine.isPropertySetted(Properties.IMMEDIATE);
	}

	public void setImmediate(boolean immediate) {
		engine.setProperty(Properties.IMMEDIATE, immediate);
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
		engine.setProperty(Properties.DATE_BEGIN, dateBegin);
	}

	/**
	 * Returns <code>true</code> if the attribute "dateBegin" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDateBeginSetted() {
		return engine.isPropertySetted(Properties.DATE_BEGIN);
	}

	public org.rcfaces.core.lang.Time getHourBegin() {
		return getHourBegin(null);
	}

	public org.rcfaces.core.lang.Time getHourBegin(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.lang.Time)engine.getValue(Properties.HOUR_BEGIN, facesContext);
	}

	public void setHourBegin(org.rcfaces.core.lang.Time hourBegin) {
		engine.setProperty(Properties.HOUR_BEGIN, hourBegin);
	}

	/**
	 * Returns <code>true</code> if the attribute "hourBegin" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isHourBeginSetted() {
		return engine.isPropertySetted(Properties.HOUR_BEGIN);
	}

	public org.rcfaces.core.lang.Time getHourEnd() {
		return getHourEnd(null);
	}

	public org.rcfaces.core.lang.Time getHourEnd(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.lang.Time)engine.getValue(Properties.HOUR_END, facesContext);
	}

	public void setHourEnd(org.rcfaces.core.lang.Time hourEnd) {
		engine.setProperty(Properties.HOUR_END, hourEnd);
	}

	/**
	 * Returns <code>true</code> if the attribute "hourEnd" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isHourEndSetted() {
		return engine.isPropertySetted(Properties.HOUR_END);
	}

	public int getPrimaryTick() {
		return getPrimaryTick(null);
	}

	public int getPrimaryTick(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.PRIMARY_TICK, 0, facesContext);
	}

	public void setPrimaryTick(int primaryTick) {
		engine.setProperty(Properties.PRIMARY_TICK, primaryTick);
	}

	/**
	 * Returns <code>true</code> if the attribute "primaryTick" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPrimaryTickSetted() {
		return engine.isPropertySetted(Properties.PRIMARY_TICK);
	}

	public int getSecondaryTick() {
		return getSecondaryTick(null);
	}

	public int getSecondaryTick(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.SECONDARY_TICK, 0, facesContext);
	}

	public void setSecondaryTick(int secondaryTick) {
		engine.setProperty(Properties.SECONDARY_TICK, secondaryTick);
	}

	/**
	 * Returns <code>true</code> if the attribute "secondaryTick" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSecondaryTickSetted() {
		return engine.isPropertySetted(Properties.SECONDARY_TICK);
	}

	public boolean isShowPrimaryTickLabel() {
		return isShowPrimaryTickLabel(null);
	}

	public boolean isShowPrimaryTickLabel(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.SHOW_PRIMARY_TICK_LABEL, true, facesContext);
	}

	public void setShowPrimaryTickLabel(boolean showPrimaryTickLabel) {
		engine.setProperty(Properties.SHOW_PRIMARY_TICK_LABEL, showPrimaryTickLabel);
	}

	/**
	 * Returns <code>true</code> if the attribute "showPrimaryTickLabel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isShowPrimaryTickLabelSetted() {
		return engine.isPropertySetted(Properties.SHOW_PRIMARY_TICK_LABEL);
	}

	public boolean isShowSecondaryTickLabel() {
		return isShowSecondaryTickLabel(null);
	}

	public boolean isShowSecondaryTickLabel(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.SHOW_SECONDARY_TICK_LABEL, false, facesContext);
	}

	public void setShowSecondaryTickLabel(boolean showSecondaryTickLabel) {
		engine.setProperty(Properties.SHOW_SECONDARY_TICK_LABEL, showSecondaryTickLabel);
	}

	/**
	 * Returns <code>true</code> if the attribute "showSecondaryTickLabel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isShowSecondaryTickLabelSetted() {
		return engine.isPropertySetted(Properties.SHOW_SECONDARY_TICK_LABEL);
	}

	public Object getPeriods() {
		return getPeriods(null);
	}

	public Object getPeriods(javax.faces.context.FacesContext facesContext) {
		return engine.getValue(Properties.PERIODS, facesContext);
	}

	public void setPeriods(Object periods) {
		engine.setValue(Properties.PERIODS, periods);
	}

	/**
	 * Returns <code>true</code> if the attribute "periods" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPeriodsSetted() {
		return engine.isPropertySetted(Properties.PERIODS);
	}

	public java.util.Date getPeriodBegin() {
		return getPeriodBegin(null);
	}

	public void setPeriodBegin(java.util.Date periodBegin) {
		engine.setProperty(Properties.PERIOD_BEGIN, periodBegin);
	}

	/**
	 * Returns <code>true</code> if the attribute "periodBegin" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPeriodBeginSetted() {
		return engine.isPropertySetted(Properties.PERIOD_BEGIN);
	}

	public java.util.Date getPeriodEnd() {
		return getPeriodEnd(null);
	}

	public void setPeriodEnd(java.util.Date periodEnd) {
		engine.setProperty(Properties.PERIOD_END, periodEnd);
	}

	/**
	 * Returns <code>true</code> if the attribute "periodEnd" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPeriodEndSetted() {
		return engine.isPropertySetted(Properties.PERIOD_END);
	}

	public String getPeriodLabel() {
		return getPeriodLabel(null);
	}

	public String getPeriodLabel(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.PERIOD_LABEL, facesContext);
	}

	public void setPeriodLabel(String periodLabel) {
		engine.setProperty(Properties.PERIOD_LABEL, periodLabel);
	}

	/**
	 * Returns <code>true</code> if the attribute "periodLabel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPeriodLabelSetted() {
		return engine.isPropertySetted(Properties.PERIOD_LABEL);
	}

	public String getPeriodStyle() {
		return getPeriodStyle(null);
	}

	public String getPeriodStyle(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.PERIOD_STYLE, facesContext);
	}

	public void setPeriodStyle(String periodStyle) {
		engine.setProperty(Properties.PERIOD_STYLE, periodStyle);
	}

	/**
	 * Returns <code>true</code> if the attribute "periodStyle" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPeriodStyleSetted() {
		return engine.isPropertySetted(Properties.PERIOD_STYLE);
	}

	public boolean isPeriodSelectable() {
		return isPeriodSelectable(null);
	}

	public boolean isPeriodSelectable(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.PERIOD_SELECTABLE, false, facesContext);
	}

	public void setPeriodSelectable(boolean periodSelectable) {
		engine.setProperty(Properties.PERIOD_SELECTABLE, periodSelectable);
	}

	/**
	 * Returns <code>true</code> if the attribute "periodSelectable" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPeriodSelectableSetted() {
		return engine.isPropertySetted(Properties.PERIOD_SELECTABLE);
	}

	public String getPeriodToolTip() {
		return getPeriodToolTip(null);
	}

	public String getPeriodToolTip(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.PERIOD_TOOL_TIP, facesContext);
	}

	public void setPeriodToolTip(String periodToolTip) {
		engine.setProperty(Properties.PERIOD_TOOL_TIP, periodToolTip);
	}

	/**
	 * Returns <code>true</code> if the attribute "periodToolTip" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPeriodToolTipSetted() {
		return engine.isPropertySetted(Properties.PERIOD_TOOL_TIP);
	}

	public String getPeriodValue() {
		return getPeriodValue(null);
	}

	public String getPeriodValue(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.PERIOD_VALUE, facesContext);
	}

	public void setPeriodValue(String periodValue) {
		engine.setProperty(Properties.PERIOD_VALUE, periodValue);
	}

	/**
	 * Returns <code>true</code> if the attribute "periodValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPeriodValueSetted() {
		return engine.isPropertySetted(Properties.PERIOD_VALUE);
	}

	public String getVar() {
		return getVar(null);
	}

	public String getVar(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.VAR, facesContext);
	}

	public void setVar(String var) {
		engine.setProperty(Properties.VAR, var);
	}

	/**
	 * Returns <code>true</code> if the attribute "var" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isVarSetted() {
		return engine.isPropertySetted(Properties.VAR);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
