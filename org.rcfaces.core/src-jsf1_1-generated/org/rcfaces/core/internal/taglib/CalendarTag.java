package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import org.rcfaces.core.component.CalendarComponent;
import javax.faces.application.Application;

public class CalendarTag extends AbstractCalendarTag implements Tag {


	private static final Log LOG=LogFactory.getLog(CalendarTag.class);

	private String border;
	private String calendarLayout;
	private String multipleSelect;
	private String mode;
	private String autoSelection;
	public String getComponentType() {
		return CalendarComponent.COMPONENT_TYPE;
	}

	public final String getBorder() {
		return border;
	}

	public final void setBorder(String border) {
		this.border = border;
	}

	public final String getCalendarLayout() {
		return calendarLayout;
	}

	public final void setCalendarLayout(String calendarLayout) {
		this.calendarLayout = calendarLayout;
	}

	public final String getMultipleSelect() {
		return multipleSelect;
	}

	public final void setMultipleSelect(String multipleSelect) {
		this.multipleSelect = multipleSelect;
	}

	public final String getMode() {
		return mode;
	}

	public final void setMode(String mode) {
		this.mode = mode;
	}

	public final void setAutoSelection(String autoSelection) {
		this.autoSelection = autoSelection;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (CalendarComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  border='"+border+"'");
			LOG.debug("  calendarLayout='"+calendarLayout+"'");
			LOG.debug("  multipleSelect='"+multipleSelect+"'");
			LOG.debug("  mode='"+mode+"'");
			LOG.debug("  autoSelection='"+autoSelection+"'");
		}
		if ((uiComponent instanceof CalendarComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'CalendarComponent'.");
		}

		super.setProperties(uiComponent);

		CalendarComponent component = (CalendarComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (border != null) {
			if (isValueReference(border)) {
				ValueBinding vb = application.createValueBinding(border);
				component.setValueBinding(Properties.BORDER, vb);

			} else {
				component.setBorder(getBool(border));
			}
		}

		if (calendarLayout != null) {
			if (isValueReference(calendarLayout)) {
				ValueBinding vb = application.createValueBinding(calendarLayout);
				component.setValueBinding(Properties.CALENDAR_LAYOUT, vb);

			} else {
				component.setCalendarLayout(calendarLayout);
			}
		}

		if (multipleSelect != null) {
			if (isValueReference(multipleSelect)) {
				ValueBinding vb = application.createValueBinding(multipleSelect);
				component.setValueBinding(Properties.MULTIPLE_SELECT, vb);

			} else {
				component.setMultipleSelect(getBool(multipleSelect));
			}
		}

		if (mode != null) {
			if (isValueReference(mode)) {
				ValueBinding vb = application.createValueBinding(mode);
				component.setValueBinding(Properties.MODE, vb);

			} else {
				component.setMode(mode);
			}
		}

		if (autoSelection != null) {
			if (isValueReference(autoSelection)) {
				ValueBinding vb = application.createValueBinding(autoSelection);
				component.setValueBinding(Properties.AUTO_SELECTION, vb);

			} else {
				component.setAutoSelection(getBool(autoSelection));
			}
		}
	}

	public void release() {
		border = null;
		calendarLayout = null;
		multipleSelect = null;
		mode = null;
		autoSelection = null;

		super.release();
	}

}
