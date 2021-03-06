/*
 * $Id$
 */
package org.rcfaces.core.internal.converter;

import javax.faces.convert.Converter;

import org.rcfaces.core.converter.AbstractNumberConverter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class LiteralNumberConverter extends AbstractNumberConverter {
    

    public static final Converter SINGLETON = new LiteralNumberConverter();

}
