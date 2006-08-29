/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.3  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.2  2004/08/06 14:18:12  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/06 14:03:57  jmerlin
 * Vedana Faces
 *
 * Revision 1.1  2004/08/06 12:03:49  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.internal.util;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class FastWriter {
    private static final String REVISION = "$Revision$";

    private static final char EMPTY_BUFFER[] = new char[0];

    protected char buffer[];

    protected int count;

    /**
     * Creates a new CharArrayWriter.
     */
    public FastWriter() {
        buffer = EMPTY_BUFFER;
    }

    public FastWriter(int initialSize) {
        buffer = new char[initialSize];
    }

    public final FastWriter write(int c) {
        int newcount = count + 1;
        if (newcount > buffer.length) {
            char newbuf[] = new char[Math.max(buffer.length << 1, newcount)];
            System.arraycopy(buffer, 0, newbuf, 0, count);
            buffer = newbuf;
        }
        buffer[count] = (char) c;
        count = newcount;

        return this;
    }

    public final FastWriter write(String str, int off, int len) {
        int newcount = count + len;
        if (newcount > buffer.length) {
            char newbuf[] = new char[Math.max(buffer.length << 1, newcount)];
            System.arraycopy(buffer, 0, newbuf, 0, count);
            buffer = newbuf;
        }
        str.getChars(off, off + len, buffer, count);
        count = newcount;

        return this;
    }

    public final FastWriter write(String str) {
        write(str, 0, str.length());

        return this;
    }

    public final void ensure(int len) {
        int newcount = count + len;
        if (newcount <= buffer.length) {
            return;
        }

        char newbuf[] = new char[Math.max(buffer.length << 1, newcount)];
        System.arraycopy(buffer, 0, newbuf, 0, count);
        buffer = newbuf;
    }

    public final String getBuffer() {
        return new String(buffer, 0, count);
    }

    public final int getSize() {
        return count;
    }
}