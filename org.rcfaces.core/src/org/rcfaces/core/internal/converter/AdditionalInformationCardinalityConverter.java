/*
 * $Id$
 */
package org.rcfaces.core.internal.converter;

import javax.faces.convert.Converter;

import org.rcfaces.core.component.capability.IAdditionalInformationCardinalityCapability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class AdditionalInformationCardinalityConverter extends
        CardinalityConverter {
    private static final String REVISION = "$Revision$";

    public static final Converter SINGLETON = new AdditionalInformationCardinalityConverter();

    private static final Integer DEFAULT_CARDINALITY = new Integer(
            IAdditionalInformationCardinalityCapability.DEFAULT_CARDINALITY);

    protected Object getDefaultCardinality() {
        return DEFAULT_CARDINALITY;
    }

}
