/*
 * $Id$
 */
package org.rcfaces.core.internal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.lang.StringAppender;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class StringList {

    private static final Log LOG = LogFactory.getLog(StringList.class);

    private static final char STRING_DEFAULT_SEPARATOR = ',';

    private static final char STRING_DEFAULT_ESCAPE = '\\';

    public static String[] parseTokensList(String value) {

        if (value == null) {
            return null;
        }
        if (value.length() < 1) {
            return new String[] { /*value*/ };
        }

        char chs[] = value.toCharArray();

        List l = null;

        StringAppender sa = new StringAppender(32);

        for (int i = 0; i < chs.length; i++) {
            char c = chs[i];

            if (c == STRING_DEFAULT_ESCAPE) {
                i++;
                if (i == chs.length) {
                    break;
                }
                sa.append(chs[i]);
                continue;
            }

            if (c == STRING_DEFAULT_SEPARATOR) {
                if (l == null) {
                    l = new ArrayList(value.length() / 8);
                }
                l.add(sa.toString().trim());

                sa.setLength(0);
                continue;
            }

            sa.append(chs[i]);
        }

        if (l == null) {
            return new String[] { sa.toString().trim() };
        }

        l.add(sa.toString());

        return (String[]) l.toArray(new String[l.size()]);
    }

    public static int countTokens(String value) {

        char chs[] = value.toCharArray();

        int cnt = 1;
        for (int i = 0; i < chs.length; i++) {
            char c = chs[i];

            if (c == STRING_DEFAULT_ESCAPE) {
                i++; // On ignore le suivant
                continue;
            }

            if (c == STRING_DEFAULT_SEPARATOR) {
                cnt++;
                continue;
            }
        }

        return cnt;
    }

    public static String getFirstToken(String value) {

        if (value == null || value.length() < 1) {
            return value;
        }

        StringAppender sa = new StringAppender(32);

        char chs[] = value.toCharArray();

        for (int i = 0; i < chs.length; i++) {
            char c = chs[i];

            if (c == STRING_DEFAULT_ESCAPE) {
                i++;
                if (i == chs.length) {
                    break;
                }
                sa.append(chs[i]);
                continue;
            }

            if (c == STRING_DEFAULT_SEPARATOR) {
                break;
            }

            sa.append(chs[i]);
        }

        return sa.toString().trim();
    }

    public static String joinTokens(Collection collection) {
        StringAppender sa = new StringAppender(collection.size() * 16);

        for (Iterator it = collection.iterator(); it.hasNext();) {
            String token = (String) it.next();

            if (sa.length() > 0) {
                sa.append(STRING_DEFAULT_SEPARATOR);
            }

            char chs[] = token.trim().toCharArray();
            for (int i = 0; i < chs.length; i++) {
                char c = chs[i];

                if (c == STRING_DEFAULT_SEPARATOR || c == STRING_DEFAULT_ESCAPE
                        || c == '=') {
                    sa.append(STRING_DEFAULT_ESCAPE);
                }

                sa.append(c);
            }
        }

        return sa.toString();
    }

    public static Map parseTokensMap(String value) {

        if (value == null) {
            return null;
        }
        if (value.length() < 1) {
            return Collections.EMPTY_MAP;
        }

        char chs[] = value.toCharArray();

        Map map = new HashMap();

        StringAppender sa = new StringAppender(32);
        String key = null;

        for (int i = 0; i < chs.length; i++) {
            char c = chs[i];

            if (c == '=') {
                key = sa.toString().trim();
                sa.setLength(0);
                continue;
            }

            if (c == STRING_DEFAULT_ESCAPE) {
                i++;
                if (i == chs.length) {
                    break;
                }

                c = chs[i];
                sa.append(chs[i]);
                continue;
            }

            if (c == STRING_DEFAULT_SEPARATOR) {
                if (key == null) {
                    map.put(sa.toString().trim(), "");

                } else {
                    map.put(key, sa.toString().trim());
                }

                key = null;
                sa.setLength(0);
                continue;
            }

            sa.append(chs[i]);
        }

        if (key == null) {
            map.put(sa.toString().trim(), "");

        } else {
            map.put(key, sa.toString().trim());
        }

        return map;
    }

    public static String joinTokens(Map collection) {
        StringAppender sa = new StringAppender(collection.size() * 32);

        for (Iterator it = collection.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Entry) it.next();
            String token = (String) entry.getKey();
            String value = (String) entry.getValue();

            if (sa.length() > 0) {
                sa.append(STRING_DEFAULT_SEPARATOR);
            }

            char chs[] = token.trim().toCharArray();
            for (int i = 0; i < chs.length; i++) {
                char c = chs[i];

                if (c == STRING_DEFAULT_SEPARATOR || c == STRING_DEFAULT_ESCAPE
                        || c == '=') {
                    sa.append(STRING_DEFAULT_ESCAPE);
                }

                sa.append(c);
            }

            if (value != null && value.length() > 0) {
                sa.append('=');

                chs = token.trim().toCharArray();
                for (int i = 0; i < chs.length; i++) {
                    char c = chs[i];

                    if (c == STRING_DEFAULT_SEPARATOR
                            || c == STRING_DEFAULT_ESCAPE || c == '=') {
                        sa.append(STRING_DEFAULT_ESCAPE);
                    }

                    sa.append(c);
                }
            }
        }

        return sa.toString();
    }
}
