/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.util;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.faces.FacesException;

import org.rcfaces.core.component.capability.IOutlinedLabelCapability;
import org.rcfaces.core.component.capability.IOutlinedLabelCapability.Method;
import org.rcfaces.core.internal.lang.StringAppender;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class OutlinedLabelTools {

    public static EnumSet<IOutlinedLabelCapability.Method> normalize(
            String method) {

        StringTokenizer st = new StringTokenizer(method, ",; ");

        Set<IOutlinedLabelCapability.Method> ms = new HashSet<IOutlinedLabelCapability.Method>(
                8);

        for (; st.hasMoreTokens();) {
            String token = st.nextToken().toLowerCase();

            if ("ignorecase".equals(token) || "ignore-case".equals(token)) {
                ms.add(Method.IgnoreCase);
                continue;
            }
            if ("ignoreaccents".equals(token) || "ignore-accents".equals(token)) {
                ms.add(Method.IgnoreAccents);
                continue;
            }
            if ("multiple".equals(token)) {
                ms.add(Method.Multiple);
                continue;
            }
            if ("startswith".equals(token)) {
                ms.add(Method.StartsWith);
                ms.remove(Method.FullText);
                ms.remove(Method.WordOnly);
                continue;
            }
            if ("word".equals(token) || "eachword".equals(token)
                    || "each-word".equals(token)) {
                ms.remove(Method.StartsWith);
                ms.remove(Method.FullText);
                ms.add(Method.WordOnly);
                continue;
            }
            if ("fulltext".equals(token)) {
                ms.remove(Method.StartsWith);
                ms.add(Method.FullText);
                ms.remove(Method.WordOnly);
                continue;
            }

            throw new FacesException("Unknown outlined label method '" + token
                    + "'");
        }

        if (ms.isEmpty()) {
            return null;
        }

        EnumSet<IOutlinedLabelCapability.Method> ms2 = EnumSet.copyOf(ms);

        return ms2;
    }

    public static String format(EnumSet<IOutlinedLabelCapability.Method> ms) {

        StringAppender sa = new StringAppender(ms.size() * 3);

        for (IOutlinedLabelCapability.Method m : ms) {
            if (sa.length() > 0) {
                sa.append(',');
            }
            switch (m) {
            case FullText:
                sa.append("ft");
                break;
            case IgnoreAccents:
                sa.append("ia");
                break;
            case IgnoreCase:
                sa.append("ic");
                break;
            case Multiple:
                sa.append("mt");
                break;
            case StartsWith:
                sa.append("sw");
                break;
            case WordOnly:
                sa.append("ew");
                break;
            }
        }

        return sa.toString();

    }
}
