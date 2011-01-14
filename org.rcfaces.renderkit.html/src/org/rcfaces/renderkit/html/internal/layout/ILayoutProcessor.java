/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.layout;

import javax.faces.component.UIComponent;

public interface ILayoutProcessor {
    boolean layoutChildren(String zoneId, UIComponent component,
            ILayoutContainerRenderer layoutRenderer);
}
