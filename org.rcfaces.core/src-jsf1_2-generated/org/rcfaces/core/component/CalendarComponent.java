package org.rcfaces.core.component;

import java.lang.String;
import org.rcfaces.core.component.capability.ICalendarModeCapability;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.rcfaces.core.internal.converter.CalendarModeConverter;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.AbstractCalendarComponent;

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
	ICalendarModeCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.calendar";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractCalendarComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"mode","border"}));
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

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
