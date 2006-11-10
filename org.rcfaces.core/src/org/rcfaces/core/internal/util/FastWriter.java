/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.util;

import javax.faces.FacesException;

import org.rcfaces.core.internal.lang.StringAppender;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class FastWriter {
    private static final String REVISION = "$Revision$";

    protected final StringAppender sa;

    private boolean closed = false;

    /**
     * Creates a new CharArrayWriter.
     */
    public FastWriter() {
        sa = new StringAppender();
    }

    public FastWriter(int initialSize) {
        sa = new StringAppender(initialSize);
    }

    public final FastWriter write(char c) {
        if (closed) {
            throw new FacesException("Writer is closed !");
        }
        sa.append(c);

        return this;
    }

    public final FastWriter write(String str) {
        if (closed) {
            throw new FacesException("Writer is closed !");
        }

        sa.append(str);

        return this;
    }

    public final String getBuffer() {
        return sa.toString();
    }

    public final int getSize() {
        return sa.length();
    }

    public final FastWriter ensure(int length) {
        if (closed) {
            throw new FacesException("Writer is closed !");
        }

        sa.ensure(length);

        return this;
    }

    protected void close() {
        this.closed = true;
    }
}
