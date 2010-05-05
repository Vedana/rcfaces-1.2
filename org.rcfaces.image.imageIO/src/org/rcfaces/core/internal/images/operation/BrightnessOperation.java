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
public class BrightnessOperation extends ContrastBrightnessOperation {
    private static final String REVISION = "$Revision$";

    protected String getDefaultPropertyName() {
        return getOffsetPropertyName();
    }
}
