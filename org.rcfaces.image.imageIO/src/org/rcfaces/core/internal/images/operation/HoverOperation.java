/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.images.operation;

import org.rcfaces.core.image.operation.IHoverOperation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HoverOperation extends ContrastBrithnessOperation implements
        IHoverOperation {
    private static final String REVISION = "$Revision$";

    protected float getDefaultOffset() {
        return -0.3f;
    }

    protected float getDefaultScale() {
        return 1.3f;
    }
}
