package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.CalendarModeConverter;
import org.rcfaces.core.internal.converter.CalendarLayoutConverter;
import java.lang.String;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.ICalendarModeCapability;
import org.rcfaces.core.component.capability.ICalendarLayoutCapability;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.capability.IMultipleSelectCapability;
import org.rcfaces.core.component.AbstractCalendarComponent;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import java.util.Set;
import java.util.Arrays;
import java.util.Collection;

/**
 * <p>The calendar Component shows a calendar. It can be customized in differents ways (days off, holidays, tool tips ...).</p>
 * <p>The calendar Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text, font &amp; image</li>
 * <li>Margin &amp; border</li>
 * <li>Help</li>
 * <li>Visibility, Read-Only, Disabled</li>
 * <li>Events Handling</li>
 * <li>Calendar functions</li>
 * </ul>
 * </p>
 */
public class CalendarComponent extends AbstractCalendarComponent implements 
	IBorderCapability,
	ICalendarLayoutCapability,
	IMultipleSelectCapability,
	ICalendarModeCapability {

	private static final Log LOG = LogFactory.getLog(CalendarComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.calendar";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractCalendarComponent.BEHAVIOR_EVENT_NAMES;

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

	public boolean isMultipleSelect() {
		return isMultipleSelect(null);
	}

	/**
	 * See {@link #isMultipleSelect() isMultipleSelect()} for more details
	 */
	public boolean isMultipleSelect(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.MULTIPLE_SELECT, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "multipleSelect" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMultipleSelectSetted() {
		return getStateHelper().get(Properties.MULTIPLE_SELECT)!=null;
	}

	public void setMultipleSelect(boolean multipleSelect) {
		getStateHelper().put(Properties.MULTIPLE_SELECT, multipleSelect);
	}

	public int getMode() {
		return getMode(null);
	}

	/**
	 * See {@link #getMode() getMode()} for more details
	 */
	public int getMode(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.MODE, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "mode" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isModeSetted() {
		return getStateHelper().get(Properties.MODE)!=null;
	}

	public void setMode(int mode) {
		getStateHelper().put(Properties.MODE, mode);
	}

	public boolean isAutoSelection() {
		return isAutoSelection(null);
	}

	public boolean isAutoSelection(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.AUTO_SELECTION, false);
	}

	public void setAutoSelection(boolean autoSelection) {
		 getStateHelper().put(Properties.AUTO_SELECTION, autoSelection);
	}

	/**
	 * Returns <code>true</code> if the attribute "autoSelection" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isAutoSelectionSetted() {
		return getStateHelper().get(Properties.AUTO_SELECTION)!=null;
	}

}
