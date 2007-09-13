/*
 * $Id$
 */
package org.rcfaces.core.internal.component;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;

import org.rcfaces.core.internal.capability.IAsyncRenderComponent;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class AsyncHtmlOutputText extends HtmlOutputText {

    public boolean isTransient() {
        UIComponent parent = getParent();
        for (; parent != null; parent = parent.getParent()) {
            if (parent instanceof IAsyncRenderComponent) {
                return false;
            }
        }

        return super.isTransient();
    }
}
