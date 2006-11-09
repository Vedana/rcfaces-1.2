/*
 * $Id$
 */
package org.rcfaces.core.internal.component;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractPropertiesAccessor implements IPropertiesAccessor {
    private static final String REVISION = "$Revision$";

    protected Map createMap(int size) {
        return new HashMap(size);
    }
}
