/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/10/13 18:04:38  oeuillot
 * Ajout de:
 * DateEntry
 * StyledMessage
 * MessageFieldSet
 * xxxxConverter
 * Adapter
 *
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
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.FacesException;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.TimeEntryComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.TimeConverter;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.CalendarTools;
import org.rcfaces.core.internal.tools.TimeTools;
import org.rcfaces.core.model.Time;
import org.rcfaces.renderkit.html.internal.util.ListenerTools.INameSpace;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TimeEntryRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.TIME_ENTRY;
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        encodeComponent(htmlWriter);

        super.encodeEnd(writer);
    }

    protected void encodeComponent(IHtmlWriter htmlWriter)
            throws WriterException {
        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();
        FacesContext facesContext = componentRenderContext.getFacesContext();

        TimeEntryComponent timeEntryComponent = (TimeEntryComponent) componentRenderContext
                .getComponent();

        htmlWriter.startElement("SPAN");

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        String timeFormat = timeEntryComponent.getTimeFormat(facesContext);
        if (timeFormat == null) {
            Locale locale = htmlWriter.getComponentRenderContext()
                    .getRenderContext().getProcessContext().getUserLocale();

            timeFormat = TimeTools.getDefaultTimeFormatPattern(locale);
        }

        if (timeFormat == null) {
            throw new FacesException("Invalid date format for component '"
                    + timeEntryComponent.getId() + "'.");
        }

        timeFormat = TimeTools.normalizeTimeFormat(componentRenderContext,
                timeFormat);

        htmlWriter.writeAttribute("v:timeFormat", timeFormat);

        if (timeEntryComponent.isAutoCompletion(facesContext)) {
            htmlWriter.writeAttribute("v:autoCOmpletion", "true");
        }

        int parametersCount = timeEntryComponent
                .getValidationParametersCount(facesContext);
        if (parametersCount > 0) {
            Map parameters = timeEntryComponent
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

        encodeSubComponents(htmlWriter, timeEntryComponent, timeFormat);

        htmlWriter.endElement("SPAN");

        htmlWriter.enableJavaScript();
    }

    protected void encodeSubComponents(IHtmlWriter htmlWriter,
            TimeEntryComponent timeEntryComponent, String dateFormat)
            throws WriterException {
        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();
        FacesContext facesContext = componentRenderContext.getFacesContext();

        Integer tabIndex = timeEntryComponent.getTabIndex(facesContext);
        String accessKey = timeEntryComponent.getAccessKey(facesContext);

        Calendar calendar = CalendarTools
                .getAttributesCalendar(timeEntryComponent);

        int minHour = -1;
        int maxHour = -1;
        int defaultHour = -1;
        int curHour = -1;

        int minMinute = 0;
        int maxMinute = 59;
        int defaultMinute = -1;
        int curMinute = -1;

        int minSecond = 0;
        int maxSecond = 59;
        int defaultSecond = -1;
        int curSecond = -1;

        int minMillis = 0;
        int maxMillis = 1000;
        int defaultMillis = -1;
        int curMillis = -1;

        Object currentValue = timeEntryComponent.getValue();
        if (currentValue instanceof Time) {
            Time curTime = (Time) currentValue;

            curHour = curTime.getHours();
            curMinute = curTime.getMinutes();
            curSecond = curTime.getSeconds();
            curMillis = curTime.getMillis();
        }

        Time defaultTime = timeEntryComponent.getDefaultTime(facesContext);
        if (defaultTime != null) {
            defaultHour = defaultTime.getHours();
            defaultMinute = defaultTime.getMinutes();
            defaultSecond = defaultTime.getSeconds();
            defaultMillis = defaultTime.getMillis();
        }

        Time minTime = timeEntryComponent.getMinTime(facesContext);
        Time maxTime = timeEntryComponent.getMaxTime(facesContext);

        if (minTime != null && maxTime != null) {
            if (minTime.compareTo(maxTime) > 0) {
                throw new FacesException("minTime (" + minTime
                        + ") is after maxTime (" + maxTime + ")");
            }

            minHour = minTime.getHours();
            maxHour = maxTime.getHours();

            if (minHour == maxHour) {
                minMinute = minTime.getMinutes();
                maxMinute = maxTime.getMinutes();

                if (minMinute == maxMinute) {
                    minSecond = minTime.getSeconds();
                    maxSecond = maxTime.getSeconds();

                    if (minSecond == maxSecond) {
                        minMillis = minTime.getMillis();
                        maxMillis = maxTime.getMillis();
                    }
                }
            }
        }

        boolean disabled = timeEntryComponent.isDisabled(facesContext);
        boolean readOnly = timeEntryComponent.isReadOnly(facesContext);

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
            case 'H':
                minValue = minHour;
                maxValue = maxHour;
                defaultValue = defaultHour;
                curValue = curHour;

                nb = 2;
                break;

            case 'm':
                nb = 2;
                minValue = minMinute;
                maxValue = maxMinute;
                defaultValue = defaultMinute;
                curValue = curMinute;
                break;

            case 's':
                nb = 2;
                minValue = minSecond;
                maxValue = maxSecond;
                defaultValue = defaultSecond;
                curValue = curSecond;
                break;

            case 'S':
                nb = 4;
                minValue = minMillis;
                maxValue = maxMillis;
                defaultValue = defaultMillis;
                curValue = curMillis;
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
        String subId = htmlWriter.getComponentRenderContext()
                .getComponentClientId()
                + NamingContainer.SEPARATOR_CHAR + ch;

        htmlWriter.startElement("INPUT");
        htmlWriter.writeType("TEXT");
        htmlWriter.writeMaxLength(length);
        htmlWriter.writeSize(length);
        htmlWriter.writeName(subId);
        htmlWriter.writeId(subId);
        htmlWriter.writeAttribute("v:type", String.valueOf(ch));
        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        StringAppender sb = new StringAppender(1 + length);
        sb.append('_');
        for (int i = 0; i < length; i++) {
            sb.append(ch);
        }

        htmlWriter.writeClass(getStyleClassName(componentRenderContext,
                componentRenderContext.getComponent(), sb.toString()));

        if (tabIndex != null) {
            htmlWriter.writeTabIndex(tabIndex.intValue());
        }
        if (accessKey != null) {
            htmlWriter.writeAccessKey(accessKey);
        }

        if (disabled) {
            htmlWriter.writeDisabled();
        }
        if (readOnly) {
            htmlWriter.writeReadOnly();
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

            htmlWriter.writeValue(s);
        }

        htmlWriter.endElement("INPUT");
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        TimeEntryComponent dateEntryComponent = (TimeEntryComponent) component;

        String dateValue = componentData.getStringProperty(Properties.VALUE);
        if (dateValue != null) {

            Time time = (Time) TimeConverter.SINGLETON.getAsObject(context
                    .getFacesContext(), component, dateValue);

            dateEntryComponent.setSubmittedValue(time);
        }
    }

    protected String getActionEventName(INameSpace nameSpace) {
        return nameSpace.getSelectionEventName();
    }
}
