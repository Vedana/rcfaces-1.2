/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.DateEntryComponent;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.CalendarTools;
import org.rcfaces.renderkit.html.internal.AbstractCalendarRenderer;
import org.rcfaces.renderkit.html.internal.AbstractCompositeRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.util.ListenerTools.INameSpace;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DateEntryRenderer extends AbstractCalendarRenderer {
    private static final String REVISION = "$Revision$";

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.DATE_ENTRY;
    }

    protected boolean hasComponenDecoratorSupport() {
        return true;
    }

    protected void encodeBeforeDecorator(IHtmlWriter htmlWriter,
            IComponentDecorator componentDecorator) throws WriterException {

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();
        FacesContext facesContext = componentRenderContext.getFacesContext();

        DateEntryComponent dateEntryComponent = (DateEntryComponent) componentRenderContext
                .getComponent();

        Calendar calendar = CalendarTools
                .getAttributesCalendar(dateEntryComponent);

        htmlWriter.startElement("SPAN");

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        if (dateEntryComponent.isShowCalendarOnFocus(facesContext)) {
            htmlWriter.writeAttribute("v:showOnFocus", "true");
        }

        String dateFormat = dateEntryComponent.getDateFormat(facesContext);
        if (dateFormat == null) {
            Locale locale = htmlWriter.getComponentRenderContext()
                    .getRenderContext().getProcessContext().getUserLocale();

            dateFormat = CalendarTools.getDefaultPattern(locale);
        }

        if (dateFormat == null) {
            throw new FacesException("Invalid date format for component '"
                    + dateEntryComponent.getId() + "'.");
        }

        dateFormat = CalendarTools.normalizeFormat(componentRenderContext,
                dateFormat);

        // htmlWriter.writeAttribute("v:dateFormat", dateFormat);

        Date minDate = dateEntryComponent.getMinDate(facesContext);
        if (minDate != null) {
            htmlWriter.writeAttribute("v:minDate", convertDate(calendar,
                    minDate, true));
        }

        Date maxDate = dateEntryComponent.getMaxDate(facesContext);
        if (maxDate != null) {
            htmlWriter.writeAttribute("v:maxDate", convertDate(calendar,
                    maxDate, true));
        }

        AbstractCompositeRenderer.writeClientValidatorParams(htmlWriter);

        encodeSubComponents(htmlWriter, dateEntryComponent, dateFormat,
                calendar);

        htmlWriter.enableJavaScript();
    }

    protected void encodeAfterDecorator(IHtmlWriter htmlWriter,
            IComponentDecorator componentDecorator) throws WriterException {
        super.encodeAfterDecorator(htmlWriter, componentDecorator);

        htmlWriter.endElement("SPAN");
    }

    protected void encodeSubComponents(IHtmlWriter htmlWriter,
            DateEntryComponent dateEntryComponent, String dateFormat,
            Calendar calendar) throws WriterException {
        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();
        FacesContext facesContext = componentRenderContext.getFacesContext();

        Integer tabIndex = dateEntryComponent.getTabIndex(facesContext);
        String accessKey = dateEntryComponent.getAccessKey(facesContext);

        int minYear = -1;
        int maxYear = -1;
        int defaultYear = -1;
        int curYear = -1;

        int minMonth = 1;
        int maxMonth = 12;
        int defaultMonth = -1;
        int curMonth = -1;

        int minDay = 1;
        int maxDay = 31;
        int defaultDay = -1;
        int curDay = -1;

        Object currentValue = dateEntryComponent.getValue();
        if (currentValue instanceof Date) {
            Date curDate = (Date) currentValue;

            calendar.setTime(curDate);
            curYear = calendar.get(Calendar.YEAR);
            curMonth = calendar.get(Calendar.MONTH) + 1;
            curDay = calendar.get(Calendar.DAY_OF_MONTH);
        }

        Date defaultDate = dateEntryComponent.getDefaultDate(facesContext);
        if (defaultDate != null) {
            calendar.setTime(defaultDate);
            defaultYear = calendar.get(Calendar.YEAR);
            defaultMonth = calendar.get(Calendar.MONTH) + 1;
            defaultDay = calendar.get(Calendar.DAY_OF_MONTH);
        }

        Date minDate = dateEntryComponent.getMinDate(facesContext);
        Date maxDate = dateEntryComponent.getMaxDate(facesContext);

        if (minDate != null && maxDate != null) {
            if (minDate.compareTo(maxDate) > 0) {
                throw new FacesException("minDate (" + minDate
                        + ") is after maxDate (" + maxDate + ")");
            }
        }

        if (minDate != null) {
            calendar.setTime(minDate);
            minYear = calendar.get(Calendar.YEAR);
        }
        if (maxDate != null) {
            calendar.setTime(maxDate);
            maxYear = calendar.get(Calendar.YEAR);
        }

        if (minDate != null && maxDate != null) {
            // On peut limiter le nombre du jour ou du mois si les années sont
            // identiques !
            if (maxYear == minYear) {
                calendar.setTime(minDate);
                minMonth = calendar.get(Calendar.MONTH) + 1;

                calendar.setTime(maxDate);
                maxMonth = calendar.get(Calendar.MONTH) + 1;

                // On peut limiter le nombre du jour si les années et les mois
                // sont identiques !
                if (minMonth == maxMonth) {
                    calendar.setTime(minDate);
                    minDay = calendar.get(Calendar.DAY_OF_MONTH);

                    calendar.setTime(maxDate);
                    maxDay = calendar.get(Calendar.DAY_OF_MONTH);
                }
            }
        }

        boolean disabled = dateEntryComponent.isDisabled(facesContext);
        boolean readOnly = dateEntryComponent.isReadOnly(facesContext);
        boolean defaultAutoComplete = dateEntryComponent
                .isAutoCompletion(facesContext);

        StringAppender sb = new StringAppender(128);

        char chs[] = dateFormat.toCharArray();

        int nbSub = 0;
        char lastChar = 0;
        int nb = 0;
        for (int i = 0; i <= chs.length; i++) {
            char c = 0;
            if (i < chs.length) {
                c = chs[i];

                if (c == lastChar) {
                    nb++;
                    continue;
                }
                if (lastChar == 0) {
                    lastChar = c;
                    nb = 1;
                    continue;
                }
            }

            // C'est le cas si la fin etait quotée !
            if (nb < 1) {
                break;
            }

            int minValue = -1;
            int maxValue = -1;
            int curValue = -1;
            int defaultValue = -1;
            boolean cycle = false;
            boolean autoComplete = defaultAutoComplete;

            switch (lastChar) {
            case 'y':
                minValue = minYear;
                maxValue = maxYear;
                defaultValue = defaultYear;
                curValue = curYear;
                autoComplete = false;

                if (nb == 2) {
                    cycle = true;

                    if (minValue < maxValue && minValue / 100 == maxValue / 100) {
                        minValue %= 100;
                        maxValue %= 100;
                    } else {
                        minValue = 0;
                        maxValue = 99;
                    }
                    if (defaultValue >= 0) {
                        defaultValue %= 100;
                    }
                    if (curValue >= 0) {
                        curValue %= 100;
                    }

                } else {
                    nb = 4;
                }
                break;

            case 'M':
                nb = 2;
                cycle = true;
                minValue = minMonth;
                maxValue = maxMonth;
                defaultValue = defaultMonth;
                curValue = curMonth;
                break;

            case 'd':
                nb = 2;
                minValue = minDay;
                maxValue = maxDay;
                defaultValue = defaultDay;
                curValue = curDay;
                cycle = true;
                break;

            default:
                for (; nb > 0; nb--) {
                    sb.append(lastChar);
                }
            }

            if (nb > 0) {
                String separators = null;
                int sbLength = sb.length();

                if (sbLength > 0) {
                    if (nbSub > 0) {
                        if (sbLength < 2) {
                            separators = sb.toString();

                        } else {
                            char sb2[] = new char[sbLength];
                            int idx2 = 0;
                            next_separator: for (int j = 0; j < sbLength; j++) {
                                char sep = sb.charAt(j);

                                for (int k = 0; k < idx2; k++) {
                                    if (sb2[k] != sep) {
                                        continue;
                                    }

                                    continue next_separator;
                                }

                                sb2[idx2++] = sep;
                            }

                            separators = new String(sb2, 0, idx2);
                        }
                    }

                    // htmlWriter.startElement("SPAN");
                    htmlWriter.writeText(sb.toString());
                    // htmlWriter.endElement("SPAN");

                    sb.setLength(0);
                }

                String sCurValue = null;
                if (curValue >= 0) {
                    StringAppender s = new StringAppender(String
                            .valueOf(curValue), nb);
                    s.insert(0, '0', nb - s.length());

                    sCurValue = s.toString();
                }

                Map attributes = new HashMap(8);
                if (minValue >= 0) {
                    attributes.put("v:min", String.valueOf(minValue));
                }

                if (maxValue >= 0) {
                    attributes.put("v:max", String.valueOf(maxValue));
                }

                if (defaultValue >= 0) {
                    attributes.put("v:defaultValue", String
                            .valueOf(defaultValue));
                }

                if (separators != null && separators.length() > 0) {
                    attributes.put("v:separators", separators);
                }

                if (cycle) {
                    attributes.put("v:cycle", "true");
                }

                if (autoComplete) {
                    attributes.put("v:auto", "true");
                }

                AbstractCompositeRenderer.writeSubInput(htmlWriter,
                        getSubStyleClassName(htmlWriter, lastChar, nb),
                        accessKey, tabIndex, lastChar, nb, sCurValue, disabled,
                        readOnly, false, attributes);
                accessKey = null; // Un seul accessKey !
                nbSub++;
            }

            if (c == 0) {
                break;
            }

            if (c != '\'') {
                lastChar = c;
                nb = 1;
                continue;
            }

            for (i++; i < chs.length; i++) {
                c = chs[i];

                if (c != '\'') {
                    sb.append(c);
                    continue;
                }

                // double quote ???
                if (i + 1 < chs.length && chs[i + 1] == c) {
                    sb.append(c);
                    i++;
                    continue;
                }
                break;
            }

            nb = 0;
            lastChar = 0;
        }

        if (sb.length() > 0) {
            htmlWriter.writeText(sb.toString());
        }
    }

    protected String getSubStyleClassName(IHtmlWriter htmlWriter, char ch,
            int length) {

        StringAppender sb = new StringAppender(1 + length);
        sb.append('_');
        sb.append(ch, length);

        return getMainStyleClassName() + sb.toString();
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        DateEntryComponent dateEntryComponent = (DateEntryComponent) component;

        String dateValue = componentData.getStringProperty("value");
        if (dateValue != null) {
            Calendar calendar = CalendarTools
                    .getAttributesCalendar(dateEntryComponent);

            Date date = parseDate(calendar, dateValue, true);

            dateEntryComponent.setSubmittedValue(date);
        }
    }

    protected String getActionEventName(INameSpace nameSpace) {
        return nameSpace.getSelectionEventName();
    }
}
