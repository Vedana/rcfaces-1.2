/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.codec;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public final class SourceFilter {
    private static final String REVISION = "$Revision$";

    public static final String filterSkipSpaces(String content) {
        char bufs[] = content.toCharArray();
        int pos = 0;

        boolean space = false;
        boolean newLine = false;
        char lastChar = 0;

        for (int i = 0; i < bufs.length; i++) {
            char c = bufs[i];

            if (Character.isWhitespace(c)) {
                if (c == '\n') {
                    if (lastChar != ';' && lastChar != ',' && lastChar != '|'
                            && lastChar != '&') {
                        newLine = true;
                    }
                }
                space = true;
                continue;
            }

            if (c == '/' && bufs[i + 1] == '*') {
                for (i++; i < bufs.length; i++) {
                    c = bufs[i];

                    if (c == '*' && bufs[i + 1] == '/') {
                        i++;
                        break;
                    }
                }

                continue;
            }

            if (c == '/' && bufs[i + 1] == '/') {
                for (i++; i < bufs.length; i++) {
                    c = bufs[i];

                    if (c == '\n') {
                        break;
                    }
                }

                continue;
            }

            if (c == '\'' || c == '\"') {
                bufs[pos++] = c;
                for (i++; i < bufs.length; i++) {
                    char c2 = bufs[i];
                    bufs[pos++] = c2;

                    if (c == c2) {
                        break;
                    }
                }

                lastChar = c;

                continue;
            }

            if (space) {
                space = false;
                if (newLine) {
                    bufs[pos++] = '\n';
                    newLine = false;

                } else if (lastChar != '}'
                        && Character.isJavaIdentifierPart(lastChar)
                        && Character.isJavaIdentifierPart(c)) {
                    bufs[pos++] = ' ';
                }
            }

            bufs[pos++] = c;
            lastChar = c;
        }

        return new String(bufs, 0, pos);
    }

    public static final String filter(String content) {
        char bufs[] = content.toCharArray();
        int pos = 0;

        boolean space = false;
        boolean newLine = false;

        for (int i = 0; i < bufs.length; i++) {
            char c = bufs[i];

            if (Character.isWhitespace(c)) {
                if (c == '\n') {
                    newLine = true;
                }
                space = true;
                continue;
            }

            if (c == '/' && bufs[i + 1] == '*') {
                for (i++; i < bufs.length; i++) {
                    c = bufs[i];

                    if (c == '*' && bufs[i + 1] == '/') {
                        i++;
                        break;
                    }
                }

                continue;
            }

            if (c == '/' && bufs[i + 1] == '/') {
                for (i++; i < bufs.length; i++) {
                    c = bufs[i];

                    if (c == '\n') {
                        break;
                    }
                }

                continue;
            }

            if (c == '\'' || c == '\"') {
                bufs[pos++] = c;
                for (i++; i < bufs.length; i++) {
                    char c2 = bufs[i];
                    bufs[pos++] = c2;

                    if (c == c2) {
                        break;
                    }
                }

                continue;
            }

            if (space) {
                space = false;
                if (newLine) {
                    bufs[pos++] = '\n';
                    newLine = false;

                } else {
                    bufs[pos++] = ' ';
                }
            }

            bufs[pos++] = c;
        }

        return new String(bufs, 0, pos);
    }

}
