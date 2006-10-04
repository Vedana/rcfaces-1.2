/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/10/04 12:31:59  oeuillot
 * Stabilisation
 *
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
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

import org.rcfaces.core.internal.lang.StringAppender;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class FastWriter {
    private static final String REVISION = "$Revision$";

    protected final StringAppender sa;

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
        sa.append(c);

        return this;
    }

    public final FastWriter write(String str) {
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
        sa.ensure(length);

        return this;
    }
}