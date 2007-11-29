package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.component.CalendarComponent;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class CalendarTag extends AbstractCalendarTag implements Tag {


	private static final Log LOG=LogFactory.getLog(CalendarTag.class);

	private ValueExpression border;
	private ValueExpression mode;
	public String getComponentType() {
		return CalendarComponent.COMPONENT_TYPE;
	}

	public final void setBorder(ValueExpression border) {
		this.border = border;
	}

	public final void setMode(ValueExpression mode) {
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
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'CalendarComponent'.");
		}

		CalendarComponent component = (CalendarComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (border != null) {
			if (border.isLiteralText()==false) {
				component.setValueExpression(Properties.BORDER, border);

			} else {
				component.setBorder(getBool(border.getExpressionString()));
			}
		}

		if (mode != null) {
			if (mode.isLiteralText()==false) {
				component.setValueExpression(Properties.MODE, mode);

			} else {
				component.setMode(mode.getExpressionString());
			}
		}
	}

	public void release() {
		border = null;
		mode = null;

		super.release();
	}

}
