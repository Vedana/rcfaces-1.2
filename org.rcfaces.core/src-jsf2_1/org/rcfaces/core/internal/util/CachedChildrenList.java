/*
 * $Id$
 */
package org.rcfaces.core.internal.util;

import java.util.List;

import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.manager.IContainerManager;

public class CachedChildrenList {

    public static UIComponent[] getArray(IContainerManager parent,
            Class childClass) {
        throw new UnsupportedOperationException("Not supported for jsf 2.0");
    }

    public static int getCount(IContainerManager parent, Class childClass) {
        throw new UnsupportedOperationException("Not supported for jsf 2.0");
    }

    public static List getList(IContainerManager parent, Class childClass) {
        throw new UnsupportedOperationException("Not supported for jsf 2.0");
    }

}
