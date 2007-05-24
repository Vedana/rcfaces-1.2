/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.tools;

import javax.faces.component.UIComponent;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public final class VisibilityTools {
    private static final String REVISION = "$Revision$";

    public static final boolean isVisible(UIComponent component) {
        for (; component != null; component = component.getParent()) {
            if (component.isRendered() == false) {
                return false;
            }
        }

        return true;
    }
}
