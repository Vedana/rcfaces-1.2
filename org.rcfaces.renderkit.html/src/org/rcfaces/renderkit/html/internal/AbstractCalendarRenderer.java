/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.AbstractCalendarComponent;
import org.rcfaces.core.component.capability.IClientDatesStrategyCapability;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.renderkit.html.internal.decorator.CalendarDecorator;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractCalendarRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    protected void writeCalendarAttributes(IHtmlWriter htmlWriter,
            Calendar componentCalendar) throws WriterException {

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        IProcessContext processContext = componentRenderContext
                .getRenderContext().getProcessContext();

        AbstractCalendarComponent calendarComponent = (AbstractCalendarComponent) componentRenderContext
                .getComponent();

        Date maxDate = calendarComponent.getMaxDate(facesContext);
        Date minDate = calendarComponent.getMinDate(facesContext);
        Date twoDigitYearStart = calendarComponent
                .getTwoDigitYearStart(facesContext);

        if (maxDate != null || minDate != null || twoDigitYearStart != null) {
            StringAppender sb = new StringAppender(12);

            if (maxDate != null) {
                sb.setLength(0);
                HtmlTools.formatDate(maxDate, sb, processContext,
                        calendarComponent, true);
                htmlWriter.writeAttribute("v:maxDate", sb.toString());
            }

            if (minDate != null) {
                sb.setLength(0);
                HtmlTools.formatDate(maxDate, sb, processContext,
                        calendarComponent, true);
                htmlWriter.writeAttribute("v:minDate", sb.toString());
            }

            if (twoDigitYearStart != null) {
                sb.setLength(0);
                HtmlTools.formatDate(maxDate, sb, processContext,
                        calendarComponent, true);
                htmlWriter.writeAttribute("v:twoDigitYearStart", sb.toString());
            }
        }

        int wdays = calendarComponent.getDisabledWeekDays(facesContext);
        if (wdays > 0) {
            htmlWriter.writeAttribute("v:disabledWeekDays", wdays);
        }

        int clientDatesStrategy = calendarComponent
                .getClientDatesStrategy(facesContext);
        if (clientDatesStrategy == IClientDatesStrategyCapability.MONTH_DATES_STRATEGY
                || clientDatesStrategy == IClientDatesStrategyCapability.YEAR_DATES_STRATEGY) {

            htmlWriter.writeAttribute("v:clientDatesStrategy",
                    clientDatesStrategy);
        }

    }

    protected static String convertDate(Calendar calendar, Date dates[],
            boolean onlyDate) {
        StringAppender sb = new StringAppender(dates.length * 10);

        appendDates(calendar, dates, sb, onlyDate);

        return sb.toString();
    }

    protected static String convertDate(Calendar calendar, Date date,
            boolean onlyDate) {
        StringAppender sb = new StringAppender(10);

        HtmlTools.formatDate(date, sb, calendar, onlyDate);

        return sb.toString();
    }

    public static void appendDate(Calendar calendar, Date date,
            StringAppender sb, boolean onlyDate) {

        HtmlTools.formatDate(date, sb, calendar, onlyDate);
    }

    public static void appendDates(Calendar calendar, Date dates[],
            StringAppender sb, boolean onlyDate) {

        for (int i = 0; i < dates.length; i++) {
            Date d = dates[i];
            if (d == null) {
                continue;
            }

            if (sb.length() > 0) {
                sb.append(',');
            }

            HtmlTools.formatDate(d, sb, calendar, onlyDate);
        }
    }

    public static void appendPeriods(Calendar calendar, Date dates[][],
            StringAppender sb, boolean onlyDate) {

        for (int i = 0; i < dates.length; i++) {
            Date d[] = dates[i];
            if (d == null) {
                continue;
            }

            if (sb.length() > 0) {
                sb.append(',');
            }

            HtmlTools.formatDate(d[0], sb, calendar, onlyDate);
            if (d.length < 2 || d[0].equals(d[1])) {
                continue;
            }

            sb.append(':');

            HtmlTools.formatDate(d[1], sb, calendar, onlyDate);
        }
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {

        boolean dayOnly = true;
        int maxResultNumber = -1;
        IFilterProperties filterProperties = null;

        return new CalendarDecorator(component, dayOnly, filterProperties,
                maxResultNumber);
    }

    protected void addRequiredJavaScriptClassNames(IHtmlWriter writer,
            Set classes) {
        super.addRequiredJavaScriptClassNames(writer, classes);

        FacesContext facesContext = writer.getComponentRenderContext()
                .getFacesContext();

        AbstractCalendarComponent calendarComponent = (AbstractCalendarComponent) writer
                .getComponentRenderContext().getComponent();

        IJavaScriptRenderContext javaScriptRenderContext = writer
                .getHtmlComponentRenderContext().getHtmlRenderContext()
                .getJavaScriptRenderContext();

        int clientDatesStrategy = calendarComponent
                .getClientDatesStrategy(facesContext);
        if (clientDatesStrategy == IClientDatesStrategyCapability.MONTH_DATES_STRATEGY
                || clientDatesStrategy == IClientDatesStrategyCapability.YEAR_DATES_STRATEGY) {

            javaScriptRenderContext.appendRequiredClasses(classes,
                    JavaScriptClasses.CALENDAR_OBJECT, "ajax");
        }
    }

}
