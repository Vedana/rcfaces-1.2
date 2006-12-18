package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.ICalendarModeCapability;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.converter.CalendarModeConverter;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.AbstractCalendarComponent;

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

	public final void setMode(String calendarMode) {


			setMode(((Integer)CalendarModeConverter.SINGLETON.getAsObject(null, this, calendarMode)).intValue());
			
	}

	public final boolean isBorder() {
		return isBorder(null);
	}

	public final boolean isBorder(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.BORDER, true, facesContext);
	}

	public final void setBorder(boolean border) {
		engine.setProperty(Properties.BORDER, border);
	}

	public final void setBorder(ValueBinding border) {
		engine.setProperty(Properties.BORDER, border);
	}

	public final int getMode() {
		return getMode(null);
	}

	public final int getMode(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.MODE,0, facesContext);
	}

	public final void setMode(int mode) {
		engine.setProperty(Properties.MODE, mode);
	}

	public final void setMode(ValueBinding mode) {
		engine.setProperty(Properties.MODE, mode);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
