package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.CalendarLayoutConverter;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.ICalendarModeCapability;
import java.lang.String;
import org.rcfaces.core.component.capability.ICalendarLayoutCapability;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.capability.IMultipleSelectCapability;
import org.rcfaces.core.component.AbstractCalendarComponent;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.internal.converter.CalendarModeConverter
			;

/**
 * <p>The calendar Component shows a calendar. It can be customized in differents ways (days off, holidays, tool tips ...).</p>
 * <p>The calendar Component has the following capabilities :
 * <ul>
 * <li>ISelectionEventCapability</li>
 * <li>IReadOnlyCapability</li>
 * <li>ILiteralLocaleCapability</li>
 * <li>ILiteralTimeZoneCapability</li>
 * <li>IComponentLocaleCapability</li>
 * <li>IComponentTimeZoneCapability</li>
 * <li>IClientDatesStrategyCapability</li>
 * <li>IBorderCapability</li>
 * <li>ICalendarLayoutCapability</li>
 * <li>IMultipleSelectCapability</li>
 * <li>ICalendarModeCapability</li>
 * </ul>
 * </p>
 * 
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/CalendarComponent.html">calendar</a> renderer is linked to the <a href="/jsdocs/index.html?f_calendar.html" target="_blank">f_calendar</a> javascript class. f_calendar extends f_component, fa_readOnly, fa_disabled, fa_itemsWrapper</p>
 * <p> Table of the main component style classes: </p>
 * <table border="1" cellpadding="3" cellspacing="0" width="100%">
 * <tbody>
 * 
 * <tr style="text-align:left">
 * <th  width="33%">Style Name</th>
 * <th width="50%">Description</th>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td width="33%">f_calendar_yearCursor</td>
 * <td width="50%">Defines styles for the wrapper Table element used to change years of the calendar. Contains other style</td>
 * </tr>
 * <tr  style="text-align:left">
 * <td width="33%">f_calendar_monthList</td>
 * <td width="50%">Defines styles for the wrapper Table element the element containing the  months of year  </td>
 * </tr>
 * <tr  style="text-align:left">
 * <td width="33%">f_calendar_dayList</td>
 * <td width="50%">Defines styles for the wrapper Table element the element containing the day list of year  </td>
 * </tr>
 * <tr  style="text-align:left">
 * <td width="33%">f_calendar_day</td>
 * <td width="50%">Defines styles for the A element</td>
 * </tr>
 * 
 * 
 * </tbody>
 * </table>
 */
public class CalendarComponent extends AbstractCalendarComponent implements 
	IBorderCapability,
	ICalendarLayoutCapability,
	IMultipleSelectCapability,
	ICalendarModeCapability {

	private static final Log LOG = LogFactory.getLog(CalendarComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.calendar";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractCalendarComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"multipleSelect","border","autoSelection","mode","calendarLayout"}));
	}

	public CalendarComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public CalendarComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void setMode(String calendarMode) {

			
				setMode(((Integer)CalendarModeConverter.SINGLETON.getAsObject(null, this, calendarMode)).intValue());
			
	}

	public void setCalendarLayout(String layout) {


			setCalendarLayout(((Integer)CalendarLayoutConverter.SINGLETON.getAsObject(null, this, layout)).intValue());
		
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

	public boolean isMultipleSelect() {
		return isMultipleSelect(null);
	}

	/**
	 * See {@link #isMultipleSelect() isMultipleSelect()} for more details
	 */
	public boolean isMultipleSelect(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.MULTIPLE_SELECT, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "multipleSelect" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMultipleSelectSetted() {
		return engine.isPropertySetted(Properties.MULTIPLE_SELECT);
	}

	public void setMultipleSelect(boolean multipleSelect) {
		engine.setProperty(Properties.MULTIPLE_SELECT, multipleSelect);
	}

	public int getMode() {
		return getMode(null);
	}

	/**
	 * See {@link #getMode() getMode()} for more details
	 */
	public int getMode(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.MODE,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "mode" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isModeSetted() {
		return engine.isPropertySetted(Properties.MODE);
	}

	public void setMode(int mode) {
		engine.setProperty(Properties.MODE, mode);
	}

	public boolean isAutoSelection() {
		return isAutoSelection(null);
	}

	public boolean isAutoSelection(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.AUTO_SELECTION, false, facesContext);
	}

	public void setAutoSelection(boolean autoSelection) {
		engine.setProperty(Properties.AUTO_SELECTION, autoSelection);
	}

	/**
	 * Returns <code>true</code> if the attribute "autoSelection" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isAutoSelectionSetted() {
		return engine.isPropertySetted(Properties.AUTO_SELECTION);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
