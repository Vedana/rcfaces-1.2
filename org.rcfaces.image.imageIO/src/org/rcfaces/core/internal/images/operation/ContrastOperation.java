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
public class ContrastOperation extends ContrastBrightnessOperation {
    private static final String REVISION = "$Revision$";

    protected String getDefaultPropertyName() {
        return getScalePropertyName();
    }
}
