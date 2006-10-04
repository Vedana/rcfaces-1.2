/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/10/04 12:31:42  oeuillot
 * Stabilisation
 *
 * Revision 1.2  2006/09/14 14:34:38  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.3  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.2  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.1  2006/06/28 17:48:28  oeuillot
 * Ajout de dateEntry
 * Ajout D'une constante g�n�rale de sp�cification de l'attributesLocale
 * Ajout d'un attribut <v:init attributesLocale='' />
 *
 */
package org.rcfaces.renderkit.html.internal;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.FacesException;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.DateEntryComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.CalendarTools;
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

        htmlWriter.startElement("SPAN");

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        if (dateEntryComponent.isShowCalendarOnFocus(facesContext)) {
            htmlWriter.writeAttribute("v:showOnFocus", "true");
        }

        if (dateEntryComponent.isAutoCompletion(facesContext)) {
            htmlWriter.writeAttribute("v:autoCOmpletion", "true");
        }

        String dateFormat = dateEntryComponent.getDateFormat(facesContext);
        if (dateFormat == null) {
            Locale locale = htmlWriter.getComponentRenderContext()
                    .getRenderContext().getProcessContext().getUserLocale();

            dateFormat = CalendarTools.getDateFormatPattern(locale,
                    DateFormat.SHORT);
        }

        if (dateFormat == null) {
            throw new FacesException("Invalid date format for component '"
                    + dateEntryComponent.getId() + "'.");
        }

        dateFormat = CalendarTools.normalizeDateFormat(componentRenderContext,
                dateFormat);

        htmlWriter.writeAttribute("v:dateFormat", dateFormat);

        int parametersCount = dateEntryComponent
                .getValidationParametersCount(facesContext);
        if (parametersCount > 0) {
            Map parameters = dateEntryComponent
                    .getValidationParametersMap(facesContext);

            StringAppender sb = new StringAppender(parametersCount * 128);
            for (Iterator it = parameters.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry = (Entry) it.next();

                String key = (String) entry.getKey();
                if (sb.length() > 0) {
                    sb.append(':');
                }
                if (key == null) {
                    key = "%";
                }
                EventsRenderer.appendCommand(sb, key);

                String value = (String) entry.getValue();
                if (sb.length() > 0) {
                    sb.append(':');
                }
                if (value == null) {
                    value = "%";
                }

                EventsRenderer.appendCommand(sb, value);
            }

            htmlWriter.writeAttribute("v:clientValidatorParams", sb.toString());
        }

        encodeSubComponents(htmlWriter, dateEntryComponent, dateFormat);

        htmlWriter.enableJavaScript();
    }

    protected void encodeAfterDecorator(IHtmlWriter htmlWriter,
            IComponentDecorator componentDecorator) throws WriterException {
        super.encodeAfterDecorator(htmlWriter, componentDecorator);

        htmlWriter.endElement("SPAN");
    }

    protected void encodeSubComponents(IHtmlWriter htmlWriter,
            DateEntryComponent dateEntryComponent, String dateFormat)
            throws WriterException {
        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();
        FacesContext facesContext = componentRenderContext.getFacesContext();

        Integer tabIndex = dateEntryComponent.getTabIndex(facesContext);
        String accessKey = dateEntryComponent.getAccessKey(facesContext);

        Calendar calendar = CalendarTools
                .getAttributesCalendar(dateEntryComponent);

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
            defaultDay = calendar.get(DateFormat.DATE_FIELD);
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

            switch (lastChar) {
            case 'y':
                minValue = minYear;
                maxValue = maxYear;
                defaultValue = defaultYear;
                curValue = curYear;

                if (nb == 2) {
                    if (minValue >= 0) {
                        minValue %= 100;
                    }
                    if (maxValue >= 0) {
                        maxValue %= 100;

                        if (minValue > maxValue) {
                            // Ca peut arriver dans des cas d'année sur 2
                            // chiffres !

                            maxValue += 100;
                        }
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

                writeSubInput(htmlWriter, accessKey, tabIndex, lastChar,
                        calendar, nb, minValue, maxValue, defaultValue,
                        curValue, disabled, readOnly, separators);
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

    protected void writeSubInput(IHtmlWriter htmlWriter, String accessKey,
            Integer tabIndex, char ch, Calendar calendar, int length,
            int minValue, int maxValue, int defaultValue, int curValue,
            boolean disabled, boolean readOnly, String separators)
            throws WriterException {
        String subId = htmlWriter.getComponentRenderContext().getComponentId()
                + NamingContainer.SEPARATOR_CHAR + ch;

        htmlWriter.startElement("INPUT");
        htmlWriter.writeAttribute("type", "TEXT");
        htmlWriter.writeAttribute("maxlength", length);
        htmlWriter.writeAttribute("size", length);
        htmlWriter.writeAttribute("name", subId);
        htmlWriter.writeAttribute("id", subId);
        htmlWriter.writeAttribute("v:type", String.valueOf(ch));
        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        StringAppender sb = new StringAppender(1 + length);
        sb.append('_');
        for (int i = 0; i < length; i++) {
            sb.append(ch);
        }

        htmlWriter.writeAttribute("class", getStyleClassName(
                componentRenderContext, componentRenderContext.getComponent(),
                sb.toString()));

        if (tabIndex != null) {
            htmlWriter.writeAttribute("tabIndex", tabIndex.intValue());
        }
        if (accessKey != null) {
            htmlWriter.writeAttribute("accessKey", accessKey);
        }

        if (disabled) {
            htmlWriter.writeAttribute("DISABLED");
        }
        if (readOnly) {
            htmlWriter.writeAttribute("READONLY");
        }

        if (minValue >= 0) {

            htmlWriter.writeAttribute("v:min", minValue);
        }

        if (maxValue >= 0) {
            htmlWriter.writeAttribute("v:max", maxValue);
        }

        if (defaultValue >= 0) {
            htmlWriter.writeAttribute("v:default", defaultValue);
        }

        if (separators != null && separators.length() > 0) {
            htmlWriter.writeAttribute("v:separators", separators);
        }

        if (curValue >= 0) {
            String s = String.valueOf(curValue);
            for (; s.length() < length;) {
                s = "0" + s;
            }

            htmlWriter.writeAttribute("value", s);
        }

        htmlWriter.endElement("INPUT");
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        DateEntryComponent dateEntryComponent = (DateEntryComponent) component;

        String dateValue = componentData.getStringProperty(Properties.VALUE);
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
