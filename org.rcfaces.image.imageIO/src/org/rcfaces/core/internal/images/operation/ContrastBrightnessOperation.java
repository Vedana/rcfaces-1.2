/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.images.operation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ContrastBrightnessOperation extends ColorsRescaleOperation {
    private static final String REVISION = "$Revision$";

    protected String getOffsetPropertyName() {
        return "brightness";
    }

    protected String getScalePropertyName() {
        return "contrast";
    }
}
