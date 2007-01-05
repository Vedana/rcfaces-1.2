/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.converter;

import javax.faces.convert.Converter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class LiteralTimeConverter extends TimeConverter {
    private static final String REVISION = "$Revision$";

    public static final Converter SINGLETON = new LiteralTimeConverter();

    protected boolean isLiteral() {
        return true;
    }

}
