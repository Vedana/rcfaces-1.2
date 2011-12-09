/*
 * $Id$
 */
package org.rcfaces.core.internal.tools;

import javax.faces.context.FacesContext;

public class AsyncModeTools {

    public static boolean isTagProcessor(Object object) {
        return true;
    }

    public static String getEnableValue(FacesContext context) {
        return null; // NEVER CALLED
    }

}
