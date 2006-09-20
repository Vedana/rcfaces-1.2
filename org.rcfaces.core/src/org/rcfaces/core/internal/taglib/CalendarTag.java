package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import org.rcfaces.core.component.CalendarComponent;
import javax.faces.application.Application;

public class CalendarTag extends AbstractCalendarTag implements Tag {


	private static final Log LOG=LogFactory.getLog(CalendarTag.class);

	private String border;
	private String mode;
	public String getComponentType() {
		return CalendarComponent.COMPONENT_TYPE;
	}

	public final String getBorder() {
		return border;
	}

	public final void setBorder(String border) {
		this.border = border;
	}

	public final String getMode() {
		return mode;
	}

	public final void setMode(String mode) {
		this.mode = mode;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (CalendarComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  border='"+border+"'");
			LOG.debug("  mode='"+mode+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof CalendarComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'CalendarComponent'.");
		}

		CalendarComponent component = (CalendarComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (border != null) {
			if (isValueReference(border)) {
				ValueBinding vb = application.createValueBinding(border);

				component.setBorder(vb);
			} else {
				component.setBorder(getBool(border));
			}
		}

		if (mode != null) {
			if (isValueReference(mode)) {
				ValueBinding vb = application.createValueBinding(mode);

				component.setMode(vb);
			} else {
				component.setMode(mode);
			}
		}
	}

	public void release() {
		border = null;
		mode = null;

		super.release();
	}

}
