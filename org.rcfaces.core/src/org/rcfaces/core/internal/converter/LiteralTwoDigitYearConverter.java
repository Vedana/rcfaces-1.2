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
public class LiteralTwoDigitYearConverter extends TwoDigitYearConverter {
    

    public static final Converter SINGLETON = new LiteralTwoDigitYearConverter();

    protected boolean isLiteral() {
        return true;
    }
}
