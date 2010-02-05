package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import javax.el.ValueExpression;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import org.rcfaces.core.component.CalendarComponent;
import javax.faces.application.Application;

public class CalendarTag extends AbstractCalendarTag implements Tag {


	private static final Log LOG=LogFactory.getLog(CalendarTag.class);

	private ValueExpression border;
	private ValueExpression calendarLayout;
	private ValueExpression multipleSelect;
	private ValueExpression mode;
	private ValueExpression autoSelection;
	public String getComponentType() {
		return CalendarComponent.COMPONENT_TYPE;
	}

	public final void setBorder(ValueExpression border) {
		this.border = border;
	}

	public final void setCalendarLayout(ValueExpression calendarLayout) {
		this.calendarLayout = calendarLayout;
	}

	public final void setMultipleSelect(ValueExpression multipleSelect) {
		this.multipleSelect = multipleSelect;
	}

	public final void setMode(ValueExpression mode) {
		this.mode = mode;
	}

	public final void setAutoSelection(ValueExpression autoSelection) {
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

		if (border != null) {
			if (border.isLiteralText()==false) {
				component.setValueExpression(Properties.BORDER, border);

			} else {
				component.setBorder(getBool(border.getExpressionString()));
			}
		}

		if (calendarLayout != null) {
			if (calendarLayout.isLiteralText()==false) {
				component.setValueExpression(Properties.CALENDAR_LAYOUT, calendarLayout);

			} else {
				component.setCalendarLayout(calendarLayout.getExpressionString());
			}
		}

		if (multipleSelect != null) {
			if (multipleSelect.isLiteralText()==false) {
				component.setValueExpression(Properties.MULTIPLE_SELECT, multipleSelect);

			} else {
				component.setMultipleSelect(getBool(multipleSelect.getExpressionString()));
			}
		}

		if (mode != null) {
			if (mode.isLiteralText()==false) {
				component.setValueExpression(Properties.MODE, mode);

			} else {
				component.setMode(mode.getExpressionString());
			}
		}

		if (autoSelection != null) {
			if (autoSelection.isLiteralText()==false) {
				component.setValueExpression(Properties.AUTO_SELECTION, autoSelection);

			} else {
				component.setAutoSelection(getBool(autoSelection.getExpressionString()));
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
