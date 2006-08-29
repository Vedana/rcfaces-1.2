package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.ICalendarModeCapability;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.converter.CalendarModeConverter;
import org.rcfaces.core.internal.component.AbstractCalendarComponent;
import org.rcfaces.core.component.capability.IBorderCapability;

public class CalendarComponent extends AbstractCalendarComponent implements 
	IBorderCapability,
	ICalendarModeCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.calendar";


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

	public void release() {
		super.release();
	}
}
