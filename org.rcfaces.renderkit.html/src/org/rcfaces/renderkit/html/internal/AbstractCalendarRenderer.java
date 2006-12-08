/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.AbstractCalendarComponent;
import org.rcfaces.core.component.capability.IClientDatesStrategyCapability;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.CalendarTools;
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

    protected void writeCalendarAttributes(IHtmlWriter htmlWriter)
            throws WriterException {

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        AbstractCalendarComponent calendarComponent = (AbstractCalendarComponent) componentRenderContext
                .getComponent();

        Date maxDate = calendarComponent.getMaxDate(facesContext);
        Date minDate = calendarComponent.getMinDate(facesContext);
        Date twoDigitYearStart = calendarComponent
                .getTwoDigitYearStart(facesContext);

        if (maxDate != null || minDate != null || twoDigitYearStart != null) {
            Calendar calendar = CalendarTools.getAttributesCalendar(
                    componentRenderContext.getRenderContext()
                            .getProcessContext(), calendarComponent);

            StringAppender sb = new StringAppender(12);

            if (maxDate != null) {
                sb.setLength(0);
                appendDate(calendar, maxDate, sb, true);
                htmlWriter.writeAttribute("v:maxDate", sb.toString());
            }

            if (minDate != null) {
                sb.setLength(0);
                appendDate(calendar, minDate, sb, true);
                htmlWriter.writeAttribute("v:minDate", sb.toString());
            }

            if (twoDigitYearStart != null) {
                sb.setLength(0);
                appendDate(calendar, twoDigitYearStart, sb, true);
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

        appendDate(calendar, date, sb, onlyDate);

        return sb.toString();
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

            appendDate(calendar, d, sb, onlyDate);
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

            appendDate(calendar, d[0], sb, onlyDate);
            if (d.length < 2 || d[0].equals(d[1])) {
                continue;
            }

            sb.append(':');

            appendDate(calendar, d[1], sb, onlyDate);
        }
    }

    public static void appendDate(Calendar calendar, Date date,
            StringAppender sb, boolean onlyDate) {

        calendar.setTime(date);

        int day = calendar.get(Calendar.DAY_OF_MONTH) - 1;
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        if (onlyDate == false) {
            int second = calendar.get(Calendar.SECOND);
            int minute = calendar.get(Calendar.MINUTE);
            int hour = calendar.get(Calendar.HOUR);

            if (second > 0) {
                appendNumber(
                        sb,
                        second
                                + 60
                                * (minute + 60 * (hour + 24 * (day + 31 * (month + 12 * (year))))));
                return;
            }
            if (minute > 0) {
                sb.append('N');
                appendNumber(sb, minute + 60
                        * (hour + 24 * (day + 31 * (month + 12 * (year)))));
                return;
            }

            if (hour > 0) {
                sb.append('H');
                appendNumber(sb, hour + 24 * (day + 31 * (month + 12 * (year))));
                return;
            }
        }

        if (day > 1) {

            if (onlyDate == false) {
                sb.append('D');
            }
            appendNumber(sb, day + 31 * (month + 12 * (year)));
            return;
        }

        if (month > 0) {
            sb.append('M');
            appendNumber(sb, month + 12 * (year));
            return;
        }

        sb.append('Y');
        appendNumber(sb, year);
    }

    private static void appendNumber(StringAppender sb, int i) {
        sb.append(Integer.toString(i, 32));
    }

    protected static Date parseDate(Calendar calendar, String date,
            boolean onlyDate) {

        if (date.length() < 1) {
            return null;
        }

        char command = date.charAt(0);
        if (command >= 'A' && command <= 'Z') {
            date = date.substring(1);
        } else if (onlyDate) {
            command = 'D';
        } else {
            command = 'S';
        }

        int value = Integer.parseInt(date, 32);
        switch (command) {
        case 'Y':
            calendar.set(value, 0, 1);
            return calendar.getTime();

        case 'M':
            int year = value / 12;
            value %= 12;
            calendar.set(year, value, 1);
            return calendar.getTime();

        case 'D':
            int month = value / 31;
            year = month / 12;
            month %= 12;
            value %= 31;
            calendar.set(year, month, value + 1);
            return calendar.getTime();
        }

        throw new FacesException("Unsupported command '" + command
                + "' for date expression '" + date + "'.");
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {

        boolean dayOnly = true;
        int maxResultNumber = -1;
        IFilterProperties filterProperties = null;

        AbstractCalendarComponent calendarComponent = (AbstractCalendarComponent) component;

        Calendar calendar = CalendarTools.getAttributesCalendar(null,
                calendarComponent);

        return new CalendarDecorator(component, calendar, dayOnly,
                filterProperties, maxResultNumber);
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
