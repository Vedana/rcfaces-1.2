/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.layout;

import javax.faces.component.UIComponent;

public class AbsoluteLayoutProcessor extends AbstractLayoutProcessor {
    public static ILayoutProcessor singleton = new AbsoluteLayoutProcessor();

    public static ILayoutProcessor getSingleton() {
        return singleton;
    }

    private AbsoluteLayoutProcessor() {

    }

    public boolean layoutChildren(String zoneId, UIComponent component,
            ILayoutContainerRenderer layoutRenderer) {

        Size size = computeSize(component);
        if (size == null) {
            return false;
        }

        Size internalSize = layoutRenderer.computeInternalSize(zoneId, size);
        if (internalSize == null) {
            return false;
        }

        return true;
    }

}
