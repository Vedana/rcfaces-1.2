/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.images.operation;

import org.rcfaces.core.image.operation.ISelectedOperation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SelectedOperation extends ContrastBrightnessOperation implements
        ISelectedOperation {
    private static final String REVISION = "$Revision$";

    public static final String ID = "selected";

    protected float getDefaultOffset() {
        return -0.3f;
    }

    protected float getDefaultScale() {
        return 1.3f;
    }
}
