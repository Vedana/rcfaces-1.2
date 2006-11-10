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
public class HoverOperation extends ContrastBrithnessOperation {
    private static final String REVISION = "$Revision$";

    public static final String ID = "hover";

    protected float getDefaultOffset() {
        return -0.3f;
    }

    protected float getDefaultScale() {
        return 1.3f;
    }
}
