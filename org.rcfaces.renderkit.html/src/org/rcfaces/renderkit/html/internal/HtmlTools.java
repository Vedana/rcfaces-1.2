/*
 * $Id$
 * 
 * $Log$
 * Revision 1.4  2006/10/13 18:04:38  oeuillot
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
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.10  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.9  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.8  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.7  2006/03/15 13:53:04  oeuillot
 * Stabilisation
 * Ajout des bundles pour le javascript
 * R�organisation de l'arborescence de GridData qui n'herite plus de UIData
 *
 * Revision 1.6  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.5  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.4  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.3  2005/12/27 16:08:16  oeuillot
 * Gestion imageButtonWriter
 * Ajout de fa_images
 * Preparation de f_imageCombo
 *
 * Revision 1.2  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.1  2005/10/05 14:34:19  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IAccessKeyCapability;
import org.rcfaces.core.internal.codec.URLFormCodec;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.CalendarTools;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.internal.tools.FilterExpressionTools;
import org.rcfaces.core.model.IFilterProperties;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlTools {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(HtmlTools.class);

    private static final String ACCESSKEY_CLASSNAME = "f_accessKey";

    private static final String DEFAULT_URL_DECODER_CHARSET = "UTF8";

    public static final String LIST_SEPARATORS = "\u0001";

    public static final String ALL_VALUE = "\u0007all";

    private static final char LF_CHARACTER = '\n';

    private static final char NO_ACCESS_KEY = (char) -1;

    private static final char NULL_TYPE = 'L';

    private static final char TRUE_TYPE = 'T';

    private static final char FALSE_TYPE = 'F';

    private static final char STRING_TYPE = 'S';

    private static final char DATE_TYPE = 'D';

    private static final char ZERO_TYPE = '0';

    private static final Number NUMBER_0 = new Double(0);

    public static Map decodeParametersToMap(UIComponent component,
            String values, char sep, Object noValue) {
        if (values == null || values.length() < 1) {
            return Collections.EMPTY_MAP;
        }

        char cs[] = values.toCharArray();

        Map properties = new HashMap((cs.length / 16) + 1);
        for (int i = 0; i < cs.length;) {
            int nameStart = i;
            int nameEnd = 0;

            char c = 0;

            for (; i < cs.length; i++) {
                c = cs[i];

                if (c != '=') {
                    continue;
                }

                nameEnd = i;
                break;
            }
            if (i == cs.length) {
                throw createFormatException("EOF", i, values);
            }

            i++;
            if (i == cs.length) {
                properties.put(values.substring(nameStart, nameEnd), noValue);
                // System.out.println(">>>decode '"+values+"' => "+properties);
                return properties;
            }

            int valueStart = i;
            int valueEnd = 0;
            for (; i < cs.length; i++) {
                c = cs[i];

                if (c != sep) {
                    continue;
                }

                valueEnd = i;
                break;
            }

            if (i == cs.length) {
                valueEnd = i;

            } else if (c == sep) {
                i++;
            }

            Object vs;

            if (valueStart >= valueEnd) {
                vs = "";

            } else {
                char type = values.charAt(valueStart++);
                switch (type) {
                case STRING_TYPE:
                    vs = URLFormCodec.decodeURL(cs, valueStart, valueEnd);
                    break;

                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    if (valueStart == valueEnd) {
                        type -= '0';
                        if (type == 0) {
                            vs = NUMBER_0;
                        } else {
                            vs = new Double(type);
                        }
                        break;
                    }

                    // J'ai pas oublié le break !

                case '-':
                    vs = Double.valueOf(URLFormCodec.decodeURL(cs,
                            valueStart - 1, valueEnd));

                    break;

                case TRUE_TYPE:
                    vs = Boolean.TRUE;
                    break;

                case FALSE_TYPE:
                    vs = Boolean.FALSE;
                    break;

                case NULL_TYPE:
                    vs = null;
                    break;

                case DATE_TYPE:
                    String date = URLFormCodec.decodeURL(cs, valueStart,
                            valueEnd);

                    vs = CalendarTools.parseDate(component, date);
                    break;

                default:
                    throw createFormatException("Unknown serialized type '"
                            + type + "'.", i, values);
                }
            }

            properties.put(values.substring(nameStart, nameEnd), vs);
        }

        // System.out.println(">>>decode '"+values+"' => "+properties);

        return properties;
    }

    private static FacesException createFormatException(String message, int i,
            String datas) {
        return new FacesException("Bad format of rcfaces serialized datas ! ("
                + message + ": pos=" + i + " data='" + datas + "')");
    }

    public static String encodeParametersFromMap(Map map, char sep) {
        if (map.isEmpty()) {
            return "";
        }

        StringAppender sb = new StringAppender(map.size() * 24);

        for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();

            String key = (String) entry.getKey();
            Object value = entry.getValue();

            if (sb.length() > 0) {
                sb.append(sep);
            }

            URLFormCodec.encode(sb, key);
            sb.append('=');

            if (value == null) {
                sb.append(NULL_TYPE);
                continue;
            }

            if (value.equals("")) {
                // String vide !
                continue;
            }

            if (value instanceof Boolean) {
                if (((Boolean) value).booleanValue()) {
                    sb.append(TRUE_TYPE);
                    continue;
                }

                sb.append(FALSE_TYPE);
                continue;
            }

            if ((value instanceof Number)
                    && ((Number) value).doubleValue() == 0.0) {
                sb.append(ZERO_TYPE);
                continue;
            }

            if (value instanceof Number) {
                // sb.append(NUMBER_TYPE);
                // Pas necessaire !

                value = ((Number) value).toString();

            } else {
                sb.append(STRING_TYPE);
            }

            URLFormCodec.encode(sb, (String) value);
        }

        return sb.toString();
    }

    public static void appendData(StringAppender datas, String key, String value) {
        URLFormCodec.encode(datas, key);
        datas.append('=');
        URLFormCodec.encode(datas, value);
    }

    public static IFilterProperties decodeFilterExpression(
            UIComponent component, String filterExpression) {

        Map filter = HtmlTools.decodeParametersToMap(component,
                filterExpression, '&', null);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Decode filter expression to " + filter);
        }

        return FilterExpressionTools.create(filter);
    }

    public static String encodeFilterExpression(IFilterProperties filterMap) {

        Map map = filterMap.toMap();

        String encode = HtmlTools.encodeParametersFromMap(map, '&');

        if (LOG.isDebugEnabled()) {
            LOG.debug("Encode filter expression to " + encode);
        }

        return encode;
    }

    public static final boolean writeSpanAccessKey(IHtmlWriter writer,
            IAccessKeyCapability accessKeyCapability, String text,
            boolean escapeLF) throws WriterException {
        if (text == null || text.length() < 1) {
            return false;
        }

        String accessKey = accessKeyCapability.getAccessKey();
        if (accessKey == null || accessKey.length() < 1) {
            if (escapeLF) {
                writeAndEscapeLF(writer, text, NO_ACCESS_KEY);
                return false;
            }

            writer.writeText(text);

            return false;
        }

        if (escapeLF) {
            writeAndEscapeLF(writer, text, accessKey.charAt(0));
            return false;
        }

        int idx = text.toLowerCase().indexOf(accessKey.toLowerCase());
        if (idx < 0) {
            writer.writeText(text);
            return false;
        }

        if (idx > 0) {
            String txt = text.substring(0, idx);
            writer.writeText(txt);
        }

        int end = idx + accessKey.length();

        writer.startElement("U");
        writer.writeAttribute("class", ACCESSKEY_CLASSNAME);
        writer.writeText(text.substring(idx, end));
        writer.endElement("U");

        if (end < text.length()) {
            String txt = text.substring(end);

            writer.writeText(txt);
        }

        return true;
    }

    private static void writeAndEscapeLF(IHtmlWriter writer, String text,
            char accessKey) throws WriterException {
        char chs[] = text.toCharArray();
        int p = 0;

        char ak = accessKey;
        if (ak != NO_ACCESS_KEY) {
            ak = Character.toLowerCase(accessKey);
        }

        for (;;) {
            int old = p;
            char c = 0;
            for (; p < chs.length; p++) {
                c = chs[p];
                if (c == LF_CHARACTER) {
                    break;
                }
                if (ak == NO_ACCESS_KEY) {
                    continue;
                }
                if (Character.toLowerCase(c) == ak) {
                    break;
                }
            }
            if (old < p) {
                writer.write(chs, old, p - old);
            }

            if (p == chs.length) {
                break;
            }

            if (c == LF_CHARACTER) {
                writer.startElement("BR");
                writer.endElement("BR");

            } else {
                writer.startElement("U");
                writer.writeAttribute("class", ACCESSKEY_CLASSNAME);
                writer.write(c);
                writer.endElement("U");
                ak = NO_ACCESS_KEY;
            }

            p++;
        }
    }

    public static IHtmlWriter writeClientData(IHtmlWriter writer, Map values)
            throws WriterException {

        StringAppender datas = new StringAppender(values.size() * 64);
        for (Iterator it = values.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();

            String key = (String) entry.getKey();
            if (key == null || key.length() < 1) {
                continue;
            }

            String value = (String) entry.getValue();
            if (value == null) {
                continue;
            }

            if (datas.length() > 0) {
                datas.append(',');
            }

            HtmlTools.appendData(datas, key, value);
        }

        writer.writeAttribute("v:data", datas.toString());

        return writer;
    }

    public static String replaceSeparator(String id, String separatorChar) {
        int idx = id.indexOf(NamingContainer.SEPARATOR_CHAR);
        if (idx < 0) {
            return id;
        }

        StringAppender sa = new StringAppender(id.length()
                + (separatorChar.length() - 1) * 4);

        char chs[] = id.toCharArray();
        sa.append(chs, 0, idx);
        sa.append(separatorChar);

        for (idx++; idx < chs.length; idx++) {
            char ch = chs[idx];

            if (ch != NamingContainer.SEPARATOR_CHAR) {
                sa.append(ch);
                continue;
            }

            sa.append(separatorChar);
        }

        return sa.toString();
    }

    public static String convertToNamingSeparator(String id, String separator) {
        int idx = id.indexOf(separator);
        if (idx < 0) {
            return id;
        }

        StringAppender sa = new StringAppender(id.length()
                + (separator.length() - 1) * 4);

        sa.append(id, 0, idx);
        sa.append(NamingContainer.SEPARATOR_CHAR);
        idx += separator.length();

        for (;;) {
            int newIdx = id.indexOf(separator, idx);

            if (newIdx < 0) {
                sa.append(id, idx, id.length() - idx);
                break;
            }

            sa.append(NamingContainer.SEPARATOR_CHAR);
            idx = newIdx + separator.length();
        }

        return sa.toString();
    }

    public static UIComponent getForComponent(FacesContext context,
            String forComponent, UIComponent component) {

        if (Constants.PARAMETERIZED_SEPARATOR_SUPPORT) {
            IHtmlProcessContext processContext = HtmlProcessContextImpl
                    .getHtmlProcessContext(context);

            String separator = processContext.getNamingSeparator();
            if (separator != null) {
                forComponent = convertToNamingSeparator(forComponent, separator);
            }
        }

        return ComponentTools.getForComponent(context, forComponent, component);
    }

    public static String computeGroupName(IHtmlProcessContext processContext,
            UIComponent component, String groupName) {

        if (Constants.CLIENT_GROUP_NAME_SUPPORT == false) {
            return groupName;
        }

        // Recherche un Container
        for (; component != null; component = component.getParent()) {
            if (component instanceof NamingContainer) {
                break;
            }
        }

        StringAppender prefixClientId = new StringAppender(64);

        if (component == null) {
            LOG.error("No naming container for component '" + component.getId()
                    + "'.");
        } else {
            NamingContainer namingContainer = (NamingContainer) component;

            String parentClientId = ((UIComponent) namingContainer)
                    .getClientId(processContext.getFacesContext());
            if (parentClientId != null) {
                prefixClientId.append(parentClientId);
            }
        }

        String separator = processContext.getNamingSeparator();
        if (separator != null) {
            prefixClientId.append(separator);
        } else {
            prefixClientId.append(NamingContainer.SEPARATOR_CHAR);
        }

        prefixClientId.append(groupName);

        String convertedGroupName = prefixClientId.toString();
        if (LOG.isDebugEnabled()) {
            LOG.debug("Convert groupName '" + groupName + "' to '"
                    + convertedGroupName + "'.");
        }

        return convertedGroupName;
    }
}
