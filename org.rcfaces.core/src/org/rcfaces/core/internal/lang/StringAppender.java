/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.2  2006/09/01 15:24:29  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 */
package org.rcfaces.core.internal.lang;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Java Team (latest modification by $Author$) 
 * @version $Revision$ $Date$
 */
public final class StringAppender {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(StringAppender.class);

    private char value[];

    private int count;

    public StringAppender() {
        this(16);
    }

    public StringAppender(int length) {
        value = new char[length];
    }

    public StringAppender(String str) {
        this(str, 16);
    }

    public StringAppender(String str, int length) {
        this(str.length() + length);
        append(str);
    }

    public int length() {
        return count;
    }

    private void expandCapacity(int minimumCapacity) {
        int newCapacity = (value.length + 1) * 2;
        if (newCapacity < 0) {
            newCapacity = Integer.MAX_VALUE;

        } else if (minimumCapacity > newCapacity) {
            newCapacity = minimumCapacity;
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace("Expand capacity from '" + value.length + "' to '"
                    + newCapacity + "'.");
        }

        char newValue[] = new char[newCapacity];
        System.arraycopy(value, 0, newValue, 0, count);
        value = newValue;
    }

    public void setLength(int newLength) {
        if (newLength < 0) {
            throw new StringIndexOutOfBoundsException(newLength);
        }

        if (newLength > value.length) {
            expandCapacity(newLength);
        }

        if (count < newLength) {
            for (; count < newLength; count++) {
                value[count] = '\0';
            }
            return;
        }

        count = newLength;
    }

    public char charAt(int index) {
        if ((index < 0) || (index >= count)) {
            throw new StringIndexOutOfBoundsException(index);
        }
        return value[index];
    }

    private void getChars(int srcBegin, int srcEnd, char dst[], int dstBegin) {
        if (srcBegin < 0) {
            throw new StringIndexOutOfBoundsException(srcBegin);
        }
        if ((srcEnd < 0) || (srcEnd > count)) {
            throw new StringIndexOutOfBoundsException(srcEnd);
        }
        if (srcBegin > srcEnd) {
            throw new StringIndexOutOfBoundsException("srcBegin > srcEnd");
        }
        System.arraycopy(value, srcBegin, dst, dstBegin, srcEnd - srcBegin);
    }

    /*
     * public void setCharAt(int index, char ch) { if ((index < 0) || (index >=
     * count)) { throw new StringIndexOutOfBoundsException(index); }
     * value[index] = ch; }
     */
    public StringAppender append(String str) {
        if (str == null) {
            str = String.valueOf(str);
        }

        int len = str.length();
        if (len == 0) {
            return this;
        }

        int newcount = count + len;
        if (newcount > value.length) {
            expandCapacity(newcount);
        }

        str.getChars(0, len, value, count);
        count = newcount;

        return this;
    }

    public StringAppender append(StringAppender sb) {
        if (sb == null) {
            return append((String) null);
        }

        int len = sb.length();
        int newcount = count + len;
        if (newcount > value.length) {
            expandCapacity(newcount);
        }

        sb.getChars(0, len, value, count);
        count = newcount;
        return this;
    }

    public StringAppender append(char str[]) {
        return append(str, 0, str.length);
    }

    public StringAppender append(char str[], int offset, int len) {
        int newcount = count + len;
        if (newcount > value.length) {
            expandCapacity(newcount);
        }

        System.arraycopy(str, offset, value, count, len);
        count = newcount;

        return this;
    }

    public StringAppender append(boolean b) {
        if (b) {
            return append("true");
        }

        return append("false");
    }

    public StringAppender append(char c) {
        int newcount = count + 1;
        if (newcount > value.length) {
            expandCapacity(newcount);
        }

        value[count++] = c;
        return this;
    }

    public StringAppender append(int i) {
        return append(String.valueOf(i));
    }

    public StringAppender append(long l) {
        return append(String.valueOf(l));
    }

    public StringAppender append(float f) {
        return append(String.valueOf(f));
    }

    public String toString() {
        return new String(value, 0, count);
    }

    public void copyInto(Writer writer) throws IOException {
        if (count < 1) {
            return;
        }

        writer.write(value, 0, count);
    }
}
