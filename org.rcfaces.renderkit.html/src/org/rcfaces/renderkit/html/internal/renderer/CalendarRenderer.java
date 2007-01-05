/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.util.Calendar;
import java.util.Date;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import org.rcfaces.core.component.CalendarComponent;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.capability.ICalendarModeCapability;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.CalendarTools;
import org.rcfaces.renderkit.html.internal.AbstractCalendarRenderer;
import org.rcfaces.renderkit.html.internal.ICssWriter;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CalendarRenderer extends AbstractCalendarRenderer {
    private static final String REVISION = "$Revision$";

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.CALENDAR;
    }

    protected boolean hasComponenDecoratorSupport() {
        return true;
    }

    protected void encodeBeforeDecorator(IHtmlWriter htmlWriter,
            IComponentDecorator componentDecorator) throws WriterException {

        htmlWriter.startElement("DIV");

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        Calendar componentCalendar = CalendarTools.getCalendar(htmlWriter);
        writeCalendarAttributes(htmlWriter, componentCalendar);

        htmlWriter.enableJavaScript();

        super.encodeBeforeDecorator(htmlWriter, componentDecorator);
    }

    protected void encodeAfterDecorator(IHtmlWriter htmlWriter,
            IComponentDecorator componentDecorator) throws WriterException {
        super.encodeAfterDecorator(htmlWriter, componentDecorator);

        htmlWriter.endElement("DIV");
    }

    protected void writeCustomCss(IHtmlWriter writer, ICssWriter cssWriter) {
        super.writeCustomCss(writer, cssWriter);

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        UIComponent component = componentRenderContext.getComponent();
        if (component instanceof IBorderCapability) {
            IBorderCapability borderCapability = (IBorderCapability) component;

            if (borderCapability.isBorder() == false) {
                cssWriter.writeBorderStyle("none");
            }
        }
    }

    protected void writeCalendarAttributes(IHtmlWriter htmlWriter,
            Calendar componentCalendar) throws WriterException {
        super.writeCalendarAttributes(htmlWriter, componentCalendar);

        writeCalendarMode(htmlWriter, componentCalendar);
    }

    protected void writeCalendarMode(IHtmlWriter htmlWriter,
            Calendar componentCalendar) throws WriterException {
        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        CalendarComponent calendarComponent = (CalendarComponent) componentRenderContext
                .getComponent();

        int mode = calendarComponent.getMode();
        if (mode != 0) {
            htmlWriter.writeAttribute("v:mode", mode);
        }

        Object value = calendarComponent.getValue();
        String s_value = null;

        StringAppender sb = new StringAppender(12);
        switch (mode) {
        case ICalendarModeCapability.CALENDAR_MODE_DATE:
            if (value instanceof Date) {
                Date d = (Date) value;

                sb.setLength(0);
                appendDate(componentCalendar, d, sb, true);

                s_value = sb.toString();

            } else if (value != null) {
                throw new FacesException("Value for calendarMode " + mode
                        + " must be a Date object.");
            }

            break;

        case ICalendarModeCapability.CALENDAR_MODE_PERIOD:
            if (value instanceof Date[][]) {
                Date ds[][] = (Date[][]) value;

                if (ds.length > 1) {
                    throw new FacesException(
                            "Only one period is accepted for calendarMode '"
                                    + mode + "'.");
                }

                if (ds.length > 0) {
                    s_value = convertDate(componentCalendar, ds[0], true);
                }

            } else if (value != null) {
                throw new FacesException("Value for calendarMode " + mode
                        + " must be an array of Date object.");
            }

            break;

        case ICalendarModeCapability.CALENDAR_MODE_PERIODS:
            if (value instanceof Date[][]) {
                // Date ds[][] = (Date[][]) value;

                // String s = convertDate(calendar, ds);
            }
            break;

        default:
            throw new FacesException("Unknown calendarMode ! (" + mode + ")");
        }

        if (s_value != null) {
            htmlWriter.writeAttribute("v:value", s_value);
        }
    }

}
