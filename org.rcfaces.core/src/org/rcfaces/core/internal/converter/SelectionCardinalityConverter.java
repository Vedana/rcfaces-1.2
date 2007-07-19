/*
 * $Id$
 */
package org.rcfaces.core.internal.converter;

import javax.faces.convert.Converter;

import org.rcfaces.core.component.capability.ISelectionCardinalityCapability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SelectionCardinalityConverter extends CardinalityConverter {
    private static final String REVISION = "$Revision$";

    public static final Converter SINGLETON = new SelectionCardinalityConverter();

    private static final Integer DEFAULT_CARDINALITY = new Integer(
            ISelectionCardinalityCapability.DEFAULT_CARDINALITY);

    protected Object getDefaultCardinality() {
        return DEFAULT_CARDINALITY;
    }

}
