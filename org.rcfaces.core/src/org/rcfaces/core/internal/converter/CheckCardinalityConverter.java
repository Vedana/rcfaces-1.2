/*
 * $Id$
 */
package org.rcfaces.core.internal.converter;

import javax.faces.convert.Converter;

import org.rcfaces.core.component.capability.ICheckCardinalityCapability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CheckCardinalityConverter extends CardinalityConverter {
    

    public static final Converter SINGLETON = new CheckCardinalityConverter();

    private static final Integer DEFAULT_CARDINALITY = new Integer(
            ICheckCardinalityCapability.DEFAULT_CARDINALITY);

    protected Object getDefaultCardinality() {
        return DEFAULT_CARDINALITY;
    }

}
