/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.codec;

import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class JavascriptCodec {

    private static final String REVISION = "$Revision$";

    public static final String encodeJavaScript(String text, char sep) {
        if (text == null) {
            return null;
        }

        int l = text.length();
        if (l < 1) {
            return text;
        }

        char dst[] = new char[l * 2];

        int pos = encodeJavaScript(text, dst, sep);
        if (pos > 0) {
            return new String(dst, 0, pos);
        }

        return "";
    }

    public static final IJavaScriptWriter writeJavaScript(
            IJavaScriptWriter writer, String text, char separator)
            throws WriterException {
        if (text == null) {
            return writer;
        }

        int l = text.length();
        if (l < 1) {
            return writer;
        }

        char dst[] = new char[l * 2];

        int pos = encodeJavaScript(text, dst, separator);
        if (pos > 0) {
            writer.writeRaw(dst, 0, pos);
        }

        return writer;
    }

    static final int encodeJavaScript(String text, char dst[], char separator) {
        int l = text.length();

        text.getChars(0, l, dst, l);

        int opos = l; // ASTUCE de la mort !
        int dpos = 0;
        char c;
        for (int i = 0; i < l; i++) {
            c = dst[opos++];

            if (c == '\\' || c == separator) { // || c == '\"') {
                dst[dpos++] = '\\';
                dst[dpos++] = c;

            } else if (c == '\n') {
                dst[dpos++] = '\\';
                dst[dpos++] = 'n';

            } else if (c == '\t') {
                dst[dpos++] = '\\';
                dst[dpos++] = 't';

            } else if (c == '\r') {
                dst[dpos++] = '\\';
                dst[dpos++] = 'r';

            } else if (c < 32) {
                dst[dpos++] = ' ';

            } else {
                dst[dpos++] = c;
            }
        }

        return dpos;
    }
}
